package de.bwvaachen.botscheduler.model;

/**
 * Data Access Object zum Speichern der Unternehmen in der Datenbank
 * 
 * @author Max Tautenhahn
 */
public class UnternehmenDAO {
	
	private int firmenID; // ist die Firmen Nr
	private String unternehmen; 
	private String fachrichtung;
	private int maxTeilnehmer, maxVeranstaltungen;
	private String fruehsterZeitslot;
	private double gewichtung;
	private boolean aktiv;
	
	public UnternehmenDAO(int firmenID,String unternehmen, String fachrichtung, 
			int maxTeilnehmer, int maxVeranstaltungen, String fruehsterZeitslot)
	{
		this.firmenID = firmenID;
		this.unternehmen = unternehmen;
		this.fachrichtung = fachrichtung;
		this.maxTeilnehmer = maxTeilnehmer;
		this.maxVeranstaltungen = maxVeranstaltungen;
		this.fruehsterZeitslot = fruehsterZeitslot;
	}

	public int getFirmenID() {
		return firmenID;
	}

	public void setFirmenID(int firmenID) {
		this.firmenID = firmenID;
	}

	public String getUnternehmen() {
		return unternehmen;
	}

	public void setUnternehmen(String unternehmen) {
		this.unternehmen = unternehmen;
	}

	public String getFachrichtung() {
		return fachrichtung;
	}

	public void setFachrichtung(String fachrichtung) {
		this.fachrichtung = fachrichtung;
	}

	public int getMaxTeilnehmer() {
		return maxTeilnehmer;
	}

	public void setMaxTeilnehmer(int maxTeilnehmer) {
		this.maxTeilnehmer = maxTeilnehmer;
	}

	public int getMaxVeranstaltungen() {
		return maxVeranstaltungen;
	}

	public void setMaxVeranstaltungen(int maxVeranstaltungen) {
		this.maxVeranstaltungen = maxVeranstaltungen;
	}

	public String getFruehesterZeitslot() {
		return fruehsterZeitslot;
	}

	public void setFruehsterZeitslot(String fruehsterZeitslot) {
		this.fruehsterZeitslot = fruehsterZeitslot;
	}

	public double getGewichtung() {
		return gewichtung;
	}

	public void setGewichtung(double gewichtung) {
		this.gewichtung = gewichtung;
	}

	public boolean isAktiv() {
		return aktiv;
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}
	
	


}
