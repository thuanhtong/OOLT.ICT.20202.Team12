package hust.soict.ict.miniproject.bot;

import java.util.Random;

import hust.soict.ict.miniproject.box.PlayerBox;
import hust.soict.ict.miniproject.controller.GameController;

public class BotEasy extends Bot{

	public BotEasy(GameController gameController) {
		super(gameController);
	}

	@Override
	public int getMove() {
		int pos;
		Random random = new Random();
		PlayerBox[] playerBox = getGameController().getPlayerBox();
		while(true) {
			pos = random.nextInt(5);
			if(playerBox[pos].getStonesInBox().size()>0){
				if(random.nextInt(2)==0) return pos+5;	
				else return pos;
			}
			else continue;
		}
	}

}
