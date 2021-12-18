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
	
	int minBlocksX = 0;
	int maxBlocksX = 0;
	
	int maxWorldHeight = (int)(frame.getHeight()/blockSize * 3d/4d);
	
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
		blocks.add(new Block(0, (int)(Math.random() * maxWorldHeight/2), Blocks.GRAS));
		player = new Player(0, getGroundHeight(0), 100, 10, standardPlayerImageSet);
		players.add(player);
	}
	
	public void generateNewBlock(int direction) {
		if(direction >= 0) { // right
			int y = blocks.get(blocks.size()-1).y;
			if(blocks.size() > 3) {
				boolean equal = true;
				for(int i = blocks.size()-3; i<blocks.size(); i++) {
					if(blocks.get(i).y != y) { equal = false; break; }
				}
				double rand = Math.random();
				if(equal) y += rand < 0.1 ? 1 : rand < 0.2 ? -1 : 0;
			} if(y < 0) y = 0; else if(y > maxWorldHeight) y = maxWorldHeight;
			blocks.add(new Block(blocks.get(blocks.size()-1).x+1, y, Blocks.GRAS));
			maxBlocksX++;
		} else { // left
			int y = blocks.get(0).y;
			if(blocks.size() > 3) {
				boolean equal = true;
				for(int i = 0; i<3; i++) {
					if(blocks.get(i).y != y) { equal = false; break; }
				}
				double rand = Math.random();
				if(equal) y += rand < 0.1 ? 1 : rand < 0.2 ? -1 : 0;
			} if(y < 0) y = 0; else if(y > maxWorldHeight) y = maxWorldHeight;
			blocks.add(0, new Block(blocks.get(0).x-1, y, Blocks.GRAS));
			minBlocksX--;
		}
	}
	
	public int getGroundHeight(double x) {
		if(x < 0) x--;
		return blocks.get(Math.abs(minBlocksX) + (int)x).y+1;
	}

}
