package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame {
	
	public Stage stage = new Stage();
	
	public long fps = 60;
	public float currentFps = 0;
	
	public final Game game;
	
	long ticks = 0;
	long lastFrameTime;
	
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
		
		lastFrameTime = System.currentTimeMillis();
		
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
		if((Controls.wDown || Controls.spaceDown) && (player.y == ((int)(player.x-player.hitBoxWidth/2d/game.blockSize) == (int)(player.x+player.hitBoxWidth/2d/game.blockSize) ? game.getGroundHeight(player.x) : Math.max(game.getGroundHeight(player.x-player.hitBoxWidth/2d/game.blockSize), game.getGroundHeight(player.x+player.hitBoxWidth/2d/game.blockSize))))) {
			player.vy = player.jumpSpeed;
		}
		for(Player p : game.players) {
			float groundHeight = (int)(p.x-p.hitBoxWidth/2d/game.blockSize) == (int)(p.x+p.hitBoxWidth/2d/game.blockSize) ? game.getGroundHeight(p.x) : Math.max(game.getGroundHeight(p.x-p.hitBoxWidth/2d/game.blockSize), game.getGroundHeight(p.x+p.hitBoxWidth/2d/game.blockSize));
			if(p.y + p.vy < groundHeight) p.vy = groundHeight - p.y;
			p.y += p.vy;
			p.vy += gravity;
		}
		if(Controls.dDown) {
			if(game.getGroundHeight(player.x+1) > player.y && (int)(player.x + player.movementSpeed + player.hitBoxWidth/2d/game.blockSize) != (int)(player.x)) {
				int correction = player.x+1 < 0 ? 0 : 1;
				player.x += (int)(player.x+correction) - (player.x + player.hitBoxWidth/2d/game.blockSize) - 0.0001;
			}
			else player.x += player.movementSpeed;
		}
		if(Controls.aDown) {
			if(game.getGroundHeight(player.x-1) > player.y && (int)(player.x - player.movementSpeed - player.hitBoxWidth/2d/game.blockSize) != (int)(player.x)) {
				int correction = player.x-1 < 0 ? 1 : 0;
				player.x += (int)(player.x-correction) - (player.x - player.hitBoxWidth/2d/game.blockSize) + 0.0001;
			}
			else player.x -= player.movementSpeed;
		} ticks++;
	}
	
	public class Stage extends JPanel {
		
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setColor(new Color(200, 200, 255)); // Sky color
			g2.fillRect(0, 0, getWidth(), getHeight());
			
			int playerX = (int)(game.player.x*game.blockSize);
			int playerY = (int)(game.player.y*game.blockSize);
			
			// Block generation
			while(game.maxBlocksX < (playerX+getWidth()/2)/game.blockSize) {
				game.generateNewBlock(1);
			}
			while(game.minBlocksX > (playerX-getWidth()/2-game.blockSize)/game.blockSize) {
				game.generateNewBlock(-1);
			}
			
			try { // Blocks
				// player.x - getWidth()
				for(int i = Math.max((int)(game.player.x - getWidth()/2/game.blockSize) + Math.abs(game.minBlocksX) - 2, 0); i<Math.min((int)(game.player.x - getWidth()/2/game.blockSize)+Math.abs(game.minBlocksX)+getWidth()/game.blockSize+2, game.blocks.size()); i++) {
					Block block = game.blocks.get(i);
					switch(block.type) {
						case DIRT: break;
						default:
							g2.setColor(new Color(40, 140, 40));
					}
					g2.fillRect(block.x * game.blockSize - playerX + getWidth()/2, getHeight() - (block.y * game.blockSize) - game.blockSize, game.blockSize, game.blockSize);
					for(int y = block.y-1; y>=0; y--) {
						g2.setColor(new Color(80, 50, 40));
						g2.fillRect(block.x * game.blockSize - playerX + getWidth()/2, getHeight() - (y * game.blockSize) - game.blockSize, game.blockSize, game.blockSize);
					}
				}
			} catch(Exception e) {} // Ignored
			
			// Player
			try {
				Image img = null;
				EntityImageSet imageSet = game.player.imageSet;
				if(Controls.aDown) img = ticks % 16 < 8 ? imageSet.leftOne : imageSet.leftTwo;
				else if(Controls.dDown) img = ticks % 16 < 8 ? imageSet.rightOne : imageSet.rightTwo;
				else if(game.player.y > game.getGroundHeight(game.player.x) || Controls.spaceDown || Controls.wDown) img = imageSet.jump;
				else img = imageSet.defaultImage;
				g2.drawImage(img, (int)(getWidth()/2) - img.getWidth(null)/2, getHeight() - (int)(game.player.y*game.blockSize) - img.getHeight(null), null);
			} catch(Exception e) { e.printStackTrace(); }
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
			
			/*// HP
			try {
				Image heartImage = ImageIO.read(new File("./rsc/heart.png"));
				for(int y = 0; y<(int)(game.player.hp/5)+1; y++) {
					for(int i = 0; i<Math.min(game.player.hp - y*5, 5); i++) {
						g2.drawImage(heartImage, (int)(getWidth()/2d + Math.min(game.player.hp - y*5d, 5d)/2d * (double)heartImage.getWidth(null) - i*heartImage.getWidth(null)), 10 * (y+1), null);
					}
				}
			} catch(Exception e) { e.printStackTrace(); } // Ignored*/
			
			// Info
			g2.setColor(Color.white);
			g2.setFont(new Font(g2.getFont().getName(), Font.BOLD, g2.getFont().getSize()));
			g2.drawString("x: " + game.player.x, 10, 20);
			g2.drawString("y: " + game.player.y, 10, 20 + g2.getFont().getSize());
			if(ticks % 20 == 0) currentFps = 1f / ((System.currentTimeMillis() - lastFrameTime) / 1000f);
			lastFrameTime = System.currentTimeMillis();
			g2.drawString("fps: " + currentFps + " (" + fps + ")", 10, 20 + 2 * g2.getFont().getSize());
		}
		
	}

}
