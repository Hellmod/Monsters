package pierwsza_zabawa;

public class Test {

	static Game game = new Game();
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1,t2;
		t1 = new MyThread("x",game);
		t2 = new MyThread("o",game);
		//((MyThread) t1).setPrzeciwnik(t2);
		//CountDownLatch cdl = new CountDownLatch(2);
		

		t1.start();
		t2.start();
		
		
	}

}
