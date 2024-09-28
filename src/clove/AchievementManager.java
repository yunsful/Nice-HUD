package clove;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

// TODO : Replace Object to Achievement Class
public class AchievementManager {
    /*
        Variables
     */
    private HashMap<Object, Boolean> achievementMap;
    private ArrayList<AchievementChangedCallback> achievementChangedCallbacks;

    /*
        Callbacks
     */
    @FunctionalInterface
    public interface AchievementChangedCallback {
        void onAchievementChanged(Object achievement, boolean value);
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
    public AchievementManager(){
        ;
    }

    /*
        Functions
     */
    public boolean AddAchievement(Object achievement, Boolean completed){
        if (achievementMap.containsKey(achievement))
            return false; // 이미 업적이 추가되었을 경우 거절
        achievementMap.put(achievement, completed);
        return true;
    }

    public boolean AddAchievement(Object achievement){
        return AddAchievement(achievement, false);
    }

    public boolean hasAchivement(Object achievement){
        return achievementMap.containsKey(achievement);
    }

    public Set<Object> getAchievements(){
        return achievementMap.keySet();
    }

    public boolean setAchievementValue(Object achievement, Boolean completed){
        if(!hasAchivement(achievement)){
            // TODO : 업적을 설정하는 것에 실패했다는 알림(또는 로그) 출력
            return false;
        }
        achievementMap.replace(achievement, completed);
        for (AchievementChangedCallback callback : achievementChangedCallbacks) {
            callback.onAchievementChanged(achievement, completed);
        }
        return true;
    }

    public boolean getAchievementValue(Object achievement){
        // Boolean 타입을 boolean 타입으로 변환하여 반환
        return Boolean.TRUE.equals(achievementMap.getOrDefault(achievement, false));
    }

    /*
        편의성 함수
        (코드를 편하게 사용하기 위해 추가된 함수)
     */
    public boolean completeAchievement(Object achievement){
        return setAchievementValue(achievement, true);
    }
}
