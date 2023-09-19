package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.MyTouch;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Build.VERSION;
import androidx.core.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity.MainActivity;

import yuku.ambilwarna.BuildConfig;

public class MultiTouchListener1 implements OnTouchListener {
    private static final int INVALID_POINTER_ID = -1;
    Context context;
    public boolean isRotateEnabled = true;
    public boolean isScaleEnabled = true;
    public boolean isTranslateEnabled = true;
    private int mActivePointerId = INVALID_POINTER_ID;
    private float mPrevX;
    private float mPrevY;
    private ScaleGestureDetector mScaleGestureDetector = new ScaleGestureDetector(new ScaleGestureListener());
    public float maximumScale = 10.0f;
    public float minimumScale = 0.1f;

    private class ScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private float mPivotX;
        private float mPivotY;
        private Vector2D mPrevSpanVector;

        private ScaleGestureListener() {
            this.mPrevSpanVector = new Vector2D(this.mPivotX, this.mPivotX);
        }

        public boolean onScaleBegin(View view, ScaleGestureDetector detector) {
            this.mPivotX = detector.getFocusX();
            this.mPivotY = detector.getFocusY();
            this.mPrevSpanVector.set(detector.getCurrentSpanVector());
            return true;
        }

        public boolean onScale(View view, ScaleGestureDetector detector) {
            float angle;
            float f = 0.0f;
            TransformInfo info = new TransformInfo();
            info.deltaScale = MultiTouchListener1.this.isScaleEnabled ? detector.getScaleFactor() : 1.0f;
            if (MultiTouchListener1.this.isRotateEnabled) {
                angle = Vector2D.getAngle(this.mPrevSpanVector, detector.getCurrentSpanVector());
            } else {
                angle = 0.0f;
            }
            info.deltaAngle = angle;
            if (MultiTouchListener1.this.isTranslateEnabled) {
                angle = detector.getFocusX() - this.mPivotX;
            } else {
                angle = 0.0f;
            }
            info.deltaX = angle;
            if (MultiTouchListener1.this.isTranslateEnabled) {
                f = detector.getFocusY() - this.mPivotY;
            }
            info.deltaY = f;
            info.pivotX = this.mPivotX;
            info.pivotY = this.mPivotY;
            info.minimumScale = MultiTouchListener1.this.minimumScale;
            info.maximumScale = MultiTouchListener1.this.maximumScale;
            MultiTouchListener1.move(view, info);
            return false;
        }
    }

    private class TransformInfo {
        public float deltaAngle;
        public float deltaScale;
        public float deltaX;
        public float deltaY;
        public float maximumScale;
        public float minimumScale;
        public float pivotX;
        public float pivotY;

        private TransformInfo() {
        }
    }

    public MultiTouchListener1(Context context) {
        this.context = context;
    }

    private static float adjustAngle(float degrees) {
        if (degrees > 180.0f) {
            return degrees - 360.0f;
        }
        if (degrees < -180.0f) {
            return degrees + 360.0f;
        }
        return degrees;
    }

    private static void move(View view, TransformInfo info) {
        computeRenderOffset(view, info.pivotX, info.pivotY);
        adjustTranslation(view, info.deltaX, info.deltaY);
        float scale = Math.max(info.minimumScale, Math.min(info.maximumScale, view.getScaleX() * info.deltaScale));
        view.setScaleX(scale);
        view.setScaleY(scale);
        view.setRotation(adjustAngle(view.getRotation() + info.deltaAngle));
    }

    private static void adjustTranslation(View view, float deltaX, float deltaY) {
        float[] deltaVector = new float[]{deltaX, deltaY};
        view.getMatrix().mapVectors(deltaVector);
        view.setTranslationX(view.getTranslationX() + deltaVector[0]);
        view.setTranslationY(view.getTranslationY() + deltaVector[1]);
    }

    private static void computeRenderOffset(View view, float pivotX, float pivotY) {
        if (view.getPivotX() != pivotX || view.getPivotY() != pivotY) {
            float[] prevPoint = new float[]{0.0f, 0.0f};
            view.getMatrix().mapPoints(prevPoint);
            view.setPivotX(pivotX);
            view.setPivotY(pivotY);
            float[] currPoint = new float[]{0.0f, 0.0f};
            view.getMatrix().mapPoints(currPoint);
            float offsetY = currPoint[1] - prevPoint[1];
            view.setTranslationX(view.getTranslationX() - (currPoint[0] - prevPoint[0]));
            view.setTranslationY(view.getTranslationY() - offsetY);
        }
    }

    public boolean onTouch(View view, MotionEvent event) {
        this.mScaleGestureDetector.onTouchEvent(view, event);
        if (!this.isTranslateEnabled) {
            return true;
        }
        view.bringToFront();
        RelativeLayout imrel = (RelativeLayout) view;
        ImageView im = (ImageView) imrel.getChildAt(1);
        MainActivity.selectedImageView = im;
        ImageView im1 = (ImageView) imrel.getChildAt(0);
        if (VERSION.SDK_INT <= 16) {
            MainActivity.seek.setProgress(MainActivity.seek.getMax() - ((int) MainActivity.selectedImageView.getAlpha()));
        } else {
            MainActivity.seek.setProgress(MainActivity.seek.getMax() - MainActivity.selectedImageView.getImageAlpha());
        }
        MainActivity.effbitmap = ((BitmapDrawable) im1.getDrawable()).getBitmap();
        MainActivity.sideblur_seek.setProgress(Integer.parseInt((String) im.getContentDescription()));
        MainActivity.delete.setVisibility(View.VISIBLE);
        MainActivity.seek_rel.setVisibility(View.VISIBLE);
        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MainActivity.tv_Image.setTextColor(context.getColor(R.color.color_text));
        }
        MainActivity.backs.setImageResource(R.drawable.icon1);
        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MainActivity.tv_Bg.setTextColor(context.getColor(R.color.color_text));
        }


        MainActivity.camgal.setImageResource(R.drawable.gallery);

        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MainActivity.tv_Blend.setTextColor(context.getColor(R.color.color_app));
        }
        MainActivity.v2.setVisibility(View.GONE);
        MainActivity.v1.setVisibility(View.GONE);
        MainActivity.v3.setVisibility(View.VISIBLE);
        MainActivity.opacity.setImageResource(R.drawable.edit_bg1);
        MainActivity.opacbool = Boolean.valueOf(false);
        MainActivity.backs.setColorFilter(ContextCompat.getColor(this.context, R.color.white), Mode.MULTIPLY);
        MainActivity.camgal.setColorFilter(ContextCompat.getColor(this.context, R.color.white), Mode.MULTIPLY);
        MainActivity.opacity.setColorFilter(ContextCompat.getColor(this.context, R.color.colorAccent), Mode.MULTIPLY);
        MainActivity.backs_rel.setVisibility(View.GONE);
        MainActivity.bckbool = Boolean.valueOf(true);
        if (MainActivity.pref.getInt("tut", 0) == 2) {
            MainActivity.clickseek.setVisibility(View.VISIBLE);
            MainActivity.clickadjust.setVisibility(View.GONE);
            MainActivity.clickonphoto.setVisibility(View.GONE);
            Editor ed = MainActivity.pref.edit();
            ed.putInt("tut", 3);
            ed.commit();
        }
        int action = event.getAction();
        int pointerIndex;
        switch (event.getActionMasked() & action) {
            case 0 :
                this.mPrevX = event.getX();
                this.mPrevY = event.getY();
                this.mActivePointerId = event.getPointerId(0);
                break;
            case 1 :
                this.mActivePointerId = INVALID_POINTER_ID;
                break;
            case 2 :
                pointerIndex = event.findPointerIndex(this.mActivePointerId);
                if (pointerIndex != INVALID_POINTER_ID) {
                    float currX = event.getX(pointerIndex);
                    float currY = event.getY(pointerIndex);
                    if (!this.mScaleGestureDetector.isInProgress()) {
                        adjustTranslation(view, currX - this.mPrevX, currY - this.mPrevY);
                        break;
                    }
                }
                break;
            case BuildConfig.VERSION_CODE :
                this.mActivePointerId = INVALID_POINTER_ID;
                break;
            case 6:
                pointerIndex = (65280 & action) >> 8;
                if (event.getPointerId(pointerIndex) == this.mActivePointerId) {
                    int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    this.mPrevX = event.getX(newPointerIndex);
                    this.mPrevY = event.getY(newPointerIndex);
                    this.mActivePointerId = event.getPointerId(newPointerIndex);
                    break;
                }
                break;
        }
        return true;
    }
}
