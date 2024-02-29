package de.bwvaachen.botscheduler.calculate;

import klassenObjekte.Kurse;

public class Zeitslot {
	
	private final Typ typ;
	private Kurse kurs;
	
	
	public Zeitslot(Typ typ) {
		this.typ = typ;
	}
	
	
	public enum Typ {
		A, B, C, D, E
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
