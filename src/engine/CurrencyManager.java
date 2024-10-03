package engine;

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
    protected static CurrencyManager getInstance() {
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
    // Written by Ctrl+S
    public int calculateCurrency(int score, int level, float hitRate, long
            startTime, long endTime) {
        //
        int baseCurrency = score / 10;
        int levelBonus = baseCurrency * level;
        int currency = baseCurrency + levelBonus;
        //

        if (hitRate > 0.9) {
            currency += (int) (currency * 0.3); // 30% 보너스 지급
            Core.getLogger().info("hitRate bonus occurs (30%).");
        } else if (hitRate > 0.8) {
            currency += (int) (currency * 0.2); // 20% 보너스 지급
            Core.getLogger().info("hitRate bonus occurs (20%).");
        }
        //

        long timeBonus = (startTime - endTime) / 10;
        if (timeBonus > 0) {
            currency += timeBonus;
        }
        return currency;
    }


    public int getCurrency() throws IOException {
        return fileManager.loadCurrency();
    }
}
