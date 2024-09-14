package entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Implements a pool of recyclable bullets.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public final class BulletPool {

	/** Set of already created bullets. */
	private static Set<Bullet> pool = new HashSet<Bullet>(); // 해시 테이블을 이용해 재사용 가능한 bullet을 저장할 pool을 생성

	/**
	 * Constructor, not called.
	 */
	private BulletPool() {

	}

	/**
	 * Returns a bullet from the pool if one is available, a new one if there
	 * isn't.
	 * 
	 * @param positionX
	 *            Requested position of the bullet in the X axis.
	 * @param positionY
	 *            Requested position of the bullet in the Y axis.
	 * @param speed
	 *            Requested speed of the bullet, positive or negative depending
	 *            on direction - positive is down.
	 * @return Requested bullet.
	 */
	public static Bullet getBullet(final int positionX,
			final int positionY, final int speed) {
		Bullet bullet;
		if (!pool.isEmpty()) { // pool에 재사용 가능한 bullet이 있다면
			bullet = pool.iterator().next(); // pool에서의 첫번째 bullet
			pool.remove(bullet); // 방금 가져온 bullet은 재사용 중이므로 pool에서 제거
			bullet.setPositionX(positionX - bullet.getWidth() / 2);
			bullet.setPositionY(positionY);
			bullet.setSpeed(speed); // bullet의 X축 위치, Y축 위치, 속도 설정
			bullet.setSprite(); // speed에 따라 스프라이트 타입 설정
		} else { // pool이 비어있다면
			bullet = new Bullet(positionX, positionY, speed); // 새로운 bullet 생성
			bullet.setPositionX(positionX - bullet.getWidth() / 2); // bullet의 X축 위치 설정
		}
		return bullet;
	}

	/**
	 * Adds one or more bullets to the list of available ones.
	 * 
	 * @param bullet
	 *            Bullets to recycle.
	 */
	public static void recycle(final Set<Bullet> bullet) {
		pool.addAll(bullet);
	} // set에 있는 bullet들을 pool에 추가
}
