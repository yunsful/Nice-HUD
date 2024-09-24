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
}
