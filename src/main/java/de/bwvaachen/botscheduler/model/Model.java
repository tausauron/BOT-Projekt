package de.bwvaachen.botscheduler.model;

import java.util.ArrayList;
import java.util.List;

import de.bwvaachen.botscheduler.calculate.CalcSchueler;
import de.bwvaachen.botscheduler.calculate.KursPlaner;
import de.bwvaachen.botscheduler.grassmann.myInterface.ModelInterface;
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
	public String belegeKurse() {
		KursPlaner planer = new KursPlaner();
		String score = planer.belegeKurse(schueler, unternehmen, raeume);
		kurse = planer.getKurse();
		cSchueler = planer.getcSchueler();
		
		return score;
	}


	@Override
	public void createStudent(int schuelerID, String vorname, String nachname, ArrayList<String> wuensche,
			String klasse) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void createCompany(String firmenName, int firmenID, int maxTeilnehmer, ArrayList<Integer> zeitslots,
			double gewichtung, boolean aktiv) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void editStudent(Schueler schueler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStudent(Schueler schueler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editCompany(Unternehmen unternehmen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCompany(Unternehmen unternehmen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Schueler> importStudent(String absolutePath) {
		
		schueler = ImportFile.getChoices(absolutePath);
		return schueler;
	}


	@Override
	public List<Schueler> getAllStudents() {
		return schueler;
	}

	@Override
	public void saveAllStudents(List<Schueler> students) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportStudent(String path, List<Schueler> students) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Raum> getAllRooms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAllRooms(List<Raum> rooms) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createRoom(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editRoom(Raum room) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRoom(Raum room) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Raum> importRooms(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exportRooms(String path, List<Raum> rooms) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Unternehmen> getAllCompanies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAllCompanies(List<Unternehmen> companies) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Unternehmen> importCompany(String absolutePath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exportCompany(String path, List<Unternehmen> companies) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void exportSchuelerSchedule(String path) {
		// TODO Auto-generated method stub
		
	}

}
