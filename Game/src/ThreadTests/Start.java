package ThreadTests;

public class Start {
    static TicTacToe ticTacToe = new TicTacToe();

    static Thread t2 =new MyThread("o",ticTacToe);

    static final java.util.concurrent.CyclicBarrier bariera1 = new java.util.concurrent.CyclicBarrier(1,new MyThread2("x",ticTacToe));



    public static void main(String[] args) throws InterruptedException {

        //t1.start();
        t2.start();


    }
}
