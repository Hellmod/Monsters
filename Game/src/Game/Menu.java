package Game;

import SQLlight.BazaLonczenie;
import SQLlight.Uzytkownicy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class Menu extends JFrame implements ActionListener , KeyListener 
{
	static Menu frame;

	JButton bStart;
	JCheckBox cNiesmiertelnosc;
	JLabel lNiesmiertelnosc,lSpeed,lBallSpeed,lTekst,lKile;
	JTextField tHealth,tSpeed,tBallSpeed;
	
	public Menu() 
	{
		setFocusable(true);
		setLayout(null);
		frame = this;
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setTitle("Manu");
		frame.setLocation(810, 150);
		frame.setSize(200, 400);
		frame.setAlwaysOnTop(true);
		frame.addKeyListener(this);
		
		
		bStart = new JButton("OK");
		bStart.setBounds(25, 240, 100, 20);
		bStart.addActionListener(this);
		bStart.addKeyListener(this);
		frame.add(bStart);
		//---------------Immortality
		cNiesmiertelnosc = new JCheckBox();
		cNiesmiertelnosc.setBounds(5, 20, 30, 20);
		cNiesmiertelnosc.addKeyListener(this);
		frame.add(cNiesmiertelnosc);
		
		lNiesmiertelnosc = new JLabel("Immortality");
		lNiesmiertelnosc.setBounds(40, 20, 120, 20);
		frame.add(lNiesmiertelnosc);
		//-----------------
		//---------------Health
		tHealth = new JTextField("5");
		tHealth.setBounds(5, 40, 30, 20);
		tHealth.addKeyListener(this);
	    PlainDocument doc = (PlainDocument) tHealth.getDocument();
	    doc.setDocumentFilter(new MyIntFilter());
		frame.add(tHealth);
		
		lNiesmiertelnosc = new JLabel("Health");
		lNiesmiertelnosc.setBounds(40, 40, 120, 20);
		frame.add(lNiesmiertelnosc);
		//-----------------
		//---------------Speed
		tSpeed = new JTextField("15");
		tSpeed.setBounds(5, 60, 30, 20);
		tSpeed.addKeyListener(this);
	    PlainDocument doc2 = (PlainDocument) tSpeed.getDocument();
	    doc2.setDocumentFilter(new MyIntFilter());
		frame.add(tSpeed);
		
		lSpeed = new JLabel("Player Speed");
		lSpeed.setBounds(40, 60, 120, 20);
		frame.add(lSpeed);
		//-----------------
		//---------------Ball Speed
		tBallSpeed = new JTextField("5");
		tBallSpeed.setBounds(5, 80, 30, 20);
		tBallSpeed.addKeyListener(this);
	    PlainDocument doc3 = (PlainDocument) tBallSpeed.getDocument();
	    doc3.setDocumentFilter(new MyIntFilter());
		frame.add(tBallSpeed);
		
		lBallSpeed = new JLabel("Ball Speed");
		lBallSpeed.setBounds(40, 80, 120, 20);
		frame.add(lBallSpeed);

		lKile = new JLabel("Kile: ");
		lKile.setBounds(40, 100, 120, 20);
		frame.add(lKile);



		//-----------------
		//---------------Test
		lTekst = new JLabel("<html>M - monster <br> "
				+ "WSAD - ruch <br> "
				+ "Strza³ki - strza³</html>");
		lTekst.setBounds(40, 260, 250, 60);
		frame.add(lTekst);
		//-----------------


		
		requestFocusInWindow();
		frame.setVisible(true);
	}

	public  void aktualizacjaKili(){
		BazaLonczenie b = new BazaLonczenie();
		List<Uzytkownicy> urzytkownicy = b.selectUzytkownik();

		for(Uzytkownicy k: urzytkownicy){
			if(k.getId()==Game.getUserId())
				lKile.setText("Kile: "+k.getKills());
				frame.repaint();
			}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
	
		if (source == bStart)
		{
			ok();
		} 
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_ESCAPE)
		{
			this.dispose();
		}
		
		if (id == KeyEvent.VK_ENTER)
		{
			ok();
		}
		
	}
	private void ok()
	{
		Game.game.setNiesmiertelnosc(cNiesmiertelnosc.isSelected());
		Game.game.player.setHealth(Integer.parseInt(tHealth.getText()));
		Game.game.player.setSpeed(Integer.parseInt(tSpeed.getText()));
		this.setVisible(false);
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

}

class MyIntFilter extends DocumentFilter {
	   @Override
	   public void insertString(FilterBypass fb, int offset, String string,
	         AttributeSet attr) throws BadLocationException {

	      Document doc = fb.getDocument();
	      StringBuilder sb = new StringBuilder();
	      sb.append(doc.getText(0, doc.getLength()));
	      sb.insert(offset, string);

	      if (test(sb.toString())) {
	         super.insertString(fb, offset, string, attr);
	      } else {
	         // warn the user and don't allow the insert
	      }
	   }

	   private boolean test(String text) {
	      try {
	         Integer.parseInt(text);
	         return true;
	      } catch (NumberFormatException e) {
	         return false;
	      }
	   }

	   @Override
	   public void replace(FilterBypass fb, int offset, int length, String text,
	         AttributeSet attrs) throws BadLocationException {

	      Document doc = fb.getDocument();
	      StringBuilder sb = new StringBuilder();
	      sb.append(doc.getText(0, doc.getLength()));
	      sb.replace(offset, offset + length, text);

	      if (test(sb.toString())) {
	         super.replace(fb, offset, length, text, attrs);
	      } else {
	         // warn the user and don't allow the insert
	      }

	   }

	   @Override
	   public void remove(FilterBypass fb, int offset, int length)
	         throws BadLocationException {
	      Document doc = fb.getDocument();
	      StringBuilder sb = new StringBuilder();
	      sb.append(doc.getText(0, doc.getLength()));
	      sb.delete(offset, offset + length);

	      if (test(sb.toString())) {
	         super.remove(fb, offset, length);
	      } else {
	         // warn the user and don't allow the insert
	      }

	   }
	}

