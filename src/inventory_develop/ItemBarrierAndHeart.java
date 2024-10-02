package inventory_develop;
import screen.GameScreen;
import entity.Ship;
import Enemy.PlayerGrowth;

public class ItemBarrierAndHeart {

    private static final int barrier_DURATION = 3000;
    private static final int MAX_LIVES = 3;

    private boolean barrierActive;
    private boolean heartActive;
    private long barrierActivationTime;

    public ItemBarrierAndHeart() {
        this.barrierActive = false;
        this.heartActive = false;
    }

    //barrier
    public void updatebarrier() {
        if (this.barrierActive) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - this.barrierActivationTime >= barrier_DURATION) {
                deactivatebarrier();
            }
        }
    }

    public boolean isbarrierActive() {
        return barrierActive;
    }

    public void activatebarrier() {
        this.barrierActive = true;
        this.barrierActivationTime = System.currentTimeMillis();
    }

    public void deactivatebarrier() {
        this.barrierActive = false;
    }

    //heart
    public void Operateheart(GameScreen gameScreen, Ship ship, PlayerGrowth growth) {
//        if (growth.getHealth() < MAX_LIVES) {
//            ship.increaseHealth(1);
//            System.out.println("aa");
//        }
        if (gameScreen.getLives() < MAX_LIVES) {
            gameScreen.setLives(gameScreen.getLives() + 1);
        }
    }

    public void activeheart() {
        this.heartActive = true;
    }

    public boolean isheartActive() {
        return heartActive;
    }

    public void deactivateheart() {
        this.heartActive = false;
    }
}
