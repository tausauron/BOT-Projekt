package de.bwvaachen.botscheduler.calculate;

public class WunschSlot extends Zeitslot{
	
	private Status status;
	
	public WunschSlot(Typ typ, Status status) {
		super(typ);
		this.status = status;
	}
	
	public enum Status {
		GEBLOCKT, FREI, BELEGT;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

}
