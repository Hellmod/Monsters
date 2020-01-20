package model;

public class Uzytkownicy {
    private int id;
    private String login;
    private String haslo;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {this.login = login;}
    public String getHaslo() {
        return haslo;
    }
    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public Uzytkownicy() {}
    public Uzytkownicy(int id, String login, String haslo) {
        this.id = id;
        this.login = login;
        this.haslo = haslo;
    }

    @Override
    public String toString() {
        return "["+id+"] - "+ login +" - "+ haslo;
    }
}