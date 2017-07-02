package com.zql.android.led;

import android.graphics.Color;

import com.zql.android.led.data.LEDDao;
import com.zql.android.led.data.LEDEntity;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by scott on 2017/6/30.
 */

public class LEDService {

    private static LEDService sInstance;

    private LEDDao mLEDDao;

    private LEDService() {
        mLEDDao = LEDApplication.own().getDatabase().getLEDDao();
    }

    public synchronized static LEDService own() {
        if (sInstance == null) {
            sInstance = new LEDService();
        }
        return sInstance;
    }

    public List<LEDEntity> getLEDData() {
        return mLEDDao.getAllLEDEntities();
    }

    public void update(LEDEntity... entities) {
        mLEDDao.updateLEDEntities(entities);
    }

    public void update_A(final LEDEntity... entities) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mLEDDao.updateLEDEntities(entities);
            }
        });
    }

    public void initFirstLED() {
        List<LEDEntity> ledEntities = mLEDDao.getAllLEDEntities();
        if (ledEntities.size() == 0) {
            LEDEntity first = new LEDEntity();
            first.content = "Hello LED \uD83D\uDE00";
            first.bgColor = Color.BLACK;
            first.textColor = Color.RED;
            first.textSize = 30;
            first.ledSize = 40;
            mLEDDao.insertLEDEntities(first);
        }
    }

    public void addLED() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<LEDEntity> ledEntities = mLEDDao.getAllLEDEntities();
                if (ledEntities.size() < 10) {
                    LEDEntity first = new LEDEntity();
                    first.content = "Hello LED \uD83D\uDE00";
                    first.bgColor = Color.BLACK;
                    first.textColor = Color.RED;
                    first.textSize = 30;
                    first.ledSize = 40;
                    mLEDDao.insertLEDEntities(first);
                }
            }
        });
    }
}
