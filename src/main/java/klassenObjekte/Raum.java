package klassenObjekte;

<<<<<<< HEAD
/**
 * 
 * @author Martin Albertz
 * 
 * 
 * TODO Liste mit Zeitslots hinzufuegen
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
=======
public class Raum {

	String name;

	public Raum(String name) {
		this.name = name;
>>>>>>> branch 'Sprint2' of https://github.com/tausauron/BOT-Projekt.git
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
	

	

}
