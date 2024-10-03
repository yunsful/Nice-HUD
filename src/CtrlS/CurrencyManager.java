package CtrlS;

import engine.Core;
import engine.FileManager;
import engine.GameState;

import java.io.IOException;
import java.util.logging.Logger;



public final class CurrencyManager {

    /** Singleton instance of the class. */
    private static CurrencyManager instance;
    /** Application logger. */
    private static Logger logger;
    private static FileManager fileManager;

    /**
     * private constructor.
     */
    private CurrencyManager() {
        fileManager = Core.getFileManager();
        logger = Core.getLogger();
    }

    /**
     * Returns shared instance of FileManager.
     *
     * @return Shared instance of FileManager.
     */
    public static CurrencyManager getInstance() {
        if (instance == null)
             instance = new CurrencyManager();
        return instance;
    }

    /**
     * Add an amount of money to the current currency.
     */
    public void addCurrency(int amount) throws IOException {
        int current_currency = fileManager.loadCurrency();
        amount += current_currency;
        fileManager.saveCurrency(amount);
    }

    /**
     * Consume as much money as the amount you have (cannot spend more than you currently have).
     */
    public boolean spendCurrency(int amount) throws IOException {
        int current_currency = fileManager.loadCurrency();
        if (amount <= current_currency) {
            current_currency -= amount;
            fileManager.saveCurrency(current_currency);
            return true;
        }
        else {
            return false;
        }
    }
    // Team-Ctrl-S(Currency)
    public int calculateCurrency(GameState prevState, GameState currState) {
        // Score gained this round
        int scoreDiff = currState.getScore() - prevState.getScore();

        // hitRate of this round
        int bulletsShotDiff = currState.getBulletsShot() - prevState.getBulletsShot();
        int shipsDestroyedDiff = currState.getShipsDestroyed() - prevState.getShipsDestroyed();
        float hitRate = bulletsShotDiff / (float) shipsDestroyedDiff;

        int baseCurrency = scoreDiff / 10;
        int levelBonus = baseCurrency * currState.getLevel();
        int currency = baseCurrency + levelBonus;

        if (hitRate > 0.9) {
            currency += (int) (currency * 0.3); // 30% 보너스 지급
            Core.getLogger().info("hitRate bonus occurs (30%).");
        } else if (hitRate > 0.8) {
            currency += (int) (currency * 0.2); // 20% 보너스 지급
            Core.getLogger().info("hitRate bonus occurs (20%).");
        }

        // Round clear time in seconds
        // DEBUGGING NEEDED(playTime)
        long timeDifferenceInSeconds = (currState.getTime() - prevState.getTime()) / 1000;

        int timeBonus = 0;

        /*
          clear time   : 0 ~ 50    : +50
                       : 51 ~ 80   : +30
                       : 81 ~ 100  : +10
                       : 101 ~     : 0
         */
        if (timeDifferenceInSeconds <= 50) {
            timeBonus = 50;
        } else if (timeDifferenceInSeconds <= 80) {
            timeBonus = 30;
        } else if (timeDifferenceInSeconds <= 100) {
            timeBonus = 10;
        }
        currency += timeBonus;

        return currency;
    }

    public int getCurrency() throws IOException {
        return fileManager.loadCurrency();
    }

    /**
     * Add an amount of gem to the current gem.
     */
    // Team-Ctrl-S(Currency)
    public void addGem(int amount) throws IOException {
        int current_gem = fileManager.loadGem();
        amount += current_gem;
        fileManager.saveGem(amount);
    }

    /**
     * Consume as much gem as the amount you have (cannot spend more than you currently have).
     */
    // Team-Ctrl-S(Currency)
    public boolean spendGem(int amount) throws IOException {
        int current_gem = fileManager.loadGem();
        if (amount <= current_gem) {
            current_gem -= amount;
            fileManager.saveGem(current_gem);
            return true;
        }
        else {
            return false;
        }
    }

    // Team-Ctrl-S(Currency)
    public int getGem() throws IOException {
        return fileManager.loadGem();
    }
}
