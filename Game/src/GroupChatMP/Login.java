package GroupChatMP;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener, KeyListener {
	JButton join;
	JLabel text;
	JTextField name;

	public static void main(String[] args) {
		new Login();
	}

	public Login() {
		// setFocusable(true);
		addKeyListener(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(350, 150);
		setLocation(512, 300);
		setTitle("Login");
		setLayout(null);

		text = new JLabel("Type your name: ");
		text.setBounds(20, 20, 200, 20);
		add(text);

		name = new JTextField();
		name.setBounds(150, 20, 150, 20);
		name.setFocusable(true);
		name.addKeyListener(this);
		add(name);

		join = new JButton("Join group chat");
		join.setBounds(75, 80, 170, 20);
		join.addKeyListener(this);
		join.addActionListener((ActionListener) this);
		add(join);

		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		login();
	}

	private void login() {
		new Client(name.getText());
		this.dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_ESCAPE) {
			this.dispose();
		}

		if (id == KeyEvent.VK_ENTER) {
			System.out.println("Enter");
			login();
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}
