package klassenObjekte;

import java.util.List;

//Martin, Eric

public class schueler
{
	private int schuelerID;
	private String vorname, nachname;
	private List<String> wuensche;
	private String klasse;
	
	public schueler(String klasse,String vorname, String nachname,List<String> wuensche) {
		this.klasse=klasse;
		this.vorname=vorname;
		this.nachname=nachname;
		this.wuensche=wuensche;
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
	public String getWuensche(int i)
	{
		return wuensche.get(i);
	}
	public void setWuensche(List<String> wuensche)
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
