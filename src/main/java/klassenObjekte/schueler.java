package klassenObjekte;

import java.util.ArrayList;

public class schueler
{
	private int schuelerID;
	private String vorname, nachname;
	private ArrayList<String> wuensche;
	private String klasse;
	
	
	
	public int getSchuelerID()
	{
		return schuelerID;
	}
	public void setSchuelerID(int schuelerID)
	{
		this.schuelerID = schuelerID;
	}
	public String getVorname()
	{
		return vorname;
	}
	public void setVorname(String vorname)
	{
		this.vorname = vorname;
	}
	public String getNachname()
	{
		return nachname;
	}
	public void setNachname(String nachname)
	{
		this.nachname = nachname;
	}
	public ArrayList<String> getWuensche()
	{
		return wuensche;
	}
	public void setWuensche(ArrayList<String> wuensche)
	{
		this.wuensche = wuensche;
	}
	public String getKlasse()
	{
		return klasse;
	}
	public void setKlasse(String klasse)
	{
		this.klasse = klasse;
	}
}
