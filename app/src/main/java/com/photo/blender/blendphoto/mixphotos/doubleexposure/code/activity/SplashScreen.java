package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Intro.IntroScreenActivity;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Language.LanguageStartActivity;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SharePrefUtils;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SystemUtil;

public class SplashScreen extends AppCompatActivity {


    @SuppressLint("UseCompatLoadingForColorStateLists")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtil.setLocale(this);
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
            finish();
            return;
        }
        setContentView(R.layout.activity_splash_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView text = findViewById(R.id.text);
        TextView text2 = findViewById(R.id.text2);
        setTextColor(text,getResources().getColor(R.color.color_sp),getResources().getColor(R.color.color_s1));
        setTextColor(text2,getResources().getColor(R.color.color_sp),getResources().getColor(R.color.color_s1));

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    startAct();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();
    }

    private void setTextColor(TextView text, int...color) {
        TextPaint textPaint = text.getPaint();
        float with = textPaint.measureText(text.getText().toString());
        Shader shader = new LinearGradient(0,0,with,text.getTextSize(),color,null,Shader.TileMode.CLAMP);
        text.getPaint().setShader(shader);
        text.setTextColor(color[0]);
    }
    public void startAct() {
        if (SharePrefUtils.getCountOpenFirstHelp(this) == 0) {
            startActivity(new Intent(this, LanguageStartActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, IntroScreenActivity.class));
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        return;
    }
}