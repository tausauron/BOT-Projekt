package gui;

import java.util.ArrayList;

import klassenObjekte.schueler;

public class GUI_Main {

	public GUI_Main() {
		ArrayList<schueler> personen = new ArrayList<>();
		
		
		ArrayList<schueler> un = new ArrayList<>();
		
		GUI_ListView x = new GUI_ListView(personen,un);
	}

	public static void main(String[] args) {
		new GUI_Main();
	}

}
