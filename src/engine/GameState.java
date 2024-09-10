package engine;

/**
 * Implements an object that stores the state of the game between levels.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class GameState {

	/** 현재 게임 레벨 */
	private int level;
	/** 현재 스코어 */
	private int score;
	/** 현재 남은 목숨 */
	private int livesRemaining;
	/** 지금 까지의 bulletshot */
	private int bulletsShot;
	/** 지금까지 부신 함선 개수 */
	private int shipsDestroyed;

	/**
	 * Constructor.
	 * 
	 * @param level
	 *            Current game level.
	 * @param score
	 *            Current score.
	 * @param livesRemaining
	 *            Lives currently remaining.
	 * @param bulletsShot
	 *            Bullets shot until now.
	 * @param shipsDestroyed
	 *            Ships destroyed until now.
	 */
	// 레벨, 스코어, 남은목숨, bulletshot, 부신 적 함선 개수 를 인자로 받아서 각각 초기화.
	public GameState(final int level, final int score,
			final int livesRemaining, final int bulletsShot,
			final int shipsDestroyed) {
		this.level = level;
		this.score = score;
		this.livesRemaining = livesRemaining;
		this.bulletsShot = bulletsShot;
		this.shipsDestroyed = shipsDestroyed;
	}

	/**
	 * @return level getter
	 */
	public final int getLevel() {
		return level;
	}

	/**
	 * @return the score getter
	 */
	public final int getScore() {
		return score;
	}

	/**
	 * @return the livesRemaining getter
	 */
	public final int getLivesRemaining() {
		return livesRemaining;
	}

	/**
	 * @return the bulletsShot getter
	 */
	public final int getBulletsShot() {
		return bulletsShot;
	}

	/**
	 * @return the shipsDestroyed getter
	 */
	public final int getShipsDestroyed() {
		return shipsDestroyed;
	}

}
