package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Eine Klasse zur Verwaltung der Tastenschläge.
 * @author Mika Thein
 * @version 1.0
 */
public class Controls implements KeyListener {
	
	/**
	 * Wahr, wenn die 'w'-Taste gedrückt wird.
	 */
	public static boolean wDown = false;
	/**
	 * Wahr, wenn die 's'-Taste gedrückt wird.
	 */
	public static boolean sDown = false;
	/**
	 * Wahr, wenn die 'a'-Taste gedrückt wird.
	 */
	public static boolean aDown = false;
	/**
	 * Wahr, wenn die 'd'-Taste gedrückt wird.
	 */
	public static boolean dDown = false;
	/**
	 * Wahr, wenn die ' '-Taste gedrückt wird.
	 */
	public static boolean spaceDown = false;
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
  
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W: wDown = true; break;
			case KeyEvent.VK_S: sDown = true; break;
			case KeyEvent.VK_A: aDown = true; break;
			case KeyEvent.VK_D: dDown = true; break;
			case KeyEvent.VK_SPACE: spaceDown = true; break;
		}
	}
  
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W: wDown = false; break;
			case KeyEvent.VK_S: sDown = false; break;
			case KeyEvent.VK_A: aDown = false; break;
			case KeyEvent.VK_D: dDown = false; break;
			case KeyEvent.VK_SPACE: spaceDown = false; break;
		}
	}

}
