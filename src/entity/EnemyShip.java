package entity;

import java.awt.Color;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;

/**
 * Implements a enemy ship, to be destroyed by the player.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class EnemyShip extends Entity {

	// 어느 타입의 적 함선이냐에 따라 점수를 다르게 함
	/** Point value of a type A enemy. */
	private static final int A_TYPE_POINTS = 10;
	/** Point value of a type B enemy. */
	private static final int B_TYPE_POINTS = 20;
	/** Point value of a type C enemy. */
	private static final int C_TYPE_POINTS = 30;
	/** Point value of a bonus enemy. */
	private static final int BONUS_TYPE_POINTS = 100;

	/** Cooldown between sprite changes. */
	private Cooldown animationCooldown;
	/** Checks if the ship has been hit by a bullet.
	 * 파괴 되었는지 여부를 저장하는 변수. 생성자에 의해 false로 초기화. */
	private boolean isDestroyed;
	/** Values of the ship, in points, when destroyed.
	 * 함선이 파괴되었을 때 얻는 점수. 생성자에서 타입에 따라 초기화. */
	private int pointValue;

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
	// 적함선 Entity의 위치(x, y)와 어느 타입의 함선인지(spriteType)을 받아 생성
	// animationCooldown, isDestroyed, pointValue 값 초기화
	public EnemyShip(final int positionX, final int positionY,
			final SpriteType spriteType) {
		super(positionX, positionY, 12 * 2, 8 * 2, Color.WHITE);

		this.spriteType = spriteType;
		this.animationCooldown = Core.getCooldown(500);
		this.isDestroyed = false;

		switch (this.spriteType) {
			// A 타입인 경우
		case EnemyShipA1:
		case EnemyShipA2:
			this.pointValue = A_TYPE_POINTS;
			break;
			// B 타입인 경우
		case EnemyShipB1:
		case EnemyShipB2:
			this.pointValue = B_TYPE_POINTS;
			break;
			// C 타입인 경우
		case EnemyShipC1:
		case EnemyShipC2:
			this.pointValue = C_TYPE_POINTS;
			break;
			// 기본 점수 = 0
		default:
			this.pointValue = 0;
			break;
		}
	}

	/**
	 * Constructor, establishes the ship's properties for a special ship, with
	 * known starting properties.
	 */
	// 아무런 인수를 넘기지 않고 생성한 EnemyShip 은 EnemyShipSpecial
	public EnemyShip() {
		super(-32, 60, 16 * 2, 7 * 2, Color.RED);

		this.spriteType = SpriteType.EnemyShipSpecial;
		this.isDestroyed = false;
		this.pointValue = BONUS_TYPE_POINTS;
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
	// EnemyShip Entity 를 (x, y) 칸 만큼 이동
	public final void move(final int distanceX, final int distanceY) {
		this.positionX += distanceX;
		this.positionY += distanceY;
	}

	/**
	 * Updates attributes, mainly used for animation purposes.
	 */
	// 업데이트 될 때마다 움직임 추가
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
			default:
				break;
			}
		}
	}

	/**
	 * Destroys the ship, causing an explosion.
	 */
	// 파괴되는 경우 isDestroyed 변수 업데이트 및 SpriteType 변경
	public final void destroy() {
		this.isDestroyed = true;
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
}
