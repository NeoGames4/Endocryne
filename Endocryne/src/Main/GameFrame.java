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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				repaint();
				update();
			}
		}, 100, (int)(1d/fps * 1000d), TimeUnit.MILLISECONDS);
	}
	
	public final float gravity = -0.01f;
	
	public void update() {
		Player player = game.player;
		if((Controls.wDown || Controls.spaceDown) && player.y == game.getGroundHeight(player.x)) {
			player.vy = player.jumpSpeed;
		}
		for(Player p : game.players) {
			if(p.y + p.vy < game.getGroundHeight(p.x)) p.vy = game.getGroundHeight(p.x) - p.y;
			p.y += p.vy;
			p.vy += gravity;
		}
		if(Controls.dDown) {
			player.x += player.movementSpeed;
		}
		if(Controls.aDown) {
			player.x -= player.movementSpeed;
		}
	}
	
	public class Stage extends JPanel {
		
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setColor(new Color(200, 200, 255)); // Sky color
			g2.fillRect(0, 0, getWidth(), getHeight());
			
			try { // Blocks
				int playerX = (int)(game.player.x*game.blockSize);
				int playerY = (int)(game.player.y*game.blockSize);
				for(Block block : game.blocks) {
					switch(block.type) {
						case DIRT: break;
						default:
							g2.setColor(new Color(40, 140, 40));
					}
					g2.fillRect(block.x * game.blockSize - playerX, getHeight() - (block.y * game.blockSize) - game.blockSize, game.blockSize, game.blockSize);
					for(int y = block.y-1; y>=0; y--) {
						g2.setColor(new Color(80, 50, 40));
						g2.fillRect(block.x * game.blockSize, getHeight() - (y * game.blockSize) - game.blockSize, game.blockSize, game.blockSize);
					}
				}
			} catch(Exception e) {} // Ignored
			
			// Player
			g2.setColor(Color.MAGENTA);
			g2.drawRect((int)(getWidth()/2) - game.player.hitBoxWidth/2, getHeight() - (int)(game.player.y*game.blockSize) - game.player.hitBoxHeight, game.player.hitBoxWidth, game.player.hitBoxHeight);
			g2.setColor(Color.GREEN);
			g2.fillOval((int)(getWidth()/2) - 2, getHeight() - (int)(game.player.y*game.blockSize) - 2, 4, 4);
			
			try { // Players
				for(Player player : game.players) {
					if(player == game.player) continue;
					if(Launcher.DEBUG) {
						g2.setColor(Color.MAGENTA);
						g2.drawRect((int)(player.x*game.blockSize) - player.hitBoxWidth/2, getHeight() - (int)(player.y*game.blockSize) - player.hitBoxHeight, player.hitBoxWidth, player.hitBoxHeight);
						g2.setColor(Color.YELLOW);
						g2.fillOval((int)(player.x*game.blockSize) - 2, getHeight() - (int)(player.y*game.blockSize) - 2, 4, 4);
					}
				}
			} catch(Exception e) {} // Ignored
		}
		
	}

}
