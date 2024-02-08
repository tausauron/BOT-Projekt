package de.bwvaachen.botscheduler.testInterface;

// Grassmann

public class MyController {

	private TestGUI myGUI;
	
	public MyController() {
		this.myGUI = new TestGUI(this);
	}
	
	public void checkLogin(String username, String pwd) {
		System.out.println("checkLogin");
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