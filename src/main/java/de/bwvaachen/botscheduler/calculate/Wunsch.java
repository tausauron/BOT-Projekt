package de.bwvaachen.botscheduler.calculate;

import java.util.HashMap;
import java.util.Map;

import de.bwvaachen.botscheduler.calculate.WunschSlot.Status;
import de.bwvaachen.botscheduler.calculate.Zeitslot.Typ;
import klassenObjekte.Unternehmen;

public class Wunsch {
	
	private Unternehmen veranstaltung;
	private Map<Typ,WunschSlot> slots;
	private final int prio;
	private WunschState status;
		
	
	public Wunsch() {
		initSlotMap();
		status = WunschState.LEER;
		prio = 0;
	}
	
	public Wunsch(Unternehmen veranstaltung, int prio) {
		this.veranstaltung = veranstaltung;
		initSlotMap();
		this.prio = prio;
		this.status = WunschState.UNERFUELLT;
	}
	
	private void initSlotMap() {
		slots=new HashMap<>();
		
		Typ fruehest = Typ.A;
		if(veranstaltung != null) {
			fruehest = Typ.valueOf(veranstaltung.getFruehesterZeitslot());
		}
		
		for(Typ typ : Typ.values()) {
			
			if(Zeitslot.istGleichOderSpaeter(typ, fruehest)) {
				slots.put(typ, new WunschSlot(typ, Status.FREI));
			}
			else {
				slots.put(typ, new WunschSlot(typ, Status.GEBLOCKT));
			}
		}		
	}

	public Unternehmen getVeranstaltung() {
		return veranstaltung;
	}

	public Map<Typ, WunschSlot> getSlots() {
		return slots;
	}

	public int getPrio() {
		return prio;
	}

	public WunschState getState() {
		return status;
	}
	
	public void setState(WunschState state) {
		this.status = state;
	}
	
	public enum WunschState {
		ERFUELLT, LEER, UNERFUELLT
	}
}
