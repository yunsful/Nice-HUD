package inventory_develop;

import engine.DrawManager;

import java.util.*;
import java.io.*;

public class ShipStatus {

    protected int Speed;
    protected int SHOOTING_INTERVAL;
    protected int BULLET_SPEED;
    protected int Speed_Level;
    protected int SHOOTING_INTERVAL_Level;
    protected int BULLET_SPEED_Level;

    Properties properties = new Properties();


    public ShipStatus(final int Speed_Level, final int SHOOTING_INTERVAL_Level,
                      final int BULLET_SPEED_Level) {
        this.Speed_Level = Speed_Level;
        this.SHOOTING_INTERVAL_Level = SHOOTING_INTERVAL_Level;
        this.BULLET_SPEED_Level = BULLET_SPEED_Level;

    }

    public void loadStatus() throws IOException{
        InputStream inputStream = null;
        try{
            inputStream = this.getClass().getClassLoader()
                    .getResourceAsStream("Status_increase");
            properties.load(inputStream);
            System.out.println(properties.getProperty("Speed.increase"));
            if (inputStream != null)
                inputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound");
        } finally {
            if (inputStream != null)
                inputStream.close();
        }
    }


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
