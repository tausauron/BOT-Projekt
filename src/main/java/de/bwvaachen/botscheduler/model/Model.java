package de.bwvaachen.botscheduler.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.bwvaachen.botscheduler.calculate.CalcSchueler;
import de.bwvaachen.botscheduler.calculate.KursPlaner;
import de.bwvaachen.botscheduler.calculate.Zeitslot.Typ;
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
	
	List<Schueler> schueler = new ArrayList<>();
	List<Kurse> kurse = new ArrayList<>();
	List<Unternehmen> unternehmen = new ArrayList<>();
	List<Raum> raeume = new ArrayList<>();
	List<CalcSchueler> cSchueler;

//	@Override
//	public Boolean checkLogin(String username, String password) {
//		// TODO Auto-generated method stub
//		return true;
//	}
	
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
	public void editStudent(Schueler schueler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStudent(Schueler schueler) {
		this.schueler.remove(schueler);
		
		try {
			for(CalcSchueler cSchuel : cSchueler) {
				if(cSchuel.getSchueler().equals(schueler)) {
					cSchueler.remove(cSchuel);
				}
			}
		}catch(Exception e){
			//do nothing
		}
	}

	@Override
	public void editCompany(Unternehmen unternehmen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCompany(Unternehmen unternehmen) {
		this.unternehmen.remove(unternehmen);		
	}

	@Override
	public List<Schueler> importStudent(String absolutePath) throws IllegalArgumentException{
		
		schueler = ImportFile.getChoices(absolutePath);
		return schueler;
	}


	@Override
	public List<Schueler> getAllStudents() {
		return schueler;
	}

	@Override
	public void saveAllStudents(List<Schueler> students) {
		this.schueler = students;
		
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
	public void saveAllRooms(List<Raum> rooms) {
		this.raeume = rooms;		
	}

	@Override
	public void createRoom(String name) {
		this.raeume.add(new Raum(name, 20));		
	}

	@Override
	public void editRoom(Raum room) {
		//wieso?		
	}

	@Override
	public void deleteRoom(Raum room) {
		this.raeume.remove(room);
	}

	@Override
	public List<Raum> importRooms(String path) throws IllegalArgumentException{
		this.raeume = ImportFile.getRoom(path);
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
	public void saveAllCompanies(List<Unternehmen> companies) {
		this.unternehmen = companies;		
	}

	@Override
	public List<Unternehmen> importCompany(String absolutePath) throws IllegalArgumentException{
		this.unternehmen = ImportFile.getCompany(absolutePath);
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
}
