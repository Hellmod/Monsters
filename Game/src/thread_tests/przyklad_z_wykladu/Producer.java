package przyklad_z_wykladu;

import java.util.Scanner;

class Producer implements Runnable {
	Q q;
	Scanner scan = new Scanner(System.in);

	Producer(Q q) {
		this.q = q;
		new Thread(this, "Producer").start();
	}

	public void run() {
		int i = 0;
		while (true) {
			
	    	int x;
	    	System.out.println("witaj ile chcesz dodać ?");
	    	x = scan.nextInt();
			q.put(i+=x);
		}
	}
}