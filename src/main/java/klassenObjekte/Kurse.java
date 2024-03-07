package klassenObjekte;

import java.util.ArrayList;
import java.util.List;

import de.bwvaachen.botscheduler.calculate.CalcSchueler;
import de.bwvaachen.botscheduler.calculate.Zeitslot;

/**
 * @author Martin Albertz
 * Diese Klasse soll bietet das Grundgerüst, um ein Kursobjekt zu erstellen in wlechem relevante informationen zu einem Kurs gespeichert werden.
 * 
 */
public class Kurse
{

	private Raum raum;
	private List<CalcSchueler> kursTeilnehmer;
	private Unternehmen unternehmen;
	private Zeitslot zeitslot;
	
	
	public Kurse(Raum raum,ArrayList<CalcSchueler> kursTeilnehmer, Unternehmen unternehmen, Zeitslot zeitslot)
	{
		setRaum(raum);
		setKursTeilnehmer(kursTeilnehmer);
		setUnternehmen(unternehmen);
		setZeitslot(zeitslot);
	}
	
	/**
	 * @author Martin Albertz
	 * 
	 * Diese Methode nimmt ein Objekt und ein String an und prüft ob dieses Objekt null ist, ist dies der Fall so wird eine Exception geworfen.
	 * 
	 * @param objekt das zu prüfende Objekt.
	 * @param variable welches Objekt geprüft wurde.
	 */
	private void pruefeNullEingabe(Object objekt, String variable)
	{

	}

	public Raum getRaum()
	{
		return raum;
	}

	public void setRaum(Raum raum)
	{
		pruefeNullEingabe(raum, "Der Raum");
		this.raum = raum;
	}

	public List<CalcSchueler> getKursTeilnehmer()
	{
		return kursTeilnehmer;
	}

	public void setKursTeilnehmer(List<CalcSchueler> kursTeilnehmer)
	{
		pruefeNullEingabe(kursTeilnehmer, "Die Kurs Teilnehmer");
		this.kursTeilnehmer = kursTeilnehmer;
	}

	public Unternehmen getUnternehmen()
	{
		return unternehmen;
	}

	public void setUnternehmen(Unternehmen unternehmen)
	{
		pruefeNullEingabe(unternehmen, "Das Unternehmen");
		this.unternehmen = unternehmen;
	}

	public Zeitslot getZeitslot()
	{
		return zeitslot;
	}

	public void setZeitslot(Zeitslot zeitslot)
	{
		pruefeNullEingabe(zeitslot, "Der Zeitslot, in welchem der Kurs stattfindet");
		this.zeitslot = zeitslot;
	}
}
