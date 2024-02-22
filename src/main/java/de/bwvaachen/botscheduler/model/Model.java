package de.bwvaachen.botscheduler.model;

import java.util.ArrayList;
import java.util.List;
import klassenObjekte.*;
import de.bwvaachen.botscheduler.grassmann.myInterface.ModelInterface;

/**
 * Zentrale Modellklasse zur Verwaltung aller Daten
 * 
 * @author Max Tautenhahn
 */
public class Model implements ModelInterface{
	
	List<schueler> schueler = new ArrayList<>();
	List<kurse> kurse = new ArrayList<>();
	List<unternehmen> unternehmen = new ArrayList<>();

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
	public klassenObjekte.schueler getStudent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createStudent(int schuelerID, String vorname, String nachname, ArrayList<String> wuensche,
			String klasse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editStudent(klassenObjekte.schueler schueler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStudent(klassenObjekte.schueler schueler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<klassenObjekte.schueler> importStudent() {
		// TODO Auto-generated method stub
		return null;
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
	public void editCompany(klassenObjekte.unternehmen unternehmen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCompany(klassenObjekte.unternehmen unternehmen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<klassenObjekte.unternehmen> importCompany() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exportCompany() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<klassenObjekte.schueler> importStudent(String absolutePath) {
		// TODO Auto-generated method stub
		return null;
	}

}
