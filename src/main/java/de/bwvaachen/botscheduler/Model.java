package de.bwvaachen.botscheduler;

import java.util.ArrayList;
import java.util.List;
import klassenObjekte.*;
import de.bwvaachen.botscheduler.grassmann.myInterface.ModelInterface;

/**
 * Zentrale Modellklasse zur Verwaltung aller Daten
 * 
 * @author Max Tautenhahn
 */
public class Model implements ModelInterface{
	
	List<schueler> schueler = new ArrayList<>();
	List<kurse> kurse = new ArrayList<>();
	List<unternehmen> unternehmen = new ArrayList<>();

	@Override
	public Boolean checkLogin(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void belegeKurse() {
		KursPlaner planer = new KursPlaner();
		kurse = planer.belegeKurse(schueler, unternehmen);
	}

}
