package entity;

import java.awt.Color;
import java.util.Set;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;

/**
 * Implements a ship, to be controlled by the player. 플레이어가 실제로 조종하는 함선 클래스
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class Ship extends Entity {

    /** Time between shots. */
    /** 발사간격(밀리초 단위) */
    private static final int SHOOTING_INTERVAL = 750;
    /** Speed of the bullets shot by the ship. */
    /** 함선에서 발사된 총알 속도 */
    private static final int BULLET_SPEED = -6;
    /** Movement of the ship for each unit of time. */
    /** 단위 시간당 함선 이동 간격(함선속도) */
    private static final int SPEED = 2;

    /** Minimum time between shots. */
    /** 발사간격 저장 변수 */
    private Cooldown shootingCooldown;
    /** Time spent inactive between hits. */
    /** 피격 시 비활성화되는 시간 저장 변수 */
    private Cooldown destructionCooldown;

    /**
     * Constructor, establishes the ship's properties.
     * 생성자, 함선의 초기 위치, 크기, 색깔 세팅
     *
     * @param positionX
     *            Initial position of the ship in the X axis.
     *            생성자에 함선의 초기 x축 죄표 전달
     * @param positionY
     *            Initial position of the ship in the Y axis.
     *            생성자에 함선의 초기 y축 죄표 전달
     */
    public Ship(final int positionX, final int positionY) {
        super(positionX, positionY, 13 * 2, 8 * 2, Color.GREEN);

        this.spriteType = SpriteType.Ship; // 객체의 함선 타입을 아군 함선으로 지정
        this.shootingCooldown = Core.getCooldown(SHOOTING_INTERVAL); // 함선 발사간격 지정
        this.destructionCooldown = Core.getCooldown(1000); // 피격 시 비활성화 시간 1초 지정
    }

    /**
     * Moves the ship speed units right, or until the right screen border is
     * reached.
     * 함선 속도만큼 오른쪽 이동
     */
    public final void moveRight() {
        this.positionX += SPEED;
    }

    /**
     * Moves the ship speed units left, or until the left screen border is
     * reached.
     * 함선 속도만큼 왼쪽 이동
     */
    public final void moveLeft() {
        this.positionX -= SPEED;
    }

    /**
     * Shoots a bullet upwards.
     *
     * @param bullets
     *            List of bullets on screen, to add the new bullet.
     * @return Checks if the bullet was shot correctly.
     */
    public final boolean shoot(final Set<Bullet> bullets) {
        if (this.shootingCooldown.checkFinished()) { // 만약 발사 쿨타임 돌았으면
            this.shootingCooldown.reset(); // 쿨타임 재설정
            bullets.add(BulletPool.getBullet(positionX + this.width / 2,
                    positionY, BULLET_SPEED)); // 화면상 총알 리스트에 현재 발사된 총알 추가
            return true; // 쿨타임 돌았으면 true 리턴
        }
        return false; // 쿨타임 안돌았으면 false 리턴
    }

    /**
     * Updates status of the ship.
     */
    public final void update() {
        if (!this.destructionCooldown.checkFinished()) // 함선이 비활성화 상태면 함선을 파괴된 함선으로 변경
            this.spriteType = SpriteType.ShipDestroyed;
        else
            this.spriteType = SpriteType.Ship; // 함선이 활성화 상태면 아군 함선 상태로 유지
    }

    /**
     * Switches the ship to its destroyed state.
     */
    public final void destroy() {
        this.destructionCooldown.reset(); // 함선 비활성화 시간 재설정
    }

    /**
     * Checks if the ship is destroyed.
     *
     * @return True if the ship is currently destroyed.
     */
    public final boolean isDestroyed() {
        return !this.destructionCooldown.checkFinished(); // 함선이 파괴되었는지 여부 반환
    }

    /**
     * Getter for the ship's speed.
     *
     * @return Speed of the ship.
     */
    public final int getSpeed() {
        return SPEED; // 함선 속도 반환
    }
}
