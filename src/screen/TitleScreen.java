package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;

import engine.Cooldown;
import engine.Core;

/**
 * Implements the title screen.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class TitleScreen extends Screen {

	/** Milliseconds between changes in user selection. */
	private static final int SELECTION_TIME = 200;

	/** Time between changes in user selection. */
	private Cooldown selectionCooldown;

	private int currentCoin = 500;

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
		super(width, height, fps);

		// Defaults to play.
		this.returnCode = 2;
		this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
		this.selectionCooldown.reset();

		/**
		 * require function that save and get players coin
		 */

//		try {
//			this.currentCoin = Core.getFileManager().loadCurrentCoin();
//		} catch (NumberFormatException | IOException e) {
//			logger.warning("Couldn't load current coin!");
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
		if (this.selectionCooldown.checkFinished()
				&& this.inputDelay.checkFinished()) {
			if (inputManager.isKeyDown(KeyEvent.VK_UP)
					|| inputManager.isKeyDown(KeyEvent.VK_W)) {
				previousMenuItem();
				this.selectionCooldown.reset();
			}
			if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
					|| inputManager.isKeyDown(KeyEvent.VK_S)) {
				nextMenuItem();
				this.selectionCooldown.reset();
			}
			if (inputManager.isKeyDown(KeyEvent.VK_SPACE))
				if(returnCode == 6) {
					testCoinDiscounter();
					this.selectionCooldown.reset();
				}
				else this.isRunning = false;
		}
	}

	/**
	 * runs when player do buying things
	 * when store system is ready -- unwrap annotated code and rename this method
	 */
	private void testCoinDiscounter(){
		if(this.currentCoin > 0){
			this.currentCoin -= 50;
		}

//		try{
//			Core.getFileManager().saveCurrentCoin();
//		} catch (IOException e) {
//			logger.warning("Couldn't save current coin!");
//		}
	}

	/**
	 * Shifts the focus to the next menu item.
	 */
	private void nextMenuItem() {
		if (this.returnCode == 6)
			this.returnCode = 0; // from 'merchant' to 'Exit' (starter)
		else if (this.returnCode == 0)
			this.returnCode = 2; // from 'Exit' to 'Play' (starter)
		else
			this.returnCode++; // go next (starter)
	}

	/**
	 * Shifts the focus to the previous menu item.
	 */
	private void previousMenuItem() {
		if (this.returnCode == 0)
			this.returnCode = 6; // from 'Exit' to 'merchant' (starter)
		else if (this.returnCode == 2)
			this.returnCode = 0; // from 'Play' to 'Exit' (starter)
		else
			this.returnCode--; // go previous (starter)
	}


	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {

		int coin = this.currentCoin;
		drawManager.initDrawing(this);

		drawManager.drawTitle(this);
		drawManager.drawMenu(this, this.returnCode);
		drawManager.drawCurrentCoin(this,coin);

		drawManager.completeDrawing(this);
	}


}
