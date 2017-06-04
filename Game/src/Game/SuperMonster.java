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
		this.x = 0;
		this.y = 500;
		health = 3;
		c = new Color(0, 255, 125);
		calculate();
	}

	private void calculate()
	{
		double angle;
		int a=this.x-Game.game.player.getX();
		int c=this.y-Game.game.player.getY();
		if(a<0)a*=-1;
		if(c<0)c*=-1;
		if(a==0)a=1;
		angle = (double)c/a;
		motionX=speed;
		motionY=(int) (motionX*angle);

		
	}

	void update() {
		calculate();
		if (health == 2)
			c = new Color(255, 0, 125);
		if (health == 1)
			c = new Color(125, 0, 255);
		if (health == 0)
			Game.lisM.remove(this);

		// System.out.println("WOO");

		this.x += motionX * speed;
		this.y += motionY * speed;

		for (int i = 0; i < Game.lis.size(); i++) {
			if (this.y + height >= Game.lis.get(i).getY()) {

				if (this.y <= Game.lis.get(i).getY() + Ball.height)
					if (x + width >= Game.lis.get(i).x) {
						if (x <= Game.lis.get(i).x + Ball.width) {
							health--;
							Game.lis.remove(i);
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
						Game.lisM.remove(this);
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
				// System.out.println("dó³");
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

	}

	void render(Graphics g) {
		g.setColor(c);
		g.fillRect(x, y, width, height);
	}

}
