package SQLlight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.Zagrozenie;
import model.Uzytkownicy;

public class Biblioteka {

	public static final String DRIVER = "org.sqlite.JDBC";
	public static final String DB_URL = "jdbc:sqlite:biblioteka.db";

	private Connection conn;
	private Statement stat;

	public ArrayList<Object[]> baza=new ArrayList<>();
	
	public Biblioteka() {
		try {
			Class.forName(Biblioteka.DRIVER);
		} catch (ClassNotFoundException e) {
			System.err.println("Brak sterownika JDBC");
			e.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(DB_URL);
			stat = conn.createStatement();
		} catch (SQLException e) {
			System.err.println("Problem z otwarciem polaczenia");
			e.printStackTrace();
		}

		createTables();
	}
	
//TODO zaszyfrowa� chas�o np. ...
	
//TODO zabezpieczy� przed takimi samymi loginami	
	public boolean createTables() {
		String createZagrozenia = "CREATE TABLE IF NOT EXISTS zagrozenia (id INTEGER PRIMARY KEY AUTOINCREMENT, nazwa varchar(255), prawdopodobienstow int, zagrozenie int, ryzyko int, opis varchar(255))";
		String createUzytkownicy = "CREATE TABLE IF NOT EXISTS uzytkownicy (id INTEGER PRIMARY KEY AUTOINCREMENT, login varchar(255), haslo varchar(255))";
		try {
			stat.execute(createZagrozenia);
			stat.execute(createUzytkownicy);
		} catch (SQLException e) {
			System.err.println("Blad przy tworzeniu tabeli");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean insertZagrozenia(String nazwa, int prawdopodobienstow, int zagrozenie,int ryzyko,String opis) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("insert into zagrozenia values (NULL, ?, ?, ?, ?, ?);");
			prepStmt.setString(1, nazwa);
			prepStmt.setInt(2, prawdopodobienstow);
			prepStmt.setInt(3, zagrozenie);
			prepStmt.setInt(4, prawdopodobienstow*zagrozenie);
			prepStmt.setString(5, opis);
			prepStmt.execute();
		} catch (SQLException e) {
			System.err.println("Blad przy wstawianiu zagrozenia");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean insertUzytkownik(String login, String haslo) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("insert into uzytkownicy values (NULL, ?, ?);");
			prepStmt.setString(1, login);
			prepStmt.setString(2, haslo);
			prepStmt.execute();
		} catch (SQLException e) {
			System.err.println("Blad przy uzytkownikach");
			return false;
		}
		return true;
	}

	public List<Zagrozenie> selectRekord() {
		List<Zagrozenie> rekordy = new LinkedList<Zagrozenie>();
		try {
			ResultSet result = stat.executeQuery("SELECT * FROM zagrozenia");
			int id;
			String nazwa;
			int prawdopodobienstow;
			int zagrozenie;
			int ryzyko;
			String opis;

			while (result.next()) {
				id = result.getInt("id");
				nazwa = result.getString("nazwa");
				prawdopodobienstow = result.getInt("prawdopodobienstow");
				zagrozenie = result.getInt("zagrozenie");
				ryzyko = result.getInt("ryzyko");
				opis = result.getString("opis");
				rekordy.add(new Zagrozenie(id, nazwa, prawdopodobienstow, zagrozenie, ryzyko, opis));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return rekordy;
	}

	public List<Uzytkownicy> selectUzytkownik() {		
		List<Uzytkownicy> urzytkownicy = new LinkedList<Uzytkownicy>();
		try {
			ResultSet result = stat.executeQuery("SELECT * FROM uzytkownicy");
			int id;
			String login, haslo;
			while (result.next()) {
				id = result.getInt("id");
				login = result.getString("login");
				haslo = result.getString("haslo");
				urzytkownicy.add(new Uzytkownicy(id, login, haslo));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return urzytkownicy;
	}

	public boolean DeleteZagrozenia(int id) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("DELETE FROM zagrozenia WHERE id=?;");
			prepStmt.setInt(1, id);
			prepStmt.execute();
			
		} catch (SQLException e) {
			System.err.println("Blad przy usuwaniu zagrozenia");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.err.println("Problem z zamknieciem polaczenia");
			e.printStackTrace();
		}
	}
}