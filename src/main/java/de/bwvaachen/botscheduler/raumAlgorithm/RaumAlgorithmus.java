package de.bwvaachen.botscheduler.raumAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.bwvaachen.botscheduler.calculate.Zeitslot.Typ;
import klassenObjekte.Kurse;
import klassenObjekte.Raum;


/**
 * Diese Klasse sorgt fuer den Raumalgorithmus.
 * Die Klasse Verwendet die Kurs und Raumliste, um einen Raum einem Kurs zuzuordnen.
 * 
 * Das Ergebniss des Algorithmus ist, dass jeder Kurs/Veranstaltungurs einen Raum zugeorndet hat. 
 * Hierbei wird darauf geachtet, dass der Raum derselbe bleiben soll, wenn moeglich.
 * 
 * Mithilfe der "nichtZuordbareKurseMap" Map , welche durch ".getnichtZuordbareKurseMap()" 
 * geholt werden kann, ist es moeglich zu sehen, welcher Kurs aus welchem Grund keinen Raum zugeordnet bekommen hat.
 * 
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
	private List<Kurse> schonStattgefundeneKurse;

	private Comparator<Kurse> compareByKurse = new Comparator<Kurse>()
	{
		@Override
		public int compare(Kurse o1, Kurse o2)
		{
			return Integer.compare(o1.getKursTeilnehmer().size(), o2.getKursTeilnehmer().size());
		}
	};

	private Comparator<Kurse> compareByStattgefunden = new Comparator<Kurse>()
	{
		@Override
		public int compare(Kurse o1, Kurse o2)
		{
			boolean isInHigherRanked1 = schonStattgefundeneKurse.contains(o1);
			boolean isInHigherRanked2 = schonStattgefundeneKurse.contains(o2);

			// Wenn o1 in higherRankedList enthalten ist und o2 nicht, o1 ist "kleiner"
			if (isInHigherRanked1 && !isInHigherRanked2)
			{
				return -1;
			}
			// Wenn o2 in higherRankedList enthalten ist und o1 nicht, o2 ist "kleiner"
			else if (!isInHigherRanked1 && isInHigherRanked2)
			{
				return 1;
			}
			// Ansonsten behalte die Reihenfolge bei
			else
			{
				return 0;
			}
		}
	};

	/**
	 * Diese Methode ist der eigentliche Algorithmus und verteilt die Veranstaltung
	 * anhand ihrer Zeitslots auf die noch freien Raeume. 
	 * Der Algorithmus geht dabei wie folgt vor:
	 * 1. Erkenne wann welcher kurs stadttfindet und Speichere diese in einer
	 *  zweidimensionalen Liste.
	 * 2. Die Kurse/Veranstaltungen, die in einem Zeitslot stadttfinden
	 * werden zuerst nach der Groesse der Teilnehmerzahlen sortiert.
	 * Anschliessend werden die Kurse/Veranstaltungen, welche schon an dem
	 * Zeitslot davor stattgefunden haben, an den Anfang der Liste geschoben.
	 * 3. Ordnet den Kursen/Veranstaltungen eine freien Raum zu.
	 * Wenn moeglich soll der gleiche Raum zugeordnet werden, der dem Kurs/Veranstaltung
	 * im Vorherigem Zeitslot zugeordnet wurde. 
	 * 
	 * @param kursListe, die Liste mit den Einzelnen kursen.
	 * @param raumListe, die Liste mit den Raeumen.
	 */
	public void verteileVeranstaltungenAufRaeume(List<Kurse> kursListe, List<Raum> raumListe)
	{
		setKursListe(kursListe); // Setzt die kurse
		setRaumListe(raumListe); // Setzt die raumliste
		verteileRaeume();
	}

	/**
	 * Diese Methode fuegt 5 ArrayListen, zu dem zweidimensionalen Array hinzu.
	 * Es wird der KursTyp aller Kurse in der KursListe erkannt und diese werden
	 * dementsprechend in die 5 Arraylisten gespeichert. 
	 * Durch die nun festgelegten Kurse wird die Zuordnung von Raeumen zu Veranstaltungen erstellt.
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
			zuordnungErstellen(einzelKursListe, raumListe);
		}
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
		sortierKurseNachObSchonmalPassiert(slotKursListe);

		List<Raum> bereitsZugeordneteRaeume = new ArrayList<>();
		int erfolgreichZugeordnet = 0;

		for (Kurse aktBetrachteterKurs : slotKursListe)
		{
			Raum passenderRaum = null;
			Raum vorherigerRaum = null;

			if (bereitsZugeordneteRaeume.size() >= raumListe2.size())
			{
				kursGehoertInNichtZuOrdbareListe(aktBetrachteterKurs, "Es ist kein freier Raum mehr verfuegbar", true);
			} else
			{
				vorherigerRaum = versucheGleichenRaumZuzuordnen(aktBetrachteterKurs, slotKursListe); // gibt mir ein
				Kurse dieserKursHatDenBenoetigtenRaum = null;
				Raum freierRaum = null;

				if (vorherigerRaum != null)
				{
					for (Kurse temp : slotKursListe)
					{
						if (temp.getRaum() != null)
						{
							// suche den kurs der den gleichen raum hat wie mein vorheriger raum
							if ((temp.getRaum().getName()).equalsIgnoreCase(vorherigerRaum.getName()))
							{
								dieserKursHatDenBenoetigtenRaum = temp;
								break;
							}
						}
					}

					// nehme einen freien raum!
					for (Raum frei : raumListe2)
					{
						if (!bereitsZugeordneteRaeume.contains(frei))
						{
							freierRaum = frei;
							break;
						}
					}

					if (freierRaum != null && dieserKursHatDenBenoetigtenRaum != null)
					{
						if (dieserKursHatDenBenoetigtenRaum.getKursTeilnehmer().size() <= freierRaum.getKapazitaet())
						{
							// checke ob aktueller Kurs genug kap hat
							if (aktBetrachteterKurs.getKursTeilnehmer().size() <= dieserKursHatDenBenoetigtenRaum
									.getRaum().getKapazitaet())
							{
								aktBetrachteterKurs.setRaum(dieserKursHatDenBenoetigtenRaum.getRaum());
								kursRaumMap.put(aktBetrachteterKurs, dieserKursHatDenBenoetigtenRaum.getRaum());
								dieserKursHatDenBenoetigtenRaum.setRaum(freierRaum);
								kursRaumMap.replace(dieserKursHatDenBenoetigtenRaum, freierRaum);
								bereitsZugeordneteRaeume.add(freierRaum);
								erfolgreichZugeordnet++;
								kursGehoertInNichtZuOrdbareListe(aktBetrachteterKurs, "", false);
							}
						}
					}
				
					if(dieserKursHatDenBenoetigtenRaum == null)
					{
						aktBetrachteterKurs.setRaum(vorherigerRaum);
						bereitsZugeordneteRaeume.add(vorherigerRaum);
						kursRaumMap.put(aktBetrachteterKurs, vorherigerRaum);
						erfolgreichZugeordnet++;
					}
					vorherigerRaum = null;
				}

				for (int i = 0; i < raumListe2.size(); i++)
				{
					if(aktBetrachteterKurs.getRaum()!=null)
					{break;}
					
					Raum aktBetrachteteRaum = raumListe2.get(i);

					// wenn beim aktuellen Kurs, es schon einen vorherigem Raum gab

					if(vorherigerRaum!=null)
					{
						i=i-1;
						aktBetrachteteRaum= vorherigerRaum;
						vorherigerRaum= null;
					}
					if (!bereitsZugeordneteRaeume.contains(aktBetrachteteRaum)) // wenn raum nicht schon zugeteilt
					{
						if (aktBetrachteteRaum.getKapazitaet() >= aktBetrachteterKurs.getKursTeilnehmer().size())
						{
							passenderRaum = aktBetrachteteRaum;
							bereitsZugeordneteRaeume.add(aktBetrachteteRaum);
							erfolgreichZugeordnet++;
							aktBetrachteterKurs.setRaum(passenderRaum);
							kursRaumMap.put(aktBetrachteterKurs, passenderRaum);
							kursGehoertInNichtZuOrdbareListe(aktBetrachteterKurs, "", false);
							break;
						} else // else Raum hat nicht passende Kapazitaet
						{
							kursGehoertInNichtZuOrdbareListe(aktBetrachteterKurs,
									"Fuer diesen Kurs, gibt es keinen Raum mit passender Kapazitaet", true);
						}
					} // else ist der Raum schon zugeteilt worden.
				}
			}
		}

		int maxZugeordnet = Math.min(slotKursListe.size(), raumListe2.size()); // Maximal moegliche Anzahl der
		// Zuteilungen

		// Berechne den Prozentsatz der erfolgreich zugeordneten Kurse
		double score = (double) erfolgreichZugeordnet / maxZugeordnet * 100;

		//System.out.println("Es wurden " + erfolgreichZugeordnet + " von max " + maxZugeordnet + " Kurse zugeordnet.");
		//System.out.println("Effizienz: " + score + "%");
	}

	/**
	 * Diese Methode schaut, ob der Aktuelle Kursveranstalter also das Unternehmen,
	 * schon einen Zeitslot frueher einen Kurs/Veranstaltung hat.
	 * ist das der Fall, so wird sich gemerkt in welchem Raum dieser stattfindet.
	 * 
	 * @param kurs
	 * @param slotKursListe
	 */
	private Raum versucheGleichenRaumZuzuordnen(Kurse kurs, ArrayList<Kurse> aktuelleZeitSlotListe)
	{
		int indexVonAktuellenZeitslot = 0;
		List<Kurse> vorherigerZeitslot;
		Raum alterRaum = null;

		indexVonAktuellenZeitslot = eingeteilteKurseListe.indexOf(aktuelleZeitSlotListe);

		if (indexVonAktuellenZeitslot != 0) // wenn nicht der erste zeitslot
		{
			vorherigerZeitslot = (eingeteilteKurseListe.get(indexVonAktuellenZeitslot - 1));

			for (Kurse element : vorherigerZeitslot)
			{
				if (element.getUnternehmen() == kurs.getUnternehmen())
				{
					alterRaum = element.getRaum();
				}
			}
		}
		return alterRaum;
	}

	/**
	 * Sortiert eine Kursliste anhand der Teilnehmerzahl. 
	 * Standartmaessig ist die Sortierung Aufsteigend, 
	 * weshalb die Kursliste noch zusaetzlich invertiert wird.
	 * 
	 * @param slotKursListe
	 */
	private void sortierKurseNachTeilnehmerZahl(ArrayList<Kurse> slotKursListe)
	{
		Collections.sort(slotKursListe, compareByKurse);
		Collections.reverse(slotKursListe);
	}

	/**
	 * Sortiert die Kursliste so, dass Kurse, 
	 * die bereits stattgefunden haben, in der Liste zuerst auftauchen.
	 * 
	 * Die Sortierung erfolgt aus dem Grund, dass dadurch diese Kurse/Veranstaltungen 
	 * priorisiert werden, um eine bessere Chance zu haben, 
	 * den gleichen Raum wie im vorherigen Zeitfenster zu erhalten.
	 * 
	 * @param slotKursListe
	 */
	private void sortierKurseNachObSchonmalPassiert(ArrayList<Kurse> slotKursListe)
	{
		int indexVonAktuellenZeitslot = 0;
		List<Kurse> vorherigerZeitslot;
		schonStattgefundeneKurse = new ArrayList<Kurse>();

		indexVonAktuellenZeitslot = eingeteilteKurseListe.indexOf(slotKursListe);

		if (indexVonAktuellenZeitslot != 0) // wenn nicht der erste zeitslot
		{
			vorherigerZeitslot = (eingeteilteKurseListe.get(indexVonAktuellenZeitslot - 1));

			for (Kurse element : vorherigerZeitslot)
			{
				for (Kurse aktZeitSlot : slotKursListe)
				{
					// Wenn ein kurs im aktuellen zeitslot, schonmal im vorherigem zeitslot war
					if (aktZeitSlot.getUnternehmen() == element.getUnternehmen())
					{
						schonStattgefundeneKurse.add(aktZeitSlot);
					}
				}
			}
		}
		Collections.sort(slotKursListe, compareByStattgefunden);
	}

	/**
	 * Hier werden Kurse, die keinen Raum bekommen konnten gespeichert.
	 * Anfangs wird ein kurs als nicht zuordbar betrachet bis dieser zugeorndet werden kann.
	 * 
	 * Die Map, in welcher ein nicht zuordbarer kurs gespeichert wird, enthaelt Informationen darueber 
	 * welche Kurse/veranstaltungen keinen Raum zugeordnet bekommen hat Inklusive des Grundes.
	 * 
	 * die Map kann durch .getNichtZuordbareKurseMap() geholt werden.
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
	 * Beispiel: 
	 * Ist der Kurstyp "A" so wird der Kurs in die erste Arraylist gepackt. 
	 * Ist der Kurstyp "B" so wird der Kurs in die zweite Arraylist gepackt.
	 * 
	 * 
	 * @param kurs, der Kurs fuer den der Zeitslot ermittelt werden soll.
	 * @param eingeteilteKurseListe, die zweidimensionale Liste welche die Zeitslots darstellt
	 */
	private void erkenneKurstyp(Kurse kurs, ArrayList<ArrayList<Kurse>> eingeteilteKurseListe)
	{
		if (kurs.getZeitslot().getTyp() == Typ.A)
		{
			// Fuege zu Array pos 0 hinzu
			eingeteilteKurseListe.get(0).add(kurs);
		}
		if (kurs.getZeitslot().getTyp() == Typ.B)
		{
			// Fuege zu Array pos 1 hinzu
			eingeteilteKurseListe.get(1).add(kurs);
		}
		if (kurs.getZeitslot().getTyp() == Typ.C)
		{
			// Fuege zu Array pos 2 hinzu
			eingeteilteKurseListe.get(2).add(kurs);
		}
		if (kurs.getZeitslot().getTyp() == Typ.D)
		{
			// Fuege zu Array pos 3 hinzu
			eingeteilteKurseListe.get(3).add(kurs);
		}
		if (kurs.getZeitslot().getTyp() == Typ.E)
		{
			// Fuege zu Array pos 4 hinzu
			eingeteilteKurseListe.get(4).add(kurs);
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
