package Game;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
    //public int score;
    public int paddleNumber;

    public int x = 0, y = 0, width = 50, height = 50, health, speed,id;




    public Player() {
        this.x = Game.width / 2 - this.width / 2;
        this.y = Game.height / 2 - this.height / 2;
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(getX(), getY(), width, height);
        for (int i = 0; i < health; i++) {
            g.setColor(Color.RED);
            g.fillRect(10 * i + i, 0, 10, 10);
        }

    }

    public void update() {
        System.out.println("test");
    }

    public void moveUP(boolean flaga) {

        if (y - speed > 0) {
            y -= speed;
        } else {
            y = 0;
        }
    }

    public void moveDown(boolean flaga) {

        if (y + height + speed < Game.height) {
            y += speed;
        } else {
            y = Game.height - height;
        }
    }

    public void moveLeft(boolean flaga) {

        if (x - speed > 0) {
            x -= speed;
        } else {
            x = 0;
        }
    }

    public void moveRight(boolean flaga) {

        if (x + width + speed < Game.width) {
            x += speed;
        } else {
            x = Game.width - width;
        }
    }

    public void switchHealth(int health){
    	this.health+=health;
	}

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPaddleNumber() {
        return paddleNumber;
    }

    public void setPaddleNumber(int paddleNumber) {
        this.paddleNumber = paddleNumber;
    }

    public synchronized void setX(int x) {
        this.x = x;
    }

    public synchronized void setY(int y) {
        this.y = y;
    }


    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public void setHealth(int health) {
        this.health = health;
    }

    public int getWidth() {
        return width;
    }

    public int getHealth() {
        return health;
    }


    public int getHeight() {
        return height;
    }

    public synchronized int getX() {
        return x;
    }

    public synchronized int getY() {
        return y;
    }

}
