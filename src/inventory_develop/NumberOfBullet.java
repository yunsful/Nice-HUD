package inventory_develop;

import Enemy.PiercingBullet;
import Enemy.PiercingBulletPool;

import java.util.HashSet;
import java.util.Set;

/**
 * TwoBulletPool extends BulletPool to manage firing two bullets at once.
 */
public class NumberOfBullet{

    /** Offset to ensure bullets don't overlap when fired together. */
    private static final int OFFSET_X_TWOBULLETS = 15;
    private static final int OFFSET_X_THREEBULLETS = 12;

    /** Bullet levels */
    private static int bulletLevel = 3;

    /**
     * Constructor
     */
    public NumberOfBullet() {
    }

    /**
     *
     * @return
     */
    public Set<PiercingBullet> addBullet(int positionX, int positionY, int speed) {
        Set<PiercingBullet> bullets = new HashSet<>();

        switch (bulletLevel) {
            case 1:
                bullets.add(PiercingBulletPool.getPiercingBullet(positionX, positionY, speed,2));
                break;
            case 2:
                bullets.add(PiercingBulletPool.getPiercingBullet(positionX - OFFSET_X_TWOBULLETS, positionY, speed,2));
                bullets.add(PiercingBulletPool.getPiercingBullet(positionX + OFFSET_X_TWOBULLETS, positionY, speed,2));
                break;
            case 3:
                bullets.add(PiercingBulletPool.getPiercingBullet(positionX + OFFSET_X_THREEBULLETS, positionY, speed,2));
                bullets.add(PiercingBulletPool.getPiercingBullet(positionX, positionY, speed,2));
                bullets.add(PiercingBulletPool.getPiercingBullet(positionX - OFFSET_X_THREEBULLETS, positionY, speed,2));
                break;
        }

        return bullets;

    }

}