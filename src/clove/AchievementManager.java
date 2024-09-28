package clove;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

// TODO : Replace Object to Achievement Class
// 수정 완료, 테스트 필요
public class AchievementManager {
    /*
        Variables
     */
    private HashMap<Achievement, Boolean> achievementMap; // Object -> Achievement
    private ArrayList<AchievementChangedCallback> achievementChangedCallbacks;

    /*
        Callbacks
     */
    @FunctionalInterface
    public interface AchievementChangedCallback {
        void onAchievementChanged(Achievement achievement, boolean value); // Object -> Achievement
    }

    public void addAchievementChangedCallback(AchievementChangedCallback callback){
        achievementChangedCallbacks.add(callback);
    }

    public void removeAchievementChangedCallback(AchievementChangedCallback callback){
        achievementChangedCallbacks.remove(callback);
    }

    /*
        Declare
     */
    public AchievementManager() { // HashMap<Achievement, Boolean>으로 초기화
        achievementMap = new HashMap<>();
        achievementChangedCallbacks = new ArrayList<>();
    }

    /*
        Functions
     */

    public boolean addAchievement(Achievement achievement, Boolean completed) { // Object -> Achievement
        if (achievementMap.containsKey(achievement))
            return false;
        achievementMap.put(achievement, completed);
        return true; // Changed code
    }

    public boolean addAchievement(Achievement achievement) { // Object -> Achievement로 변경
        return addAchievement(achievement, false);
    }

    public boolean hasAchivement(Achievement achievement){ // Object -> Achievement
        return achievementMap.containsKey(achievement);
    }

    public Set<Achievement> getAchievements() { // Object -> Achievement
        return achievementMap.keySet();
    }

    public boolean setAchievementValue(Achievement achievement, Boolean completed) { // Object -> Achievement
        if(!hasAchivement(achievement)){
            // TODO : 업적을 설정하는 것에 실패했다는 알림(또는 로그) 출력
            // 로그 출력 코드 작성 완료, 테스트 필요
            System.out.println("Failed to set achievement: " + achievement.getAchievementName()); // 로그 추가
            return false;
        }
        achievementMap.replace(achievement, completed);
        for (AchievementChangedCallback callback : achievementChangedCallbacks) {
            callback.onAchievementChanged(achievement, completed);
        }
        return true;
    }

    public boolean getAchievementValue(Achievement achievement) { // Object -> Achievement
        // Boolean 타입을 boolean 타입으로 변환하여 반환
        return Boolean.TRUE.equals(achievementMap.getOrDefault(achievement, false));
    }

    /*
        편의성 함수
        (코드를 편하게 사용하기 위해 추가된 함수)
     */
    public boolean completeAchievement(Achievement achievement) { // 코드 추가
        if (!achievement.isCompleted()) {
            achievement.completeAchievement();
            return setAchievementValue(achievement, true);
        }
        return false;
    }
}
