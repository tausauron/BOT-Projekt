package klassenObjekte;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Albertz
 * Diese Klasse soll bietet das Grundgerüst, um ein Kursobjekt zu erstellen in wlechem relevante informationen zu einem Kurs gespeichert werden.
 * 
 */
public class Kurse
{

	private int raum;
	private List<Schueler> kursTeilnehmer;
	private Unternehmen unternehmen;
	private String zeitslot;
	
	
	public Kurse(int raum,ArrayList<Schueler> kursTeilnehmer, Unternehmen unternehmen, String zeitslot)
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
		if(objekt==null)
		{
			throw new IllegalArgumentException(variable+ " darf/duerfen nicht leer sein");
		}
	}

	public int getRaum()
	{
		return raum;
	}

	public void setRaum(int raum)
	{
		pruefeNullEingabe(raum, "Der Raum");
		this.raum = raum;
	}

	public List<Schueler> getKursTeilnehmer()
	{
		return kursTeilnehmer;
	}

	public void setKursTeilnehmer(List<Schueler> kursTeilnehmer)
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

	public String getZeitslot()
	{
		return zeitslot;
	}

	public void setZeitslot(String zeitslot)
	{
		pruefeNullEingabe(zeitslot, "Der Zeitslot, in welchem der Kurs stattfindet");
		this.zeitslot = zeitslot;
	}
}
