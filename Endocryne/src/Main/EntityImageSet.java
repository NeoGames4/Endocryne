package Main;

import java.awt.Image;

public class EntityImageSet {
	
	public final Image defaultImage;
	public final Image leftOne;
	public final Image rightOne;
	public final Image leftTwo;
	public final Image rightTwo;
	public final Image jump;
	public final Image hit;
	
	public EntityImageSet(Image defaultImage, Image leftOne, Image rightOne, Image leftTwo, Image rightTwo, Image jump, Image hit) {
		this.defaultImage = defaultImage;
		this.leftOne = leftOne;
		this.rightOne = rightOne;
		this.leftTwo = leftTwo;
		this.rightTwo = rightTwo;
		this.jump = jump;
		this.hit = hit;
	}

}
