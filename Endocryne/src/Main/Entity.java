package Main;

public class Entity {
	
	float x;
	float y;
	float vy = 0;
	
	final public EntityImageSet imageSet;
	
	float hp;
	float attackDamage;
	
	public Entity(float x, float y, float hp, float attackDamage, EntityImageSet imageSet) {
		this.x = x;
		this.y = y;
		this.imageSet = imageSet;
		this.hp = hp;
		this.attackDamage = attackDamage;
	}

}
