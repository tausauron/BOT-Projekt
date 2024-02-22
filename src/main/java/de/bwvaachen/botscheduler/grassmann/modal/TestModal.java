package de.bwvaachen.botscheduler.grassmann.modal;

import java.util.ArrayList;
import java.util.List;

import de.bwvaachen.botscheduler.grassmann.myInterface.ModelInterface;
import de.bwvaachen.botscheduler.grassmann.myInterface.MyController;
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
	public Schueler getStudent() {
		// TODO Auto-generated method stub
		return null;
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
	public void exportStudent() {
		// TODO Auto-generated method stub
		
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
	public List<Unternehmen> importCompany() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exportCompany() {
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

}