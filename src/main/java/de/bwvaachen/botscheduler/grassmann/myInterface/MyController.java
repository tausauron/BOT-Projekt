package de.bwvaachen.botscheduler.grassmann.myInterface;

import de.bwvaachen.botscheduler.grassmann.gui.GUI_Login;
import de.bwvaachen.botscheduler.grassmann.gui.TestGUI;
import de.bwvaachen.botscheduler.grassmann.modal.TestModal;

// Grassmann

public class MyController {

	private GUI_Login loginGUI;
	private TestGUI myGUI;
	private TestModal myModal;
	
	public MyController() {
		this.loginGUI = new GUI_Login(this);
		this.myModal = new TestModal(this);
	}
	
	public void checkLogin(String username, String pwd) {
		if (myModal.checkLogin(username, pwd)) {
			loginGUI.close();
			myGUI = new TestGUI(this);
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
	
	public void importStudent() {
		System.out.println("importStudent");
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
	
	public void importCompany() {
		System.out.println("importCompany");
	}
	
	public void exportCompany() {
		System.out.println("exportCompany");
	}
}