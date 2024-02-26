 package de.bwvaachen.botscheduler.calculate;

import java.util.ArrayList;
import java.util.List;

import de.bwvaachen.botscheduler.calculate.WunschSlot.Status;
import de.bwvaachen.botscheduler.calculate.Zeitslot.Typ;
import klassenObjekte.Kurse;
import klassenObjekte.Schueler;
import klassenObjekte.Unternehmen;

/**
 * Klasse, die die Kurse belegt
 * 
 * @author tautenhahn_max
 *
 */
public class KursPlaner {
	
	private List<Kurse> kurse;
	private List<CalcSchueler> cSchueler;
	private List<Unternehmen> unternehmen;
	
	


	public List<Kurse> belegeKurse(List<Schueler> schueler, List<Unternehmen> unternehmen){
		
		initSchueler(schueler, unternehmen);
		kurse = new ArrayList<>();
		
		
		
		return null;		
	}
	
	private void initSchueler(List<Schueler> schueler, List<Unternehmen> unternehmen) {
		
		cSchueler = new ArrayList<>();
		for(Schueler schuel : schueler) {
			cSchueler.add(new CalcSchueler(schuel, unternehmen));
		}
	}
	
	private int calculateScore() {
		
		int retVal = 0;
		
		for(CalcSchueler cSchuel : cSchueler) {
			
			retVal += cSchuel.calculateCurrScore();
		}
		
		System.out.println("Score: " + retVal);

		return retVal;
 	}
	
	
	private int calculateMaxScore() {
		
		int retVal = 0;
		
		for(CalcSchueler cSchuel : cSchueler) {
			retVal += cSchuel.calculateMaxScore();			
		}
		System.out.println("MaxScore: " + retVal);
		return retVal;		
	}
	
	public String prozentScore() {
		
		String retVal = "0%";
		double pScore = (double)calculateScore()/(double)calculateMaxScore()*100d;
		
		retVal = String.format("%.2f",pScore) + " %";
		
		return retVal;
	}
	
	
	public void setcSchueler(List<Schueler> schueler, List<Unternehmen> unternehmen) {
		initSchueler(schueler, unternehmen);
	}
	
	public void runIteration() {
		
		for(CalcSchueler schuel : cSchueler) {
			
			List<Wunsch> wuensche = schuel.getWuensche();
			
			for(Wunsch wunsch : wuensche) {
				
				if( ! wunsch.isErfuellt() && wunsch.getVeranstaltung() != null) {
					SchuelerSlot freeSlot = nextMatching(wunsch, schuel);
					if(freeSlot != null) {
						Kurse kurs = existsKurs(freeSlot, wunsch);
						
						if(kurs != null) {
							if(! kursVoll(kurs)) {

								schuel.bookCourse(freeSlot, kurs, wunsch);
							}
							
						}else {
							kurs = new Kurse(0, new ArrayList<>(), wunsch.getVeranstaltung(), (Zeitslot)freeSlot);
							kurse.add(kurs);
							wunsch.getVeranstaltung().getKurse().put(kurs.getZeitslot().getTyp(), kurs);
							schuel.bookCourse(freeSlot, kurs, wunsch);
						}						
					}
					break;					
				}
			}
			
		}
	}
	
	public Kurse existsKurs(Zeitslot slot, Wunsch wunsch) {
		Kurse retVal = null;
		
		for(Kurse kurs : kurse) {
			if(kurs.getZeitslot().getTyp().equals(slot.getTyp()) 
					&& kurs.getUnternehmen().equals(wunsch.getVeranstaltung())) {
				retVal = kurs;
			}
		}
		
		return retVal;
	}
	
	public boolean kursVoll(Kurse kurs) {
		boolean retVal=true;
		
		int maxTeilnehmer = kurs.getUnternehmen().getMaxTeilnehmer();
		int currTeilnehmer = kurs.getKursTeilnehmer().size();
		
		if(currTeilnehmer < maxTeilnehmer) {
			retVal = false;
		}	
		
		return retVal;
	}
	
	public SchuelerSlot nextMatching(Wunsch wunsch, CalcSchueler cSchuel) {
		SchuelerSlot retVal = null;
		SchuelerSlot freeSlot = new SchuelerSlot(Typ.A);
		
		while(cSchuel.nextFreeSlot(freeSlot) != null) {
			freeSlot=cSchuel.nextFreeSlot(freeSlot);
			if(wunsch.getSlots().get(freeSlot.getTyp()).getStatus().equals(Status.FREI)){
				retVal = freeSlot;
				break;
			}			
		}		
		return retVal;
	}
	
	public void setKurse(List<Kurse> kurse) {
		this.kurse = kurse;
	}
	
	public List<CalcSchueler> getcSchueler() {
		return cSchueler;
	}
	
	public List<Kurse> getKurse() {
		return kurse;
	}
	
	public List<Unternehmen> getUnternehmen() {
		return unternehmen;
	}
	
	public void setUnternehmen(List<Unternehmen> unternehmen) {
		this.unternehmen = unternehmen;
	}
}
