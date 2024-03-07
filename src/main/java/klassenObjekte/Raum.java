package klassenObjekte;

public class Raum {

	String name;
	int Kapazitaet;

	public Raum(String name) {
		this.name = name;
		this.Kapazitaet = Kapazitaet;
	}
	public Raum(String name, int Kapazitaet ) {
		this.name = name;
		this.Kapazitaet = Kapazitaet;
	}

	public String getName() {
		return name;
	}
	public int getKapazitaet() {
		return Kapazitaet;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setName(int Kapazitaet) {
		this.Kapazitaet = Kapazitaet;
	}

}
