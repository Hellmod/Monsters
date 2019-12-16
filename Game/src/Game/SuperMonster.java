package Game;

import java.awt.Color;
import java.awt.Graphics;

public class SuperMonster extends Monster {
	public int x, y, width = 25, height = 25;
	int speed = 2;

	public int motionX, motionY, health;
	Color c;

	public SuperMonster() {

		// this.x=Game.game.random.nextInt(Game.height);
		// this.y=Game.game.random.nextInt(Game.width);
		this.x = 400;
		this.y = 350;
		health = 3;
		c = new Color(0, 255, 125);
		calculate();
	}

	private void calculate()
	{
		int x1=this.x;
		int y1=this.y;
		int x2=Game.game.player.getX();
		int y2=Game.game.player.getY();
		

	     // zmienne pomocnicze
	     int d, dx, dy, ai, bi, xi, yi;
	     int xx = x1, yy = y1;
	     // ustalenie kierunku rysowania
	     if (x1 < x2)
	     {
	         xi = 1;
	         dx = x2 - x1;
	     }
	     else
	     {
	         xi = -1;
	         dx = x1 - x2;
	     }
	     // ustalenie kierunku rysowania
	     if (y1 < y2)
	     {
	         yi = 1;
	         dy = y2 - y1;
	     }
	     else
	     {
	         yi = -1;
	         dy = y1 - y2;
	     }
	     // pierwszy piksel
	     
	     // oœ wiod¹ca OX
	     if (dx > dy)
	     {
	         ai = (dy - dx) * 2;
	         bi = dy * 2;
	         d = bi - dx;
	         // pêtla po kolejnych x
	         while (xx != x2)
	         {
	             // test wspó³czynnika
	             if (d >= 0)
	             {
	                 xx += xi;
	                 yy += yi;
	                 d += ai;
	             }
	             else
	             {
	                 d += bi;
	                 xx += xi;
	             }
	             //setPixel(xx, yy, R, G, B);
	             motionX=xi;
	             motionY=yi;
	         }
	     }
	     // oœ wiod¹ca OY
	     else
	     {
	         ai = ( dx - dy ) * 2;
	         bi = dx * 2;
	         d = bi - dy;
	         // pêtla po kolejnych y
	         while (yy != y2)
	         {
	             // test wspó³czynnika
	             if (d >= 0)
	             {
	                 xx += xi;
	                 yy += yi;
	                 d += ai;
	             }
	             else
	             {
	                 d += bi;
	                 yy += yi;
	             }
	             motionX=xi;
	             motionY=yi;
	         }
	     }
	    
		
		
		
		
/*		
		double angle;
		int dX=this.x-Game.game.player.getX();
		int dY=this.y-Game.game.player.getY();
		
		
		if(dX<0)dX*=-1;
		if(dY<0)dY*=-1;
		if(dX==0)dX=1;
		
		angle = (double)dY/dX;
		motionX=1;
		motionY=(int) (motionX*angle);
*/
		
	}

	void update() {
		calculate();
		if (health == 2)
			c = new Color(255, 0, 125);
		if (health == 1)
			c = new Color(125, 0, 255);
		if (health == 0)
			Game.listMonster.remove(this);

		// System.out.println("WOO");

		this.x += motionX * speed;
		this.y += motionY * speed;

		//czy ktoś trafił gracza
		for (int i = 0; i < Game.listBall.size(); i++) {
			if (this.y + height >= Game.listBall.get(i).getY()) {

				if (this.y <= Game.listBall.get(i).getY() + Ball.height)
					if (x + width >= Game.listBall.get(i).x) {
						if (x <= Game.listBall.get(i).x + Ball.width) {
							health--;
							Game.listBall.remove(i);
							// c = new Color(255, 0, 0);
						}
					}

				// Game.lisM.remove(this);
			}
		}

		if (this.y + height >= Game.game.player.getY()) {
			if (this.y <= Game.game.player.getY() + Game.game.player.getHeight())
				if (x + width >= Game.game.player.getX())
					if (x <= Game.game.player.getX() + Game.game.player.getWidth()) {
						int l = Game.game.player.getHealth();
						Game.game.player.setHealth(l - 1);
						Game.listMonster.remove(this);
					}

		}

		if (this.y + height - motionY > Game.height || this.y + motionY < 0) {
			if (this.motionY < 0) {
				this.y = 0;
				this.motionY = speed;
				// System.out.println("góra");
			} else {
				this.motionY = -speed;
				this.y = Game.height - height;
				// System.out.println("dół");
			}
		} else if (this.x + width - motionX > Game.width || this.x + motionX < 0) {
			if (this.motionX < 0) {
				this.x = 0;
				this.motionX = speed;
				// System.out.println("lewo");
			} else {
				this.motionX = -speed;
				this.x = Game.width - width;
				// System.out.println("prawo");
			}
		}
		
		
		for (int i = 0; i < Game.listBall.size(); i++) {
			if (this.y + height  > Game.listBall.get(i).getY() || this.y + width < Game.listBall.get(i).getX()) {
				if (this.motionY < 0) {
					this.y = 0;
					this.motionY = speed;
					 System.out.println("góra");
				} else {
					this.motionY = -speed;
					this.y = Game.height - height;
					 System.out.println("dół");
				}
			}
		}
		

	}

	void render(Graphics g) {
		g.setColor(c);
		g.fillRect(x, y, width, height);
	}

}
