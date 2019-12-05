package pierwsza_zabawa;

import java.util.Scanner;

/**
 * @author Rafal
 *
 */
public class MyThread extends Thread {
	String nazwa = "";
	Game game;
	Scanner scan = new Scanner(System.in);
	Thread przeciwnik;

	@Override
	public void run() {
		/*
		 * if (nazwa == "x") { ; try { synchronized (this) { this.wait(); } } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */
		System.out.println("Urumiono " + nazwa);
		while(true)
		synchronized (this) {

			while (game.flaga == nazwa) {

				jaki_ruch();
				
				if(nazwa=="x")
					 game.flaga = "o";
				else
					game.flaga="x";
			}
		}

	}

	void jaki_ruch() {

		int x, y;
		System.out.println("witaj " + nazwa + " jaki chcesz wykonac ruch?");
		x = scan.nextInt();
		y = scan.nextInt();
		game.ustaw(x, y, nazwa);
		game.wypisz();

	}

	public MyThread(String nazwa, Game game, Thread przeciwnik) {
		this.nazwa = nazwa;
		this.game = game;
		this.przeciwnik = przeciwnik;
	}

	public MyThread(String nazwa, Game game) {
		this.nazwa = nazwa;
		this.game = game;
	}

	void setPrzeciwnik(Thread przeciwnik) {
		this.przeciwnik = przeciwnik;
	}
}