package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.CropImageView.CropMode;
import com.isseiaoki.simplecropview.CropImageView.RotateDegrees;
import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SystemUtil;


public class CropBackgroundActivity extends AppCompatActivity implements OnClickListener {
    public static Bitmap bitmap;
    Bitmap bm;
    private ImageView buttonCancel;
    private ImageView buttonDone;
    private ImageView buttonRotateLeft;
    private ImageView buttonRotateRight;
    private ImageView ll_16_9;
    private ImageView ll_1_1;
    private ImageView ll_3_4;
    private ImageView ll_4_3;
    private ImageView ll_4_6;
    private ImageView ll_6_4;
    private ImageView ll_9_16;
    private ImageView ll_Original;
    private ImageView ll_free;
    private LinearLayout lll_16_9;
    private LinearLayout lll_1_1;
    private LinearLayout lll_3_4;
    private LinearLayout lll_4_3;
    private LinearLayout lll_4_6;
    private LinearLayout lll_6_4;
    private LinearLayout lll_9_16;
    private LinearLayout lll_Original;
    private LinearLayout lll_free;
    CropImageView mCropView;
    private TextView tv_16_9;
    private TextView tv_1_1;
    private TextView tv_3_4;
    private TextView tv_4_3;
    private TextView tv_4_6;
    private TextView tv_6_4;
    private TextView tv_9_16;
    private TextView tv_Original;
    private TextView tv_free;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtil.setLocale(this);
        setContentView(R.layout.activity_crop);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        try {
            bindViews();
            this.bm = BitmapFactory.decodeFile(getIntent().getStringExtra("uri"));
            this.bm = resizeBitmap(this.bm);
            if (this.mCropView.getImageBitmap() == null) {
                this.mCropView.setImageBitmap(this.bm);
            }
        } catch (Exception e) {
        } catch (Error e2) {

        }
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
    Bitmap resizeBitmap(Bitmap bit) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float wr = (float) dm.widthPixels;
        float hr = (float) dm.heightPixels;
        float wd = (float) bit.getWidth();
        float he = (float) bit.getHeight();
        float rat1 = wd / he;
        float rat2 = he / wd;
        if (wd > wr) {
            wd = wr;
            he = wd * rat2;
        } else if (he > hr) {
            he = hr;
            wd = he * rat1;
        } else if (rat1 > 0.75f) {
            wd = wr;
            he = wd * rat2;
        } else if (rat2 > 1.5f) {
            he = hr;
            wd = he * rat1;
        } else {
            wd = wr;
            he = wd * rat2;
        }
        return Bitmap.createScaledBitmap(bit, (int) wd, (int) he, false);
    }

    private void bindViews() {
        this.mCropView = (CropImageView) findViewById(R.id.cropImageView);
        this.lll_free = (LinearLayout) findViewById(R.id.lll_free);
        this.lll_free.setOnClickListener(this);
        this.lll_Original = (LinearLayout) findViewById(R.id.lll_Original);
        this.lll_Original.setOnClickListener(this);
        this.lll_1_1 = (LinearLayout) findViewById(R.id.lll_1_1);
        this.lll_1_1.setOnClickListener(this);
        this.lll_3_4 = (LinearLayout) findViewById(R.id.lll_3_4);
        this.lll_3_4.setOnClickListener(this);
        this.lll_4_3 = (LinearLayout) findViewById(R.id.lll_4_3);
        this.lll_4_3.setOnClickListener(this);
        this.lll_4_6 = (LinearLayout) findViewById(R.id.lll_4_6);
        this.lll_4_6.setOnClickListener(this);
        this.lll_6_4 = (LinearLayout) findViewById(R.id.lll_6_4);
        this.lll_6_4.setOnClickListener(this);
        this.lll_9_16 = (LinearLayout) findViewById(R.id.lll_9_16);
        this.lll_9_16.setOnClickListener(this);
        this.lll_16_9 = (LinearLayout) findViewById(R.id.lll_16_9);
        this.lll_16_9.setOnClickListener(this);
        this.ll_free = (ImageView) findViewById(R.id.ll_free);
        this.ll_Original = (ImageView) findViewById(R.id.ll_Original);
        this.ll_1_1 = (ImageView) findViewById(R.id.ll_1_1);
        this.ll_3_4 = (ImageView) findViewById(R.id.ll_3_4);
        this.ll_4_3 = (ImageView) findViewById(R.id.ll_4_3);
        this.ll_4_6 = (ImageView) findViewById(R.id.ll_4_6);
        this.ll_6_4 = (ImageView) findViewById(R.id.ll_6_4);
        this.ll_9_16 = (ImageView) findViewById(R.id.ll_9_16);
        this.ll_16_9 = (ImageView) findViewById(R.id.ll_16_9);
        this.tv_free = (TextView) findViewById(R.id.tv_free);
        this.tv_Original = (TextView) findViewById(R.id.tv_Original);
        this.tv_1_1 = (TextView) findViewById(R.id.tv_1_1);
        this.tv_3_4 = (TextView) findViewById(R.id.tv_3_4);
        this.tv_4_3 = (TextView) findViewById(R.id.tv_4_3);
        this.tv_4_6 = (TextView) findViewById(R.id.tv_4_6);
        this.tv_6_4 = (TextView) findViewById(R.id.tv_6_4);
        this.tv_9_16 = (TextView) findViewById(R.id.tv_9_16);
        this.tv_16_9 = (TextView) findViewById(R.id.tv_16_9);
        manageColorFilter();
        this.ll_free.setColorFilter(getResources().getColor(R.color.color_app));
        this.tv_free.setTextColor(getResources().getColor(R.color.color_app));
        this.mCropView.setCropMode(CropMode.FREE);
        this.buttonDone = (ImageView) findViewById(R.id.buttonDone);
        this.buttonDone.setOnClickListener(this);
        this.buttonCancel = (ImageView) findViewById(R.id.buttonCancel);
        this.buttonCancel.setOnClickListener(this);
        this.buttonRotateLeft = (ImageView) findViewById(R.id.buttonRotateLeft);
        this.buttonRotateLeft.setOnClickListener(this);
        this.buttonRotateRight = (ImageView) findViewById(R.id.buttonRotateRight);
        this.buttonRotateRight.setOnClickListener(this);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.buttonCancel :
                    finish();
                    return;
                case R.id.buttonDone :
                    bitmap = this.mCropView.getCroppedBitmap();
                    setResult(6);
                    finish();
                    return;
                case R.id.buttonRotateLeft :
                    this.mCropView.rotateImage(RotateDegrees.ROTATE_M90D);
                    return;
                case R.id.buttonRotateRight :
                    this.mCropView.rotateImage(RotateDegrees.ROTATE_90D);
                    return;
                case R.id.lll_16_9 :
                    manageColorFilter();
                    this.ll_16_9.setColorFilter(getResources().getColor(R.color.color_app));
                    this.tv_16_9.setTextColor(getResources().getColor(R.color.color_app));
                    this.mCropView.setCropMode(CropMode.RATIO_16_9);
                    this.ll_free.setImageResource(R.drawable.ic_free_no);
                    this.ll_Original.setImageResource(R.drawable.original);
                    this.ll_1_1.setImageResource(R.drawable.ic__1);
                    this.ll_3_4.setImageResource(R.drawable.ic_3_4);
                    this.ll_4_3.setImageResource(R.drawable.ic_4_3);
                    this.ll_4_6.setImageResource(R.drawable.ic_4_6);
                    this.ll_6_4.setImageResource(R.drawable.ic_6_4);
                    this.ll_16_9.setImageResource(R.drawable._6_9);
                    this.ll_9_16.setImageResource(R.drawable.ic_9_16);
                    return;
                case R.id.lll_1_1 :
                    manageColorFilter();
                    this.ll_1_1.setColorFilter(getResources().getColor(R.color.color_app));
                    this.tv_1_1.setTextColor(getResources().getColor(R.color.color_app));
                    this.mCropView.setCropMode(CropMode.SQUARE);
                    this.ll_free.setImageResource(R.drawable.ic_free_no);
                    this.ll_Original.setImageResource(R.drawable.original);
                    this.ll_1_1.setImageResource(R.drawable.__1);
                    this.ll_3_4.setImageResource(R.drawable.ic_3_4);
                    this.ll_4_3.setImageResource(R.drawable.ic_4_3);
                    this.ll_4_6.setImageResource(R.drawable.ic_4_6);
                    this.ll_6_4.setImageResource(R.drawable.ic_6_4);
                    this.ll_16_9.setImageResource(R.drawable.crop_16_9);
                    this.ll_9_16.setImageResource(R.drawable.ic_9_16);
                    return;
                case R.id.lll_3_4 :
                    manageColorFilter();
                    this.ll_3_4.setColorFilter(getResources().getColor(R.color.color_app));
                    this.tv_3_4.setTextColor(getResources().getColor(R.color.color_app));
                    this.mCropView.setCropMode(CropMode.RATIO_3_4);
                    this.ll_free.setImageResource(R.drawable.ic_free_no);
                    this.ll_Original.setImageResource(R.drawable.original);
                    this.ll_1_1.setImageResource(R.drawable.ic__1);
                    this.ll_3_4.setImageResource(R.drawable.__4);
                    this.ll_4_3.setImageResource(R.drawable.ic_4_3);
                    this.ll_4_6.setImageResource(R.drawable.ic_4_6);
                    this.ll_6_4.setImageResource(R.drawable.ic_6_4);
                    this.ll_16_9.setImageResource(R.drawable.crop_16_9);
                    this.ll_9_16.setImageResource(R.drawable.ic_9_16);
                    return;
                case R.id.lll_4_3 :
                    manageColorFilter();
                    this.ll_4_3.setColorFilter(getResources().getColor(R.color.color_app));
                    this.tv_4_3.setTextColor(getResources().getColor(R.color.color_app));
                    this.mCropView.setCropMode(CropMode.RATIO_4_3);
                    this.ll_free.setImageResource(R.drawable.ic_free_no);
                    this.ll_Original.setImageResource(R.drawable.original);
                    this.ll_1_1.setImageResource(R.drawable.ic__1);
                    this.ll_3_4.setImageResource(R.drawable.ic_3_4);
                    this.ll_4_3.setImageResource(R.drawable.__3);
                    this.ll_4_6.setImageResource(R.drawable.ic_4_6);
                    this.ll_6_4.setImageResource(R.drawable.ic_6_4);
                    this.ll_16_9.setImageResource(R.drawable.crop_16_9);
                    this.ll_9_16.setImageResource(R.drawable.ic_9_16);
                    return;
                case R.id.lll_4_6 :
                    manageColorFilter();
                    this.ll_4_6.setColorFilter(getResources().getColor(R.color.color_app));
                    this.tv_4_6.setTextColor(getResources().getColor(R.color.color_app));
                    this.mCropView.setCustomRatio(4, 6);
                    this.ll_free.setImageResource(R.drawable.ic_free_no);
                    this.ll_Original.setImageResource(R.drawable.original);
                    this.ll_1_1.setImageResource(R.drawable.ic__1);
                    this.ll_3_4.setImageResource(R.drawable.ic_3_4);
                    this.ll_4_3.setImageResource(R.drawable.ic_4_3);
                    this.ll_4_6.setImageResource(R.drawable.__6);
                    this.ll_6_4.setImageResource(R.drawable.ic_6_4);
                    this.ll_16_9.setImageResource(R.drawable.crop_16_9);
                    this.ll_9_16.setImageResource(R.drawable.ic_9_16);
                    return;
                case R.id.lll_6_4 :
                    manageColorFilter();
                    this.ll_6_4.setColorFilter(getResources().getColor(R.color.color_app));
                    this.tv_6_4.setTextColor(getResources().getColor(R.color.color_app));
                    this.mCropView.setCustomRatio(6, 4);
                    this.ll_free.setImageResource(R.drawable.ic_free_no);
                    this.ll_Original.setImageResource(R.drawable.original);
                    this.ll_1_1.setImageResource(R.drawable.ic__1);
                    this.ll_3_4.setImageResource(R.drawable.ic_3_4);
                    this.ll_4_3.setImageResource(R.drawable.ic_4_3);
                    this.ll_4_6.setImageResource(R.drawable.ic_4_6);
                    this.ll_6_4.setImageResource(R.drawable.i_3_2);
                    this.ll_16_9.setImageResource(R.drawable.crop_16_9);
                    this.ll_9_16.setImageResource(R.drawable.ic_9_16);
                    return;
                case R.id.lll_9_16 :
                    manageColorFilter();
                    this.ll_9_16.setColorFilter(getResources().getColor(R.color.color_app));
                    this.tv_9_16.setTextColor(getResources().getColor(R.color.color_app));
                    this.mCropView.setCropMode(CropMode.RATIO_9_16);
                    this.ll_free.setImageResource(R.drawable.ic_free_no);
                    this.ll_Original.setImageResource(R.drawable.original);
                    this.ll_1_1.setImageResource(R.drawable.ic__1);
                    this.ll_3_4.setImageResource(R.drawable.ic_3_4);
                    this.ll_4_3.setImageResource(R.drawable.ic_4_3);
                    this.ll_4_6.setImageResource(R.drawable.ic_4_6);
                    this.ll_6_4.setImageResource(R.drawable.ic_6_4);
                    this.ll_16_9.setImageResource(R.drawable.crop_16_9);
                    this.ll_9_16.setImageResource(R.drawable.__16);
                    return;
                case R.id.lll_Original :
                    manageColorFilter();
                    this.ll_Original.setImageResource(R.drawable.original1);
                    this.tv_Original.setTextColor(getResources().getColor(R.color.color_app));
                    this.mCropView.setCropMode(CropMode.FIT_IMAGE);
                    this.ll_free.setImageResource(R.drawable.ic_free_no);
                    this.ll_Original.setImageResource(R.drawable.original1);
                    this.ll_1_1.setImageResource(R.drawable.ic__1);
                    this.ll_3_4.setImageResource(R.drawable.ic_3_4);
                    this.ll_4_3.setImageResource(R.drawable.ic_4_3);
                    this.ll_4_6.setImageResource(R.drawable.ic_4_6);
                    this.ll_6_4.setImageResource(R.drawable.ic_6_4);
                    this.ll_16_9.setImageResource(R.drawable.crop_16_9);
                    this.ll_9_16.setImageResource(R.drawable.ic_9_16);
                    return;
                case R.id.lll_free :
                    manageColorFilter();
                    this.ll_free.setImageResource(R.drawable.free);
                    this.ll_Original.setImageResource(R.drawable.original);
                    this.ll_1_1.setImageResource(R.drawable.ic__1);
                    this.ll_3_4.setImageResource(R.drawable.ic_3_4);
                    this.ll_4_3.setImageResource(R.drawable.ic_4_3);
                    this.ll_4_6.setImageResource(R.drawable.ic_4_6);
                    this.ll_6_4.setImageResource(R.drawable.ic_6_4);
                    this.ll_16_9.setImageResource(R.drawable.crop_16_9);
                    this.ll_9_16.setImageResource(R.drawable.ic_9_16);

                    this.tv_free.setTextColor(getResources().getColor(R.color.color_app));
                    this.mCropView.setCropMode(CropMode.FREE);
                    return;
                default:
                    return;
            }
        } catch (Exception e) {
        } catch (Error e2) {
        }
    }

    private void manageColorFilter() {
        this.ll_free.setColorFilter(null);
        this.ll_Original.setColorFilter(null);
        this.ll_1_1.setColorFilter(null);
        this.ll_3_4.setColorFilter(null);
        this.ll_4_3.setColorFilter(null);
        this.ll_4_6.setColorFilter(null);
        this.ll_6_4.setColorFilter(null);
        this.ll_9_16.setColorFilter(null);
        this.ll_16_9.setColorFilter(null);
        this.tv_free.setTextColor(getResources().getColor(R.color.color_text));
        this.tv_Original.setTextColor(getResources().getColor(R.color.color_text));
        this.tv_1_1.setTextColor(getResources().getColor(R.color.color_text));
        this.tv_3_4.setTextColor(getResources().getColor(R.color.color_text));
        this.tv_4_3.setTextColor(getResources().getColor(R.color.color_text));
        this.tv_4_6.setTextColor(getResources().getColor(R.color.color_text));
        this.tv_6_4.setTextColor(getResources().getColor(R.color.color_text));
        this.tv_9_16.setTextColor(getResources().getColor(R.color.color_text));
        this.tv_16_9.setTextColor(getResources().getColor(R.color.color_text));
    }
}
