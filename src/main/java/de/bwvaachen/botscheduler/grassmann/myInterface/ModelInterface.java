package de.bwvaachen.botscheduler.grassmann.myInterface;

import java.util.ArrayList;
import java.util.List;

import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

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
	
	// Student
	public List<Schueler> getAllStudents();
	public void saveAllStudents(List<Schueler> students);

    Schueler getStudent();

    public void createStudent(int schuelerID, String vorname, String nachname, ArrayList<String> wuensche, String klasse);
	public void editStudent(Schueler schueler);
	public void deleteStudent(Schueler schueler);

	void exportCompany();

	public List<Schueler> importStudent(String absolutePath);
	public void exportStudent(String path, List<Schueler> students); // download Excel
	
	// Room
	public List<Raum> getAllRooms();
	public void saveAllRooms(List<Raum> rooms);
	public void createRoom(String name);
	public void editRoom(Raum room);
	public void deleteRoom(Raum room);
	public List<Raum> importRooms(String path);
	public void exportRooms(String path, List<Raum> rooms);
	
	// Company
	public List<Unternehmen> getAllCompanies();
	public void saveAllCompanies(List<Unternehmen> companies);

	void exportStudent();

	public void createCompany(String firmenName, int firmenID, int maxTeilnehmer, ArrayList<Integer> zeitslots, double gewichtung, boolean aktiv);
	public void editCompany(Unternehmen unternehmen);
	public void deleteCompany(Unternehmen unternehmen);
	public List<Unternehmen> importCompany(String absolutePath);
	public void exportCompany(String path, List<Unternehmen> companies); // download Excel

	List<Unternehmen> importCompany();
}