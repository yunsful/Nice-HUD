package entity;

import java.awt.Color;
import java.util.Set;

import Enemy.PiercingBullet; // Edited by Enemy
import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;
import Enemy.PiercingBulletPool; // Edited by Enemy

// Import PlayerGrowth class
import Enemy.PlayerGrowth; // Edited by Enemy

/**
 * Implements a ship, to be controlled by the player.
 *
 * Adds functionality for player growth based on PlayerGrowth class without modifying existing code.
 *
 * @author Roberto Izquierdo Amo
 *
 */
public class Ship extends Entity {

	/** Time between shots. */
	private static final int SHOOTING_INTERVAL = 750;
	/** Speed of the bullets shot by the ship. */
	private static final int BULLET_SPEED = -6;
	/** Movement of the ship for each unit of time. */
	private static final int SPEED = 2;

	/** Minimum time between shots. */
	private Cooldown shootingCooldown;
	/** Time spent inactive between hits. */
	private Cooldown destructionCooldown;

	/** PlayerGrowth instance */
	private PlayerGrowth growth; //Edited by Enemy

	/**
	 * Constructor, establishes the ship's properties.
	 *
	 * @param positionX
	 *            Initial position of the ship in the X axis.
	 * @param positionY
	 *            Initial position of the ship in the Y axis.
	 */
	public Ship(final int positionX, final int positionY) {
		super(positionX, positionY, 13 * 2, 8 * 2, Color.GREEN);

		this.spriteType = SpriteType.Ship;
		this.shootingCooldown = Core.getCooldown(SHOOTING_INTERVAL);
		this.destructionCooldown = Core.getCooldown(1000);

		//Create PlayerGrowth object and set initial stats
		this.growth = new PlayerGrowth(); //Edited by Enemy
	}

	/**
	 * Moves the ship speed units right, or until the right screen border is
	 * reached.
	 */
	public final void moveRight() {
		this.positionX += growth.getMoveSpeed(); // Use PlayerGrowth for movement speed //Edited by Enemy
	}

	/**
	 * Moves the ship speed units left, or until the left screen border is
	 * reached.
	 */
	public final void moveLeft() {
		this.positionX -= growth.getMoveSpeed(); // Use PlayerGrowth for movement speed//Edited by Enemy
	}

	/**
	 * Shoots a bullet upwards.
	 *
	 * @param bullets
	 *            List of bullets on screen, to add the new bullet.
	 * @return Checks if the bullet was shot correctly.
	 */
	public final boolean shoot(final Set<PiercingBullet> bullets) {
		// Get shooting delay from PlayerGrowth //Edited by Enemy
		this.shootingCooldown = Core.getCooldown(growth.getShootingDelay());

		if (this.shootingCooldown.checkFinished()) {
			this.shootingCooldown.reset();

			// Add a piercing bullet fired by the player's ship.
			bullets.add(PiercingBulletPool.getPiercingBullet(
					positionX + this.width / 2,
					positionY,
					growth.getBulletSpeed(), // Use PlayerGrowth for bullet speed //Edited by Enemy
					2 // Number of enemies the bullet can pierce
			));
			return true;
		}
		return false;
	}


	/**
	 * Updates status of the ship.
	 */
	public final void update() {
		if (!this.destructionCooldown.checkFinished())
			this.spriteType = SpriteType.ShipDestroyed;
		else
			this.spriteType = SpriteType.Ship;
	}

	/**
	 * Switches the ship to its destroyed state.
	 */
	public final void destroy() {
		this.destructionCooldown.reset();
	}

	/**
	 * Checks if the ship is destroyed.
	 *
	 * @return True if the ship is currently destroyed.
	 */
	public final boolean isDestroyed() {
		return !this.destructionCooldown.checkFinished();
	}

	/**
	 *
	 * Methods to increase stats (using PlayerGrowth)
	 */

	//Increases health //Edited by Enemy
	public void increaseHealth(int increment) {
		growth.increaseHealth(increment);
	}

	//Increases movement speed //Edited by Enemy
	public void increaseMoveSpeed(int increment) {
		growth.increaseMoveSpeed(increment);
	}

	//Increases bullet speed //Edited by Enemy
	public void increaseBulletSpeed(int increment) {
		growth.increaseBulletSpeed(increment);
	}

	//Decreases shooting delay //Edited by Enemy
	public void decreaseShootingDelay(int decrement) {
		growth.decreaseShootingDelay(decrement);
		this.shootingCooldown = Core.getCooldown(growth.getShootingDelay()); // Apply new shooting delay //Edited by Enemy
	}

	/**
	 * Getter for the ship's speed.
	 *
	 * @return Speed of the ship.
	 */
	public final int getSpeed() {
		return SPEED;
	}
}
