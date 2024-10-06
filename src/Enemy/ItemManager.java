package Enemy;

import engine.Core;
import entity.EnemyShip;
import entity.Ship;
import screen.GameScreen;
import engine.DrawManager;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;


public class ItemManager {

    public Set<Item> items;
    private int screenHeight;
    private DrawManager drawManager;
    private GameScreen gameScreen;
    protected Logger logger = Core.getLogger();
    private Set<Item> recyclableItems = new HashSet<>();

    public ItemManager(int screenHeight, DrawManager drawManager) {
        this.items = new HashSet<>();
        this.screenHeight = screenHeight;
        this.drawManager = drawManager;
    }

    public void cleanItems() {
        Set<Item> recyclable = new HashSet<>();
        for (Item item : this.items) {
            item.update();
            if (item.getPositionY() > screenHeight) {
                recyclable.add(item);
            }
        }
        this.items.removeAll(recyclable);
        ItemPool.recycle(recyclable);
    }

    public void initialize() {
        this.items = new HashSet<>();
    }

    public void drawItems() {
        for (Item item : this.items) {
            drawManager.drawEntity(item, item.getPositionX(), item.getPositionY());
        }
    }

    public void dropItem(EnemyShip enemyShip, double probability, int enemyship_type) {
        if(Math.random() < probability) {
            Item item = ItemPool.getBullet(enemyShip.getPositionX(), enemyShip.getPositionY(), 5, enemyship_type);
            this.items.add(item);
        }
    }
    public void addItemRecycle(Item item) {
        if(item != null) {
            recyclableItems.add(item);
            this.logger.info("get item");
        }
    }
    public void removeAllReItems(){
        this.items.removeAll(recyclableItems);
        ItemPool.recycle(recyclableItems);
    }
}
