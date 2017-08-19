package com.zql.android.led.data;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.thebluealliance.spectrum.SpectrumDialog;
import com.zql.android.led.LEDService;
import com.zql.android.led.LEDView;
import com.zql.android.led.R;


/**
 * Created by scott on 2017/6/30.
 */

public class LEDDashboard extends CardView {

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

    public void init(final LEDEntity entity, final AppCompatActivity activity){
        this.entity = entity;
        this.activity = activity;
        ledView = findViewById(R.id.led_view);

        findViewById(R.id.btn_change_led_content).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText textView = new EditText(getContext());
                textView.setText(entity.content);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(layoutParams);
                textView.setGravity(Gravity.CENTER);
                textView.setHint(R.string.input_hint);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.modity_led_content)
                        .setCancelable(true)
                        .setPositiveButton(R.string.comm_ok, new DialogInterface.OnClickListener() {
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
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            int[][] status = {{}};
            int[] colors = {entity.textColor};
            ColorStateList colorStateList = new ColorStateList(status,colors);
            changeTextColor.setBackgroundTintList(colorStateList);
        }
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
                                    int[] colors = {color};
                                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                                        int[][] status = {{}};
                                        ColorStateList colorStateList = new ColorStateList(status,colors);
                                        changeTextColor.setBackgroundTintList(colorStateList);
                                    }
                                    LEDService.own().update_A(entity);
                                }
                            }
                        }).setDismissOnColorSelected(true)
                        .setSelectedColor(entity.textColor).setTitle(R.string.choose_color)
                        .build().show(activity.getSupportFragmentManager(),"color-picker");
            }
        });

        View changeLedSize = findViewById(R.id.btn_change_led_size);
        changeLedSize.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] size = new String[]{"10","20","30","40","50","60"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.modify_led_pixel)
                        .setItems(size, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String s = size[i];
                                int int_s = Integer.parseInt(s);
                                entity.ledSize = int_s;
                                ledView.setLEDSize(int_s);
                                LEDService.own().update_A(entity);
                            }
                        })
                        .setCancelable(true)
                        .show();
            }
        });


        View changeLedTextSize = findViewById(R.id.btn_change_led_text_size);
        changeLedTextSize.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] size = new String[]{"10","20","30","40","50","60"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.modify_led_text_size)
                        .setItems(size, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String s = size[i];
                                int int_s = Integer.parseInt(s);
                                entity.textSize = int_s;
                                ledView.setLEDTextSize(int_s);
                                LEDService.own().update_A(entity);
                            }
                        })
                        .setCancelable(true)
                        .show();
            }
        });
    }
}
