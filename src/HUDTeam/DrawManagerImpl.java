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
    
    public static void drawAttackSpeed(final Screen screen, final double attackSpeed) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.setColor(Color.WHITE);
        String attackSpeedText = String.format("AS: %.2f ", attackSpeed);
        backBufferGraphics.drawString(attackSpeedText, 10, screen.getHeight() - 25); // 화면 상단 좌측에 표시
    }

    public static void drawSpeed(final Screen screen, final int speed) {
        String speedString = "MS : " + speed;
        backBufferGraphics.setColor(Color.WHITE);
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.drawString(speedString, 85, screen.getHeight() - 25);
    }

    /**
     * Show accomplished achievement
     *
     * @param screen
     *            Screen to draw on.
     * @param achievementText
     *            Accomplished achievement text.
     */
    public static void drawAchievement(final Screen screen, String achievementText) {
        int width = screen.getWidth() / 4;
        int height = screen.getHeight() / 16;

        backBufferGraphics.setColor(Color.white);
        backBufferGraphics.drawRect(screen.getWidth() - width - 8, screen.getHeight() - height - 20, width, height);

        backBufferGraphics.setColor(Color.white);
        backBufferGraphics.setFont(fontRegular);
        if(achievementText.length() < 14){
            backBufferGraphics.drawString(achievementText, screen.getWidth() - width, screen.getHeight() - 35);
        }
        else if(achievementText.length() < 27){
            backBufferGraphics.drawString(achievementText.substring(0,13), screen.getWidth() - width, screen.getHeight() - 45);
            backBufferGraphics.drawString(achievementText.substring(13), screen.getWidth() - width, screen.getHeight() - 25);
        }
        else{
            backBufferGraphics.drawString(achievementText.substring(0,13), screen.getWidth() - width, screen.getHeight() - 45);
            backBufferGraphics.drawString(achievementText.substring(13,26), screen.getWidth() - width, screen.getHeight() - 25);
        }
    }
}
