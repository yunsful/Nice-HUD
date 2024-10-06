package engine;

/**
 * Implements an object that stores the state of the game between levels.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class GameState {

	/** Current game level. */
	private int level;
	/** Current score. */
	private int score;
	/** Lives currently remaining. */
	private int livesRemaining;
	/** Bullets shot until now. */
	private int bulletsShot;
	/** Ships destroyed until now. */
	private int shipsDestroyed;
	// Soomin Lee / TeamHUD
	/** Total time to play. */
	private int playTime;
	/** Current currency **/
	// Team-Ctrl-S(Currency)
	private int currency;

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
	 *
	 * Soomin Lee / TeamHUD
	 * @param playTime
	 * 	          Total time to play.
	 */
	public GameState(final int level, final int score,
					 final int livesRemaining, final int bulletsShot,
					 final int shipsDestroyed, final int playTime, final int currency) {
		this.level = level;
		this.score = score;
		this.livesRemaining = livesRemaining;
		this.bulletsShot = bulletsShot;
		this.shipsDestroyed = shipsDestroyed;
		this.playTime = playTime;
		this.currency = currency; // Team-Ctrl-S(Currency)
	}

	/**
	 * @return the level
	 */
	public final int getLevel() {
		return level;
	}

	/**
	 * @return the score
	 */
	public final int getScore() {
		return score;
	}

	/**
	 * @return the livesRemaining
	 */
	public final int getLivesRemaining() {
		return livesRemaining;
	}

	/**
	 * @return the bulletsShot
	 */
	public final int getBulletsShot() {
		return bulletsShot;
	}

	/**
	 * @return the shipsDestroyed
	 */
	public final int getShipsDestroyed() {
		return shipsDestroyed;
	}

	/**
	 * Soomin Lee / TeamHUD
	 * @return the playTime
	 */
	public final int getTime() { return playTime; }

	/**
	 * @return the currency
	 */
	// Team-Ctrl-S(Currency)
	public final int getCurrency() {
		return currency;
	}

}
