package screen;

import java.awt.event.KeyEvent;

/**
 * Implements the high scores screen, it shows player records.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class ReceiptScreen extends Screen {

	private final int score;
	private final int currency;
	private final boolean hitrateBonus;
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
	public ReceiptScreen(final int width, final int height, final int fps, final int score, final int currency, final boolean hitrateBonus) {
		super(width, height, fps);

		this.score = score;
		this.currency = currency;
		this.hitrateBonus = hitrateBonus;

		this.returnCode = 2;
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
		super.update();

		draw();
		if (inputManager.isKeyDown(KeyEvent.VK_SPACE)
				&& this.inputDelay.checkFinished())
			this.isRunning = false;
	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);

		drawManager.drawReceipt(this, this.score, this.currency, this.hitrateBonus);

		drawManager.completeDrawing(this);
	}
}
