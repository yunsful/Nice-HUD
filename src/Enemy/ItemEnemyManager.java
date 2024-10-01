package Enemy;

import java.awt.Color;

public class ItemEnemyManager{

    private static double magentaProbability = 0.1; //set basic probability

    /**
     * For determining color
     *
     * @return determined color.
     */

    public static Color setColor() {
        return Math.random() < magentaProbability ? Color.MAGENTA : Color.WHITE; // 설정된 확률로 보라색 반환
    }

    /**
     * set the probability of being a MAGENTA color.
     *
     * @param probability the probability to a value between 0 and 1.
     */

    public static void setItemEnemyProbability(double probability) {
        if (probability >= 0.0 && probability <= 1.0) {
            magentaProbability = probability;
        } else {
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        }
    }

    /**
     * returns the probability of being the current MAGENTA color.
     *
     * @return current probability.
     */
    public static double getMagentaProbability() {
        return magentaProbability;
    }
}