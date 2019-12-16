package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Shot implements ActionListener 
{
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(Game.game.rs)
			Game.listBall.add(new Ball(Game.game.player.getX() ,Game.game.player.getY()+(Game.game.player.getHeight()/2)-Ball.height/2,0,2,Integer.parseInt(Game.game.menu.tBallSpeed.getText())));
		if(Game.game.ls)
			Game.listBall.add(new Ball(Game.game.player.getX() ,Game.game.player.getY()+(Game.game.player.getHeight()/2)-Ball.height/2,0,-2,Integer.parseInt(Game.game.menu.tBallSpeed.getText())));
		if(Game.game.ds)
			Game.listBall.add(new Ball(Game.game.player.getX() +(Game.game.player.getWidth()/2)-Ball.width/2,Game.game.player.getY(),2,0,Integer.parseInt(Game.game.menu.tBallSpeed.getText())));
		if(Game.game.us)
			Game.listBall.add(new Ball(Game.game.player.getX() +(Game.game.player.getWidth()/2)-Ball.width/2 ,Game.game.player.getY(),-2,0,Integer.parseInt(Game.game.menu.tBallSpeed.getText())));
	}

}
