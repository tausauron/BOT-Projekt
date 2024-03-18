package de.bwvaachen.botscheduler.calculate;

import java.util.HashMap;
import java.util.Map;

import de.bwvaachen.botscheduler.calculate.WunschSlot.Status;
import de.bwvaachen.botscheduler.calculate.Zeitslot.Typ;
import klassenObjekte.Unternehmen;

/**
 * Auspraegung von Schuelerwuenschen für den Belegungsalgorithmus
 * 
 * @author Max Tautenhahn
 */
public class Wunsch {
	
	private Unternehmen veranstaltung;
	private Map<Typ,WunschSlot> slots;
	private final int prio;
	private WunschState status;
	private int nummer;
	
	/**
	 * Konstruktor fuer leeren Wunsch
	 * @param nummer Wunschnummer
	 */
	public Wunsch(int nummer) {
		initSlotMap();
		status = WunschState.LEER;
		this.nummer = nummer;
		prio = 0;
	}
	
	public Wunsch(int nummer, Unternehmen veranstaltung, int prio) {
		this.nummer = nummer;
		this.veranstaltung = veranstaltung;
		initSlotMap();
		this.prio = prio;
		this.status = WunschState.UNERFUELLT;
	}
	
	/**
	 * initialisiert die Map der Zeitslots unter Beruecksichtung des fruehesten Zeitslots
	 * und der maximalen Anzahl Veranstaltungen
	 */
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

	//Getter Setter
	
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
	
	public int getNummer() {
		return nummer;
	}
	
	public void setState(WunschState state) {
		this.status = state;
	}
	
	
	/**
	 * Enum zur Definition gueltiger Zustaende eines Wunsches
	 */
	public enum WunschState {
		ERFUELLT, LEER, UNERFUELLT
	}
}
