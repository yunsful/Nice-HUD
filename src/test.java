import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class test {
    public static void main(String[] args) {
        try {
            File mp3File = new File("res/Sound.assets/BGMpixelated-drive-228439.mp3");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(mp3File);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
