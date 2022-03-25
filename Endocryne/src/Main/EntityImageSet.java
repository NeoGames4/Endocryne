package Main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EntityImageSet {
	
	public final Image defaultImage;
	public final Image leftOne;
	public final Image rightOne;
	public final Image leftTwo;
	public final Image rightTwo;
	public final Image jump;
	public final Image hit;
	public final Image hitFlipped;
	
	public static final int width = 40;
	public static final int height = 120;
	
	public EntityImageSet(Image defaultImage, Image leftOne, Image rightOne, Image leftTwo, Image rightTwo, Image jump, Image hit) {
		this.defaultImage = defaultImage;
		this.leftOne = leftOne;
		this.rightOne = rightOne;
		this.leftTwo = leftTwo;
		this.rightTwo = rightTwo;
		this.jump = jump;
		this.hit = hit;
		this.hitFlipped = Game.flipHorizontally((BufferedImage) hit);
	}
	
	/**
	 * Konvertiert ein Bild in ein EntityImageSet.
	 * @param image ein Bildset
	 * @return das das resultierende {@link #getEntityImageSet(Image)}
	 */
	public static EntityImageSet getEntityImageSet(Image image) throws Exception {
		if(image.getWidth(null) == 7 * width && image.getHeight(null) == height) {
			BufferedImage img = (BufferedImage) image;
			Image defaultImage = img.getSubimage(0, 0, width, height);
			Image leftOne = img.getSubimage(width, 0, width, height);
			Image rightOne = img.getSubimage(2 * width, 0, width, height);
			if(isEmpty(rightOne)) rightOne = Game.flipHorizontally((BufferedImage)leftOne);
			Image leftTwo = img.getSubimage(3 * width, 0, width, height);
			Image rightTwo = img.getSubimage(4 * width, 0, width, height);
			if(isEmpty(rightTwo)) rightTwo = Game.flipHorizontally((BufferedImage)leftTwo);
			Image jump = img.getSubimage(5 * width, 0, width, height);
			Image hit = img.getSubimage(6 * width, 0, width, height);
			return new EntityImageSet(defaultImage, leftOne, rightOne, leftTwo, rightTwo, jump, hit);
		} else throw new Exception("Image has to be scaled like " + (7 * width) + " px times " + height + " px");
	}
	
	public static void writeStandard(File path) throws IOException {
		System.out.println("a");
		Image defaultImage = ImageIO.read(new File("./rsc/default.png"));
		Image leftOne = Game.flipHorizontally(ImageIO.read(new File("./rsc/one.png")));
		Image rightOne = ImageIO.read(new File("./rsc/one.png"));
		Image leftTwo = Game.flipHorizontally(ImageIO.read(new File("./rsc/two.png")));
		Image rightTwo = ImageIO.read(new File("./rsc/two.png"));
		Image jump = ImageIO.read(new File("./rsc/jump.png"));
		Image hit = null;
		BufferedImage image = new BufferedImage(7 * width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();
		g2.drawImage(defaultImage, 0 * width, 0, null);
		g2.drawImage(leftOne, 1 * width, 0, null);
		g2.drawImage(rightOne, 2 * width, 0, null);
		g2.drawImage(leftTwo, 3 * width, 0, null);
		g2.drawImage(rightTwo, 4 * width, 0, null);
		g2.drawImage(jump, 5 * width, 0, null);
		g2.dispose();
		ImageIO.write(image, path.getName().substring(path.getName().lastIndexOf(".")+1), path);
		System.out.println("-> " + path.getAbsolutePath());
	}
	
	public static boolean isEmpty(Image image) throws Exception {
		for(int x = 0; x<image.getWidth(null); x++) {
			for(int y = 0; y<image.getHeight(null); y++) {
			}
		} return true;
	}

}
