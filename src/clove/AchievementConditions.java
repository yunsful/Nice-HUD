package clove;

public class AchievementConditions extends Achievement {
    private static final int REQ_KILL_100 = 100;

    public AchievementConditions() {
        super("Space Hunter", "Defeat 100 enemies", REQ_KILL_100);
    }

    public void onkill(int currentKills) {
        if (currentKills > REQ_KILL_100 && !isCompleted()) {
            completeAchievement();
        }
    }
}
