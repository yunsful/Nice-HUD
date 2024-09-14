package engine;

import java.awt.Insets;

import javax.swing.JFrame;

import screen.Screen;

/**
 * Implements a frame to show screens on.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
@SuppressWarnings("serial")
public class Frame extends JFrame {

	/** Frame width. */
	private int width;
	/** Frame height. */
	private int height;
	/** Screen currently shown. */
	private Screen currentScreen;

	/**
	 * Initializes the new frame.
	 * 
	 * @param width
	 *            Frame width.
	 * @param height
	 *            Frame height.
	 */
	public Frame(final int width, final int height) {
		setSize(width, height); // 창 크기 설정
		setResizable(false); // 창 크기 조절이 가능한지 여부
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 유저가 '닫기'를 시작할때 수행되는 작업

		setLocationRelativeTo(null); // 창 위치 설정 (null : 화면 가운데)
		setVisible(true); // 창을 최소화 할 수 있는지 여부

		Insets insets = getInsets(); // 창의 여백
		this.width = width - insets.left - insets.right;
		this.height = height - insets.top + insets.bottom; // 상하좌우 여백 크기를 창에서 더하고 빼 실제 컨텐츠가 표시되는 영역 설정
		setTitle("Invaders"); // 창의 제목

		addKeyListener(Core.getInputManager()); // 키보드 입력을 현재 객체의 키 리스너로 등록
	}

	/**
	 * Sets current screen.
	 * 
	 * @param screen
	 *            Screen to show.
	 * @return Return code of the finished screen.
	 */
	// 현재 화면을 파라미터로 받은 스크린으로 변경하고 초기화 한뒤 가동시킨다
	public final int setScreen(final Screen screen) {
		currentScreen = screen;
		currentScreen.initialize();
		return currentScreen.run();
	}

	/**
	 * Getter for frame width.
	 * 
	 * @return Frame width.
	 */
	public final int getWidth() {
		return this.width;
	} // 프레임 너비 getter

	/**
	 * Getter for frame height.
	 * 
	 * @return Frame height.
	 */

	public final int getHeight() {
		return this.height;
	} // 프레임 높이 getter
}
