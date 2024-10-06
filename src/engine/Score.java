package engine;

import java.util.logging.Level;
import java.time.LocalDate;

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

	private LocalDate currentDate;
	/** Integers for describe present Date */
	private int Year;
	private int Month;
	private int Day;
	/** String for storage present Date */
	private String Date;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            Player name, three letters.
	 * @param score
	 *            Player score.
	 * Added non-parameter elements for Date // Clove
	 */
	public Score(final String name, final int score) {
		this.name = name;
		this.score = score;

		this.currentDate = LocalDate.now();
		this.Year = currentDate.getYear();
		this.Month = currentDate.getMonthValue();
		this.Day = currentDate.getDayOfMonth();
		this.Date = new String (Year+"-"+Month+"-"+Day);
	}

	/**
	 * Constructor for read Record files
	 *
	 * @param name
	 * 				Player name but non-value parameter, just for overload constructor
	 * @param score
	 * 				Player score
	 * @param date
	 * 				Date (especially Time Player played the game.
	 */
	public Score(final String name, final int score, final String date) {
		this.name = name;
		this.score = score;
		this.Date = date;

	}


	/**
	 * Constructor for Save UserData
	 *
	 * @param bulletsShot
	 * 				Number of Fired Bullets
	 * @param shipsDestroyed
	 * 				Number of Destroyed Ships
	 * @param level
	 * 				Player Reached Level
	 */
	/*
		Team Clove Create Constructor for Save UserData Variable
		Additional Parameters
		+ bulletShot, shipsDestroyed, level
	*/
	public Score(int bulletsShot, int shipsDestroyed, int level) {
		GameState gameState = new GameState(bulletsShot, shipsDestroyed, level);
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
	 * Getter for Date
	 *
	 * @return Object currentDate
	 */
	public final LocalDate getcurrentDate() { return this.currentDate; }

	/**
	 * Getter for String type Date
	 *
	 * @return Date
	 */
	public final String getDate() { return this.Date; }

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
