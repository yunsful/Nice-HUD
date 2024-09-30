package HUDTeam;

import engine.DrawManager;
import screen.Screen;

import java.awt.*;

public class DrawManagerImpl extends DrawManager {

    // 메서드 추가
    public static void drawLevel(final Screen screen, final int level){
        String levelText = "Level: " + level;

        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.setColor(Color.WHITE);

        int xPosition = screen.getWidth() - fontRegularMetrics.stringWidth(levelText) - 100;
        int yPosition = 25;

        backBufferGraphics.drawString(levelText, xPosition, yPosition);
    } // Lee Hyun Woo - level

}
