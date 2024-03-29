package de.bwvaachen.botscheduler.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.bwvaachen.botscheduler.raumAlgorithm.RaumAlgorithmus;
import de.bwvaachen.botscheduler.calculate.CalcSchueler;
import de.bwvaachen.botscheduler.calculate.KursPlaner;
import de.bwvaachen.botscheduler.calculate.Zeitslot;
import de.bwvaachen.botscheduler.calculate.Zeitslot.Typ;
import de.bwvaachen.botscheduler.database.DBModel;
import de.bwvaachen.botscheduler.database.IDatabase;
import de.bwvaachen.botscheduler.grassmann.myInterface.ModelInterface;
import execlLoad.ExportFile;
import execlLoad.IExport;
import execlLoad.ImportFile;
import klassenObjekte.Kurse;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

/**
 * Zentrale Modellklasse zur Verwaltung aller Daten
 * 
 * @author Max Tautenhahn
 */
public class Model implements ModelInterface {
	
	private static final String LAUFZETTEL = "Laufzettel.xlsx";
	private static final String RAUMPLAN = "Raumplan.xlsx";
	private static final String ANWESENHEIT = "Anwesenheitsliste.xlsx";	
	private static final String DIRECTORYRESULTS = "BOT-Ergebnis";	

	private List<Schueler> schuelerInput = new ArrayList<>();
	private List<Kurse> kurse = new ArrayList<>();
	private List<Unternehmen> unternehmen = new ArrayList<>();
	private List<Unternehmen> unternehmenInput = new ArrayList<>();
	private List<Raum> raeume = new ArrayList<>();
	private List<Raum> raeumeInput = new ArrayList<>();
	private List<CalcSchueler> cSchueler = new ArrayList<>();
	private final IDatabase database;

	public Model() throws Exception {
		Path pfad = Path.of(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
		Path p = Paths.get(pfad.toString(),"..", "data", "BOT-Database");
		database = new DBModel(p.toString());
		loadFromDB();
	}


	@Override
	public String belegeKurse() throws IllegalStateException {

		unternehmen = copyUnternehmenInput();
		raeume = new ArrayList<>(raeumeInput);
		cSchueler = new ArrayList<>();
		kurse = new ArrayList<>();

		if (schuelerInput.size() == 0) {
			throw new IllegalStateException("Keine Schülerliste geladen!");
		}

		if (unternehmen.size() == 0) {
			throw new IllegalStateException("Keine Veranstaltungsliste geladen!");
		}

		if (raeume.size() == 0) {
			throw new IllegalStateException("Keine Raumliste geladen!");
		}

		KursPlaner planer = new KursPlaner();
		String score = planer.belegeKurse(schuelerInput, unternehmen, raeume);
		kurse = planer.getKurse();
		cSchueler = planer.getcSchueler();
		
		RaumAlgorithmus raumAlg = new RaumAlgorithmus();
		raumAlg.verteileVeranstaltungenAufRaeume(kurse, raeume);

		return score;
	}

	@Override
	public List<Schueler> importStudent(String absolutePath) throws Exception {

		schuelerInput = ImportFile.getChoices(absolutePath);
		return schuelerInput;
	}
	

	@Override
	public List<Schueler> getAllStudents() {
		return new ArrayList<>(schuelerInput);
	}
	

	@Override
	public void saveAllStudents(List<Schueler> students) throws Exception {
		this.schuelerInput = new ArrayList<>(students);
		saveToDB();
	}
	

	@Override
	public void exportStudent(String path, List<Schueler> students) {
		IExport exporter = new ExportFile();
		exporter.exportStudentData(students, path);
	}
	

	@Override
	public List<Raum> getAllRooms() {
		return new ArrayList<>(raeumeInput);
	}
	

	@Override
	public void saveAllRooms(List<Raum> rooms) throws Exception {
		this.raeumeInput = new ArrayList<>(rooms);
		saveToDB();
	}
	

	@Override
	public List<Raum> importRooms(String path) throws Exception {
		this.raeumeInput = ImportFile.getRoom(path);
		return raeumeInput;
	}
	

	@Override
	public void exportRooms(String path, List<Raum> rooms) {
		IExport exporter = new ExportFile();
		exporter.exportRoomData(rooms, path);
	}
	

	@Override
	public List<Unternehmen> getAllCompanies() {
		return new ArrayList<>(unternehmenInput);
	}
	

	@Override
	public void saveAllCompanies(List<Unternehmen> companies) throws Exception {
		this.unternehmenInput = new ArrayList<>(companies);
		saveToDB();
	}
	

	@Override
	public List<Unternehmen> importCompany(String absolutePath) throws Exception {
		this.unternehmenInput = ImportFile.getCompany(absolutePath);
		return unternehmenInput;
	}
	

	@Override
	public void exportCompany(String path, List<Unternehmen> companies) {
		IExport exporter = new ExportFile();
		exporter.exportCompanyData(companies, path);
	}
	

	@Override
	public void exportLoesung(String path) throws FileNotFoundException, IOException {
		IExport exporter = new ExportFile();
		
		Path targetDirPath = Path.of(path, DIRECTORYRESULTS);		
		File targetDir = new File(targetDirPath.toString());
		if (!targetDir.exists()){
	        targetDir.mkdir();
	    }
		
		path = targetDirPath.toString();
		
		String schuelerSchedulePath = Path.of(path, LAUFZETTEL).toString();
		String roomPlanPath = Path.of(path, RAUMPLAN).toString();
		String attendencePath = Path.of(path, ANWESENHEIT).toString();

		if (cSchueler.size() == 0) {
			throw new IllegalStateException("Noch keine Kursbelegung kalkuliert.");
		}
		exporter.exportStudentSchedule(cSchueler, schuelerSchedulePath);
		exporter.exportRoomUsage(unternehmen, roomPlanPath);
		exporter.exportParticipants(unternehmen, attendencePath);
	}
	

	@Override
	public Boolean checkLogin(String username, String password) {
		try {
			String decryptUsername = StringEncryption.decrypt(ModelInterface.username, ModelInterface.key);
			String decryptPassword = StringEncryption.decrypt(ModelInterface.password, ModelInterface.key);

			if (decryptUsername.equals(username) && decryptPassword.equals(password)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

	/**
	 * Speichert den aktuellen Datenstand in der Datenbank
	 * 
	 * @throws Exception
	 */
	private void saveToDB() throws Exception {
		database.saveState(raeume, getSchuelerFromResult(), createUnternehmenDAOs(unternehmen), createKursDAOs(),
				raeumeInput, schuelerInput, createUnternehmenDAOs(unternehmenInput));
	}
	

	private List<UnternehmenDAO> createUnternehmenDAOs(List<Unternehmen> unternehmen) {
		List<UnternehmenDAO> retVal = new ArrayList<>();

		for (Unternehmen unt : unternehmen) {
			retVal.add(DAOFactory.createUnternehmenDAO(unt));
		}
		return retVal;
	}
	

	private List<KursDAO> createKursDAOs() {
		List<KursDAO> retVal = new ArrayList<>();

		for (Kurse kurs : kurse) {
			retVal.add(DAOFactory.createKursDAO(kurs));
		}
		return retVal;
	}
	

	/**
	 * laedt, falls vorhanden einen frueheren Datenbestand aus der Datenbank
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private void loadFromDB() throws SQLException, ClassNotFoundException {

		schuelerInput = database.loadSchuelerInput();
		List<Schueler> schueler = database.loadSchueler();
		raeume = database.loadRooms();
		raeumeInput = database.loadRoomsInput();
		unternehmen = new ArrayList<>();

		List<UnternehmenDAO> unternehmenInputDAOs = database.loadUnternehmenInput();
		for (UnternehmenDAO untDAO : unternehmenInputDAOs) {
			unternehmenInput
					.add(new Unternehmen(untDAO.getFirmenID(), untDAO.getUnternehmen(), untDAO.getFachrichtung(),
							untDAO.getMaxTeilnehmer(), untDAO.getMaxVeranstaltungen(), untDAO.getFruehesterZeitslot()));
		}

		List<UnternehmenDAO> unternehmenDAOS = database.loadUnternehmen();
		for (UnternehmenDAO untDAO : unternehmenDAOS) {
			unternehmen.add(new Unternehmen(untDAO.getFirmenID(), untDAO.getUnternehmen(), untDAO.getFachrichtung(),
					untDAO.getMaxTeilnehmer(), untDAO.getMaxVeranstaltungen(), untDAO.getFruehesterZeitslot()));
		}

		cSchueler = new ArrayList<>();

		for (Schueler schuel : schueler) {
			cSchueler.add(new CalcSchueler(schuel, unternehmen));
		}

		kurse = new ArrayList<>();

		for (KursDAO kursDAO : database.loadKurse(schueler, raeume, unternehmenDAOS)) {
			List<CalcSchueler> teilnehmer = new ArrayList<>();

			for (Schueler schuel : kursDAO.getKursTeilnehmer()) {
				teilnehmer.add(findCalcSchueler(schuel));
			}

			Kurse kurs = new Kurse(teilnehmer, findUnternehmen(kursDAO.getUnternehmen().getFirmenID()),
					new Zeitslot(Typ.valueOf(kursDAO.getZeitslot())));
			kurse.add(kurs);

			for (CalcSchueler cSchuel : kurs.getKursTeilnehmer()) {
				cSchuel.getSlotByType(kurs.getZeitslot().getTyp()).setKurs(kurs);
			}

			kurs.getUnternehmen().getKurse().put(kurs.getZeitslot().getTyp(), kurs);
		}

	}
	

	/**
	 * findet das passende Ergebnis-Schuelerobjekt fuer einen gesuchten Schueler
	 * 
	 * @param schuel gesuchter Schueler
	 * @return gefundenes CalcSchueler-Objekt
	 */
	private CalcSchueler findCalcSchueler(Schueler schuel) {
		CalcSchueler retVal = null;

		for (CalcSchueler cSchuel : cSchueler) {
			if (schuel.equals(cSchuel.getSchueler())) {
				retVal = cSchuel;
				break;
			}
		}
		return retVal;
	}

	/**
	 * findet das passende Unternehmensobjekt mit einer Id
	 * 
	 * @param id gesuchte Id
	 * @return gefundenes Unternehmensobjekt
	 */
	private Unternehmen findUnternehmen(int id) {
		Unternehmen retVal = null;

		for (Unternehmen unt : unternehmen) {
			if (unt.getFirmenID() == id) {
				retVal = unt;
				break;
			}
		}
		return retVal;
	}


	/**
	 * extrahiert die Schuelerliste aus einer vorhandenen Ergebnis-Schuelerliste
	 * 
	 * @return extrahierte Schuelerliste
	 */
	private List<Schueler> getSchuelerFromResult() {
		List<Schueler> retVal = new ArrayList<>();

		for (CalcSchueler cSchuel : cSchueler) {
			retVal.add(cSchuel.getSchueler());
		}
		return retVal;
	}
	

	@Override
	public void deleteAllStudent() throws Exception {
		this.schuelerInput = new ArrayList<Schueler>();
		saveToDB();

	}

	
	@Override
	public void deleteAllRooms() throws Exception {
		this.raeumeInput = new ArrayList<Raum>();
		saveToDB();
	}

	
	@Override
	public void deleteAllCompanies() throws Exception {
		this.unternehmenInput = new ArrayList<Unternehmen>();
		saveToDB();
	}
	
	
	/**
	 * Erzeugt eine Kopie der vorhandenen Unternehmensdaten
	 * 
	 * @return kopierte Unternehmensliste
	 */
	private List<Unternehmen> copyUnternehmenInput(){
		List<Unternehmen> retVal = new ArrayList<>();
		
		for(Unternehmen unt : unternehmenInput) {
			retVal.add(new Unternehmen(unt.getFirmenID(),
					unt.getUnternehmen(),
					unt.getFachrichtung(),
					unt.getMaxTeilnehmer(),
					unt.getMaxVeranstaltungen(),
					unt.getFruehesterZeitslot()));
		}		
		return retVal;
	}
}
