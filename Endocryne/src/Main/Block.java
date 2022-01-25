package Main;

import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Die Block-Klasse.
 * @author Mika Thein
 * @version 1.0
 */
public class Block {
	
	/**
	 * Die x-Koordinate.
	 */
	public final int x;
	/**
	 * die y-Koordinate.
	 */
	public final int y;
	/**
	 * Der Blocktyp.
	 */
	public final Blocks type;
	
	static public Image grasImage = null;
	
	/**
	 * Erstellt einen neuen Block.
	 * @param x die x-Koordinate.
	 * @param y die y-Koordinate.
	 * @param type der Blocktyp.
	 */
	public Block(int x, int y, Blocks type) {
		this.x = x;
		this.y = y;
		this.type = type;
		try {
			if(grasImage == null) grasImage = ImageIO.read(new File("./rsc/gras.png"));
		} catch(Exception e) {}
	}

}
