package CtrlS;

import engine.Core;
import engine.FileManager;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.logging.Logger;

public final class UpgradeManager {

    /** Singleton instance of the class. */
    private static UpgradeManager instance;
    /** Application logger. */
    private static Logger logger;
    private static FileManager fileManager;

    // Upgrade keys
    private static final String COIN_ACQUISITION_MULTIPLIER = "coin_acquisition_multiplier";
    private static final String ATTACK_SPEED = "attack_speed";
    private static final String MOVEMENT_SPEED = "movement_speed";
    private static final String BULLET_SPEED = "bullet_speed";

    /** Decimal format to ensure values have one decimal place. */
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.#");

    /**
     * private constructor.
     */
    private UpgradeManager() {
        fileManager = Core.getFileManager();
        logger = Core.getLogger();
    }

    /**
     * Returns shared instance of UpgradeManager.
     *
     * @return Shared instance of UpgradeManager.
     */
    public static UpgradeManager getInstance() {
        if (instance == null)
            instance = new UpgradeManager();
        return instance;
    }

    // Methods for coin acquisition multiplier

    /**
     * Get the current coin acquisition multiplier value.
     *
     * @return The current coin acquisition multiplier.
     * @throws IOException In case of loading problems.
     */
    public double getCoinAcquisitionMultiplier() throws IOException {
        Properties properties = fileManager.loadUpgradeStatus();
        return Double.parseDouble(properties.getProperty(COIN_ACQUISITION_MULTIPLIER, "1.0"));
    }

    /**
     * Add to the current coin acquisition multiplier.
     *
     * @param amount The amount to add.
     * @throws IOException In case of saving problems.
     */
    public void addCoinAcquisitionMultiplier(double amount) throws IOException {
        double currentValue = getCoinAcquisitionMultiplier();
        currentValue += amount;

        // Format the value to one decimal place
        String formattedValue = decimalFormat.format(currentValue);

        Properties properties = fileManager.loadUpgradeStatus();
        properties.setProperty(COIN_ACQUISITION_MULTIPLIER, formattedValue);
        fileManager.saveUpgradeStatus(properties);
    }

    // Methods for attack speed

    /**
     * Get the current attack speed value.
     *
     * @return The current attack speed.
     * @throws IOException In case of loading problems.
     */
    public int getAttackSpeed() throws IOException {
        Properties properties = fileManager.loadUpgradeStatus();
        return Integer.parseInt(properties.getProperty(ATTACK_SPEED, "1"));
    }

    /**
     * Add to the current attack speed.
     *
     * @param amount The amount to add.
     * @throws IOException In case of saving problems.
     */
    public void addAttackSpeed(int amount) throws IOException {
        int currentValue = getAttackSpeed();
        currentValue += amount;
        Properties properties = fileManager.loadUpgradeStatus();
        properties.setProperty(ATTACK_SPEED, Integer.toString(currentValue));
        fileManager.saveUpgradeStatus(properties);
    }

    // Methods for movement speed

    /**
     * Get the current movement speed value.
     *
     * @return The current movement speed.
     * @throws IOException In case of loading problems.
     */
    public int getMovementSpeed() throws IOException {
        Properties properties = fileManager.loadUpgradeStatus();
        return Integer.parseInt(properties.getProperty(MOVEMENT_SPEED, "1"));
    }

    /**
     * Add to the current movement speed.
     *
     * @param amount The amount to add.
     * @throws IOException In case of saving problems.
     */
    public void addMovementSpeed(int amount) throws IOException {
        int currentValue = getMovementSpeed();
        currentValue += amount;
        Properties properties = fileManager.loadUpgradeStatus();
        properties.setProperty(MOVEMENT_SPEED, Integer.toString(currentValue));
        fileManager.saveUpgradeStatus(properties);
    }

    // Methods for bullet speed

    /**
     * Get the current bullet speed value.
     *
     * @return The current bullet speed.
     * @throws IOException In case of loading problems.
     */
    public int getBulletSpeed() throws IOException {
        Properties properties = fileManager.loadUpgradeStatus();
        return Integer.parseInt(properties.getProperty(BULLET_SPEED, "1"));
    }

    /**
     * Add to the current bullet speed.
     *
     * @param amount The amount to add.
     * @throws IOException In case of saving problems.
     */
    public void addBulletSpeed(int amount) throws IOException {
        int currentValue = getBulletSpeed();
        currentValue += amount;
        Properties properties = fileManager.loadUpgradeStatus();
        properties.setProperty(BULLET_SPEED, Integer.toString(currentValue));
        fileManager.saveUpgradeStatus(properties);
    }

    /**
     * Reset all upgrades to their default values.
     *
     * @throws IOException In case of saving problems.
     */
    public void resetUpgrades() throws IOException {
        Properties properties = fileManager.loadDefaultUpgradeStatus();
        fileManager.saveUpgradeStatus(properties);
    }
}