package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images.Media;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.photo.blender.blendphoto.mixphotos.doubleexposure.APPUtility;
import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SystemUtil;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.adapter.ColorAdapter;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.adapter.ColorAdapter2;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.adapter.EffectAdapter;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.adapter.FontAdapter;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.adapter.GalleryAdapter;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.adapter.GalleryAdapterSticker;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.adapter.GalleryAdapterEffect;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.bitmap.BitmapLoader;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.bitmap.BitmapProcessing;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.other.DisplayUtil;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.other.GPUImageNormalBlendFilterAlpha;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.other.StickerView;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.other.Util;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.tools.ImageUtils;
import com.commit451.nativestackblur.NativeStackBlur;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yalantis.ucrop.view.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImageAddBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageAlphaBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageChromaKeyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorDodgeBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDifferenceBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDissolveBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExclusionBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLightenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOverlayBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageScreenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSoftLightBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSubtractBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;
import me.grantland.widget.AutofitTextView;
import pl.aprilapps.easyphotopicker.BuildConfig;

public class EditActivity extends BaseActivity {
    private saveAndGo mSaveAndGoTask;

    private boolean isStickerBottomSheetVisible = false;
    private AutofitTextView afltext;

    private boolean mIsClick = false;
    private boolean blurFinished;
    private Bitmap bmblur;
    public static Bitmap bmmain;
    private Bitmap bmmask;
    private int center;
    private boolean checkTouch;
    private ColorAdapter2 colorAdapter2;
    private String colorsample;
    private int contrast = 0;
    private int dis;
    private EditText edtext;
    private EffectAdapter effectAdapter;
    private int exposure = 10;
    private String filter = "overlays/overlay_00000.png";
    private boolean firstTouch = false;
    private String fontsample;
    private GalleryAdapter galleryAdapter;
    private GalleryAdapterSticker galleryAdapter1;
    private  GalleryAdapterEffect galleryAdapterEffect;
    public static GPUImageView gpuview;
    private int highlight = 0;
    private ImageView iceffect;
    private ImageView icframe;
    private ImageView icrandom;
    private ImageView icsnap;
    private ImageView ictext;
    private boolean isNext;
    private ImageView ivalign;
    public static ImageView ivblur;
    private ImageView ivchangecontrast;
    private ImageView ivchangeexposure;
    private ImageView ivchangehighlight;
    private ImageView ivchangeshadow;
    private ImageView ivchangesharpen;
    private ImageView ivchangetemperature;
    private ImageView ivchangevignette;
    private ImageView ivcircle;
    private ImageView ivframe;
    private ImageView ivphoto;
    private int kindEdit = 0;
    private int lastTouchedPositionX;
    private int lastTouchedPositionY;
    private String[] listColor = new String[]{"#ffffff", "#d4d4d4", "#828282", "#515A5A", "#444444", "#000000", "#873600", "#9C640C", "#9A7D0A", "#1D8348", "#0E6655", "#1A5276", "#2980B9", "#5B2C6F", "#F08080", "#7B241C", "#CB4335", "#e60012", "#499157", "#f44444", "#d38f23", "#0099cc", "#f9d1fa", "#c3e2cc", "#50e3c2", "#f24c4c", "#ffa0f5", "#3ebde0", "#f8d9f7", "#e3ac55", "#00cc9e", "#cc5200", "#8b00cc", "#cc008b", "#a3cc00"};
    private String[] listItem;
    private String[] listfont;
    private LinearLayout llchange;
    private LinearLayout llcontrol;

    private boolean mShowLoader = true;
    private int mToolbarWidgetColor;
    private MenuItem menuItemCrop;
    private Drawable menuItemCropIconDone;
    private Drawable menuItemCropIconSave;
    private int precontrast = 0;
    private int preexposure = 6;
    private int prehighlight = 0;
    private int preshadow = 0;
    private int presharpen = 0;
    private int pretemperature = 6;
    private int prevignette = 0;
    private RelativeLayout rlblur;
    private RelativeLayout rlphoto;
    private RelativeLayout rlslider;
    private RelativeLayout rltext;
    public static RecyclerView rvselect;
    private RecyclerView rvtext;
    private String savePath;
    private SeekBar sbslider;
    private int shadow = 0;
    private int sharpen = 0;
    private int temperature = 6;
    private String textsample;
    private long timetouch;
    private TextView toolbarTitle;
    private TextView tvslider;
    private int type = 0;
    private int vignette = 0;
    private int widthScreen;
    private int wthumb;
    ImageView back;
    TextView tv_effect,tv_change,tv_frame,tv_sticker,tv_text;

    EditText et_text;
    TextView tv_text_sample;
    ImageView color,close,font,done;
    RecyclerView rcy_font;

    ConstraintLayout contro_view;

    LinearLayout ln_effect,ln_change,ln_frame,ln_sticker,ln_text;
    TextView done_text;
    private int selectedPosition = -1;
    private SharedPreferences preferences;
    private static final String PREF_SELECTED_POSITION = "selected_position";

    class BlurTask extends AsyncTask<Void, Void, Bitmap> {
        private int maskPosX;
        private int maskPosY;

        public BlurTask(int maskPosX, int maskPosY) {
            this.maskPosX = maskPosX;
            this.maskPosY = maskPosY;
        }

        protected void onPreExecute() {
            EditActivity.this.blurFinished = false;
        }

        protected Bitmap doInBackground(Void... dataArr) {
            try {
                Bitmap sharp = BitmapProcessing.applyMask(EditActivity.this.bmmain, EditActivity.this.bmmask, this.maskPosX, this.maskPosY);
                EditActivity.this.bmblur = NativeStackBlur.process(EditActivity.this.bmmain,8);
                new Canvas(EditActivity.this.bmblur).drawBitmap(sharp, (float) this.maskPosX, (float) this.maskPosY, new Paint());
                sharp.recycle();
                return EditActivity.this.bmblur;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(Bitmap result) {
            EditActivity.this.ivblur.setImageBitmap(result);
            EditActivity.this.blurFinished = true;
        }
    }

    class saveAndGo extends AsyncTask<Void, Void, String> {
        private boolean mCancelled;

        saveAndGo() {
        }

        protected void onPreExecute() {
            final Dialog dialog = new Dialog(EditActivity.this);
            dialog.setContentView(R.layout.layout_dialog_export);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();
            EditActivity.this.mShowLoader = true;
        }

        protected String doInBackground(Void... dataArr) {
            while (!mCancelled){
                if (isCancelled()) {
                    break;
                }
                return BuildConfig.FLAVOR;
            }
            return null;
        }

        protected void onPostExecute(String result) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String path = storeImage(viewToBitmap());
                    Intent intent = new Intent(EditActivity.this, SaveActivity.class);
                    intent.putExtra("path", path);
                    startActivity(intent);
                    setResult(RESULT_OK);
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EditActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("selected_position", selectedPosition);
                    editor.apply();
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
                    finish();
                }
            }, 1000);
        }

        @Override
        protected void onCancelled() {
            mCancelled = true;
            super.onCancelled();
        }
    }

    public static Bitmap ddd;
    public static Bitmap bmOverlay;
    public static Bitmap b;

    private Bitmap viewToBitmap() {
        try {
            b = Bitmap.createBitmap(rlphoto.getWidth(), rlphoto.getHeight(), Bitmap.Config.ARGB_8888);
            rlphoto.draw(new Canvas(b));

        } finally {
            rlphoto.destroyDrawingCache();
        }
        ddd = gpuview.getGPUImage().getBitmapWithFilterApplied();


        ddd = Bitmap.createScaledBitmap(ddd, b.getWidth(), b.getHeight(), false);
        bmOverlay = Bitmap.createBitmap(ddd.getWidth(), ddd.getHeight(), ddd.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(ddd, new Matrix(), null);
        canvas.drawBitmap(b, 0, 0, null);
        return bmOverlay;
    }

    private String storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.e("save",
                    "Error creating media file, check storage permissions: ");
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("save1", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.e("save2", "Error accessing file: " + e.getMessage());
        }
        return pictureFile.toString();
    }


    private File getOutputMediaFile() {
        File mediaStorageDir = new File(APPUtility.getAppDir() + "/" + getResources().getString(R.string.app_name));

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "BlendPhoto-" + timeStamp + ".jpg";

        // Kiểm tra xem file đã tồn tại chưa, nếu có thì thêm số đếm vào tên file
        int count = 1;
        while (new File(mediaStorageDir.getPath() + File.separator + mImageName).exists()) {
            mImageName = "BlendPhoto-" + timeStamp + "_" + count + ".jpg";
            count++;
        }

        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
    int sh, sw;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtil.setLocale(this);
        setContentView(R.layout.activity_edit);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tv_effect = findViewById(R.id.tv_effect);
//        tv_change = findViewById(R.id.tv_change);
        tv_frame = findViewById(R.id.tv_frame);
        tv_sticker = findViewById(R.id.tv_sticker);
        tv_text = findViewById(R.id.tv_text);
        done_text = findViewById(R.id.done_text);


        ln_effect = findViewById(R.id.ln_effect);
//        ln_change = findViewById(R.id.ln_change);
        ln_frame = findViewById(R.id.ln_frame);
        ln_sticker = findViewById(R.id.ln_sticker);
        ln_text = findViewById(R.id.ln_text);

        done_text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSave();
            }
        });


        Intent intent = getIntent();
        this.isNext = false;
        this.widthScreen = DisplayUtil.getDisplayWidthPixels(this);
        DisplayMetrics dimension = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimension);
        this.sw = dimension.widthPixels;
        this.sh = dimension.heightPixels - ImageUtils.dpToPx(this, 135);

        bmmain = MainActivity.resultBitmap;
        this.menuItemCropIconDone = ContextCompat.getDrawable(this, R.drawable.ucrop_ic_done);
        this.menuItemCropIconDone.mutate();
        this.menuItemCropIconDone.setColorFilter(this.mToolbarWidgetColor, Mode.SRC_ATOP);
        this.menuItemCropIconSave = ContextCompat.getDrawable(this, R.drawable.small);
        this.menuItemCropIconSave.mutate();
        this.menuItemCropIconSave.setColorFilter(this.mToolbarWidgetColor, Mode.SRC_ATOP);
        this.rlphoto = (RelativeLayout) findViewById(R.id.rlphoto);
        LayoutParams layoutParams = new LayoutParams(this.widthScreen, this.widthScreen);
        layoutParams.addRule(13);
        this.rlphoto.setLayoutParams(layoutParams);
        this.gpuview = (GPUImageView) findViewById(R.id.gpuview);
        this.gpuview.setImage(this.bmmain);
        this.ivphoto = (ImageView) findViewById(R.id.ivphoto);
        this.bmmask = BitmapLoader.loadFromResource(this, new int[]{300, 300}, R.drawable.mask);
        this.bmmask = BitmapProcessing.resizeBitmap(this.bmmask, (this.widthScreen * 2) / 5, (this.widthScreen * 2) / 5);
        this.blurFinished = true;
        this.rvselect = (RecyclerView) findViewById(R.id.rvselect);


        this.ivframe = (ImageView) findViewById(R.id.ivframe);
        this.ivframe.setTag("0");
        this.rvselect.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));




        back = findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EditActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("selected_position", selectedPosition);
                onBackPressed();
                finish();
            }
        });
        this.icframe = (ImageView) findViewById(R.id.icframe);
        ln_frame.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                rvselect.setVisibility(View.VISIBLE);
                if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv_effect.setTextColor(getColor(R.color.color_text));
//                    tv_change.setTextColor(getColor(R.color.color_text));
                    tv_frame.setTextColor(getColor(R.color.color_app));
                    tv_sticker.setTextColor(getColor(R.color.color_text));
                    tv_text.setTextColor(getColor(R.color.color_text));
                }
                iceffect.setImageResource(R.drawable.effect);
                icframe.setImageResource(R.drawable.fram_ic);
//                icrandom.setImageResource(R.drawable.change);
                icsnap.setImageResource(R.drawable.sticker);
                ictext.setImageResource(R.drawable.insert_text);
                indstrialads();

                EditActivity.this.rlslider.setVisibility(View.GONE);
                EditActivity.this.loadFrame();


            }
        });

        this.iceffect = (ImageView) findViewById(R.id.iceffect);

        iceffect.setImageResource(R.drawable.effect_ic);
        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv_effect.setTextColor(getColor(R.color.color_app));
        }
        ln_effect.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
//                preferences = PreferenceManager.getDefaultSharedPreferences(EditActivity.this);
//
//                // Restore the selected position from SharedPreferences
//                selectedPosition = preferences.getInt(PREF_SELECTED_POSITION, -1);

                rvselect.setVisibility(View.VISIBLE);
                if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv_effect.setTextColor(getColor(R.color.color_app));
//                    tv_change.setTextColor(getColor(R.color.color_text));
                    tv_frame.setTextColor(getColor(R.color.color_text));
                    tv_sticker.setTextColor(getColor(R.color.color_text));
                    tv_text.setTextColor(getColor(R.color.color_text));
                }
                iceffect.setImageResource(R.drawable.effect_ic);
                icframe.setImageResource(R.drawable.filter_frames);
//                icrandom.setImageResource(R.drawable.change);
                icsnap.setImageResource(R.drawable.sticker);
                ictext.setImageResource(R.drawable.insert_text);

                EditActivity.this.rlslider.setVisibility(View.GONE);
                EditActivity.this.loadGalaxy();


            }
        });


        this.icsnap = (ImageView) findViewById(R.id.icsnap);
        ln_sticker.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(EditActivity.this);
                View dialog = LayoutInflater.from(EditActivity.this).inflate(R.layout.layout_bottom_sheet,null);
                bottomSheetDialog.setContentView(dialog);
                if (!isStickerBottomSheetVisible ){
                    isStickerBottomSheetVisible = true;
                    Log.d("isClickStick", "isClickStick = true");
                    rvselect.setVisibility(View.INVISIBLE);
                    if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv_effect.setTextColor(getColor(R.color.color_text));
//                        tv_change.setTextColor(getColor(R.color.color_text));
                        tv_frame.setTextColor(getColor(R.color.color_text));
                        tv_sticker.setTextColor(getColor(R.color.color_app));
                        tv_text.setTextColor(getColor(R.color.color_text));
                    }
                    iceffect.setImageResource(R.drawable.effect);
                    icframe.setImageResource(R.drawable.filter_frames);
//                    icrandom.setImageResource(R.drawable.change);
                    icsnap.setImageResource(R.drawable.sticker_ic);
                    ictext.setImageResource(R.drawable.insert_text);
                    indstrialads();
                    RecyclerView rcy_bottom;

                    rcy_bottom = dialog.findViewById(R.id.rcy_bottom);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(EditActivity.this,4,RecyclerView.VERTICAL,false);
                    rcy_bottom.setLayoutManager(gridLayoutManager);
                    rcy_bottom.setHasFixedSize(true);

                    try {
                        listItem = getAssets().list("stickers");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (listItem != null) {
                        List<String> tmplist = new ArrayList();
                        for (String aListItem : listItem) {

                            tmplist.add("stickers/" + aListItem);

                        }
                        listItem = (String[]) tmplist.toArray(new String[tmplist.size()]);
                        galleryAdapter1 = new GalleryAdapterSticker(listItem, EditActivity.this);
                        rcy_bottom.setAdapter(galleryAdapter1);
                        galleryAdapter1.setOnItemClickListener(new GalleryAdapterSticker.OnRecyclerViewItemClickListener() {
                            public void onItemClick(View view, String resId) {
                                if (EditActivity.this.rltext.getVisibility() != View.GONE || EditActivity.this.rlblur.getVisibility() != View.GONE || EditActivity.this.llchange.getVisibility() != View.GONE) {
                                    return;
                                }
                                if (resId.contains("frame_")) {
                                    EditActivity.this.addFrame(resId);
                                } else if (resId.contains("st")) {
                                    EditActivity.this.addStickerItem(resId);
                                } else if (resId.contains("text_")) {
                                    EditActivity.this.addABC(resId);
                                } else if (resId.contains("emoji_")) {
                                    EditActivity.this.addStickerItem(resId);
                                } else if (resId.contains("overlay_")) {
                                    EditActivity.this.filter = resId;
                                    EditActivity.this.ivframe.setTag("0");
                                    EditActivity.this.gpuEffect();
                                }
                                bottomSheetDialog.dismiss();
                            }
                        });
                    }
                    EditActivity.this.rlslider.setVisibility(View.GONE);
                    bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            isStickerBottomSheetVisible = false;
                        }
                    });
                    bottomSheetDialog.show();
//                EditActivity.this.loadSnap();

                }
            }
        });




//        this.icrandom = (ImageView) findViewById(R.id.icrandom);
//        ln_change.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                rvselect.setVisibility(View.INVISIBLE);
//                if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    tv_effect.setTextColor(getColor(R.color.color_text));
//                    tv_change.setTextColor(getColor(R.color.color_app));
//                    tv_frame.setTextColor(getColor(R.color.color_text));
//                    tv_sticker.setTextColor(getColor(R.color.color_text));
//                    tv_text.setTextColor(getColor(R.color.color_text));
//                }
//                iceffect.setImageResource(R.drawable.effect);
//                icframe.setImageResource(R.drawable.filter_frames);
//                icrandom.setImageResource(R.drawable.change1);
//                icsnap.setImageResource(R.drawable.sticker);
//                ictext.setImageResource(R.drawable.insert_text);
//                indstrialads();
//                EditActivity.this.gpuEffect();
//
//
//            }
//        });
        this.ictext = (ImageView) findViewById(R.id.ictext);
        ln_text.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!isStickerBottomSheetVisible){
                    isStickerBottomSheetVisible = true;
                    rvselect.setVisibility(View.INVISIBLE);
                    if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv_effect.setTextColor(getColor(R.color.color_text));
//                        tv_change.setTextColor(getColor(R.color.color_text));
                        tv_frame.setTextColor(getColor(R.color.color_text));
                        tv_sticker.setTextColor(getColor(R.color.color_text));
                        tv_text.setTextColor(getColor(R.color.color_app));
                    }
                    iceffect.setImageResource(R.drawable.effect);
                    icframe.setImageResource(R.drawable.filter_frames);
//                    icrandom.setImageResource(R.drawable.change);
                    icsnap.setImageResource(R.drawable.sticker);
                    ictext.setImageResource(R.drawable.text_ic);
                    indstrialads();
                    ImageView keyboard;

                    final Dialog dialog = new Dialog(EditActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.layout_text);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.setCancelable(false);
                    et_text = dialog.findViewById(R.id.et_text);
                    tv_text_sample = dialog.findViewById(R.id.tv_text_sample);
                    color = dialog.findViewById(R.id.color);
                    close = dialog.findViewById(R.id.close);
                    font = dialog.findViewById(R.id.font);
                    rcy_font = dialog.findViewById(R.id.rcy_font);
                    done = dialog.findViewById(R.id.done);
                    keyboard = dialog.findViewById(R.id.keyboard);

                    //hiện kayboard
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                    rcy_font.setVisibility(View.GONE);
                    keyboard.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rcy_font.setVisibility(View.GONE);
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                            et_text.requestFocus();
                            keyboard.setImageResource(R.drawable.ic_keyboard1);
                            color.setImageResource(R.drawable.palette);
                            font.setImageResource(R.drawable.ic_front);
                        }
                    });
                    done.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = tv_text_sample.getText().toString().trim();
                            if (text.isEmpty()||text.equals("")){
                                Toast.makeText(EditActivity.this, ""+getText(R.string.text_null), Toast.LENGTH_SHORT).show();
                            }else {
                                textsample = tv_text_sample.getText().toString();
                                tv_text_sample.setDrawingCacheEnabled(true);
                                tv_text_sample.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                                Bitmap bitmap = Bitmap.createBitmap(tv_text_sample.getDrawingCache());
                                tv_text_sample.setDrawingCacheEnabled(false);
                                addText(bitmap);
                                final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(done.getWindowToken(), 0);
                                dialog.dismiss();
                            }


                        }
                    });
                    font.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rcy_font.setVisibility(View.VISIBLE);
                            keyboard.setImageResource(R.drawable.ic_keyboard);
                            color.setImageResource(R.drawable.palette);
                            font.setImageResource(R.drawable.ic_front1);
                            final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(font.getWindowToken(), 0);
                            rcy_font.setHasFixedSize(true);
                            try {
                                listfont = getAssets().list("fonts");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (listfont != null) {
                                for (int i = 0; i < listfont.length; i++) {
                                    listfont[i] = "fonts/" + listfont[i];
                                }
                                FontAdapter fontAdapter = new FontAdapter(listfont, EditActivity.this);
                                rcy_font.setAdapter(fontAdapter);
                                fontAdapter.setOnItemClickListener(new FontAdapter.OnRecyclerViewItemClickListener() {
                                    public void onItemClick(View view, String resId) {
                                        loadSampleText1(BuildConfig.FLAVOR,resId,BuildConfig.FLAVOR);
                                    }
                                });
                            }
                        }
                    });

                    close.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(close.getWindowToken(), 0);
                            dialog.dismiss();


                        }
                    });
                    color.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(color.getWindowToken(), 0);
                            rcy_font.setVisibility(View.GONE);
                            keyboard.setImageResource(R.drawable.ic_keyboard);
                            color.setImageResource(R.drawable.palette1);
                            font.setImageResource(R.drawable.ic_front);
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                            ColorPickerDialogBuilder
                                    .with(EditActivity.this)
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
                                            tv_text_sample.setTextColor(selectedColor);
                                            et_text.setFocusable(true);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putInt("background_color", selectedColor);
                                            editor.apply();
                                        }
                                    })
                                    .setNegativeButton(""+getText(R.string.cancel), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            et_text.setFocusable(true);
                                        }
                                    })
                                    .build()
                                    .show();
                        }
                    });
                    et_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {
                            if (b){
                                keyboard.setImageResource(R.drawable.ic_keyboard1);
                                color.setImageResource(R.drawable.ic_color);
                                font.setImageResource(R.drawable.ic_front);
                            }else {
                                keyboard.setImageResource(R.drawable.ic_keyboard1);
                                color.setImageResource(R.drawable.ic_color);
                                font.setImageResource(R.drawable.ic_front);
                            }
                        }
                    });

                    et_text.addTextChangedListener(new TextWatcher() {
                        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        }

                        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                            tv_text_sample.setText(charSequence.toString());
                        }

                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            isStickerBottomSheetVisible = false;
                        }
                    });
                    dialog.show();
                }
            }
        });
        this.rltext = (RelativeLayout) findViewById(R.id.rltext);
        this.rvtext = (RecyclerView) findViewById(R.id.rvtext);
        this.afltext = (AutofitTextView) findViewById(R.id.afltext);
        this.edtext = (EditText) findViewById(R.id.edtext);
        ((ImageView) findViewById(R.id.ivchangetext)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditActivity.this.rvtext.setVisibility(View.GONE);
                EditActivity.this.edtext.setVisibility(View.VISIBLE);
                EditActivity.this.edtext.requestFocus();
                ((InputMethodManager) EditActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(EditActivity.this.edtext, 1);
            }
        });
        this.edtext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));
        this.edtext.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                EditActivity.this.afltext.setText(charSequence.toString());
            }

            public void afterTextChanged(Editable editable) {
            }
        });
        ((ImageView) findViewById(R.id.ivchangefont)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditActivity.this.rvtext.setVisibility(View.VISIBLE);
                EditActivity.this.edtext.setVisibility(View.GONE);
                EditActivity.this.closeKeyboard();
                EditActivity.this.rvtext.setLayoutManager(new LinearLayoutManager(EditActivity.this, RecyclerView.HORIZONTAL, false));
                EditActivity.this.loadFont();
            }
        });
        ((ImageView) findViewById(R.id.ivchangecolor)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditActivity.this.rvtext.setVisibility(View.VISIBLE);
                EditActivity.this.edtext.setVisibility(View.GONE);
                EditActivity.this.closeKeyboard();
                EditActivity.this.rvtext.setLayoutManager(new LinearLayoutManager(EditActivity.this, RecyclerView.HORIZONTAL, false));
                ColorAdapter colorAdapter = new ColorAdapter(EditActivity.this.listColor, EditActivity.this);
                EditActivity.this.rvtext.setAdapter(colorAdapter);
                colorAdapter.setOnItemClickListener(new ColorAdapter.OnRecyclerViewItemClickListener() {
                    public void onItemClick(View view, String resId) {
                        EditActivity.this.loadSampleText(resId, BuildConfig.FLAVOR, BuildConfig.FLAVOR);
                    }
                });
            }
        });
        this.ivalign = (ImageView) findViewById(R.id.ivalign);
        this.ivalign.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditActivity.this.setAlignText();
            }
        });
        this.ivcircle = (ImageView) findViewById(R.id.ivcircle);
        this.ivcircle.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditActivity.this.setCircleText();
            }
        });
        this.rlblur = (RelativeLayout) findViewById(R.id.rlblur);
        this.ivblur = (ImageView) findViewById(R.id.ivblur);
        this.ivblur.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                EditActivity.this.lastTouchedPositionX = (int) event.getX();
                EditActivity.this.lastTouchedPositionY = (int) event.getY();
                EditActivity.this.refreshImageView();
                return true;
            }
        });
        this.ivchangeexposure = (ImageView) findViewById(R.id.ivchangeexposure);
        this.ivchangecontrast = (ImageView) findViewById(R.id.ivchangecontrast);
        this.ivchangesharpen = (ImageView) findViewById(R.id.ivchangesharpen);
        this.ivchangetemperature = (ImageView) findViewById(R.id.ivchangetemperature);
        this.ivchangehighlight = (ImageView) findViewById(R.id.ivchangehighlight);
        this.ivchangeshadow = (ImageView) findViewById(R.id.ivchangeshadow);
        this.ivchangevignette = (ImageView) findViewById(R.id.ivchangevignette);
        this.llcontrol = (LinearLayout) findViewById(R.id.llcontrol);
        this.llchange = (LinearLayout) findViewById(R.id.llchange);
        this.rlslider = (RelativeLayout) findViewById(R.id.rlslider);
        this.sbslider = (SeekBar) findViewById(R.id.sbslider);
        this.tvslider = (TextView) findViewById(R.id.tvslider);
        loadPointforSlider();
        this.ivchangeexposure.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditActivity.this.type = 1;
                EditActivity.this.setTextSlider(EditActivity.this.exposure);
                EditActivity.this.setChange();
            }
        });
        this.ivchangecontrast.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditActivity.this.type = 2;
                EditActivity.this.setTextSlider(EditActivity.this.contrast);
                EditActivity.this.setChange();
            }
        });
        this.ivchangesharpen.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditActivity.this.type = 3;
                EditActivity.this.setTextSlider(EditActivity.this.sharpen);
                EditActivity.this.setChange();
            }
        });
        this.ivchangehighlight.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditActivity.this.type = 4;
                EditActivity.this.setTextSlider(EditActivity.this.highlight);
                EditActivity.this.setChange();
            }
        });
        this.ivchangeshadow.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditActivity.this.type = 5;
                EditActivity.this.setTextSlider(EditActivity.this.shadow);
                EditActivity.this.setChange();
            }
        });
        this.ivchangetemperature.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditActivity.this.type = 6;
                EditActivity.this.setTextSlider(EditActivity.this.temperature);
                EditActivity.this.setChange();
            }
        });
        this.ivchangevignette.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditActivity.this.type = 7;
                EditActivity.this.setTextSlider(EditActivity.this.vignette);
                EditActivity.this.setChange();
            }
        });

        EditActivity.this.rlslider.setVisibility(View.GONE);
        EditActivity.this.loadGalaxy();
        gpuEffect();
        addFrame("frames/frame_00000.png");
        loadPopup();
    }


    int countvideo = 2;
    ProgressDialog dialog;


    void indstrialads() {


        if (countvideo == 4) {
            countvideo = 0;
        }
        if (countvideo == 0) {


        }
        countvideo = +countvideo + 1;

    }

    @Override
    protected void onStart() {
        super.onStart();
        onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
//        if (mSaveAndGoTask != null) {
//            mSaveAndGoTask.cancel(true);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSaveAndGoTask != null && mSaveAndGoTask.getStatus() == AsyncTask.Status.RUNNING) {
            final Dialog dialog = new Dialog(EditActivity.this);
            dialog.setContentView(R.layout.layout_dialog_export);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();
            EditActivity.this.mShowLoader = true;
        } else {
            // AsyncTask đã hoàn thành hoặc chưa bắt đầu, không cần hiển thị Dialog
            EditActivity.this.mShowLoader = false;
        }

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }



    private void setAlignText() {
        if (this.ivalign.getTag().equals(Integer.valueOf(1))) {
            this.afltext.setGravity(3);
            this.ivalign.setImageResource(R.drawable.icalignleft);
            this.ivalign.setTag(Integer.valueOf(2));
        } else if (this.ivalign.getTag().equals(Integer.valueOf(2))) {
            this.afltext.setGravity(5);
            this.ivalign.setImageResource(R.drawable.icalignright);
            this.ivalign.setTag(Integer.valueOf(3));
        } else if (this.ivalign.getTag().equals(Integer.valueOf(3))) {
            this.afltext.setGravity(17);
            this.ivalign.setImageResource(R.drawable.iccentertextalignment);
            this.ivalign.setTag(Integer.valueOf(1));
        }
    }

    private void setCircleText() {
        if (this.ivcircle.getTag().equals(Integer.valueOf(0))) {
            this.ivcircle.setImageResource(R.drawable.iccirclepressed);
            this.ivcircle.setTag(Integer.valueOf(1));
            this.afltext.setShadowLayer(1.6f, StickerView.MAX_SCALE_SIZE, StickerView.MAX_SCALE_SIZE, -1);
        } else if (this.ivcircle.getTag().equals(Integer.valueOf(1))) {
            this.ivcircle.setTag(Integer.valueOf(0));
            this.ivcircle.setImageResource(R.drawable.iccircle);
            this.afltext.setShadowLayer(0.0f, 0.0f, 0.0f, -1);
        }
    }

    private void loadPopup() {

    }
    private void setClick2() {
        this.galleryAdapterEffect.setOnItemClickListener(new GalleryAdapterEffect.OnRecyclerViewItemClickListener() {
            public void onItemClick(View view, String resId) {
                if (EditActivity.this.rltext.getVisibility() != View.GONE || EditActivity.this.rlblur.getVisibility() != View.GONE || EditActivity.this.llchange.getVisibility() != View.GONE) {
                    return;
                }
                if (resId.contains("frame_")) {
                    EditActivity.this.addFrame(resId);
                } else if (resId.contains("st")) {
                    EditActivity.this.addStickerItem(resId);
                } else if (resId.contains("text_")) {
                    EditActivity.this.addABC(resId);
                } else if (resId.contains("emoji_")) {
                    EditActivity.this.addStickerItem(resId);
                } else if (resId.contains("overlay_")) {
                    EditActivity.this.filter = resId;
                    EditActivity.this.ivframe.setTag("0");
                    EditActivity.this.gpuEffect();
                }

            }
        });
    }

    private void setClick() {
        this.galleryAdapter.setOnItemClickListener(new GalleryAdapter.OnRecyclerViewItemClickListener() {
            public void onItemClick(View view, String resId) {
                if (EditActivity.this.rltext.getVisibility() != View.GONE || EditActivity.this.rlblur.getVisibility() != View.GONE || EditActivity.this.llchange.getVisibility() != View.GONE) {
                    return;
                }
                if (resId.contains("frame_")) {
                    EditActivity.this.addFrame(resId);
                } else if (resId.contains("st")) {
                    EditActivity.this.addStickerItem(resId);
                } else if (resId.contains("text_")) {
                    EditActivity.this.addABC(resId);
                } else if (resId.contains("emoji_")) {
                    EditActivity.this.addStickerItem(resId);
                } else if (resId.contains("overlay_")) {
                    EditActivity.this.filter = resId;
                    EditActivity.this.ivframe.setTag("0");
                    EditActivity.this.gpuEffect();
                }
            }
        });
    }
    private void setClick1() {
        this.galleryAdapter1.setOnItemClickListener(new GalleryAdapterSticker.OnRecyclerViewItemClickListener() {
            public void onItemClick(View view, String resId) {
                if (EditActivity.this.rltext.getVisibility() != View.GONE || EditActivity.this.rlblur.getVisibility() != View.GONE || EditActivity.this.llchange.getVisibility() != View.GONE) {
                    return;
                }
                if (resId.contains("frame_")) {
                    EditActivity.this.addFrame(resId);
                } else if (resId.contains("st")) {
                    EditActivity.this.addStickerItem(resId);
                } else if (resId.contains("text_")) {
                    EditActivity.this.addABC(resId);
                } else if (resId.contains("emoji_")) {
                    EditActivity.this.addStickerItem(resId);
                } else if (resId.contains("overlay_")) {
                    EditActivity.this.filter = resId;
                    EditActivity.this.ivframe.setTag("0");
                    EditActivity.this.gpuEffect();
                }
            }
        });
    }

    private void effectClick() {
        this.effectAdapter.setOnItemClickListener(new EffectAdapter.OnRecyclerViewItemClickListener() {
            public void onItemClick(View view, String resId) {
                if (EditActivity.this.rltext.getVisibility() == View.GONE && EditActivity.this.rlblur.getVisibility() == View.GONE && EditActivity.this.llchange.getVisibility() == View.GONE && resId.contains("thumb_effect_")) {
                    EditActivity.this.filter = resId;
                    EditActivity.this.gpuEffect();
                }
            }
        });
    }

    @SuppressLint({"PrivateResource"})
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ucrop_menu_activity, menu);
        MenuItem menuItemLoader = menu.findItem(R.id.menu_loader);
        Drawable menuItemLoaderIcon = menuItemLoader.getIcon();
        if (menuItemLoaderIcon != null) {
            try {
                menuItemLoaderIcon.mutate();
                menuItemLoaderIcon.setColorFilter(this.mToolbarWidgetColor, Mode.SRC_ATOP);
                menuItemLoader.setIcon(menuItemLoaderIcon);
            } catch (IllegalStateException e) {
                Log.i("Photos to Collage", e.getMessage());
            }
            ((Animatable) menuItemLoader.getIcon()).start();
        }
        this.menuItemCrop = menu.findItem(R.id.menu_crop);
        this.menuItemCrop.setIcon(this.menuItemCropIconSave);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_crop).setVisible(!this.mShowLoader);
        menu.findItem(R.id.menu_loader).setVisible(this.mShowLoader);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.kindEdit == 0) {
            if (item.getItemId() == R.id.menu_crop) {
                this.isNext = true;
                clickSave();
            } else if (item.getItemId() == 16908332) {
                this.isNext = false;

                finish();
            }
        } else if (this.kindEdit == 1) {
            if (item.getItemId() == R.id.menu_crop) {
                this.textsample = this.afltext.getText().toString();
                if (!this.textsample.equals(BuildConfig.FLAVOR)) {
                    this.afltext.setDrawingCacheEnabled(true);
                    this.afltext.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                    Bitmap bitmap = Bitmap.createBitmap(this.afltext.getDrawingCache());
                    this.afltext.setDrawingCacheEnabled(false);
                    boolean checkEdit = false;
                    int i = 0;
                    while (i < this.rlphoto.getChildCount()) {
                        try {
                            if ((this.rlphoto.getChildAt(i) instanceof StickerView) && ((StickerView) this.rlphoto.getChildAt(i)).isEdit()) {
                                checkEdit = true;
                                ((StickerView) this.rlphoto.getChildAt(i)).setEdit(false);
                                ((StickerView) this.rlphoto.getChildAt(i)).setText(this.textsample);
                                ((StickerView) this.rlphoto.getChildAt(i)).setColor(this.colorsample);
                                ((StickerView) this.rlphoto.getChildAt(i)).setFont(this.fontsample);
                                ((StickerView) this.rlphoto.getChildAt(i)).setAlign(((Integer) this.ivalign.getTag()).intValue());
                                ((StickerView) this.rlphoto.getChildAt(i)).setCircle(((Integer) this.ivcircle.getTag()).intValue());
                                ((StickerView) this.rlphoto.getChildAt(i)).setWaterMark(bitmap, false);
                                break;
                            }
                        } catch (Exception e) {
                            Log.i("Photos to Collage", e.getMessage());
                        }
                        i++;
                    }
                    if (!checkEdit) {
                        addText(bitmap);
                    }
                }
            } else if (item.getItemId() == 16908332) {
                resetEditSticker();
            }
            this.rltext.setVisibility(View.GONE);
            this.kindEdit = 0;
            this.toolbarTitle.setText(getResources().getString(R.string.editphoto));
            clickable(true);
            closeKeyboard();
        } else if (this.kindEdit == 2) {
            if (item.getItemId() == R.id.menu_crop) {
                this.gpuview.setImage(this.bmblur);
            }
            this.rlblur.setVisibility(View.GONE);
            this.ivblur.setVisibility(View.GONE);
            this.kindEdit = 0;
            this.toolbarTitle.setText(getResources().getString(R.string.editphoto));
            clickable(true);
        } else if (this.kindEdit == 3) {
            if (item.getItemId() == R.id.menu_crop) {
                this.preexposure = this.exposure;
                this.precontrast = this.contrast;
                this.prehighlight = this.highlight;
                this.preshadow = this.shadow;
                this.presharpen = this.shadow;
                this.pretemperature = this.temperature;
                this.prevignette = this.vignette;
            } else {
                this.exposure = this.preexposure;
                this.contrast = this.precontrast;
                this.highlight = this.prehighlight;
                this.shadow = this.preshadow;
                this.sharpen = this.presharpen;
                this.temperature = this.pretemperature;
                this.vignette = this.prevignette;
                gpuEffect();
            }
            this.llcontrol.setVisibility(View.VISIBLE);
            this.rvselect.setVisibility(View.VISIBLE);
            this.llchange.setVisibility(View.GONE);
            this.rlslider.setVisibility(View.GONE);
            this.kindEdit = 0;
            this.type = 0;
            this.toolbarTitle.setText(getResources().getString(R.string.editphoto));
            clickable(true);
        }
        this.menuItemCrop.setIcon(this.menuItemCropIconSave);
        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            if (this.kindEdit == 0) {
                this.isNext = false;

                finish();
            } else if (this.kindEdit == 1) {
                resetEditSticker();
                this.kindEdit = 0;
                this.rltext.setVisibility(View.GONE);
                this.toolbarTitle.setText(getResources().getString(R.string.editphoto));
                clickable(true);
                this.menuItemCrop.setIcon(this.menuItemCropIconSave);
                return false;
            } else if (this.kindEdit == 2) {
                this.kindEdit = 0;
                this.rlblur.setVisibility(View.GONE);
                this.ivblur.setVisibility(View.GONE);
                this.toolbarTitle.setText(getResources().getString(R.string.editphoto));
                clickable(true);
                this.menuItemCrop.setIcon(this.menuItemCropIconSave);
                return false;
            } else if (this.kindEdit == 3) {
                this.exposure = this.preexposure;
                this.contrast = this.precontrast;
                this.highlight = this.prehighlight;
                this.shadow = this.preshadow;
                this.sharpen = this.preshadow;
                this.temperature = this.pretemperature;
                this.vignette = this.prevignette;
                gpuEffect();
                this.kindEdit = 0;
                this.type = 0;
                this.llcontrol.setVisibility(View.VISIBLE);
                this.rvselect.setVisibility(View.VISIBLE);
                this.llchange.setVisibility(View.GONE);
                this.rlslider.setVisibility(View.GONE);
                this.toolbarTitle.setText(getResources().getString(R.string.editphoto));
                clickable(true);
                this.menuItemCrop.setIcon(this.menuItemCropIconSave);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }



    private void clickSave() {
        new saveAndGo().execute(new Void[0]);
    }


    private java.lang.String savePhoto() {
        return null;
    }

    private void addImageGallery(String url) {
        ContentValues values = new ContentValues();
        values.put("_data", url);
        values.put("mime_type", "image/jpeg");
        getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
    }

    private String pathtoSave() {
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            return getFilesDir() + File.separator + Util.ALBUM;
        }
        try {
            return Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + Util.ALBUM;
        } catch (Exception e) {
            return Environment.getExternalStorageDirectory() + File.separator + Util.ALBUM;
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 102 :
                if (grantResults[0] == 0) {
                    clickSave();
                    return;
                }
                return;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                return;
        }
    }

    private void addStickerItem(String resId) {
        StickerView stickerView = new StickerView((Context) this, false);
        LayoutParams params = new LayoutParams(-1, -1);
        params.addRule(8, pl.aprilapps.easyphotopicker.R.id.image);
        params.addRule(6, pl.aprilapps.easyphotopicker.R.id.image);
        this.rlphoto.addView(stickerView, params);
        Bitmap bitmap = BitmapLoader.loadFromAsset(this, new int[]{720, 720}, resId.replace("thumb_", BuildConfig.FLAVOR));
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            int width = this.widthScreen / 2;
            bitmap = BitmapProcessing.resizeBitmap(bitmap, width, (bitmap.getHeight() * width) / bitmap.getWidth());
        } else {
            int height = this.widthScreen / 2;
            bitmap = BitmapProcessing.resizeBitmap(bitmap, (bitmap.getWidth() * height) / bitmap.getHeight(), height);
        }
        stickerView.setWaterMark(bitmap, true);
        stickerView.setTag(resId);
    }

    private void addFrame(String resId) {
        if (resId.contains("00000")) {
            this.ivframe.setImageBitmap(null);
        } else if (resId.equals("frame_00000")) {
            this.ivframe.setImageBitmap(null);
        } else {
            this.ivframe.setImageBitmap(BitmapLoader.loadFromAsset(this, new int[]{1440, 1440}, resId.replace("thumb_", BuildConfig.FLAVOR)));
        }
    }

    private void addABC(String resId) {
        StickerView stickerView = new StickerView((Context) this, true);
        LayoutParams params = new LayoutParams(-1, -1);
        params.addRule(8, pl.aprilapps.easyphotopicker.R.id.image);
        params.addRule(6, pl.aprilapps.easyphotopicker.R.id.image);
        this.rlphoto.addView(stickerView, params);
        Bitmap bitmap = BitmapLoader.loadFromAsset(this, new int[]{720, 720}, resId.replace("thumb_", BuildConfig.FLAVOR));
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            int width = this.widthScreen / 2;
            bitmap = BitmapProcessing.resizeBitmap(bitmap, width, (bitmap.getHeight() * width) / bitmap.getWidth());
        } else {
            int height = this.widthScreen / 2;
            bitmap = BitmapProcessing.resizeBitmap(bitmap, (bitmap.getWidth() * height) / bitmap.getHeight(), height);
        }
        stickerView.setWaterMark(bitmap, true);
        stickerView.setTag(resId);
    }

    private void addSnap(String resId) {
        StickerView stickerView = new StickerView((Context) this, false);
        LayoutParams params = new LayoutParams(-1, -1);
        params.addRule(8, pl.aprilapps.easyphotopicker.R.id.image);
        params.addRule(6, pl.aprilapps.easyphotopicker.R.id.image);
        this.rlphoto.addView(stickerView, params);
        Bitmap bitmap = BitmapLoader.loadFromAsset(this, new int[]{512, 512}, resId);
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            int width = this.widthScreen / 2;
            bitmap = BitmapProcessing.resizeBitmap(bitmap, width, (bitmap.getHeight() * width) / bitmap.getWidth());
        } else {
            int height = this.widthScreen / 2;
            bitmap = BitmapProcessing.resizeBitmap(bitmap, (bitmap.getWidth() * height) / bitmap.getHeight(), height);
        }
        stickerView.setWaterMark(bitmap, true);
        stickerView.setTag(resId);
    }



    private void addText(Bitmap bm) {
        StickerView stickerView = new StickerView((Context) this, true);
        LayoutParams params = new LayoutParams(-1, -1);
        params.addRule(8, pl.aprilapps.easyphotopicker.R.id.image);
        params.addRule(6, pl.aprilapps.easyphotopicker.R.id.image);
        this.rlphoto.addView(stickerView, params);
        stickerView.setWaterMark(bm, true);
        stickerView.setTag("text");
        stickerView.setColor(this.colorsample);
        stickerView.setFont(this.fontsample);
        stickerView.setText(this.textsample);
    }

    private void resetStickersFocus() {
        for (int i = 0; i < this.rlphoto.getChildCount(); i++) {
            try {
                if (this.rlphoto.getChildAt(i) instanceof StickerView) {
                    this.rlphoto.getChildAt(i).setFocusable(false);
                }
            } catch (Exception e) {
                Log.i("Photos to Collage", e.getMessage());
            }
        }
    }

    private void resetEditSticker() {
        for (int i = 0; i < this.rlphoto.getChildCount(); i++) {
            try {
                if (this.rlphoto.getChildAt(i) instanceof StickerView) {
                    ((StickerView) this.rlphoto.getChildAt(i)).setEdit(false);
                }
            } catch (Exception e) {
                Log.i("Photos to Collage", e.getMessage());
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean z = false;
        if (this.rltext.getVisibility() == View.GONE && this.rlblur.getVisibility() == View.GONE && this.llchange.getVisibility() == View.GONE) {
            if (ev.getAction() == 0) {
                this.checkTouch = z;
                int x = ((int) ev.getX()) - Util.getRelativeLeft(this.rlphoto);
                int y = ((int) ev.getY()) - Util.getRelativeTop(this.rlphoto);
                for (int i = this.rlphoto.getChildCount() - 1; i >= 0; i--) {
                    try {
                        if (this.rlphoto.getChildAt(i) instanceof StickerView) {
                            StickerView stickerView = (StickerView) this.rlphoto.getChildAt(i);
                            if (!stickerView.isInDelete((float) x, (float) y) || !stickerView.isFocusable()) {
                                if (stickerView.isInController((float) x, (float) y) && stickerView.isFocusable()) {
                                    this.checkTouch = true;
                                    break;
                                } else if (!stickerView.isInEdit((float) x, (float) y) || !stickerView.isFocusable()) {
                                    if (stickerView.isInFlip((float) x, (float) y) && stickerView.isFocusable()) {
                                        this.checkTouch = true;
                                        break;
                                    } else if (stickerView.getContentRect().contains((float) x, (float) y)) {
                                        this.checkTouch = true;
                                        if (!stickerView.isFocusable()) {
                                            resetStickersFocus();
                                            stickerView.setFocusable(true);
                                            stickerView.bringToFront();
                                        }
                                        if (!this.firstTouch || System.currentTimeMillis() - this.timetouch > 300) {
                                            this.firstTouch = true;
                                            this.timetouch = System.currentTimeMillis();
                                        } else {
                                            this.firstTouch = false;
                                            if (!stickerView.getText().equals(BuildConfig.FLAVOR)) {
                                                this.toolbarTitle.setText(getResources().getString(R.string.edittext));
                                                this.kindEdit = 1;
                                                stickerView.setEdit(true);
                                                this.menuItemCrop.setIcon(this.menuItemCropIconDone);
                                                this.rltext.setVisibility(View.VISIBLE);
                                                this.rvtext.setVisibility(View.GONE);
                                                this.edtext.setVisibility(View.GONE);
                                                this.ivalign.setTag(Integer.valueOf(stickerView.getAlign()));
                                                this.edtext.requestFocus();
                                                if (this.ivalign.getTag().equals(Integer.valueOf(1))) {
                                                    this.afltext.setGravity(17);
                                                    this.ivalign.setImageResource(R.drawable.iccentertextalignment);
                                                } else if (this.ivalign.getTag().equals(Integer.valueOf(2))) {
                                                    this.afltext.setGravity(3);
                                                    this.ivalign.setImageResource(R.drawable.icalignleft);
                                                } else if (this.ivalign.getTag().equals(Integer.valueOf(3))) {
                                                    this.afltext.setGravity(5);
                                                    this.ivalign.setImageResource(R.drawable.icalignright);
                                                }
                                                this.ivcircle.setTag(Integer.valueOf(stickerView.getCircle()));
                                                if (this.ivcircle.getTag().equals(Integer.valueOf(0))) {
                                                    this.ivcircle.setImageResource(R.drawable.iccircle);
                                                    this.afltext.setShadowLayer(0.0f, 0.0f, 0.0f, -1);
                                                } else if (this.ivcircle.getTag().equals(Integer.valueOf(1))) {
                                                    this.ivcircle.setImageResource(R.drawable.iccirclepressed);
                                                    this.afltext.setShadowLayer(1.6f, StickerView.MAX_SCALE_SIZE, StickerView.MAX_SCALE_SIZE, -1);
                                                }
                                                loadSampleText(stickerView.getColor(), stickerView.getFont(), stickerView.getText());
                                                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(this.edtext, 1);
                                            }
                                        }
                                    }
                                } else {
                                    this.checkTouch = true;
                                    if (stickerView.getText().equals(BuildConfig.FLAVOR)) {
                                        this.colorAdapter2 = new ColorAdapter2(this.listColor, this, this.rvselect.getHeight());
                                        this.rvselect.setAdapter(this.colorAdapter2);
                                        this.colorAdapter2.setOnItemClickListener(new ColorAdapter2.OnRecyclerViewItemClickListener() {
                                            public void onItemClick(View view, String resId) {
                                                int i = 0;
                                                while (i < EditActivity.this.rlphoto.getChildCount()) {
                                                    try {
                                                        if ((EditActivity.this.rlphoto.getChildAt(i) instanceof StickerView) && ((StickerView) EditActivity.this.rlphoto.getChildAt(i)).isFocusable() && ((StickerView) EditActivity.this.rlphoto.getChildAt(i)).isDrawedit()) {
                                                            ((StickerView) EditActivity.this.rlphoto.getChildAt(i)).changeColor(Color.parseColor(resId));
                                                            return;
                                                        }
                                                    } catch (Exception e) {
                                                    }
                                                    i++;
                                                }
                                            }
                                        });
                                    } else {
                                        this.toolbarTitle.setText(getResources().getString(R.string.edittext));
                                        this.kindEdit = 1;
                                        stickerView.setEdit(true);
                                        this.menuItemCrop.setIcon(this.menuItemCropIconDone);
                                        this.rltext.setVisibility(View.VISIBLE);
                                        this.rvtext.setVisibility(View.GONE);
                                        this.edtext.setVisibility(View.VISIBLE);
                                        this.ivalign.setTag(Integer.valueOf(stickerView.getAlign()));
                                        this.edtext.requestFocus();
                                        if (this.ivalign.getTag().equals(Integer.valueOf(1))) {
                                            this.afltext.setGravity(17);
                                            this.ivalign.setImageResource(R.drawable.iccentertextalignment);
                                        } else if (this.ivalign.getTag().equals(Integer.valueOf(2))) {
                                            this.afltext.setGravity(3);
                                            this.ivalign.setImageResource(R.drawable.icalignleft);
                                        } else if (this.ivalign.getTag().equals(Integer.valueOf(3))) {
                                            this.afltext.setGravity(5);
                                            this.ivalign.setImageResource(R.drawable.icalignright);
                                        }
                                        this.ivcircle.setTag(Integer.valueOf(stickerView.getCircle()));
                                        if (this.ivcircle.getTag().equals(Integer.valueOf(0))) {
                                            this.ivcircle.setImageResource(R.drawable.iccircle);
                                            this.afltext.setShadowLayer(0.0f, 0.0f, 0.0f, -1);
                                        } else if (this.ivcircle.getTag().equals(Integer.valueOf(1))) {
                                            this.ivcircle.setImageResource(R.drawable.iccirclepressed);
                                            this.afltext.setShadowLayer(1.6f, StickerView.MAX_SCALE_SIZE, StickerView.MAX_SCALE_SIZE, -1);
                                        }
                                        loadSampleText(stickerView.getColor(), stickerView.getFont(), stickerView.getText());
                                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(this.edtext, 1);
                                    }
                                }
                            } else {
                                this.checkTouch = true;
                                break;
                            }
                        }
                        continue;
                    } catch (Exception e) {
                        Log.i("Photos to Collage", e.getMessage());
                    }
                }
            }
            if (ev.getAction() == 1) {
                Rect rect = new Rect(0, this.rvselect.getTop(), this.widthScreen, this.rvselect.getTop() + this.rvselect.getHeight());
                if (!this.checkTouch) {
                    if (this.rvselect.getAdapter() != this.colorAdapter2) {
                        resetStickersFocus();
                    } else if (!rect.contains((int) ev.getX(), (int) ev.getY())) {
                        resetStickersFocus();
                    }
                }
            }
        }
        try {
            z = super.dispatchTouchEvent(ev);
        } catch (Exception e2) {
        }
        return z;
    }

    private void loadPointforSlider() {
        try {
            Bitmap bmpoint = BitmapFactory.decodeResource(getResources(), R.drawable.slidersmall);
            this.wthumb = DisplayUtil.dip2px(this, 30.0f);
            this.sbslider.setThumb(new BitmapDrawable(getResources(), BitmapProcessing.resizeBitmap(bmpoint, this.wthumb, this.wthumb)));
            int wpoint = this.wthumb / 3;
            int top = this.wthumb + wpoint;
            this.center = this.widthScreen / 2;
            this.dis = (this.widthScreen - (this.wthumb * 2)) / 10;
            int start = this.wthumb - (wpoint / 2);
            for (int i = 0; i < 11; i++) {
                LayoutParams lp = new LayoutParams(wpoint, wpoint);
                lp.setMargins(start, top, 0, 0);
                ImageView iv = new ImageView(this);
                iv.setLayoutParams(lp);
                iv.setImageBitmap(bmpoint);
                this.rlslider.addView(iv);
                start += this.dis;
            }
            this.sbslider.bringToFront();
            this.sbslider.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar seekBar) {
                    EditActivity.this.setTextSlider(seekBar.getProgress());
                    EditActivity.this.exposure = seekBar.getProgress();
                    if (VERSION.SDK_INT >= 16) {
                        EditActivity.this.ivframe.setImageAlpha((seekBar.getProgress() * 255) / 10);
                    } else {
                        EditActivity.this.ivframe.setAlpha((seekBar.getProgress() * 255) / 10);
                    }
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        EditActivity.this.setTextSlider(progress);
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    private void setTextSlider(int progress) {
        try {
            this.sbslider.setProgress(progress);
            this.tvslider.setText(String.valueOf(((float) progress) / CropImageView.DEFAULT_MAX_SCALE_MULTIPLIER));
            LayoutParams lpram = (LayoutParams) this.tvslider.getLayoutParams();
            lpram.setMargins((this.center - this.wthumb) + ((progress - 5) * this.dis), DisplayUtil.dip2px(this, 5.0f), 0, 0);
            this.tvslider.setLayoutParams(lpram);
        } catch (Exception e) {
        }
    }

    private void loadFrame() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedPosition = preferences.getInt("selected_position1", -1);

        this.galleryAdapterEffect = new GalleryAdapterEffect(this.listItem, this, selectedPosition);
        try {
            this.listItem = getAssets().list("frames");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (this.listItem != null) {
            List<String> tmplist = new ArrayList();
            for (String aListItem : this.listItem) {

                tmplist.add("frames/" + aListItem);

            }
            this.listItem = (String[]) tmplist.toArray(new String[tmplist.size()]);
            this.galleryAdapter = new GalleryAdapter(this.listItem, this,selectedPosition);
            this.rvselect.setAdapter(this.galleryAdapter);
            this.galleryAdapter.setOnItemClickListener(new GalleryAdapter.OnRecyclerViewItemClickListener() {
                public void onItemClick(View view, String resId) {
                    if (EditActivity.this.rltext.getVisibility() != View.GONE || EditActivity.this.rlblur.getVisibility() != View.GONE || EditActivity.this.llchange.getVisibility() != View.GONE) {
                        return;
                    }
                    if (resId.contains("frame_")) {
                        EditActivity.this.addFrame(resId);
                    } else if (resId.contains("st")) {
                        EditActivity.this.addStickerItem(resId);
                    } else if (resId.contains("text_")) {
                        EditActivity.this.addABC(resId);
                    } else if (resId.contains("emoji_")) {
                        EditActivity.this.addStickerItem(resId);
                    } else if (resId.contains("overlay_")) {
                        EditActivity.this.filter = resId;
                        EditActivity.this.ivframe.setTag("0");
                        EditActivity.this.gpuEffect();
                    }
                }
            });
            rvselect.scrollToPosition(selectedPosition);
        }
    }


    private void loadGalaxy() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedPosition = preferences.getInt("selected_position", -1);
        this.galleryAdapterEffect = new GalleryAdapterEffect(this.listItem, this, selectedPosition);

        try {
            this.listItem = getAssets().list("overlays");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (this.listItem != null) {
            List<String> tmplist = new ArrayList();
            for (String aListItem : this.listItem) {

                tmplist.add("overlays/" + aListItem);

            }
            this.listItem = (String[]) tmplist.toArray(new String[tmplist.size()]);
            this.galleryAdapterEffect = new GalleryAdapterEffect(this.listItem, this,selectedPosition);
            this.rvselect.setAdapter(this.galleryAdapterEffect);
            this.galleryAdapterEffect.setOnItemClickListener(new GalleryAdapterEffect.OnRecyclerViewItemClickListener() {
                public void onItemClick(View view, String resId) {
                    if (EditActivity.this.rltext.getVisibility() != View.GONE || EditActivity.this.rlblur.getVisibility() != View.GONE || EditActivity.this.llchange.getVisibility() != View.GONE) {
                        return;
                    }
                    if (resId.contains("frame_")) {
                        EditActivity.this.addFrame(resId);
                    } else if (resId.contains("st")) {
                        EditActivity.this.addStickerItem(resId);
                    } else if (resId.contains("text_")) {
                        EditActivity.this.addABC(resId);
                    } else if (resId.contains("emoji_")) {
                        EditActivity.this.addStickerItem(resId);
                    } else if (resId.contains("overlay_")) {
                        EditActivity.this.filter = resId;
                        EditActivity.this.ivframe.setTag("0");
                        EditActivity.this.gpuEffect();
                    }

                }
            });
            rvselect.scrollToPosition(selectedPosition);
        }
    }


    private void loadFont() {
        try {
            this.listfont = getAssets().list("fonts");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (this.listfont != null) {
            for (int i = 0; i < this.listfont.length; i++) {
                this.listfont[i] = "fonts/" + this.listfont[i];
            }
            FontAdapter fontAdapter = new FontAdapter(this.listfont, this);
            this.rvtext.setAdapter(fontAdapter);
            fontAdapter.setOnItemClickListener(new FontAdapter.OnRecyclerViewItemClickListener() {
                public void onItemClick(View view, String resId) {
                    EditActivity.this.loadSampleText(BuildConfig.FLAVOR, resId, BuildConfig.FLAVOR);
                }
            });
        }
    }

    private void loadSampleText(String color, String font, String text) {
        if (!text.equals(BuildConfig.FLAVOR)) {
            this.afltext.setText(text);
            this.edtext.setText(text);
            this.textsample = text;
        }
        if (!color.equals(BuildConfig.FLAVOR)) {
            this.afltext.setTextColor(Color.parseColor(color));
            this.colorsample = color;
        }
        if (!font.equals(BuildConfig.FLAVOR)) {
            this.afltext.setTypeface(Typeface.createFromAsset(getAssets(), font));
            this.fontsample = font;
        }
    }
    private void loadSampleText1(String color, String font, String text) {
        if (!text.equals(BuildConfig.FLAVOR)) {
            this.tv_text_sample.setText(text);
            this.et_text.setText(text);
            this.textsample = text;
        }
        if (!color.equals(BuildConfig.FLAVOR)) {
            this.tv_text_sample.setTextColor(Color.parseColor(color));
            this.colorsample = color;
        }
        if (!font.equals(BuildConfig.FLAVOR)) {
            this.tv_text_sample.setTypeface(Typeface.createFromAsset(getAssets(), font));
            this.fontsample = font;
        }
    }

    private void closeKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void clickable(boolean bool) {
        this.icframe.setClickable(bool);
        this.iceffect.setClickable(bool);
        this.icsnap.setClickable(bool);
        this.ictext.setClickable(bool);

        this.icrandom.setClickable(bool);
        this.rlphoto.setClickable(bool);
    }

    private void refreshImageView() {
        if (this.lastTouchedPositionX != -1 && this.lastTouchedPositionY != -1 && this.blurFinished) {
            this.lastTouchedPositionX -= this.bmmask.getWidth() / 2;
            this.lastTouchedPositionY -= this.bmmask.getHeight() / 2;
            if (this.lastTouchedPositionX < 0) {
                this.lastTouchedPositionX = 0;
            }
            if (this.lastTouchedPositionY < 0) {
                this.lastTouchedPositionY = 0;
            }
            if (this.lastTouchedPositionX > this.bmmain.getWidth()) {
                this.lastTouchedPositionX = this.bmmain.getWidth() - 10;
            }
            if (this.lastTouchedPositionY > this.bmmain.getHeight()) {
                this.lastTouchedPositionY = this.bmmain.getHeight() - 10;
            }
            new BlurTask(this.lastTouchedPositionX, this.lastTouchedPositionY).execute(new Void[0]);
        }
    }

    private void gpuEffect() {
        try {
            float tmp;
            Boolean isChange = Boolean.valueOf(false);
            GPUImageFilterGroup filterGroup = new GPUImageFilterGroup();
            if (this.exposure != 6) {
                isChange = Boolean.valueOf(true);
                tmp = 2.0f - ((((float) (this.exposure - 6)) * StickerView.MIN_SCALE_SIZE) + 1.0f);
                GPUImageGammaFilter gammafilter = new GPUImageGammaFilter();
                gammafilter.setGamma(tmp);
                filterGroup.addFilter(gammafilter);
            }
            if (this.contrast != 0) {
                isChange = Boolean.valueOf(true);
                tmp = (((float) this.contrast) * StickerView.MIN_SCALE_SIZE) + 1.0f;
                GPUImageContrastFilter contrastfilter = new GPUImageContrastFilter();
                contrastfilter.setContrast(tmp);
                filterGroup.addFilter(contrastfilter);
            }
            if (this.sharpen != 0) {
                isChange = Boolean.valueOf(true);
                tmp = ((float) this.sharpen) * StickerView.MIN_SCALE_SIZE;
                GPUImageSharpenFilter sharpenfilter = new GPUImageSharpenFilter();
                sharpenfilter.setSharpness(tmp);
                filterGroup.addFilter(sharpenfilter);
            }
            if (!(this.highlight == 0 && this.shadow == 0)) {
                isChange = Boolean.valueOf(true);
                float tmphl = 1.0f - (((float) this.highlight) * 0.08f);
                float tmpsd = ((float) this.shadow) * 0.08f;
                GPUImageHighlightShadowFilter hsdfilter = new GPUImageHighlightShadowFilter();
                hsdfilter.setHighlights(tmphl);
                hsdfilter.setShadows(tmpsd);
                filterGroup.addFilter(hsdfilter);

            }
            if (this.temperature != 6) {
                isChange = Boolean.valueOf(true);
                int change = 400;
                if (this.temperature < 6) {
                    change = 200;
                }
                tmp = 5000.0f + ((float) ((this.temperature - 6) * change));
                GPUImageWhiteBalanceFilter whiteblancefilter = new GPUImageWhiteBalanceFilter();
                whiteblancefilter.setTemperature(tmp);
                filterGroup.addFilter(whiteblancefilter);
            }
            if (this.vignette != 0) {
                isChange = Boolean.valueOf(true);
                PointF centerPoint = new PointF();
                centerPoint.x = 0.5f;
                centerPoint.y = 0.5f;
                float[] fArr = new float[3];
                filterGroup.addFilter(new GPUImageVignetteFilter(centerPoint, new float[]{0.0f, 0.0f, 0.0f}, 0.3f, 1.0f - (((float) this.vignette) * 0.01f)));
            }
            int[] iArr;
            String aaaa = "overlays/overlay_00000.png";
            Bitmap aa;


            if (aaaa.equals(this.filter.replace("thumb_", BuildConfig.FLAVOR))) {
                aa = BitmapFactory.decodeResource(this.getResources(),
                        R.drawable.blank);
            } else {
                aa = makeTransparent(BitmapLoader.loadFromAsset(this, new int[]{512, 512}, this.filter.replace("thumb_", BuildConfig.FLAVOR)), 75);

            }


            if (this.ivframe.getTag().toString().equals("0")) {
                GPUImageScreenBlendFilter imagefilter = new GPUImageScreenBlendFilter();
                iArr = new int[2];
                imagefilter.setBitmap(aa);
                filterGroup.addFilter(imagefilter);
                this.ivframe.setTag("1");
            } else if (this.ivframe.getTag().toString().equals("1")) {
                GPUImageColorDodgeBlendFilter imagefilter1 = new GPUImageColorDodgeBlendFilter();
                iArr = new int[2];
                imagefilter1.setBitmap(aa);
                filterGroup.addFilter(imagefilter1);
                this.ivframe.setTag("2");
            } else if (this.ivframe.getTag().toString().equals("2")) {
                GPUImageLightenBlendFilter imagefilter2 = new GPUImageLightenBlendFilter();
                iArr = new int[2];
                imagefilter2.setBitmap(aa);
                filterGroup.addFilter(imagefilter2);
                this.ivframe.setTag("3");


            } else if (this.ivframe.getTag().toString().equals("3")) {
                GPUImageSoftLightBlendFilter imagefilter3 = new GPUImageSoftLightBlendFilter();
                iArr = new int[2];
                imagefilter3.setBitmap(aa);
                filterGroup.addFilter(imagefilter3);
                this.ivframe.setTag("4");
            } else if (this.ivframe.getTag().toString().equals("4")) {
                GPUImageOverlayBlendFilter imagefilter4 = new GPUImageOverlayBlendFilter();
                iArr = new int[2];
                imagefilter4.setBitmap(aa);
                filterGroup.addFilter(imagefilter4);
                this.ivframe.setTag("5");
            } else if (this.ivframe.getTag().toString().equals("5")) {
                GPUImageNormalBlendFilterAlpha imagefilter5 = new GPUImageNormalBlendFilterAlpha();
                iArr = new int[2];
                imagefilter5.setBitmap(aa);
                filterGroup.addFilter(imagefilter5);
                this.ivframe.setTag("6");
            } else if (this.ivframe.getTag().toString().equals("6")) {
                GPUImageDifferenceBlendFilter imagefilter6 = new GPUImageDifferenceBlendFilter();
                iArr = new int[2];
                imagefilter6.setBitmap(aa);
                filterGroup.addFilter(imagefilter6);
                this.ivframe.setTag("10");
            }


            else if (this.ivframe.getTag().toString().equals("10")) {
                GPUImageDissolveBlendFilter imagefilter10 = new GPUImageDissolveBlendFilter();
                iArr = new int[2];
                imagefilter10.setBitmap(aa);
                filterGroup.addFilter(imagefilter10);
                this.ivframe.setTag("11");
            } else if (this.ivframe.getTag().toString().equals("11")) {
                GPUImageExclusionBlendFilter imagefilter11 = new GPUImageExclusionBlendFilter();
                iArr = new int[2];
                imagefilter11.setBitmap(aa);
                filterGroup.addFilter(imagefilter11);
                this.ivframe.setTag("13");
            }

            else if (this.ivframe.getTag().toString().equals("13")) {
                GPUImageAddBlendFilter imagefilter13 = new GPUImageAddBlendFilter();
                iArr = new int[2];
                imagefilter13.setBitmap(aa);
                filterGroup.addFilter(imagefilter13);
                this.ivframe.setTag("16");
            }

            else if (this.ivframe.getTag().toString().equals("16")) {
                GPUImageAlphaBlendFilter imagefilter16 = new GPUImageAlphaBlendFilter();
                iArr = new int[2];
                imagefilter16.setBitmap(aa);
                filterGroup.addFilter(imagefilter16);
                this.ivframe.setTag("17");
            } else if (this.ivframe.getTag().toString().equals("17")) {
                GPUImageHueBlendFilter imagefilter17 = new GPUImageHueBlendFilter();
                iArr = new int[2];
                imagefilter17.setBitmap(aa);
                filterGroup.addFilter(imagefilter17);
                this.ivframe.setTag("18");
            } else if (this.ivframe.getTag().toString().equals("18")) {
                GPUImageColorBlendFilter imagefilter18 = new GPUImageColorBlendFilter();
                iArr = new int[2];
                imagefilter18.setBitmap(aa);
                filterGroup.addFilter(imagefilter18);
                this.ivframe.setTag("19");
            } else if (this.ivframe.getTag().toString().equals("19")) {
                GPUImageSaturationBlendFilter imagefilter19 = new GPUImageSaturationBlendFilter();
                iArr = new int[2];
                imagefilter19.setBitmap(aa);
                filterGroup.addFilter(imagefilter19);
                this.ivframe.setTag("22");
            }

            else if (this.ivframe.getTag().toString().equals("22")) {
                GPUImageSubtractBlendFilter imagefilter22 = new GPUImageSubtractBlendFilter();
                iArr = new int[2];
                imagefilter22.setBitmap(aa);
                filterGroup.addFilter(imagefilter22);
                this.ivframe.setTag("23");
            } else if (this.ivframe.getTag().toString().equals("23")) {
                GPUImageChromaKeyBlendFilter imagefilter23 = new GPUImageChromaKeyBlendFilter();
                iArr = new int[2];
                imagefilter23.setBitmap(aa);
                filterGroup.addFilter(imagefilter23);
                this.ivframe.setTag("0");
            }

            this.gpuview.setFilter(filterGroup);
            this.gpuview.requestRender();
        } catch (Exception e) {
        }
    }

    private void setChange() {
        try {
            if (this.type == 1) {
                this.toolbarTitle.setText(getResources().getString(R.string.editexposure));
            } else if (this.type == 2) {
                this.toolbarTitle.setText(getResources().getString(R.string.editcontrast));
            } else if (this.type == 3) {
                this.toolbarTitle.setText(getResources().getString(R.string.editsharpen));
            } else if (this.type == 4) {
                this.toolbarTitle.setText(getResources().getString(R.string.edithightlightsave));
            } else if (this.type == 5) {
                this.toolbarTitle.setText(getResources().getString(R.string.editshadowsave));
            } else if (this.type == 6) {
                this.toolbarTitle.setText(getResources().getString(R.string.edittemperature));
            } else if (this.type == 7) {
                this.toolbarTitle.setText(getResources().getString(R.string.editvignette));
            }
        } catch (Exception e) {
        } catch (OutOfMemoryError e2) {
        }
    }

    public Bitmap makeTransparent(Bitmap src, int value) {
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap transBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8.ARGB_8888);
        Canvas canvas = new Canvas(transBitmap);
        canvas.drawARGB(0, 0, 0, 0);

        final Paint paint = new Paint();
        paint.setAlpha(value);
        canvas.drawBitmap(src, 0, 0, paint);
        return transBitmap;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
