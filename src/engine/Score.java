package engine;

import java.util.logging.Level;

/**
 * Implements a high score record.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Score implements Comparable<Score> {

	/** Player's name. */
	private String name;
	/** Score points. */
	private int score;

	/** The number of bullets fired by the player. */
	private int bulletsShot;
	/** The number of ships destroyed by the player. */
	private int shipsDestroyed;
	/** The level reached by the player. */
	private int level;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            Player name, three letters.
	 * @param score
	 *            Player score.
	 * @param bulletsShot
	 * 			  Number of Bullets shot.
	 * @param shipsDestroyed
	 *			  Number of Ships destroyed.
	 * @param level
	 * 			  Level the player has achieved.
	 */
	/*
		Team Clove Fixed Constructor for Save UserData Variable
		Additional Parameters
		+ score, bulletShot, shipsDestroyed, level
	*/
	public Score(final String name, final int score, final int bulletsShot, final int shipsDestroyed, final int level) {
		GameState gameState = new GameState(bulletsShot, shipsDestroyed, level);
		this.name = name;
		this.score = score;
		this.bulletsShot = gameState.getBulletsShot();
		this.shipsDestroyed = gameState.getShipsDestroyed();
		this.level = gameState.getLevel();
	}

	/**
	 * Getter for the player's name.
	 * 
	 * @return Name of the player.
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Getter for the player's score.
	 * 
	 * @return High score.
	 */
	public final int getScore() { return this.score; }

	//Team Clove Create GetVariable Functions
	public final int getBulletsShot() { return this.bulletsShot; }
	public final int getShipsDestroyed() { return this.shipsDestroyed; }
	public final int getLevel() { return this.level; }
	/**
	 * Orders the scores descending by score.
	 * 
	 * @param score
	 *            Score to compare the current one with.
	 * @return Comparison between the two scores. Positive if the current one is
	 *         smaller, positive if its bigger, zero if its the same.
	 */

	@Override
	public final int compareTo(final Score score) {
		int comparison = this.score < score.getScore() ? 1 : this.score > score
				.getScore() ? -1 : 0;
		return comparison;
	}

}
