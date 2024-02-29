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
	private Wunsch ausweichWunsch;
	private List<SchuelerSlot> slots;
	
	public CalcSchueler(Schueler schueler, List<Unternehmen> unternehmen) {
		this.schueler = schueler;
		initWuensche(unternehmen);
		initVeranstaltungen();
	}
	
	
	private void initWuensche(List<Unternehmen> unternehmen) {
		
		wuensche = new ArrayList<>();
		List<String> strWuensche = schueler.getAllWuensche();
		
		for(int i = 0; i < strWuensche.size()-1; i++) {
			wuensche.add(createWunsch(unternehmen, strWuensche.get(i), strWuensche.size()-i));
		}
		
		ausweichWunsch = createWunsch(unternehmen, strWuensche.get(strWuensche.size()-1), 1);
	}
	
	private Wunsch createWunsch(List<Unternehmen> unternehmen, String strWunsch, int prio) {
		Wunsch retVal = null;		

		Unternehmen unt = findUnternehmen(strWunsch, unternehmen);
		if(unt != null) {
			retVal = new Wunsch(unt, prio);
		}
		else {
			retVal = new Wunsch();
		}	
		return retVal;	
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
		for(int i=0; i < wuensche.size(); i++) {
			
			if(wuensche.get(i).getVeranstaltung() != null) {
				retVal += wuensche.get(i).getPrio();
				counter++;
			}			
		}
		
		//falls einer leer war, den letzten noch addieren
		if(counter < wuensche.size() && ausweichWunsch.getVeranstaltung() != null) {
			retVal += ausweichWunsch.getPrio();
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
	
	public SchuelerSlot nextFreeSlot(Typ start) {
		SchuelerSlot retVal = null;
		
		for(SchuelerSlot slot : slots) {
			if(slot.getKurs() == null
				&&  Zeitslot.istGleichOderSpaeter(slot.getTyp(), start)) {
				retVal = slot;
				break;
			}
		}
		
		return retVal;
	}
	
	public void bookCourse(Kurse kurs, Wunsch erfWunsch) {
		getSlotByType(kurs.getZeitslot().getTyp()).setKurs(kurs);
		List<CalcSchueler> teilnehmer = kurs.getKursTeilnehmer();
		teilnehmer.add(this);
		kurs.setKursTeilnehmer(teilnehmer);
		
		
		for(Wunsch wunsch : wuensche) {
			WunschSlot wSlot = wunsch.getSlots().get(kurs.getZeitslot().getTyp());
			if(wSlot.getStatus().equals(Status.FREI)) {
				wSlot.setStatus(Status.BELEGT);
			}
			if(wunsch.equals(erfWunsch)) {
				wunsch.setErfuellt(true);
			}
		}		
	}
	
	public SchuelerSlot getSlotByType(Typ typ){
		SchuelerSlot retVal = null;
		
		for(SchuelerSlot slot : slots) {
			if(slot.getTyp().equals(typ)) {
				retVal = slot;
				break;
			}
		}
		return retVal;		
	}

}







