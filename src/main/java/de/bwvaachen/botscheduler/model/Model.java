package de.bwvaachen.botscheduler.model;

import java.util.ArrayList;
import java.util.List;

import de.bwvaachen.botscheduler.calculate.KursPlaner;
import de.bwvaachen.botscheduler.grassmann.myInterface.ModelInterface;
import execlLoad.ImportFile;
import klassenObjekte.Kurse;
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

	@Override
	public Boolean checkLogin(String username, String password) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void belegeKurse() {
		KursPlaner planer = new KursPlaner();
		kurse = planer.belegeKurse(schueler, unternehmen);
	}

	@Override
	public Schueler getStudent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createStudent(int schuelerID, String vorname, String nachname, ArrayList<String> wuensche,
			String klasse) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void exportStudent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createCompany(String firmenName, int firmenID, int maxTeilnehmer, ArrayList<Integer> zeitslots,
			double gewichtung, boolean aktiv) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void exportCompany() {
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
		return ImportFile.getChoices(absolutePath);
	}

	@Override
	public List<Unternehmen> importCompany() {
		// TODO Auto-generated method stub
		return null;
	}

}
