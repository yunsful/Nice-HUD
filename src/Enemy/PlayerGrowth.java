package Enemy;

public class PlayerGrowth {

    // 플레이어의 기본 스탯 설정 / Player's base stats
    private int health;          // 체력 / Health
    private int moveSpeed;       // 이동 속도 / Movement speed
    private int bulletSpeed;     // 총알 속도 / Bullet speed
    private int shootingDelay;   // 총알 발사 딜레이 / Shooting delay

    // 생성자: 기본 값을 설정 / Constructor to set initial values
    public PlayerGrowth() {
        // 기본 스탯 설정 / Base stat values
        this.health = 3;          // 기본 체력은 3 / Base health is 3
        this.moveSpeed = 4;       // 기본 이동 속도는 2 / Base movement speed is 2
        this.bulletSpeed = -15;    // 기본 총알 속도는 -6 / Base bullet speed is -6
        this.shootingDelay = 400; // 기본 발사 딜레이는 750ms / Base shooting delay is 750ms
    }

    // 체력 증가 / Increases health
    public void increaseHealth(int increment) {
        this.health += increment;
    }

    // 이동 속도 증가 / Increases movement speed
    public void increaseMoveSpeed(int increment) {
        this.moveSpeed += increment;
    }

    // 총알 속도 증가 / Increases bullet speed (makes bullets faster)
    public void increaseBulletSpeed(int increment) {
        this.bulletSpeed -= increment; // 속도는 음수이므로 증가시킬 때 감소 / Increase by subtracting (since negative speed)
    }

    // 발사 딜레이 감소 / Decreases shooting delay (makes shooting faster)
    public void decreaseShootingDelay(int decrement) {
        this.shootingDelay -= decrement; // 딜레이를 줄임으로써 더 빠르게 발사 / Decrease delay for faster shooting
        if (this.shootingDelay < 100) {
            this.shootingDelay = 100; // 최소 발사 딜레이는 100ms로 설정 / Minimum shooting delay is 100ms
        }
    }

    // 체력 반환 / Returns current health
    public int getHealth() {
        return this.health;
    }

    // 이동 속도 반환 / Returns current movement speed
    public int getMoveSpeed() {
        return this.moveSpeed;
    }

    // 총알 속도 반환 / Returns current bullet speed
    public int getBulletSpeed() {
        return this.bulletSpeed;
    }

    // 발사 딜레이 반환 / Returns current shooting delay
    public int getShootingDelay() {
        return this.shootingDelay;
    }

    // 플레이어 스탯 출력 (디버깅용) / Prints player stats (for debugging)
    public void printStats() {
        System.out.println("체력: " + this.health);
        System.out.println("이동 속도: " + this.moveSpeed);
        System.out.println("총알 속도: " + this.bulletSpeed);
        System.out.println("발사 딜레이: " + this.shootingDelay + "ms");
    }
}
