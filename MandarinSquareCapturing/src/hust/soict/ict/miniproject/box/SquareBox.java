package hust.soict.ict.miniproject.box;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import hust.soict.ict.miniproject.controller.GameController;
import hust.soict.ict.miniproject.screen.GamePanel;

public class SquareBox extends PlayerBox{

	private JButton playerBoxBtn = new JButton(), rArrow = new JButton(), lArrow = new JButton();
	
	public SquareBox(GamePanel gamePanel, GameController gameController, int pos) {
		super(gamePanel, gameController, pos);
		
//		add square box button
		addBoxBtn(gamePanel, gameController);
		
//		add right arrow
		addArrow(gamePanel, gameController, rArrow, "src/images/r_arrow.png", 1);
		
//		add left arrow
		addArrow(gamePanel, gameController, lArrow, "src/images/l_arrow.png", -1);
	}
	
	public void addBoxBtn(GamePanel gamePanel, GameController game) {
		makeBtnTransparent(playerBoxBtn);
		playerBoxBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.removeAllArrow();
				int player = game.getCurPlayer();
				if(getStonesInBox().size() >0) {
					if((player==1 && pos <5) || (player==0 &pos>5)) showArrow();
				}
			}
		});
		gamePanel.add(playerBoxBtn);
	}
	
	public void addArrow(GamePanel gamePanel, GameController gameController, JButton arrow, String imgPath, int dir) {
		ImageIcon imgArrow = new ImageIcon(imgPath);
		arrow.setIcon(imgArrow);
		makeBtnTransparent(arrow);
		arrow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int direction;
				if(pos<6) direction=dir;
				else direction=(-1)*dir;
				hideArrow();
				gameController.moveStone(pos,direction,false);
			}
		});
		arrow.setVisible(false);
		gamePanel.add(arrow);
	}
	
	public void showArrow() {
		rArrow.setVisible(true);
		lArrow.setVisible(true);
	}
	
	public void hideArrow() {
		rArrow.setVisible(false);
		lArrow.setVisible(false);
	}
	
	
	public void makeBtnTransparent(JButton btn){
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
	}

	@Override
	public void setVisual() {
		int col;
		if(pos <6) {
			col = pos;
			playerBoxBtn.setBounds(280+col*155, 350, 120, 150);
			getNumOfStoneLbl().setBounds(270+col*155, 350, 50, 50);
			
			rArrow.setBounds(400+col*155, 390, 50, 50);
			lArrow.setBounds(220+col*155, 390, 50, 50);
		}
		else {
			col = 10-pos;
			playerBoxBtn.setBounds(280+col*155, 200, 120, 150);
			getNumOfStoneLbl().setBounds(270+col*155, 200, 50, 50);
			
			rArrow.setBounds(400+col*155, 240, 50, 50);
			lArrow.setBounds(220+col*155, 240, 50, 50);
		}
	}

	@Override
	public int initNumOfStones() {
		return 5;
	}
}
