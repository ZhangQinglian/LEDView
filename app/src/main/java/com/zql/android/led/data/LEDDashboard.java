package com.zql.android.led.data;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thebluealliance.spectrum.SpectrumDialog;
import com.zql.android.led.LEDService;
import com.zql.android.led.LEDView;
import com.zql.android.led.R;

import java.util.concurrent.Executors;

/**
 * Created by scott on 2017/6/30.
 */

public class LEDDashboard extends CardView {

    private int position ;
    private LEDView ledView;
    private LEDEntity entity;
    private AppCompatActivity activity;

    public LEDDashboard(Context context) {
        super(context);
    }

    public LEDDashboard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LEDDashboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(int listPosition, final LEDEntity entity, final AppCompatActivity activity){
        this.position = listPosition;
        this.entity = entity;
        this.activity = activity;
        ledView = findViewById(R.id.led_view);

        findViewById(R.id.btn_change_led_content).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText textView = new EditText(getContext());
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(layoutParams);
                textView.setGravity(Gravity.CENTER);


                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("修改LED内容")
                        .setMessage("请输入你要显示的内容")
                        .setCancelable(true)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String content = textView.getText().toString();
                                if(content == null || content.trim().length() == 0) return;
                                ledView.setLEDContent(content);
                                entity.content = content;
                                LEDService.own().update_A(entity);
                            }
                        })
                        .setView(textView)
                        .show();
            }
        });

        final View changeTextColor = findViewById(R.id.btn_change_text_color);
        changeTextColor.setBackgroundColor(entity.textColor);
        changeTextColor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SpectrumDialog.Builder builder = new SpectrumDialog.Builder(getContext());
                builder.setColors(R.array.colors)
                        .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                                if(positiveResult){
                                    ledView.setLEDTextColor(color);
                                    entity.textColor = color;
                                    changeTextColor.setBackgroundColor(color);
                                    LEDService.own().update_A(entity);
                                }
                            }
                        }).setDismissOnColorSelected(true)
                        .setSelectedColor(entity.textColor)
                        .build().show(activity.getSupportFragmentManager(),"color-picker");
            }
        });
    }
}
