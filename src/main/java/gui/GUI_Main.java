package gui;
import java.util.ArrayList;

import klassenObjekte.*;

import klassenObjekte.Schueler;


public class GUI_Main {

	public GUI_Main() {

		ArrayList<Schueler> personen = new ArrayList<>();
		ArrayList<Unternehmen> un = new ArrayList<>();
		ArrayList<Raum> raum = new ArrayList<>();
		GUI_ListView x = new GUI_ListView(personen,un,raum);
		
	}

	public static void main(String[] args) {
		new GUI_Main();
	}

}
