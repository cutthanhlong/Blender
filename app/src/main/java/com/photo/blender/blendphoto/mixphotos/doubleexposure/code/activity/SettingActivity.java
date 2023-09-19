package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Language.LanguageActivity;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SharePrefUtils;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SomeThingApp;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SystemUtil;

public class SettingActivity extends AppCompatActivity {
    ImageView back;
    public  static TextView done,tv_text_language;
    TextView tv_toolbar;
    public static LinearLayout ll_1,ll_2,ll_3,ll_4,ll_5;
    public static View v2;


    private boolean isShareClicked = false;
    private boolean isClick = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtil.setLocale(this);
        setContentView(R.layout.activity_setting);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mapping();
        view();

        if (SharePrefUtils.isRated(this)) {
            ll_2.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
        tv_toolbar.setText(getText(R.string.setting));
//        tv_toolbar.setTextColor(Color.parseColor("#313123123"));
        done.setVisibility(View.GONE);
        String codeLang = SystemUtil.getPreLanguage(getBaseContext());
        switch (codeLang) {
            case "en":
                tv_text_language.setText("English");
                break;
            case "pt":
                tv_text_language.setText("Portuguese");
                break;
            case "es":
                tv_text_language.setText("Spanish");
                break;
            case "de":
                tv_text_language.setText("German");
                break;
            case "fr":
                tv_text_language.setText("French");
                break;
            case "zh":
                tv_text_language.setText("China");
                break;
            case "hi":
                tv_text_language.setText("Hindi");
                break;
            case "in":
                tv_text_language.setText("Indonesia");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShareClicked = false;
        isClick = false;
    }

    private void view() {
        ll_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isShareClicked){
                    startActivity(new Intent(SettingActivity.this, AboutActivity.class));
                    isShareClicked = true;
                }
            }
        });
        ll_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isShareClicked){
                    startActivity(new Intent(SettingActivity.this, LanguageActivity.class));
                    isShareClicked = true;
                }
            }
        });
        ll_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isClick){
                    if (!SharePrefUtils.isRated(SettingActivity.this)) {
                        SomeThingApp.rateApp(SettingActivity.this, 0);
                    }
                    isClick = true;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isClick = false;
                    }
                },1000);

            }
        });
        ll_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isShareClicked){
                    SomeThingApp.shareApp(SettingActivity.this);
                    isShareClicked = true;
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
    private void mapping() {
        back = findViewById(R.id.back);
        done = findViewById(R.id.done_text);
        tv_toolbar = findViewById(R.id.tv_toolbar);
        ll_1 = findViewById(R.id.ll_1);
        ll_2 = findViewById(R.id.ll_2);
        ll_3 = findViewById(R.id.ll_3);
        ll_4 = findViewById(R.id.ll_4);
        ll_5 = findViewById(R.id.ll_5);
        tv_text_language = findViewById(R.id.tv_text_language);
        v2 = findViewById(R.id.v2);
    }
}