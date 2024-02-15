package gui;

import java.util.ArrayList;

public class GUI_Main {

	public GUI_Main() {
		ArrayList<Person> personen = new ArrayList<>();
		personen.add(new Person("Max", "Muster"));
		personen.add(new Person("Anna", "Schmidt"));
		personen.add(new Person("Test", "123"));
		GUI_ListView x = new GUI_ListView(personen);
	}

	public static void main(String[] args) {
		
		new GUI_Main();
	}

}
