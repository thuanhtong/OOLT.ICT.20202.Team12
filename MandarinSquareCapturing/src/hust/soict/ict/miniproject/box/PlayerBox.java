package hust.soict.ict.miniproject.box;

import hust.soict.ict.miniproject.controller.GameController;
import hust.soict.ict.miniproject.screen.GamePanel;
import hust.soict.ict.miniproject.stone.Stone;

public abstract class PlayerBox extends Box{

	private GameController gameController;
	int pos;
	
	public int getPos() {
		return pos;
	}

	public PlayerBox(GamePanel gamePanel, GameController gameController, int pos) {
		super(gamePanel);
		this.gameController = gameController;
		this.pos = pos;
	}

	public abstract int initNumOfStones();
	
	public void initStones(Stone[] stone, int idxStone) {
		int num = initNumOfStones();
		for(int i=0; i<num; i++) {
			stone[idxStone].initPos(pos);
			getStonesInBox().add(stone[idxStone++]);
		}
	}
}
