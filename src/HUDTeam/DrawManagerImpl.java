package HUDTeam;

import engine.DrawManager;
import screen.Screen;
import entity.Entity;
import java.awt.Color;

import java.awt.*;

public class DrawManagerImpl extends DrawManager {

    // Add method
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
        backBufferGraphics.drawString(attackSpeedText, 10, screen.getHeight() - 25);
    }

    public static void drawSpeed(final Screen screen, final int speed) {
        String speedString = "MS : " + speed;
        backBufferGraphics.setColor(Color.WHITE);
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.drawString(speedString, 85, screen.getHeight() - 25);
    }

    public void drawLivesWithHeart(final Screen screen, final int lives) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.setColor(Color.WHITE);

        Entity heart = new Entity(0, 0, 13 * 2, 8 * 2, Color.RED);
        heart.setSpriteType(SpriteType.Heart);

        for (int i = 0; i < lives; i++) {
            drawEntity(heart, 20 + 30 * i, 10);
        }
    } // Saeum Jung - heart graphic

    /**
     * Draws current score on screen.
     *
     * @param screen
     *            Screen to draw on.
     * @param playTime
     *            Current playtime.
     *
     * by Soomin Lee - TeamHUD
     */
    public static void drawTime(final Screen screen, final int playTime) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.setColor(Color.WHITE);
        int playTimeMinutes = playTime / 60;
        int playTimeSeconds = playTime % 60;
        String playTimeString = String.format("%d"+"m "+"%d"+"s", playTimeMinutes, playTimeSeconds);
        backBufferGraphics.drawString(playTimeString, screen.getWidth() / 2 - 20, 25);
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
    
    /**
     * Draw remaining enemies
     *
     * @param screen
     *            Screen to draw on.
     * @param remainingEnemies
     *            remaining enemies count.
     */
    public static void drawRemainingEnemies(final Screen screen, final int remainingEnemies) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.setColor(Color.WHITE);
        String remainingEnemiesString = "Enemies: " + remainingEnemies;
        backBufferGraphics.drawString(remainingEnemiesString, 140, screen.getHeight() - 25);
    } // by SeungYun
}
