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
	private List<schueler> kursTeilnehmer;
	private unternehmen unternehmen;
	
	
	public kurse(int raum,ArrayList<schueler> kursTeilnehmer, unternehmen unternehmen)
	{
		setRaum(raum);
		setKursTeilnehmer(kursTeilnehmer);
		setUnternehmen(unternehmen);
	}
	
	public int getRaum()
	{
		return raum;
	}
	public void setRaum(int raum)
	{
		this.raum = raum;
	}
	public List<schueler> getKursTeilnehmer()
	{
		return kursTeilnehmer;
	}
	public void setKursTeilnehmer(ArrayList<schueler> kursTeilnehmer)
	{
		this.kursTeilnehmer = kursTeilnehmer;
	}
	public unternehmen getUnternehmen()
	{
		return unternehmen;
	}
	public void setUnternehmen(unternehmen unternehmen)
	{
		this.unternehmen = unternehmen;
	}
}
