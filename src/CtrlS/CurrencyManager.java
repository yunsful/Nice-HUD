package CtrlS;

import engine.Core;
import engine.FileManager;

import java.io.IOException;
import java.util.logging.Logger;
// item level Bonus
import inventory_develop.ShipStatus;



public final class CurrencyManager {

    /** Singleton instance of the class. */
    private static CurrencyManager instance;
    /** Application logger. */
    private static Logger logger;
    private static FileManager fileManager;

    private ShipStatus shipStatus;
    private static double levelBonus = 10;

    /**
     * private constructor.
     */
    private CurrencyManager() {
        fileManager = Core.getFileManager();
        logger = Core.getLogger();

        //Coin Bonus increase
        shipStatus = new ShipStatus();
        shipStatus.loadStatus();
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

    public int calculateCurrency(int score, float hitRate, int
            clearTime, int maxTime) {
        //

        int currency = score / 10;
        //

        if (hitRate > 0.8) {
            currency += (int) (currency * 0.2); // 20% 보너스 지급
            Core.getLogger().info("hitRate bonus occurs.");
        }
        //

        int timeBonus = (maxTime - clearTime) / 10;
        if (timeBonus > 0) {
            currency += timeBonus;
        }

        currency = (int) (currency * levelBonus);

        return currency;
    }


    public int getCurrency() throws IOException {
        return fileManager.loadCurrency();
    }

    public void levelBonusIN(){
        levelBonus += shipStatus.getCoinIn();
    }
}
