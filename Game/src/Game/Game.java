package Game;

import GroupChatMP.Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Game implements ActionListener, KeyListener {



    public int gameStatus = 0; //0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
    public static Game game;
    public static int width = 800, height = 700;
    int p = 0;
    public Renderer renderer;
    public Player player;
    private boolean niesmiertelnosc = false;
    public boolean w, s, a, d, v, ls, rs, us, ds;
    public Random random;
    public JFrame jframe;
    public static ArrayList<Ball> listBall = new ArrayList<Ball>();
    public static ArrayList<Monster> listMonster = new ArrayList<Monster>();
    public static ArrayList<Player> listPlayer = new ArrayList<Player>();
	public Thread t1 = new BallThread(this);
    public Thread serverThread;
    Menu menu = new Menu();


    Timer t;
    Timer timer;

    public Game() {
        game = this;
        timer = new Timer(20, this);//20
        ActionListener listener = new Shot();
        t = new Timer(200, listener);//timer do strza?u
        random = new Random();
        jframe = new JFrame("Game V 0.2");
        renderer = new Renderer();
        jframe.setSize(width + 15, height + 35);
        jframe.setLocation(width,0);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(renderer);
        jframe.addKeyListener(this);
        start();

        player.setHealth(Integer.parseInt(menu.tHealth.getText()));
        player.setSpeed(Integer.parseInt(menu.tSpeed.getText()));

        timer.start();
    }

    public void setNiesmiertelnosc(boolean niesmiertelnosc) {
        this.niesmiertelnosc = niesmiertelnosc;
    }


    public void start() {
        setGameStatus(1);

        player = new Player();
        serverThread= new ServerThread(this.player,this,this.listPlayer);
		t1.start();
		serverThread.start();
        jframe.setTitle("Game V 0.2   "+player.hashCode());
    }

    public void update() {
        if (getGameStatus() == 2) {


            //for (int i = 0; i < listBall.size(); i++) listBall.get(i).update();
            for (int i = 0; i < listMonster.size(); i++) listMonster.get(i).update();
            if (w) player.moveUP(true);
            if (s) player.moveDown(true);
            if (a) player.moveLeft(true);
            if (d) player.moveRight(true);
            if (player.getHealth() <= 0) {
                if (!isNiesmiertelnosc()) {
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
    public synchronized void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        if (getGameStatus() == 1 || getGameStatus() == 2) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);
			player.render(g);
            for (int i = 0; i < listBall.size(); i++) listBall.get(i).render(g);

            for (int i = 0; i < listMonster.size(); i++) listMonster.get(i).render(g);

            for (int i = 0; i < listPlayer.size(); i++) listPlayer.get(i).render(g);
            if (listPlayer.size()!=0){
                System.out.println(listPlayer.get(0).x+" "+listPlayer.get(0).y);
            }
        }
        if (getGameStatus() == 1) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("PAUSED", width / 2 - 103, height / 2 - 25);
        }


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        renderer.repaint();
    }

    public static void main(String[] args) {
        game = new Game();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int id = e.getKeyCode();

        if (id == KeyEvent.VK_W) w = true;
		else if (id == KeyEvent.VK_S) s = true;
		else if (id == KeyEvent.VK_A) a = true;
		else if (id == KeyEvent.VK_D) d = true;
		else if (id == KeyEvent.VK_LEFT) {
            ls = true;
            if (!t.isRunning())
                Game.listBall.add(new Ball(game.player.getX(), game.player.getY() + (game.player.getHeight() / 2) - Ball.height / 2, 0, -2, Integer.parseInt(menu.tBallSpeed.getText())));
            t.start();
        } else if (id == KeyEvent.VK_RIGHT) {
            rs = true;
            if (!t.isRunning())
                Game.listBall.add(new Ball(game.player.getX(), game.player.getY() + (game.player.getHeight() / 2) - Ball.height / 2, 0, 2, Integer.parseInt(menu.tBallSpeed.getText())));
            t.start();
        } else if (id == KeyEvent.VK_DOWN) {
            ds = true;
            if (!t.isRunning())
                Game.listBall.add(new Ball(game.player.getX() + (Game.game.player.getWidth() / 2) - Ball.width / 2, game.player.getY(), 2, 0, Integer.parseInt(menu.tBallSpeed.getText())));
            t.start();
        } else if (id == KeyEvent.VK_UP) {
            us = true;
            if (!t.isRunning())
                Game.listBall.add(new Ball(game.player.getX() + (game.player.getWidth() / 2) - Ball.width / 2, game.player.getY(), -2, 0, Integer.parseInt(menu.tBallSpeed.getText())));
            t.start();
        } else if (id == KeyEvent.VK_M) {

            listMonster.add(new NormalMonster(random.nextInt(width), random.nextInt(height), random.nextInt(4) - 2, random.nextInt(4) - 2));
            p++;
            if (p > 100) {
                p = 0;
                System.out.println(listMonster.size());//<---
            }
        } else if (id == KeyEvent.VK_N) {
            listMonster.add(new SuperMonster());
        } else if (id == KeyEvent.VK_ESCAPE) {
            setGameStatus(1);
            /*
            try {
                t1.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            */


            if (!menu.isVisible())
                menu.setVisible(true);

        } else if (id == KeyEvent.VK_SPACE) {
		    setGameStatus(2);
		    /*
		    if(t1.isAlive())
                t1.notify();

		     */
            menu.setVisible(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int id = e.getKeyCode();

        if (id == KeyEvent.VK_W) w = false;
		else if (id == KeyEvent.VK_S) s = false;
		else if (id == KeyEvent.VK_A) a = false;
		else if (id == KeyEvent.VK_D) d = false;
		else if (id == KeyEvent.VK_LEFT) {
            if (!rs || !us || !ds)
                t.stop();
            ls = false;
        } else if (id == KeyEvent.VK_RIGHT) {
            rs = false;
            if (!ls || !us || !ds)
                t.stop();
        } else if (id == KeyEvent.VK_DOWN) {
            ds = false;
            if (!rs || !us || !ls)
                t.stop();
        } else if (id == KeyEvent.VK_UP) {
            us = false;
            if (!rs || !ls || !ds)
                t.stop();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) { }

    static void alert(String tytul, String komunikat) {
        String msg = "<html><center><b><font color=red>" + komunikat + "</font></b></center></html>";
        JOptionPane.showMessageDialog(null, msg, tytul, JOptionPane.WARNING_MESSAGE, null);
    }

    public boolean isNiesmiertelnosc() {
        return niesmiertelnosc;
    }
    public synchronized int getGameStatus() { return gameStatus; }

    public synchronized void setGameStatus(int gameStatus) {this.gameStatus = gameStatus;  }
}

class ServerThread extends Thread  {

    BufferedReader czytelnik;
    PrintWriter pisarz;
    Socket gniazdo;

    
    Player player;
    Game game;
    ArrayList<Player> listPlayer;

    ServerThread(Player player,Game game,ArrayList<Player> listPlayer){
        this.listPlayer=listPlayer;
        this.player=player;
        this.game=game;
        konfigurujKomunikacje();
        Thread receiverThread = new Thread(new MessageReceiver());
        receiverThread.start();
    }
    @Override
    public void run() {

        while (true){

            if(game.getGameStatus()==2) {

                sendWspulrzendne(player.x, player.y);

                try {
                    this.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class MessageReceiver implements Runnable {
        public void run() {
            String message;
            HashSet<Integer> hashTab = new HashSet<Integer>();
            hashTab.add(player.hashCode());
            try {
                while ((message = czytelnik.readLine()) != null) {

                    String[] tab =message.split(";");
                    int x= Integer.parseInt(tab[0]);
                    int y= Integer.parseInt(tab[1]);
                    int hashCode= Integer.parseInt(tab[2]);
                    if(hashCode!=player.id) {
                        if (!hashTab.contains(hashCode)) {
                            Player p = new Player();
                            p.id=hashCode;
                            listPlayer.add(p);
                        }

                        for (Player p : listPlayer) {
                            if (p.id == hashCode) {
                                p.setX(x);
                                p.setY(y);
                                break;
                            }
                        }
                        System.out.println(listPlayer.size());
                        //System.out.println("messages: "+ x+";"+y+";"+hashCode);
                    }

                   // for (Player p : listPlayer) System.out.println(p.getX()+" "+p.getY());

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    private void sendWspulrzendne(int x, int y){
        String kod=x+";"+y+";"+player.hashCode();
        try {
            //System.out.println(kod);
            pisarz.println(kod);
            pisarz.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendPlayer(Player player){
        try {
            pisarz.print(player);
            pisarz.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void konfigurujKomunikacje() {
        try {
            gniazdo = new Socket("127.0.0.1", 5000);
            InputStreamReader czytelnikStrm = new InputStreamReader(gniazdo.getInputStream());
            czytelnik = new BufferedReader(czytelnikStrm);
            pisarz = new PrintWriter(gniazdo.getOutputStream());
            System.out.println("obsluga sieci przygotowana");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}


