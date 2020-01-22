package ThreadTests;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class CyclicBarrierV2 {

    private static final int opoznienie = 2;
    private static final int N_PRZESTAWIANYCH = 10;
    private static final int[] dane = new int[N_PRZESTAWIANYCH];
    private static final Random random = new Random();

    private static final java.util.concurrent.CyclicBarrier bariera1;
    private static final java.util.concurrent.CyclicBarrier bariera2;

    static {
        bariera1 = new java.util.concurrent.CyclicBarrier(N_PRZESTAWIANYCH);
        bariera2 = new java.util.concurrent.CyclicBarrier(N_PRZESTAWIANYCH, new Runnable() {
            @Override
            public void run() {
                for (int x : dane) {
                    System.out.print(" " + x);
                }
                System.out.println();
            }
        });
    }

    private static void losoweOpóźnienie() throws InterruptedException {
        Thread.sleep(random.nextInt(opoznienie));
    }

    private static class Pracownik implements Runnable {

        private final int ktora;

        private Pracownik(final int która) {
            this.ktora = która;
        }

        @Override
        public void run() {
            try {
                losoweOpóźnienie();
                dane[ktora] = ktora + 1;
                bariera1.await();
                losoweOpóźnienie();
                final int druga = dane[N_PRZESTAWIANYCH - 1 - ktora];
                bariera1.await();
                losoweOpóźnienie();
                dane[ktora] = druga;
                bariera2.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                Thread.currentThread().interrupt();
                System.err.println("Wątek " + ktora + " przerwany");
            }
        }

    }

    public static void main(final String[] args) {
        for (int i = 0; i < N_PRZESTAWIANYCH; ++i) {
            new Thread(new Pracownik(i)).start();
        }
    }

}