package ThreadTests;

import java.util.Scanner;

public class MyThread extends Thread {
    String nazwa = "";
    TicTacToe ticTacToe;
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

                while (ticTacToe.flaga == nazwa) {

                    jaki_ruch();

                    if(nazwa=="x")
                        ticTacToe.flaga = "o";
                    else
                        ticTacToe.flaga="x";
                }
            }

    }

    void jaki_ruch() {

        int x, y;
        System.out.println("witaj " + nazwa + " jaki chcesz wykonac ruch?");
        x = scan.nextInt();
        y = scan.nextInt();
        ticTacToe.ustaw(x, y, nazwa);
        ticTacToe.wypisz();

    }

    public MyThread(String nazwa, TicTacToe game, Thread przeciwnik) {
        this.nazwa = nazwa;
        this.ticTacToe = game;
        this.przeciwnik = przeciwnik;
    }

    public MyThread(String nazwa, TicTacToe game) {
        this.nazwa = nazwa;
        this.ticTacToe = game;
    }

    void setPrzeciwnik(Thread przeciwnik) {
        this.przeciwnik = przeciwnik;
    }
}
