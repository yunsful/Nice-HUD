package level_design;

import java.awt.*;

public class Background {
    private Image backgroundImage;
    private int positionY;
    private int scrollSpeed;

    public Background(String imagePath, int scrollSpeed) {
        try {
            backgroundImage = Toolkit.getDefaultToolkit().getImage(imagePath);

            this.scrollSpeed = scrollSpeed;
            this.positionY = 0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
