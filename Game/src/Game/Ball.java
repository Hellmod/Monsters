package Game;

import java.awt.Color;
import java.awt.Graphics;

public class Ball
{

	public int x, y;
	public static int width = 25, height = 25;
	private int speed;

	public int motionX, motionY;

	public  int getSpeed()
	{
		return speed;
	}


	public void setSpeed(int speed)
	{
		this.speed = speed;
	}


	public int getX()
	{
		return x;
	}


	public int getY()
	{
		return y;
	}

	public Ball(int x,int y,int motionY,int motionX,int speed)
	{
		this.x=x;
		this.y=y;
		this.motionX=motionX;
		this.motionY=motionY;
		this.speed=speed;
	}
	
	public Ball(int x,int y,int motionY,int motionX)
	{
		this.x=x;
		this.y=y;
		this.motionX=motionX;
		this.motionY=motionY;
		this.speed=5;		
	}

	public void update()
	{
		//int speed = 2;

		this.x += motionX * speed;
		this.y += motionY * speed;

		if (this.y + height - motionY > Game.height || this.y + motionY < 0)
		{
			if (this.motionY < 0)
			{
//				this.y = 0;
//				this.motionY = speed;
//				//System.out.println("góra");
				Game.lis.remove(this);
			}
			else
			{
//				this.motionY = -speed;
//				this.y = pong.height - height;
//				//System.out.println("dó³");
				Game.lis.remove(this);
			}
		}
		else if (this.x + width - motionX > Game.width || this.x + motionX < 0)
		{
			if (this.motionX < 0)
			{
//				this.x = 0;
//				this.motionX = speed;
//				//System.out.println("lewo");
				Game.lis.remove(this);
				
			}
			else
			{
//				this.motionX = -speed;
//				this.x = pong.width - width;	
//				System.out.println("prawo");
				Game.lis.remove(this);
				
			}
		}

	}
	


	public void render(Graphics g)
	{
		g.setColor(Color.white);
		g.fillOval(x, y, width, height);
	}

}
