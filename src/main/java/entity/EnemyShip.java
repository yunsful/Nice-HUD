package entity;

import Enemy.HpEnemyShip;
import Sound_Operator.SoundManager;
import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;

import java.awt.*;

/**
 * Implements a enemy ship, to be destroyed by the player.
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 */
public class EnemyShip extends Entity {
	/** Point value of a type A enemy. */
	private static final int A_TYPE_POINTS = 10;
	/** Point value of a type B enemy. */
	private static final int B_TYPE_POINTS = 20;
	/** Point value of a type C enemy. */
	private static final int C_TYPE_POINTS = 30;
	/** Point value of a type Explosive enemy. */
	private static final int EXPLOSIVE_TYPE_POINTS = 50; //Edited by Enemy
	/** Point value of a bonus enemy. */
	private static final int BONUS_TYPE_POINTS = 100;

	/** EnemyShip's health point */
	private int hp; // Add by team Enemy
	/** EnemyShip's Initial x-coordinate **/
	private int x; // Add by team Enemy
	/** EnemyShip's Initial y=coordinate **/
	private int y; // Add by team Enemy

	/** Cooldown between sprite changes. */
	private Cooldown animationCooldown;
	/** Checks if the ship has been hit by a bullet. */
	private boolean isDestroyed;
	/** Checks if the ship is bombed */
	private boolean isChainExploded;
	/** Values of the ship, in points, when destroyed. */
	private int pointValue;

	// Sound Operator
	private static SoundManager sm;

	/** Speed reduction or increase multiplier (1.0 means normal speed). */
	private double speedMultiplier;
	private double defaultSpeedMultiplier;

	/**
	 * Constructor, establishes the ship's properties.
	 *
	 * @param positionX
	 *            Initial position of the ship in the X axis.
	 * @param positionY
	 *            Initial position of the ship in the Y axis.
	 * @param spriteType
	 *            Sprite type, image corresponding to the ship.
	 */


	public EnemyShip(final int positionX, final int positionY,
			final SpriteType spriteType,int hp,int x, int y) {// Edited by Enemy
		super(positionX, positionY, 12 * 2, 8 * 2, HpEnemyShip.determineColor(hp));

		this.hp = hp;// Add by team Enemy
		this.spriteType = spriteType;
		this.animationCooldown = Core.getCooldown(500);
		this.isDestroyed = false;
		this.x = x; // Add by team enemy
		this.y = y; // Add by team enemy
		this.speedMultiplier=1.0; // default 1.0
		this.defaultSpeedMultiplier = 1.0;

		switch (this.spriteType) {
			case EnemyShipA1:
			case EnemyShipA2:
				this.pointValue = A_TYPE_POINTS;
				break;
			case EnemyShipB1:
			case EnemyShipB2:
				this.pointValue = B_TYPE_POINTS;
				break;
			case EnemyShipC1:
			case EnemyShipC2:
				this.pointValue = C_TYPE_POINTS;
				break;
			case ExplosiveEnemyShip1: //Edited by Enemy
			case ExplosiveEnemyShip2:
				super.setColor(new Color(237, 28, 36)); //set ExplosiveEnemyShip Color
				this.pointValue = EXPLOSIVE_TYPE_POINTS;
				break;
			default:
				this.pointValue = 0;
				break;
		}
	}

	/**
	 * Constructor, establishes the ship's properties for a special ship, with
	 * known starting properties.
	 */
	public EnemyShip() {
		super(-32, 60, 16 * 2, 7 * 2, Color.RED);

		this.hp = 1; // Add by team Enemy
		this.spriteType = SpriteType.EnemyShipSpecial;
		this.isDestroyed = false;
		this.pointValue = BONUS_TYPE_POINTS;
		this.x = -2;  // Add by team Enemy
		this.y = -2; // Add by team Enemy
	}


	/**
	 * Getter for the score bonus if this ship is destroyed.
	 *
	 * @return Value of the ship.
	 */
	public final int getPointValue() {
		return this.pointValue;
	}

	/**
	 * Moves the ship the specified distance.
	 *
	 * @param distanceX
	 *            Distance to move in the X axis.
	 * @param distanceY
	 *            Distance to move in the Y axis.
	 */
	public final void move(final int distanceX, final int distanceY) {
		this.positionX += distanceX * this.getSpeedMultiplier(); // team Inventory
		this.positionY += distanceY;
	}

	/**
	 * Updates attributes, mainly used for animation purposes.
	 */
	public final void update() {
		if (this.animationCooldown.checkFinished()) {
			this.animationCooldown.reset();

			switch (this.spriteType) {
				case EnemyShipA1:
					this.spriteType = SpriteType.EnemyShipA2;
					break;
				case EnemyShipA2:
					this.spriteType = SpriteType.EnemyShipA1;
					break;
				case EnemyShipB1:
					this.spriteType = SpriteType.EnemyShipB2;
					break;
				case EnemyShipB2:
					this.spriteType = SpriteType.EnemyShipB1;
					break;
				case EnemyShipC1:
					this.spriteType = SpriteType.EnemyShipC2;
					break;
				case EnemyShipC2:
					this.spriteType = SpriteType.EnemyShipC1;
					break;
				case ExplosiveEnemyShip1: //Edited by Enemy
					this.spriteType = SpriteType.ExplosiveEnemyShip2;
					break;
				case ExplosiveEnemyShip2: //Edited by Enemy
					this.spriteType = SpriteType.ExplosiveEnemyShip1;
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Destroys the ship, causing an explosion.
	 */
	public final void destroy() {
		this.isDestroyed = true;
		// Sound Operator
		sm = SoundManager.getInstance();
		if(this.spriteType == SpriteType.EnemyShipSpecial){
			sm.playES("special_enemy_die");
		}else{
			sm.playES("basic_enemy_die");
		}
		this.spriteType = SpriteType.Explosion;

	}

	/**
	 * Checks if the ship has been destroyed.
	 *
	 * @return True if the ship has been destroyed.
	 */
	public final boolean isDestroyed() {
		return this.isDestroyed;
	}


	/** Constructor for original EnemyShip that did not have hp.
	 * That enemyShip is moved to a constructor with the hp default of 1*/
	public EnemyShip(final int positionX, final int positionY,
					 final SpriteType spriteType){
		this(positionX,positionY,spriteType,1,-2,-2);
	}// Edited by Enemy

	/**
	 * Getter for the Hp of this Enemy ship.
	 *
	 * @return Hp of the ship.
	 */
	public final int getHp() {
		return this.hp;
	}// Added by team Enemy

	/**
	 * Setter for the Hp of the Enemy ship.
	 *
	 * @param hp
	 * 			New hp of the Enemy ship.
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}// Added by team Enemy

	/**
	 * Getter for the Initial x-coordinate of this EnemyShip.
	 *
	 * @return Initial x-coordinate of the ship.
	 */
	public int getX(){ return this.x;} // Add by team Enemy

	/**
	 * Getter for the Initial y-coordinate of this EnemyShip.
	 *
	 * @return Initial x-coordinate of the ship.
	 */
	public int getY(){ return this.y;} // Add by team Enemy

	/**
	 * Destroys ship, causing a chain explode.
	 */
	public final void chainExplode() { // Added by team Enemy
		destroy();
		setChainExploded(true);
		setHp(0);
	}

	/**
	 * Checks if the ship has been chain exploded.
	 *
	 * @return True if the ship has been chain exploded.
	 */
	public final boolean isChainExploded() {
		return this.isChainExploded;
	} // Added by team Enemy

	/**
	 * Setter for enemy ship's isChainExploded to false.
	 */
	public final void setChainExploded(boolean isChainExploded) {
		this.isChainExploded = isChainExploded;
	} // Added by team Enemy

	/**
	 * Getter for the current speed multiplier.
	 *
	 * @return The current speed multiplier.
	 */
	public void setSpeedMultiplier(double speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}
	public void resetSpeedMultiplier() {
		this.speedMultiplier = this.defaultSpeedMultiplier;
	}
	public double getSpeedMultiplier() {
		return this.speedMultiplier;
	}
}
