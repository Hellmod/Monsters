package ThreadTests;

import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;

public class MyThread2 extends Thread {
    String nazwa = "";
    TicTacToe ticTacToe;
    Scanner scan = new Scanner(System.in);
    Thread przeciwnik;

    @Override
    public void run() {
        System.out.println("Urumiono " + nazwa);

        while (true) {
            jaki_ruch();

            Start.bariera1.reset();

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

    public MyThread2(String nazwa, TicTacToe game, Thread przeciwnik) {
        this.nazwa = nazwa;
        this.ticTacToe = game;
        this.przeciwnik = przeciwnik;
    }

    public MyThread2(String nazwa, TicTacToe game) {
        this.nazwa = nazwa;
        this.ticTacToe = game;
    }

    void setPrzeciwnik(Thread przeciwnik) {
        this.przeciwnik = przeciwnik;
    }
}
