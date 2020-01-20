package Game;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Clock;

public class Ball {

    public int x, y;
    public static int width = 25, height = 25;
    public int speed;
    public int motionX, motionY;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Ball(int x, int y, int motionY, int motionX, int speed) {
        this.x = x;
        this.y = y;
        this.motionX = motionX;
        this.motionY = motionY;
        this.speed = speed;
    }

    public Ball(int x, int y, int motionY, int motionX) {
        this.x = x;
        this.y = y;
        this.motionX = motionX;
        this.motionY = motionY;
        this.speed = 5;
    }

    public void update() {
        this.x += motionX * speed;
        this.y += motionY * speed;

        if (this.y + height - motionY > Game.height || this.y + motionY < 0) {
            if (this.motionY < 0) Game.listBall.remove(this);
			else Game.listBall.remove(this);
        } else if (this.x + width - motionX > Game.width || this.x + motionX < 0) {
            if (this.motionX < 0) Game.listBall.remove(this);
			else Game.listBall.remove(this);
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(x, y, width, height);
    }
}
class BallThread extends Thread  {

    Game game;
    Ball ball;
    BallThread(Game game){
        this.game=game;
    }
	@Override
	public void run() {
		while (true){
		    if(game.getGameStatus()==2) {
                update();
                try {
                    this.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
	}


	public synchronized void update() {

        for (int i = 0; i < game.listBall.size(); i++) {
            ball=game.listBall.get(i);

            ball.x += ball.motionX * ball.speed;
            ball.y += ball.motionY * ball.speed;

            if (ball.y + ball.height - ball.motionY > Game.height || ball.y + ball.motionY < 0) {
                if (ball.motionY < 0) Game.listBall.remove(ball);
                else Game.listBall.remove(ball);
            } else if (ball.x + ball.width - ball.motionX > Game.width || ball.x + ball.motionX < 0) {
                if (ball.motionX < 0) Game.listBall.remove(ball);
                else Game.listBall.remove(ball);
            }
        }
	}


}