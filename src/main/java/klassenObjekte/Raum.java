package klassenObjekte;

/**
 * 
 * @author Martin Albertz
 * 
 * 
 *  TODO Liste mit Zeitslots hinzufuegen
 */
public class Raum
{
	private String name;
	private int kapazitaet;


	public Raum(String name, int kapazitaet)
	{
		// TODO Auto-generated constructor stub
		setKapazitaet(kapazitaet);
		setName(name);

  }
    
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	public int getKapazitaet()
	{
		return kapazitaet;
	}
	public void setKapazitaet(int kapazitaet)
	{
		this.kapazitaet = kapazitaet;
	}


	public int getKapazität() {
		return kapazitaet;
	}

	public void setKapazität(int kapazität) {
		this.kapazitaet = kapazität;
	}
}