package de.bwvaachen.botscheduler.model;

import java.util.List;

import klassenObjekte.Raum;
import klassenObjekte.Schueler;

/**
 * Data Access Object zum Speichern der Kurse in der Datenbank
 * 
 * @author Max Tautenhahn
 */
public class KursDAO {
	private int ID;



	private Raum raum;
	private List<Schueler> kursTeilnehmer;
	private UnternehmenDAO unternehmen;
	private String zeitslot;
	
	public KursDAO(Raum raum, List<Schueler> kursTeilnehmer, UnternehmenDAO unternehmen, String zeitslot){
		this.raum = raum;
		this.kursTeilnehmer = kursTeilnehmer;
		this.unternehmen = unternehmen;
		this.zeitslot = zeitslot;
	}

	public Raum getRaum() {
		return raum;
	}

	public void setRaum(Raum raum) {
		this.raum = raum;
	}

	public List<Schueler> getKursTeilnehmer() {
		return kursTeilnehmer;
	}

	public void setKursTeilnehmer(List<Schueler> kursTeilnehmer) {
		this.kursTeilnehmer = kursTeilnehmer;
	}

	public UnternehmenDAO getUnternehmen() {
		return unternehmen;
	}

	public void setUnternehmen(UnternehmenDAO unternehmen) {
		this.unternehmen = unternehmen;
	}

	public String getZeitslot() {
		return zeitslot;
	}

	public void setZeitslot(String zeitslot) {
		this.zeitslot = zeitslot;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}
	
	

}
