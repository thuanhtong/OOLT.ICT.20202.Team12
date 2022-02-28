package hust.soict.ict.miniproject.bot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

import hust.soict.ict.miniproject.controller.GameController;

public abstract class Bot {
	private GameController gameController;
	
	public GameController getGameController() {
		return gameController;
	}
	public Bot(GameController gameController) {
		this.gameController = gameController;
	}
	
	public abstract int getMove();
	
	public void botPlay() {
		int timePrepare = new Random().nextInt(2)+1;
		Timer timer = new Timer(0, new ActionListener() {
		    public void actionPerformed(ActionEvent ae) {
		    	 try {
						Thread.sleep(1000*timePrepare);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    	 int move = getMove();
		    	 if(move < 5) gameController.moveStone(move, 1, false);
		    	 else gameController.moveStone(move-5, -1, false);
		    }
		});
		timer.setRepeats(false);
	 	timer.start();
	}
}
