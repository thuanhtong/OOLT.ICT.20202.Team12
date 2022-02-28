package hust.soict.ict.miniproject.screen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GameScreen extends JFrame {
	
	public final static Font font = new Font("Arial",Font.PLAIN,(int)(30)); //font used throughout the game
	private static GamePanel gamePanel = new GamePanel(); //gamePanel is a JPanel containing all components (except Menu Bar) appear in the game screen

	public GameScreen() {
		setLayout(new BorderLayout());
		add(createMenu(), BorderLayout.NORTH);
		add(gamePanel, BorderLayout.CENTER);
		setSize(1290,780);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setTitle("Mandarin Square Capturing Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);        
	}
	
	JMenuBar createMenu() {
		JMenu mnGame = new JMenu("Game");
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePanel.newGame();
			}
		});
		JMenuItem endGame = new JMenuItem("End Game");
		endGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(gamePanel, "Are you sure you want to exit the game?", "Confirm to exit the game",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
				if(option ==JOptionPane.YES_OPTION)	System.exit(0);;
			}
		});
		mnGame.add(newGame);
		mnGame.add(endGame);
		
		JMenu mnHelp = new JMenu("Help");
		JMenuItem guide = new JMenuItem("Guide");
		guide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(gamePanel,"You select a square and a direction to spread the gems."
						+ "\nYou got points when after finishing spreading, there is one empty square followed by a square with gems."
						+ "\nThe game ends when there is no gem in both half-circles."
						+ "\nGood luck!", "Guide", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		JMenuItem info = new JMenuItem("Information");
		info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(gamePanel,"Mandarin Square Capturing"
						+ "\nCreator: Thu Anh & Thu Giang", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnHelp.add(guide);
		mnHelp.add(info);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(mnGame);
		menuBar.add(mnHelp);
		return menuBar;
	}
	
	public static void main(String[] args) {
		new GameScreen();
		gamePanel.hello();
	}
}
