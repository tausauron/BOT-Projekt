package gui;
import java.util.ArrayList;

<<<<<<< HEAD
import klassenObjekte.*;
=======
import klassenObjekte.Schueler;
>>>>>>> refs/heads/sprint1

public class GUI_Main {

	public GUI_Main() {
<<<<<<< HEAD
		ArrayList<schueler> personen = new ArrayList<>();
		ArrayList<unternehmen> un = new ArrayList<>();
		ArrayList<Raum> raum = new ArrayList<>();
		GUI_ListView x = new GUI_ListView(personen,un,raum);
=======
		ArrayList<Schueler> personen = new ArrayList<>();
		
		
		ArrayList<Schueler> un = new ArrayList<>();
		
		GUI_ListView x = new GUI_ListView(personen,un);
>>>>>>> refs/heads/sprint1
	}

	public static void main(String[] args) {
		new GUI_Main();
	}

}
