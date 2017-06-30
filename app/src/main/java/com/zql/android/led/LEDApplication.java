package com.zql.android.led;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.zql.android.led.data.LEDDatabase;

/**
 * Created by scott on 2017/6/30.
 */

public class LEDApplication extends Application {

    private static LEDApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        if(sInstance == null){
            sInstance = this;
        }
        getDatabase();
    }

    public static LEDApplication own(){
        return sInstance;
    }

    public LEDDatabase getDatabase(){
        LEDDatabase database = Room.databaseBuilder(this,LEDDatabase.class,"led-database").build();
        return database;
    }

}
