package klassenObjekte;

public class Raum {

	private String name;
	private int kapazität;

	public Raum(String name, int roomroomKapazitaet) {
		this.name = name;
		this.kapazität=roomroomKapazitaet;
	}

	public int getKapazität() {
		return kapazität;
	}

	public void setKapazität(int kapazität) {
		this.kapazität = kapazität;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
