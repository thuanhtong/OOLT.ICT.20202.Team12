package hust.soict.ict.miniproject.box;

import java.util.ArrayList;
import javax.swing.JLabel;

import hust.soict.ict.miniproject.screen.GameScreen;
import hust.soict.ict.miniproject.screen.GamePanel;
import hust.soict.ict.miniproject.stone.Stone;

public abstract class Box {
	
	private GamePanel gamePanel;
	private ArrayList<Stone> stonesInBox = new ArrayList<Stone>(); //List of stones currently in the box
	private JLabel numOfStoneLbl = new JLabel(); //Display the number of stones in the box

	public ArrayList<Stone> getStonesInBox() {
		return stonesInBox;
	}


	public void setStonesInBox(ArrayList<Stone> stonesInBox) {
		this.stonesInBox = stonesInBox;
	}


	public void setNumOfStoneLbl() {
		numOfStoneLbl.setText(Integer.toString(stonesInBox.size()));
	}


	public Box(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		numOfStoneLbl = new JLabel();
		numOfStoneLbl.setFont(GameScreen.font);
		gamePanel.add(numOfStoneLbl);
	}


	public JLabel getNumOfStoneLbl() {
		return numOfStoneLbl;
	}
	
	public void resetStone() {
		if(!getStonesInBox().isEmpty())
			stonesInBox.clear();
	}

	public abstract void setVisual();
}
