package entity;

import java.awt.Color;

import engine.DrawManager.SpriteType;

/**
 * Implements a bullet that moves vertically up or down.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Bullet extends Entity {

	/**
	 * Speed of the bullet, positive or negative depending on direction -
	 * positive is down.
	 */
	private int speed;
	private boolean isPiercing;  // Flag indicating whether the bullet is piercing
	private int piercingCount;   // Variable to track the number of enemies the bullet can pierce
	protected boolean isDestroyed;

	/**
	 * Constructor, establishes the bullet's properties.
	 * 
	 * @param positionX
	 *            Initial position of the bullet in the X axis.
	 * @param positionY
	 *            Initial position of the bullet in the Y axis.
	 * @param speed
	 *            Speed of the bullet, positive or negative depending on
	 *            direction - positive is down.
	 */
	public Bullet(final int positionX, final int positionY, final int speed, boolean isPiercing, int piercingCount) {
		super(positionX, positionY, 3 * 2, 5 * 2, Color.WHITE);

		this.speed = speed;
		setSprite();
		this.isPiercing = isPiercing; // Set whether the bullet is piercing
		this.piercingCount = piercingCount; // Set the number of times the bullet can pierce
		this.isDestroyed = false; // Set the destroyed state to false upon initialization
	}

	// Getter for whether the bullet is piercing
	public boolean isPiercing() {
		return isPiercing;
	}

	// Setter for whether the bullet is piercing
	public void setPiercing(boolean isPiercing) {
		this.isPiercing = isPiercing;
	}

	// Setter for piercing count
	public void setPiercingCount(int piercingCount) {
		this.piercingCount = piercingCount;
	}

	/**
	 * Collision handling logic
	 */
	public void onCollision(Entity entity) {
		if (this.isPiercing) {
			// If the bullet is piercing, decrease the number of pierceable targets
			this.piercingCount--;
			// If the piercing count reaches 0, destroy the bullet
			if (this.piercingCount <= 0) {
				this.destroy();
			}
		} else {
			// If the bullet is not piercing, destroy it immediately
			this.destroy();
		}
	}

	/**
	 * Destroys the ship, causing an explosion.
	 */
	public void destroy() {
		this.isDestroyed = true;
		this.spriteType = SpriteType.Explosion;
	}

	/**
	 * Checks if the ship has been destroyed.
	 *
	 * @return True if the ship has been destroyed.
	 */
	public boolean isDestroyed() {
		return this.isDestroyed;
	}

	/**
	 * Sets correct sprite for the bullet, based on speed.
	 */
	public final void setSprite() {
		if (speed < 0)
			this.spriteType = SpriteType.Bullet;
		else
			this.spriteType = SpriteType.EnemyBullet;
	}

	/**
	 * Updates the bullet's position.
	 */
	public final void update() {
		this.positionY += this.speed;
	}

	/**
	 * Setter of the speed of the bullet.
	 * 
	 * @param speed
	 *            New speed of the bullet.
	 */
	public final void setSpeed(final int speed) {
		this.speed = speed;
	}

	/**
	 * Getter for the speed of the bullet.
	 * 
	 * @return Speed of the bullet.
	 */
	public final int getSpeed() {
		return this.speed;
	}
}
