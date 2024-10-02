package clove;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import CtrlS.CurrencyManager;
import engine.Core;
import engine.FileManager;

public class Statistics {

    public static Statistics instance;

    private int shipsDestructionStreak;
    private int playedGameNumber;
    private int clearAchievementNumber;
    private int bulletCount;

    private static FileManager fileManager;
    private static Logger logger;

    private List<Statistics> playerStatistics;

    public Statistics(int shipsDestructionStreak, int playedGameNumber, int clearAchievementNumber) {
        this.shipsDestructionStreak = shipsDestructionStreak;
        this.playedGameNumber = playedGameNumber;
        this.clearAchievementNumber = clearAchievementNumber;
    }

    public Statistics(int bulletCount) {
        this.bulletCount = bulletCount;
    }

    public Statistics() {
        fileManager = Core.getFileManager();
        logger = Core.getLogger();
    }


    public int getShipsDestructionStreak() { return shipsDestructionStreak; }

    public int getPlayedGameNumber() { return playedGameNumber; }

    public int getClearAchievementNumber() { return clearAchievementNumber; }

    public int getBulletCount() { return bulletCount; }

    public void addPlayedGameNumber(int PlusPlayedGameNumber) throws IOException {
        Statistics stat = fileManager.loadUserData();
        int CurrentPlayedGameNumber = stat.getPlayedGameNumber();
        PlusPlayedGameNumber += CurrentPlayedGameNumber;
        playerStatistics.add(new Statistics(shipsDestructionStreak, PlusPlayedGameNumber, clearAchievementNumber));
        fileManager.saveUserData(playerStatistics);
    }

    public void calShipsDestructionStreak(int DestroyedShipNumber) throws IOException {
        Statistics stat = fileManager.loadUserData();
        int CurrentShipsDestructionStreak = stat.getShipsDestructionStreak();
        if(CurrentShipsDestructionStreak < DestroyedShipNumber){
            playerStatistics.add(new Statistics(DestroyedShipNumber, playedGameNumber, clearAchievementNumber));
            fileManager.saveUserData(playerStatistics);
        }
    }

    public void calClearAchievementNumber(int ClearedAchievement) throws IOException {
        Statistics stat = fileManager.loadUserData();
        int CurrentClearAchievementNumber = stat.getClearAchievementNumber();
        if(CurrentClearAchievementNumber < ClearedAchievement){
            playerStatistics.add(new Statistics(shipsDestructionStreak, playedGameNumber, ClearedAchievement));
            fileManager.saveUserData(playerStatistics);
        }
    }

    public int onBullet(boolean Hit, int count){
        if(Hit){
            count++;
        }
        else{
            count = 0;
        }
        Core.getLogger().info("Now BulletCount is " + bulletCount);
        return count;
    }
}