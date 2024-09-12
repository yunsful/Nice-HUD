package engine;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;

public class SoundManager {
    private static SoundManager instance;
    private Clip bgmClip;
    private Map<String, Clip> soundEffects = new HashMap<>();
    static String[][] ESFiles;
/**
 * 코드 설명
 * Base : bgm 파일은 res/sound/BGM 에 저장
 *        ES 파일은 res/sound/ES 에 저장, res/ES 파일에 [효과명];[파일이름] 으로 명시 필요
 * 사용법
 * 매니저 호출 : getInstance()
 * BGM 호출 : playBGM(String 파일이름) 호출하여 사용, 무한 루프 이며 끝내려면 stopBGM() 호출
 * ES 호출 : playPreloadedEffectSound(String 효과명) 호출 하여 사용, 시간차로 동일 ES 사용 가능(확인필요)
 * 볼륨 조절기능 UI 아직 안만들어져서 간단하게만 추가해놓음(아직 사용 안하는걸 추천)
 **/
    private SoundManager() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("res/ES"));
            String line;
            ESFiles = new String[(int)br.lines().count()][2];

            br = new BufferedReader(new FileReader("res/ES"));
            int idx = 0;
            while ((line = br.readLine()) != null) {
                // 세미콜론(;)로 구분된 데이터를 파싱
                String[] parts = line.split(";");
                ESFiles[idx][0] = parts[0];
                ESFiles[idx][1] = parts[1];
                idx += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int idx = 0; idx < ESFiles.length; idx++)
            this.preloadEffectSound(ESFiles[idx][0], "res/ES/"+ESFiles[idx][1]);
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void playBGM(String fileName) {
        stopBGM();
        try {
            File bgmFile = new File("res/sound/BGM/"+fileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bgmFile);
            AudioFormat baseFormat = audioStream.getFormat();
            AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    44100,
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    44100,
                    false
            );
            AudioInputStream convertedStream = AudioSystem.getAudioInputStream(targetFormat, audioStream);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(convertedStream);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY); // 반복 재생
            bgmClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }

    public void preloadEffectSound(String name, String filePath) {
        try {
            if (!soundEffects.containsKey(name)) {
                File soundFile = new File(filePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
                AudioFormat baseFormat = audioStream.getFormat();
                AudioFormat targetFormat = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        44100,
                        16,
                        baseFormat.getChannels(),
                        baseFormat.getChannels() * 2,
                        44100,
                        false
                );
                AudioInputStream convertedStream = AudioSystem.getAudioInputStream(targetFormat, audioStream);
                Clip clip = AudioSystem.getClip();
                clip.open(convertedStream);
                soundEffects.put(name, clip); // 미리 로드하여 맵에 저장
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public int playPreloadedEffectSound(String name) {
        Clip clip = soundEffects.get(name);
        if (clip != null) {
            clip.setFramePosition(0); // 재생 위치를 처음으로 설정
            clip.start();
            return 1;
        }else{
            return 0;
        }
    }

    public int modifyEffectSoundVolume(String name, float volume){
        Clip clip = soundEffects.get(name);
        if (clip != null) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
            return 1;
        }else{
            return 0;
        }
    }
}
