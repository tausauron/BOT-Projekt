package gui;

public class Schüler {
    private String klasse;
    private String name;
    private String nachname;
    public String getKlasse() {
		return klasse;
	}

	public void setKlasse(String klasse) {
		this.klasse = klasse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getWahl1() {
		return wahl1;
	}

	public void setWahl1(String wahl1) {
		this.wahl1 = wahl1;
	}

	public String getWahl2() {
		return wahl2;
	}

	public void setWahl2(String wahl2) {
		this.wahl2 = wahl2;
	}

	private String wahl1;
    private String wahl2;

    public Schüler(String klasse, String name, String nachname, String wahl1, String wahl2) {
        this.klasse = klasse;
        this.name = name;
        this.nachname = nachname;
        this.wahl1 = wahl1;
        this.wahl2 = wahl2;
    }

    // Getters and Setters here
}