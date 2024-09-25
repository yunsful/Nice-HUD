package engine;

import java.util.logging.Logger;



public final class CurrencyManager {
    private static CurrencyManager instance;
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
