package Main;

import java.io.File;

/**
 * Die Entity-Klasse.
 * @author Mika Thein
 * @version 1.0
 */
public class Entity {
	
	/**
	 * Die maximale horizontale Geschwindigkeit eines Entities in Blöcken pro Tick.
	 */
	static public final float maxVelocityX = 0.1f;
	
	/**
	 * Die x-Position in Blï¿½cken.
	 */
	public float x;
	/**
	 * Die y-Position in Blï¿½cken.
	 */
	public float y;
	/**
	 * Die horizontale Geschwindigkeit in Blï¿½cken pro Tick.
	 */
	public float vx = 0;
	/**
	 * Die vertikale Geschwindigkeit in Blï¿½cken pro Tick.
	 */
	public float vy = 0;
	
	/**
	 * Die Startgeschwindigkeit bei einem Sprung in Blï¿½cken pro Tick.
	 */
	public float jumpSpeed = 0.175f; // > 0
	/**
	 * Die Bewegungsgeschwindigkeit in Blï¿½cken pro Tick.
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
	 * Die volle Anzahl an Lebenspunkten.
	 */
	public float maxHp;
	/**
	 * Die Schadensstï¿½rke.
	 */
	public float attackDamage;
	
	/**
	 * Die Reichweite.
	 */
	public float range;
	/**
	 * Der Anteil der GesamthÃ¶he, von welchem die Range des Entitys beginnt.
	 */
	public float hitHeightRatio = 2f/3f;
	
	/**
	 * Die HitBox-Weite in Pixeln.
	 */
	public int hitBoxWidth = EntityImageSet.width;
	/**
	 * Die HitBox-Hï¿½he in Pixeln.
	 */
	public int hitBoxHeight = EntityImageSet.height;
	
	/**
	 * Attackierungscooldown in Millisekunden.
	 */
	public long attackDelay = 500;
	/**
	 * Schadenschutzdauer in Millisekunden.
	 */
	public long damageDelay = 750;
	
	/**
	 * Ein CooldownSet zum Abgleich mit den Cooldowns.
	 */
	CooldownSet cooldownSet = new CooldownSet();
	
	/**
	 * Erstellt ein neues Entity.
	 * @param x die Start-x-Koordinate
	 * @param y die Start-y-Koordinate
	 * @param hp die Lebenspunkte
	 * @param attackDamage die Schadensstï¿½rke
	 * @param imageSet das {@link #imageSet}
	 */
	public Entity(float x, float y, float hp, float attackDamage, float range, EntityImageSet imageSet) {
		this.x = x;
		this.y = y;
		this.imageSet = imageSet;
		this.hp = hp;
		this.maxHp = hp;
		this.range  = range;
		this.attackDamage = attackDamage;
	}
	
	public void attack(Entity entity) {
		if(System.currentTimeMillis() - cooldownSet.lastTimeHit > attackDelay) {
			cooldownSet.lastTimeHit = System.currentTimeMillis();
			Game.sound.playPunchSounds();
			if(entity instanceof Player) entity.onDamage(attackDamage, entity.x < x ? -0.1f : 0.1f, this);
			else entity.onDamage(attackDamage, entity.x < x ? -1.5f : 1.5f, this);
		}
	}
	
	public void onDamage(float damage, float velocity, Entity attacker) {
		if(System.currentTimeMillis() - cooldownSet.lastTimeDamage > damageDelay) {
			if(attacker instanceof Player) ((Player) attacker).lastEntityHit = this;
			this.hp -= damage;
			this.vx += velocity;
			if(this instanceof Player) Game.sound.playPlayerTakesDamageSounds();
			else Game.sound.playMobTakesDamageSounds();
			if(Math.abs(this.vx) > maxVelocityX) this.vx = Math.copySign(maxVelocityX, velocity);
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
		 * Das letzte Mal, als geschlagen worden ist (Zeitpunkt in Millisekunden gemï¿½ï¿½ {@code System.getCurrentMillis()}).
		 */
		public long lastTimeHit = 0;
		/**
		 * Das letzte Mal, als Schaden genommen worden ist (Zeitpunkt in Millisekunden gemï¿½ï¿½ {@code System.getCurrentMillis()}).
		 */
		public long lastTimeDamage = 0;
		
	}

}
