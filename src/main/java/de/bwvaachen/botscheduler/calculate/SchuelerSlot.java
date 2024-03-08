package de.bwvaachen.botscheduler.calculate;

/**
 * Erweiterung von Zeitslot um schülerspezifische Daten
 * 
 * @author Max Tautenhahn
 */

public class SchuelerSlot extends Zeitslot{
	
	private Wunsch erfuellterWunsch;
	
	public SchuelerSlot(Typ typ) {
		super(typ);
	}
	
	public void setErfuellterWunsch(Wunsch erfuellterWunsch) {
		this.erfuellterWunsch = erfuellterWunsch;
	}
	
	public Wunsch getErfuellterWunsch() {
		return erfuellterWunsch;
	}


}
