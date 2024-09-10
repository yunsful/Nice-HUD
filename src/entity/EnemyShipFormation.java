package entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import screen.Screen;
import engine.Cooldown;
import engine.Core;
import engine.DrawManager;
import engine.DrawManager.SpriteType;
import engine.GameSettings;

/**
 * Groups enemy ships into a formation that moves together.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
/**
 * 적군 우주선들이 함께 이동하는 집단을 관리하는 역할
 * ** width, height -> px 단위
 * ** wide, high -> 개 단위 (함선 수)
 */
public class EnemyShipFormation implements Iterable<EnemyShip> {

	/** Initial position in the x-axis. 우주선 집단의 초기 x 좌표 */
	private static final int INIT_POS_X = 20;
	/** Initial position in the y-axis. 우주선 집단의 초기 y 좌표 */
	private static final int INIT_POS_Y = 100;
	/** Distance between ships. 우주선 사이의 간격 */
	private static final int SEPARATION_DISTANCE = 40;
	/** Proportion of C-type ships. C 타입 우주선의 비율 */
	private static final double PROPORTION_C = 0.2;
	/** Proportion of B-type ships. B 타입 우주선의 비율 */
	private static final double PROPORTION_B = 0.4;
	/** Lateral speed of the formation. 우주선 집단의 X 축 이동 속도 */
	private static final int X_SPEED = 8;
	/** Downwards speed of the formation. 우주선 집단의 Y 축 이동 속도 */
	private static final int Y_SPEED = 4;
	/** Speed of the bullets shot by the members. 우주선이 발사하는 총알의 속도 */
	private static final int BULLET_SPEED = 4;
	/** Proportion of differences between shooting times. 총알 발사 시간의 변동성 */
	private static final double SHOOTING_VARIANCE = .2;
	/** Margin on the sides of the screen. 우주선 집단이 넘어가지 않는 양쪽 여백 */
	private static final int SIDE_MARGIN = 20;
	/** Margin on the bottom of the screen. 우주선 집단이 넘어가지 않는 위아래 여백 */
	private static final int BOTTOM_MARGIN = 80;
	/** Distance to go down each pass. 각 패스마다 내려가는 거리*/
	private static final int DESCENT_DISTANCE = 20;
	/** Minimum speed allowed. 집단의 최소 이동 속도 */
	private static final int MINIMUM_SPEED = 10;

	/** DrawManager instance. */
	private DrawManager drawManager;
	/** Application logger. */
	private Logger logger;
	/** Screen to draw ships on. */
	private Screen screen;

	/** List of enemy ships forming the formation. 적 함선으로 된 2차원 배열 */
	private List<List<EnemyShip>> enemyShips;
	/** Minimum time between shots. */
	private Cooldown shootingCooldown;
	/** Number of ships in the formation - horizontally. 집단의 행의 크기 */
	private int nShipsWide;
	/** Number of ships in the formation - vertically. 집단의 열의 크기*/
	private int nShipsHigh;
	/** Time between shots. 총알을 발사하는 시간 간격 */
	private int shootingInterval;
	/** Variance in the time between shots. 발사 간격의 변동성 */
	private int shootingVariance;
	/** Initial ship speed. 집단의 기본 이동 속도 */
	private int baseSpeed;
	/** Speed of the ships. 현재 집단의 이동 속도. 기본 이동속도에서 다른 요인에 따라 조절됨 */
	private int movementSpeed;
	/** Current direction the formation is moving on. 집단의 이동 방향 */
	private Direction currentDirection;
	/** Direction the formation was moving previously. 집단의 이전에 이동하던 방향 */
	private Direction previousDirection;
	/** Interval between movements, in frames.  */
	/** 집단의 이동 타이밍을 조절하는 속성
	 * 이 값은 0 에서 시작해 각 프레임마다 증가하며 'movementSpeed' 와 비교
	 * 같아지면 집단이 이동. 이후 다시 0으로 초기화
	 */
	private int movementInterval;
	/** Total width of the formation. 집단이 차지하는 가로 공간의 크기 */
	private int width;
	/** Total height of the formation. 집단이 차지하는 세로 공간의 크기 */
	private int height;
	/** Position in the x-axis of the upper left corner of the formation. 집단의 좌 상단 x 좌표 */
	private int positionX;
	/** Position in the y-axis of the upper left corner of the formation. 집단의 좌 상단 y 좌표 */
	private int positionY;
	/** Width of one ship. 우주선 하나의 너비 */
	private int shipWidth;
	/** Height of one ship. 우주선 하나의 높이 */
	private int shipHeight;
	/** List of ships that are able to shoot. 총알을 발사할 수 있는 우주선들의 집합 */
	private List<EnemyShip> shooters;
	/** Number of not destroyed ships. 남아있는 우주선의 수 */
	private int shipCount;

	/** Directions the formation can move. 집단의 이동 방향의 종류 */
	private enum Direction {
		/** Movement to the right side of the screen. 오른쪽 이동 방향 */
		RIGHT,
		/** Movement to the left side of the screen. 왼쪽 이동 방향 */
		LEFT,
		/** Movement to the bottom of the screen. 아래쪽 이동 방향 */
		DOWN
	};

	/**
	 * Constructor, sets the initial conditions.
	 * 
	 * @param gameSettings
	 *            Current game settings.
	 */
	/**
	 *  gameSettings 으로부터 설정 값을 불러와 집단 초기화
	 * @param gameSettings
	 */
	public EnemyShipFormation(final GameSettings gameSettings) {
		// drawManager 와 logger 를 Core 의 것으로 초기화
		this.drawManager = Core.getDrawManager();
		this.logger = Core.getLogger();

		// 적 함선의 2차원 배열 초기화
		this.enemyShips = new ArrayList<List<EnemyShip>>();

		// 초기 이동 방향을 오른쪽으로 초기화
		this.currentDirection = Direction.RIGHT;

		// 0 으로 초기화
		this.movementInterval = 0;

		// 집단의 크기(함선수)를 설정대로 초기화
		this.nShipsWide = gameSettings.getFormationWidth();
		this.nShipsHigh = gameSettings.getFormationHeight();

		// 발사 간격과 변동을 설정값으로 초기화
		this.shootingInterval = gameSettings.getShootingFrecuency();
		this.shootingVariance = (int) (gameSettings.getShootingFrecuency()
				* SHOOTING_VARIANCE);

		// 집단의 기본 속도를 설정대로 초기화
		this.baseSpeed = gameSettings.getBaseSpeed();

		// 현재 이동속도를 기본 속도로 초기화
		this.movementSpeed = this.baseSpeed;

		// 집단의 위치를 초기화
		this.positionX = INIT_POS_X;
		this.positionY = INIT_POS_Y;

		// 총을 발사할 수 있는 적 함선 리스트 초기화
		this.shooters = new ArrayList<EnemyShip>();

		SpriteType spriteType;

		// 몇 x 몇 함선을 어느 위치 (x, y) 에 생성하였는지 메시지
		this.logger.info("Initializing " + nShipsWide + "x" + nShipsHigh
				+ " ship formation in (" + positionX + "," + positionY + ")");

		// Each sub-list is a column on the formation.
		// 적 함선의 2차원 배열에 값 초기화
		// enemyShips 의 값은 집단의 한 열
		for (int i = 0; i < this.nShipsWide; i++)
			this.enemyShips.add(new ArrayList<EnemyShip>());

		// enemyShips 에 실제로 적 함선을 넣는 반복문
		// 한 열씩 꺼내어 column 변수에 저장
		for (List<EnemyShip> column : this.enemyShips) {
			// 열이므로 집단의 높이만큼 반복
			for (int i = 0; i < this.nShipsHigh; i++) {
				// 열의 높이 중 현재 인덱스의 비율을 계산해서 어느 타입의 함선으로 지정할 것인지 정함 (나누기 연산을 하기 위해 float 으로 casting)
				// 예를 들어, nShipsHigh: 4 이고, i: 0인 경우 -> 0.0이므로 PROPORTION_C(0.2) 보다 작기 때문에 EnemyShipC1 으로 spriteType 초기화
				if (i / (float) this.nShipsHigh < PROPORTION_C)
					spriteType = SpriteType.EnemyShipC1;
				else if (i / (float) this.nShipsHigh < PROPORTION_B
						+ PROPORTION_C)
					spriteType = SpriteType.EnemyShipB1;
				else
					spriteType = SpriteType.EnemyShipA1;

				// 내부 for 문이 돌 땐 위에서 아래로 간격이 추가됨
				// 겉 for 문이 돌 땐 왼쪽에서 오른쪽으로 간격이 추가됨
				column.add(new EnemyShip((SEPARATION_DISTANCE 
						* this.enemyShips.indexOf(column))
								+ positionX, (SEPARATION_DISTANCE * i)
								+ positionY, spriteType));
				// 함선의 갯수 추가
				this.shipCount++;
			}
		}

		// 첫 함선의 너비와 높이로 집단의 기본 너비와 높이 설정
		this.shipWidth = this.enemyShips.get(0).get(0).getWidth();
		this.shipHeight = this.enemyShips.get(0).get(0).getHeight();

		// 집단의 전체 너비(높이) = 간격 크기 * 간격 갯수 + 한 함선의 너비(높이)
		// 개체의 왼쪽 상단 모서리가 기준이므로 간격을 n-1번 곱한 값에 한 개체의 크기만큼을 더해야 집단의 너비(높이)가 나옴
		this.width = (this.nShipsWide - 1) * SEPARATION_DISTANCE
				+ this.shipWidth;
		this.height = (this.nShipsHigh - 1) * SEPARATION_DISTANCE
				+ this.shipHeight;

		// 각 열에서 가장 마지막에 있는 함선(맨 밑에 있는 함선)들을 shooters 리스트에 추가
		for (List<EnemyShip> column : this.enemyShips)
			this.shooters.add(column.get(column.size() - 1));
	}

	/**
	 * Associates the formation to a given screen.
	 * 
	 * @param newScreen
	 *            Screen to attach.
	 */
	public final void attach(final Screen newScreen) {
		screen = newScreen;
	}

	/**
	 * Draws every individual component of the formation.
	 */
	// 함선 하나씩 그리기
	public final void draw() {
		for (List<EnemyShip> column : this.enemyShips)
			for (EnemyShip enemyShip : column)
				drawManager.drawEntity(enemyShip, enemyShip.getPositionX(),
						enemyShip.getPositionY());
	}

	/**
	 * Updates the position of the ships.
	 */
	public final void update() {
		// 최초로 update 를 할 땐 shootingCooldown 이 null 이므로 해당 조건문 실행
		// shootingInterval 과 shootingVariance 를 갖는 Cooldown 인스턴스 생성
		if(this.shootingCooldown == null) {
			this.shootingCooldown = Core.getVariableCooldown(shootingInterval,
					shootingVariance);
			this.shootingCooldown.reset();
		}
		
		cleanUp();

		int movementX = 0;
		int movementY = 0;

		// 남아있는 함선의 비율
		double remainingProportion = (double) this.shipCount
				/ (this.nShipsHigh * this.nShipsWide);

		// 매번 업데이트마다 남아있는 함선의 비율에 따라 속도 조정 (속도는 작을 수록 빠름)
		this.movementSpeed = (int) (Math.pow(remainingProportion, 2)
				* this.baseSpeed);
		this.movementSpeed += MINIMUM_SPEED;

		// 0부터 시작해서 movementSpeed 에 도달할 때까지 1씩 증가
		movementInterval++;

		// movementSpeed 에 도달한 경우, 실제로 이동
		if (movementInterval >= this.movementSpeed) {
			// movementInterval 을 다시 0으로 초기화
			movementInterval = 0;

			// 집단이 더 내려갈 곳이 없는지
			boolean isAtBottom = positionY + this.height > screen.getHeight() - BOTTOM_MARGIN;

			// 집단이 오른쪽으로 갈 곳이 없는지
			boolean isAtRightSide = positionX + this.width >= screen.getWidth() - SIDE_MARGIN;

			// 집단이 왼쪽으로 갈 공간이 없는지
			boolean isAtLeftSide = positionX <= SIDE_MARGIN;

			// 위치가 함선 간격의 배수인지 확인
			// 아래로 내려갈 때 내려가는 이동이 끝났는지 확인하기 위한 변수
			boolean isAtHorizontalAltitude = positionY % DESCENT_DISTANCE == 0;

			// 이동 중에 벽을 만난 경우들 대처
			// 내려가는 중인 경우
			if (currentDirection == Direction.DOWN) {
				// 내려가는 이동이 끝난 경우
				if (isAtHorizontalAltitude)
					// 이전 이동이 오른쪽이었다면 왼쪽으로 이동 시작. Vice Versa
					if (previousDirection == Direction.RIGHT) {
						currentDirection = Direction.LEFT;
						this.logger.info("Formation now moving left 1");
					} else {
						currentDirection = Direction.RIGHT;
						this.logger.info("Formation now moving right 2");
					}
			// 왼쪽으로 이동 중인 경우
			} else if (currentDirection == Direction.LEFT) {
				if (isAtLeftSide)
					// 왼쪽 끝에 도달했고 더 내려갈 수 있다면
					if (!isAtBottom) {
						// 이전 이동 방향을 왼쪽으로 설정 (왼쪽으로 가다 벽을 만나 내려가는 거다)
						previousDirection = currentDirection;
						// 이제부터 내려가기 시작
						currentDirection = Direction.DOWN;
						this.logger.info("Formation now moving down 3");
					} else {
						// 왼쪽 끝에 도달했는데 더 내려갈 수 없는 경우
						// 오른쪽으로 이동 시작
						currentDirection = Direction.RIGHT;
						this.logger.info("Formation now moving right 4");
					}
			// 오른쪽으로 이동 중인 경우
			} else {
				if (isAtRightSide)
					// 오른쪽 끝에 도달했고 더 내려갈 수 있다면
					if (!isAtBottom) {
						// 이전 이동 방향을 오른쪽으로 설정 (오른쪽으로 가다 벽을 만나 내려가는 거다)
						previousDirection = currentDirection;
						// 이제부터 내려가기 시작
						currentDirection = Direction.DOWN;
						this.logger.info("Formation now moving down 5");
					} else {
						// 오른쪽 끝에 도달했는데 더 내려갈 수 없는 경우
						// 왼쪽으로 이동 시작
						currentDirection = Direction.LEFT;
						this.logger.info("Formation now moving left 6");
					}
			}

			// 일반적으로 이동중인 경우
			// movementX 와 movementY 는 매 업데이트마다 기본적으로 0으로 초기화 됨
			// 이후에 movementX, movementY 값만큼 함선들이 이동함

			// 오른쪽으로 이동할 경우 movementX 값을 X_SPEED 만큼 설정
			if (currentDirection == Direction.RIGHT)
				movementX = X_SPEED;
			// 왼쪽으로 이동할 경우 movementX 값을 -X_SPEED 로 설정
			// 왼쪽 이동이므로 현재 위치에서 X 좌표 값이 작아지게 해야함
			else if (currentDirection == Direction.LEFT)
				movementX = -X_SPEED;
			// 아래로 내려가는 경우
			// Y축 이동값 만큼 아래로 내려감
			else
				movementY = Y_SPEED;

			// 집단의 위치 이동
			positionX += movementX;
			positionY += movementY;

			// Cleans explosions.
			// 업데이트마다 파괴된 함선 치우기

			// 파괴된 함선을 담을 리스트 변수
			List<EnemyShip> destroyed;

			// enemyShips 한 열씩 꺼내어 파괴된 함선 제거
			for (List<EnemyShip> column : this.enemyShips) {
				// 파괴된 함선 담을 리스트 생성
				destroyed = new ArrayList<EnemyShip>();
				for (EnemyShip ship : column) {
					// 꺼낸 함선이 파괴되었다면 그 함선을 파괴된 함선을 담는 리스트에 추가
					if (ship != null && ship.isDestroyed()) {
						destroyed.add(ship);
						this.logger.info("Removed enemy "
								+ column.indexOf(ship) + " from column "
								+ this.enemyShips.indexOf(column));
					}
				}
				// 한 열에서 파괴된 함선 제거
				column.removeAll(destroyed);
			}

			// 집단에 있는 모든 함선을 이동시키고 업데이트
			for (List<EnemyShip> column : this.enemyShips)
				for (EnemyShip enemyShip : column) {
					enemyShip.move(movementX, movementY);
					enemyShip.update();
				}
		}
	}

	/**
	 * Cleans empty columns, adjusts the width and height of the formation.
	 */
	// 집단에서 빈 열을 제거
	// 집단의 너비와 높이 재조정
	private void cleanUp() {
		// 빈 열의 인덱스 모으기
		Set<Integer> emptyColumns = new HashSet<Integer>();
		int maxColumn = 0;
		int minPositionY = Integer.MAX_VALUE;

		// enemyShips 에서 한 열씩 꺼내어 비어있는지 확인
		for (List<EnemyShip> column : this.enemyShips) {
			if (!column.isEmpty()) {
				// Height of this column
				// 비어있지 않은 경우, 열의 길이를 구하고 그중 가장 큰 값을 maxColumn 에 최신화
				int columnSize = column.get(column.size() - 1).positionY
						- this.positionY + this.shipHeight;
				maxColumn = Math.max(maxColumn, columnSize);
				// 집단에서 가장 위쪽의 좌표 구하기
				minPositionY = Math.min(minPositionY, column.get(0)
						.getPositionY());
			} else {
				// Empty column, we remove it.
				// 빈 열인 경우, 이 열의 인덱스를 emptyColumns 에 추가
				emptyColumns.add(this.enemyShips.indexOf(column));
			}
		}

		// 빈 열의 인덱스를 받아 실제로 enemyShips 에서 제거
		for (int index : emptyColumns) {
			this.enemyShips.remove(index);
			logger.info("Removed column " + index);
		}

		int leftMostPoint = 0;
		int rightMostPoint = 0;
		
		for (List<EnemyShip> column : this.enemyShips) {
			if (!column.isEmpty()) {
				// 최초로 비어있지 않은 열의 x 좌표가 왼쪽 끝점
				if (leftMostPoint == 0)
					leftMostPoint = column.get(0).getPositionX();
				// 가장 마지막에 비어있지 않은 열의 x 좌표가 오른쪽 끝점
				rightMostPoint = column.get(0).getPositionX();
			}
		}

		// 집단의 너비는 왼쪽 끝 점과 오른쪽 끝 점의 차이 + 한 함선의 너비
		this.width = rightMostPoint - leftMostPoint + this.shipWidth;
		// 집단의 높이는 열들 중 가장 긴 열의 높이
		this.height = maxColumn;

		// 집단의 x 좌표를 왼쪽 끝점으로 설정
		this.positionX = leftMostPoint;

		// 집단의 y 좌표를 가장 위쪽 y 좌표로 설정
		this.positionY = minPositionY;
	}

	/**
	 * Shoots a bullet downwards.
	 * 
	 * @param bullets
	 *            Bullets set to add the bullet being shot.
	 */
	// 집단에서 총알 발사
	public final void shoot(final Set<Bullet> bullets) {
		// For now, only ships in the bottom row are able to shoot.
		// 슈터들 중 랜덤으로 하나 선택
		int index = (int) (Math.random() * this.shooters.size());
		// 선택된 인덱스의 함선이 슈터가 됨
		EnemyShip shooter = this.shooters.get(index);

		// 쿨다운이 완료되었다면 총알 발사
		if (this.shootingCooldown.checkFinished()) {
			// 쿨다운 초기화
			this.shootingCooldown.reset();
			// 총알 변수를 생성하여 bullets 리스트에 추가
			// 총알 변수의 x 좌표는 슈터의 가운데가 됨 (x좌표 + 함선 크기 절반, y좌표)
			bullets.add(BulletPool.getBullet(shooter.getPositionX()
					+ shooter.width / 2, shooter.getPositionY(), BULLET_SPEED));
		}
	}

	/**
	 * Destroys a ship.
	 * 
	 * @param destroyedShip
	 *            Ship to be destroyed.
	 */
	// 함선 하나를 입력받아 그 함선을 집단에서 파괴된 상태로 변경
	public final void destroy(final EnemyShip destroyedShip) {
		// 입력 받은 함선을 enemyShips 에서 찾아 파괴 처리
		for (List<EnemyShip> column : this.enemyShips)
			for (int i = 0; i < column.size(); i++)
				if (column.get(i).equals(destroyedShip)) {
					// 파괴 처리
					column.get(i).destroy();
					// 파괴 정보 로그 남기기
					this.logger.info("Destroyed ship in ("
							+ this.enemyShips.indexOf(column) + "," + i + ")");
				}

		// Updates the list of ships that can shoot the player.
		// 파괴된 함선이 shooters 에 포함되어있던 경우, 새로운 슈터를 지정해줘야 함
		if (this.shooters.contains(destroyedShip)) {
			// shooters 에서 파괴된 함선의 인덱스 구하기
			int destroyedShipIndex = this.shooters.indexOf(destroyedShip);
			// 파괴된 함선이 있는 열의 인덱스를 담을 변수
			int destroyedShipColumnIndex = -1;

			for (List<EnemyShip> column : this.enemyShips)
				// 파괴된 함선이 포함된 열인 경우
				if (column.contains(destroyedShip)) {
					// 파괴된 함선이 있는 열의 인덱스
					destroyedShipColumnIndex = this.enemyShips.indexOf(column);
					break;
				}

			// 파괴된 함선이 있는 열에서 nextShooter 를 찾아 nextShooter 로 지정
			EnemyShip nextShooter = getNextShooter(this.enemyShips
					.get(destroyedShipColumnIndex));

			// nextShooter 가 있는 경우
			if (nextShooter != null)
				// shooters 에서 파괴된 함선이 있던 자리에 nextShooter 넣기
				this.shooters.set(destroyedShipIndex, nextShooter);
			else {
				// nextShooter 가 없는 경우
				// 파괴된 함선이 있던 shooters 자리 삭제
				this.shooters.remove(destroyedShipIndex);
				this.logger.info("Shooters list reduced to "
						+ this.shooters.size() + " members.");
			}
		}

		// 한 함선이 파괴되었으므로 집단의 총 함선 수 감소
		this.shipCount--;
	}

	/**
	 * Gets the ship on a given column that will be in charge of shooting.
	 * 
	 * @param column
	 *            Column to search.
	 * @return New shooter ship.
	 */
	// 하나의 열을 입력받아 그 열의 다음 슈터 구하기
	public final EnemyShip getNextShooter(final List<EnemyShip> column) {
		// 하나의 열을 반복 가능한 변수로 생성
		Iterator<EnemyShip> iterator = column.iterator();

		// nextShooter 를 찾지 못한 경우 기본값으로 null 지정
		EnemyShip nextShooter = null;
		while (iterator.hasNext()) {
			EnemyShip checkShip = iterator.next();
			// 파괴되지 않은 함선이 있다면 그 함선이 nextShooter 가 됨
			// while 문이므로 가장 마지막에 조건을 만족하는 함선이 최종적인 nextShooter
			if (checkShip != null && !checkShip.isDestroyed())
				nextShooter = checkShip;
		}

		// nextShooter 를 찾지 못한 경우: null 반환
		// nextShooter 를 찾은 경우: 그 함선 반환
		return nextShooter;
	}

	/**
	 * Returns an iterator over the ships in the formation.
	 * 
	 * @return Iterator over the enemy ships.
	 */
	// 집단의 반복자 생성
	@Override
	public final Iterator<EnemyShip> iterator() {
		Set<EnemyShip> enemyShipsList = new HashSet<EnemyShip>();

		for (List<EnemyShip> column : this.enemyShips)
			for (EnemyShip enemyShip : column)
				enemyShipsList.add(enemyShip);

		return enemyShipsList.iterator();
	}

	/**
	 * Checks if there are any ships remaining.
	 * 
	 * @return True when all ships have been destroyed.
	 */
	// 집단이 비어있는지 여부 반환
	public final boolean isEmpty() {
		return this.shipCount <= 0;
	}
}
