package hust.soict.ict.miniproject.bot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import hust.soict.ict.miniproject.box.PlayerBox;
import hust.soict.ict.miniproject.controller.GameController;
import hust.soict.ict.miniproject.stone.Stone;

public class BotHard extends Bot {
	private int virtualCnt;	//to calculate prediction result of bot
	private ArrayList<Stone>[] stoneList = new ArrayList[12];
	public BotHard(GameController gameController) {
		super(gameController);
	}
	
	public void getCurrentStatus(){
		PlayerBox[] playerBox = getGameController().getPlayerBox();
		for(int i=0; i<12; i++) {
			stoneList[i] = new ArrayList<Stone>();
			for(Stone stone: playerBox[i].getStonesInBox()) 
				stoneList[i].add(stone);				
		}
	}
	
	private int botCalculate(int pos, int direction) {
		virtualCnt = 0;
		getCurrentStatus();
		virtualMoveStone(pos, direction, false);
		return virtualCnt;
	}
	
	private void virtualMoveStone(int pos, int direction, boolean stopSpreadStone) {
		int n = stoneList[pos].size();
		if(n>0){
			if(pos==5||pos==11) 
				return;
			else if(!stopSpreadStone){
				for(int i=1; i<=n; i++) {
					int nextPos = getGameController().getNextPos(pos, direction, i);
					Stone stone= stoneList[pos].remove(0);
					stoneList[nextPos].add(stone);	
				}
				int finalPos= getGameController().getNextPos(pos, direction, n+1);
				virtualMoveStone(finalPos,direction,false);
			}
			else 
				return;
		}
		else{
			int nextPos = getGameController().getNextPos(pos, direction, 1);
			if(stoneList[nextPos].size()>0){
				virtualCnt+=stoneList[nextPos].size();
				stoneList[nextPos].clear();
				virtualMoveStone(getGameController().getNextPos(pos,direction,2), direction, true);
			}
			else
				return;
		}
		
	}
	
	public ArrayList<Integer> getPredictResult(){
		ArrayList<Integer> predictResult = new ArrayList<Integer>();
		PlayerBox[] playerBox = getGameController().getPlayerBox();
		for(int i=0; i<5; i++)	
			if(playerBox[i].getStonesInBox().size() == 0) predictResult.add(-1);
			else {
				botCalculate(i, 1);
				predictResult.add(virtualCnt);				
			}
	
		for(int i=0; i<5; i++) 
			if(playerBox[i].getStonesInBox().size()==0) 
				predictResult.add(-1);
			else {
				botCalculate(i, -1);
				predictResult.add(virtualCnt);			
			}
		
		return predictResult;
	}

	@Override
	public int getMove() {
		getCurrentStatus();
		int move;
		if(getGameController().isFirstPlay() == true) {//bot move first at the beginning of the game
			ArrayList<Integer> firstMove = new ArrayList<Integer>();
			firstMove.add(1);
			firstMove.add(3);
			firstMove.add(6);
			firstMove.add(8);
			getGameController().setFirstPlay(false);
			move = firstMove.get(new Random().nextInt(firstMove.size()));
			//first move bot should move the box at position 1 or 3 (either direction left or right is ok)
			//because other moves will let the other player can earn many stones
				
			}
		else {
			ArrayList<Integer> predictResult = getPredictResult();
			int max = Collections.max(predictResult);
			ArrayList<Integer> bestOptions = new ArrayList<Integer>();
			for(int i=0; i<10; i++)
				if(predictResult.get(i) == max) bestOptions.add(i);
					 
			move = bestOptions.get(new Random().nextInt(bestOptions.size())); //get a random option from the set of best options
		}		
		return move;
	}
}
