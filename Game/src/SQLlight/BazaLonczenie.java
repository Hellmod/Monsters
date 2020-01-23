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

public class BazaLonczenie {

	public static final String DRIVER = "org.sqlite.JDBC";
	public static final String DB_URL = "jdbc:sqlite:biblioteka.db";

	private Connection conn;
	private Statement stat;

	public ArrayList<Object[]> baza=new ArrayList<>();
	
	public BazaLonczenie() {
		try {
			Class.forName(BazaLonczenie.DRIVER);
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
	

	
//TODO zabezpieczyæ przed takimi samymi loginami	
	public boolean createTables() {
		String createUzytkownicy = "CREATE TABLE IF NOT EXISTS uzytkownicy (id INTEGER PRIMARY KEY AUTOINCREMENT, login varchar(255), haslo varchar(255))";
		try {
			stat.execute(createUzytkownicy);
		} catch (SQLException e) {
			System.err.println("Blad przy tworzeniu tabeli");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean insertUzytkownik(String login, String haslo,int kills) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("insert into uzytkownicy values (NULL, ?, ?, ?);");
			prepStmt.setString(1, login);
			prepStmt.setString(2, haslo);
			prepStmt.setInt(3, kills);
			prepStmt.execute();
		} catch (SQLException e) {
			System.err.println("Blad przy uzytkownikach");
			return false;
		}
		return true;
	}

	public List<Uzytkownicy> selectUzytkownik() {		
		List<Uzytkownicy> urzytkownicy = new LinkedList<Uzytkownicy>();
		try {
			ResultSet result = stat.executeQuery("SELECT * FROM uzytkownicy");
			int id,kills;
			String login, haslo;
			while (result.next()) {
				id = result.getInt("id");
				login = result.getString("login");
				haslo = result.getString("haslo");
				kills = result.getInt("kills");
				urzytkownicy.add(new Uzytkownicy(id, login, haslo,kills));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return urzytkownicy;
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