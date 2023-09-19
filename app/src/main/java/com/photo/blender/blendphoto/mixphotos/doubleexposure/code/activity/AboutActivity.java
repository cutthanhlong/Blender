package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.BuildConfig;

import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SystemUtil;

public class AboutActivity extends AppCompatActivity {
    ImageView back;
    TextView tv_toolbar,done,version,policy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtil.setLocale(this);
        setContentView(R.layout.activity_about);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        back = (ImageView) findViewById(R.id.back);
        done = (TextView) findViewById(R.id.done_text);
        tv_toolbar = (TextView) findViewById(R.id.tv_toolbar);
        version = (TextView) findViewById(R.id.version);
        policy = (TextView) findViewById(R.id.policy);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        version.setText(getResources().getString(R.string.version)+" "+ BuildConfig.VERSION_NAME);
        tv_toolbar.setText(R.string.about);
        done.setVisibility(View.GONE);
        TextView text = findViewById(R.id.text);
        TextView text2 = findViewById(R.id.text2);
        setTextColor(text,getResources().getColor(R.color.color_sp),getResources().getColor(R.color.color_s1));
        setTextColor(text2,getResources().getColor(R.color.color_sp),getResources().getColor(R.color.color_s1));
        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://firebasestorage.googleapis.com/v0/b/photo-blender---blend-photo.appspot.com/o/Privacy%20Policy.html?alt=media&token=d6c5aa3d-a41e-406b-9a65-3d7a827bc8aa"));
                startActivity(intent);
            }
        });
    }
    private void setTextColor(TextView text, int...color) {
        TextPaint textPaint = text.getPaint();
        float with = textPaint.measureText(text.getText().toString());
        Shader shader = new LinearGradient(0,0,with,text.getTextSize(),color,null,Shader.TileMode.CLAMP);
        text.getPaint().setShader(shader);
        text.setTextColor(color[0]);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}