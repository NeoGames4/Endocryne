package Main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class Button extends JButton {
	
	public Image image;
	
	public Button(String title) {
		super(title);
		try {
			image = ImageIO.read(new File("./rsc/button.png"));
		} catch(Exception e) { e.printStackTrace();}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getBackground());
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		g2.drawImage(image, 0, 0, null);
		
		g2.setColor(getForeground());
		String title = getText();
		g2.drawString(title, getWidth()/2 - g2.getFontMetrics().stringWidth(title)/2, getHeight()/2 + g2.getFont().getSize()/2);
	}

}
