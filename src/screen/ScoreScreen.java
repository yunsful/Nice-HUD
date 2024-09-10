package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import engine.Cooldown;
import engine.Core;
import engine.GameState;
import engine.Score;

/**
 * 점수 화면 구현
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
//Screen 상속
public class ScoreScreen extends Screen {

	/** 유저 선택에 부여할 쿨타임 시간 */
	private static final int SELECTION_TIME = 200;
	/** 최대 점수의 최대 개수 */
	private static final int MAX_HIGH_SCORE_NUM = 7;
	/** Code of first mayus character. 신기록시 이용할 이름 입력다이얼의 첫 문자, A*/
	private static final int FIRST_CHAR = 65;
	/** Code of last mayus character. 신기록시 이용할 이름 입력다이얼의 마지막 문자, Z*/
	private static final int LAST_CHAR = 90;

	/** 현재 점수 */
	private int score;
	/** 플레이어의 남은 목숨 */
	private int livesRemaining;
	/** 플레이어가 쏜 총알의 총 개수 */
	private int bulletsShot;
	/** 플레이어가 부신 함선의 총 개수 */
	private int shipsDestroyed;
	/** 과거 최대스코어들의 리스트. */
	private List<Score> highScores;
	/** 현재 점수가 신기록인지에 대한 확인 변수 */
	private boolean isNewRecord;
	/** 기록에 입력할 플레이어의 이름 */
	private char[] name;
	/** 신기록에 유지할 이름 입력을 위한 다이얼에 사용될 문자의 번째 */
	private int nameCharSelected;
	/** 유저 선택의 쿨타임 */
	private Cooldown selectionCooldown;

	/**
	 * Constructor, establishes the properties of the screen.
	 * 
	 * @param width
	 *            Screen width.
	 * @param height
	 *            Screen height.
	 * @param fps
	 *            Frames per second, frame rate at which the game is run.
	 * @param gameState
	 *            Current game state.
	 */
	public ScoreScreen(final int width, final int height, final int fps,
			final GameState gameState) {
		super(width, height, fps); //점수 화면의 너비, 높이, fps 인자로 받아서 초기화

		this.score = gameState.getScore(); //현재 게임상태의 스코어
		this.livesRemaining = gameState.getLivesRemaining(); //현재 게임의 남은 목숨
		this.bulletsShot = gameState.getBulletsShot(); //현재 게임에서 쏜 총알 수
		this.shipsDestroyed = gameState.getShipsDestroyed(); //현재 부신 적 함선 개수
		this.isNewRecord = false; // 초기 신기록인지 체크는 false로 설정
		this.name = "AAA".toCharArray(); //AAA를 ['A', 'A', 'A']로 변환하여 저장
		this.nameCharSelected = 0;
		this.selectionCooldown = Core.getCooldown(SELECTION_TIME); //유저 선택 쿨타임 객체 생성
		this.selectionCooldown.reset();// 선택 쿨타임 적용 시작

		try {
			this.highScores = Core.getFileManager().loadHighScores(); //fileManger객체로 highscore(score 리스트) 불러옴.
			if (highScores.size() < MAX_HIGH_SCORE_NUM
					|| highScores.get(highScores.size() - 1).getScore() // highscores 리스트의 요소 개수가 최대 개수보다 적거나 마지막 스코어가 지금 점수보다 낮으면
					< this.score)
				this.isNewRecord = true; // 신기록임을 인정, true 대입

		} catch (IOException e) { //예외 처리
			logger.warning("Couldn't load high scores!");
		}
	}

	/**
	 * Starts the action.
	 * 
	 * @return Next screen code.
	 */
	public final int run() {
		super.run();

		return this.returnCode;
	}

	/**
	 * Updates the elements on screen and checks for events.
	 */
	protected final void update() {
		super.update(); //업데이트 메소드

		draw();
		if (this.inputDelay.checkFinished()) { //inputDelay 쿨타임이 끝났으면 아래 블록 시행
			if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) { //ESC누르면
				// Return to main menu.
				this.returnCode = 1; //메인메뉴로 이동
				this.isRunning = false;
				if (this.isNewRecord) // 점수가 신기록일시
					saveScore(); //점수 저장
			} else if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) { //스페이스바 입력시
				// Play again.
				this.returnCode = 2; // 재시작
				this.isRunning = false;
				if (this.isNewRecord) // 신기록일시
					saveScore(); //점수 저장
			}

			if (this.isNewRecord && this.selectionCooldown.checkFinished()) { // 신기록이고 선택 쿨다운 완료시
				if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)) { // 오른쪽 방향키 입력시
					this.nameCharSelected = this.nameCharSelected == 2 ? 0
							: this.nameCharSelected + 1; //nameCharSelected가 2이면 0, 2가 아니면 +1을 더한 값을 저장
					this.selectionCooldown.reset(); // 선택 쿨다운 재시작
				}
				if (inputManager.isKeyDown(KeyEvent.VK_LEFT)) { //왼쪽 방향키 누를 시
					this.nameCharSelected = this.nameCharSelected == 0 ? 2
							: this.nameCharSelected - 1; //nameCharSelected가 0이면 2를, 0이 아니면 -1을 더한 값을 저장
					this.selectionCooldown.reset(); // 선택 쿨다운 재시작
				}
				if (inputManager.isKeyDown(KeyEvent.VK_UP)) { // 위 방향키를 입력시
					this.name[this.nameCharSelected] = // name 리스트의 nameCharSelected 번째의 요소에 대입
							(char) (this.name[this.nameCharSelected]
									== LAST_CHAR ? FIRST_CHAR // 해당 요소가 LAST_CHAR이면 FIRST_CHAR로 돌아감, 아니면 +1을 더한 문자를 출력
							: this.name[this.nameCharSelected] + 1);
					this.selectionCooldown.reset(); // 선택 쿨다운 재시작
				}
				if (inputManager.isKeyDown(KeyEvent.VK_DOWN)) { // 아래 방향키 입력시
					this.name[this.nameCharSelected] = // name 리스트의 nameCharSelected 번째의 요소에 대입
							(char) (this.name[this.nameCharSelected]
									== FIRST_CHAR ? LAST_CHAR // 해당 요소가 FIRST_CHAR이면 LAST_CHAR로 돌아감, 아니면 -1을 더한 문자 출력
							: this.name[this.nameCharSelected] - 1);
					this.selectionCooldown.reset(); // 선택 쿨다운 재시작
				}
			}
		}

	}

	/**
	 * Saves the score as a high score.
	 */
	private void saveScore() {
		highScores.add(new Score(new String(this.name), score)); // highsocres 리스트에 새 신기록인 score를 추가.
		Collections.sort(highScores); // 정렬
		if (highScores.size() > MAX_HIGH_SCORE_NUM) // 최대 신기록 개수를 넘어가면
			highScores.remove(highScores.size() - 1); // 제일 마지막의 신기록 한 개를 삭제

		try {
			Core.getFileManager().saveHighScores(highScores); //파일매니저를 통해 신기록 리스트를 저장
		} catch (IOException e) {
			logger.warning("Couldn't load high scores!");
		}
	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);

		drawManager.drawGameOver(this, this.inputDelay.checkFinished(),
				this.isNewRecord);
		drawManager.drawResults(this, this.score, this.livesRemaining,
				this.shipsDestroyed, (float) this.shipsDestroyed
						/ this.bulletsShot, this.isNewRecord);

		if (this.isNewRecord)
			drawManager.drawNameInput(this, this.name, this.nameCharSelected);

		drawManager.completeDrawing(this);
	}
}
