package Sound_Operator;

import javax.sound.sampled.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SoundManager {
    private static SoundManager instance;
    static Map<String, String[]> EffectSounds;
    static Map<String, Clip> BGMs;
    static String[][] ESFiles;
    static String[][] BGMFiles;
/**
* Code Description
* Base: BGM files are stored in res/sound/BGM
*       ES files are stored in res/sound/ES, and should be specified in res/ES file in the format: [Type];[Alias];[File Name];[Volume]
        *         -> Type: bgm, es
*         -> Volume: A value between -80.0 and 6.0
        * Usage
* Manager Call: Use getInstance() to call the manager
* BGM Call: Use playBGM(String fileName) to play BGM. It will loop indefinitely, and you can stop it by calling stopBGM()
* ES Call: Use playES(String effectName) to play sound effects. The same ES can be called simultaneously.
* Change BGM Volume: Use modifyBGMVolume(String name, float volume) to modify the volume
* Change ES Volume: Use modifyESVolume(String name, float volume) to modify the volume
*
* All the above functionalities have been implemented and tested successfully.
*/


//  test code
//    public static void main(String[] args) throws IOException, InterruptedException {
//        SoundManager sm = SoundManager.getInstance();
//        sm.playBGM("test");
//        Thread.sleep(10000);
//    }

    private SoundManager() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("res/sound"));
            int ESFileCount = Objects.requireNonNull((new File("res/Sound.assets/ES")).listFiles()).length;
            int BGMFileCount = Objects.requireNonNull((new File("res/Sound.assets/BGM")).listFiles()).length;

            EffectSounds = new HashMap<String, String[]>(ESFileCount);
            BGMs = new HashMap<String, Clip>(BGMFileCount);
            ESFiles = new String[ESFileCount][3];
            BGMFiles = new String[BGMFileCount][3];

            int idx = 0;
            int idy = 0;
            String line;

            while ((line = br.readLine()) != null) {
                // 세미콜론(;)로 구분된 데이터를 파싱
                String[] data = line.split(";");
                if(data[0].equals("es")){
                    ESFiles[idx][0] = data[1];
                    ESFiles[idx][1] = data[2];
                    ESFiles[idx][2] = data[3];
                    this.presetEffectSound(ESFiles[idx][0], "res/Sound.assets/ES/"+ESFiles[idx][1], Float.parseFloat(ESFiles[idx][2]));
                    idx += 1;
                }else if(data[0].equals("bgm")){
                    BGMFiles[idy][0] = data[1];
                    BGMFiles[idy][1] = data[2];
                    BGMFiles[idy][2] = data[3];
                    this.preloadBGM(BGMFiles[idy][0], "res/Sound.assets/BGM/"+BGMFiles[idy][1], Float.parseFloat(BGMFiles[idy][2]));
                    idy += 1;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void stopAllBGM() {
        for (Clip c : BGMs.values()) {
            c.stop();
        }
    }

    public void preloadBGM(String name, String filePath, float volume){
        try {
            if (!BGMs.containsKey(name)) {
                File soundFile = new File(filePath);
                System.out.println(soundFile.getName()+" is loading");
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

//                볼륨 조절
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue(volume);

//                해쉬멥에 추가
                BGMs.put(name, clip); // 미리 로드하여 맵에 저장
                System.out.println(soundFile.getName()+" load complete");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public int playPreloadedBGM(String name){
        Clip clip = BGMs.get(name);
        if(clip != null){
            clip.setFramePosition(0);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            return 1;
        }else{
            return 0;
        }
    }

    public void presetEffectSound(String name, String filePath, float volume) {
        try {
            if (!EffectSounds.containsKey(name)) {
                String[] tmp = {filePath, String.valueOf(volume)};
                EffectSounds.put(name, tmp);
                System.out.println(name+" is set");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int playEffectSound(String name) {
        try {
            if (EffectSounds.containsKey(name)) {
                String[] tmp = EffectSounds.get(name);
                File soundFile = new File(tmp[0]);
                System.out.println(soundFile.getName() + " is loading");
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

//                볼륨 조절
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue(Float.parseFloat(tmp[1]));

                clip.start();
                clip.addLineListener(event -> {
                            if(event.getType() == LineEvent.Type.STOP){
                                clip.close();
                            }
                        }
                );
                System.out.println(soundFile.getName() + " load complete");
                return 1;
            }
            System.out.println("there is no ES : " + name);
            return 0;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int playBGM(String name){
        try {
            stopAllBGM();
            new Thread(() -> playPreloadedBGM(name)).start();
            return 1;
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }

    public int playES(String name){
        try {
            new Thread(() -> playEffectSound(name)).start();
            return 1;
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }

    public int modifyBGMVolume(String name, float volume){
        if(volume > 2 || volume < -60){
            System.out.println("Error : volume is out of index!!!!!");
            System.out.println("input volume : "+ volume);
            return 0;
        }
        if(BGMs.containsKey(name)){
            Clip clip = BGMs.get(name);
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
            return 1;
        }
        return 0;
    }

    public int modifyESVolume(String name, float volume){
        if(volume > 2 || volume < -60){
            System.out.println("Error : volume is out of index!!!!!");
            System.out.println("input volume : "+ volume);
            return 0;
        }
        if(EffectSounds.containsKey(name)){
            EffectSounds.get(name)[1] = String.valueOf(volume);
            return 1;
        }else{
            return 0;
        }
    }
}