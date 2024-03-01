package klassenObjekte;

public class Raum {

	String name;
	private int kapazität;

	Raum(String name, int roomroomKapazitaet) {
		this.name = name;
		this.kapazität = roomroomKapazitaet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKapazität() {
		return kapazität;
	}

	public void setKapazität(int kapazität) {
		this.kapazität = kapazität;
	}
}