package de.bwvaachen.botscheduler.grassmann.myInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
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
	public void saveAllStudents(List<Schueler> students) throws Exception;
	public void createStudent(int schuelerID, String vorname, String nachname, ArrayList<String> wuensche,String klasse);
	public void editStudent(Schueler schueler) throws Exception;
	public void deleteStudent(Schueler schueler);
	public List<Schueler> importStudent(String absolutePath) throws Exception;
	public void exportStudent(String path, List<Schueler> students); // download Excel
	
	// Room
	public List<Raum> getAllRooms();
	public void saveAllRooms(List<Raum> rooms) throws Exception;
	public void createRoom(String name) throws Exception;
	public void editRoom(Raum room);
	public void deleteRoom(Raum room) throws Exception;
	public List<Raum> importRooms(String path) throws Exception;
	public void exportRooms(String path, List<Raum> rooms);
	
	// Company
	public List<Unternehmen> getAllCompanies();
	public void saveAllCompanies(List<Unternehmen> companies) throws Exception;
	public void createCompany(String firmenName, int firmenID, int maxTeilnehmer, ArrayList<Integer> zeitslots, double gewichtung, boolean aktiv);
	public void editCompany(Unternehmen unternehmen) throws Exception;
	public void deleteCompany(Unternehmen unternehmen) throws Exception;
	public List<Unternehmen> importCompany(String absolutePath) throws Exception;
	public void exportCompany(String path, List<Unternehmen> companies); // download Excel
	
	//Scheduling Algorithm
	public String belegeKurse() throws IllegalStateException;
	
	//Export
	public void exportSchuelerSchedule(String path) throws FileNotFoundException, IOException;
	

	

}