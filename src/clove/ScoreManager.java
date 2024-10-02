package clove;

import java.time.Instant;
import screen.GameScreen;

public class ScoreManager {

    private int levelScore;
    private Instant gameStartTime;
    private static int accumulatedScore = 0;
    private int level;
    private GameScreen gameScreen;

    // Constructor - initializes level and GameScreen
    public ScoreManager(int level, GameScreen gameScreen) {
        this.level = level;
        this.gameScreen = gameScreen;
    }

    // Records the game start time
    public void startGame() {
        if (this.gameStartTime == null)
            this.gameStartTime = Instant.now();
    }

    // Adds score based on enemy score and level
    public void addScore(int enemyScore) {
        int scoreToAdd = enemyScore * this.level;
        this.levelScore += scoreToAdd;
        accumulatedScore += scoreToAdd;
        //System.out.println("Enemy destroyed. Score added: " + scoreToAdd + ", Level Score: " + this.levelScore);
    }

    // Retrieves bulletsShot from GameScreen
    public int getBulletsShot() {
        return gameScreen.getBulletsShot(); // GameScreen에서 bulletsShot 값을 가져옴
    }

    public int getAccumulatedScore() {
        return accumulatedScore;
    }

    public int getLevel() {
        return level;
    }
}
