package de.bwvaachen.botscheduler.grassmann.modal;

import java.util.ArrayList;
import java.util.List;

import de.bwvaachen.botscheduler.grassmann.myInterface.ModelInterface;
import de.bwvaachen.botscheduler.grassmann.myInterface.MyController;
import klassenObjekte.Raum;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

// Grassmann

public class TestModal implements ModelInterface{
	
	public TestModal(MyController myController) {
	}
	
	public Boolean checkLogin(String username, String password) {
		try {
			String decryptUsername = StringEncryption.decrypt(ModelInterface.username, ModelInterface.key);
			String decryptPassword = StringEncryption.decrypt(ModelInterface.password, ModelInterface.key);
			
			if (decryptUsername.equals(username) && decryptPassword.equals(password)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void createStudent(int schuelerID, String vorname, String nachname, ArrayList<String> wuensche,
			String klasse) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteStudent(Schueler schueler) {
		// TODO Auto-generated method stub
		
	}

	public List<Schueler> importStudent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createCompany(String firmenName, int firmenID, int maxTeilnehmer, ArrayList<Integer> zeitslots,
			double gewichtung, boolean aktiv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editCompany(Unternehmen unternehmen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCompany(Unternehmen unternehmen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Schueler> importStudent(String absolutePath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editStudent(Schueler schueler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Schueler> getAllStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAllStudents(List<Schueler> students) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportStudent(String path, List<Schueler> students) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Raum> getAllRooms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAllRooms(List<Raum> rooms) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createRoom(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editRoom(Raum room) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRoom(Raum room) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Raum> importRooms(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exportRooms(String path, List<Raum> rooms) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Unternehmen> getAllCompanies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAllCompanies(List<Unternehmen> companies) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Unternehmen> importCompany(String absolutePath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exportCompany(String path, List<Unternehmen> companies) {
		// TODO Auto-generated method stub
		
	}

}