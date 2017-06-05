package Game;

import java.awt.Color;
import java.awt.Graphics;

public class SuperMonster extends Monster {
	public int x, y, width = 25, height = 25;
	int speed = 2;

	public int motionX, motionY, health;
	Color c;

	public SuperMonster() {// tworzenie uperMonster

		this.x=Game.game.random.nextInt(Game.height);
		this.y=Game.game.random.nextInt(Game.width);		
		health = 3;
		c = new Color(0, 255, 125);
		motionX = 2;
		motionY = 2;
	}

	private void calculate() {
			
		// wykrywanie po³o¿enia gracza
		if (this.y + height - motionY > Game.game.player.getY()) {
			if(this.x + width - motionX > Game.game.player.getX()){
				//System.out.println("góra_lewo");
				if(motionX>0)
					motionX*=-1;
				if(motionY>0)
					motionY*=-1;
			}				
			else{
				//System.out.println("góra_prawo");
				if(motionX<0)
					motionX*=-1;
				if(motionY>0)
					motionY*=-1;
								
			}				
		}
		else{
			if(this.x + width - motionX > Game.game.player.getX()){
				//System.out.println("dó³_lewo");
				if(motionX>0)
					motionX*=-1;
				if(motionY<0)
					motionY*=-1;

			}
			else{
				//System.out.println("dó³_prawo");
				if(motionX<0)
					motionX*=-1;
				if(motionY<0)
					motionY*=-1;

			}				
		}


	}

	void update() {
		calculate();
		if (health == 0)
			Game.lisM.remove(this);
		else if (health == 1)
			c = new Color(125, 0, 255);
		else if (health == 2)
			c = new Color(255, 0, 125);
		//róch
		this.x += motionX * speed;
		this.y += motionY * speed;

		// wykrywanie kolizji z pociskiem
		for (int i = 0; i < Game.lis.size(); i++) {
			if (this.y + height >= Game.lis.get(i).getY()) {

				if (this.y <= Game.lis.get(i).getY() + Ball.height)
					if (x + width >= Game.lis.get(i).x) {
						if (x <= Game.lis.get(i).x + Ball.width) {
							health--;
							Game.lis.remove(i);
							 //TODO Add player points 
						}
					}
			}
		}

		// wykrywanie kolizji z graczem
		if (this.y + height >= Game.game.player.getY()) {
			if (this.y <= Game.game.player.getY() + Game.game.player.getHeight())
				if (x + width >= Game.game.player.getX())
					if (x <= Game.game.player.getX() + Game.game.player.getWidth()) {
						int l = Game.game.player.getHealth();
						Game.game.player.setHealth(l - 1);
						Game.lisM.remove(this);
					}
		}

		

	}

	void render(Graphics g) {
		g.setColor(c);
		g.fillRect(x, y, width, height);
	}


}
