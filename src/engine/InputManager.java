package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Manages keyboard input for the provided screen.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
/** 제공된 화면에 대한 키보드 입력을 관리합니다. */
public final class InputManager implements KeyListener {

	/** Number of recognised keys. */
	/** 인식 가능한 키의 수입니다. */
	private static final int NUM_KEYS = 256;
	/** Array with the keys marked as pressed or not. */
	/** 키가 눌렸는지 여부를 나타내는 배열입니다. */
	private static boolean[] keys;
	/** Singleton instance of the class. */
	/** 클래스의 싱글톤 인스턴스입니다. */
	private static InputManager instance;

	/**
	 * Private constructor.
	 */
	/** private 생성자입니다. */
	private InputManager() {
		keys = new boolean[NUM_KEYS];
	}

	/**
	 * Returns shared instance of InputManager.
	 *
	 * @return Shared instance of InputManager.
	 */
	/** InputManager의 공유 인스턴스를 반환합니다. 인스턴스가 없을 경우, 새로운 인스턴스를 생성 및 반환합니다. */
	protected static InputManager getInstance() {
		if (instance == null)
			instance = new InputManager();
		return instance;
	}

	/**
	 * Returns true if the provided key is currently pressed.
	 *
	 * @param keyCode
	 *            Key number to check.
	 *            확인할 키 번호(정수형)
	 *            사용 예시: 여러 Screen 스크립트에서 inputManager.isKeyDown(KeyEvent.VK_RIGHT)와 같은 형태로 사용됨.
	 * @return Key state.
	 * 			키 상태 반환, keys[keyCode]에 해당하는 bool 값이 True/False인지 알려 줍니다 .
	 */
	/** 제공된 키가 현재 눌려 있는지 여부를 반환합니다. */
	public boolean isKeyDown(final int keyCode) {
		return keys[keyCode];
	}

	/**
	 * Changes the state of the key to pressed.
	 *
	 * @param key
	 *            Key pressed.
	 */
	/** 키 상태(keys[key.getKeyCode()])를 눌림(true)으로 변경합니다. */
	@Override
	public void keyPressed(final KeyEvent key) {
		if (key.getKeyCode() >= 0 && key.getKeyCode() < NUM_KEYS)
			keys[key.getKeyCode()] = true;
	}

	/**
	 * Changes the state of the key to not pressed.
	 *
	 * @param key
	 *            Key released.
	 */
	/** 키 상태(keys[key.getKeyCode()])를 눌리지 않음(false)으로 변경합니다. */
	@Override
	public void keyReleased(final KeyEvent key) {
		if (key.getKeyCode() >= 0 && key.getKeyCode() < NUM_KEYS)
			keys[key.getKeyCode()] = false;
	}

	/**
	 * Does nothing.
	 *
	 * @param key
	 *            Key typed.
	 *            입력된 키.
	 */
	/** 현재로서는 아무 기능도 하지 않습니다. */
	@Override
	public void keyTyped(final KeyEvent key) {

	}
}
