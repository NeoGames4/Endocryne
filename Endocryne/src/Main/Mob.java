package Main;

/**
 * Die Mob-Klasse für Mobs ("Monster").
 * @author Mika Thein
 * @version 1.0
 */
public class Mob extends Entity {
	
	/**
	 * Erstellt ein neues Mob ("Monster").
	 * @param x die Start-x-Koordinate
	 * @param y die Start-y-Koordinate
	 * @param hp die Lebenspunkte
	 * @param attackDamage die Schadensstärke
	 * @param imageSet das imageSet
	 */
	public Mob(float x, float y, float hp, float attackDamage, EntityImageSet imageSet) {
		super(x, y, hp, attackDamage, imageSet);
	}

}
