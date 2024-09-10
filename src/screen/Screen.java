package screen;

import java.awt.Insets;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager;
import engine.InputManager;

/**
 * 기본 화면을 구현
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Screen {
	
	/** 화면이 유저의 입력을 받아들이기 까지의 시간 */
	private static final int INPUT_DELAY = 1000;

	/** Draw Manager instance. */
	protected DrawManager drawManager;
	/** Input Manager instance. */
	protected InputManager inputManager;
	/** Application logger. */
	protected Logger logger;

	/** 화면 너비 */
	protected int width;
	/** 화면 높이 */
	protected int height;
	/** 화면의 fps */
	protected int fps;
	/** Screen insets. */
	protected Insets insets;
	/** 화면이 유저 입력을 받아들이는 쿨타임. */
	protected Cooldown inputDelay;

	/** screen이 실행중인가 */
	protected boolean isRunning;
	/** 어떤 화면이 다음으로 갈 화면인지에 대한 변수 */
	protected int returnCode;

	/**
	 * Constructor, establishes the properties of the screen.
	 * 
	 * @param width
	 *            화면 너비
	 * @param height
	 *            화면 높이
	 * @param fps
	 *            게임이 실행되는 화면의 fps
	 */
	public Screen(final int width, final int height, final int fps) {
		this.width = width;
		this.height = height;
		this.fps = fps; //인자로 받은 너비, 높이, fps로 각각 너비, 높이, fps 초기화

		this.drawManager = Core.getDrawManager(); //drawManager 객체 생성
		this.inputManager = Core.getInputManager(); //inputManager 객체 생성
		this.logger = Core.getLogger(); //logger 객체 생성
		this.inputDelay = Core.getCooldown(INPUT_DELAY); //입력 쿨타임 객체 생성
		this.inputDelay.reset(); // 입력 쿨타임 시작
		this.returnCode = 0; //초기 화면 returnCode 0으로 설정
	}

	/**
	 * Initializes basic screen properties.
	 */
	public void initialize() {

	}

	/**
	 * 화면을 가동
	 * 
	 * @return 다음 화면 코드를 반환
	 */
	public int run() {
		this.isRunning = true; //화면이 가동되고 있는지를 true

		while (this.isRunning) { //화면이 가동되고 있으면 아래를 반복
			long time = System.currentTimeMillis(); //현재 시간 저장

			update(); //화면 업데이트

			//프레임 유지를 위한 화면 갱신에 대기시간 부여
			time = (1000 / this.fps) - (System.currentTimeMillis() - time); // 한 프레임에 걸릴 시간 - 업데이트 작업에 걸린 시간
			if (time > 0) { //대기시간이 0보다 클 경우,
				try {
					TimeUnit.MILLISECONDS.sleep(time); //계산된 대기 시간 만큼 대기.
				} catch (InterruptedException e) { //예외처리
					return 0;
				}
			}
		}

		return 0;
	}

	/**
	 * 화면 요소 업데이트 및 이벤트 체크
	 */
	protected void update() {
	}

	/**
	 * 화면 너비 getter
	 * 
	 * @return Screen width.
	 */
	public final int getWidth() {
		return this.width;
	}

	/**
	 * 화면 높이 getter
	 * 
	 * @return Screen height.
	 */
	public final int getHeight() {
		return this.height;
	}
}