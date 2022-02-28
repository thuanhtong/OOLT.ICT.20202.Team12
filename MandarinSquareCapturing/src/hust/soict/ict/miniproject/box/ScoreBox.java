package hust.soict.ict.miniproject.box;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import hust.soict.ict.miniproject.screen.GameScreen;
import hust.soict.ict.miniproject.screen.GamePanel;

public class ScoreBox extends Box{

	private JLabel flagLabel = new JLabel(), nameLabel = new JLabel();
	private int player;
	public ScoreBox(GamePanel gamePanel, int player) {
		super(gamePanel);
		this.player = player;
		ImageIcon flag = new ImageIcon("src/images/flag.png");
		flagLabel.setIcon(flag);
		flagLabel.setVisible(false);
//		setVisual();
		
		gamePanel.add(flagLabel);
		gamePanel.add(nameLabel);
	}
	
	public void setNameLabel(String name) {
		nameLabel.setText(name);
		nameLabel.setFont(GameScreen.font);
	}
	
	public void setFlag(boolean isTurn) {
		//set flag visible if it's the player turn
		flagLabel.setVisible(isTurn);
	}

	@Override
	public void setVisual() {
		Icon flag = flagLabel.getIcon();
			if(player==0) {
				getNumOfStoneLbl().setBounds(550, 50 , 100, 50);
				nameLabel.setBounds(550, 15, 150, 50);
				flagLabel.setBounds(725, 50, flag.getIconWidth(), flag.getIconHeight());
			}
			else if(player==1) {
				getNumOfStoneLbl().setBounds(550, 550, 100, 50);
				nameLabel.setBounds(550, 650, 150, 50);
				flagLabel.setBounds(725, 550, flag.getIconWidth(), flag.getIconHeight());
			}
	}
}
