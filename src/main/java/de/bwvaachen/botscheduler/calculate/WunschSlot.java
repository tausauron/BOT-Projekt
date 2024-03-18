package de.bwvaachen.botscheduler.calculate;

/**
 * Erweiterung von Zeitslot um Wunschspezifische Daten
 * 
 * @author Max Tautenhahn
 */
public class WunschSlot extends Zeitslot{
	
	private Status status;
	
	public WunschSlot(Typ typ, Status status) {
		super(typ);
		this.status = status;
	}
	
	/**
	 * Enum zur Definition gueltiger Zustände eines Wunsch-Zeitslots
	 */
	public enum Status {
		GEBLOCKT, FREI, BELEGT;
	}
	
	
	//Getter Setter
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

}
