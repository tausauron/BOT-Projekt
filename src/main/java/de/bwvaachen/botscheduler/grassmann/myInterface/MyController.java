package de.bwvaachen.botscheduler.grassmann.myInterface;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.bwvaachen.botscheduler.model.Model;
import gui.GUI_ListView;
import gui.GUI_Login;
import gui.GUI_Main_Start;
import gui.MyJFileChooser;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

/**
 * @author Grassmann
 */

public class MyController {

	private GUI_Login loginGUI;
	private Model myModal;

	/**
	 * 
	 * @param activateLogin
	 * @throws Exception
	 */
	public MyController(boolean activateLogin) throws Exception {
		this.myModal = new Model();
		start(activateLogin);
	}

	/**
	 * Start-Funktion nach Konstruktor
	 */
	private void start(Boolean activateLogin) {
		if (activateLogin) {
			loginGUI = new GUI_Login(this);
		} else {
			startMainGUI();
		}
	}

	public void startListView() {
		new GUI_ListView(this, getAllStudents(), getAllCompanies(), getAllRooms());
	}

	/**
	 * erstellt / oeffnet die Haupt-GUI
	 */
	public void startMainGUI() {
		new GUI_Main_Start(this, getAllStudents(), getAllCompanies(), getAllRooms());
	}

	/**
	 * Beim schließen der ListView werden alle Daten gespeichert
	 * 
	 * @param students
	 * @param rooms
	 * @param companies
	 * @throws Exception
	 */
	public void closeListView(List<Schueler> students, List<Raum> rooms, List<Unternehmen> companies) throws Exception {
		this.saveAllStudents(students);
		this.saveAllRooms(rooms);
		this.saveAllCompanies(companies);
	}

	/**
	 * Prüft die login-Daten und leitet weiter bei korrekter eingabe. Bei falscher
	 * Eingabe wird der User informiert
	 * 
	 * @param username - Test
	 * @param pwd      - Password - Testen123
	 */
	public void checkLogin(String username, String pwd) {
		if (myModal.checkLogin(username, pwd)) {
			loginGUI.close();
			startMainGUI();
		} else {
			loginGUI.setlblErrorMessage("Falsche Logindaten!");
		}
	}

	/**
	 * hohlt alle Schueler aus der Datenbank und übergibt sie
	 * 
	 * @return - List von Schueler
	 */
	public List<Schueler> getAllStudents() {
		return myModal.getAllStudents();
	}

	/**
	 * Speichert alle uebergebenen Schueler in der Datenbank
	 * 
	 * @param Students
	 * @throws Exception
	 */
	public void saveAllStudents(List<Schueler> Students) throws Exception {
		myModal.saveAllStudents(Students);
	}

	/**
	 * Loescht alle Raeume aus der Datenbank
	 */
	public void deleteAllStudent() {
		try {
			myModal.deleteAllStudent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * importiert Schueler und zeigt sie in der GUI an
	 * 
	 * @param frame - frame von GUI der diese Funktion aufruft um Styleelemente dem
	 *              FileChooser mitzugeben
	 * @return List von Schueler
	 */
	public List<Schueler> importStudent(JFrame frame) {
		try {
			String path = MyJFileChooser.getPathExcelImport(frame, "BOT_Import_Schüler");
			if (!path.equals("")) {
				return myModal.importStudent(path);
			}
		} catch (Exception e) {
			handleEcxeption(e);
		}
		// return empty list
		return new ArrayList<Schueler>();
	}

	/**
	 * stoest export der Schueler an
	 * 
	 * @param students
	 * @param frame
	 */
	public void exportStudent(List<Schueler> students, JFrame frame) {
		try {

			String path = MyJFileChooser.getPathExcelExport(frame, "BOT_Export_Schüler");
			if (!path.equals("")) {
				myModal.exportStudent(path, students);
			}
		} catch (Exception e) {
			handleEcxeption(e);
		}
	}

	/**
	 * alle Raeume aus der Datenbank bekommen
	 * 
	 * @return- List von Raum
	 */
	public List<Raum> getAllRooms() {
		return myModal.getAllRooms();
	}

	/**
	 * Speichere alle uebergebene Raeume in der Datenbank
	 * 
	 * @param rooms
	 * @throws Exception
	 */
	public void saveAllRooms(List<Raum> rooms) throws Exception {
		myModal.saveAllRooms(rooms);
	}

	/**
	 * Loescht alle Raeume aus der Datenbank
	 */
	public void deleteAllRoom() {
		try {
			myModal.deleteAllRooms();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			handleEcxeption(e);
		}
	}

	/**
	 * importiert Raeume und zeigt sie in der GUI an
	 * 
	 * @param frame - frame von GUI der diese Funktion aufruft um Styleelemente dem
	 *              FileChooser mitzugeben
	 * @return List von Raum
	 */
	public List<Raum> importRooms(JFrame frame) {
		try {
			String path = MyJFileChooser.getPathExcelImport(frame, "BOT_Import_Räume");
			if (!path.equals("")) {
				return myModal.importRooms(path);
			}
		} catch (Exception e) {
			handleEcxeption(e);
		}
		// return empty list
		return new ArrayList<Raum>();
	}

	/**
	 * stoest export der Raeume an
	 * 
	 * @param rooms - zu exportierende Raeume
	 * @param frame - frame von GUI der diese Funktion aufruft um Styleelemente dem
	 *              FileChooser mitzugeben
	 */
	public void exportRooms(List<Raum> rooms, JFrame frame) {
		try {
			String path = MyJFileChooser.getPathExcelExport(frame, "BOT_Export_Räume");
			if (!path.equals("")) {
				myModal.exportRooms(path, rooms);
			}
		} catch (FileNotFoundException e) {
			handleEcxeption(e);
		}
	}

	/**
	 * Holt sich alle Unternehmen aus der Datenbank
	 * 
	 * @return List von Unternehmen
	 */
	public List<Unternehmen> getAllCompanies() {
		return myModal.getAllCompanies();
	}

	/**
	 * Speicher die übergebenen Unternehmen in der Datenbank
	 * 
	 * @param companies
	 * @throws Exception
	 */
	public void saveAllCompanies(List<Unternehmen> companies) throws Exception {
		myModal.saveAllCompanies(companies);
	}

	/**
	 * Lösche alle Unternehmen in der Datenbank
	 */
	public void deleteAllCompany() {
		try {
			myModal.deleteAllCompanies();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * importiert Unternehmen und zeigt sie auf der GUI an
	 * 
	 * @param frame - frame von GUI der diese Funktion aufruft um Styleelemente dem
	 *              FileChooser mitzugeben
	 * @return List von Unternehmen
	 */
	public List<Unternehmen> importCompany(JFrame frame) {
		try {
			String path = MyJFileChooser.getPathExcelImport(frame, "BOT_Import_Unternehmen");
			if (!path.equals("")) {
				return myModal.importCompany(path);
			}
		} catch (Exception e) {
			handleEcxeption(e);
		}
		// return empty list
		return new ArrayList<Unternehmen>();
	}

	/**
	 * stoest export der Unternehmen an
	 * 
	 * @param companies - zu exportierende Unternehmen
	 * @param frame     - frame von GUI der diese Funktion aufruft um Styleelemente
	 *                  dem FileChooser mitzugeben
	 */
	public void exportCompany(List<Unternehmen> companies, JFrame frame) {
		try {
			String path = MyJFileChooser.getPathExcelExport(frame, "BOT_Export_Unternehmen");
			if (!path.equals("")) {
				myModal.exportCompany(path, companies);
			}
		} catch (Exception e) {
			handleEcxeption(e);
		}
	}

	/**
	 * Gibt Zielpfad an Backend weiter und stoest die Berechnung an
	 * 
	 * @param Zielpfad
	 */
	public void generiereLoesungen(String path) {

		try {
			String score = myModal.belegeKurse();
			myModal.exportLoesung(path);
			JOptionPane.showMessageDialog(null, "Der Erreichte Punkte Wert ist: " + score, "Punkte Ergebnis:",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			handleEcxeption(e);
		}
	}

	/**
	 * Gibt die Fehlermeldung in einem Popup aus
	 * 
	 * @param exception
	 */
	private static void handleEcxeption(Throwable e) {
		JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), e.getClass().toString(), JOptionPane.ERROR_MESSAGE);
	}
}