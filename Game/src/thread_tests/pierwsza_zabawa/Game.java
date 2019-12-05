package pierwsza_zabawa;

public class Game {
	private String tab[][]=new String[3][3];
	String flaga ="x";
	
	public Game() {
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[1].length; j++) {
				tab[i][j]=" ";
			}
		}

	}
	
	void wypisz() {
		System.out.println("-------");
		for (int i = 0; i < tab.length; i++) {
			System.out.print("|");
			for (int j = 0; j < tab[1].length; j++) {
				System.out.print(tab[i][j]+"|");
			}
			System.out.println("\n-------");
		}
		
	}
	
	void ustaw(int x,int y, String z) {
		synchronized (this) {
			tab[x][y]=z;
		}
	}
}
