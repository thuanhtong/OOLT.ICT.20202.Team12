package hust.soict.ict.miniproject.controller;

import java.util.Random;
import javax.swing.JOptionPane;
//import javax.swing.Timer;

import hust.soict.ict.miniproject.bot.*;
import hust.soict.ict.miniproject.box.MandarinBox;
import hust.soict.ict.miniproject.box.PlayerBox;
import hust.soict.ict.miniproject.box.ScoreBox;
import hust.soict.ict.miniproject.box.SquareBox;
import hust.soict.ict.miniproject.screen.GamePanel;
import hust.soict.ict.miniproject.stone.Stone;

public class GameController {
	private GamePanel gamePanel;
	private PlayerBox[] playerBox = new PlayerBox[12];
	private ScoreBox[] scoreBox = new ScoreBox[2];
	private Stone[] stone = new Stone[70];
	private Bot bot;
	private String player1, player2;
	private int curPlayer;
	private int mode;
	private int level;
	private boolean firstPlay;
	
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getCurPlayer() {
		return curPlayer;
	}
	

	public PlayerBox[] getPlayerBox() {
		return playerBox;
	}
	
	public boolean isFirstPlay() {
		return firstPlay;
	}

	public void setFirstPlay(boolean firstPlay) {
		this.firstPlay = firstPlay;
	}

	public GameController(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		init();
	}
	
	public void init() {
		firstPlay = true;
		for(int i=0; i<12; i++) {
			if(i==5 || i==11)
				playerBox[i] = new MandarinBox(gamePanel, this,i);
			else playerBox[i]=new SquareBox(gamePanel, this,i);
			playerBox[i].setVisual();
		}
		
		for(int i=0; i<2; i++) {
			scoreBox[i] = new ScoreBox(gamePanel, i);
			scoreBox[i].setVisual();
		}
		
		for(int i=0; i<70; i++) {
			stone[i] = new Stone(gamePanel);
			stone[i].setVisible(false);
		}	
	}
	
	//reset stones
	public void reset() {
		firstPlay = true;
		removeAllArrow();
		curPlayer = new Random().nextInt(2);
		if(curPlayer==0) JOptionPane.showMessageDialog(gamePanel, player1+ " plays first!");
		else JOptionPane.showMessageDialog(gamePanel, player2 + " plays first!");
		int cnt = 0;
		for(int i=0; i<12; i++) {
			playerBox[i].resetStone();
			playerBox[i].initStones(stone, cnt);
			cnt += playerBox[i].initNumOfStones();
			playerBox[i].setNumOfStoneLbl();
		}
		
		for(int i=0; i<2; i++) {
			scoreBox[i].resetStone();
			scoreBox[i].setNumOfStoneLbl();
			scoreBox[i].setNameLabel((i==0)?player1:player2);
		}
		
		scoreBox[curPlayer].setFlag(true);
		scoreBox[1-curPlayer].setFlag(false);
		
		for(int i=0; i<70; i++)
			stone[i].setVisible(true);
		if(mode == 1) {
			if(level == 1) bot = new BotHard(this);
			else bot = new BotEasy(this);
			if(curPlayer == 1) bot.botPlay();
			else firstPlay = false;
			
		}
		else firstPlay = false;
	}
	
	public void removeAllArrow() {
		//remove all arrows of square box
		for(int i=0;i<12;i++) {
			if(playerBox[i] instanceof SquareBox)
				((SquareBox)playerBox[i]).hideArrow();
		}
	}
	
	public int getNextPos(int curPos, int direction, int step) {
		return (curPos+direction*step +6*12)%12;
	}
	
	public void moveStone(int pos, int direction, boolean stopSpreadStone) {
		int n= playerBox[pos].getStonesInBox().size();
		if(n==0){
			if(playerBox[getNextPos(pos,direction,1)].getStonesInBox().size()>0){
				moveToScoreBox(curPlayer,getNextPos(pos,direction,1));
				moveStone(getNextPos(pos,direction,2), direction, true);
			}
			else nextTurn();
			
		}
		else{
			if(pos==5||pos==11) nextTurn();
			else if(!stopSpreadStone){
				for(int i=1; i<=n; i++) {
					int nextPos = getNextPos(pos, direction, i);
					Stone stone= playerBox[pos].getStonesInBox().remove(0);
					playerBox[pos].setNumOfStoneLbl();
					stone.moveToPos(nextPos);
					playerBox[nextPos].getStonesInBox().add(stone);
					playerBox[nextPos].setNumOfStoneLbl();					
				}
				int finalPos= getNextPos(pos, direction, n+1);
				moveStone(finalPos,direction,false);
			}
			else nextTurn();
		}
	}

	public void nextTurn() {
		
		curPlayer = 1-curPlayer;
		
		int status = checkContinue(curPlayer);
		if(status==0) {
			curPlayer = -1;
			int score0 = scoreBox[0].getStonesInBox().size()+getTotalStones(0);
			int score1 = scoreBox[1].getStonesInBox().size()+getTotalStones(1);
			if(score0 > score1) endGame(0);
			else if(score0<score1) endGame(1);
			else endGame(-1); //draw
		}
		else if(status==-1) {
			curPlayer = -1;
			endGame(1-curPlayer);
		}
		else if(status==1) spreadStones(curPlayer);
		
		if(curPlayer == -1) return;
		scoreBox[curPlayer].setFlag(true);
		scoreBox[1-curPlayer].setFlag(false);
		if(curPlayer == 1 && mode == 1) bot.botPlay();
	}
	
	public void moveToScoreBox(int curPlayer, int pos) {
		//move eaten stones into score box
		int n = playerBox[pos].getStonesInBox().size();
		
		for(int i=0; i<n; i++) {
			Stone stone = playerBox[pos].getStonesInBox().remove(0);
			playerBox[pos].setNumOfStoneLbl();
			scoreBox[curPlayer].getStonesInBox().add(stone);
			scoreBox[curPlayer].setNumOfStoneLbl();
			stone.moveToPos(curPlayer-2);
		}
	}
	
	public int checkContinue(int curPlayer) {
		//check if there is a end game situation or out of stones in current player's turn
		if(playerBox[5].getStonesInBox().size()==0 && playerBox[11].getStonesInBox().size()==0) return 0; // out of stone in mandarin boxes
		else if(getTotalStones(curPlayer)==0) {
			if(scoreBox[curPlayer].getStonesInBox().size()<5) return -1; //player run out of stone
			else return 1;
		} 
		else return 2;
	}
	
	public int getTotalStones(int curPlayer) {
		int total = 0;
		if(curPlayer==0)	
			for(int i=6; i<11; i++) {
				total+=playerBox[i].getStonesInBox().size();
			}
		else for(int i=0; i<5; i++) {
				total+=playerBox[i].getStonesInBox().size();
			}
		return total;
	}
	
	public void endGame(int winPlayer) {
		if(winPlayer==0) JOptionPane.showMessageDialog(gamePanel, player1 + " win with " + (getTotalStones(winPlayer)+scoreBox[winPlayer].getStonesInBox().size()) + " Stones!","Game over",JOptionPane.INFORMATION_MESSAGE);
		else if(winPlayer==1) JOptionPane.showMessageDialog(gamePanel, player2+ " win with "+ (getTotalStones(winPlayer)+scoreBox[winPlayer].getStonesInBox().size())+ " Stones!","Game over",JOptionPane.INFORMATION_MESSAGE);
		else JOptionPane.showMessageDialog(gamePanel,"Draw","Game over",JOptionPane.INFORMATION_MESSAGE);
		gamePanel.newGame();
	}
	
	public void startGame() {
		if(mode==2) {
			player1 = JOptionPane.showInputDialog(this.gamePanel, "Enter Player1's name:", "Start Game", JOptionPane.QUESTION_MESSAGE);
			player2 = JOptionPane.showInputDialog(this.gamePanel, "Enter Player2's name:", "Start Game", JOptionPane.QUESTION_MESSAGE);
		}
		else {
			player1 = JOptionPane.showInputDialog(this.gamePanel, "Enter your name:", "Start Game", JOptionPane.QUESTION_MESSAGE);
			player2 = "Bot";
		}
		if(player1==null || player1.isBlank()) player1 = "Player 1";
		if(player2==null || player2.isBlank()) player2 = "Player 2";
	}
	
	public void spreadStones(int curPlayer) {
		if(curPlayer == 1) {
			for(int i=0; i<5; i++) boxSpread(curPlayer, i);
		}
		else for(int i=6;i<11;i++) boxSpread(curPlayer,i);
	}
	
	public void boxSpread(int curPlayer, int pos) {
		Stone stone = scoreBox[curPlayer].getStonesInBox().remove(0);
		scoreBox[curPlayer].setNumOfStoneLbl();
		stone.moveToPos(pos);
		playerBox[pos].getStonesInBox().add(stone);
		playerBox[pos].setNumOfStoneLbl();
	}
}
