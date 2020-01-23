package SQLlight;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import Game.Game;

import javax.swing.*;

@SuppressWarnings("serial")
public class Logowanie extends JFrame implements ActionListener, KeyListener {
	JButton bZaloguj;
	JLabel lLogin, lHaslo;
	JTextField tLogin;
	JPasswordField tHaslo;
	int id;

	public static void main(String[] args) {
		new Logowanie();
	}

	public Logowanie() {
		// setFocusable(true);
		addKeyListener(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(300, 150);
		setLocation(512, 300);
		setTitle("Logowanie");
		setLayout(null);

		lLogin = new JLabel("Login: ");
		lLogin.setBounds(20, 20, 150, 20);
		add(lLogin);

		tLogin = new JTextField("Ala");
		tLogin.setBounds(100, 20, 150, 20);
		tLogin.setFocusable(true);
		tLogin.addKeyListener(this);
		add(tLogin);

		lHaslo = new JLabel("Haslo: ");
		lHaslo.setBounds(20, 50, 150, 20);
		add(lHaslo);

		tHaslo = new JPasswordField("as");
		tHaslo.setBounds(100, 50, 150, 20);
		tHaslo.addKeyListener(this);
		add(tHaslo);

		bZaloguj = new JButton("Zaloguj");
		bZaloguj.setBounds(75, 80, 120, 20);
		bZaloguj.addKeyListener(this);
		bZaloguj.addActionListener((ActionListener) this);
		add(bZaloguj);

		setVisible(true);
		// setFocusable(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		login();
	}

	private void login() {

		if ( logowanie(tLogin.getText(),tHaslo.getText())) {
			new Game(id);
			this.dispose();
		} else
			alert("Blad logowania", "Niepoprawy login lub haslo");

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_ESCAPE) {
			this.dispose();
		}

		if (id == KeyEvent.VK_ENTER) {

			login();
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	static void alert(String tytul, String komunikat) {
		String msg = "<html><center><b><font color=red>" + komunikat + "</font></b></center></html>";
		JOptionPane.showMessageDialog(null, msg, tytul, JOptionPane.WARNING_MESSAGE, null);
	}

	boolean logowanie(String login,String haslo){
		BazaLonczenie b = new BazaLonczenie();
		List<Uzytkownicy> urzytkownicy = b.selectUzytkownik();

		for(Uzytkownicy k: urzytkownicy)
			if(k.getLogin().equals(login) &&k.getHaslo().equals(haslo)) {
				id=k.getId();
				return true;
			}
		return false;
	}

}
