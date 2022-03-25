package Main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class Button extends JButton {
	
	public Image image;
	
	public Button(Image image) {
		super();
		this.image = image;
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		
	}

}
