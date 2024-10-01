package HUDTeam;

public class Achievement {

    static int timer;
    static String achievementText;

    public Achievement() {
        timer = 100;
        achievementText = null;
    }

    public static int getTimer(){return timer;}

    public static String getAchievementText(){return achievementText;}

    public static void addTimer(){timer++;}

    /**
     * Input accomplished achievement text.
     *
     * @param Text
     *            Accomplished achievement text.
     */
    public static void achieve(String Text){
        timer = 0;
        achievementText = Text;
    }
}
