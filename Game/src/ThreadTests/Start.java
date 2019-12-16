package ThreadTests;

public class Start {
    static TicTacToe ticTacToe = new TicTacToe();

    public static void main(String[] args) throws InterruptedException {
        Thread t1,t2;
        t1 = new MyThread("x",ticTacToe);
        t2 = new MyThread("o",ticTacToe);
        //((MyThread) t1).setPrzeciwnik(t2);
        //CountDownLatch cdl = new CountDownLatch(2);


        t1.start();
        t2.start();


    }
}
