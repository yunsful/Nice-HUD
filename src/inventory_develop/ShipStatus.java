package inventory_develop;

import engine.DrawManager;

import java.util.*;
import java.io.*;

public class ShipStatus {

    protected int Speed;
    protected int SHOOTING_INTERVAL;
    protected int BULLET_SPEED;

    Properties properties = new Properties();


    public ShipStatus(final int Speed, final int SHOOTING_INTERVAL,
                      final int BULLET_SPEED) {
        this.Speed = Speed;
        this.SHOOTING_INTERVAL = SHOOTING_INTERVAL;
        this.BULLET_SPEED = BULLET_SPEED;
    }

    public void SetStatus(final int Speed_Level, final int SHOOTING_INTERVAL_Level,
                          final int BULLET_SPEED_Level) {
        this.Speed += Speed_Level - 1;
        this.SHOOTING_INTERVAL -= 100 * SHOOTING_INTERVAL_Level - 100;
        this.BULLET_SPEED -= 2 * BULLET_SPEED_Level - 2;
    }

//    public void loadStatus() throws IOException{
//        InputStream inputStream = null;
//        try{
//            inputStream = this.getClass().getClassLoader()
//                    .getResourceAsStream("Status_increase");
//            properties.load(inputStream);
//            System.out.println(properties.getProperty("Speed.increase"));
//            if (inputStream != null)
//                inputStream.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("FileNotFound");
//        } finally {
//            if (inputStream != null)
//                inputStream.close();
//        }
//    }


    public final int getSpeed(){
        return Speed;
    }
    public final int getSHOOTING_INTERVAL(){
        return SHOOTING_INTERVAL;
    }
    public final int getBULLET_SPEED(){
        return BULLET_SPEED;
    }
}
