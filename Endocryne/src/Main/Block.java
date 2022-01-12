package Main;

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
	}

}
