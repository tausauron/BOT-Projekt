package de.bwvaachen.botscheduler.RaumAlgorithm;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.bwvaachen.botscheduler.calculate.Zeitslot.Typ;
import klassenObjekte.Kurse;
import klassenObjekte.Raum;
import klassenObjekte.Unternehmen;

/*
 * Der Alogrithmus sollte schauen, dass alle Räume zu einen Kurs zugeordnet werden.
 * Sollte es zwei Veranstaltungen, zur selben zeit geben, die einen großen Raum benoetigen,so ist die Polizei vorranging zu beachten.
 * 
 * Der Andere Kurs muss:
 * 
 * 3. Man kann schauen, wie viele Personen wircklich in einer veranstaltung sind und die Aufteilung anhand dessen gestalten. <--
 * 
 * Es gibt 5 zeitslots
 * Ein Raum hat eine Kapazität
 * Ich brauche ein Unternehmensobjekt/veranstaltungsobjekt
 * 
 * Rechung der effizens ist: alleVeranstaltungen - verteilteVeranstaltungen 
 * ich muss die kursliste benutzen indem ich planner.getKurse aufrufe, den die kurse besitzen die infos welche teinlehmer die 
 * haben und so. DAS brauche ich eigentlich.
 */

/**
 * Diese Klasse wird vom Model aufgerufen.
 * 
 * @author Martin Albertz
 */
public class RaumAlgorithmus
{

	private List<Raum> raumListe;
	private List<Kurse> kursListe; // hier stehen die infos drinne, zu einen einzelnen Kurs

	private ArrayList<ArrayList<Kurse>> eingeteilteKurseListe = new ArrayList<ArrayList<Kurse>>();

	private Map<Kurse, Raum> kursRaumMap = new HashMap<>();
	private Map<Kurse, String> nichtZuordbareKurseMap = new HashMap<>();

	Comparator<Kurse> compareByKurse = new Comparator<Kurse>() {
		@Override
		public int compare(Kurse o1, Kurse o2)
		{
			return Integer.compare(o1.getKursTeilnehmer().size(), o2.getKursTeilnehmer().size());
		}
	};

	/**
	 * Diese Methode ist der eigentliche Algorithmus und verteilt die Veranstaltung
	 * anhand ihrer zeitslots auf die noch freien Raeume. 1. packe die
	 * unternehmen/veranstaltungen in ein zweidimensionales array anhand ihrer
	 * Zeitslots. 2. Suche die veranstaltung mit den meisten personen. 3. ordne
	 * freien raum zu, wenn dieser frei ist und genug personen hat.
	 * 
	 */
	public void verteileVeranstaltungenAufRaeume(List<Kurse> kursListe, List<Raum> raumListe)
	{
		setKursListe(kursListe); // Setzt die kurse
		setRaumListe(raumListe); // Setzt die raumliste
		verteileRaeume();
	}

	/**
	 * Diese Methode fuegt 5 ArrayListen, zu dem zweidimensionalen Array hinzu Es
	 * wird der KursTyp aller Kurse in der KursListe erkannt und diese werden
	 * dementsprechend in die 5 Arraylisten gespeichert. Mithilfe der nun
	 * eingeteiltenKurse wird die Raum zu Veranstaltung zuordnung erstellt.
	 */
	private void verteileRaeume()
	{
		// eine ArrayList pro Zeitslot anlegen. (5 Stueck)
		for (int i = 0; i < 5; i++)
		{
			eingeteilteKurseListe.add(new ArrayList<Kurse>());
		}

		for (Kurse einzelkurs : kursListe) // fuer jeden Kurs in meiner Kursliste, erkenne den Kurstyp(A,B,C,D,E) und
		{
			erkenneKurstyp(einzelkurs, eingeteilteKurseListe); // erkennt welcher typ der einzelKurs ist und packt es in
			// die liste entsprechend
		}
		teileRaumVeranstaltungZu(eingeteilteKurseListe);
	}

	/**
	 * Diese Methode ist der Kern des Algorithmus und sorgt dafuer, dass die Kurse
	 * auf die verfuegbaren Raeume aufgeteilt werden.
	 * 
	 * Diese Methode bekommt die EingeteilteKurseListe, diese enthaelt 5
	 * Arraylisten, welche fuer je ein Zeitslot steht bsp:
	 * eingeteilteKurseliste.get(0) wuerde eine liste zurueckgeben mit allen Kursen,
	 * die in Zeitslot/Kurstyp A sind.
	 * 
	 * @param eingeteilteKurseListe
	 */
	private void teileRaumVeranstaltungZu(ArrayList<ArrayList<Kurse>> eingeteilteKurseListe)
	{
		for (ArrayList<Kurse> einzelKursListe : eingeteilteKurseListe) // eines der 5 dinger
		{
			System.out.println("-----------Naechster Zeitslot----------- \n ");

			zuordnungErstellen(einzelKursListe, raumListe);
		}
		System.out.println(
				"----------------------------------------------------------------Zuordnung ende-------------------------------------------");
	}

	/**
	 * Diese Methode ist der Algorithmus. Hier wird der Passende Raum im bezug auf
	 * die Teilnehmeranzahl des Kurses zugeordnet. Ein Raum kann nicht doppelt
	 * zugeordnet werden, weshalb hier eine extra Liste genutzt wird.
	 * 
	 * Der Algorithmus geht durch die RaumListe und wenn der aktuell betrachtete
	 * Raum kapazitaetzmaessig passt, dann wird dieser in die Map gepackt. Sollten
	 * alle Kurse so zugeordnet sein, kann es sein, das Raeume wie die Aula nicht
	 * zugeordnet wurden, weil alle Kurse schon einen Raum bekommen haben, bevor die
	 * position der Aula ueberhaupt erreicht wurde. AKA Wir haben mehr raeume als
	 * Kurse und es wurden nicht alle Raeume gebraucht.
	 * 
	 * @param slotKursListe Eine ArrayList von Kursen eines bestimmten Zeitslots
	 * @param raumListe2
	 */
	private void zuordnungErstellen(ArrayList<Kurse> slotKursListe, List<Raum> raumListe2)
	{
		sortierKurseNachTeilnehmerZahl(slotKursListe);

		List<Raum> bereitsZugeordneteRaeume = new ArrayList<>();
		int erfolgreichZugeordnet = 0;

		for (Kurse kurs : slotKursListe)
		{
			System.out.println("Betrachteter Kurs: " + kurs.getUnternehmen());
			Raum passenderRaum = null;

			if (bereitsZugeordneteRaeume.size() > raumListe2.size()) // Wenn bei aktuellem Kurs, die
			// bereitsZugeordneteRaeume liste = raumliste
			// groß
			{
				System.out.println("Kurs wird anders gespeichert");
				kursGehoertInNichtZuOrdbareListe(kurs, "Es ist kein freier Raum mehr verfuegbar", true);
			} else
			{
				// versucheGleichenRaumZuzuordnen(kurs, slotKursListe);

				for (Raum raum : raumListe2)
				{
					System.out.println("\n Betrachteter Raum ist:" + raum.getName());
					if (!bereitsZugeordneteRaeume.contains(raum)) // wenn raum nicht schon zugeteilt
					{
						if (raum.getKapazitaet() >= kurs.getKursTeilnehmer().size())
						{
							passenderRaum = raum;
							bereitsZugeordneteRaeume.add(raum);
							erfolgreichZugeordnet++;
							kursGehoertInNichtZuOrdbareListe(kurs, "", false);
							System.out.println("Raum: " + raum.getName() + " zugeteilt!!");
							break;
						} else // else Raum hat nicht passende Kapazitaet
						{
							kursGehoertInNichtZuOrdbareListe(kurs,
									"Fuer diesen Kurs, gibt es keinen Kurs mit passender Kapazitaet", true);
							System.out.println("Raum: " + raum.getName() + " keine Kapazitaet!! " + raum.getKapazitaet()
									+ " VS " + kurs.getKursTeilnehmer().size());

						}
					} else // else Raum ist schon Zugeteilt worden
					{
						System.out.println("Raum: " + raum.getName() + " schon zugeteilt!!");
					}
				}
			}

			if (passenderRaum != null)
			{
				kursRaumMap.put(kurs, passenderRaum);
				kurs.setRaum(passenderRaum);
			}

			System.out.println("-------------------------- \n");
		}

		int maxZugeordnet = Math.min(slotKursListe.size(), raumListe2.size()); // Maximal mögliche Anzahl der
		// Zuteilungen

		// Berechne den Prozentsatz der erfolgreich zugeordneten Kurse
		double score = (double) erfolgreichZugeordnet / maxZugeordnet * 100;

		System.out.println("Es wurden " + erfolgreichZugeordnet + " von max " + maxZugeordnet + " Kurse zugeordnet.");
		System.out.println("Effizienz: " + score + "%");
	}

	/**
	 * Diese Methode schaut, ob der Aktuelle Kurs im Vorherigen Zeitslot existiert,
	 * wenn ja, dann wirde versucht, diesem, der Gleiche Raum zuzuordnen, wie im
	 * vorherigem Zeitslot.
	 * 
	 * @param kurs
	 * @param slotKursListe
	 */
	private void versucheGleichenRaumZuzuordnen(Kurse kurs, ArrayList<Kurse> aktuelleZeitSlotListe)
	{
		for (int i = 0; i < eingeteilteKurseListe.size(); i++)
		{
			if (eingeteilteKurseListe.get(i).equals(aktuelleZeitSlotListe))
			{
				// i ist nun die pos an welcher stelle das aktuelle zeitslot ist
				if (i == 0)
				{
					// nichts
				} else
				{
					// checke ob aktueller kurs in vorherigen array ist
					if ((eingeteilteKurseListe.get(i - 1)).contains(kurs))
					{
						// versuche den raum vom letztenmal nochmal zuzuordnen
					}

				}
			}
		}
	}

	/**
	 * Sortiert eine Kursliste anhand der Teilnehmerzahl. Standart ist eine
	 * Aufsteigende Sortierung, deshalb wird die liste reversed.
	 * 
	 * @param slotKursListe
	 */
	private void sortierKurseNachTeilnehmerZahl(ArrayList<Kurse> slotKursListe)
	{
		Collections.sort(slotKursListe, compareByKurse);
		Collections.reverse(slotKursListe);
	}

	/**
	 * Hier werden Kurse, die keinen Raum bekommen konnten gespeichert.
	 * 
	 * 
	 * @param kurs
	 * @param grund
	 * @param wert
	 */
	private void kursGehoertInNichtZuOrdbareListe(Kurse kurs, String grund, boolean wert)
	{
		if (wert == true)
		{
			nichtZuordbareKurseMap.put(kurs, grund);
		} else
		{
			// Andernfalls entferne den Kurs aus der Zuordnung zum Grund
			nichtZuordbareKurseMap.remove(kurs);
		}
	}

	/**
	 * Diese Methode bekommt einen Kurs uebergeben und Ordnet diesen in die
	 * zweidimensionale Arraylist ein
	 * 
	 * Beispiel: Ist der Kurstyp "A" so wird der Kurs in die erste Arraylist
	 * gepackt. Ist der Kurstyp "B" so wird der Kurs in die zweite Arraylist
	 * gepackt.
	 * 
	 * Notiz: Hier muss die modKursListe benutzt werden, da ich waehrend ich ueber
	 * die kursListeiteriere diese liste nicht veraendern kann. Stadttdessen wird im
	 * modKursListe das entsprechende objekt geloescht.
	 * 
	 * @param kurs
	 * @param eingeteilteKurseListe
	 */
	private void erkenneKurstyp(Kurse kurs, ArrayList<ArrayList<Kurse>> eingeteilteKurseListe)
	{
		if (kurs.getZeitslot().getTyp() == Typ.A)
		{
			// Füge zu Array pos 0 hinzu
			eingeteilteKurseListe.get(0).add(kurs);
		}
		if (kurs.getZeitslot().getTyp() == Typ.B)
		{
			// Füge zu Array pos 1 hinzu
			eingeteilteKurseListe.get(1).add(kurs);
		}
		if (kurs.getZeitslot().getTyp() == Typ.C)
		{
			// Füge zu Array pos 2 hinzu
			eingeteilteKurseListe.get(2).add(kurs);
		}
		if (kurs.getZeitslot().getTyp() == Typ.D)
		{
			// Füge zu Array pos 3 hinzu
			eingeteilteKurseListe.get(3).add(kurs);
		}
		if (kurs.getZeitslot().getTyp() == Typ.E)
		{
			// Füge zu Array pos 4 hinzu
			eingeteilteKurseListe.get(4).add(kurs);
		}
	}

	private void pruefeObMehrKurseAlsRaeumeGibt(ArrayList<ArrayList<Kurse>> eingeteilteKurseListe2,
			List<Raum> raumListe2) throws Exception
	{
		if (eingeteilteKurseListe2.size() >= raumListe2.size())
		{
			throw new Exception("Es gibt mehr Kurse in einem Zeitslot als es Raeume gibt!");
		}
	}

	public List<Raum> getRaumListe()
	{
		return raumListe;
	}

	public void setRaumListe(List<Raum> raumListe)
	{
		this.raumListe = raumListe;
	}

	public List<Kurse> getKursListe()
	{
		return kursListe;
	}

	public void setKursListe(List<Kurse> kursListe)
	{
		this.kursListe = kursListe;
	}

	public Map<Kurse, String> getNichtZuordbareKurseMap()
	{
		return nichtZuordbareKurseMap;
	}

	public void setNichtZuordbareKurseMap(Map<Kurse, String> nichtZuordbareKurseMap)
	{
		this.nichtZuordbareKurseMap = nichtZuordbareKurseMap;
	}
}
