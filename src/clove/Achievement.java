package clove;

public class Achievement {
    public enum AchievementType {
        KILLS,
        KILLSTREAKS,
        LIVES,
        STAGE,
        TRIALS,
        FASTKILL,
        SCORE
    }

    private String achievementName;
    private String achievementDescription;
    private int requiredKills;
    private int requiredScore;
    private int requiredStages;
    private boolean isCompleted;
    private AchievementType type;

    public Achievement(String achievementName, String achievementDescription, int requiredValue, AchievementType type) {
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        this.isCompleted = false;
        this.type = type;

        // Assign values to appropriate fields based on type
        switch (type) {
            case KILLS:
            case KILLSTREAKS:
                this.requiredKills = requiredValue;
                break;
            case SCORE:
                this.requiredScore = requiredValue;
                break;
            case STAGE:
            case TRIALS:
                this.requiredStages = requiredValue;
                break;
            default:
                throw new IllegalArgumentException("Unsupported AchievementType: " + type);
        }
    }

    public AchievementType getType() {
        return type;
    }

    public int getRequiredScore() {
        return requiredScore;
    }

    public int getRequiredKills() {
        return requiredKills;
    }

    public int getRequiredStages() {
        return requiredStages;
    }

    public String getAchievementDescription() {
        return achievementDescription;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean checkKillConditions(int currentKills) {
        if (this.type == AchievementType.KILLS || this.type == AchievementType.KILLSTREAKS) {
            return currentKills >= requiredKills;
        }
        return false;
    }

    public boolean checkStageConditions(int currentStages) {
        if (this.type == AchievementType.STAGE || this.type == AchievementType.TRIALS) {
            return currentStages >= requiredStages;
        }
        return false;
    }

    public boolean checkScoreConditions(int currentScore) {
        if (this.type == AchievementType.SCORE) {
            return currentScore >= requiredScore;
        }
        return false;
    }

    public void completeAchievement() {
        if (!isCompleted) {
            isCompleted = true;
            System.out.println("Achievement Unlocked: " + achievementName);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Achievement that = (Achievement) o;
        return achievementName.equals(that.achievementName);
    }

    @Override
    public int hashCode() {
        return achievementName.hashCode();
    }

    @Override
    public String toString() {
        return "Achievement{" + "Achieved: " + achievementName + " - " + achievementDescription + "}";
    }
}