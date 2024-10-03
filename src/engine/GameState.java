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
	public static int score; // CLOVER Dongjun Suh : Changed score from private to public for usage in achievement
	/** Lives currently remaining. */
	public static int livesRemaining; // CLOVER Dongjun Suh : Changed livesRemaining from private to public for usage in achievement
	/** Bullets shot until now. */
	private int bulletsShot;
	/** Ships destroyed until now. */
	private int shipsDestroyed;
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
	 */
	public GameState(final int level, final int score,
			final int livesRemaining, final int bulletsShot,
			final int shipsDestroyed, final int currency) {
		this.level = level;
		this.score = score;
		this.livesRemaining = livesRemaining;
		this.bulletsShot = bulletsShot;
		this.shipsDestroyed = shipsDestroyed;
		this.currency = currency; // Team-Ctrl-S(Currency)
	}

	public static int livesRemaining() { // CLOVER DongjunSuh : Added public static for achievement
		return livesRemaining;
	}

	/**
	 * Team Clove Create Constructor for using in "engine.Score"
	 *
	 * Constructor for Save file
	 *
	 * @param bulletsShot
	 *            Bullets shot until now.
	 * @param shipsDestroyed
	 *            Ships destroyed until now.
	 * @param level
	 *            Current game level.
	 */
	public GameState(int bulletsShot, int shipsDestroyed, int level) { //Team Clove
		this.bulletsShot = bulletsShot;
		this.shipsDestroyed = shipsDestroyed;
		this.level = level;
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
	public final int getScore() { return score; }

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
	 * @return the currency
	 */
	// Team-Ctrl-S(Currency)
	public final int getCurrency() {
		return currency;
	}

}
