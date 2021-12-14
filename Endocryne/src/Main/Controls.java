package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controls implements KeyListener {
	
	public static boolean wDown = false;
	public static boolean sDown = false;
	public static boolean aDown = false;
	public static boolean dDown = false;
	
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
		}
	}
  
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W: wDown = false; break;
			case KeyEvent.VK_S: sDown = false; break;
			case KeyEvent.VK_A: aDown = false; break;
			case KeyEvent.VK_D: dDown = false; break;
		}
	}

}
