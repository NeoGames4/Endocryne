package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
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
	 * Die Spielmusik.
	 */
	Clip clip;
	
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
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				TitleScreen titleScreen = new TitleScreen();
				titleScreen.setVisible(true);
				game.pause = true;
				clip.stop();
				dispose();
			}
		});
		
		lastFrameTime = System.currentTimeMillis();
		
		try {
			File soundFile = new File("./rsc/DontFallForBravery.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);
			DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
			clip = (Clip)AudioSystem.getLine(info);
			clip.open(stream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch(Exception e) { e.printStackTrace(); }
		
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				repaint();
				if(!game.pause) update();
			}
		}, 100, (int)(1d/fps * 1000d), TimeUnit.MILLISECONDS);
	}
	
	/**
	 * Die Gravitationsbeschleunigung in Bl�cken pro Tick.
	 */
	public final float gravity = -0.01f;
	
	public final float groundResistance = -0.005f;
	
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
		for(Entity e : game.entities) {
			float groundHeight = (int)(e.x-e.hitBoxWidth/2d/game.blockSize) == (int)(e.x+e.hitBoxWidth/2d/game.blockSize) ? game.getGroundHeight(e.x) : Math.max(game.getGroundHeight(e.x-e.hitBoxWidth/2d/game.blockSize), game.getGroundHeight(e.x+e.hitBoxWidth/2d/game.blockSize));
			if(e.vx != 0) {
				e.vx += -Math.copySign(groundResistance, e.vx);
				if(Math.abs(e.vx) < Math.abs(groundResistance)) e.vx = 0;
			}
		}
		if(Controls.dDown) {
			if(game.getGroundHeight(player.x+1) > player.y && (int)(player.x + player.movementSpeed + player.hitBoxWidth/2d/game.blockSize) != (int)(player.x)) {
				int correction = player.x+1 < 0 ? 0 : 1;
				player.x += (int)(player.x+correction) - (player.x + player.hitBoxWidth/2d/game.blockSize) - 0.0001;
				player.vx = 0;
			}
			else player.x += player.movementSpeed;
		}
		if(Controls.aDown) {
			if(game.getGroundHeight(player.x-1) > player.y && (int)(player.x - player.movementSpeed - player.hitBoxWidth/2d/game.blockSize) != (int)(player.x)) {
				int correction = player.x-1 < 0 ? 1 : 0;
				player.x += (int)(player.x-correction) - (player.x - player.hitBoxWidth/2d/game.blockSize) + 0.0001;
				player.vx = 0;
			}
			else player.x -= player.movementSpeed;
		}
		for(Entity e : game.entities) {
			if(e.vx > 0) {
				if(game.getGroundHeight(e.x+1) > e.y && (int)(e.x + e.vx + e.hitBoxWidth/2d/game.blockSize) != (int)(e.x)) {
					int correction = e.x+1 < 0 ? 0 : 1;
					e.x += (int)(e.x+correction) - (e.x + e.hitBoxWidth/2d/game.blockSize) - 0.0001;
					if(e instanceof Mob && e.y == game.getGroundHeight(e.x)) e.vy = e.jumpSpeed;
				}
				else e.x += e.vx;
			} else if(e.vx < 0) {
				if(game.getGroundHeight(e.x-1) > e.y && (int)(e.x + e.vx - e.hitBoxWidth/2d/game.blockSize) != (int)(e.x)) {
					int correction = e.x-1 < 0 ? 1 : 0;
					e.x += (int)(e.x-correction) - (e.x - e.hitBoxWidth/2d/game.blockSize) + 0.0001;
					if(e instanceof Mob && e.y == game.getGroundHeight(e.x)) e.vy = e.jumpSpeed;
				}
				else e.x += e.vx;
			}
		} ticks++;
		if(System.currentTimeMillis() - game.lastMobWaveSpawned > 10000 && game.entities.size()-1 < 0.075 * (ticks * 1d/fps) + 4) {
			int amount = (int) (0.075 * (ticks * 1d/fps) - game.entities.size() + 3);
			for(int i = 0; i<amount; i++) {
				try {
					float x = player.x - getWidth()/2/game.blockSize + (float) ((double)getWidth()/(double)game.blockSize * Math.random());
					if(Math.abs(player.x - x) < 4) x += Math.random() > 0.5 ? 4 : -4;
					Mob mob = new Mob(x, game.maxWorldHeight + 4, 30, 8, 1.2f, game.standardMobImageSet);
					game.entities.add(mob);
					System.out.println("At: " + x);
				} catch(Exception e) { e.printStackTrace(); }
			}
			game.lastMobWaveSpawned = System.currentTimeMillis();
			System.out.println("New wave spawned: " + amount);
		}
		for(Entity e : game.entities) {
			if(e instanceof Mob) {
				Player nearest = game.player;
				for(Entity p : game.entities) {
					if(p instanceof Player) {
						if(Math.abs(p.x - e.x) < Math.abs(nearest.x - e.x)) nearest = (Player)p;
					}
				}
				
				e.vx = nearest.x < e.x ? -e.movementSpeed : e.movementSpeed;
				if(e.y > game.maxWorldHeight + 1.5) e.vx = 0;
				if(Math.abs(nearest.x - e.x) < Math.max(Math.random()* 2, 0.5)) {
					e.vx = 0;
					if(Math.abs(nearest.y - e.y) < 1.3) e.attack(nearest);
				}
			}
		}
		if(player != null && player.hp <= 0) {
			game.pause = true;
			game.entities.remove(player);
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
			
			if(!game.pause) g2.setColor(new Color(200, 200, 255)); // Sky color
			else g2.setColor(new Color(120, 120, 120));
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
						g2.setColor(block.type.color);
						if(block.type == Blocks.GRAS) g2.drawImage(Block.grasImage, block.x * game.blockSize - playerX + getWidth()/2, getHeight() - (block.y * game.blockSize) - game.blockSize, null);
						else g2.fillRect(block.x * game.blockSize - playerX + getWidth()/2, getHeight() - (block.y * game.blockSize) - game.blockSize, game.blockSize, game.blockSize);
						for(int y = block.y-1; y>=0; y--) {
							g2.setColor(Blocks.DIRT.color);
							g2.fillRect(block.x * game.blockSize - playerX + getWidth()/2, getHeight() - (y * game.blockSize) - game.blockSize, game.blockSize, game.blockSize);
						}
					}
					
				} catch(Exception e) {} // Ignored
				
				// Player
				try {
					Image img = null;
					EntityImageSet imageSet = game.player.imageSet;
					float groundHeight = (int)(game.player.x-game.player.hitBoxWidth/2d/game.blockSize) == (int)(game.player.x+game.player.hitBoxWidth/2d/game.blockSize) ? game.getGroundHeight(game.player.x) : Math.max(game.getGroundHeight(game.player.x-game.player.hitBoxWidth/2d/game.blockSize), game.getGroundHeight(game.player.x+game.player.hitBoxWidth/2d/game.blockSize));
					if(Controls.aDown) img = ticks % 16 < 8 ? imageSet.leftOne : imageSet.leftTwo;
					else if(Controls.dDown) img = ticks % 16 < 8 ? imageSet.rightOne : imageSet.rightTwo;
					else if(game.player.y > groundHeight || Controls.spaceDown || Controls.wDown) img = imageSet.jump;
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
						float groundHeight = (int)(entity.x-entity.hitBoxWidth/2d/game.blockSize) == (int)(entity.x+entity.hitBoxWidth/2d/game.blockSize) ? game.getGroundHeight(entity.x) : Math.max(game.getGroundHeight(entity.x-entity.hitBoxWidth/2d/game.blockSize), game.getGroundHeight(entity.x+entity.hitBoxWidth/2d/game.blockSize));
						if(entity.vx < 0) img = ticks % 16 < 8 ? imageSet.leftOne : imageSet.leftTwo;
						else if(entity.vx > 0) img = ticks % 16 < 8 ? imageSet.rightOne : imageSet.rightTwo;
						else if(entity.y > groundHeight) img = imageSet.jump;
						else img = imageSet.defaultImage;
						g2.drawImage(img, entityX - img.getWidth(null)/2, getHeight() - (int)(entity.y*game.blockSize) - img.getHeight(null), null);
					}
				} catch(Exception e) {} // Ignored
				
				// HP
				/*try {
					Image heartImage = ImageIO.read(new File("./rsc/heart.png")).getScaledInstance(24, 24, Image.SCALE_FAST);
					double heartsPerLine = 10d;
					for(int y = 0; y<(int)(game.player.hp/heartsPerLine)+1; y++) {
						for(int i = 0; i<Math.min(game.player.hp - y*heartsPerLine, heartsPerLine); i++) {
							int xPos = (int)(getWidth()/2d + Math.min(game.player.hp - y*heartsPerLine, heartsPerLine)/2d * (double)heartImage.getWidth(null) - i*heartImage.getWidth(null));
							int yPos = 10 * (y+1);
							g2.drawImage(heartImage, xPos, yPos, null);
						}
					}
				} catch(Exception e) { e.printStackTrace(); } // Ignored*/
				g2.setColor(Color.RED);
				g2.fillRect(0, getHeight()-10, getWidth(), getHeight());
				g2.setColor(Color.GREEN);
				g2.fillRect(0, getHeight()-10, (int)(getWidth() * (game.player.hp/game.player.maxHp)), getHeight());
				
				// Death / Pause screen
				if(game.pause) {
					if(game.player.hp <= 0) {
						g2.setColor(new Color(32, 32, 32, 186));
						g2.drawRect(0, 0, getWidth(), getHeight());
						g2.setColor(Color.WHITE);
						Font previousFont = g2.getFont();
						g2.setFont(new Font(g2.getFont().getName(), Font.BOLD, 36));
						String infText = "you died lmao";
						g2.drawString(infText, getWidth()/2-g2.getFontMetrics().stringWidth(infText)/2, 100);
						g2.setFont(new Font(g2.getFont().getName(), Font.BOLD, 16));
						String[] subTexts = new String[] {"you survived that long: " + (int)((double)ticks * 1d/fps) + " seconds", "wow", "", "you may close this window now and try better", "", "", "",
								"Statistics",
								"Entities killed (by you): " + game.player.kills,
								"Blocks generated: " + game.blocks.size(),
								"Amount of entities (still living): " + game.entities.size(),
								"Game ticks: " + ticks,
								"Game time: " + ((double)(int)((double)ticks * 1d/fps * 1000000)/1000000d) + " seconds"};
						for(int i = 0; i<subTexts.length; i++) g2.drawString(subTexts[i], getWidth()/2-g2.getFontMetrics().stringWidth(subTexts[i])/2, 150 + i * g2.getFontMetrics().getHeight());
						g2.setFont(previousFont);
					} else {
						g2.setColor(Color.WHITE);
						Font previousFont = g2.getFont();
						g2.setFont(new Font(g2.getFont().getName(), Font.BOLD, 36));
						String infText = "pause";
						g2.drawString(infText, getWidth()/2-g2.getFontMetrics().stringWidth(infText)/2, 100);
						g2.setFont(new Font(g2.getFont().getName(), Font.BOLD, 16));
						String[] subTexts = new String[] {"you survived that long (yet): " + (int)((double)ticks * 1d/fps) + " seconds"};
						for(int i = 0; i<subTexts.length; i++) g2.drawString(subTexts[i], getWidth()/2-g2.getFontMetrics().stringWidth(subTexts[i])/2, 150 + i * g2.getFontMetrics().getHeight());
						g2.setFont(previousFont);
					}
				}
				
				// Info
				g2.setColor(Color.white);
				g2.setFont(new Font(g2.getFont().getName(), Font.BOLD, g2.getFont().getSize()));
				g2.drawString("x: " + game.player.x, 10, 20);
				int cIndex = 1;
				g2.drawString("y: " + game.player.y, 10, 20 + (cIndex++) * g2.getFont().getSize());
				if(ticks % 20 == 0) currentFps = 1f / ((System.currentTimeMillis() - lastFrameTime) / 1000f);
				lastFrameTime = System.currentTimeMillis();
				g2.drawString("fps: " + currentFps + " (" + fps + ")", 10, 20 + (cIndex++) * g2.getFont().getSize());
				g2.drawString("time: " + ((double)(int)((double)ticks * 1d/fps * 100)/100d), 10, 20 + (cIndex++) * g2.getFont().getSize());
				if(Launcher.DEBUG) {
					g2.drawString("Last hit: " + (System.currentTimeMillis() - game.player.cooldownSet.lastTimeHit), 10, 20 + (cIndex++) * g2.getFont().getSize());
					g2.drawString("Last damage: " + (System.currentTimeMillis() - game.player.cooldownSet.lastTimeHit), 10, 20 + (cIndex++) * g2.getFont().getSize());
					g2.drawString("HP: " + game.player.hp + "/" + game.player.maxHp, 10, 20 + (cIndex++) * g2.getFont().getSize());
					g2.drawString("Attack damage: " + game.player.attackDamage, 10, 20 + (cIndex++) * g2.getFont().getSize());
					g2.drawString("Entities: " + game.entities.size(), 10, 20 + (cIndex++) * g2.getFont().getSize());
				}
			} catch(Exception outerException) {
				System.err.println("Caught exception: " + outerException.getMessage());
			}
		}
		
	}

}
