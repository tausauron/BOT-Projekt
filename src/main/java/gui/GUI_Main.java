package gui;
import java.util.ArrayList;

import klassenObjekte.*;

public class GUI_Main {

	public GUI_Main() {
		ArrayList<schueler> personen = new ArrayList<>();
		ArrayList<unternehmen> un = new ArrayList<>();
		ArrayList<Raum> raum = new ArrayList<>();
		GUI_ListView x = new GUI_ListView(personen,un,raum);
	}

	public static void main(String[] args) {
		
		new GUI_Main();
	}

}
