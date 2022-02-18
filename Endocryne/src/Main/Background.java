package Main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class Background extends JPanel {
	
	public Image image;
	
	public Background(Image image) {
		super();
		this.image = image;
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getBackground());
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		
	}

}