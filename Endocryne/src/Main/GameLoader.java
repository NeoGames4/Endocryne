package Main;

import org.json.JSONArray;
import org.json.JSONObject;

public class GameLoader {
	
	public final Game game;
	
	public GameLoader(Game game) {
		this.game = game;
	}
	
	public void load(JSONObject world) throws Exception {
		game.entities.clear();
		game.blocks.clear();
		// Entities
		JSONArray entities = world.getJSONArray("entities");
		for(int i = 0; i<entities.length(); i++) {
			JSONObject entity = entities.getJSONObject(i);
			JSONObject imageSet = entity.has("imageSet") ? entity.getJSONObject("imageSet") : null;
			if(entity.getString("type").equals("player")) {
				Player player = new Player(entity.getFloat("x"), entity.getFloat("y"), entity.getFloat("maxHp"), entity.getFloat("attackDamage"), imageSet == null ? game.standardPlayerImageSet : null); // Nicht fertig!
				player.hp = entity.getFloat("hp");
				player.attackDelay = entity.getLong("attackDelay");
				player.damageDelay = entity.getLong("damageDelay");
				player.hitBoxWidth = entity.getInt("hitBoxWidth");
				player.hitBoxHeight = entity.getInt("hitBoxHeight");
				player.hitHeightRatio = entity.getFloat("hitHeightRatio");
				player.jumpSpeed = entity.getFloat("jumpSpeed");
				player.movementSpeed = entity.getFloat("movementSpeed");
				player.range = entity.getFloat("range");
				player.vx = entity.getFloat("vx");
				player.vy = entity.getFloat("vy");
				if(entity.has("isPlayer") && entity.getBoolean("isPlayer")) game.player = player;
				game.entities.add(player);
			} else {
				Mob mob = new Mob(entity.getFloat("x"), entity.getFloat("y"), entity.getFloat("maxHp"), entity.getFloat("attackDamage"), entity.getFloat("range"), imageSet == null ? game.standardPlayerImageSet : null); // Nicht fertig!
				mob.hp = entity.getFloat("hp");
				mob.attackDelay = entity.getLong("attackDelay");
				mob.damageDelay = entity.getLong("damageDelay");
				mob.hitBoxWidth = entity.getInt("hitBoxWidth");
				mob.hitBoxHeight = entity.getInt("hitBoxHeight");
				mob.hitHeightRatio = entity.getFloat("hitHeightRatio");
				mob.jumpSpeed = entity.getFloat("jumpSpeed");
				mob.movementSpeed = entity.getFloat("movementSpeed");
				mob.vx = entity.getFloat("vx");
				mob.vy = entity.getFloat("vy");
				game.entities.add(mob);
			}
		}
		// Blocks
		JSONArray blocks = world.getJSONArray("blocks");
		for(int i = 0; i<blocks.length(); i++) {
			JSONObject block = blocks.getJSONObject(i);
			Blocks type = block.getString("type").equalsIgnoreCase(Blocks.GRAS.id) ? Blocks.GRAS : Blocks.DIRT;
			Block b = new Block(block.getInt("x"), block.getInt("y"), type);
			game.blocks.add(b);
		}
	}
	
	public JSONObject generate() {
		JSONObject world = new JSONObject();
		// Entities
		JSONArray entities = new JSONArray();
		for(Entity e : game.entities) {
			JSONObject entity = new JSONObject();
			if(e instanceof Player) {
				entity.put("type", "player");
				Player p = (Player)e;
				entity.put("x", p.x);
				entity.put("y", p.y);
				entity.put("maxHp", p.maxHp);
				entity.put("hp", p.hp);
				entity.put("attackDamage", p.attackDamage);
				entity.put("attackDelay", p.attackDelay);
				entity.put("damageDelay", p.damageDelay);
				entity.put("hitBoxWidth", p.hitBoxWidth);
				entity.put("hitBoxHeight", p.hitBoxHeight);
				entity.put("hitHeightRatio", p.hitHeightRatio);
				entity.put("jumpSpeed", p.jumpSpeed);
				entity.put("movementSpeed", p.movementSpeed);
				entity.put("range", p.range);
				entity.put("vx", p.vx);
				entity.put("vy", p.vy);
				entity.put("isPlayer", p == game.player);
			} else {
				entity.put("type", "mob");
				Mob m = (Mob)e;
				entity.put("x", m.x);
				entity.put("y", m.y);
				entity.put("maxHp", m.maxHp);
				entity.put("hp", m.hp);
				entity.put("range", m.range);
				entity.put("attackDamage", m.attackDamage);
				entity.put("attackDelay", m.attackDelay);
				entity.put("damageDelay", m.damageDelay);
				entity.put("hitBoxWidth", m.hitBoxWidth);
				entity.put("hitBoxHeight", m.hitBoxHeight);
				entity.put("hitHeightRatio", m.hitHeightRatio);
				entity.put("jumpSpeed", m.jumpSpeed);
				entity.put("movementSpeed", m.movementSpeed);
				entity.put("vx", m.vx);
				entity.put("vy", m.vy);
			}
			entities.put(entity);
		}
		world.put("entities", entities);
		// Blocks
		JSONArray blocks = new JSONArray();
		for(Block b : game.blocks) {
			JSONObject block = new JSONObject();
			block.put("x", b.x);
			block.put("y", b.y);
			block.put("type", b.type.id);
			blocks.put(block);
		}
		world.put("blocks", blocks);
		return world;
	}

}