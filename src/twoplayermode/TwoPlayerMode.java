package twoplayermode;

import entity.Ship;
import java.awt.event.KeyEvent;
import java.util.Set;
import Enemy.PiercingBullet;
import java.awt.Graphics2D;

public class TwoPlayerMode {

    private PlayerShip player1;
    private PlayerShip player2;
    private Set<PiercingBullet> bullets;  // PiercingBullet 목록 관리

    // 생성자에서 두 명의 플레이어를 초기화
    public TwoPlayerMode() {
        player1 = new PlayerShip(50, 400, "resources/player1.png");  // 1P
        player2 = new PlayerShip(250, 400, "resources/player2.png");  // 2P
        // bullets도 초기화 필요
    }

    // 키 입력 처리
    public void handleKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player1.moveLeft();
                break;
            case KeyEvent.VK_D:
                player1.moveRight();
                break;
            case KeyEvent.VK_LEFT:
                player2.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                player2.moveRight();
                break;
            case KeyEvent.VK_W:
                player1.shoot(bullets);
                break;
            case KeyEvent.VK_UP:
                player2.shoot(bullets);
                break;
        }
    }

    // 두 플레이어 상태 업데이트
    public void updateGame() {
        player1.update();
        player2.update();
        // 게임 종료 또는 충돌 처리 등의 추가 로직 구현
    }

    // 두 플레이어의 렌더링
    public void renderPlayers(Graphics2D g) {
        player1.render(g);
        player2.render(g);
    }
}
