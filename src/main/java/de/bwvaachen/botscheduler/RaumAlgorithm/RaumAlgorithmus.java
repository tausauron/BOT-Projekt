package de.bwvaachen.botscheduler.RaumAlgorithm;

import java.util.ArrayList;

import klassenObjekte.Raum;
import klassenObjekte.Unternehmen;

/*
 * Der Alogrithmus sollte schauen, dass alle Räume zu einer Veranstaltung zugeordnet werden.
 * Sollte es zwei Veranstaltungen, zur selben zeit geben, die einen großen Raum benoetigen,so ist die Polizei vorranging zu beachten.
 * 
 * Der Andere Kurs muss:
 * 1. Dann an einen anderem zeitslot stattfinden.
 * 2. Einem kleineren Raum zugewiesen werden und einfach weniger Teilnehmer nehmen.
 * 3. Man kann schauen, wie viele Personen wircklich in einer veranstaltung sind und die Aufteilung anhand dessen gestalten.
 * 
 * Es gibt 5 zeitslots
 * Ein Raum hat eine Kapazität
 * Ich brauche ein Unternehmensobjekt/veranstaltungsobjekt
 * 
 * Rechung der effizens ist: alleVeranstaltungen - verteilteVeranstaltungen 
 * 
 */

/**
 * Diese Klasse wird vom Model aufgerufen.
 * @author Martin Albertz
 */
public class RaumAlgorithmus
{
	// diese Klasse hat 5 listen mit Veranstaltungen o.
	// zweidimensionales array, von veranstaltungsobjekten.
	
	private Raum raum;
	private ArrayList<Raum> raumListe;
	private ArrayList<Unternehmen> veranstaltungsListe;
	
	//Score: Anzahl aller Raeume-verteilte Raeume
	
	//getRaumliste() setze raumliste
	//setRaumliste() setze raumliste
	
	//gibt eine liste aus, in der eine veranstaltung/Kurs einen Raum zugeordnet bekommen hat.
	
	/**
	 * Diese Methode bekommt eine Liste mit Raeumen und eine Liste mit Veranstaltungen/Unternehmen
	 * 
	 * @param raumListe
	 * @param unternehmensListe
	 */
	public void gebeListen(ArrayList<Raum> raumListe, ArrayList<Unternehmen> unternehmensListe)
	{
		setRaumListe(raumListe);
		setVeranstaltungsListe(unternehmensListe);
	}
	
	/**
	 * Diese Methode berrechnet, wie "gut" der Algorithmus dabei ist, die Raeume zu verteilen
	 * Rechnung: anzAlleVeranstaltungen - anzAufRaeumeZugeteilteVeranstaltungen = n%
	 */
	private void berrechneAlgorithmusEffizentz()
	{
		Double effizienz = 0.0;
		
		System.out.println("Raumalgorithmus Effizienz ist bei: "+effizienz+"%");
	}

	public Raum getRaum()
	{
		return raum;
	}

	public void setRaum(Raum raum)
	{
		this.raum = raum;
	}

	public ArrayList<Raum> getRaumListe()
	{
		return raumListe;
	}

	public void setRaumListe(ArrayList<Raum> raumListe)
	{
		this.raumListe = raumListe;
	}

	public ArrayList<Unternehmen> getVeranstaltungsListe()
	{
		return veranstaltungsListe;
	}

	public void setVeranstaltungsListe(ArrayList<Unternehmen> veranstaltungsListe)
	{
		this.veranstaltungsListe = veranstaltungsListe;
	}
	
	
}
