package de.bwvaachen.botscheduler.calculate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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

	public List<Kurse> belegeKurse(List<Schueler> schueler, List<Unternehmen> unternehmen) {

		initSchueler(schueler, unternehmen);
		kurse = new ArrayList<>();

		return null;
	}

	private void initSchueler(List<Schueler> schueler, List<Unternehmen> unternehmen) {

		cSchueler = new ArrayList<>();
		for (Schueler schuel : schueler) {
			cSchueler.add(new CalcSchueler(schuel, unternehmen));
		}
	}

	private int calculateScore() {

		int retVal = 0;

		for (CalcSchueler cSchuel : cSchueler) {

			retVal += cSchuel.calculateCurrScore();
		}

		System.out.println("Score: " + retVal);

		return retVal;
	}

	private int calculateMaxScore() {

		int retVal = 0;

		for (CalcSchueler cSchuel : cSchueler) {
			retVal += cSchuel.calculateMaxScore();
		}
		System.out.println("MaxScore: " + retVal);
		return retVal;
	}

	public String prozentScore() {

		String retVal = "0%";
		double pScore = (double) calculateScore() / (double) calculateMaxScore() * 100d;

		retVal = String.format("%.2f", pScore) + " %";

		return retVal;
	}

	public void setcSchueler(List<Schueler> schueler, List<Unternehmen> unternehmen) {
		initSchueler(schueler, unternehmen);
	}

	public void runIteration() {

		for (CalcSchueler schuel : cSchueler) {

			List<Wunsch> wuensche = schuel.getWuensche();
			wuensche.sort(wunschByOccurrence);

			for (Wunsch wunsch : wuensche) {

				if (!wunsch.isErfuellt() && wunsch.getVeranstaltung() != null) {
					Kurse kurs = findMatchingKurs(wunsch, schuel, Typ.A);
					
					if(kurs != null ) {
						schuel.bookCourse(kurs, wunsch);
					}
					else {
						Typ typ = findOpenKursSlot(wunsch, schuel, Typ.A);
						if(typ != null) {
							kurs = new Kurse(0, new ArrayList<>(), wunsch.getVeranstaltung(), new Zeitslot(typ));
							kurse.add(kurs);
							wunsch.getVeranstaltung().getKurse().put(kurs.getZeitslot().getTyp(), kurs);
							schuel.bookCourse(kurs, wunsch);
						}
					}
					break;
				}

			}
		}

	}
	

	public Kurse existsKurs(Zeitslot slot, Wunsch wunsch) {
		Kurse retVal = null;

		for (Kurse kurs : kurse) {
			if (kurs.getZeitslot().getTyp().equals(slot.getTyp())
					&& kurs.getUnternehmen().equals(wunsch.getVeranstaltung())) {
				retVal = kurs;
			}
		}

		return retVal;
	}

	public boolean kursVoll(Kurse kurs) {
		boolean retVal = true;

		int maxTeilnehmer = kurs.getUnternehmen().getMaxTeilnehmer();
		int currTeilnehmer = kurs.getKursTeilnehmer().size();

		if (currTeilnehmer < maxTeilnehmer) {
			retVal = false;
		}

		return retVal;
	}

	public SchuelerSlot nextMatching(Wunsch wunsch, CalcSchueler cSchuel, Typ start) {
		SchuelerSlot retVal = null;
		Typ freeSlot = start;

		while (cSchuel.nextFreeSlot(freeSlot) != null) {
			freeSlot = cSchuel.nextFreeSlot(freeSlot).getTyp();
			if (wunsch.getSlots().get(freeSlot).getStatus().equals(Status.FREI)) {
				retVal = cSchuel.getSlotByType(freeSlot);
				break;
			}
		}
		return retVal;
	}

	public Kurse findMatchingKurs(Wunsch wunsch, CalcSchueler cSchuel, Typ start) {
		Kurse retVal = null;

		SchuelerSlot freeSlot = nextMatching(wunsch, cSchuel, start);
		if (freeSlot != null) {
			Kurse kurs = existsKurs(freeSlot, wunsch);

			if (kurs != null) {
				if (!kursVoll(kurs)) {

					retVal = kurs;
				} 
				else if (start.ordinal() < Typ.values().length-1){
					Typ typ = Typ.values()[start.ordinal()+1];
					retVal = findMatchingKurs(wunsch, cSchuel, typ);
				}
			}
		}
		return retVal;
	}
	
	public Typ findOpenKursSlot(Wunsch wunsch, CalcSchueler cSchuel, Typ start) {
		Typ retVal = null;
		
		Unternehmen unt = wunsch.getVeranstaltung();
		SchuelerSlot freeSlot = nextMatching(wunsch, cSchuel, start);
		
		if (freeSlot != null) {
			Kurse kurs = unt.getKurse().get(freeSlot.getTyp());
			if (kurs == null) {				
				retVal = freeSlot.getTyp();
			} 
			else  if (start.ordinal() < Typ.values().length-1) {
				Typ typ = Typ.values()[start.ordinal()+1];
				retVal = findOpenKursSlot(wunsch, cSchuel, typ);
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
		
		for(Unternehmen unt : unternehmen) {
			Map<Typ, Kurse> kurse = unt.getKurse();
			
			for(Typ typ : Typ.values()) {
				kurse.put(typ, null);
			}
		}
	}
	
	
	Comparator<Wunsch> wunschByOccurrence = new Comparator<Wunsch>() {
		
		@Override
		public int compare(Wunsch o1, Wunsch o2) {			
			return Integer.compare(occurrence(o2), occurrence(o1));
		}
		
		
		private int occurrence(Wunsch wunsch) {
			int retVal = 0;
			
			if(wunsch.getVeranstaltung() != null) {
			
				for(CalcSchueler cSchuel : cSchueler){
					List<Wunsch> wuensche = cSchuel.getWuensche();
					for(Wunsch w : wuensche) {
						if(w.getVeranstaltung() != null) {
							if(wunsch.getVeranstaltung().equals(w.getVeranstaltung())) {
								retVal+=1;
							}
						}
					}
				}
			}
			
			return retVal;
		}
	}; 
}
