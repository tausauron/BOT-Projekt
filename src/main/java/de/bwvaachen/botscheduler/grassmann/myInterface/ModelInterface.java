package de.bwvaachen.botscheduler.grassmann.myInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
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
	/**
	 * gibt eine Liste von Schuelern aus dem Input zurueck
	 * 
	 * @return Schuelerliste
	 */
	public List<Schueler> getAllStudents();
	
	/**
	 * speichert eine Schuelerliste im Input und in der Datenbank
	 * 
	 * @param students
	 * @throws Exception
	 */
	public void saveAllStudents(List<Schueler> students) throws Exception;
	
	/**
	 * loescht alle Schueler aus dem Input und der Datenbank
	 * 
	 * @throws Exception
	 */
	public void deleteAllStudent()  throws Exception;
	
	/**
	 * importiert eine Schuelerliste aus einer Excel-Datei
	 * 
	 * @param absolutePath Quellpfad
	 * @return importierte Schuelerliste
	 * @throws Exception
	 */
	public List<Schueler> importStudent(String absolutePath) throws Exception;
	
	/**
	 * exportiert eine Liste mit Schuelern aus dem Input in eine Excel-Datei
	 * 
	 * @param path Zielpfad
	 * @param students zu exportierende Schuelerliste
	 */
	public void exportStudent(String path, List<Schueler> students); // download Excel
	
	// Room
	/**
	 * gibt die Raumliste aud dem Input zurueck
	 * 
	 * @return Raumliste
	 */
	public List<Raum> getAllRooms();
	
	/**
	 * speichert die uebergebene Liste von Raeumen im Input und in der Datenbank
	 * 
	 * @param rooms zu speichernde Raumliste
	 * @throws Exception
	 */
	public void saveAllRooms(List<Raum> rooms) throws Exception;
	
	/**
	 * loescht alle Raeume des Inputs aus der Datenbank
	 * 
	 * @throws Exception
	 */
	public void deleteAllRooms() throws Exception;
	
	/**
	 * importiert eine Liste von Raeumen aus einer Excel-Datei in den Input
	 * 
	 * @param path Quellpfad
	 * @return Liste von importierten Raeumen
	 * @throws Exception
	 */
	public List<Raum> importRooms(String path) throws Exception;
	
	/**
	 * exportiert eine Liste von Raeumen aus dem Input in eine Excel-Datei
	 * 
	 * @param path Zielpfad
	 * @param rooms Raumliste
	 */
	public void exportRooms(String path, List<Raum> rooms);
	
	// Company
	/**
	 * gibt eine Liste aller Veranstaltungstypen aus dem Input zurueck
	 * 
	 * @return Liste von Veranstaltungstypen
	 */
	public List<Unternehmen> getAllCompanies();
	
	/**
	 * speichert alle Veranstaltungstypen in der Datenbank
	 * 
	 * @param companies Liste von Veranstaltungstypen
	 * @throws Exception
	 */
	public void saveAllCompanies(List<Unternehmen> companies) throws Exception;
	
	/**
	 * loescht alle Veranstaltungstypen aus dem Input
	 * 
	 * @throws Exception
	 */
	public void deleteAllCompanies() throws Exception;
	
	/**
	 * importiert eine Veranstaltungsliste vom uebergebenen Pfad
	 * @param path Zielpfad
	 * @return Liste von Veranstaltungstypen aus dem Import
	 */
	public List<Unternehmen> importCompany(String absolutePath) throws Exception;
	
	/**
	 * exportiert die Veranstaltungstypenliste aus dem Import in eine Excel-Datei
	 * 
	 * @param path Zielpfad
	 * @param companies zu exportierende Liste
	 */
	public void exportCompany(String path, List<Unternehmen> companies); // download Excel
	
	//Scheduling Algorithm
	/**
	 * loest die Belegungsalgorithmen fuer Kursbelegung und Raumbelegung mit den verfuegbaren Daten aus.
	 * 
	 * @return Erfolggscore in Prozent
	 * @throws IllegalStateException wenn benoetigte Daten fehlen.
	 */
	public String belegeKurse() throws IllegalStateException;
	
	//Export
	/**
	 * loest den Export aller Excel-Reports in den uebergebenen Pfad aus
	 * 
	 * @param path Zielpfad fuer die Reports
	 * @throws FileNotFoundException wenn der Pfad nicht existiert
	 * @throws IOException
	 */
	public void exportLoesung(String path) throws FileNotFoundException, IOException;

}