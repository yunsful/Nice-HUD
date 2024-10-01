package level_design;

import engine.Frame;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Background {
    // Arbitrary value, for some reason the game jolts if I set it to zero. 25 Produces a seamless flow
    private final int resetOffset = 25;
    private final int scrollSpeedHorizontal = 3;
    private final int VerticalSpeedLimit = 2;

    private int verticalOffset = resetOffset;
    // Also arbitrary, but it sets the position at the middle
    private int horizontalOffset = -448;
    private int scrollSpeedVertical = 1;


    public static List<String> levelBackgrounds;
    // Static block to initialize levelBackgrounds
    static {
        levelBackgrounds = new ArrayList<>();
        levelBackgrounds.add("/backgrounds/background_level_1.jpg");
        levelBackgrounds.add("/backgrounds/background_level_2.jpg");
        levelBackgrounds.add("/backgrounds/background_level_3.jpg");
        levelBackgrounds.add("/backgrounds/background_level_4.jpg");
        levelBackgrounds.add("/backgrounds/background_level_5.jpg");
        levelBackgrounds.add("/backgrounds/background_level_6.jpg");
        levelBackgrounds.add("/backgrounds/background_level_7.jpg");
    }
    // Static method to get background image stream
    public static InputStream getBackgroundImageStream(int levelIndex) {
        if (levelBackgrounds != null && levelIndex >= 0 && levelIndex <= levelBackgrounds.size()) {
            return Background.class.getResourceAsStream(levelBackgrounds.get(levelIndex-1));
        } else {
            throw new IllegalArgumentException("Invalid index or levelBackgrounds not initialized");
        }
    }
    // Dynamic method to update background image vertical offset
    public int getVerticalOffset(Frame frame) {
        if (verticalOffset <= -frame.getHeight()) {
           verticalOffset = resetOffset;

           if (scrollSpeedVertical < VerticalSpeedLimit) {
               scrollSpeedVertical++;
           }
        }
        verticalOffset -= scrollSpeedVertical;

        return verticalOffset;
    }
    // Dynamic method to update background image horizontal offset
    public int getHorizontalOffset(Frame frame, boolean backgroundMoveRight, boolean backgroundMoveLeft) {
        if (backgroundMoveRight) {
            horizontalOffset -= scrollSpeedHorizontal;
        }
        if (backgroundMoveLeft) {
            horizontalOffset += scrollSpeedHorizontal;
        }
        return horizontalOffset;
    }
}
