package Main;

/**
 * Die Entity-Klasse.
 * @author Mika Thein
 * @version 1.0
 */
public class Entity {
	
	/**
	 * Die x-Position in Blˆcken.
	 */
	public float x;
	/**
	 * Die y-Position in Blˆcken.
	 */
	public float y;
	/**
	 * Die horizontale Geschwindigkeit in Blˆcken pro Tick.
	 */
	public float vx = 0;						// FEHLT NOCH!
	/**
	 * Die vertikale Geschwindigkeit in Blˆcken pro Tick.
	 */
	public float vy = 0;
	
	/**
	 * Die Startgeschwindigkeit bei einem Sprung in Blˆcken pro Tick.
	 */
	public float jumpSpeed = 0.175f; // > 0
	/**
	 * Die Bewegungsgeschwindigkeit in Blˆcken pro Tick.
	 */
	public float movementSpeed = 0.1f; // > 0
	
	/**
	 * Das EntityImageSet, welches alle Grafiken des Entitys beinhaltet.
	 */
	final public EntityImageSet imageSet;
	
	/**
	 * Die Lebenspunkte.
	 */
	public float hp;
	/**
	 * Die Schadensst‰rke.
	 */
	public float attackDamage;
	
	/**
	 * Die HitBox-Weite in Pixeln.
	 */
	public int hitBoxWidth = 40;
	/**
	 * Die HitBox-Hˆhe in Pixeln.
	 */
	public int hitBoxHeight = 120;
	
	/**
	 * Attackierungscooldown in Millisekunden.
	 */
	public long attackDelay = 10;
	/**
	 * Schadenschutzdauer in Millisekunden.
	 */
	public long damageDelay = 50;
	
	/**
	 * Ein CooldownSet zum Abgleich mit den Cooldowns.
	 */
	CooldownSet cooldownSet = new CooldownSet();
	
	/**
	 * Erstellt ein neues Entity.
	 * @param x die Start-x-Koordinate
	 * @param y die Start-y-Koordinate
	 * @param hp die Lebenspunkte
	 * @param attackDamage die Schadensst‰rke
	 * @param imageSet das {@link #imageSet}
	 */
	public Entity(float x, float y, float hp, float attackDamage, EntityImageSet imageSet) {
		this.x = x;
		this.y = y;
		this.imageSet = imageSet;
		this.hp = hp;
		this.attackDamage = attackDamage;
	}
	
	public void attack(Entity entity) {
		if(System.currentTimeMillis() - cooldownSet.lastTimeHit > attackDelay) {
			cooldownSet.lastTimeHit = System.currentTimeMillis();
			entity.onDamage(attackDamage, entity.x < x ? -0.1f : 0.1f);
		}
	}
	
	public void onDamage(float damage, float velocity) {
		if(System.currentTimeMillis() - cooldownSet.lastTimeDamage > damageDelay) {
			this.hp -= damage;
			this.vx += velocity;
			this.cooldownSet.lastTimeDamage = System.currentTimeMillis();
		}
	}
	
	/**
	 * Die CooldownSet-Klasse, welche ein Set an Cooldowns speichert.
	 * @author Mika Thein
	 * @version 1.0
	 */
	public class CooldownSet {
		
		/**
		 * Das letzte Mal, als geschlagen worden ist (Zeitpunkt in Millisekunden gem‰ﬂ {@code System.getCurrentMillis()}).
		 */
		public long lastTimeHit = 0;
		/**
		 * Das letzte Mal, als Schaden genommen worden ist (Zeitpunkt in Millisekunden gem‰ﬂ {@code System.getCurrentMillis()}).
		 */
		public long lastTimeDamage = 0;
		
	}

}
