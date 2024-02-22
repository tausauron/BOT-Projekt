package klassenObjekte;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Martin Albertz
 * 
 * Diese Klasse beitet das grundgerüst für die Unternehmen. 
 * Diese Klasse erlaubt es ein Unternehmensobjekt anzulegen
 */
public class Unternehmen
{

	private String firmenName;
	private int firmenID;
	private int maxTeilnehmer;
	private List<Integer> zeitslots;
	private double gewichtung;
	private boolean aktiv;
	
	public unternehmen(String name, int firmenID, int maxTeilnehmer)
	{
		setFirmenName(name);
		setFirmenID(firmenID);
		setMaxTeilnehmer(maxTeilnehmer);
	}

	public String getFirmenName()
	{
		return firmenName;
	}

	public void setFirmenName(String firma)
	{
		this.firmenName = firma;
	}

	public int getFirmenID()
	{
		return firmenID;
	}

	public void setFirmenID(int firmenID)
	{
		this.firmenID = firmenID;
	}

	public int getMaxTeilnehmer()
	{
		return maxTeilnehmer;
	}

	public void setMaxTeilnehmer(int maxTeilnehmer)
	{
		this.maxTeilnehmer = maxTeilnehmer;
	}

	public List<Integer> getZeitslots()
	{
		return zeitslots;
	}

	public void setZeitslots(ArrayList<Integer> zeitslots)
	{
		this.zeitslots = zeitslots;
	}

	public double getGewichtung()
	{
		return gewichtung;
	}

	public void setGewichtung(double gewichtung)
	{
		this.gewichtung = gewichtung;
	}

	public boolean isAktiv()
	{
		return aktiv;
	}

	public void setAktiv(boolean aktiv)
	{
		this.aktiv = aktiv;
	}
	
	
	
	
	
	
	
	
}
