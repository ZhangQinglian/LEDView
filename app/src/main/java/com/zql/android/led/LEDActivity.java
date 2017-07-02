package com.zql.android.led;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.thebluealliance.spectrum.SpectrumDialog;
import com.zql.android.led.data.LEDEntity;

import java.util.List;
import java.util.concurrent.Executors;

public class LEDActivity extends AppCompatActivity {
    private PowerManager.WakeLock mWakeLock;

    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchLEDData();
        PowerManager pm = (PowerManager)getSystemService(
                Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK,
                "led");
        mWakeLock.acquire();

        final View decorView = getWindow().getDecorView();
        final int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                if ((i & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            decorView.setSystemUiVisibility(uiOptions);
                        }
                    },1000);

                } else {
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWakeLock.release();
    }

    private void fetchLEDData(){

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                LEDService.own().initFirstLED();
                final List<LEDEntity> ledEntities = LEDService.own().getLEDData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LEDView ledView = (LEDView) findViewById(R.id.led_view);
                        LEDEntity entity = ledEntities.get(0);
                        ledView.setLED(entity.content,entity.textColor,entity.textSize,entity.ledSize);
                    }
                });
            }
        });

    }
}
