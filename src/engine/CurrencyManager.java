package engine;

import java.util.logging.Logger;



public final class CurrencyManager {

    /** Singleton instance of the class. */
    private static CurrencyManager instance;
    /** Application logger. */
    private static Logger logger;

    /**
     * private constructor.
     */
    private CurrencyManager() { logger = Core.getLogger(); }

    /**
     * Returns shared instance of FileManager.
     *
     * @return Shared instance of FileManager.
     */
    private static CurrencyManager getInstance() {
        if (instance == null)
             instance = new CurrencyManager();
        return instance;
    }

    /**
     * Add an amount of money to the current currency.
     */
    public static void addCurrency(int amount) {
        int current_currency = FileManager.loadCurrency();
        amount += current_currency;
        FileManager.saveCurrency(amount);
    }

    /**
     * Consume as much money as the amount you have (cannot spend more than you currently have).
     */
    public static boolean spendCurrency(int amount) {
        int current_currency = FileManager.loadCurrency();
        if (amount <= current_currency) {
            current_currency -= amount;
            FileManager.saveCurrency(current_currency);
            return true;
        }
        else {
            return false;
        }
    }

    public int calculateCurrency(int score, float hitRate, int
            clearTime, int maxTime) {
        //

        int currency = score / 10;
        //

        if (hitRate > 80.0) {
            currency += (int) (currency * 0.2); // 20% 보너스 지급
        }
        //

        int timeBonus = (maxTime - clearTime) / 10;
        if (timeBonus > 0) {
            currency += timeBonus;
        }
        return currency;
    }


    public static int getCurrency() {
        return FileManager.loadCurrency();
    }
}
