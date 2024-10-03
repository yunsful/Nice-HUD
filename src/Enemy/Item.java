package Enemy;

import java.awt.Color;

import engine.DrawManager.SpriteType;
import entity.Entity;
import inventory_develop.ShipStatus;

public class Item extends Entity {
    private final ShipStatus shipStatus = new ShipStatus();
    private int speed;
    public Item(final int positionX, final int positionY, final int speed, final int type) {
        super(positionX, positionY, 3 * 2, 5 * 2, Color.yellow);
        this.speed = speed;
        setSprite();
    }

    public final void setSprite() {
        double rdItem = Math.random();
        shipStatus.loadProbability();

        // Import odds from properties file for easy balance patches
        if (rdItem < shipStatus.getBomb_probability()) { // 0% - Fixing error
            this.spriteType = SpriteType.ItemBomb;
            this.setColor(Color.gray);
        } else if (rdItem < shipStatus.getPierce_probability()) { // 0% - Fixing error
            this.spriteType = SpriteType.ItemBomb;
            this.setColor(Color.black);
        } else if (rdItem < shipStatus.getShield_probability()) { // 50%
            this.spriteType = SpriteType.ItemBarrier;
            this.setColor(Color.green);
        } else { // 50%
            this.spriteType = SpriteType.ItemHeart;
            this.setColor(Color.red);
        }
    }

    public final void update() {
        this.positionY += this.speed;
    }
    public final void setSpeed(final int speed) {
        this.speed = speed;
    }
    public final int getSpeed() {
        return this.speed;
    }
}
