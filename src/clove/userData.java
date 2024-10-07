package clove;

//userDataSet
public class userData {
    private String userName;
    private int killCount;
    private int Level;
    private int highScore;

    public userData(){}

    public userData(String userName, int killCount, int Level, int Score) {
        this.userName = userName;
        this.killCount = killCount;
        this.Level = Level;
        this.highScore = Score;
    }

    //userData Get and Set
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public int getKillCount() { return killCount; }
    public void setKillCount(int killCount) { this.killCount = killCount; }

    public int getLevel() { return Level; }
    public void setLevel(int Level) { this.Level = Level; }

    public int getHighScore() { return highScore; }
    public void setHighScore(int highScore) { this.highScore = highScore; }


}