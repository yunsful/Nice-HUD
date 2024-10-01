package clove;

import java.util.ArrayList;
import java.util.List;
import engine.Core;
import engine.GameState;

public class AchievementConditions {

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

    public AchievementConditions() {

        // TODO 아직 작동하지 않는 코드 일부 있음.(fastKillAchievement,trialAchievement) 관련 기록 기능 추가되면 수정 필요
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

    public void checkAllAchievements() {
        boolean allCompleted = true;

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

    public void onScoreAchievement(int currentScore) {
        for (Achievement achievement : scoreAchievements) {
            if (currentScore >= achievement.getRequiredScore() && !achievement.isCompleted()) {
                completeAchievement(achievement);
            }
        }
    }

    public void checkStageAchievements(int currentStage) {
        for (Achievement achievement : stageAchievements) {
            if (currentStage >= achievement.getRequiredStages() && !achievement.isCompleted()) {
                completeAchievement(achievement);
            }
        }
    }

    public void onKill(int currentKills) {
        for (Achievement achievement : killAchievements) {
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
    // 시간 내 연속처치 업적에 필요한 타이머
    private void resetKillTimer() {
        killStartTime = 0;
        enemiesKilledInTime = 0;
    }

    public void checkNoDeathAchievements() {
        int livesRemaining = GameState.livesRemaining();
        if (livesRemaining == Core.MAX_LIVES) {
            for (Achievement achievement : noDeathAchievements) {
                if (!achievement.isCompleted()) {
                    completeAchievement(achievement);
                }
            }
        }
    }

    // TODO 현재 아래의 미완성 코드는 주석으로 처리한 상태
    // TODO getRequiredTrials : 현재 플레이 횟수를 추적하는 기능 필요
    /*
    public void trials(int currentTrials) {
        for (Achievement achievement : trialAchievements) {
            if (currentTrials >= achievement.getRequiredTrials() && !achievement.isCompleted()) {
                completeAchievement(achievement);
            }
        }
    }
    // TODO getRequiredStreaks : 현재 연속처치(탄 누수 없이 적을 연속으로 처치)한 횟수를 기록하는 기능 필요
    public void killStreak(int currentStreak) {
        for (Achievement achievement : streakAchievements) {
            if (currentStreak >= achievement.getRequiredStreaks() && !achievement.isCompleted()) {
                completeAchievement(achievement);
            }
        }
    }
    // TODO 추가할 업적 : 보스 처치 업적 / 이후 개방되는 히든 업적
    */

    private void completeAchievement(Achievement achievement) {
        if (!unlockedAchievements.contains(achievement.getAchievementName())) {  // 중복 확인
            System.out.println("Achievement Unlocked: " + achievement.getAchievementName() + " - " + achievement.getAchievementDescription());
            unlockedAchievements.add(achievement.getAchievementName());
            achievement.completeAchievement(); // Achievement 클래스에서 달성 처리
        }
    }

    public List<String> getUnlockedAchievements() {  // static 제거
        return new ArrayList<>(unlockedAchievements); // 리스트 복사본을 반환
    }

    public int getTotalAchievements() {  // static 제거
        return unlockedAchievements.size();
    }
}