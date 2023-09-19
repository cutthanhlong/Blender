package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.VmPolicy.Builder;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images.Media;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.photo.blender.blendphoto.mixphotos.doubleexposure.APPUtility;
import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SystemUtil;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.MyTouch.MultiTouchListener1;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.io.File;
import java.io.IOException;

import yuku.ambilwarna.BuildConfig;

public class MainActivity extends AppCompatActivity {
    static final boolean $assertionsDisabled = (!MainActivity.class.desiredAssertionStatus());
    private static final String PREF_COLOR_KEY = "color_key";
    static ImageView back;
    public static ImageView backs;
    public static RelativeLayout backs_rel;
    public static Boolean bckbool = Boolean.valueOf(true);
    public static ImageView camgal;
    public static TextView clickadjust;
    static TextView clickback;
    static TextView clickdelete;
    static TextView clickdone;
    public static TextView clickonphoto;
    static TextView clickphoto;
    static TextView clickphoto1;
    public static TextView clickseek;
    static TextView clickstartover;
    public static Button delete;
    public static TextView done;
    public static Bitmap effbitmap;
    public static Boolean opacbool = Boolean.valueOf(true);
    public static ImageView opacity;
    public static SharedPreferences pref;
    static Bitmap resultBitmap;
    public static SeekBar seek;
    public static RelativeLayout seek_rel;
    public static ImageView selectedImageView = null;
    public static SeekBar sideblur_seek;
    static RelativeLayout tutrel;
    static RelativeLayout tutrel1;
    ImageView bg_v1, bg_v2, bg_v3, bg_v4, bg_v5, bg_v6, bg_v7, bg_v8, bg_v9, bg_v10;
    SharedPreferences appPreferences;
    ImageView b10;
    ImageView b11;
    ImageView b2;
    ImageView b20;
    ImageView b3;
    ImageView b4;
    ImageView b5;
    ImageView b6;
    ImageView b7;
    ImageView b8;
    ImageView b9;

    int backcolor = -1;
    Bitmap bitmap;
    Bitmap bitmap1;

    public static LinearLayout ll_bg, ll_image, ll_blend;
    public static TextView tv_Bg, tv_Image, tv_Blend;
    private boolean isselected = false;
    ImageView cam1;
    ImageView colorback;
    LinearLayout footer;
    RelativeLayout forcalrel;
    ImageView gal1;
    int i = 1;
    ImageView image;
    boolean isAppInstalled = false;
    SharedPreferences ratePreferences;
    RelativeLayout rel;
    RelativeLayout rel1;
    int sbr = 150;

    Uri selectedImage1;
    OnSeekBarChangeListener sideblurSeekBarChangeListener;
    Typeface ttf;
    Typeface ttf1;
    public  static  View v1, v2, v3;
    LinearLayout ll_background_home;
    private String[] storagePermissions;
    private static final int STORAGE_REQUEST_CODE = 100;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtil.setLocale(this);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ll_bg = findViewById(R.id.ll_bg);
        ll_image = findViewById(R.id.ll_image);
        ll_blend = findViewById(R.id.ll_blend);
        ll_background_home = findViewById(R.id.ll_background_home);

        tv_Bg = findViewById(R.id.tv_Bg);
        tv_Image = findViewById(R.id.tv_Image);
        tv_Blend = findViewById(R.id.tv_Blend);

        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);


        StrictMode.setVmPolicy(new Builder().build());
        this.image = (ImageView) findViewById(R.id.image);
//        this.image.setImageResource(R.drawable.n1);
        camgal = (ImageView) findViewById(R.id.camgal);
        backs = (ImageView) findViewById(R.id.backs);
        opacity = (ImageView) findViewById(R.id.opacity);
        done = (TextView) findViewById(R.id.done_text);
        back = (ImageView) findViewById(R.id.back);
        delete = (Button) findViewById(R.id.delete);
        this.colorback = (ImageView) findViewById(R.id.colorback);
        this.cam1 = (ImageView) findViewById(R.id.cam1);
        this.gal1 = (ImageView) findViewById(R.id.gal1);
        this.forcalrel = (RelativeLayout) findViewById(R.id.forcalrel);

        this.footer = (LinearLayout) findViewById(R.id.footer);
        this.footer.setVisibility(View.INVISIBLE);
        seek = (SeekBar) findViewById(R.id.seek);
        seek.setMax(0xFF);
        seek.setProgress(125);
        sideblur_seek = (SeekBar) findViewById(R.id.sideblur_seek);
        sideblur_seek.setMax(300);
        sideblur_seek.setProgress(this.sbr);
        this.rel = (RelativeLayout) findViewById(R.id.rel);
        this.rel1 = (RelativeLayout) findViewById(R.id.rel1);
        backs_rel = (RelativeLayout) findViewById(R.id.backs_rel);
        seek_rel = (RelativeLayout) findViewById(R.id.seek_rel);
        this.b2 = (ImageView) findViewById(R.id.b2);
        this.b3 = (ImageView) findViewById(R.id.b3);
        this.b4 = (ImageView) findViewById(R.id.b4);
        this.b5 = (ImageView) findViewById(R.id.b5);
        this.b6 = (ImageView) findViewById(R.id.b6);
        this.b7 = (ImageView) findViewById(R.id.b7);
        this.b8 = (ImageView) findViewById(R.id.b8);
        this.b9 = (ImageView) findViewById(R.id.b9);
        this.b10 = (ImageView) findViewById(R.id.b10);
        this.b11 = (ImageView) findViewById(R.id.b11);
        ActionBar actionBar = getSupportActionBar();
        bg_v1 = findViewById(R.id.bg_v1);
        bg_v2 = findViewById(R.id.bg_v2);
        bg_v3 = findViewById(R.id.bg_v3);
        bg_v4 = findViewById(R.id.bg_v4);
        bg_v5 = findViewById(R.id.bg_v5);
        bg_v6 = findViewById(R.id.bg_v6);
        bg_v7 = findViewById(R.id.bg_v7);
        bg_v8 = findViewById(R.id.bg_v8);
        bg_v9 = findViewById(R.id.bg_v9);
        bg_v10 = findViewById(R.id.bg_v10);

        if (checkStoragePermission()){
            done.setEnabled(true);
            done.setBackgroundResource(R.drawable.bg_export);
        }else {
            done.setBackgroundResource(R.drawable.bg_export_none);
            requestStoragePermission();
            done.setEnabled(false);
        }

        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv_Bg.setTextColor(getColor(R.color.color_app));
        }
        v1.setVisibility(View.VISIBLE);
        backs.setImageResource(R.drawable.icon);
        backs.setImageResource(R.drawable.icon);
        if (VERSION.SDK_INT >= 21) {
            tutrel = (RelativeLayout) findViewById(R.id.tutrel);
            tutrel1 = (RelativeLayout) findViewById(R.id.tutrel1);
            clickback = (TextView) findViewById(R.id.clickback);
            clickphoto = (TextView) findViewById(R.id.clickphoto);
            clickphoto1 = (TextView) findViewById(R.id.clickphoto1);
            clickadjust = (TextView) findViewById(R.id.clickadjust);
            clickstartover = (TextView) findViewById(R.id.clickstartover);
            clickdelete = (TextView) findViewById(R.id.clickdelete);
            clickdone = (TextView) findViewById(R.id.clickdone);
            clickonphoto = (TextView) findViewById(R.id.clickonphoto);
            clickseek = (TextView) findViewById(R.id.clickseek);
            this.ttf = Typeface.createFromAsset(getAssets(), "barbatrick.ttf");
            this.ttf1 = Typeface.createFromAsset(getAssets(), "Arabella.ttf");


            this.footer.setVisibility(View.VISIBLE);

            this.ratePreferences = PreferenceManager.getDefaultSharedPreferences(this);
            this.appPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            this.isAppInstalled = this.appPreferences.getBoolean("isAppInstalled", false);
        } else {
            tutrel = (RelativeLayout) findViewById(R.id.tutrel);
            tutrel1 = (RelativeLayout) findViewById(R.id.tutrel1);
            clickback = (TextView) findViewById(R.id.clickback);
            clickphoto = (TextView) findViewById(R.id.clickphoto);
            clickphoto1 = (TextView) findViewById(R.id.clickphoto1);
            clickadjust = (TextView) findViewById(R.id.clickadjust);
            clickstartover = (TextView) findViewById(R.id.clickstartover);
            clickdelete = (TextView) findViewById(R.id.clickdelete);
            clickdone = (TextView) findViewById(R.id.clickdone);
            clickonphoto = (TextView) findViewById(R.id.clickonphoto);
            clickseek = (TextView) findViewById(R.id.clickseek);
            this.ttf = Typeface.createFromAsset(getAssets(), "barbatrick.ttf");
            this.ttf1 = Typeface.createFromAsset(getAssets(), "Arabella.ttf");
            clickback.setTypeface(this.ttf);
            clickphoto.setTypeface(this.ttf);
            clickphoto1.setTypeface(this.ttf);
            clickadjust.setTypeface(this.ttf);
            clickstartover.setTypeface(this.ttf);
            clickdelete.setTypeface(this.ttf);
            clickdone.setTypeface(this.ttf);
            clickonphoto.setTypeface(this.ttf);
            clickseek.setTypeface(this.ttf);


            this.footer.setVisibility(View.VISIBLE);

            this.ratePreferences = PreferenceManager.getDefaultSharedPreferences(this);
            this.appPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            this.isAppInstalled = this.appPreferences.getBoolean("isAppInstalled", false);
        }
        if (!this.isAppInstalled) {
            createShortCut();
            Editor editor = this.appPreferences.edit();
            editor.putBoolean("isAppInstalled", true);
            editor.commit();
        }
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        Editor ed = MainActivity.pref.edit();
        ed.putInt("tut", 4);
        ed.commit();

        ll_image.setOnClickListener(new OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {

                if (!isselected){
                    backs.setImageResource(R.drawable.icon1);
                    if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv_Bg.setTextColor(getColor(R.color.color_text));
                    }
                    camgal.setImageResource(R.drawable.gallery1);
                    if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv_Image.setTextColor(getColor(R.color.color_app));
                    }
                    opacity.setImageResource(R.drawable.edit);
                    if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv_Blend.setTextColor(getColor(R.color.color_text));
                    }
                    v2.setVisibility(View.VISIBLE);
                    v1.setVisibility(View.GONE);
                    v3.setVisibility(View.GONE);
                    MainActivity.backs.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                    MainActivity.opacity.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                    MainActivity.camgal.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), Mode.MULTIPLY);
                    MainActivity.seek_rel.setVisibility(View.GONE);
                    MainActivity.opacbool = Boolean.valueOf(true);
                    MainActivity.backs_rel.setVisibility(View.GONE);
                    MainActivity.bckbool = Boolean.valueOf(true);
                    Intent intent = new Intent(MainActivity.this, LoadImageActivity.class);
                    startActivityForResult(intent, 5);
                    isselected = true;
                }

            }


        });


        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        ll_bg.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                MainActivity.camgal.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.opacity.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.backs.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), Mode.MULTIPLY);
                MainActivity.seek_rel.setVisibility(View.GONE);
                MainActivity.backs_rel.setVisibility(View.VISIBLE);

                backs.setImageResource(R.drawable.icon);
                if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv_Bg.setTextColor(getColor(R.color.color_app));
                }
                camgal.setImageResource(R.drawable.gallery);
                if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv_Image.setTextColor(getColor(R.color.color_text));
                }
                opacity.setImageResource(R.drawable.edit);
                if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv_Blend.setTextColor(getColor(R.color.color_text));
                }
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);
            }
        });
        ll_blend.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                MainActivity.backs.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.camgal.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.opacity.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), Mode.MULTIPLY);
                if (MainActivity.selectedImageView == null) {
                    MainActivity.seek_rel.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this.getApplicationContext(), MainActivity.this.getResources().getString(R.string.selectim).toString(), Toast.LENGTH_SHORT).show();
                } else if (MainActivity.opacbool.booleanValue()) {
                    MainActivity.seek_rel.setVisibility(View.VISIBLE);
                    MainActivity.opacbool = Boolean.valueOf(false);
                    MainActivity.backs_rel.setVisibility(View.GONE);
                    MainActivity.bckbool = Boolean.valueOf(true);
                } else {
                    MainActivity.seek_rel.setVisibility(View.VISIBLE);
                    MainActivity.opacbool = Boolean.valueOf(true);
                }
                if (MainActivity.pref.getInt("tut", 0) == 2) {
                    MainActivity.clickonphoto.setVisibility(View.VISIBLE);
                }
                MainActivity.backs_rel.setVisibility(View.GONE);

                backs.setImageResource(R.drawable.icon1);
                if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv_Bg.setTextColor(getColor(R.color.color_text));
                }
                camgal.setImageResource(R.drawable.gallery);
                if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv_Image.setTextColor(getColor(R.color.color_text));
                }
                opacity.setImageResource(R.drawable.edit_bg1);
                if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv_Blend.setTextColor(getColor(R.color.color_app));
                }
                v3.setVisibility(View.VISIBLE);
                v1.setVisibility(View.GONE);
                v2.setVisibility(View.GONE);
            }
        });
        seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (MainActivity.pref.getInt("tut", 0) == 3) {
                    MainActivity.clickback.setVisibility(View.GONE);
                    MainActivity.clickphoto.setVisibility(View.GONE);
                    MainActivity.clickadjust.setVisibility(View.GONE);
                    MainActivity.clickonphoto.setVisibility(View.GONE);
                    MainActivity.clickseek.setVisibility(View.GONE);
                    MainActivity.clickstartover.setVisibility(View.VISIBLE);
                    MainActivity.clickdone.setVisibility(View.VISIBLE);
                    MainActivity.clickdelete.setVisibility(View.VISIBLE);
                    MainActivity.backs.setEnabled(false);
                    MainActivity.camgal.setEnabled(false);
                    MainActivity.opacity.setEnabled(false);
                    MainActivity.delete.setEnabled(false);
                    MainActivity.done.setEnabled(false);
                    MainActivity.tutrel.setOnTouchListener(new View.OnTouchListener() {
                        public boolean onTouch(View v, MotionEvent event) {
                            MainActivity.backs.setEnabled(true);
                            MainActivity.camgal.setEnabled(true);
                            MainActivity.opacity.setEnabled(true);
                            MainActivity.delete.setEnabled(true);
                            MainActivity.done.setEnabled(true);
                            MainActivity.clickback.setVisibility(View.GONE);
                            MainActivity.clickphoto.setVisibility(View.GONE);
                            MainActivity.clickadjust.setVisibility(View.GONE);
                            MainActivity.clickonphoto.setVisibility(View.GONE);
                            MainActivity.clickstartover.setVisibility(View.GONE);
                            MainActivity.clickdone.setVisibility(View.GONE);
                            MainActivity.clickdelete.setVisibility(View.GONE);
                            MainActivity.clickseek.setVisibility(View.GONE);
                            MainActivity.tutrel.setVisibility(View.GONE);
                            MainActivity.tutrel1.setVisibility(View.VISIBLE);
                            MainActivity.clickphoto1.setVisibility(View.VISIBLE);
                            MainActivity.tutrel1.setOnTouchListener(new View.OnTouchListener() {
                                public boolean onTouch(View v, MotionEvent event) {
                                    MainActivity.tutrel1.setVisibility(View.GONE);
                                    return true;
                                }
                            });
                            return true;
                        }
                    });
                    Editor ed = MainActivity.pref.edit();
                    ed.putInt("tut", 4);
                    ed.commit();
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = MainActivity.seek.getMax() - progress;
                if (VERSION.SDK_INT <= 16) {
                    MainActivity.selectedImageView.setAlpha(progress);
                } else {
                    MainActivity.selectedImageView.setImageAlpha(progress);
                }
            }
        });
        SeekBar seekBar = sideblur_seek;
        OnSeekBarChangeListener anonymousClass5 = new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (MainActivity.pref.getInt("tut", 0) == 3) {
                    MainActivity.clickback.setVisibility(View.GONE);
                    MainActivity.clickphoto.setVisibility(View.GONE);
                    MainActivity.clickadjust.setVisibility(View.GONE);
                    MainActivity.clickonphoto.setVisibility(View.GONE);
                    MainActivity.clickseek.setVisibility(View.GONE);
                    MainActivity.clickstartover.setVisibility(View.VISIBLE);
                    MainActivity.clickdone.setVisibility(View.VISIBLE);
                    MainActivity.clickdelete.setVisibility(View.VISIBLE);
                    MainActivity.backs.setEnabled(false);
                    MainActivity.camgal.setEnabled(false);
                    MainActivity.opacity.setEnabled(false);
                    MainActivity.delete.setEnabled(false);
                    MainActivity.done.setEnabled(false);
                    MainActivity.tutrel.setOnTouchListener(new View.OnTouchListener() {
                        public boolean onTouch(View v, MotionEvent event) {
                            MainActivity.backs.setEnabled(true);
                            MainActivity.camgal.setEnabled(true);
                            MainActivity.opacity.setEnabled(true);
                            MainActivity.delete.setEnabled(true);
                            MainActivity.done.setEnabled(true);
                            MainActivity.clickback.setVisibility(View.GONE);
                            MainActivity.clickphoto.setVisibility(View.GONE);
                            MainActivity.clickadjust.setVisibility(View.GONE);
                            MainActivity.clickonphoto.setVisibility(View.GONE);
                            MainActivity.clickstartover.setVisibility(View.GONE);
                            MainActivity.clickdone.setVisibility(View.GONE);
                            MainActivity.clickdelete.setVisibility(View.GONE);
                            MainActivity.clickseek.setVisibility(View.GONE);
                            MainActivity.tutrel.setVisibility(View.GONE);
                            MainActivity.tutrel1.setVisibility(View.VISIBLE);
                            MainActivity.clickphoto1.setVisibility(View.VISIBLE);
                            MainActivity.tutrel1.setOnTouchListener(new View.OnTouchListener() {
                                public boolean onTouch(View v, MotionEvent event) {
                                    MainActivity.tutrel1.setVisibility(View.GONE);
                                    return true;
                                }
                            });
                            return true;
                        }
                    });
                    Editor ed = MainActivity.pref.edit();
                    ed.putInt("tut", 4);
                    ed.commit();
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    MainActivity.selectedImageView.setImageBitmap(MainActivity.this.sideblur(MainActivity.effbitmap, 1));
                    MainActivity.selectedImageView.setContentDescription("1");
                    MainActivity.this.sbr = 1;
                    return;
                }
                MainActivity.selectedImageView.setImageBitmap(MainActivity.this.sideblur(MainActivity.effbitmap, progress));
                MainActivity.selectedImageView.setContentDescription(BuildConfig.FLAVOR + progress);
                MainActivity.this.sbr = progress;
            }
        };
        this.sideblurSeekBarChangeListener = anonymousClass5;
        seekBar.setOnSeekBarChangeListener(anonymousClass5);

        this.colorback.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.cam1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.gal1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.colorback.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), Mode.MULTIPLY);
                MainActivity.this.b2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b5.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b6.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b7.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b8.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b9.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b10.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b11.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                ColorPickerDialogBuilder
                        .with(MainActivity.this)
                        .setTitle(R.string.choose)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .initialColor(sharedPreferences.getInt("background_color", Color.WHITE))
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {

                            }
                        })
                        .setPositiveButton(""+getText(R.string.ok), new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                Drawable drawable = new ColorDrawable(selectedColor);
                                image.setImageDrawable(drawable);
                                bg_v1.setVisibility(View.INVISIBLE);
                                bg_v2.setVisibility(View.INVISIBLE);
                                bg_v3.setVisibility(View.INVISIBLE);
                                bg_v4.setVisibility(View.INVISIBLE);
                                bg_v5.setVisibility(View.INVISIBLE);
                                bg_v6.setVisibility(View.INVISIBLE);
                                bg_v7.setVisibility(View.INVISIBLE);
                                bg_v8.setVisibility(View.INVISIBLE);
                                bg_v8.setVisibility(View.INVISIBLE);
                                bg_v10.setVisibility(View.INVISIBLE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("background_color", selectedColor);
                                editor.apply();
                            }
                        })
                        .setNegativeButton(""+getText(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });
        this.cam1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.cam1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), Mode.MULTIPLY);
                MainActivity.this.gal1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.colorback.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.b2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b5.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b6.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b7.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b8.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b9.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b10.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b11.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));


                File nnn = new File(APPUtility.getAppDir(), MainActivity.this.getResources().getString(R.string.app_name));

                nnn.mkdirs();

                try {


                    File filedel = new File(Uri.parse(nnn + "/MUS.png").getPath());


                    filedel.delete();
                    if (filedel.exists()) {
                        filedel.getCanonicalFile().delete();
                        if (filedel.exists()) {
                            MainActivity.this.getApplicationContext().deleteFile(filedel.getName());
                        }
                    }
                    MainActivity.this.removeImageGallery(filedel);
                    File filedel1 = new File(Uri.parse(nnn + "/UPM.jpg").getPath());


                    filedel1.delete();
                    if (filedel1.exists()) {
                        filedel1.getCanonicalFile().delete();
                        if (filedel1.exists()) {
                            MainActivity.this.getApplicationContext().deleteFile(filedel1.getName());
                        }
                    }
                    MainActivity.this.removeImageGallery(filedel1);
                    File filedel2 = new File(Uri.parse("file:///sdcard/DCIM/share.png").getPath());
                    filedel2.delete();
                    if (filedel2.exists()) {
                        filedel2.getCanonicalFile().delete();
                        if (filedel2.exists()) {
                            MainActivity.this.getApplicationContext().deleteFile(filedel2.getName());
                        }
                    }
                    MainActivity.this.removeImageGallery(filedel2);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                Uri imgUri = Uri.fromFile(new File(nnn, "UPM.jpg"));
                intent.putExtra("output", imgUri);
                MainActivity.this.selectedImage1 = imgUri;
                MainActivity.this.startActivityForResult(intent, 4);


            }
        });
        this.gal1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!isselected){
                    MainActivity.this.cam1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                    MainActivity.this.gal1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), Mode.MULTIPLY);
                    MainActivity.this.colorback.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                    MainActivity.this.b2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                    MainActivity.this.b3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                    MainActivity.this.b4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                    MainActivity.this.b5.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                    MainActivity.this.b6.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                    MainActivity.this.b7.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                    MainActivity.this.b8.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                    MainActivity.this.b9.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                    MainActivity.this.b10.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                    MainActivity.this.b11.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));


                    Intent intent = new Intent(MainActivity.this, LoadBackgroundActivity.class);
                    startActivityForResult(intent, 6);
                    isselected = true;
                }

            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                TextView tv_cancel, tv_delete;
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_delete);
                dialog.getWindow().getAttributes().windowAnimations = androidx.appcompat.R.style.Animation_AppCompat_Dialog;

                Window window = dialog.getWindow();
                window.setGravity(Gravity.CENTER);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                tv_cancel = dialog.findViewById(R.id.tv_cancel);
                tv_delete = dialog.findViewById(R.id.tv_delete);

                tv_cancel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                tv_delete.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (MainActivity.selectedImageView != null) {
                            MainActivity.selectedImageView.setImageBitmap(null);
                            RelativeLayout imrel = (RelativeLayout) MainActivity.selectedImageView.getParent();
                            imrel.removeAllViews();
                            imrel.setOnTouchListener(null);
                            MainActivity.selectedImageView = null;
                            MainActivity.delete.setVisibility(View.INVISIBLE);
                            MainActivity.seek_rel.setVisibility(View.GONE);
                            MainActivity.opacbool = Boolean.valueOf(true);
                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();

            }
        });
        done.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!isselected){
                    MainActivity.seek_rel.setVisibility(View.GONE);
                    MainActivity.opacbool = Boolean.valueOf(true);
                    MainActivity.backs_rel.setVisibility(View.GONE);
                    MainActivity.bckbool = Boolean.valueOf(true);
                    MainActivity.this.rel.setDrawingCacheEnabled(true);
                    MainActivity.resultBitmap = Bitmap.createBitmap(MainActivity.this.rel.getDrawingCache());
                    MainActivity.this.rel.setDrawingCacheEnabled(false);
                    MainActivity.resultBitmap = MainActivity.this.resizeBitmap(MainActivity.resultBitmap);
                    MainActivity.this.PassIntent();
                    isselected = true;
                }
            }
        });
        this.b2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.cam1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.gal1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.colorback.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.b2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.colorAccent));
                MainActivity.this.b3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b5.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b6.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b7.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b8.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b9.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b10.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b11.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));

                bg_v1.setVisibility(View.VISIBLE);
                bg_v2.setVisibility(View.INVISIBLE);
                bg_v3.setVisibility(View.INVISIBLE);
                bg_v4.setVisibility(View.INVISIBLE);
                bg_v5.setVisibility(View.INVISIBLE);
                bg_v6.setVisibility(View.INVISIBLE);
                bg_v7.setVisibility(View.INVISIBLE);
                bg_v8.setVisibility(View.INVISIBLE);
                bg_v9.setVisibility(View.INVISIBLE);
                bg_v10.setVisibility(View.INVISIBLE);


                MainActivity.this.bitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.n1);
                MainActivity.this.bitmap = MainActivity.this.resizeBitmap(MainActivity.this.bitmap);
                MainActivity.this.image.setImageBitmap(MainActivity.this.bitmap);
                MainActivity.this.rel.setLayoutParams(new LayoutParams(MainActivity.this.bitmap.getWidth(), MainActivity.this.bitmap.getHeight()));
                MainActivity.this.rel.setBackgroundColor(0);
                if (MainActivity.pref.getInt("tut", 0) == 0) {
                    MainActivity.backs_rel.setVisibility(View.GONE);
                    MainActivity.bckbool = Boolean.valueOf(true);
                    MainActivity.clickback.setVisibility(View.GONE);
                    MainActivity.clickphoto.setVisibility(View.VISIBLE);
                    MainActivity.backs.setEnabled(false);
                    MainActivity.camgal.setEnabled(true);
                    MainActivity.opacity.setEnabled(false);
                    MainActivity.delete.setEnabled(false);
                    MainActivity.done.setEnabled(false);
                    Editor ed = MainActivity.pref.edit();
                    ed.putInt("tut", 1);
                    ed.commit();
                }
            }
        });
        this.b3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.cam1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.gal1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.colorback.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.b2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.colorAccent));
                MainActivity.this.b4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b5.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b6.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b7.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b8.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b9.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b10.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b11.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));


                bg_v1.setVisibility(View.INVISIBLE);
                bg_v2.setVisibility(View.VISIBLE);
                bg_v3.setVisibility(View.INVISIBLE);
                bg_v4.setVisibility(View.INVISIBLE);
                bg_v5.setVisibility(View.INVISIBLE);
                bg_v6.setVisibility(View.INVISIBLE);
                bg_v7.setVisibility(View.INVISIBLE);
                bg_v8.setVisibility(View.INVISIBLE);
                bg_v9.setVisibility(View.INVISIBLE);
                bg_v10.setVisibility(View.INVISIBLE);
                MainActivity.this.bitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.n2);
                MainActivity.this.bitmap = MainActivity.this.resizeBitmap(MainActivity.this.bitmap);
                MainActivity.this.image.setImageBitmap(MainActivity.this.bitmap);
                MainActivity.this.rel.setLayoutParams(new LayoutParams(MainActivity.this.bitmap.getWidth(), MainActivity.this.bitmap.getHeight()));
                MainActivity.this.rel.setBackgroundColor(0);
                if (MainActivity.pref.getInt("tut", 0) == 0) {
                    MainActivity.backs_rel.setVisibility(View.GONE);
                    MainActivity.bckbool = Boolean.valueOf(true);
                    MainActivity.clickback.setVisibility(View.GONE);
                    MainActivity.clickphoto.setVisibility(View.VISIBLE);
                    MainActivity.backs.setEnabled(false);
                    MainActivity.camgal.setEnabled(true);
                    MainActivity.opacity.setEnabled(false);
                    MainActivity.delete.setEnabled(false);
                    MainActivity.done.setEnabled(false);
                    Editor ed = MainActivity.pref.edit();
                    ed.putInt("tut", 1);
                    ed.commit();
                }
            }
        });
        this.b4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.cam1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.gal1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.colorback.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.b2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.colorAccent));
                MainActivity.this.b5.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b6.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b7.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b8.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b9.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b10.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b11.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));


                bg_v1.setVisibility(View.INVISIBLE);
                bg_v2.setVisibility(View.INVISIBLE);
                bg_v3.setVisibility(View.VISIBLE);
                bg_v4.setVisibility(View.INVISIBLE);
                bg_v5.setVisibility(View.INVISIBLE);
                bg_v6.setVisibility(View.INVISIBLE);
                bg_v7.setVisibility(View.INVISIBLE);
                bg_v8.setVisibility(View.INVISIBLE);
                bg_v9.setVisibility(View.INVISIBLE);
                bg_v10.setVisibility(View.INVISIBLE);
                MainActivity.this.bitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.n3);
                MainActivity.this.bitmap = MainActivity.this.resizeBitmap(MainActivity.this.bitmap);
                MainActivity.this.image.setImageBitmap(MainActivity.this.bitmap);
                MainActivity.this.rel.setLayoutParams(new LayoutParams(MainActivity.this.bitmap.getWidth(), MainActivity.this.bitmap.getHeight()));
                MainActivity.this.rel.setBackgroundColor(0);
                if (MainActivity.pref.getInt("tut", 0) == 0) {
                    MainActivity.backs_rel.setVisibility(View.GONE);
                    MainActivity.bckbool = Boolean.valueOf(true);
                    MainActivity.clickback.setVisibility(View.GONE);
                    MainActivity.clickphoto.setVisibility(View.VISIBLE);
                    MainActivity.backs.setEnabled(false);
                    MainActivity.camgal.setEnabled(true);
                    MainActivity.opacity.setEnabled(false);
                    MainActivity.delete.setEnabled(false);
                    MainActivity.done.setEnabled(false);
                    Editor ed = MainActivity.pref.edit();
                    ed.putInt("tut", 1);
                    ed.commit();
                }
            }
        });
        this.b5.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.cam1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.gal1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.colorback.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.b2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b5.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.colorAccent));
                MainActivity.this.b6.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b7.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b8.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b9.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b10.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b11.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));

                bg_v1.setVisibility(View.INVISIBLE);
                bg_v2.setVisibility(View.INVISIBLE);
                bg_v3.setVisibility(View.INVISIBLE);
                bg_v4.setVisibility(View.VISIBLE);
                bg_v5.setVisibility(View.INVISIBLE);
                bg_v6.setVisibility(View.INVISIBLE);
                bg_v7.setVisibility(View.INVISIBLE);
                bg_v8.setVisibility(View.INVISIBLE);
                bg_v9.setVisibility(View.INVISIBLE);
                bg_v10.setVisibility(View.INVISIBLE);

                MainActivity.this.bitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.n4);
                MainActivity.this.bitmap = MainActivity.this.resizeBitmap(MainActivity.this.bitmap);
                MainActivity.this.image.setImageBitmap(MainActivity.this.bitmap);
                MainActivity.this.rel.setLayoutParams(new LayoutParams(MainActivity.this.bitmap.getWidth(), MainActivity.this.bitmap.getHeight()));
                MainActivity.this.rel.setBackgroundColor(0);
                if (MainActivity.pref.getInt("tut", 0) == 0) {
                    MainActivity.backs_rel.setVisibility(View.GONE);
                    MainActivity.bckbool = Boolean.valueOf(true);
                    MainActivity.clickback.setVisibility(View.GONE);
                    MainActivity.clickphoto.setVisibility(View.VISIBLE);
                    MainActivity.backs.setEnabled(false);
                    MainActivity.camgal.setEnabled(true);
                    MainActivity.opacity.setEnabled(false);
                    MainActivity.delete.setEnabled(false);
                    MainActivity.done.setEnabled(false);
                    Editor ed = MainActivity.pref.edit();
                    ed.putInt("tut", 1);
                    ed.commit();
                }
            }
        });
        this.b6.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.cam1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.gal1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.colorback.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.b2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b5.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b6.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.colorAccent));
                MainActivity.this.b7.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b8.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b9.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b10.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b11.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));

                bg_v1.setVisibility(View.INVISIBLE);
                bg_v2.setVisibility(View.INVISIBLE);
                bg_v3.setVisibility(View.INVISIBLE);
                bg_v4.setVisibility(View.INVISIBLE);
                bg_v5.setVisibility(View.VISIBLE);
                bg_v6.setVisibility(View.INVISIBLE);
                bg_v7.setVisibility(View.INVISIBLE);
                bg_v8.setVisibility(View.INVISIBLE);
                bg_v9.setVisibility(View.INVISIBLE);
                bg_v10.setVisibility(View.INVISIBLE);

                MainActivity.this.bitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.n5);
                MainActivity.this.bitmap = MainActivity.this.resizeBitmap(MainActivity.this.bitmap);
                MainActivity.this.image.setImageBitmap(MainActivity.this.bitmap);
                MainActivity.this.rel.setLayoutParams(new LayoutParams(MainActivity.this.bitmap.getWidth(), MainActivity.this.bitmap.getHeight()));
                MainActivity.this.rel.setBackgroundColor(0);
                if (MainActivity.pref.getInt("tut", 0) == 0) {
                    MainActivity.backs_rel.setVisibility(View.GONE);
                    MainActivity.bckbool = Boolean.valueOf(true);
                    MainActivity.clickback.setVisibility(View.GONE);
                    MainActivity.clickphoto.setVisibility(View.VISIBLE);
                    MainActivity.backs.setEnabled(false);
                    MainActivity.camgal.setEnabled(true);
                    MainActivity.opacity.setEnabled(false);
                    MainActivity.delete.setEnabled(false);
                    MainActivity.done.setEnabled(false);
                    Editor ed = MainActivity.pref.edit();
                    ed.putInt("tut", 1);
                    ed.commit();
                }
            }
        });
        this.b7.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.cam1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.gal1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.colorback.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.b2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b5.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b6.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b7.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.colorAccent));
                MainActivity.this.b8.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b9.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b10.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b11.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));


                bg_v1.setVisibility(View.INVISIBLE);
                bg_v2.setVisibility(View.INVISIBLE);
                bg_v3.setVisibility(View.INVISIBLE);
                bg_v4.setVisibility(View.INVISIBLE);
                bg_v5.setVisibility(View.INVISIBLE);
                bg_v6.setVisibility(View.VISIBLE);
                bg_v7.setVisibility(View.INVISIBLE);
                bg_v8.setVisibility(View.INVISIBLE);
                bg_v9.setVisibility(View.INVISIBLE);
                bg_v10.setVisibility(View.INVISIBLE);
                MainActivity.this.bitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.n6);
                MainActivity.this.bitmap = MainActivity.this.resizeBitmap(MainActivity.this.bitmap);
                MainActivity.this.image.setImageBitmap(MainActivity.this.bitmap);
                MainActivity.this.rel.setLayoutParams(new LayoutParams(MainActivity.this.bitmap.getWidth(), MainActivity.this.bitmap.getHeight()));
                MainActivity.this.rel.setBackgroundColor(0);
                if (MainActivity.pref.getInt("tut", 0) == 0) {
                    MainActivity.backs_rel.setVisibility(View.GONE);
                    MainActivity.bckbool = Boolean.valueOf(true);
                    MainActivity.clickback.setVisibility(View.GONE);
                    MainActivity.clickphoto.setVisibility(View.VISIBLE);
                    MainActivity.backs.setEnabled(false);
                    MainActivity.camgal.setEnabled(true);
                    MainActivity.opacity.setEnabled(false);
                    MainActivity.delete.setEnabled(false);
                    MainActivity.done.setEnabled(false);
                    Editor ed = MainActivity.pref.edit();
                    ed.putInt("tut", 1);
                    ed.commit();
                }
            }
        });
        this.b8.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.cam1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.gal1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.colorback.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.b2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b5.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b6.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b7.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b8.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.colorAccent));
                MainActivity.this.b9.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b10.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b11.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));


                bg_v1.setVisibility(View.INVISIBLE);
                bg_v2.setVisibility(View.INVISIBLE);
                bg_v3.setVisibility(View.INVISIBLE);
                bg_v4.setVisibility(View.INVISIBLE);
                bg_v5.setVisibility(View.INVISIBLE);
                bg_v6.setVisibility(View.INVISIBLE);
                bg_v7.setVisibility(View.VISIBLE);
                bg_v8.setVisibility(View.INVISIBLE);
                bg_v9.setVisibility(View.INVISIBLE);
                bg_v10.setVisibility(View.INVISIBLE);
                MainActivity.this.bitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.n7);
                MainActivity.this.bitmap = MainActivity.this.resizeBitmap(MainActivity.this.bitmap);
                MainActivity.this.image.setImageBitmap(MainActivity.this.bitmap);
                MainActivity.this.rel.setLayoutParams(new LayoutParams(MainActivity.this.bitmap.getWidth(), MainActivity.this.bitmap.getHeight()));
                MainActivity.this.rel.setBackgroundColor(0);
                if (MainActivity.pref.getInt("tut", 0) == 0) {
                    MainActivity.backs_rel.setVisibility(View.GONE);
                    MainActivity.bckbool = Boolean.valueOf(true);
                    MainActivity.clickback.setVisibility(View.GONE);
                    MainActivity.clickphoto.setVisibility(View.VISIBLE);
                    MainActivity.backs.setEnabled(false);
                    MainActivity.camgal.setEnabled(true);
                    MainActivity.opacity.setEnabled(false);
                    MainActivity.delete.setEnabled(false);
                    MainActivity.done.setEnabled(false);
                    Editor ed = MainActivity.pref.edit();
                    ed.putInt("tut", 1);
                    ed.commit();
                }
            }
        });
        this.b9.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.cam1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.gal1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.colorback.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.b2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b5.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b6.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b7.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b8.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b9.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.colorAccent));
                MainActivity.this.b10.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b11.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));


                bg_v1.setVisibility(View.INVISIBLE);
                bg_v2.setVisibility(View.INVISIBLE);
                bg_v3.setVisibility(View.INVISIBLE);
                bg_v4.setVisibility(View.INVISIBLE);
                bg_v5.setVisibility(View.INVISIBLE);
                bg_v6.setVisibility(View.INVISIBLE);
                bg_v7.setVisibility(View.INVISIBLE);
                bg_v8.setVisibility(View.VISIBLE);
                bg_v9.setVisibility(View.INVISIBLE);
                bg_v10.setVisibility(View.INVISIBLE);
                MainActivity.this.bitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.n8);
                MainActivity.this.bitmap = MainActivity.this.resizeBitmap(MainActivity.this.bitmap);
                MainActivity.this.image.setImageBitmap(MainActivity.this.bitmap);
                MainActivity.this.rel.setLayoutParams(new LayoutParams(MainActivity.this.bitmap.getWidth(), MainActivity.this.bitmap.getHeight()));
                MainActivity.this.rel.setBackgroundColor(0);
                if (MainActivity.pref.getInt("tut", 0) == 0) {
                    MainActivity.backs_rel.setVisibility(View.GONE);
                    MainActivity.bckbool = Boolean.valueOf(true);
                    MainActivity.clickback.setVisibility(View.GONE);
                    MainActivity.clickphoto.setVisibility(View.VISIBLE);
                    MainActivity.backs.setEnabled(false);
                    MainActivity.camgal.setEnabled(true);
                    MainActivity.opacity.setEnabled(false);
                    MainActivity.delete.setEnabled(false);
                    MainActivity.done.setEnabled(false);
                    Editor ed = MainActivity.pref.edit();
                    ed.putInt("tut", 1);
                    ed.commit();
                }
            }
        });
        this.b10.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.cam1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.gal1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.colorback.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.b2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b5.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b6.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b7.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b8.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b9.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b10.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.colorAccent));
                MainActivity.this.b11.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));

                bg_v1.setVisibility(View.INVISIBLE);
                bg_v2.setVisibility(View.INVISIBLE);
                bg_v3.setVisibility(View.INVISIBLE);
                bg_v4.setVisibility(View.INVISIBLE);
                bg_v5.setVisibility(View.INVISIBLE);
                bg_v6.setVisibility(View.INVISIBLE);
                bg_v7.setVisibility(View.INVISIBLE);
                bg_v8.setVisibility(View.INVISIBLE);
                bg_v9.setVisibility(View.VISIBLE);
                bg_v10.setVisibility(View.INVISIBLE);

                MainActivity.this.bitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.n9);
                MainActivity.this.bitmap = MainActivity.this.resizeBitmap(MainActivity.this.bitmap);
                MainActivity.this.image.setImageBitmap(MainActivity.this.bitmap);
                MainActivity.this.rel.setLayoutParams(new LayoutParams(MainActivity.this.bitmap.getWidth(), MainActivity.this.bitmap.getHeight()));
                MainActivity.this.rel.setBackgroundColor(0);
                if (MainActivity.pref.getInt("tut", 0) == 0) {
                    MainActivity.backs_rel.setVisibility(View.GONE);
                    MainActivity.bckbool = Boolean.valueOf(true);
                    MainActivity.clickback.setVisibility(View.GONE);
                    MainActivity.clickphoto.setVisibility(View.VISIBLE);
                    MainActivity.backs.setEnabled(false);
                    MainActivity.camgal.setEnabled(true);
                    MainActivity.opacity.setEnabled(false);
                    MainActivity.delete.setEnabled(false);
                    MainActivity.done.setEnabled(false);
                    Editor ed = MainActivity.pref.edit();
                    ed.putInt("tut", 1);
                    ed.commit();
                }
            }
        });
        this.b11.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.cam1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.gal1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.colorback.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
                MainActivity.this.b2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b5.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b6.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b7.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b8.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b9.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b10.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
                MainActivity.this.b11.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.colorAccent));


                bg_v1.setVisibility(View.INVISIBLE);
                bg_v2.setVisibility(View.INVISIBLE);
                bg_v3.setVisibility(View.INVISIBLE);
                bg_v4.setVisibility(View.INVISIBLE);
                bg_v5.setVisibility(View.INVISIBLE);
                bg_v6.setVisibility(View.INVISIBLE);
                bg_v7.setVisibility(View.INVISIBLE);
                bg_v8.setVisibility(View.INVISIBLE);
                bg_v9.setVisibility(View.INVISIBLE);
                bg_v10.setVisibility(View.VISIBLE);
                MainActivity.this.bitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.n10);
                MainActivity.this.bitmap = MainActivity.this.resizeBitmap(MainActivity.this.bitmap);
                MainActivity.this.image.setImageBitmap(MainActivity.this.bitmap);
                MainActivity.this.rel.setLayoutParams(new LayoutParams(MainActivity.this.bitmap.getWidth(), MainActivity.this.bitmap.getHeight()));
                MainActivity.this.rel.setBackgroundColor(0);
                if (MainActivity.pref.getInt("tut", 0) == 0) {
                    MainActivity.backs_rel.setVisibility(View.GONE);
                    MainActivity.bckbool = Boolean.valueOf(true);
                    MainActivity.clickback.setVisibility(View.GONE);
                    MainActivity.clickphoto.setVisibility(View.VISIBLE);
                    MainActivity.backs.setEnabled(false);
                    MainActivity.camgal.setEnabled(true);
                    MainActivity.opacity.setEnabled(false);
                    MainActivity.delete.setEnabled(false);
                    MainActivity.done.setEnabled(false);
                    Editor ed = MainActivity.pref.edit();
                    ed.putInt("tut", 1);
                    ed.commit();
                }
            }
        });


        MainActivity.this.colorback.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), Mode.MULTIPLY);
        MainActivity.this.b2.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.colorAccent));
        MainActivity.this.b3.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
        MainActivity.this.b4.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
        MainActivity.this.b5.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
        MainActivity.this.b6.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
        MainActivity.this.b7.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
        MainActivity.this.b8.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
        MainActivity.this.b9.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
        MainActivity.this.b10.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));
        MainActivity.this.b11.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.white));


        MainActivity.this.bitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.n1);
        MainActivity.this.bitmap = MainActivity.this.resizeBitmap(MainActivity.this.bitmap);
        MainActivity.this.image.setImageBitmap(MainActivity.this.bitmap);
        MainActivity.this.rel.setLayoutParams(new LayoutParams(MainActivity.this.bitmap.getWidth(), MainActivity.this.bitmap.getHeight()));
        MainActivity.this.rel.setBackgroundColor(getResources().getColor(R.color.white));
        if (MainActivity.pref.getInt("tut", 0) == 0) {
            MainActivity.backs_rel.setVisibility(View.GONE);
            MainActivity.bckbool = Boolean.valueOf(true);
            MainActivity.clickback.setVisibility(View.GONE);
            MainActivity.clickphoto.setVisibility(View.VISIBLE);
            MainActivity.backs.setEnabled(false);
            MainActivity.camgal.setEnabled(true);
            MainActivity.opacity.setEnabled(false);
            MainActivity.delete.setEnabled(false);
            MainActivity.done.setEnabled(false);
            Editor ed1 = MainActivity.pref.edit();
            ed1.putInt("tut", 1);
            ed1.commit();
        }
        bg_v1.setVisibility(View.VISIBLE);
        bg_v2.setVisibility(View.INVISIBLE);
        bg_v3.setVisibility(View.INVISIBLE);
        bg_v4.setVisibility(View.INVISIBLE);
        bg_v5.setVisibility(View.INVISIBLE);
        bg_v6.setVisibility(View.INVISIBLE);
        bg_v7.setVisibility(View.INVISIBLE);
        bg_v8.setVisibility(View.INVISIBLE);
        bg_v9.setVisibility(View.INVISIBLE);
        bg_v10.setVisibility(View.INVISIBLE);

    }

    private int selectedPosition = -1;
    private void PassIntent() {
        try {
            Intent intent = new Intent(this, EditActivity.class);
            intent.setAction("android.intent.action.MAIN");
            startActivityForResult(intent, 1);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("selected_position", selectedPosition);
            editor.apply();
            SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor1 = preferences1.edit();
            editor1.putInt("selected_position1", selectedPosition);
            editor1.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == 5) {
                        this.bitmap1 = CropActivity.bitmap;
                        addImage(this.bitmap1);

        } else if (requestCode == 6 && resultCode ==6) {
            this.bitmap1 = CropBackgroundActivity.bitmap;
            MainActivity.this.image.setImageBitmap(MainActivity.this.bitmap1);
            bg_v1.setVisibility(View.INVISIBLE);
            bg_v2.setVisibility(View.INVISIBLE);
            bg_v3.setVisibility(View.INVISIBLE);
            bg_v4.setVisibility(View.INVISIBLE);
            bg_v5.setVisibility(View.INVISIBLE);
            bg_v6.setVisibility(View.INVISIBLE);
            bg_v7.setVisibility(View.INVISIBLE);
            bg_v8.setVisibility(View.INVISIBLE);
            bg_v9.setVisibility(View.INVISIBLE);
            bg_v10.setVisibility(View.INVISIBLE);
        }
        else if (requestCode == 1 && resultCode == RESULT_OK){
            finish();
        }
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public void addImage(Bitmap bitmap1) {
        MainActivity.seek_rel.setVisibility(View.GONE);
        bitmap1 = resizeBitmap(bitmap1);
        RelativeLayout imrel = new RelativeLayout(getApplicationContext());
        ImageView im1 = new ImageView(getApplicationContext());
        im1.setImageBitmap(bitmap1);
        im1.setVisibility(View.INVISIBLE);
        imrel.addView(im1);
        ImageView im = new ImageView(getApplicationContext());
        sideblur_seek.setOnSeekBarChangeListener(null);
        int w = bitmap1.getWidth();
        int h = bitmap1.getHeight();
        if (h < w) {
            sideblur_seek.setMax(h / 3);
            sideblur_seek.setProgress((h / 3) / 2);
            this.sbr = (h / 3) / 2;
        } else if (w < h) {
            sideblur_seek.setMax(w / 3);
            sideblur_seek.setProgress((w / 3) / 2);
            this.sbr = (w / 3) / 2;
        } else {
            sideblur_seek.setMax(w / 3);
            sideblur_seek.setProgress((w / 3) / 2);
            this.sbr = (w / 3) / 2;
        }

        im.setImageBitmap(sideblur(bitmap1, this.sbr));
        im.setContentDescription(BuildConfig.FLAVOR + this.sbr);
        if (VERSION.SDK_INT <= 16) {
            im.setAlpha(125);
        } else {
            im.setImageAlpha(125);
        }
        imrel.addView(im);
        sideblur_seek.setOnSeekBarChangeListener(this.sideblurSeekBarChangeListener);
        imrel.setOnTouchListener(new MultiTouchListener1(this));
        backs.setColorFilter(ContextCompat.getColor(this, R.color.white), Mode.MULTIPLY);
        camgal.setColorFilter(ContextCompat.getColor(this, R.color.white), Mode.MULTIPLY);
        opacity.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), Mode.MULTIPLY);
        this.rel1.addView(imrel);
    }




    Bitmap sideblur(Bitmap bit, int br) {
        Bitmap resultingImage = Bitmap.createBitmap(bit.getWidth(), bit.getHeight(), bit.getConfig());
        Canvas canvas = new Canvas(resultingImage);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setMaskFilter(new BlurMaskFilter((float) br, Blur.NORMAL));
        Path path = new Path();
        path.moveTo((float) br, (float) br);
        path.lineTo((float) (canvas.getWidth() - br), (float) br);
        path.lineTo((float) (canvas.getWidth() - br), (float) (canvas.getHeight() - br));
        path.lineTo((float) br, (float) (canvas.getHeight() - br));
        path.lineTo((float) br, (float) br);
        path.close();
        canvas.drawPath(path, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bit, 0.0f, 0.0f, paint);
        return resultingImage;
    }

    Bitmap resizeBitmap(Bitmap bit) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float wr = (float) dm.widthPixels;
        float hr = ((float) dm.heightPixels) - ((float) this.forcalrel.getHeight());
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

    public void createShortCut() {
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutintent.putExtra("duplicate", false);
        shortcutintent.putExtra("android.intent.extra.shortcut.NAME", getString(R.string.app_name));
        shortcutintent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.icon200));
        shortcutintent.putExtra("android.intent.extra.shortcut.INTENT", new Intent(getApplicationContext(), MainActivity.class));
        sendBroadcast(shortcutintent);
    }

    private void removeImageGallery(File file) {
        try {
            getContentResolver().delete(Media.EXTERNAL_CONTENT_URI, "_data='" + file.getPath() + "'", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    protected void onResume() {
        super.onResume();
        isselected = false;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission(){
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return  result1;

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if ( storageAccepted){
                        done.setEnabled(true);
                        done.setBackgroundResource(R.drawable.bg_export);
                    }else {
                        Toast.makeText(this, ""+getText(R.string.per_miss), Toast.LENGTH_SHORT).show();
                        done.setEnabled(false);
                        done.setBackgroundResource(R.drawable.bg_export_none);
                    }

                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
