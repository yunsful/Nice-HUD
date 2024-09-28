package clove;

public class Achievement {
    private String achievementName; // 업적의 이름
    private String achievementDescription; // 업적에 대한 설명


    public Achievement(String achievementName, String achievementDescription) {
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
    }

    public String getAchievementName() {
        return achievementName; // 업적의 이름 반환
    }

    public String getAchievementDescription() {
        return achievementDescription; // 업적의 설명 반환
    }

    @Override // 두개의 업적 객체가 동일한 이름을 가진다면 같은 업적으로 처리
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

    @Override // 업적 객체 문자열로 표현
    public String toString() {
        return "Achievement{name='" + achievementName + "', description='" + achievementDescription + "'}";
    }
}
