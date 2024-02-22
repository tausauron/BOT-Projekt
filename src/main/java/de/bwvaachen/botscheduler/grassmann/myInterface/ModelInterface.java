package de.bwvaachen.botscheduler.grassmann.myInterface;

import java.util.ArrayList;
import java.util.List;

import klassenObjekte.schueler;
import klassenObjekte.unternehmen;

/**
 * 
 *@author Grassmann
 */

public interface ModelInterface {
	final String key = ":LjY%511('NEuOjN#:ca6f(<(5C}s|*$";
	// master user
	final String username = "ZZZoY280jtXErmFp8FelzQ==";
	final String password = "pzB75ocJmB/BwEQuxx3Q7A==";
	
	public Boolean checkLogin(String username, String password);
	
	public schueler getStudent();
	public void createStudent(int schuelerID, String vorname, String nachname, ArrayList<String> wuensche,String klasse);
	public void editStudent(schueler schueler);
	public void deleteStudent(schueler schueler);
	public List<schueler> importStudent(String absolutePath);
	public void exportStudent(); // download Excel
	
	public void createCompany(String firmenName, int firmenID, int maxTeilnehmer, ArrayList<Integer> zeitslots, double gewichtung, boolean aktiv);
	public void editCompany(unternehmen unternehmen);
	public void deleteCompany(unternehmen unternehmen);
	
	public List<unternehmen> importCompany();
	public void exportCompany(); // download Excel
}