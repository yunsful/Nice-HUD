package screen;

import java.awt.event.KeyEvent;

import engine.Cooldown;
import engine.Core;

/**
 * 타이틀 화면을 구현.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class TitleScreen extends Screen {

	/** 유저의 선택을 변경하는데 거는 쿨타임 시간 */
	private static final int SELECTION_TIME = 200;
	
	/** 유저 선택의 변경에 걸 쿨타임 */
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
	 */
	public TitleScreen(final int width, final int height, final int fps) {
		super(width, height, fps); // 너비, 높이, fps 설정

		// Defaults to play.
		this.returnCode = 2;
		this.selectionCooldown = Core.getCooldown(SELECTION_TIME); //유저 선택 쿨타임 객체 생성
		this.selectionCooldown.reset(); //쿨타임 시작
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
	 * 화면 업데이트 및 이벤트 체크
	 */
	protected final void update() {
		super.update(); //Screen의 update 메소드.

		draw();
		if (this.selectionCooldown.checkFinished()
				&& this.inputDelay.checkFinished()) { //선택쿨타임과 입력쿨타임이 완료됐을 시
			if (inputManager.isKeyDown(KeyEvent.VK_UP) //위 방향키 혹은 w 입력
					|| inputManager.isKeyDown(KeyEvent.VK_W)) {
				previousMenuItem(); //이전 메뉴로 이동
				this.selectionCooldown.reset(); //유저 선택 쿨타임 다시 시작
			}
			if (inputManager.isKeyDown(KeyEvent.VK_DOWN) //아래 방향키 혹은 s입력
					|| inputManager.isKeyDown(KeyEvent.VK_S)) {
				nextMenuItem(); //다음 메뉴로 이동
				this.selectionCooldown.reset(); //유저 선택 쿨타임 시작
			}
			if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) //스페이스바 입력
				this.isRunning = false; //isRunning을 false로 변경
		}
	}

	/**
	 * 다음 메뉴 아이템으로 이동
	 */
	private void nextMenuItem() {
		if (this.returnCode == 3) //맨 마지막 메뉴인 경우 처음인 0으로
			this.returnCode = 0;
		else if (this.returnCode == 0) //처음인 0인경우 다음인 2로
			this.returnCode = 2;
		else //가운데인 경우 1을 더해서 3으로
			this.returnCode++;
	}

	/**
	 * 이전 메뉴로 이동.
	 */
	private void previousMenuItem() {
		if (this.returnCode == 0) //처음인 0인 경우, 마지막인 3으로
			this.returnCode = 3;
		else if (this.returnCode == 2) //중간인 2인 경우, 처음인 0으로
			this.returnCode = 0;
		else //마지막인 3인 경우, 그 위인 2로
			this.returnCode--;
	}

	/**
	 * 화면과 관련된 부분을 그림.
	 */
	private void draw() {
		drawManager.initDrawing(this);

		drawManager.drawTitle(this);
		drawManager.drawMenu(this, this.returnCode);

		drawManager.completeDrawing(this);
	}
}
