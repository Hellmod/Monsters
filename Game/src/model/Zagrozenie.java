package model;

public class Zagrozenie {
    private int id;
    private String nazwa;
    private int prawdopodobienstow;
    private int zagrozenie;
    private int ryzyko;
    private String opis;
    
    public int getRyzyko() {
		return ryzyko;
	}
	public void setRyzyko(int ryzyko) {
		this.ryzyko = ryzyko;
	}   
    public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public int getPrawdopodobienstow() {
		return prawdopodobienstow;
	}
	public void setPrawdopodobienstow(int prawdopodobienstow) {
		this.prawdopodobienstow = prawdopodobienstow;
	}
	public int getZagrozenie() {
		return zagrozenie;
	}
	public void setZagrozenie(int zagrozenie) {
		this.zagrozenie = zagrozenie;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public int getId() {
		return id;
	}

    public Zagrozenie() { }
    public Zagrozenie(int id, String nazwa, int prawdopodobienstow, int zagrozenie,int ryzyko,String opis) {
        this.id = id;
        this.nazwa = nazwa;
        this.prawdopodobienstow = prawdopodobienstow;
        this.zagrozenie = zagrozenie;
        this.ryzyko=ryzyko;
        this.opis = opis;
    }

    @Override
    public String toString() {
        return "["+id+"] - "+nazwa+" p"+prawdopodobienstow+"  z"+zagrozenie+"  r"+ryzyko+" - "+opis;
    }
}