package pierwsza_zabawa;

public class PrzykladSleep {

	public static void main(String[] args) {

		try {
			String Slowa[] = { "Raz", "Dwa", "Trzy", "Cztery" };
			for (int i = 0; i < Slowa.length; i++) {
				Thread.sleep(1000);
				System.out.println(Slowa[i]);
			}
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

}
	
