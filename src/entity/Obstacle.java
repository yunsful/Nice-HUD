package entity;

import java.awt.Color;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;

/**
 * Implements an obstacle, to be destroyed by the player.
 */
public class Obstacle extends Entity {

    /** Cooldown for obstacle movement or other features. */
    private Cooldown movementCooldown;
    /** Cooldown for the explosion animation. */
    private Cooldown explosionCooldown;
    /** Checks if the obstacle has been destroyed by a bullet. */
    private boolean isDestroyed;

    /**
     * Constructor, establishes the obstacle's properties.
     * 
     * @param positionX Initial position of the obstacle in the X axis.
     * @param positionY Initial position of the obstacle in the Y axis.
     */
    public Obstacle(final int positionX, final int positionY) {
        super(positionX, positionY, 14 * 2, 7 * 2, Color.ORANGE); // Use different size/color than EnemyShip
        this.spriteType = SpriteType.Obstacle; // Ensure it's an obstacle shape sprite type
        this.isDestroyed = false;
        this.movementCooldown = Core.getCooldown(500); // Adjust the cooldown for obstacle movement
    }

    /**
     * Moves the obstacle a certain distance.
     * 
     * @param distanceX Distance to move in the X axis.
     * @param distanceY Distance to move in the Y axis.
     */
    public final void move(final int distanceX, final int distanceY) {
        this.positionX += distanceX;
        this.positionY += distanceY;
    }

    /**
     * Updates the obstacle's movement or other attributes.
     */
    public final void update(int level) {
        if (!this.isDestroyed) {
            // If the obstacle is not destroyed, move or perform other actions
            if (this.movementCooldown.checkFinished()) {
                this.movementCooldown.reset();
            int speed = 10 + (level * 2);  // Base speed is 10, increases by 2 per level.
            this.positionY += speed;            }
        } else {
            // If destroyed, check if the explosion animation should finish
            if (this.explosionCooldown != null && this.explosionCooldown.checkFinished()) {
                // Explosion finished, obstacle can be removed (handled by GameScreen)
                this.spriteType = null;  // Mark this obstacle to be removed
            }
        }
    }

    /**
     * Destroys the obstacle, causing it to explode.
     */
    public final void destroy() {
        if (!this.isDestroyed) {
            this.isDestroyed = true;
            this.spriteType = SpriteType.Explosion;  // Set sprite to explosion
            this.explosionCooldown = Core.getCooldown(500);  // Explosion lasts for 500 ms (adjust as needed)
            this.explosionCooldown.reset();
        }
    }

    /**
     * Checks if the obstacle has been destroyed.
     * 
     * @return True if the obstacle has been destroyed.
     */
    public final boolean isDestroyed() {
        return this.isDestroyed;
    }

    /**
     * Checks if the obstacle should be removed after explosion animation.
     * 
     * @return True if the obstacle can be removed.
     */
    public final boolean shouldBeRemoved() {
        return this.isDestroyed && this.spriteType == null;  // Remove when spriteType is null after explosion
    }
}