package klassenObjekte;

/**
 * 
 * @author Martin Albertz
 * 
 * Diese Klasse beitet das grundgerüst für die Unternehmen. 
 * Diese Klasse erlaubt es ein Unternehmensobjekt anzulegen
 */
public class Unternehmen
{

	private int firmenID; // ist die Firmen Nr
	private String unternehmen; 
	private String fachrichtung;
	private int maxTeilnehmer, maxVeranstaltungen;
	private String fruehsterZeitslot;
	private double gewichtung;
	private boolean aktiv;
	
	public Unternehmen(int firmenID,String unternehmen, String fachrichtung, 
						int maxTeilnehmer, int maxVeranstaltungen, String fruehsterZeitslot)
	{
		setFirmenID(firmenID);
		setUnternehmen(unternehmen);
		setFachrichtung(fachrichtung);
		setMaxTeilnehmer(maxTeilnehmer);
		setMaxVeranstaltungen(maxVeranstaltungen);
		setFruesterZeitslot(fruehsterZeitslot);
	}

	public int getFirmenID()
	{
		return firmenID;
	}

	public void setFirmenID(int firmenID)
	{
		pruefeNullEingabe(firmenID, "Die Firmen nummer");
		this.firmenID = firmenID;
	}

	public String getUnternehmen()
	{
		return unternehmen;
	}

	public void setUnternehmen(String firmenName)
	{
		pruefeNullEingabe(firmenName, "Das Unternehmen");
		this.unternehmen = firmenName;
	}

	public String getFachrichtung()
	{
		return fachrichtung;
	}

	public void setFachrichtung(String fachrichtung)
	{
		this.fachrichtung = fachrichtung;
	}

	public int getMaxTeilnehmer()
	{
		return maxTeilnehmer;
	}

	public void setMaxTeilnehmer(int maxTeilnehmer)
	{
		pruefeNullEingabe(maxTeilnehmer, "Die maximalen Teilnehmer");
		this.maxTeilnehmer = maxTeilnehmer;
	}

	public int getMaxVeranstaltungen()
	{
		return maxVeranstaltungen;
	}

	public void setMaxVeranstaltungen(int maxVeranstaltungen)
	{
		pruefeNullEingabe(maxVeranstaltungen, "Die maximale Veranstaltungen");
		this.maxVeranstaltungen = maxVeranstaltungen;
	}

	public String getFruesterZeitslot()
	{
		return fruehsterZeitslot;
	}

	public void setFruesterZeitslot(String fruehsterZeitslot)
	{
		pruefeNullEingabe(fruehsterZeitslot, "Der fruehste Zeitslot");
		this.fruehsterZeitslot = fruehsterZeitslot;
	}

	public double getGewichtung()
	{
		return gewichtung;
	}

	public void setGewichtung(double gewichtung)
	{
		pruefeNullEingabe(gewichtung, "Gewichtung");
		this.gewichtung = gewichtung;
	}

	public boolean isAktiv()
	{
		return aktiv;
	}

	public void setAktiv(boolean aktiv)
	{
		pruefeNullEingabe(aktiv, "Aktiv");
		this.aktiv = aktiv;
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
}