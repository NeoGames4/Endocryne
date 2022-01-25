package Main;

import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Die Blocktypen.
 * @author Mika Thein
 * @version 1.0
 */
public enum Blocks {
	GRAS("gras", "Grassblock", new Color(40, 140, 40)),
	DIRT("dirt", "Erdblock", new Color(80, 50, 40));
	
	public final String id;
	public final String name;
	public final Color color;
	
	private Blocks(String id, String name, Color color) {
		this.id = id;
		this.name = name;
		this.color = color;
	}
}
