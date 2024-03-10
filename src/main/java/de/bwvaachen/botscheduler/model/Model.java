package de.bwvaachen.botscheduler.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
public class Model implements ModelInterface{
	
	private List<Schueler> schueler = new ArrayList<>();
	private List<Kurse> kurse = new ArrayList<>();
	private List<Unternehmen> unternehmen = new ArrayList<>();
	private List<Raum> raeume = new ArrayList<>();
	private List<CalcSchueler> cSchueler;
	private IDatabase database = new DBModel();

	
	public Model() throws Exception {
		loadFromDB();
	}
	
	
	@Override
	public String belegeKurse() throws IllegalStateException{
		
		if(schueler.size()==0) {
			throw new IllegalStateException("Keine Schülerliste geladen!");
		}
		
		if(unternehmen.size()==0) {
			throw new IllegalStateException("Keine Veranstaltungsliste geladen!");
		}
		
		if(raeume.size()==0) {
			throw new IllegalStateException("Keine Raumliste geladen!");
		}
		
		KursPlaner planer = new KursPlaner();
		String score = planer.belegeKurse(schueler, unternehmen, raeume);
		kurse = planer.getKurse();
		cSchueler = planer.getcSchueler();
		
		return score;
	}


	@Override
	public void createStudent(int schuelerID, String vorname, String nachname, ArrayList<String> wuensche,
			String klasse) {
		Schueler schuel = new Schueler(klasse, vorname, nachname, wuensche);
		schueler.add(schuel);
	}


	@Override
	public void createCompany(String firmenName, int firmenID, int maxTeilnehmer, ArrayList<Integer> zeitslots,
			double gewichtung, boolean aktiv) {
		Typ fruehesterSlot = Typ.values()[(int)(Collections.min(zeitslots))];
		Unternehmen unt = new Unternehmen(firmenID, firmenName, "", maxTeilnehmer, zeitslots.size(), fruehesterSlot.name());
		unternehmen.add(unt);
	}



	@Override
	public void editStudent(Schueler schueler) throws Exception {
		// TODO Auto-generated method stub
		saveToDB();
	}

	@Override
	public void deleteStudent(Schueler schueler) {
		kurse = new ArrayList<>();
		this.schueler.remove(schueler);
		
		try {
			for(CalcSchueler cSchuel : cSchueler) {
				if(cSchuel.getSchueler().equals(schueler)) {
					cSchueler.remove(cSchuel);
				}
			}
			saveToDB();
		}catch(Exception e){
			//do nothing
		}
	}

	@Override
	public void editCompany(Unternehmen unternehmen) throws Exception {
		// TODO Auto-generated method stub
		saveToDB();		
	}

	@Override
	public void deleteCompany(Unternehmen unternehmen) throws Exception {
		kurse = new ArrayList<>();
		this.unternehmen.remove(unternehmen);
		saveToDB();
	}

	@Override
	public List<Schueler> importStudent(String absolutePath) throws Exception {
		
		schueler = ImportFile.getChoices(absolutePath);
		saveToDB();
		return schueler;
	}


	@Override
	public List<Schueler> getAllStudents() {
		return schueler;
	}

	@Override
	public void saveAllStudents(List<Schueler> students) throws Exception {
		kurse = new ArrayList<>();
		this.schueler = students;
		saveToDB();		
	}

	@Override
	public void exportStudent(String path, List<Schueler> students) {
		IExport exporter = new ExportFile();
		exporter.exportStudentData(students, path);
	}

	@Override
	public List<Raum> getAllRooms() {
		return raeume;
	}

	@Override
	public void saveAllRooms(List<Raum> rooms) throws Exception {
		kurse = new ArrayList<>();
		this.raeume = rooms;
		saveToDB();
	}

	@Override
	public void createRoom(String name) throws Exception {
		this.raeume.add(new Raum(name, 20));
		saveToDB();
	}

	@Override
	public void editRoom(Raum room) {
		//wieso?		
	}

	@Override
	public void deleteRoom(Raum room) throws Exception {
		kurse = new ArrayList<>();
		this.raeume.remove(room);
		saveToDB();
	}

	@Override
	public List<Raum> importRooms(String path) throws Exception {
		this.raeume = ImportFile.getRoom(path);
		saveToDB();
		return raeume;
	}

	@Override
	public void exportRooms(String path, List<Raum> rooms) {
		IExport exporter = new ExportFile();
		exporter.exportRoomData(rooms, path);		
	}

	@Override
	public List<Unternehmen> getAllCompanies() {
		return unternehmen;
	}

	@Override
	public void saveAllCompanies(List<Unternehmen> companies) throws Exception {
		kurse = new ArrayList<>();
		this.unternehmen = companies;
		saveToDB();
	}

	@Override
	public List<Unternehmen> importCompany(String absolutePath) throws Exception {
		this.unternehmen = ImportFile.getCompany(absolutePath);
		saveToDB();
		return unternehmen;
	}

	@Override
	public void exportCompany(String path, List<Unternehmen> companies) {
		IExport exporter = new ExportFile();
		exporter.exportCompanyData(companies, path);			
	}


	@Override
	public void exportSchuelerSchedule(String path) throws FileNotFoundException, IOException {
		IExport exporter = new ExportFile();
		exporter.exportStudentSchedule(cSchueler, path);		
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
	
	
	private void saveToDB() throws Exception {
		database.saveState(raeume, schueler, createUnternehmenDAOs(), createKursDAOs());
	}
	
	
	private List<UnternehmenDAO> createUnternehmenDAOs(){
		List<UnternehmenDAO> retVal = new ArrayList<>();
		
		for(Unternehmen unt : unternehmen) {
			retVal.add(DAOFactory.createUnternehmenDAO(unt));
		}		
		return retVal;
	}
	
	
	private List<KursDAO> createKursDAOs(){
		List<KursDAO> retVal = new ArrayList<>();
		
		for(Kurse kurs : kurse) {
			retVal.add(DAOFactory.createKursDAO(kurs));
		}
		return retVal;
	}
	
	private void loadFromDB() throws SQLException, ClassNotFoundException {
		
		schueler = database.loadSchueler();
		raeume = database.loadRooms();
		
		unternehmen = new ArrayList<>();

		List<UnternehmenDAO> unternehmenDAOS = database.loadUnternehmen();
		
		for(UnternehmenDAO untDAO : unternehmenDAOS) {
			unternehmen.add(new Unternehmen(untDAO.getFirmenID(),
											untDAO.getUnternehmen(),
											untDAO.getFachrichtung(),
											untDAO.getMaxTeilnehmer(),
											untDAO.getMaxVeranstaltungen(),
											untDAO.getFruehesterZeitslot()));
		}		
		
		cSchueler = new ArrayList<>();
		
		for(Schueler schuel : schueler) {
			cSchueler.add(new CalcSchueler(schuel, unternehmen));
		}
		
		kurse = new ArrayList<>();
		
		for(KursDAO kursDAO : database.loadKurse(schueler,raeume,unternehmenDAOS)) {
			List<CalcSchueler> teilnehmer = new ArrayList<>();
			

			
			for(Schueler schuel : kursDAO.getKursTeilnehmer()) {
				teilnehmer.add(findCalcSchueler(schuel));
			}
			
			Kurse kurs = new Kurse(teilnehmer, findUnternehmen(kursDAO.getUnternehmen().getFirmenID()), 
									new Zeitslot(Typ.valueOf(kursDAO.getZeitslot())));
			kurse.add(kurs);
			
			for(CalcSchueler cSchuel : kurs.getKursTeilnehmer()) {
				cSchuel.getSlotByType(kurs.getZeitslot().getTyp()).setKurs(kurs);
			}
			
			kurs.getUnternehmen().getKurse().put(kurs.getZeitslot().getTyp(), kurs);
		}
		
	}
	
	
	private CalcSchueler findCalcSchueler(Schueler schuel) {
		CalcSchueler retVal = null;
		
		for(CalcSchueler cSchuel : cSchueler) {
			if(schuel.equals(cSchuel.getSchueler())) {
				retVal = cSchuel;
				break;
			}
		}		
		return retVal;
	}
	
	private Unternehmen findUnternehmen(int id){
		Unternehmen retVal = null;
		
		for(Unternehmen unt : unternehmen) {
			if(unt.getFirmenID() == id) {
				retVal = unt;
				break;
			}
		}		
		return retVal;
	}
		
	
}
