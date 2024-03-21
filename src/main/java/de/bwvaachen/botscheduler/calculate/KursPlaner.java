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
 * Klasse, die die Kurse belegt.<br />
 * <br />
 * Benutzung: Kursplaner instanzieren, die Methode "belegeKurse(params)" aufrufen.<br />
 * <br />
 * Mit "getKurse()" die erzeugte Kursliste abrufen.<br />
 *  Mit "getcSchueler()" kann man erweiterte Schuelerobjekte abrufen, die Informationen über 
 * belegte Kurse auf ihren Zeitslots enthalten.<br />
 * Den uebergebenen Unternehmensobjekten werden auch Zeitslots mit Kursobjekten angehaengt (praktisch fuer Reporte).
 * 
 * @author Max Tautenhahn
 *
 */
public class KursPlaner {

	//Instanzvariablen
	private List<Kurse> kurse;
	private List<CalcSchueler> cSchueler;
	private List<Unternehmen> unternehmen;
	private List<Raum> raeume;
	private boolean debug = false;

	
	/**
	 * Erzeugt Kurse und belegt diese mit Schuelern. 
	 * Es kommen verschiedene Strategien zum Einsatz und es wird geprueft, ob dadurch der 
	 * Erfolgsscore noch steigt. 
	 * 
	 * @param schueler Schülerliste mit Wünschen, die durch Kursbelegungen erfüllt werden sollen
	 * @param unternehmen Liste von Unternehmen, die eigentlich eine Liste von Veranstaltungen ist
	 * @return Erfolgsscore als Prozentsatz vom maximal erreichbaren Score : String
	 */
	public String belegeKurse(List<Schueler> schueler, List<Unternehmen> unternehmen, List<Raum> raeume) {
		
		initSchueler(schueler, unternehmen);
		this.kurse = new ArrayList<>();
		this.unternehmen = unternehmen;
		this.raeume = raeume;	
		int score = 0;
		
		do {
			score = calculateScore();
			runIteration(true, true);
		}while(score<calculateScore());
		simpleOutput("ignore rooms and firms");

		removeWeakCourses();
		simpleOutput("remove weak");
		
		do {
			score = calculateScore();
			runIteration(true, false);
		}while(score<calculateScore());
		simpleOutput("ignore rooms");

			
		do {
			score = calculateScore();
			trySwapping(false);
		}while(score<calculateScore());
		simpleOutput("swapping");

		do {
			score = calculateScore();
			trySwapping(true);
		}while(score<calculateScore());
		simpleOutput("force swapping");
		
		removeWeakCourses();
		simpleOutput("remove weak");
		
		do {
			score = calculateScore();
			runIteration(false, false);
		}while(score<calculateScore());
		simpleOutput("normal iteration");
		
		do {
			score = calculateScore();
			trySwapping(false);
		}while(score<calculateScore());
		simpleOutput("normal iteration");
	
		do {
			score = calculateScore();
			trySwapping(true);
		}while(score<calculateScore());
		simpleOutput("force swapping");
		
		deleteEmptyCourses();
		simpleOutput("deleteEmpty");
		
		do {
			score = calculateScore();
			runIteration(false, false);
		}while(score<calculateScore());
		simpleOutput("normal iteration");
		
		do {
			score = calculateScore();
			useAlternative();			
		}while(score<calculateScore());
		simpleOutput("alternatives");
		
		fillEmpty();
		simpleOutput("fill empty wishes");		
		
		return prozentScore();		
	}
	
	public List<CalcSchueler> getcSchueler() {
		return cSchueler;
	}
	

	public List<Kurse> getKurse() {
		return kurse;
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	

	/**
	 * berechnet den Prozentsatz des erreichten Erfolgsscore vom theoretisch erreichbaren
	 * 
	 * @return prozentualer Score: String
	 */
	public String prozentScore() {

		String retVal = "0%";
		double pScore = (double) calculateScore() / (double) calculateMaxScore() * 100d;

		retVal = String.format("%.2f", pScore) + " %";

		return retVal;
	}
	

	/**
	 * erzeugt aus der Schuelerliste eine entsprechende CalcSchuelerliste
	 * 
	 * @param schueler Schuelerliste
	 * @param unternehmen Unternehmensliste
	 */
	private void initSchueler(List<Schueler> schueler, List<Unternehmen> unternehmen) {

		cSchueler = new ArrayList<>();
		for (Schueler schuel : schueler) {
			cSchueler.add(new CalcSchueler(schuel, unternehmen));
		}
	}

	/**
	 * berechnet den aktuellen Erfolgsscore
	 * 	  
	 * @return Erfolgsscore: int
	 */
	private int calculateScore() {

		int retVal = 0;

		for (CalcSchueler cSchuel : cSchueler) {

			retVal += cSchuel.calculateCurrScore();
		}
		return retVal;
	}
		
	/**
	 * berechnet den theoretisch erreichbaren Erfolgsscore
	 * 
	 * @return maximaler Score : int
	 */
	private int calculateMaxScore() {

		int retVal = 0;

		for (CalcSchueler cSchuel : cSchueler) {
			retVal += cSchuel.calculateMaxScore();
		}
		return retVal;
	}

	/**
	 * Iteration, die Kurse anlegt und Wuensche von Schuelern erfuellt.
	 * Pro Durchlauf wird versucht pro Schueler einen nichtleeren 
	 * Wunsch zu erfuellen.
	 * Die Wuensche werden nach Gewichtung (Anzahl unzugeordneter Bewerber * Prioritaet Wunsch) vorsortiert.
	 * 
	 * @param ignoreRooms wenn true, wird die Anzahl der verfuegbaren Raeume ignoriert
	 * @param ignoreFirms wenn true, wird ignoriert, dass eine Firma pro Zeislot nur einen Kurs anbietet
	 */
	private void runIteration(boolean ignoreRooms, boolean ignoreFirms) {

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
						Typ typ = findOpenKursSlot(wunsch, schuel, Typ.A, ignoreRooms, ignoreFirms);
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
	
	
	/**
	 * Versucht unerfuellte Wuensche zu erfuellen, indem fuer bereits erfuellte Wuensche ein alternativer
	 * Kurs gesucht wird.
	 * 
	 * @param force wenn true, wird eine Kursbuchung auch ohne Alternative aufgeloest, wenn die Prioritaet
	 * des fordernden Wunsches hoeher ist
	 */
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
	
	
	/**
	 * Rekursive Methode, die Kurstauschoperationen durchfuehrt
	 * 
	 * @param wunsch Wunsch, fuer den getauscht werden soll
	 * @param schuel betreffender Schueler
	 * @param typ Zeitslottyp mit dem gestartet wird
	 * @param force wenn true, tauschen auch ohne Alternative (Prio)
	 */
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
	
	
	/**
	 * versucht Alternativwuensche zu erfuellen, auch durch Anlegen neuer Kurse
	 * 
	 */
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
								Typ typ = findOpenKursSlot(wunsch, cSchuel, Typ.A, false, false);
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
	
	/**
	 * fuellt leere Wuensche mit beliebigen Kursen, beginnend mit den leersten Kursen
	 */
	private void fillEmpty() {
		for(CalcSchueler cSchuel : cSchueler) {
			for(Wunsch wunsch : cSchuel.getWuensche()) {
				if(wunsch.getState().equals(WunschState.LEER)) {
					Zeitslot slot = cSchuel.nextFreeSlot(Typ.A);
					List<Kurse> kurse = courses(slot.getTyp(), null);
					kurse.sort(kursByParticipants);
					
					for(Kurse kurs : kurse) {
						if(! alreadyBooked(kurs.getUnternehmen(), cSchuel) && 
								! kursVoll(kurs)) {
							cSchuel.bookCourse(kurs, wunsch);
							break;
						}
					}
					
				}
			}
		}
	}
	
	/**
	 * prueft, ob eine Veranstaltung (Unternehmen) von einem Schueler schon auf einem anderen 
	 * Zeitslot belegt wird
	 * 
	 * @param unt Veranstaltungstyp
	 * @param cSchuel betreffender Schueler
	 * @return true, wenn Veranstaltung schon belegt wurde
	 */
	private boolean alreadyBooked(Unternehmen unt, CalcSchueler cSchuel) {
		boolean retVal = false;
		for(SchuelerSlot slot : cSchuel.getSlots()) {
			
			if(slot.getKurs() != null) {
				if(slot.getKurs().getUnternehmen().equals(unt)) {
					retVal = true;
					break;
				}
			}
		}
		return retVal;
	}
	
	/**
	 * loescht soviele Kurse wie noetig auf jedem Zeitslot, sodass die Raumanzahl nicht überschritten wird
	 * und doppelte Kurse eines Unternehmens
	 * Kurse werden nach schwaechster Gewichtung gelöscht (Teilnehmer * Prioritaet)
	 */
	private void removeWeakCourses() {
		for(Typ slot : Typ.values()) {
			List<Kurse> slotKurse = courses(slot, null);
			slotKurse.sort(kursByWeight);
			
			List<Kurse> toDelete = new ArrayList<Kurse>();			
			for(Kurse kurs : slotKurse) {

				Unternehmen unt = kurs.getUnternehmen();
				if(hasMultiple(unt)) {
					findWeakCourses(unt, slot, toDelete);
				}
			}
			
			for(Kurse delKurs : toDelete) {
				deleteCourse(delKurs);
				slotKurse.remove(delKurs);
			}			
			
			if(slotKurse.size() > raeume.size()) {
				toDelete = slotKurse.subList(raeume.size(), slotKurse.size());
				for(Kurse kurs : toDelete) {
					deleteCourse(kurs);
				}
			}
		}
	}
	
	
	/**
	 * fuellt eine uebergebene Liste mit Kursen die geloescht werden sollen,
	 * weil es mehr als einen Kurs derselben Firma auf dem gleichen Zeitslot gibt.
	 * Gibt immer die schwaecher gewichteten Kurse zurueck.
	 * 
	 * @param unt Veranstaltungstyp
	 * @param slot zu pruefender Zeitslot
	 * @param toDelete zu fuellende Liste
	 */
	private void findWeakCourses(Unternehmen unt, Typ slot, List<Kurse> toDelete){
		List<Kurse> list = courses(slot, unt.getUnternehmen());
		
		if(list.size() > 1) {
			list.sort(kursByWeight);
			toDelete.addAll(list.subList(1, list.size()));			
		}
	}
		

	/**
	 * prueft, ob es einen Kurs auf einem Zeitslot fuer einen Wunsch schon gibt und gibt ihn zurueck
	 * 
	 * @param slot gewuenschter Zeitslot
	 * @param wunsch Wunsch, der erfuellt werden soll
	 * @return existierenden Kurs, falls vorhanden
	 */
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
	

	/**
	 * prueft, ob ein Kurs voll ist
	 * 
	 * @param kurs betreffender Kurs
	 * @return true wenn Kurs voll ist
	 */
	private boolean kursVoll(Kurse kurs) {
		boolean retVal = true;

		int maxTeilnehmer = kurs.getUnternehmen().getMaxTeilnehmer();
		int currTeilnehmer = kurs.getKursTeilnehmer().size();

		if (currTeilnehmer < maxTeilnehmer) {
			retVal = false;
		}

		return retVal;
	}
	

	/**
	 * findet den naechsten passenden Zeitslot eines Schuelers, falls vorhanden, beginnend mit 
	 * dem übergebenen Slottyp
	 * 
	 * @param wunsch zu erfuellender Wunsch
	 * @param cSchuel zu durchsuchender Schueler
	 * @param start Zeitslot, mit dem sie Suche begonnen wird
	 * @return gefundener Zeitslot
	 */
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
	 * Kurs finden fuer einen leeren Schuelerslot, falls vorhanden.
	 * Rekursiv.
	 * 
	 * @param wunsch zu erfuellender Wunsch
	 * @param cSchuel betreffender Schueler
	 * @param start Zeitslot, mit dem begonnen wird
	 * @return gefundener Kurs
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
	
	
	/**
	 * Kurs finden fuer einen unerfuellten Wunsch, falls vorhanden.
	 * Rekursiv.
	 * 
	 * @param wunsch zu erfuellender Wunsch
	 * @param start Zeitslot, mit dem begonnen wird
	 * @return gefundener Kurs
	 */
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
		
	
	
	/**
	 * sucht einen passenden Zeitslot, falls vorhanden, um einen neuen Kurs anzulegen.
	 * Rekursiv.
	 * 
	 * @param wunsch zu erfuellender Wunsch
	 * @param cSchuel betreffender Schueler
	 * @param slot Zeitslot, mit dem begonnen wird
	 * @param ignoreRooms wenn true, Raumanzahl ignorieren
	 * @param ignoreFirms wenn true, doppelte Firmenbelegung ignorieren
	 * @return Zeitslottyp, der passt
	 */
	private Typ findOpenKursSlot(Wunsch wunsch, CalcSchueler cSchuel, Typ slot, boolean ignoreRooms, boolean ignoreFirms) {
		Typ retVal = null;
		
		Unternehmen unt = wunsch.getVeranstaltung();
		SchuelerSlot freeSlot = nextMatching(wunsch, cSchuel, slot);
		
		if (freeSlot != null) {
			Kurse kurs = unt.getKurse().get(freeSlot.getTyp());
			boolean freeRoom = freeRoom(freeSlot.getTyp()) || ignoreRooms;
			boolean avoidvoid = nextToExisting(freeSlot.getTyp(), unt);
			if(hasMultiple(unt) && !ignoreFirms) {
				avoidvoid = !(courses(freeSlot.getTyp(), unt.getUnternehmen()).size() > 0);
			}
			
			if (kurs == null  && unt.freeSlot() && freeRoom && avoidvoid) {	
				retVal = freeSlot.getTyp();
			} 
			else  if (slot.ordinal() < Typ.values().length-1) {
				Typ typ = Typ.values()[slot.ordinal()+1];
				retVal = findOpenKursSlot(wunsch, cSchuel, typ, ignoreRooms, ignoreFirms);
			}
		}		
		return retVal;
	}
	
	
	/**
	 * hat die Veranstaltung schon einen Kurs neben diesem Slot? 
	 * (Luecken vermeiden)
	 * 
	 * @param slot zu pruefender Zeitslot
	 * @param unt zu pruefender Veranstaltungstyp
	 * @return true, wenn benachbart
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
		
	
	/**
	 * prueft, ob auf dem uebergebenen Zeitslot ein freier Raum verfuegbar ist
	 * 
	 * @param slotTyp Zeitslottyp
	 * @return true, wenn Raum verfuegbar ist
	 */
	private boolean freeRoom(Typ slotTyp) {
		int number = 0;
		for( Kurse kurs : kurse ) {
			if(kurs.getZeitslot().getTyp().equals(slotTyp)) {
				number++;
			}
		}
		return (number < raeume.size());		
	}
		
	/**
	 * loescht durch "swapping" eventuell leer gewordene Kurse
	 */
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
	
	/**
	 * Loescht bestehenden Kurs, indem alle Vorgaenge des Anlegens und Belegens mit Schuelern
	 * rueckabgewickelt werden.
	 * 
	 * @param kurs zu loeschender Kurs
	 */
	private void deleteCourse(Kurse kurs) {
		for(CalcSchueler cSchuel : new ArrayList<CalcSchueler>(kurs.getKursTeilnehmer())) {
			cSchuel.leaveCourse(kurs, cSchuel.getSlotByType(kurs.getZeitslot().getTyp()).getErfuellterWunsch());
		}
		kurs.getUnternehmen().getKurse().remove(kurs.getZeitslot().getTyp());
		kurse.remove(kurs);
	}
	
	
	/**
	 * sortiert Wuensche absteigend nach einem speziellen Occurrence-Merkmal. 
	 */
	Comparator<Wunsch> wunschByOccurrence = new Comparator<Wunsch>() {
		
		@Override
		public int compare(Wunsch o1, Wunsch o2) {			
			return Integer.compare(occurrence(o2), occurrence(o1));
		}
		
	};
	
	/**
	 * sortiert Kurse nach Gewichtung. <br/>
	 * Gewichtung = Teilnehmerzahl * Prioritaet des erfuellten Wunsches, absteigend
	 */
	Comparator<Kurse> kursByWeight = new Comparator<Kurse>() {
		
		@Override
		public int compare(Kurse o1, Kurse o2) {
			return Integer.compare(o2.weight(), o1.weight());
		}
	};
	
	
	/**
	 * sortiert Kurse strikt nach der Anzahl der Kursteilnehmer, aufsteigend
	 */
	Comparator<Kurse> kursByParticipants = new Comparator<Kurse>() {

		@Override
		public int compare(Kurse o2, Kurse o1) {
			return Integer.compare(o2.getKursTeilnehmer().size(), o1.getKursTeilnehmer().size());
		}
		
	};
	
	/**
	 * Berechnung eines Sortiermerkmals.
	 * Bei Occurrence werden noch unzugeteilte Bewerberwuensche fuer die Veranstaltung mit der jeweiligen Prioritaet des 
	 * Wunsches multipliziert.
	 * 
	 * @param wunsch
	 * @return
	 */
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
	
	
	/**
	 * berechnet die Gesamtzahl der belegten Zeitslots zu Debugging-Zwecken
	 * 
	 * @return Anzahl belegter Zeitslots
	 */
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
	
	
	/**
	 * gibt die Anzahl Kurse auf einem Zeitslot zurück
	 * 
	 * @param slot betreffender Zeitslot
	 * @param unternehmen wenn nicht null, dann werden nur Veranstaltungen mit demselben Unternehmensstring beruecksichtigt
	 * @return Anzahl gefundener Kurse
	 */
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
	
	
	/**
	 * prueft, ob ein Veranstaltungstyp einer von mehreren mit demselben Unternehmensstring ist
	 * 
	 * @param unt Veranstaltungstyp
	 * @return true, wenn es mehrere Veranstaltungen der gleichen Firma gibt
	 */
	private boolean hasMultiple(Unternehmen unt) {
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
	
	
	/**
	 * Methode, die eine einfache Konsolenausgabe zu Debug-Zwecken erzeugt
	 */
	private void simpleOutput(String title) {
		//nur im Debugfall Ausgabe
		if(! debug) {
			return;
		}
		
		System.out.println(title + ":\n");
		
		for (CalcSchueler cSchuel : getcSchueler()) {

			String zeile = cSchuel.getSchueler().getNachname() + " | ";

			for (SchuelerSlot slot : cSchuel.getSlots()) {
				Kurse kurs = slot.getKurs();
				if (kurs != null) {
					zeile += slot.getKurs().getUnternehmen().getFirmenID() + " | ";
				} else {
					zeile += "null |";
				}
			}

			System.out.println(zeile);
		}
		
		System.out.println();

		for (Unternehmen unt : unternehmen) {
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
		System.out.println("Score: " + calculateScore());
		System.out.println("Max Score: " + calculateMaxScore());
		System.out.println("Prozentualer Score: " + prozentScore());
		System.out.println("Belegte Slots: " + belegteSlots());
		System.out.println("\n");
	}	
	
}
