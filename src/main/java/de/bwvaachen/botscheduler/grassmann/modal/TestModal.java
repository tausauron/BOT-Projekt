package de.bwvaachen.botscheduler.grassmann.modal;

import de.bwvaachen.botscheduler.grassmann.myInterface.MyController;

public class TestModal {

	final private String key = ":LjY%511('NEuOjN#:ca6f(<(5C}s|*$";
	final private String username = "ZZZoY280jtXErmFp8FelzQ==";
	final private String password = "pzB75ocJmB/BwEQuxx3Q7A==";
	
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
}