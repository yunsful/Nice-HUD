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
    /** Checks if the obstacle has been destroyed by a bullet. */
    private boolean isDestroyed;

    /**
     * Constructor, establishes the obstacle's properties.
     * 
     * @param positionX Initial position of the obstacle in the X axis.
     * @param positionY Initial position of the obstacle in the Y axis.
     */
    public Obstacle(final int positionX, final int positionY) {
        super(positionX, positionY, 60 * 2, 7 * 2, Color.LIGHT_GRAY); // Use different size/color than EnemyShip
        this.spriteType = SpriteType.EnemyShipSpecial; // Can change this to a new sprite type if available
        this.isDestroyed = false;
        this.movementCooldown = Core.getCooldown(500); // You can adjust the cooldown for obstacle movement
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
    public final void update() {
        if (this.movementCooldown.checkFinished()) {
            this.movementCooldown.reset();
            // Add random movement here, or simple vertical movement like:
            this.positionY += 15; // Change obstacle Speed
        }
    }

    /**
     * Destroys the obstacle, causing it to disappear.
     */
    public final void destroy() {
        this.isDestroyed = true;
        this.spriteType = SpriteType.Explosion;
    }

    /**
     * Checks if the obstacle has been destroyed.
     * 
     * @return True if the obstacle has been destroyed.
     */
    public final boolean isDestroyed() {
        return this.isDestroyed;
    }
}
