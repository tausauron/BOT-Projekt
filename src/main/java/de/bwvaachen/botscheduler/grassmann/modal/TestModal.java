package de.bwvaachen.botscheduler.grassmann.modal;

import java.util.ArrayList;
import java.util.List;

import de.bwvaachen.botscheduler.grassmann.myInterface.ModelInterface;
import de.bwvaachen.botscheduler.grassmann.myInterface.MyController;
import klassenObjekte.schueler;
import klassenObjekte.unternehmen;

// Grassmann

public class TestModal implements ModelInterface{
	
	public TestModal(MyController myController) {
	}
	
	public Boolean checkLogin(String username, String password) {
		try {
			String decryptUsername = StringEncryption.decrypt(this.username, this.key);
			String decryptPassword = StringEncryption.decrypt(this.password, this.key);
			
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
	public schueler getStudent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createStudent(int schuelerID, String vorname, String nachname, ArrayList<String> wuensche,
			String klasse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editStudent(schueler schueler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStudent(schueler schueler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<schueler> importStudent() {
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
	public void editCompany(unternehmen unternehmen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCompany(unternehmen unternehmen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<unternehmen> importCompany() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exportCompany() {
		// TODO Auto-generated method stub
		
	}
}