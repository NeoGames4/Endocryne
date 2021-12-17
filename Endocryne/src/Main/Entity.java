package Main;

public class Entity {
	
	float x;
	float y;
	float vy = 0;
	
	float jumpSpeed = 0.175f; // > 0
	float movementSpeed = 0.1f; // > 0
	
	final public EntityImageSet imageSet;
	
	float hp;
	float attackDamage;
	
	int hitBoxWidth = 40;
	int hitBoxHeight = 120;
	
	public Entity(float x, float y, float hp, float attackDamage, EntityImageSet imageSet) {
		this.x = x;
		this.y = y;
		this.imageSet = imageSet;
		this.hp = hp;
		this.attackDamage = attackDamage;
	}

}
