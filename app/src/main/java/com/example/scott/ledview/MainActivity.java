package com.example.scott.ledview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {


    private LEDView mLEDView ;

    private boolean mFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLEDView = (LEDView) findViewById(R.id.led_view);
        final View view1 = findViewById(R.id.color_group);
        final View view2 = findViewById(R.id.text_size_group);
        final View view3 = findViewById(R.id.led_heght_size_group);
        mLEDView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mFlag){
                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);
                }else {
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);
                }
                mFlag = !mFlag;
            }
        });
    }

    public void onColorClick(View view){
        if(view instanceof RadioButton){
            RadioButton radioButton = (RadioButton) view;
            String color = (String) radioButton.getText();
            mLEDView.setLED(null, Color.parseColor(color),-1,-1);
        }
    }

    public void onTextSizeClick(View view){
        if(view instanceof RadioButton){
            RadioButton radioButton = (RadioButton) view;
            String textSize = (String) radioButton.getText();
            mLEDView.setLED(null, mLEDView.getLEDColor(),Integer.valueOf(textSize),-1);
        }
    }

    public void onLEDSizeClick(View view){
        if(view instanceof RadioButton){
            RadioButton radioButton = (RadioButton) view;
            String LEDSize = (String) radioButton.getText();
            mLEDView.setLED(null, mLEDView.getLEDColor(),-1,Integer.valueOf(LEDSize));
        }
    }
}
