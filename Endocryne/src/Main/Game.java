package Main;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Die Klasse, die das Spiel und die Spielsteuerung verwaltet.
 * @author Mika Thein
 * @version 1.0
 */
public class Game {
	
	/**
	 * Die zugeh�rige Frame-Klasse (welche das Spiel darstellt).
	 */
	public final GameFrame frame = new GameFrame(this, (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.2), (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.2));
	
	/**
	 * Alle Entities auf der Karte.
	 */
	ArrayList<Entity> entities = new ArrayList<>();
	/**
	 * Der Spieler.
	 */
	Player player;
	/**
	 * Die geladenen Bl�cke.
	 */
	ArrayList<Block> blocks = new ArrayList<>();
	
	/**
	 * Die Spielergrafiken.
	 */
	private EntityImageSet standardPlayerImageSet;
	
	/**
	 * Die Darstellungsgr��e der Bl�cke in Pixeln.
	 */
	int blockSize = 64;
	
	/**
	 * Die Anzahl an geladenen Bl�cken mit x < 0.
	 */
	int minBlocksX = 0;
	/**
	 * Die Anzahl an geladenen Bl�cken mit x > 0.
	 */
	int maxBlocksX = 0;
	
	/**
	 * Der Zeitpunkt, zu welchem das letzte Mal eine Welle an Mobs erschaffen worden ist.
	 */
	long lastMobWaveSpawned = System.currentTimeMillis();
	
	/**
	 * Die maximale Welth�he in Bl�cken.
	 */
	int maxWorldHeight = (int)(frame.getHeight()/blockSize * 5d/7d);
	
	public Game() {
		try {
			Image defaultImage = ImageIO.read(new File("./rsc/default.png"));
			Image leftOne = flipHorizontally(ImageIO.read(new File("./rsc/one.png")));
			Image rightOne = ImageIO.read(new File("./rsc/one.png"));
			Image leftTwo = flipHorizontally(ImageIO.read(new File("./rsc/two.png")));
			Image rightTwo = ImageIO.read(new File("./rsc/two.png"));
			Image jump = ImageIO.read(new File("./rsc/jump.png"));
			Image hit = null;
			standardPlayerImageSet = new EntityImageSet(defaultImage, leftOne, rightOne, leftTwo, rightTwo, jump, hit);
		} catch(Exception e) { e.printStackTrace(); }
		blocks.add(new Block(0, (int)(Math.random() * maxWorldHeight/2), Blocks.GRAS));
		player = new Player(0, getGroundHeight(0), 70, 10, standardPlayerImageSet);
		entities.add(player);
		frame.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				int x = (int) (e.getX() - (frame.getWidth()/2));
				int y = (int) ((frame.getHeight() - player.y*blockSize + player.hitBoxHeight * player.hitHeightRatio) - e.getY());
				frame.lastMouseClick = new long[] {e.getX(), e.getY(), System.currentTimeMillis()};
				System.out.println("x: " + x + ", y: " + y);
				if(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) <= player.range * blockSize) {
					System.out.println("Valid");
				} else System.out.println("Invalid");
			}
			
		});
	}
	
	/**
	 * Generiert einen neuen Block.
	 * <p>Falls {@code}direction{@code} kleiner als 0, so wird ein neuer Block
	 * im negativen x-Bereich ("links") erstellt.
	 * Andernfalls dementsprechend im positiven x-Bereich ("rechts") erstellt.
	 * <p>Der neu-erstellte Block wird stets an den Anfang oder an das Ende der {@link #blocks}-ArrayList geh�ngt.
	 * @param direction ob links oder rechts aller aktuell geladenen Bl�cken
	 */
	public void generateNewBlock(int direction) {
		if(direction >= 0) { // right
			int y = blocks.get(blocks.size()-1).y;
			if(blocks.get(blocks.size()-1).x > 2) {
				boolean equal = true;
				for(int i = blocks.size()-3; i<blocks.size(); i++) {
					if(blocks.get(i).y != y) { equal = false; break; }
				}
				double rand = Math.random();
				if(equal) y += rand < 0.1 ? 1 : rand < 0.2 ? -1 : 0;
			} if(y < 0) y = 0; else if(y > maxWorldHeight) y = maxWorldHeight;
			blocks.add(new Block(blocks.get(blocks.size()-1).x+1, y, Blocks.GRAS));
			maxBlocksX++;
		} else { // left
			int y = blocks.get(0).y;
			if(blocks.get(0).x < -2) {
				boolean equal = true;
				for(int i = 0; i<3; i++) {
					if(blocks.get(i).y != y) { equal = false; break; }
				}
				double rand = Math.random();
				if(equal) y += rand < 0.1 ? 1 : rand < 0.2 ? -1 : 0;
			} if(y < 0) y = 0; else if(y > maxWorldHeight) y = maxWorldHeight;
			blocks.add(0, new Block(blocks.get(0).x-1, y, Blocks.GRAS));
			minBlocksX--;
		}
	}
	
	/**
	 * Gibt die Bodenh�he bei der x-Koordinate {@code}x{@code} zur�ck.
	 * @param x die x-Koordinate
	 * @return die Blockh�he in Bl�cken
	 */
	public int getGroundHeight(double x) {
		if(x < 0) x--;
		return blocks.get(Math.abs(minBlocksX) + (int)x).y+1;
	}
	
	/**
	 * Spiegelt Bilder mittig horizontal.
	 * @param img das zuspiegelnde Bild
	 * @return das gespiegelte Bild
	 */
	public static Image flipHorizontally(BufferedImage img) {
		AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
		transform.translate(-img.getWidth(null), 0);
		return new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR).filter(img, null);
	}

}
