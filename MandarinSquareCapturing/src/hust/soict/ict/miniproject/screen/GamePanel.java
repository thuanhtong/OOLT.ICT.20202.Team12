package hust.soict.ict.miniproject.screen;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import hust.soict.ict.miniproject.controller.GameController;

public class GamePanel extends JPanel{

	private GameController gameController;
	private Image background;
	
	public GamePanel() {
		setLayout(null);
		try {
			background = ImageIO.read(new File("src/images/background.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setSize(GameScreen.WIDTH,GameScreen.HEIGHT);
		gameController = new GameController(this);
	}
	
	public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(background, 0, 0, null);
	}
	
	public void hello() {
		JOptionPane.showMessageDialog(this, "Welcome to our Mandaring Square Capturing game!\n"
				+ "See game menu to select the game mode and see help menu for instructions.\n"
				+ "Wish you have fun.", 
                "Mandarin Square Capturing", JOptionPane.INFORMATION_MESSAGE);
		newGame();
	}
	//when player want to play a new game
	public void newGame() {
		int option = JOptionPane.showConfirmDialog(this, "Do you want to play a new game?", "New Game",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
		if(option == JOptionPane.YES_OPTION) startGame();
	}
	
	//start a new game, choose playing mode
	public void startGame() {
		Object[] mode = {"1 Player", "2 Players"};
		Object[] level = {"Easy", "Hard"};
		int option = JOptionPane.showOptionDialog(this, "Chose playing mode", "Start Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, mode , mode[1]);
		if(option == JOptionPane.YES_OPTION) {	
			gameController.setMode(1);
			int lvOption = JOptionPane.showOptionDialog(this, "Chose difficulty", "Play with bot", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, level , level[0]);
			if(lvOption == JOptionPane.YES_OPTION)
				gameController.setLevel(0);
			else if(lvOption == JOptionPane.NO_OPTION) gameController.setLevel(1);
			else return;
		}
		else if(option == JOptionPane.NO_OPTION){
			gameController.setMode(2);
		} else return;
		gameController.startGame();
		gameController.reset();
	} 

}
