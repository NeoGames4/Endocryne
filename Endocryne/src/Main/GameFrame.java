package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Die Klasse, die das Fenster zum Spiel darstellt.
 * @author Mika Thein
 * @version 1.0
 */
public class GameFrame extends JFrame {
	
	/**
	 * Das ContentPane (das tats�chliche Grafikmodul, auf welchem gezeichnet wird).
	 */
	public Stage stage = new Stage();
	
	/**
	 * Bilder pro Sekunde.
	 */
	public long fps = 60;
	/**
	 * Errechneten Bilder pro Sekunde.
	 */
	public float currentFps = 0;
	
	/**
	 * Das zugeh�rige Spiel.
	 */
	public final Game game;
	
	/**
	 * Die Ticks seit dem Spielstart.
	 */
	public long ticks = 0;
	/**
	 * Die Zeit, die bei dem letzten Tick gemessen worden ist (zur Berechung von {@link #currentFps}).
	 */
	private long lastFrameTime;
	/**
	 * Die Koordinate und der Zeitpunkt des letzten Mausklicks;
	 * <p>[0] entspricht dabei der x-Koordinate<br>
	 * [1] entspricht dabei der y-Koordinate<br>
	 * [2] entspricht dabei dem Zeitpunkt (epoch milliseconds gemäß {@Code System.currentTimeMillis()})
	 */
	public long[] lastMouseClick = new long[] {-1, -1, -1};
	
	/**
	 * Erstellt einen neuen Frame.
	 * @param game das zugeh�rige Spiel
	 * @param width die Fensterweite in Pixeln
	 * @param height die Fensterh�he in Pixeln
	 */
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
	
	/**
	 * Die Gravitationsbeschleunigung in Bl�cken pro Tick.
	 */
	public final float gravity = -0.01f;
	
	/**
	 * Durchl�uft alle Entities und aktualisiert ihre Positionen (im Hinblick auf Gravitation und Steuerung).
	 */
	public void update() {
		Player player = game.player;
		if((Controls.wDown || Controls.spaceDown) && (player.y == ((int)(player.x-player.hitBoxWidth/2d/game.blockSize) == (int)(player.x+player.hitBoxWidth/2d/game.blockSize) ? game.getGroundHeight(player.x) : Math.max(game.getGroundHeight(player.x-player.hitBoxWidth/2d/game.blockSize), game.getGroundHeight(player.x+player.hitBoxWidth/2d/game.blockSize))))) {
			player.vy = player.jumpSpeed;
		}
		for(Entity e : game.entities) {
			float groundHeight = (int)(e.x-e.hitBoxWidth/2d/game.blockSize) == (int)(e.x+e.hitBoxWidth/2d/game.blockSize) ? game.getGroundHeight(e.x) : Math.max(game.getGroundHeight(e.x-e.hitBoxWidth/2d/game.blockSize), game.getGroundHeight(e.x+e.hitBoxWidth/2d/game.blockSize));
			if(e.y + e.vy < groundHeight) e.vy = groundHeight - e.y;
			e.y += e.vy;
			e.vy += gravity;
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
		if(System.currentTimeMillis() - game.lastMobWaveSpawned > 30000 && game.entities.size() < 2) {
			int amount = 4;
			for(int i = 0; i<amount; i++) {
				try {
					Image defaultImage = ImageIO.read(new File("./rsc/default.png"));
					Image leftOne = Game.flipHorizontally(ImageIO.read(new File("./rsc/one.png")));
					Image rightOne = ImageIO.read(new File("./rsc/one.png"));
					Image leftTwo = Game.flipHorizontally(ImageIO.read(new File("./rsc/two.png")));
					Image rightTwo = ImageIO.read(new File("./rsc/two.png"));
					Image jump = ImageIO.read(new File("./rsc/jump.png"));
					Image hit = null;
					EntityImageSet imageSet = new EntityImageSet(defaultImage, leftOne, rightOne, leftTwo, rightTwo, jump, hit);
					float x = player.x + getWidth()/2/game.blockSize + 1 + Math.min((float)i/10f * 1f, 1);
					Mob mob = new Mob(x, game.getGroundHeight(x), 40, 12, 1.2f, imageSet);
					game.entities.add(mob);
					System.out.println("At: " + x);
				} catch(Exception e) { e.printStackTrace(); }
			}
			game.lastMobWaveSpawned = System.currentTimeMillis();
			System.out.println("New wave spawned!");
		}
	}
	
	/**
	 * Das ContentPane des {@link #GameFrame()}s, auf welchem tats�chlich gezeichnet wird.
	 * @author Mika Thein
	 * @version 1.0
	 */
	public class Stage extends JPanel {
		
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setColor(new Color(200, 200, 255)); // Sky color
			g2.fillRect(0, 0, getWidth(), getHeight());
			
			try {
			
				int playerX = (int)(game.player.x*game.blockSize);
				int playerY = (int)(game.player.y*game.blockSize);
				
				// Block generation
				while(game.maxBlocksX < (playerX+getWidth()/2)/game.blockSize + 3) {
					game.generateNewBlock(1);
				}
				while(game.minBlocksX > (playerX-getWidth()/2-game.blockSize)/game.blockSize - 3) {
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
				if(Launcher.DEBUG) {
					g2.setColor(Color.MAGENTA);
					g2.drawRect((int)(getWidth()/2) - game.player.hitBoxWidth/2, getHeight() - (int)(game.player.y*game.blockSize) - game.player.hitBoxHeight, game.player.hitBoxWidth, game.player.hitBoxHeight);
					g2.setColor(Color.GREEN);
					g2.fillOval((int)(getWidth()/2) - 2, getHeight() - (int)(game.player.y*game.blockSize) - 2, 4, 4);
					g2.setColor(Color.RED);
					int xPos = getWidth()/2;
					int yPos = getHeight() - (int)(game.player.y*game.blockSize + game.player.hitBoxHeight * game.player.hitHeightRatio);
					/*if(System.currentTimeMillis() - lastMouseClick[2] < 1500) {
						float x = (float) (lastMouseClick[0] - getWidth()/2);
						float y = (float) ((getHeight() - game.player.y*game.blockSize + game.player.hitBoxHeight * game.player.hitHeightRatio) - lastMouseClick[1]);
						g2.drawLine(xPos, yPos, (int)(xPos + game.player.range * game.blockSize * Math.cos(Math.atan(y/x))), (int)(yPos - game.player.range * game.blockSize * Math.sin(Math.atan(y/x))));
					}*/
					g2.fillOval(xPos - 2, yPos - 2, 4, 4);
				}
				
				try { // Entities
					for(Entity entity : game.entities) {
						if(entity == game.player) continue;
						if(entity.x < game.player.x - getWidth()/2/game.blockSize - 2 || entity.x > game.player.x + getWidth()/2/game.blockSize + 2) continue;
						int entityX = (int)((entity.x - game.player.x) * game.blockSize) + getWidth()/2;
						if(Launcher.DEBUG) {
							g2.setColor(Color.MAGENTA);
							g2.drawRect(entityX - entity.hitBoxWidth/2, getHeight() - (int)(entity.y*game.blockSize) - entity.hitBoxHeight, entity.hitBoxWidth, entity.hitBoxHeight);
							g2.setColor(Color.YELLOW);
							g2.fillOval(entityX - 2, getHeight() - (int)(entity.y*game.blockSize) - 2, 4, 4);
							g2.setColor(Color.ORANGE);
							g2.fillOval(entityX - 2, getHeight() - (int)(entity.y*game.blockSize) - entity.hitBoxHeight/2 - 2, 4, 4);
						}
						Image img = null;
						EntityImageSet imageSet = entity.imageSet;
						if(entity.vx < 0) img = ticks % 16 < 8 ? imageSet.leftOne : imageSet.leftTwo;
						else if(entity.vx > 0) img = ticks % 16 < 8 ? imageSet.rightOne : imageSet.rightTwo;
						else if(entity.y > game.getGroundHeight(entity.x)) img = imageSet.jump;
						else img = imageSet.defaultImage;
						g2.drawImage(img, entityX - img.getWidth(null)/2, getHeight() - (int)(entity.y*game.blockSize) - img.getHeight(null), null);
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
				/*if(Launcher.DEBUG) {
					g2.drawString("Last hit: " + (System.currentTimeMillis() - player) + " (" + fps + ")", 10, 20 + 2 * g2.getFont().getSize());
					g2.drawString("Last damage: " + currentFps + " (" + fps + ")", 10, 20 + 2 * g2.getFont().getSize());
				}*/
			} catch(Exception outerException) {
				System.err.println("Caught exception: " + outerException.getMessage());
			}
		}
		
	}

}
