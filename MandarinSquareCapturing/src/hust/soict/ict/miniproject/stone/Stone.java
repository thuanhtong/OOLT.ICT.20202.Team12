package hust.soict.ict.miniproject.stone;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.util.Random;

import hust.soict.ict.miniproject.screen.GamePanel;

public class Stone extends JLabel{
	
	private int x=0, y=0;
	Random random= new Random();
	private GamePanel gamePanel;
	public Stone(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		ImageIcon img = new ImageIcon("src/images/stone00.png");
		setIcon(img);
		setBounds(x,y,img.getIconWidth(),img.getIconHeight());
		gamePanel.add(this);
	}
	
	public int getX(int pos) {
		if(pos<5 && pos >=0) 
			return 270 + pos*155 + random.nextInt(100);
		else if (pos>5 && pos<11) 
			return 270 + (10-pos)*155 + random.nextInt(100);
		else if(pos == 11) 
			return 120 + random.nextInt(80);
		else if (pos==5) 
			return 1040 + random.nextInt(80);
		else 
			return 540 + random.nextInt(100);
	}

	public int getY(int pos) {
		if(pos<5 && pos >=0) 
			return 350+ random.nextInt(100);
		else if (pos>5 && pos<11) 
			return 200+ random.nextInt(100);
		else if(pos==11 || pos ==5)
			return 270+ random.nextInt(100);
		else if(pos ==-2)
			return 50 + random.nextInt(40);
		else 
			return 550 + random.nextInt(40);
	}
	
	public void initPos(int pos) {
		x = getX(pos);
		y = getY(pos);		
		setLocation(x, y);
	}
	
	public void moveToPos(int pos) {
		int newX = getX(pos);
		int newY = getY(pos);
		
		int subImages;
		if(pos<0) subImages = 4; //if move to mandarin box -> move quickly
		else subImages = 40; //if move to square box -> move slowly
		
		for(int i=0; i<=subImages; i++) {
			setLocation(x+(int)((newX-x)*i/subImages), y+(int)((newY-y)*i/subImages));
			gamePanel.paintImmediately(gamePanel.getVisibleRect());
		}
		x = newX;
		y = newY;
	}
}
