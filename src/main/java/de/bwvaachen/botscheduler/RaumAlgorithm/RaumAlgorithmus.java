package de.bwvaachen.botscheduler.RaumAlgorithm;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

	ArrayList<ArrayList<Kurse>> eingeteilteKurseListe = new ArrayList<ArrayList<Kurse>>();

	Map<Kurse, Raum> kursRaumMap = new HashMap<>();

	/**
	 * Diese Methode berrechnet, wie "gut" der Algorithmus dabei ist, die Raeume zu
	 * verteilen Rechnung: anzAlleVeranstaltungen -
	 * anzAufRaeumeZugeteilteVeranstaltungen = n%
	 */
	private void berrechneAlgorithmusEffizentz()
	{
		Double effizienz = 0.0;

		System.out.println("Raumalgorithmus Effizienz ist bei: " + effizienz + "%");
	}

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
			System.out.println("-----------Naechstes Array----------- \n ");

			zuordnungErstellen(einzelKursListe, raumListe);

			System.out.println("-----------Zuordnung erstellt----------- \n ");
		}
	}

	/**
	 * Diese Methode ist der Algorithmus.
	 * Hier wird der Passende Raum im bezug auf die Teilnehmergroesse des kurses zugeordnet.
	 * Ein Raum kann nicht doppelt zugeordnet werden, weshalb hier eine extra Liste genutzt wird.
	 * 
	 * @param slotKursListe Eine ArrayList von Kursen eines bestimmten Zeitslots
	 * @param raumListe2
	 */
	private void zuordnungErstellen(ArrayList<Kurse> slotKursListe, List<Raum> raumListe2)
	{
		List<Raum> bereitsZugeordneteRaeume = new ArrayList<>();
		int erfolgreichZugeordnet = 0;
		
		for (Kurse kurs : slotKursListe)
		{
			Raum passenderRaum = null;
			for (Raum raum : raumListe2)
			{
				if(!bereitsZugeordneteRaeume.contains(raum))
				{
					if (raum.getKapazitaet() >= kurs.getKursTeilnehmer().size())
					{
						passenderRaum = raum;
						bereitsZugeordneteRaeume.add(raum);
						erfolgreichZugeordnet++;
						System.out.println("Raum:"+raum.getName()+ " zugeteilt!!");
						break;
					}
				}else
				{
					System.out.println("Raum:"+raum.getName()+ "schon zugeteilt!!");
				}
				
			}
			if (passenderRaum != null)
			{
				kursRaumMap.put(kurs, passenderRaum);
			}
		}

		int maxZugeordnet = Math.min(slotKursListe.size(), raumListe2.size()); // Maximal mögliche Anzahl der Zuteilungen

		// Berechne den Prozentsatz der erfolgreich zugeordneten Kurse
		double score = (double) erfolgreichZugeordnet / maxZugeordnet * 100;

		System.out.println("Es wurden " + erfolgreichZugeordnet + " von max " + maxZugeordnet + " Kurse zugeordnet.");
		System.out.println("Effizienz: " + score + "%");
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

}
