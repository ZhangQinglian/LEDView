package com.zql.android.led;

import android.content.Intent;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zql.android.led.data.LEDDashboard;
import com.zql.android.led.data.LEDEntity;

import java.util.List;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity {

    private LEDDashboard mLedDashboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        initData();
        //init the first led
    }

    private void initView(){

        mLedDashboard = (LEDDashboard) findViewById(R.id.led_dashboard);
        findViewById(R.id.led_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,LEDActivity.class);
                startActivity(intent);
            }
        });

        LEDView ledView = (LEDView) findViewById(R.id.led_about);
        ledView.setLED("  ~Designed by Qinglian.Zhang. Contact me via qinglian.zhang@outlook.com \uD83D\uDE01",
                getResources().getColor(R.color.color_white),20,50);

    }


    private void initData(){
        fetchLEDData();
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
                        mLedDashboard.setVisibility(View.VISIBLE);
                        LEDView ledView = mLedDashboard.findViewById(R.id.led_view);
                        LEDEntity entity = ledEntities.get(0);
                        ledView.setLED(entity.content,entity.textColor,entity.textSize,entity.ledSize);
                        mLedDashboard.init(ledEntities.get(0),HomeActivity.this);
                    }
                });
            }
        });

    }
}
