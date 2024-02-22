package gui;

import java.util.ArrayList;

import klassenObjekte.Schueler;

public class GUI_Main {

	public GUI_Main() {
		ArrayList<Schueler> personen = new ArrayList<>();
		
		
		ArrayList<Schueler> un = new ArrayList<>();
		
		GUI_ListView x = new GUI_ListView(personen,un);
	}

	public static void main(String[] args) {
		new GUI_Main();
	}

}
