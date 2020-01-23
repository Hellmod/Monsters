package ThreadTests;

import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;

public class MyThread extends Thread {
    String nazwa = "";
    TicTacToe ticTacToe;
    Scanner scan = new Scanner(System.in);
    Thread przeciwnik;

    @Override
    public void run() {

        System.out.println("Urumiono " + nazwa);

        while (true) {
            jaki_ruch();

            try {

                System.out.println(Start.bariera1.getNumberWaiting());
                Start.bariera1.await();
                System.out.println(Start.bariera1.getNumberWaiting());

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
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
