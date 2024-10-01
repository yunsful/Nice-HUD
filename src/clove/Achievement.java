package clove;

public class Achievement {
    private String achievementName; // 업적의 이름
    private String achievementDescription; // 업적에 대한 설명
    private int requiredKills; // 필요한 처치 수
    private boolean isCompleted;

    // 생성자에서 requiredKills도 매개변수로 받아 초기화
    public Achievement(String achievementName, String achievementDescription, int requiredKills) {
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        this.requiredKills = requiredKills;
        this.isCompleted = false;
    }

    public String getAchievementName() {
        return achievementName; // 업적의 이름 반환
    }

    public String getAchievementDescription() {
        return achievementDescription; // 업적의 설명 반환
    }

    public boolean isCompleted() { // 업적 달성 여부 확인
        return isCompleted;
    }

    // 처치 수를 기준으로 업적 달성 조건 대조
    public boolean checkKillConditions(int currentKills) {
        return currentKills >= requiredKills; // requiredKills를 기준으로 조건 확인
    }

    public void completeAchievement() {
        if (!isCompleted) {
            isCompleted = true;
            System.out.println("Achievement Unlocked: " + achievementName);
        }
    }

    public void printAchievementStatus() {
        if (isCompleted) {
            System.out.println("Achievement: " + achievementName + " - Status: Completed"); // 달성된 경우 출력
        } else {
            System.out.println("Achievement: " + achievementName + " - Status: Not Completed"); // 달성되지 않은 경우 출력
        }
    }

    @Override // 두 개의 업적 객체가 동일한 이름을 가진다면 같은 업적으로 처리
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Achievement that = (Achievement) o;
        return achievementName.equals(that.achievementName);
    }

    @Override // 해시값 반환
    public int hashCode() {
        return achievementName.hashCode();
    }

    @Override // 업적 객체를 문자열로 표현
    public String toString() {
        return "Achievement{" + "Achieved: " + achievementName + " - " + achievementDescription + "}";
    }
}
