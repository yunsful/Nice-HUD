package clove;

import java.util.ArrayList;
import java.util.List;
import engine.Core;
import engine.DrawManager;
import engine.GameState;

public class AchievementConditions {

    private DrawManager drawManager;

    private long killStartTime;
    private int enemiesKilledInTime;

    private List<Achievement> killAchievements = new ArrayList<>();
    private List<Achievement> trialAchievements = new ArrayList<>();
    private List<Achievement> streakAchievements = new ArrayList<>();
    private List<Achievement> fastKillAchievements = new ArrayList<>();
    private List<Achievement> noDeathAchievements = new ArrayList<>();
    private List<Achievement> stageAchievements = new ArrayList<>();
    private List<Achievement> scoreAchievements = new ArrayList<>();
    private List<Achievement> allAchievements = new ArrayList<>();
    private List<String> unlockedAchievements = new ArrayList<>();

    private static final int FAST_KILL_TIME = 5;

    public AchievementConditions(DrawManager drawManager) {

        // TODO Some of the codes below are non-operational.(fastKillAchievement,trialAchievement) Will be updated if related record fuctions are added.
        noDeathAchievements.add(new Achievement("Aerobatics","Maintain Maximum Life",1, Achievement.AchievementType.LIVES));

        killAchievements.add(new Achievement("Rookie Pilot", "Destroy 25 enemies", 1, Achievement.AchievementType.KILLS));
        killAchievements.add(new Achievement("Space Hunter", "Destroy 50 enemies", 100, Achievement.AchievementType.KILLS));
        killAchievements.add(new Achievement("Space Trooper", "Destroy 100 enemies", 250, Achievement.AchievementType.KILLS));
        killAchievements.add(new Achievement("Guardian of Universe", "Destroy 200 enemies", 500, Achievement.AchievementType.KILLS));

        trialAchievements.add(new Achievement("Welcome Recruit", "Finished first game", 1, Achievement.AchievementType.TRIALS));
        trialAchievements.add(new Achievement("Skilled Solider", "Finished 10th game", 10, Achievement.AchievementType.TRIALS));
        trialAchievements.add(new Achievement("Veteran Pilot", "Finished 50th game", 50, Achievement.AchievementType.TRIALS));

        streakAchievements.add(new Achievement("Preheating", "Kill streak of 10", 10, Achievement.AchievementType.KILLSTREAKS));
        streakAchievements.add(new Achievement("Overheating", "Kill streak of 30", 30, Achievement.AchievementType.KILLSTREAKS));
        streakAchievements.add(new Achievement("Runaway","Kill streak of 60", 60, Achievement.AchievementType.KILLSTREAKS));

        fastKillAchievements.add(new Achievement("Gunsliger","Kill 3 enemies 5 seconds", 3, Achievement.AchievementType.FASTKILL));
        fastKillAchievements.add(new Achievement("Fear the Enemy","Kill 5 enemies 5 seconds", 5, Achievement.AchievementType.FASTKILL));
        fastKillAchievements.add(new Achievement("Genocide","Kill 15 enemies 5 seconds", 15, Achievement.AchievementType.FASTKILL));

        scoreAchievements.add(new Achievement("First Milestone", "Reach 1,000 points", 3000, Achievement.AchievementType.SCORE));
        scoreAchievements.add(new Achievement("Score Hunter", "Reach 5,000 points", 5000, Achievement.AchievementType.SCORE));
        scoreAchievements.add(new Achievement("Score Master", "Reach 10,000 points", 10000, Achievement.AchievementType.SCORE));

        stageAchievements.add(new Achievement("Home Sweet Home","Cleared Final Stage", Core.NUM_LEVELS, Achievement.AchievementType.STAGE));

        allAchievements.addAll(killAchievements);
        allAchievements.addAll(trialAchievements);
        allAchievements.addAll(streakAchievements);
        allAchievements.addAll(fastKillAchievements);
        allAchievements.addAll(noDeathAchievements);
        allAchievements.addAll(stageAchievements);
        allAchievements.addAll(scoreAchievements);

        allAchievements.add(new Achievement("Medal of Honor", "Complete all achievements", 0, Achievement.AchievementType.STAGE));
    }

    private void logCompleteAchievement(Achievement achievement) {
        if (!unlockedAchievements.contains(achievement.getAchievementName())) {
            System.out.println("Attempting to complete achievement: " + achievement.getAchievementName()); // 로그 추가
            unlockedAchievements.add(achievement.getAchievementName());
            achievement.completeAchievement();
            System.out.println("Achievement unlocked: " + achievement.getAchievementName()); // 업적이 완료되었을 때 로그 추가
        }
    }

    public void checkAllAchievements() {
        boolean allCompleted = true;

        System.out.println("Checking all achievements...");
        for (Achievement achievement: allAchievements) {
            if (!achievement.isCompleted()) {
                allCompleted = false;
                break;
            }
        }
        if (allCompleted) {
            completeAchievement(allAchievements.get(allAchievements.size() - 1));
        }
    }

    public void onScoreAchievement() {
        int currentScore = GameState.score;
        System.out.println("Checking score achievements. Current score: " + currentScore);
        for (Achievement achievement : scoreAchievements) {
            if (currentScore >= achievement.getRequiredScore() && !achievement.isCompleted()) {
                completeAchievement(achievement);
            }
        }
    }

    public void checkStageAchievements(int currentStage) {
        System.out.println("Checking stage achievements. Current stage: " + currentStage);
        for (Achievement achievement : stageAchievements) {
            if (currentStage >= achievement.getRequiredStages() && !achievement.isCompleted()) {
                completeAchievement(achievement);
            }
        }
    }

    public void onKill() {
        int currentKills = GameState.shipsDestroyed();
        System.out.println("Checking kill achievements. Current kills: " + currentKills);
        for (Achievement achievement : killAchievements) {
            System.out.println("Checking " + achievement.getAchievementName());
            if (currentKills >= achievement.getRequiredKills() && !achievement.isCompleted()) {
                completeAchievement(achievement);
            }
        }
    }

    public void fastKill(int fastKills) {
        long currentTime = System.currentTimeMillis();

        if (killStartTime == 0) {
            killStartTime = currentTime;
            enemiesKilledInTime = 0;
        }
        enemiesKilledInTime++;

        if (currentTime - killStartTime <= FAST_KILL_TIME* 1000) {
            for (Achievement achievement : fastKillAchievements) {
                if (enemiesKilledInTime >= fastKills && !achievement.isCompleted()) {
                    completeAchievement(achievement);
                    resetKillTimer();
                    break;
                }
            }
        } else {
            resetKillTimer();
        }
    }
    // Timer for Kill Streak achievements
    private void resetKillTimer() {
        killStartTime = 0;
        enemiesKilledInTime = 0;
    }

    public void checkNoDeathAchievements() {
        int livesRemaining = GameState.livesRemaining();
        System.out.println("Checking kill achievements. Current kills: " + livesRemaining);
        if (livesRemaining == Core.MAX_LIVES) {
            for (Achievement achievement : noDeathAchievements) {
                if (!achievement.isCompleted()) {
                    completeAchievement(achievement);
                }
            }
        }
    }

    // TODO Annotations below are unfinished codes
    // TODO getRequiredTrials : Function to track current number of plays required
    /*
    public void trials(int currentTrials) {
        for (Achievement achievement : trialAchievements) {
            if (currentTrials >= achievement.getRequiredTrials() && !achievement.isCompleted()) {
                completeAchievement(achievement);
            }
        }
    }
    // TODO getRequiredStreaks : Function to track current Kill Streak(Enemy kill without missing the bullets) required
    public void killStreak(int currentStreak) {
        for (Achievement achievement : streakAchievements) {
            if (currentStreak >= achievement.getRequiredStreaks() && !achievement.isCompleted()) {
                completeAchievement(achievement);
            }
        }
    }
    // TODO Achievements to add : Boss Achievements / Hidden Achievements
    */

    private void completeAchievement(Achievement achievement) {
        if (!unlockedAchievements.contains(achievement.getAchievementName())) {
            System.out.println("Achievement Unlocked: " + achievement.getAchievementName() + " - " + achievement.getAchievementDescription());
            unlockedAchievements.add(achievement.getAchievementName());
            achievement.completeAchievement();
        }
        else {
            System.out.println(achievement.getAchievementName() + " has already been unlocked.");
        }
    }

    public List<String> getUnlockedAchievements() {
        return new ArrayList<>(unlockedAchievements);
    }

    public int getTotalAchievements() {
        return unlockedAchievements.size();
    }
}