package de.bwvaachen.botscheduler.calculate;

import java.util.ArrayList;
import java.util.List;

import de.bwvaachen.botscheduler.calculate.WunschSlot.Status;
import de.bwvaachen.botscheduler.calculate.Zeitslot.Typ;
import klassenObjekte.Kurse;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

public class CalcSchueler {
	
	
	private final Schueler schueler;
	private List<Wunsch> wuensche;
	private List<SchuelerSlot> slots;
	
	public CalcSchueler(Schueler schueler, List<Unternehmen> unternehmen) {
		this.schueler = schueler;
		initWuensche(unternehmen);
		initVeranstaltungen();
	}
	
	
	private void initWuensche(List<Unternehmen> unternehmen) {
		
		wuensche = new ArrayList<>();
		List<String> strWuensche = schueler.getAllWuensche();
		
		for(int i = 0; i < strWuensche.size(); i++) {
			Unternehmen unt = findUnternehmen(strWuensche.get(i), unternehmen);
			if(unt != null) {
				wuensche.add(new Wunsch(unt, strWuensche.size()-i));
			}
			else {
				wuensche.add(new Wunsch());
			}			
		}		
	}
	
	private void initVeranstaltungen() {
		slots = new ArrayList<>();
		
		for(Typ typ : Typ.values()) {
			slots.add(new SchuelerSlot(typ));
		}			
	}
	
	
	private Unternehmen findUnternehmen(String wunsch, List<Unternehmen> unternehmen) {
		
		Unternehmen retVal = null;
		
		for(Unternehmen unt :unternehmen ) {
			
			//leerer Wunsch
			if(wunsch.equals("")) {
				break;
			}
			
			if(unt.getFirmenID() == Integer.valueOf(wunsch)) {
				retVal = unt;
				break;
			}
		}		
		return retVal;
	}


	public Schueler getSchueler() {
		return schueler;
	}


	public List<Wunsch> getWuensche() {
		return wuensche;
	}


	public List<SchuelerSlot> getSlots() {
		return slots;
	}
	
	public int calculateMaxScore() {
		
		int retVal = 0;
		int counter = 0;
		
		//erstmal alle ohne den letzten addieren
		for(int i=0; i < wuensche.size()-1; i++) {

			
			if(wuensche.get(i).getVeranstaltung() != null) {
				retVal += wuensche.get(i).getPrio();
				counter++;
			}			
		}
		
		//falls einer leer war, den letzten noch addieren
		if(counter < wuensche.size()-1) {
			retVal += wuensche.get(wuensche.size()-1).getPrio();
		}
		
		return retVal;
	}
	
	public int calculateCurrScore() {
		
		int retVal = 0;
		
		for(Wunsch wunsch : wuensche) {
			if(wunsch.isErfuellt()) {
				retVal += wunsch.getPrio();
			}
		}
		
		return retVal;
	}
	
	public SchuelerSlot nextFreeSlot(Zeitslot start) {
		SchuelerSlot retVal = null;
		
		for(SchuelerSlot slot : slots) {
			if(slot.getKurs() == null
				&&  Zeitslot.istGleichOderSpaeter(slot.getTyp(), start.getTyp())) {
				retVal = slot;
				break;
			}
		}
		
		return retVal;
	}
	
	public void bookCourse(SchuelerSlot slot, Kurse kurs, Wunsch erfWunsch) {
		slot.setKurs(kurs);
		List<CalcSchueler> teilnehmer = kurs.getKursTeilnehmer();
		teilnehmer.add(this);
		kurs.setKursTeilnehmer(teilnehmer);
		
		
		for(Wunsch wunsch : wuensche) {
			WunschSlot wSlot = wunsch.getSlots().get(slot.getTyp());
			if(wSlot.getStatus().equals(Status.FREI)) {
				wSlot.setStatus(Status.BELEGT);
			}
			if(wunsch.equals(erfWunsch)) {
				wunsch.setErfuellt(true);
			}
		}
		
	}

}








