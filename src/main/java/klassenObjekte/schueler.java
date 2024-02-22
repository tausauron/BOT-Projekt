package klassenObjekte;

import java.util.ArrayList;
import java.util.List;

public class schueler
{
	private int schuelerID;
	private String vorname, nachname;
	private List<String> wuensche;
	private String klasse;

	public schueler(String klasse, String vorname, String nachname, List<String> wunschListe) {
	this.klasse=klasse;
	this.vorname=vorname;
	this.nachname=nachname;
	this.wuensche=wunschListe;
	}


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
	public List<String> getWuensche()
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
