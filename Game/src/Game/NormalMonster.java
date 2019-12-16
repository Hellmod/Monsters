package Game;

import java.awt.Color;
import java.awt.Graphics;

public class NormalMonster extends Monster {
    public int x, y, width = 25, height = 25;
    public int motionX, motionY, health;
    Color c;

    public NormalMonster(int x, int y, int motionY, int motionX) {
        this.x = x;
        this.y = y;
        this.motionX = motionX;
        this.motionY = motionY;
        health = 3;
        c = new Color(0, 255, 0);
    }

    void setColor() {
        switch (health) {
            case 2:
                c = new Color(255, 0, 0);
                break;
            case 1:
                c = new Color(0, 0, 255);
                break;
            case 0:
                Game.listMonster.remove(this);
                break;
        }
    }

    void update() {
        setColor();

        int speed = 2;
        this.x += motionX * speed;
        this.y += motionY * speed;
//czy kula uderzy?a
        for (int i = 0; i < Game.listBall.size(); i++) {
            if (this.y + height >= Game.listBall.get(i).getY())
                if (this.y <= Game.listBall.get(i).getY() + Ball.height)
                    if (x + width >= Game.listBall.get(i).x)
                        if (x <= Game.listBall.get(i).x + Ball.width) {
							Game.listBall.remove(i);
                        	health--;
                        }
        }

//czy uderzy? w gracza
        if (this.y + height >= Game.game.player.getY()) {
            if (this.y <= Game.game.player.getY() + Game.game.player.getHeight())
                if (x + width >= Game.game.player.getX())
                    if (x <= Game.game.player.getX() + Game.game.player.getWidth()) {
                    	Game.game.player.switchHealth(-1);
                        Game.listMonster.remove(this);
                    }
        }


        if (this.y + height - motionY > Game.height || this.y + motionY < 0) {
            if (this.motionY < 0) {
                this.y = 0;
                this.motionY = speed;
            } else {
                this.motionY = -speed;
                this.y = Game.height - height;
            }
        } else if (this.x + width - motionX > Game.width || this.x + motionX < 0) {
            if (this.motionX < 0) {
                this.x = 0;
                this.motionX = speed;
            } else {
                this.motionX = -speed;
                this.x = Game.width - width;
            }
        }

    }

    void render(Graphics g) {
        g.setColor(c);
        g.fillRect(x, y, width, height);
    }

}
