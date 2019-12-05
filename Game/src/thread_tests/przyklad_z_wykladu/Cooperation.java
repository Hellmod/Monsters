package przyklad_z_wykladu;

class Cooperation {
	public static void main(String args[]) {
		Q q = new Q();
		new Producer(q);
		new Consumer(q);
	}
}