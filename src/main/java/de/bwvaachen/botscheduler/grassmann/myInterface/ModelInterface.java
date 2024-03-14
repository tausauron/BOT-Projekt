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
	public void deleteAllStudent()  throws Exception;
	public List<Schueler> importStudent(String absolutePath) throws Exception;
	public void exportStudent(String path, List<Schueler> students); // download Excel
	
	// Room
	public List<Raum> getAllRooms();
	public void saveAllRooms(List<Raum> rooms) throws Exception;
	public void deleteAllRooms() throws Exception;
	public List<Raum> importRooms(String path) throws Exception;
	public void exportRooms(String path, List<Raum> rooms);
	
	// Company
	public List<Unternehmen> getAllCompanies();
	public void saveAllCompanies(List<Unternehmen> companies) throws Exception;
	public void deleteAllCompanies() throws Exception;
	public List<Unternehmen> importCompany(String absolutePath) throws Exception;
	public void exportCompany(String path, List<Unternehmen> companies); // download Excel
	
	//Scheduling Algorithm
	public String belegeKurse() throws IllegalStateException;
	
	//Export
	public void exportSchuelerSchedule(String path) throws FileNotFoundException, IOException;
	

	

}