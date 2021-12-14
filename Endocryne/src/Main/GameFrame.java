package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame {
	
	public Stage stage = new Stage();
	
	public long fps = 60;
	
	public final Game game;
	
	public GameFrame(Game game, int width, int height) {
		this.game = game;
		setBounds(0, 0, width, height);
		setLocationRelativeTo(null);
		setTitle("Endocryne");
		addKeyListener(new Controls());
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		setContentPane(stage);
		setVisible(true);
		
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				repaint();
			}
		}, 0, (int)(1d/fps * 1000d), TimeUnit.MILLISECONDS);
	}
	
	public class Stage extends JPanel {
		
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setColor(new Color(200, 200, 255)); // Sky color
			g2.fillRect(0, 0, getWidth(), getHeight());
			
			try {
				for(Block block : game.blocks) {
					switch(block.type) {
						case DIRT: break;
						default:
							g2.setColor(new Color(40, 140, 40));
					}
					g2.fillRect(block.x * game.blockSize, getHeight() - (block.y * game.blockSize) - game.blockSize, game.blockSize, game.blockSize);
				}
			} catch(Exception e) {} // Ignored
		}
		
	}

}
