package screen;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import engine.Cooldown;
import engine.Core;
import engine.GameSettings;
import engine.GameState;
import entity.Bullet;
import entity.BulletPool;
import entity.EnemyShip;
import entity.EnemyShipFormation;
import entity.Entity;
import entity.Ship;

/**
 * Implements the game screen, where the action happens.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class GameScreen extends Screen {

	/** Milliseconds until the screen accepts user input. */
	private static final int INPUT_DELAY = 6000;
	/** Bonus score for each life remaining at the end of the level. */
	private static final int LIFE_SCORE = 100;
	/** Minimum time between bonus ship's appearances. */
	private static final int BONUS_SHIP_INTERVAL = 20000;
	/** Maximum variance in the time between bonus ship's appearances. */
	private static final int BONUS_SHIP_VARIANCE = 10000;
	/** Time until bonus ship explosion disappears. */
	private static final int BONUS_SHIP_EXPLOSION = 500;
	/** Time from finishing the level to screen change. */
	private static final int SCREEN_CHANGE_INTERVAL = 1500;
	/** Height of the interface separation line. */
	private static final int SEPARATION_LINE_HEIGHT = 40;

	/** Current game difficulty settings. */
	private GameSettings gameSettings;
	/** Current difficulty level number. */
	private int level;
	/** Formation of enemy ships. */
	private EnemyShipFormation enemyShipFormation;
	/** Player's ship. */
	private Ship ship;
	/** Bonus enemy ship that appears sometimes. */
	private EnemyShip enemyShipSpecial;
	/** Minimum time between bonus ship appearances. */
	private Cooldown enemyShipSpecialCooldown;
	/** Time until bonus ship explosion disappears. */
	private Cooldown enemyShipSpecialExplosionCooldown;
	/** Time from finishing the level to screen change. */
	private Cooldown screenFinishedCooldown;
	/** Set of all bullets fired by on screen ships. */
	private Set<Bullet> bullets;
	/** Current score. */
	private int score;
	/** Player lives left. */
	private int lives;
	/** Total bullets shot by the player. */
	private int bulletsShot;
	/** Total ships destroyed by the player. */
	private int shipsDestroyed;
	/** Moment the game starts. */
	private long gameStartTime;
	/** Checks if the level is finished. */
	private boolean levelFinished;
	/** Checks if a bonus life is received. */
	private boolean bonusLife;

	/**
	 * Constructor, establishes the properties of the screen.
	 * 
	 * @param gameState
	 *            Current game state.
	 * @param gameSettings
	 *            Current game settings.
	 * @param bonnusLife
	 *            Checks if a bonus life is awarded this level.
	 * @param width
	 *            Screen width.
	 * @param height
	 *            Screen height.
	 * @param fps
	 *            Frames per second, frame rate at which the game is run.
	 */
	public GameScreen(final GameState gameState,
			final GameSettings gameSettings, final boolean bonusLife,
			final int width, final int height, final int fps) {
		super(width, height, fps);

		this.gameSettings = gameSettings;
		this.bonusLife = bonusLife;
		this.level = gameState.getLevel();
		this.score = gameState.getScore();
		this.lives = gameState.getLivesRemaining();
		if (this.bonusLife)
			this.lives++;
		this.bulletsShot = gameState.getBulletsShot();
		this.shipsDestroyed = gameState.getShipsDestroyed();
	}

	/**
	 * Initializes basic screen properties, and adds necessary elements.
	 */
	public final void initialize() {
		super.initialize();

		enemyShipFormation = new EnemyShipFormation(this.gameSettings);
		enemyShipFormation.attach(this);
		this.ship = new Ship(this.width / 2, this.height - 30);
		// Appears each 10-30 seconds.
		this.enemyShipSpecialCooldown = Core.getVariableCooldown(
				BONUS_SHIP_INTERVAL, BONUS_SHIP_VARIANCE);
		this.enemyShipSpecialCooldown.reset();
		this.enemyShipSpecialExplosionCooldown = Core
				.getCooldown(BONUS_SHIP_EXPLOSION);
		this.screenFinishedCooldown = Core.getCooldown(SCREEN_CHANGE_INTERVAL);
		this.bullets = new HashSet<Bullet>();

		// Special input delay / countdown.
		this.gameStartTime = System.currentTimeMillis();
		this.inputDelay = Core.getCooldown(INPUT_DELAY);
		this.inputDelay.reset();
	}

	/**
	 * Starts the action.
	 * 
	 * @return Next screen code.
	 */
	public final int run() {
		super.run();

		this.score += LIFE_SCORE * (this.lives - 1);
		this.logger.info("Screen cleared with a score of " + this.score);

		return this.returnCode;
	}

	/**
	 * Updates the elements on screen and checks for events.
	 */
	protected final void update() {
		super.update();

		if (this.inputDelay.checkFinished() && !this.levelFinished) { //inputDelay 쿨타임이 끝났고 레벨이 클리어 안 됐으면

			if (!this.ship.isDestroyed()) { //함선이 아직 안부숴졌으면
				boolean moveRight = inputManager.isKeyDown(KeyEvent.VK_RIGHT)
						|| inputManager.isKeyDown(KeyEvent.VK_D);//오른쪽 방향키나 D 입력시 moveRight에 true
				boolean moveLeft = inputManager.isKeyDown(KeyEvent.VK_LEFT)
						|| inputManager.isKeyDown(KeyEvent.VK_A);//왼쪽 방향키나 A 입력시 moveRight에 true

				boolean isRightBorder = this.ship.getPositionX()
						+ this.ship.getWidth() + this.ship.getSpeed() > this.width - 1; //함선이 화면 오른쪽 끝에 있는지 확인
				boolean isLeftBorder = this.ship.getPositionX()
						- this.ship.getSpeed() < 1; //함선이 왼쪽 끝에 있는지 확인

				if (moveRight && !isRightBorder) {
					this.ship.moveRight(); //moveRight이 true고 화면의 오른쪽 경계선이 아니면 오른쪽으로 이동
				}
				if (moveLeft && !isLeftBorder) {
					this.ship.moveLeft(); //moveLeft가 true고 화면의 왼쪽 경계선이 아니면 왼쪽으로 이동
				}
				if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) //스페이스바 입력시
					if (this.ship.shoot(this.bullets)) //총알을 발사
						this.bulletsShot++; //bulletsShot 1 증가.
			}

			if (this.enemyShipSpecial != null) { // 적의 스페셜 함선이 null이 아니면(이미 있으면)
				if (!this.enemyShipSpecial.isDestroyed()) // 스페셜 함선이 부숴지지 않았다면
					this.enemyShipSpecial.move(2, 0); //x축으로 2만큼 이동
				else if (this.enemyShipSpecialExplosionCooldown.checkFinished()) //적 스페셜 함선이 있을 시간이 다 지났다면
					this.enemyShipSpecial = null; //null로 변경

			}
			if (this.enemyShipSpecial == null
					&& this.enemyShipSpecialCooldown.checkFinished()) { // 스페셜 함선 등장 쿨타임이 다 돌고 null상태면
				this.enemyShipSpecial = new EnemyShip(); //새로운 스페셜 함선 생성
				this.enemyShipSpecialCooldown.reset(); //등장 쿨타임 재시작
				this.logger.info("A special ship appears"); //log에 메세지 출력
			}
			if (this.enemyShipSpecial != null
					&& this.enemyShipSpecial.getPositionX() > this.width) { //스페셜 함선이 있지만, 화면 밖으로 나갔다면
				this.enemyShipSpecial = null; //null 로 변경
				this.logger.info("The special ship has escaped"); // log메세지 출력
			}

			this.ship.update(); // 함선을 업데이트
			this.enemyShipFormation.update(); // 적 함선 대형을 업데이트
			this.enemyShipFormation.shoot(this.bullets); //적 함선 대형이 쏘는 총알을 업데이트
		}

		manageCollisions();
		cleanBullets();
		draw();

		if ((this.enemyShipFormation.isEmpty() || this.lives == 0) //(적 함선 대형이 비었거나 목숨이 0이고) 레벨이 클리어 안됐을때,
				&& !this.levelFinished) {
			this.levelFinished = true; // 레벨 완료됨(클리어 or 죽음)
			this.screenFinishedCooldown.reset(); //스크린 완료 쿨타임 재시작
		}

		if (this.levelFinished && this.screenFinishedCooldown.checkFinished()) //스크린 완료 쿨타임 다 돌았고 레벨이 완료되었다면
			this.isRunning = false; //화면 실행 종료

	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);

		drawManager.drawEntity(this.ship, this.ship.getPositionX(),
				this.ship.getPositionY());
		if (this.enemyShipSpecial != null)
			drawManager.drawEntity(this.enemyShipSpecial,
					this.enemyShipSpecial.getPositionX(),
					this.enemyShipSpecial.getPositionY());

		enemyShipFormation.draw();

		for (Bullet bullet : this.bullets)
			drawManager.drawEntity(bullet, bullet.getPositionX(),
					bullet.getPositionY());

		// Interface.
		drawManager.drawScore(this, this.score);
		drawManager.drawLives(this, this.lives);
		drawManager.drawHorizontalLine(this, SEPARATION_LINE_HEIGHT - 1);

		// Countdown to game start.
		if (!this.inputDelay.checkFinished()) {
			int countdown = (int) ((INPUT_DELAY
					- (System.currentTimeMillis()
							- this.gameStartTime)) / 1000);
			drawManager.drawCountDown(this, this.level, countdown,
					this.bonusLife);
			drawManager.drawHorizontalLine(this, this.height / 2 - this.height
					/ 12);
			drawManager.drawHorizontalLine(this, this.height / 2 + this.height
					/ 12);
		}

		drawManager.completeDrawing(this);
	}

	/**
	 * Cleans bullets that go off screen.
	 */
	private void cleanBullets() {
		Set<Bullet> recyclable = new HashSet<Bullet>();
		for (Bullet bullet : this.bullets) {
			bullet.update();
			if (bullet.getPositionY() < SEPARATION_LINE_HEIGHT
					|| bullet.getPositionY() > this.height) // 화면 밖의 총알이면
				recyclable.add(bullet); // 재활용 가능한 총알에 추가
		}
		this.bullets.removeAll(recyclable); // 재활용 가능한 애들을 모두 bullet에서 제거후
		BulletPool.recycle(recyclable); // 재활용
	}

	/**
	 * Manages collisions between bullets and ships.
	 */
	private void manageCollisions() {
		Set<Bullet> recyclable = new HashSet<Bullet>();
		for (Bullet bullet : this.bullets)
			if (bullet.getSpeed() > 0) { // 총알이 움직이면
				if (checkCollision(bullet, this.ship) && !this.levelFinished) { //레벨이 완료가 안됐고 총알과 함선이 충돌했다면
					recyclable.add(bullet); //해당 총알 재활용 가능 총알에 추가
					if (!this.ship.isDestroyed()) { //함선이 안 부숴졌다면
						this.ship.destroy(); //함선 파괴
						this.lives--; // 목숨 1 감소
						this.logger.info("Hit on player ship, " + this.lives
								+ " lives remaining."); //log에 맞음을 알리고, 남은 목숨 기록
					}
				}
			} else { // 총알이 안움직이면
				for (EnemyShip enemyShip : this.enemyShipFormation) //적 함선 대형을 순회
					if (!enemyShip.isDestroyed()
							&& checkCollision(bullet, enemyShip)) { //적 함선이 파괴되지 않았고 적함선과 총알이 부딪히면
						this.score += enemyShip.getPointValue(); //적 함선에 따른 점수 증가
						this.shipsDestroyed++; //파괴된 적 함선 +1
						this.enemyShipFormation.destroy(enemyShip); //적 함선 대형에서 맞은 적 함선 파괴
						recyclable.add(bullet); // 총알 재활용 가능에 추가
					}
				if (this.enemyShipSpecial != null
						&& !this.enemyShipSpecial.isDestroyed()
						&& checkCollision(bullet, this.enemyShipSpecial)) { //스페셜 함선이 존재하고 부숴지지않았고 스페셜함선과 총알이 부딪혔다면
					this.score += this.enemyShipSpecial.getPointValue(); //스페셜 함선에 따른 점수 증가
					this.shipsDestroyed++; // 파괴한 적 함수 개수 증가
					this.enemyShipSpecial.destroy(); //스페셜 적 함선 파괴
					this.enemyShipSpecialExplosionCooldown.reset(); // 스페셜 적 함선 파괴 쿨타임 재시작
					recyclable.add(bullet); //재활용 총알에 추가
				}
			}
		this.bullets.removeAll(recyclable); //재활용 가능 총알에 추가된 것들은 모두 현재 bullet에서 삭제
		BulletPool.recycle(recyclable); //bulletPool에 재활용하려고 삭제한 총알들 추가
	}

	/**
	 * Checks if two entities are colliding.
	 * 
	 * @param a
	 *            First entity, the bullet.
	 * @param b
	 *            Second entity, the ship.
	 * @return Result of the collision test.
	 */
	private boolean checkCollision(final Entity a, final Entity b) { //a(총알)과 b(함선)의 충돌을 체크
		// Calculate center point of the entities in both axis.
		int centerAX = a.getPositionX() + a.getWidth() / 2;
		int centerAY = a.getPositionY() + a.getHeight() / 2;
		int centerBX = b.getPositionX() + b.getWidth() / 2;
		int centerBY = b.getPositionY() + b.getHeight() / 2;
		// Calculate maximum distance without collision.
		int maxDistanceX = a.getWidth() / 2 + b.getWidth() / 2;
		int maxDistanceY = a.getHeight() / 2 + b.getHeight() / 2;
		// Calculates distance.
		int distanceX = Math.abs(centerAX - centerBX);
		int distanceY = Math.abs(centerAY - centerBY);

		return distanceX < maxDistanceX && distanceY < maxDistanceY; // 두 엔티티 사이의 거리가 충돌이 없는 마지막 거리보다 작으면 true로 충돌임을 반환, 아니면 false를 반환
	}

	/**
	 * Returns a GameState object representing the status of the game.
	 * 
	 * @return Current game state.
	 */
	public final GameState getGameState() {
		return new GameState(this.level, this.score, this.lives,
				this.bulletsShot, this.shipsDestroyed);
	}
}