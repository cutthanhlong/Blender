package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SharePrefUtils;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SomeThingApp;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SystemUtil;


;import java.util.ArrayList;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity {
    ImageView rateus, share, moreapp;
    int iddd;
    ProgressDialog dialog;

    AppCompatImageView setting;
    Animation bounce;
    Button pip;
    LinearLayout ll_create,ll_my;
    private boolean isselected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtil.setLocale(this);
        setContentView(R.layout.activity_home);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);

        ll_create = (LinearLayout) findViewById(R.id.ll_create);
        setting = (AppCompatImageView) findViewById(R.id.setting);
        ll_my = (LinearLayout) findViewById(R.id.ll_my);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isselected){
                    startActivity(new Intent(HomeActivity.this,SettingActivity.class));
                    isselected = true;
                }
            }
        });
        ll_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isselected){
                    startActivity(new Intent(HomeActivity.this,MyCreationScreenActivity.class));
                    isselected = true;
                }

            }
        });
        ll_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isselected){
                    Intent i = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(i);
                    isselected = true;
                }


            }
        });


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
    @Override
    public void onPause() {
        super.onPause();

    }

    ArrayList<String> remoteRate = new ArrayList<String>(Arrays.asList("2", "4", "6", "8", "10"));
    @Override
    public void onBackPressed() {

        if (!SharePrefUtils.isRated(this)) {
            String cout = String.valueOf(SharePrefUtils.getCountOpenApp(this));
            Log.e("abcdg", "Cout: " + cout);
            if (remoteRate.contains(cout)) {
                SomeThingApp.rateApp(HomeActivity.this, 1);
            } else {
                Dialog();
            }
        } else {
            Dialog();
        }

    }
    private void Dialog(){
        TextView cancel,exit;
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.layout_exit);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        cancel = dialog.findViewById(R.id.cancel);
        exit = dialog.findViewById(R.id.exit);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();

            }
        });
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        isselected = false;
    }


}

