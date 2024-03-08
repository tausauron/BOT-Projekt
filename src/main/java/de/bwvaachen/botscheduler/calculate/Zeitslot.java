package de.bwvaachen.botscheduler.calculate;

import klassenObjekte.Kurse;

/**
 * Beschreibung von Zeitslots für den Belegungsalgorithmus
 * 
 * @author Max Tautenhahn
 */
public class Zeitslot {
	
	private final Typ typ;
	private Kurse kurs;
	
	
	public Zeitslot(Typ typ) {
		this.typ = typ;
	}
	
	
	public enum Typ {
		A("8:45 – 9:30"),
		B("9:50 – 10:35"),
		C("10:35 – 11:20"),
		D("11:40– 12:25"),
		E("12:25 – 13:10");
		
		private String zeitraum;
		
		Typ(String zeitraum) {
			this.zeitraum = zeitraum;
		}
		
		public String getZeitraum() {
			return zeitraum;
		}
	}
	
	
	public static boolean istGleichOderSpaeter(Typ typ, Typ vergleich) {
		
		boolean retVal = false;
		
		if(typ.compareTo(vergleich) >= 0) {
			retVal=true;
		}
		
		return retVal;

	}


	public Kurse getKurs() {
		return kurs;
	}


	public void setKurs(Kurse kurs) {
		this.kurs = kurs;
	}


	public Typ getTyp() {
		return typ;
	}
	

}
