package de.bwvaachen.botscheduler.grassmann.myInterface;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.bwvaachen.botscheduler.grassmann.gui.MyJFileChooser;
import de.bwvaachen.botscheduler.grassmann.modal.TestModal;
import gui.GUI_ListView;
import gui.GUI_Login;
import klassenObjekte.*;

/**
 * 
 * @author Grassmann
 *
 */

public class MyController {

	private GUI_Login loginGUI;
	private GUI_ListView mainGUI;
	private TestModal myModal;
	
	public MyController() {
		this.loginGUI = new GUI_Login(this);
		this.myModal = new TestModal(this);
	}
	
	public void checkLogin(String username, String pwd) {
		if (myModal.checkLogin(username, pwd)) {
			loginGUI.close();
			this.mainGUI = new GUI_ListView(this);
		} else {
			loginGUI.setlblErrorMessage("Falsche Logindaten!");
		}
	}
	
	// TODO: get data from backend/DB
	public String getStudentData() {
		return "";
	}
	
	// Student functions
	public void createStudent() {
		System.out.println("createStudent");
	}
	
	public void editStudent() {
		System.out.println("editStudent");
	}
	
	public void deleteStudent() {
		System.out.println("editStudent");
	}
	
	public List<Schueler> importStudent() {
		System.out.println("importStudent");
		System.out.println(MyJFileChooser.getPath(mainGUI.getFrame()));
		
		// Modal:
		ArrayList<String> wahlFacher = new ArrayList<String>();
		wahlFacher.add("1");
		wahlFacher.add("2");
		wahlFacher.add("3");
		wahlFacher.add("4");
		wahlFacher.add("5");
		wahlFacher.add("6");

		Schueler newSchüler = new Schueler("Klasse", "Vor", "Nach", wahlFacher);
		
		ArrayList<Schueler> students = new ArrayList<Schueler>();
		students.add(newSchüler);
		students.add(newSchüler);
		students.add(newSchüler);
		students.add(newSchüler);
		students.add(newSchüler);
		
		
		return students;
	}
	
	public void exportStudent() {
		System.out.println("exportStudent");
	}
	
	// Class functions
	public void createClass() {
		System.out.println("createClass");
	}
	
	public void editClass() {
		System.out.println("editClass");
	}
	
	public void deleteClass() {
		System.out.println("editClass");
	}
	
	// Company functions
	public void createCompany() {
		System.out.println("createCompany");
	}
	
	public void editCompany() {
		System.out.println("editCompany");
	}
	
	public void deleteCompany() {
		System.out.println("editCompany");
	}
	
	public List<Unternehmen> importCompany() {
		System.out.println("importCompany");
		return null;
	}
	
	public void exportCompany() {
		System.out.println("exportCompany");
	}
	
	private static void handleEcxeption(Throwable e) {
		JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), e.getClass().toString(), JOptionPane.ERROR_MESSAGE);
	}
}