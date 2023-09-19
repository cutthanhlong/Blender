package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SystemUtil;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.tools.Constants;


import java.io.File;
import java.io.IOException;



public class SaveActivity extends AppCompatActivity {

    private ImageView mImageView;
    Bitmap mBitmap;
    Uri myUri;

    RelativeLayout fbll, install, whatll, sharell;
    String path;

    ImageView back,save,share;
    ProgressDialog dialog;
    TextView tvTitleToolbar;
    private boolean isShare = false;

    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtil.setLocale(this);
        setContentView(R.layout.save_activity);
        Toast.makeText(this, ""+getText(R.string.photo_save), Toast.LENGTH_SHORT).show();
        save = (ImageView) findViewById(R.id.save);
        back = (ImageView) findViewById(R.id.back);
        share = (ImageView) findViewById(R.id.share);
        tvTitleToolbar = (TextView) findViewById(R.id.tv_toolbar);

        mImageView = (ImageView) findViewById(R.id.mainImageView);
        Intent in = getIntent();
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inPreferredConfig = Bitmap.Config.ARGB_8888;
        path = in.getStringExtra("path");

        tvTitleToolbar.setText(R.string.save_ac);
        mBitmap = BitmapFactory.decodeFile(path);

        try {
            mBitmap = Constants.getBitmapFromUri(this, Uri.parse("file://" + path), mBitmap.getWidth(), mBitmap.getWidth());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mImageView.setImageURI(Uri.parse(path));

//        Glide.with(SaveActivity.this).load(path).into(mImageView);
        myUri = Uri.parse(path);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImageBtnClicked();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isShare){
                    shareFileImage(myUri.toString());
                    isShare = true;
                }
            }
        });





    }

    @Override
    protected void onResume() {
        super.onResume();
        isShare = false;
    }

    public void saveImageBtnClicked() {

        finish();
    }


    public void backk() {
        File fdelete = new File(myUri.getPath());
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + myUri.getPath());
            } else {
                System.out.println("file not Deleted :" + myUri.getPath());
            }
        }
        finish();

    }
    String uri1 = "";

    private void shareFileImage(String path) {
        Log.d("TAG", "shareFileVideo: " + path);
        File file;
        if (path.contains("content://com."))
            intentFile(new File(Uri.parse(path).getPath()));
        else if (path.contains("content://")) {
            uri1 = "file://" + path;
            file = new File(Uri.parse(uri1).getPath());
            intentFile(file);
        } else {
            uri1 = "file://" + path;
            file = new File(Uri.parse(uri1).getPath());
            intentFile(file);
        }
    }

    private void intentFile(File file) {
        String packageName = getApplicationContext().getPackageName();

        if (file.exists()) {
            Uri _uri = FileProvider.getUriForFile(this,
                    packageName + ".fileprovider", file);
            Intent intent2 = new Intent(Intent.ACTION_SEND);
            intent2.putExtra("android.intent.extra.SUBJECT", getResources().getString(R.string.app_name));
            intent2.setType("video/*");
            intent2.putExtra("android.intent.extra.STREAM", _uri);
            intent2.putExtra("android.intent.extra.TEXT", "Image");
            startActivity(Intent.createChooser(intent2, "Where to Share?"));
        }
    }
}
