package Main;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class EntityImageSet {
	
	public final Image defaultImage;
	public final Image leftOne;
	public final Image rightOne;
	public final Image leftTwo;
	public final Image rightTwo;
	public final Image jump;
	public final Image hit;
	
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
	
	public static boolean isEmpty(Image image) throws Exception {
		for(int x = 0; x<image.getWidth(null); x++) {
			for(int y = 0; y<image.getHeight(null); y++) {
			}
		} return true;
	}

}
