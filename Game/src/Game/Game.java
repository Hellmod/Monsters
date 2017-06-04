package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Game implements ActionListener, KeyListener
{

	public int gameStatus = 0; //0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
	public static Game game;
	



	public static int width = 800, height = 700;
	int p=0;
	public Renderer renderer;

	public Player player;
	
	private boolean niesmiertelnosc=false;

	public boolean w, s, a, d,v,ls,rs,us,ds;
	
	public Random random;

	public JFrame jframe;
	
	//Shot shot;
	
	public static ArrayList<Ball> lis = new ArrayList<Ball>();
	public static ArrayList<Monster> lisM = new ArrayList<Monster>();
	
	Menu menu = new Menu();

	Timer t;
	Timer timer;
	
	
	public Game()
	{
		
		game=this;
		
		timer = new Timer(20, this);//20
		
		
		ActionListener listener =new Shot();
		t = new Timer(200, listener);//timer do strza³u
		
		//gameStatus=2;
		random = new Random();

		jframe = new JFrame("Game V 0.2");

		renderer = new Renderer();

		jframe.setSize(width + 15, height + 35);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(renderer);
		jframe.addKeyListener(this);
		start();

		player.setHealth(Integer.parseInt(menu.tHealth.getText()));		
		player.setSpeed(Integer.parseInt(menu.tSpeed.getText()));

		timer.start();
	}
	
	public void setNiesmiertelnosc(boolean niesmiertelnosc)
	{
		this.niesmiertelnosc = niesmiertelnosc;
	}
	
	
	public void start()
	{
		gameStatus=1;
		player = new Player();
		
	}

	public void update()
	{
		if(gameStatus==2)
		{
			for(int i =0 ;i<lis.size();i++)
			{
				lis.get(i).update();
				
			}
			for(int i =0 ;i<lisM.size();i++)
			{
				lisM.get(i).update();
			}
			if (w)
			{
				player.moveUP(true);
			}
			if (s)
			{
				player.moveDown(true);
			}
			if (a)
			{
				player.moveLeft(true);
			}
			if (d)
			{
				player.moveRight(true);
			}
			if(player.getHealth()<=0) 
			{

				
				//t.stop();
				if(!isNiesmiertelnosc())
					{
						alert("Koniec", "Game over");
						w = false;
						s = false;
						a = false;
						d = false;
					}
				player.setHealth(Integer.parseInt(menu.tHealth.getText()));
			}
		}
		

	}
//TODO
	public void render(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


		if (gameStatus == 1 || gameStatus == 2)
		{
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, width, height);
					
			for(int i =0 ;i<lis.size();i++)
			{
				lis.get(i).render(g);
			}
			player.render(g);
			for(int i =0 ;i<lisM.size();i++)
			{
				lisM.get(i).render(g);
			}
		}
		if (gameStatus == 1)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("PAUSED", width / 2 - 103, height / 2 - 25);
		}

		
	}
		

	@Override
	public void actionPerformed(ActionEvent e)
	{
		update();
		renderer.repaint();
	}

	public static void main(String[] args)
	{
		game = new Game();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W)
		{
			w = true;
		}
		else if (id == KeyEvent.VK_S)
		{
			s = true;
		}
		else if (id == KeyEvent.VK_A)
		{
			a = true;
		}
		else if (id == KeyEvent.VK_D)
		{
			d= true;
		}
		else if (id == KeyEvent.VK_LEFT)
		{
			ls=true;
			if(!t.isRunning())
				Game.lis.add(new Ball(game.player.getX() ,game.player.getY()+(game.player.getHeight()/2)-Ball.height/2,0,-2,Integer.parseInt(menu.tBallSpeed.getText())));
			t.start();
		}
		else if (id == KeyEvent.VK_RIGHT)
		{
			rs=true;
			if(!t.isRunning())
				Game.lis.add(new Ball(game.player.getX() ,game.player.getY()+(game.player.getHeight()/2)-Ball.height/2,0,2,Integer.parseInt(menu.tBallSpeed.getText())));
			t.start();
		}
		else if (id == KeyEvent.VK_DOWN)
		{
			ds=true;
			if(!t.isRunning())
				Game.lis.add(new Ball(game.player.getX() +(Game.game.player.getWidth()/2)-Ball.width/2,game.player.getY(),2,0,Integer.parseInt(menu.tBallSpeed.getText())));
			t.start();
		}
		else if (id == KeyEvent.VK_UP)
		{
			us=true;
			if(!t.isRunning())
				Game.lis.add(new Ball(game.player.getX() +(game.player.getWidth()/2)-Ball.width/2 ,game.player.getY(),-2,0,Integer.parseInt(menu.tBallSpeed.getText())));
			t.start();
		}
		else if (id == KeyEvent.VK_M)
		{
			
			lisM.add(new NormalMonster(random.nextInt(width), random.nextInt(height), random.nextInt(4)-2, random.nextInt(4)-2));
			p++;
			if(p>100)
				{
					p=0;
					System.out.println(lisM.size());//<---
				}
		}
		else if (id == KeyEvent.VK_N)
		{
			lisM.add(new SuperMonster());
		}
		else if (id == KeyEvent.VK_ESCAPE)
		{
			gameStatus=1;
			if (!menu.isVisible())
					menu.setVisible(true);
			
		}
		else if (id == KeyEvent.VK_SPACE)
		{
			gameStatus=2;
			menu.setVisible(false);
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W)
		{
			w = false;
		}
		else if (id == KeyEvent.VK_S)
		{
			s = false;
		}
		else if (id == KeyEvent.VK_A)
		{
			a = false;
		}
		else if (id == KeyEvent.VK_D)
		{
			d = false;
		}
		else if (id == KeyEvent.VK_LEFT)
		{
			if(!rs||!us||!ds)
				t.stop();
			ls=false;	
		}
		else if (id == KeyEvent.VK_RIGHT)
		{
			rs=false;
			if(!ls||!us||!ds)
				t.stop();
		}
		else if (id == KeyEvent.VK_DOWN)
		{
			ds=false;
			if(!rs||!us||!ls)
				t.stop();
		}
		else if (id == KeyEvent.VK_UP)
		{
			us=false;
			if(!rs||!ls||!ds)
				t.stop();
		}

	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}
	static void alert(String tytul, String komunikat)
	{
		String msg = "<html><center><b><font color=red>" + komunikat + "</font></b></center></html>";
		JOptionPane.showMessageDialog(null, msg, tytul, JOptionPane.WARNING_MESSAGE, null);
	}

	public boolean isNiesmiertelnosc()
	{
		return niesmiertelnosc;
	}
}


