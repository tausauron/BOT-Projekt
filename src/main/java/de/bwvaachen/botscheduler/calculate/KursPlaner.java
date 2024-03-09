package de.bwvaachen.botscheduler.calculate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.bwvaachen.botscheduler.calculate.Wunsch.WunschState;
import de.bwvaachen.botscheduler.calculate.WunschSlot.Status;
import de.bwvaachen.botscheduler.calculate.Zeitslot.Typ;
import klassenObjekte.Kurse;
import klassenObjekte.Raum;
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
	private List<Raum> raeume;

	/**
	 * 
	 * @param schueler Schülerliste mit Wünschen, die durch Kursbelegungen erfüllt werden sollen
	 * @param unternehmen Liste von Unternehmen, die eigentlich eine Liste von Veranstaltungen ist
	 * @return Erfolgsscore als Prozentsatz vom maximal erreichbaren Score : String
	 */
	public String belegeKurse(List<Schueler> schueler, List<Unternehmen> unternehmen, List<Raum> raeume) {
		
		setcSchueler(schueler, unternehmen);
		setKurse(new ArrayList<>());
		setUnternehmen(unternehmen);
		setRaeume(raeume);

		System.out.println("ignore rooms");
		for (int i = 0; i < 6; i++) {
			runIteration(true);
		}
		simpleOutput();
		
		System.out.println("swapping");
		for (int i = 0; i < 6; i++) {
			trySwapping(false);
		}
		simpleOutput();
		
		System.out.println("remove weak");
		removeWeakCourses();
		simpleOutput();
		
		System.out.println("normal iteration");
		for (int i = 0; i < 6; i++) {
			runIteration(false);
		}
		simpleOutput();
		
		System.out.println("swapping");
		for (int i = 0; i < 6; i++) {
			trySwapping(false);
		}
		simpleOutput();
	
		System.out.println("force swapping");
		for (int i = 0; i < 6; i++) {
			trySwapping(true);
		}
		simpleOutput();
		
		System.out.println("deleteEmpty");
		deleteEmptyCourses();
		simpleOutput();
		
		System.out.println("normal iteration");
		for (int i = 0; i < 6; i++) {
			runIteration(false);
		}
		simpleOutput();
		
		System.out.println("alternatives");
		for (int i = 0; i < 6; i++) {
			useAlternative();
		}
		simpleOutput();
		
		return prozentScore()+ ", " + belegteSlots();		
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

	private void setcSchueler(List<Schueler> schueler, List<Unternehmen> unternehmen) {
		initSchueler(schueler, unternehmen);
	}

	private void runIteration(boolean ignoreRooms) {

		for (CalcSchueler schuel : cSchueler) {

			List<Wunsch> wuensche = schuel.getWuensche();
			wuensche.sort(wunschByOccurrence);

			for (Wunsch wunsch : wuensche) {

				if (wunsch.getState() == WunschState.UNERFUELLT) {
					Kurse kurs = findMatchingKurs(wunsch, schuel, Typ.A);
					
					if(kurs != null ) {
						schuel.bookCourse(kurs, wunsch);
					}
					else {
						Typ typ = findOpenKursSlot(wunsch, schuel, Typ.A, ignoreRooms);
						if(typ != null) {		
							kurs = new Kurse(new ArrayList<>(), wunsch.getVeranstaltung(), new Zeitslot(typ));
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
	
	private void trySwapping(boolean force) {
		for (CalcSchueler schuel : cSchueler) {
			
			List<Wunsch> wuensche = schuel.getWuensche();
			wuensche.sort(wunschByOccurrence);

			for (Wunsch wunsch : wuensche) {

				if (wunsch.getState() == WunschState.UNERFUELLT) {
					
					swapCourse(wunsch, schuel, Typ.A, force);
					
				}
			}
			
		}

	}
	
	private void swapCourse(Wunsch wunsch, CalcSchueler schuel, Typ typ, boolean force) {
		Kurse kurs = findMatchingKurs(wunsch, typ);
		
		if(kurs != null) {
			Wunsch candidate = schuel.getSlotByType(kurs.getZeitslot().getTyp()).getErfuellterWunsch();
			
			if(candidate != null) {						
				Kurse ausweichKurs = findMatchingKurs(candidate, schuel, Typ.A);
				if(ausweichKurs != null) {
					schuel.leaveCourse(schuel.getSlotByType(kurs.getZeitslot().getTyp()).getKurs(), candidate);
					schuel.bookCourse(kurs, wunsch);
					schuel.bookCourse(ausweichKurs, candidate);
				}
				else if(force && wunsch.getPrio() > candidate.getPrio()){
					schuel.leaveCourse(schuel.getSlotByType(kurs.getZeitslot().getTyp()).getKurs(), candidate);
					schuel.bookCourse(kurs, wunsch);
				}
				else
				{
					typ = kurs.getZeitslot().getTyp();
				
					if (typ.ordinal() < Typ.values().length-1){
						typ = Typ.values()[typ.ordinal()+1];					
						swapCourse(wunsch, schuel, typ, force);
					}					
				}
			}
			else {
				schuel.bookCourse(kurs, wunsch);
			}					
		}
	}
	
	
	private void useAlternative() {
		for (CalcSchueler cSchuel : cSchueler) {
			for (SchuelerSlot slot : cSchuel.getSlots()) {
				if (slot.getKurs() == null) {
					Wunsch wunsch = cSchuel.getAusweichWunsch();
					if (wunsch.getState() == WunschState.UNERFUELLT) {
						Kurse kurs = findMatchingKurs(wunsch, Typ.A);

						if (kurs != null) {
							cSchuel.bookCourse(kurs, wunsch);
						} else {
							swapCourse(wunsch, cSchuel, Typ.A, false);

							if (slot.getKurs() == null) {
								Typ typ = findOpenKursSlot(wunsch, cSchuel, Typ.A, false);
								if (typ != null) {
									kurs = new Kurse(new ArrayList<>(), wunsch.getVeranstaltung(), new Zeitslot(typ));
									kurse.add(kurs);
									wunsch.getVeranstaltung().getKurse().put(kurs.getZeitslot().getTyp(), kurs);
									cSchuel.bookCourse(kurs, wunsch);
								}
							}
						}
					}
					break;
				}
			}
		}
	}	
	
	private void removeWeakCourses() {
		for(Typ slot : Typ.values()) {
			List<Kurse> slotKurse = courses(slot, null);
			slotKurse.sort(kursByWeight);
			if(slotKurse.size() > raeume.size()) {
				List<Kurse> toDelete = slotKurse.subList(raeume.size(), slotKurse.size());
				for(Kurse kurs : toDelete) {
					deleteCourse(kurs);
				}
			}
		}
	}

	private Kurse existsKurs(Zeitslot slot, Wunsch wunsch) {
		Kurse retVal = null;

		for (Kurse kurs : kurse) {
			if (kurs.getZeitslot().getTyp().equals(slot.getTyp())
					&& kurs.getUnternehmen().equals(wunsch.getVeranstaltung())) {
				retVal = kurs;
			}
		}

		return retVal;
	}

	private boolean kursVoll(Kurse kurs) {
		boolean retVal = true;

		int maxTeilnehmer = kurs.getUnternehmen().getMaxTeilnehmer();
		int currTeilnehmer = kurs.getKursTeilnehmer().size();

		if (currTeilnehmer < maxTeilnehmer) {
			retVal = false;
		}

		return retVal;
	}

	private SchuelerSlot nextMatching(Wunsch wunsch, CalcSchueler cSchuel, Typ start) {
		SchuelerSlot retVal = null;
		Typ freeSlot = start;

		while (cSchuel.nextFreeSlot(freeSlot) != null) {
			freeSlot = cSchuel.nextFreeSlot(freeSlot).getTyp();
			if (wunsch.getSlots().get(freeSlot).getStatus().equals(Status.FREI)) {
				retVal = cSchuel.getSlotByType(freeSlot);
				break;
			}else {
				freeSlot = Typ.values()[freeSlot.ordinal()+1];
			}
		}
		return retVal;
	}

	/**
	 * Kurs finden fuer einen leeren Schuelerslot
	 * @param wunsch
	 * @param cSchuel
	 * @param start
	 * @return
	 */
	private Kurse findMatchingKurs(Wunsch wunsch, CalcSchueler cSchuel, Typ start) {
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
	
	private Kurse findMatchingKurs(Wunsch wunsch,  Typ start) {
		
		Kurse retVal = null;

		Kurse kurs = existsKurs(new Zeitslot(start), wunsch);

		if (kurs != null) {
			if (!kursVoll(kurs)) {
				retVal = kurs;
			} 
		}
		else if (start.ordinal() < Typ.values().length-1){
			Typ typ = Typ.values()[start.ordinal()+1];
			retVal = findMatchingKurs(wunsch, typ);
		}		
		return retVal;
	}
	
	
	private Typ findOpenKursSlot(Wunsch wunsch, CalcSchueler cSchuel, Typ slot, boolean ignoreRooms) {
		Typ retVal = null;
		
		Unternehmen unt = wunsch.getVeranstaltung();
		SchuelerSlot freeSlot = nextMatching(wunsch, cSchuel, slot);
		
		if (freeSlot != null) {
			Kurse kurs = unt.getKurse().get(freeSlot.getTyp());
			boolean freeRoom = freeRoom(freeSlot.getTyp()) || ignoreRooms;
			boolean avoidvoid = nextToExisting(freeSlot.getTyp(), unt);
			if(hasMultiple(unt)) {
				avoidvoid = !(courses(freeSlot.getTyp(), unt.getUnternehmen()).size() > 0);
			}
			
			if (kurs == null  && unt.freeSlot() && freeRoom && avoidvoid) {	
				retVal = freeSlot.getTyp();
			} 
			else  if (slot.ordinal() < Typ.values().length-1) {
				Typ typ = Typ.values()[slot.ordinal()+1];
				retVal = findOpenKursSlot(wunsch, cSchuel, typ, ignoreRooms);
			}
		}		
		return retVal;
	}
	
	/**
	 * hat die Veranstaltung schon einen Kurs neben diesem Slot? 
	 * (Luecken vermeiden)
	 * @param slot
	 * @param unt
	 * @return
	 */
	private boolean nextToExisting(Typ slot, Unternehmen unt) {
		boolean retVal = false;
		
		
		if(unt.getKurse().values().size() > 0) {
			if(slot.ordinal() < Typ.values().length-1) {
				if(unt.getKurse().get(Typ.values()[slot.ordinal()+1]) != null) {
					retVal = true;
				}
			}
			
			if(slot.ordinal() > 0) {
				if(unt.getKurse().get(Typ.values()[slot.ordinal()-1]) != null) {
					retVal = true;
				}	
			}
		}
		else {
			retVal = true;
		}
		
		return retVal;
	}
	
	
	
	
	private boolean freeRoom(Typ slotTyp) {
		int number = 0;
		for( Kurse kurs : kurse ) {
			if(kurs.getZeitslot().getTyp().equals(slotTyp)) {
				number++;
			}
		}
		return (number < raeume.size());		
	}
	

	private void setKurse(List<Kurse> kurse) {
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
	
	private void setRaeume(List<Raum> raeume) {
		this.raeume = raeume;
	}

	private void setUnternehmen(List<Unternehmen> unternehmen) {
		this.unternehmen = unternehmen;
		
//		for(Unternehmen unt : unternehmen) {
//			Map<Typ, Kurse> kurse = unt.getKurse();
//			
//			for(Typ typ : Typ.values()) {
//				kurse.put(typ, null);
//			}
//		}
	}
	
	private void deleteEmptyCourses() {
		List<Kurse> found = new ArrayList<>();
		for(Kurse kurs : kurse) {
			if(kurs.getKursTeilnehmer().size() == 0) {
				kurs.getUnternehmen().getKurse().remove(kurs.getZeitslot().getTyp());
				found.add(kurs);
			}
		}
		kurse.removeAll(found);
	}
	
	private void deleteCourse(Kurse kurs) {
		for(CalcSchueler cSchuel : new ArrayList<CalcSchueler>(kurs.getKursTeilnehmer())) {
			cSchuel.leaveCourse(kurs, cSchuel.getSlotByType(kurs.getZeitslot().getTyp()).getErfuellterWunsch());
		}
		kurs.getUnternehmen().getKurse().remove(kurs.getZeitslot().getTyp());
		kurse.remove(kurs);
	}
	
	
	Comparator<Wunsch> wunschByOccurrence = new Comparator<Wunsch>() {
		
		@Override
		public int compare(Wunsch o1, Wunsch o2) {			
			return Integer.compare(occurrence(o2), occurrence(o1));
		}
		
	};
	
	Comparator<Wunsch> wunschByPriority = new Comparator<Wunsch>() {
		@Override
		public int compare(Wunsch o1, Wunsch o2) {			
			return Integer.compare(o1.getPrio(), o2.getPrio());
		}
	};
	
	Comparator<Kurse> kursByWeight = new Comparator<Kurse>() {
		
		@Override
		public int compare(Kurse o1, Kurse o2) {
			return Integer.compare(o2.weight(), o1.weight());
		}
	};
	
	
	private int occurrence(Wunsch wunsch) {
		int retVal = 0;
		
		if(wunsch.getVeranstaltung() != null && wunsch.getState().equals(WunschState.UNERFUELLT)) {
		
			for(CalcSchueler cSchuel : cSchueler){
				List<Wunsch> wuensche = cSchuel.getWuensche();
				for(Wunsch w : wuensche) {
					if(w.getVeranstaltung() != null) {
						if(wunsch.getVeranstaltung().equals(w.getVeranstaltung())) {
							retVal+= w.getPrio();
						}
					}
				}
			}
		}
		
		return retVal;
	}
	
	
	private int belegteSlots() {
		int retVal = 0;
		for(CalcSchueler cSchuel : cSchueler) {			
			for(SchuelerSlot slot : cSchuel.getSlots()) {
				if(slot.getKurs() != null) {
					retVal++;
				}
			}			
		}
		return retVal;
	}
	
	private List<Kurse> courses(Typ slot, String unternehmen) {
		List<Kurse> retVal = new ArrayList<Kurse>();
		for(Kurse kurs : kurse) {
			if(kurs.getZeitslot().getTyp().equals(slot)) {
				if(unternehmen != null) {
					if(unternehmen.replaceAll("\\s+","").equals(kurs.getUnternehmen().getUnternehmen().replaceAll("\\s+",""))) {
						retVal.add(kurs);
					}
				}else {
					retVal.add(kurs);
				}
						
			}
		}
		return retVal;
	}
	
	public boolean hasMultiple(Unternehmen unt) {
		boolean retVal = false;
		int number = 0;
		for(Unternehmen unt2 : unternehmen) {
			if(unt2.getUnternehmen().replaceAll("\\s+","").equals(unt.getUnternehmen().replaceAll("\\s+",""))) {
				number++;
			}
			if(number > 1) {
				retVal = true;
			}
		}		
		return retVal;
	}
	
	
	
	private void simpleOutput() {
//		for (CalcSchueler cSchuel : getcSchueler()) {
//
//			String zeile = cSchuel.getSchueler().getNachname() + " | ";
//
//			for (SchuelerSlot slot : cSchuel.getSlots()) {
//				Kurse kurs = slot.getKurs();
//				if (kurs != null) {
//					zeile += slot.getKurs().getUnternehmen().getFirmenID() + " | ";
//				} else {
//					zeile += "null |";
//				}
//			}
//
//			System.out.println(zeile);
//		}

		for (Unternehmen unt : getUnternehmen()) {
			String zeile = unt.getFirmenID() + " | ";
			for (Typ typ : Typ.values()) {
				Kurse kurs = unt.getKurse().get(typ);
				if (kurs != null) {
					zeile += kurs.getKursTeilnehmer().size() + " | ";
				} else {
					zeile += "null | ";
				}
			}
			System.out.println(zeile);
		}
		System.out.println(prozentScore());
		System.out.println(belegteSlots());
	}
	
}
