package de.bwvaachen.botscheduler.grassmann.myInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.bwvaachen.botscheduler.model.Model;
import gui.GUI_ListView;
import gui.GUI_Login;
import gui.GUI_Main_Start;
import gui.MyJFileChooser;
import klassenObjekte.*;

/**
 * @author Grassmann
 */

public class MyController {

	private GUI_Login loginGUI;
	private GUI_ListView listView;
	private GUI_Main_Start mainStart;
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

			startMainGUI();
		}
	}

	public void startListView() {
		if (datenBankDatenExist()) {
			this.listView=new GUI_ListView(this, getAllStudents(), getAllCompanies(), getAllRooms());
		} else {
			this.listView = new GUI_ListView(this);
		}
	}

	public void startMainGUI() {
		if (datenBankDatenExist()) {
			this.mainStart = new GUI_Main_Start(this, getAllStudents(), getAllCompanies(), getAllRooms());
		} else {
			this.mainStart = new GUI_Main_Start(this);
		}

	}

	private boolean datenBankDatenExist() {
		// TODO Auto-generated method stub
		return true;
	}

	public void closeListView(List<Schueler> students, List<Raum> rooms, List<Unternehmen> companies) {
		this.saveAllStudents(students);
		this.saveAllRooms(rooms);
		this.saveAllCompanies(companies);
	}

	public void checkLogin(String username, String pwd) {
		if (myModal.checkLogin(username, pwd)) {
			loginGUI.close();
			startListView();
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
		try {
			String path = MyJFileChooser.getPath(frame);
			if (!path.equals("")) {
				return myModal.importStudent(path);
			}
		} catch (Exception e) {
			handleEcxeption(e);
		}
		// return empty list
		return new ArrayList<Schueler>();
	}

	public void exportStudent(List<Schueler> students, JFrame frame) {
		try {

			String path = MyJFileChooser.getPath(frame);
			if (!path.equals("")) {
				myModal.exportStudent(path, students);
			}
		} catch (Exception e) {
			handleEcxeption(e);
		}
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
		try {
			String path = MyJFileChooser.getPath(frame);
			if (!path.equals("")) {
				return myModal.importRooms(path);
			}
		} catch (Exception e) {
			handleEcxeption(e);
		}
		// return empty list
		return new ArrayList<Raum>();
	}

	public void exportRooms(List<Raum> rooms, JFrame frame) {
		try {
			String path = MyJFileChooser.getPath(frame);
			if (!path.equals("")) {
				myModal.exportRooms(path, rooms);
			}
		} catch (FileNotFoundException e) {
			handleEcxeption(e);
		}
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
		try {
			String path = MyJFileChooser.getPath(frame);
			if (!path.equals("")) {
				return myModal.importCompany(path);
			}
		} catch (Exception e) {
			handleEcxeption(e);
		}
		// return empty list
		return new ArrayList<Unternehmen>();
	}

	public void exportCompany(List<Unternehmen> companies, JFrame frame) {
		try {
			String path = MyJFileChooser.getPath(frame);
			if (!path.equals("")) {
				myModal.exportCompany(path, companies);
			}
		} catch (Exception e) {
			handleEcxeption(e);
		}
	}

	public void exportStudentSchedule(JFrame frame) {
		try {
			String path = MyJFileChooser.getPath(frame, "Laufzettel.xlsx");
			if (!path.equals("")) {
				myModal.exportSchuelerSchedule(path);
			}
		} catch (IOException e) {
			handleEcxeption(e);
		}
	}

	private static void handleEcxeption(Throwable e) {
		JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), e.getClass().toString(), JOptionPane.ERROR_MESSAGE);
	}
}