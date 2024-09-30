package entity;

import java.awt.Color;
import java.util.Set;

import Enemy.PiercingBullet;
import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;
import Enemy.PiercingBulletPool;

// PlayerGrowth 클래스 임포트 / Import PlayerGrowth class
import Enemy.PlayerGrowth;

/**
 * Implements a ship, to be controlled by the player.
 *
 * 기존 코드에 PlayerGrowth 클래스를 사용한 추가 기능 적용 /
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

	/** PlayerGrowth 인스턴스 / PlayerGrowth instance */
	private PlayerGrowth growth;

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

		// PlayerGrowth 객체 생성 및 초기 스탯 설정 / Create PlayerGrowth object and set initial stats
		this.growth = new PlayerGrowth();
	}

	/**
	 * Moves the ship speed units right, or until the right screen border is
	 * reached.
	 */
	public final void moveRight() {
		this.positionX += growth.getMoveSpeed(); // PlayerGrowth로 이동 속도 적용 / Use PlayerGrowth for movement speed
	}

	/**
	 * Moves the ship speed units left, or until the left screen border is
	 * reached.
	 */
	public final void moveLeft() {
		this.positionX -= growth.getMoveSpeed(); // PlayerGrowth로 이동 속도 적용 / Use PlayerGrowth for movement speed
	}

	/**
	 * Shoots a bullet upwards.
	 *
	 * @param bullets
	 *            List of bullets on screen, to add the new bullet.
	 * @return Checks if the bullet was shot correctly.
	 */
	public final boolean shoot(final Set<PiercingBullet> bullets) {
		// PlayerGrowth에서 발사 딜레이 가져오기 / Get shooting delay from PlayerGrowth
		this.shootingCooldown = Core.getCooldown(growth.getShootingDelay());

		if (this.shootingCooldown.checkFinished()) {
			this.shootingCooldown.reset();

			// Add a piercing bullet fired by the player's ship.
			bullets.add(PiercingBulletPool.getPiercingBullet(
					positionX + this.width / 2,
					positionY,
					growth.getBulletSpeed(), // PlayerGrowth로 총알 속도 적용 / Use PlayerGrowth for bullet speed
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
	 * 스탯을 증가시키는 메서드들 (PlayerGrowth 클래스 사용)
	 * Methods to increase stats (using PlayerGrowth)
	 */

	// 체력 증가 / Increases health
	public void increaseHealth(int increment) {
		growth.increaseHealth(increment);
	}

	// 이동 속도 증가 / Increases movement speed
	public void increaseMoveSpeed(int increment) {
		growth.increaseMoveSpeed(increment);
	}

	// 총알 속도 증가 / Increases bullet speed
	public void increaseBulletSpeed(int increment) {
		growth.increaseBulletSpeed(increment);
	}

	// 발사 딜레이 감소 / Decreases shooting delay
	public void decreaseShootingDelay(int decrement) {
		growth.decreaseShootingDelay(decrement);
		this.shootingCooldown = Core.getCooldown(growth.getShootingDelay()); // 새로운 발사 딜레이 적용 / Apply new shooting delay
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
