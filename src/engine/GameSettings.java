package engine;

/**
 * Implements an object that stores a single game's difficulty settings.
 * 한 게임당 난이도 세팅
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class GameSettings {

	/** Width of the level's enemy formation.
	 * 해당 레벨의 가로 방향 적 숫자
	 */
	private int formationWidth;
	/** Height of the level's enemy formation.
	 * 해당 레벨의 세로 방향 적 숫자
	 */
	private int formationHeight;
	/** Speed of the enemies, function of the remaining number.
	 * 적들의 기본 속도
	 */
	private int baseSpeed;
	/** Frequency of enemy shootings, +/- 30%. 
	 * 적 발사 빈도
	 */
	private int shootingFrecuency;

	/**
	 * Constructor.
	 * 가로 세로 적 함선 수와 속도, 발사 빈도 초기화
	 * 
	 * @param formationWidth
	 *            Width of the level's enemy formation.
	 * @param formationHeight
	 *            Height of the level's enemy formation.
	 * @param baseSpeed
	 *            Speed of the enemies.
	 * @param shootingFrecuency
	 *            Frecuency of enemy shootings, +/- 30%.
	 */
	public GameSettings(final int formationWidth, final int formationHeight,
			final int baseSpeed, final int shootingFrecuency) {
		this.formationWidth = formationWidth;
		this.formationHeight = formationHeight;
		this.baseSpeed = baseSpeed;
		this.shootingFrecuency = shootingFrecuency;
	}

	/**
	 * @return the formationWidth
	 * 가로 방향 적 함선 수 반환
	 */
	public final int getFormationWidth() {
		return formationWidth;
	}

	/**
	 * @return the formationHeight
	 * 세로 방향 적 함선 수 반환
	 */
	public final int getFormationHeight() {
		return formationHeight;
	}

	/**
	 * @return the baseSpeed
	 * 적 속도 반환
	 */
	public final int getBaseSpeed() {
		return baseSpeed;
	}

	/**
	 * @return the shootingFrecuency
	 * 적 함선 발사 빈도 반환
	 */
	public final int getShootingFrecuency() {
		return shootingFrecuency;
	}

}
