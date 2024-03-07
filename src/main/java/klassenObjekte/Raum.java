package klassenObjekte;


<<<<<<< HEAD
	String name;
	private int kapazität;
=======
>>>>>>> branch 'Sprint2' of https://github.com/tausauron/BOT-Projekt

<<<<<<< HEAD
	Raum(String name, int roomroomKapazitaet) {
		this.name = name;
		this.kapazität = roomroomKapazitaet;
	}
=======
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
>>>>>>> branch 'Sprint2' of https://github.com/tausauron/BOT-Projekt


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
		return kapazität;
	}

	public void setKapazität(int kapazität) {
		this.kapazität = kapazität;
	}
}