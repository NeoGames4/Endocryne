package Main;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Game {
	
	public final GameFrame frame = new GameFrame(this, (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.2), (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.2));
	
	ArrayList<Player> players = new ArrayList<>();
	Player player;
	ArrayList<Mob> mobs = new ArrayList<>();
	ArrayList<Block> blocks = new ArrayList<>();
	
	private EntityImageSet standardPlayerImageSet;
	
	int blockSize = 64;
	
	int minBlocksX;
	int maxBlocksX;
	
	public Game() {
		try {
			Image defaultImage = null;
			Image leftOne = null;
			Image rightOne = null;
			Image leftTwo = null;
			Image rightTwo = null;
			Image jump = null;
			Image hit = null;
			standardPlayerImageSet = new EntityImageSet(defaultImage, leftOne, rightOne, leftTwo, rightTwo, jump, hit);
		} catch(Exception e) {}
		player = new Player(0, 0, 100, 10, standardPlayerImageSet);
		players.add(player);
		for(int i = 0; i * blockSize<frame.getWidth(); i++) {
			blocks.add(new Block(i, getNextBlockPositionY(blocks.size() > 0 ? blocks.get(blocks.size()-1).y : 0), Blocks.GRAS));
		}
	}
	
	private int getNextBlockPositionY(int previousY) {
		double rand = Math.random();
		return rand < 0.08 ? previousY+1 : rand < 0.16 ? Math.max(previousY-1, 0) : previousY;
	}

}
