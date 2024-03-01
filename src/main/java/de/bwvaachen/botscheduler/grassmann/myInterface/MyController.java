package de.bwvaachen.botscheduler.grassmann.myInterface;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.bwvaachen.botscheduler.model.Model;
import gui.GUI_ListView;
import gui.GUI_Login;
import gui.MyJFileChooser;
import klassenObjekte.*;

/**
 * 
 * @author Grassmann
 *
 */

public class MyController {

	private GUI_Login loginGUI;
	private GUI_ListView listView;
	private ModelInterface myModal;
	private boolean activateLogin = false;

	public MyController(boolean activateLogin) {
		this.activateLogin = activateLogin;
		this.myModal = new Model();
		start();
	}

	private void start() {
		if (this.activateLogin) {
			loginGUI = new GUI_Login(this);
		} else {
			this.listView = new GUI_ListView(this);
		}
	}
	
	public void closeListView(List<Schueler> students, List<Raum> rooms, List<Unternehmen> companies) {
		this.saveAllStudents(students);
		this.saveAllRooms(rooms);
		this.saveAllCompanies(companies);
	}

	public void checkLogin(String username, String pwd) {
		if (myModal.checkLogin(username, pwd)) {
			loginGUI.close();
			this.listView = new GUI_ListView(this);
		} else {
			loginGUI.setlblErrorMessage("Falsche Logindaten!");
		}
	}

	// TODO: get data from backend/DB
	// Student functions
	public List<Schueler> getAllStudents() {
		return myModal.getAllStudents();
	}
	
	public void saveAllStudents(List<Schueler> Students) {
		myModal.saveAllStudents(Students);
	}
	
	public void createStudent() {
		System.out.println("createStudent");
	}

	public void editStudent() {
		System.out.println("editStudent");
	}

	public void deleteStudent() {
		System.out.println("editStudent");
	}

	public List<Schueler> importStudent(JFrame frame) {
		String path = MyJFileChooser.getPath(frame);
		return myModal.importStudent(path);
	}

	public void exportStudent(List<Schueler> students, JFrame frame) {
		String path = MyJFileChooser.getPath(frame);
		myModal.exportStudent(path, students);
	}

	// Room functions
	public List<Raum> getAllRooms() {
		return myModal.getAllRooms();
	}
	
	public void saveAllRooms(List<Raum> rooms) {
		myModal.saveAllRooms(rooms);
	}
	
	public void createRoom() {
		System.out.println("createRoom");
	}

	public void editRoom() {
		System.out.println("editRoom");
	}

	public void deleteRoom() {
		System.out.println("editRoom");
	}
	
	public List<Raum> importRooms(JFrame frame) {
		String path = MyJFileChooser.getPath(frame);
		return myModal.importRooms(path);
	}
	
	public void exportRooms(List<Raum> rooms, JFrame frame) {
		String path = MyJFileChooser.getPath(frame);
		myModal.exportRooms(path, rooms);
	}
	
	// Company functions
	public List<Unternehmen> getAllCompanies() {
		return myModal.getAllCompanies();
	}
	
	public void saveAllCompanies(List<Unternehmen> companies) {
		myModal.saveAllCompanies(companies);
	}
	
	public void createCompany() {
		System.out.println("createCompany");
	}

	public void editCompany() {
		System.out.println("editCompany");
	}

	public void deleteCompany() {
		System.out.println("editCompany");
	}

	public List<Unternehmen> importCompany(JFrame frame) {
		String path = MyJFileChooser.getPath(frame);
		return myModal.importCompany(path);
	}

	public void exportCompany(List<Unternehmen> companies, JFrame frame) {
		String path = MyJFileChooser.getPath(frame);
		myModal.exportCompany(path, companies);
	}

	private static void handleEcxeption(Throwable e) {
		JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), e.getClass().toString(), JOptionPane.ERROR_MESSAGE);
	}
}