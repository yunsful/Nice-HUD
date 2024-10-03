package Enemy;

import java.awt.Color;

import engine.DrawManager.SpriteType;
import entity.Entity;

public class Item extends Entity {
    private int speed;
    public Item(final int positionX, final int positionY, final int speed, final int type) {
        super(positionX, positionY, 3 * 2, 5 * 2, Color.yellow);
        this.speed = speed;
        setSprite();
    }

    public final void setSprite() {
        double rdItem = Math.random();

        if (rdItem < 0) { // 0% - Fixing error
            this.spriteType = SpriteType.ItemBomb;
            this.setColor(Color.gray);
        } else if (rdItem < 0) { // 0% - Fixing error
            this.spriteType = SpriteType.ItemBomb;
            this.setColor(Color.black);
        } else if (rdItem < 0.50) { // 50%
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
