package klassenObjekte;

import java.util.List;

/**
 * @author Martin Albertz
 * Diese Klasse beitet das grundgerüst für die Schueler. 
 * Diese Klasse erlaubt es einen Schueler anzulegen.
 * 
 * TODO: Die individuellen if zeilen in den setters, durch eine gemeinsame Methode austauschen
 */
public class Schueler
{
	private int schuelerID;
	private String vorname, nachname;
	private List<String> wuensche;
	private String klasse;


	public Schueler(String klasse,String vorname, String nachname,List<String> wuensche)
	{
		setKlasse(klasse);
		setVorname(vorname);
		setNachname(nachname);
		setAllWuensche(wuensche);
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
		if(vorname==null)
		{
			throw new IllegalArgumentException("Vorname darf nicht leer sein");
		}else
		{
			this.vorname = vorname;
		}

	}
	public String getNachname()
	{
		return nachname;
	}
	public void setNachname(String nachname)
	{
		if(nachname==null)
		{
			throw new IllegalArgumentException("Nachname darf nicht leer sein");
		}else
		{
			this.nachname = nachname;
		}
	}
	public List<String> getAllWuensche()
	{
		return wuensche;
	}

	public String getWunsch(int i)
	{
		if(i>this.wuensche.size())
		{
			throw new IllegalArgumentException("Eingegebener Wert ist außerhalb der Werte");
		}else
		{
			return wuensche.get(i);
		}
	}

	public void setAllWuensche(List<String> list)
	{
		if(list==null)
		{
			throw new IllegalArgumentException("Wünsche darf nicht leer sein");
		}else
		{
			this.wuensche = list;
		}
	}
	public String getKlasse()
	{
		return klasse;
	}
	public void setKlasse(String klasse)
	{
		if(klasse==null)
		{
			throw new IllegalArgumentException("Klasse darf nicht leer sein");
		}else
		{
			this.klasse = klasse;
		}
	}

	@Override
	public String toString() {
		return "Schueler{" +
				"schuelerID=" + schuelerID +
				", vorname='" + vorname + '\'' +
				", nachname='" + nachname + '\'' +
				", wuensche=" + wuensche +
				", klasse='" + klasse + '\'' +
				'}';
	}
}
