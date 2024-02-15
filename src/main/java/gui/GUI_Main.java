package gui;

import java.util.ArrayList;

public class GUI_Main {

	public GUI_Main() {
		ArrayList<Schüler> personen = new ArrayList<>();
		personen.add(new Schüler("Klasse", "Max","Muster", "1", "2"));
		
		
		ArrayList<Schüler> un = new ArrayList<>();
		un.add(new Schüler("kl", "un","ter", "1", "2"));
		
		GUI_ListView x = new GUI_ListView(personen,un);
	}

	public static void main(String[] args) {
		
		new GUI_Main();
	}

}
