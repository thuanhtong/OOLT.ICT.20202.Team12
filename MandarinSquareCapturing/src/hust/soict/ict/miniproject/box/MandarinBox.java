package hust.soict.ict.miniproject.box;

import hust.soict.ict.miniproject.controller.GameController;
import hust.soict.ict.miniproject.screen.GamePanel;

public class MandarinBox extends PlayerBox{

	public MandarinBox(GamePanel gamePanel, GameController gameController, int pos) {
		super(gamePanel, gameController, pos);
//		setVisual();
	}

	@Override
	public void setVisual() {
		if(getPos()==11) getNumOfStoneLbl().setBounds(200, 250, 50, 50);
		else getNumOfStoneLbl().setBounds(1050, 250, 50, 50);
	}

	@Override
	public int initNumOfStones() {
		return 10;
	}
}
