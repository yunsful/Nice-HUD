package clove;

import engine.Core;
import java.io.*;
import java.util.logging.Logger;

public class Record {

    private static Logger logger;
    private String userName;

    public Record(String userName) {
        this.userName = userName;
    }
    private Record() {
        logger = Core.getLogger();
    }

    public void saveFile(String userName, int killCount, int Level, int highScore) throws IOException {
        String filePath = userName + "_data.txt";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(Integer.toString(killCount));
            bw.newLine();
            bw.write(Integer.toString(Level));
            bw.newLine();
            bw.write(Integer.toString(highScore));
            bw.newLine();
        } catch (IOException e) {
            logger.info("Failed to save the file");
        }
    }
    /*
    첫 번째 줄 : killCount
    두 번째 줄 : Level
    세 번째 줄 : highScore
    순서로 userData 파일 저장되도록 구성
     */

    public userData loadUserData(String userName) throws IOException { // userData가 입력된 파일 불러오는 함수
        String filePath = userName + "_data.txt";
        userData userData = new userData();  // UserData는 사용자 정보를 담는 클래스

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line != null) userData.setKillCount(Integer.parseInt(line)); // killCount

            line = reader.readLine();
            if (line != null) userData.setHighScore(Integer.parseInt(line));     // score

            line = reader.readLine();
            if (line != null) userData.setLevel(Integer.parseInt(line));     // level
        } catch (FileNotFoundException e) {         //userName에 대한 파일이 존재하지 않을 때,
            logger.info("Data file not found for user " + userName + ", initializing default values.");
            userData = new userData("Default",0, 0, 1);  // 초기값 설정
        } catch (IOException e) {                   //IOException이 발생했을 때,
            logger.severe("Error loading data for user " + userName);
            throw e;
        }

        return userData;
    }

    //Can be adjusted by the FileManager class
}