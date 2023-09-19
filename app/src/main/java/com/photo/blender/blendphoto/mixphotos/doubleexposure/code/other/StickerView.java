package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.other;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import androidx.annotation.NonNull;

import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.bitmap.BitmapProcessing;

import pl.aprilapps.easyphotopicker.BuildConfig;

public class StickerView extends View {
    public static final float MAX_SCALE_SIZE = 4.0f;
    public static final float MIN_SCALE_SIZE = 0.1f;
    private int align;
    private int circle;
    private String color;
    private boolean drawedit;
    private String font;
    private boolean isEdit;
    private Bitmap mBitmap;
    private Paint mBorderPaint;
    private RectF mContentRect;
    private Bitmap mControllerBitmap;
    private float mControllerHeight;
    private float mControllerWidth;
    private Bitmap mDeleteBitmap;
    private float mDeleteHeight;
    private float mDeleteWidth;
    private boolean mDrawController;
    private Bitmap mEditBitmap;
    private float mEditHeight;
    private float mEditWidth;
    private Bitmap mFlipBitmap;
    private float mFlipHeight;
    private float mFlipWidth;
    private boolean mInController;
    private boolean mInDelete;
    private boolean mInEdit;
    private boolean mInFlip;
    private boolean mInMove;
    private float mLastPointX;
    private float mLastPointY;
    private Matrix mMatrix;
    private OnStickerDeleteListener mOnStickerDeleteListener;
    private RectF mOriginContentRect;
    private float[] mOriginPoints;
    private Paint mPaint;
    private float[] mPoints;
    private float mScaleSize;
    private float mStickerScaleSize;
    private RectF mViewRect;
    private String text;

    public interface OnStickerDeleteListener {
        void onDelete(StickerView stickerView);
    }

    public StickerView(Context context, boolean isDraw) {
        this(context, null);
        setDrawedit(isDraw);
    }

    public StickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mDrawController = true;
        this.mStickerScaleSize = 1.0f;
        this.text = BuildConfig.FLAVOR;
        this.font = BuildConfig.FLAVOR;
        this.color = BuildConfig.FLAVOR;
        this.isEdit = false;
        this.align = 1;
        this.circle = 0;
        this.drawedit = false;
        init();
    }

    private void init() {
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setFilterBitmap(true);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeWidth(MAX_SCALE_SIZE);
        this.mPaint.setColor(-1);
        this.mBorderPaint = new Paint(this.mPaint);
        this.mBorderPaint.setColor(Color.parseColor("#B2ffffff"));
        this.mBorderPaint.setShadowLayer((float) DisplayUtil.dip2px(getContext(), 2.0f), 0.0f, 0.0f, Color.parseColor("#33000000"));
        int width = DisplayUtil.getDisplayWidthPixels(getContext());
        Drawable drawable3 = getResources().getDrawable(R.drawable.scale1);
        mControllerBitmap = Bitmap.createBitmap(drawable3.getIntrinsicWidth(), drawable3.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas3 = new Canvas(mControllerBitmap);
        drawable3.setBounds(0, 0, canvas3.getWidth(), canvas3.getHeight());
        drawable3.draw(canvas3);
        this.mControllerBitmap = BitmapProcessing.resizeBitmap(this.mControllerBitmap, width / 12, width / 12);
        this.mControllerWidth = (float) this.mControllerBitmap.getWidth();
        this.mControllerHeight = (float) this.mControllerBitmap.getHeight();
        Drawable drawable = getResources().getDrawable(R.drawable.close);
        mDeleteBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mDeleteBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        this.mDeleteBitmap = BitmapProcessing.resizeBitmap(this.mDeleteBitmap, width / 12, width / 12);
        this.mDeleteWidth = (float) this.mDeleteBitmap.getWidth();
        this.mDeleteHeight = (float) this.mDeleteBitmap.getHeight();

        Drawable drawable1 = getResources().getDrawable(R.drawable.flip);
        mFlipBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable1.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(mFlipBitmap);
        drawable1.setBounds(0, 0, canvas1.getWidth(), canvas1.getHeight());
        drawable1.draw(canvas1);
        this.mFlipBitmap = BitmapProcessing.resizeBitmap(this.mFlipBitmap, width / 12, width / 12);
        this.mFlipWidth = (float) this.mFlipBitmap.getWidth();
        this.mFlipHeight = (float) this.mFlipBitmap.getHeight();

        Drawable drawable2 = getResources().getDrawable(R.drawable.scale);
        mEditBitmap = Bitmap.createBitmap(drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(mEditBitmap);
        drawable2.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable2.draw(canvas2);
        this.mEditBitmap = BitmapProcessing.resizeBitmap(this.mEditBitmap, width / 12, width / 12);
        this.mEditWidth = (float) this.mEditBitmap.getWidth();
        this.mEditHeight = (float) this.mEditBitmap.getHeight();




    }

    public void setWaterMark(@NonNull Bitmap bitmap, boolean isReset) {
        this.mBitmap = bitmap;

        float px = mBitmap.getWidth();
        float py = mBitmap.getHeight();
        this.mStickerScaleSize = 1.0f;
        setFocusable(true);
        if (isReset) {
            try {
                this.mOriginPoints = new float[]{0.0f, 0.0f, px, 0.0f, px, py, 0.0f, py, ((float) this.mBitmap.getWidth()) / 2.0f, ((float) this.mBitmap.getHeight()) / 2.0f};
                this.mOriginContentRect = new RectF(0.0f, 0.0f, px, py);
                this.mPoints = new float[10];
                this.mContentRect = new RectF();
                this.mMatrix = new Matrix();
                this.mMatrix.postTranslate((((float) DisplayUtil.getDisplayWidthPixels(getContext())) - ((float) this.mBitmap.getWidth())) / 2.0f, (((float) DisplayUtil.getDisplayWidthPixels(getContext())) - ((float) this.mBitmap.getHeight())) / 2.0f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.mOriginPoints = new float[]{0.0f, 0.0f, px, 0.0f, px, py, 0.0f, py, ((float) this.mBitmap.getWidth()) / 2.0f, ((float) this.mBitmap.getHeight()) / 2.0f};
            this.mOriginContentRect = new RectF(0.0f, 0.0f, px, py);
        }
        postInvalidate();
    }

    public Matrix getMarkMatrix() {
        return this.mMatrix;
    }

    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
        postInvalidate();
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mBitmap != null && this.mMatrix != null) {
            this.mMatrix.mapPoints(this.mPoints, this.mOriginPoints);
            this.mMatrix.mapRect(this.mContentRect, this.mOriginContentRect);
            canvas.drawBitmap(this.mBitmap, this.mMatrix, this.mPaint);
            if (this.mDrawController && isFocusable()) {
                canvas.drawLine(this.mPoints[0], this.mPoints[1], this.mPoints[2], this.mPoints[3], this.mBorderPaint);
                canvas.drawLine(this.mPoints[2], this.mPoints[3], this.mPoints[4], this.mPoints[5], this.mBorderPaint);
                canvas.drawLine(this.mPoints[4], this.mPoints[5], this.mPoints[6], this.mPoints[7], this.mBorderPaint);
                canvas.drawLine(this.mPoints[6], this.mPoints[7], this.mPoints[0], this.mPoints[1], this.mBorderPaint);
                canvas.drawBitmap(this.mControllerBitmap, this.mPoints[4] - (this.mControllerWidth / 2.0f), this.mPoints[5] - (this.mControllerHeight / 2.0f), this.mBorderPaint);
                canvas.drawBitmap(this.mDeleteBitmap, this.mPoints[0] - (this.mDeleteWidth / 2.0f), this.mPoints[1] - (this.mDeleteHeight / 2.0f), this.mBorderPaint);
                canvas.drawBitmap(this.mFlipBitmap, this.mPoints[2] - (this.mFlipWidth / 2.0f), this.mPoints[3] - (this.mFlipHeight / 2.0f), this.mBorderPaint);
                if (isDrawedit()) {
                    canvas.drawBitmap(this.mEditBitmap, this.mPoints[6] - (this.mEditWidth / 2.0f), this.mPoints[7] - (this.mEditHeight / 2.0f), this.mBorderPaint);
                }
            }
        }
    }

    public Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        this.mDrawController = false;
        draw(canvas);
        this.mDrawController = true;
        canvas.save();
        return bitmap;
    }

    public void setShowDrawController(boolean show) {
        this.mDrawController = show;
    }

    public boolean isInController(float x, float y) {
        float rx = this.mPoints[4];
        float ry = this.mPoints[5];
        if (new RectF(rx - (this.mControllerWidth / 2.0f), ry - (this.mControllerHeight / 2.0f), (this.mControllerWidth / 2.0f) + rx, (this.mControllerHeight / 2.0f) + ry).contains(x, y)) {
            return true;
        }
        return false;
    }

    public boolean isInDelete(float x, float y) {
        float rx = this.mPoints[0];
        float ry = this.mPoints[1];
        if (new RectF(rx - this.mDeleteWidth, ry - this.mDeleteHeight, this.mDeleteWidth + rx, this.mDeleteHeight + ry).contains(x, y)) {
            return true;
        }
        return false;
    }

    public boolean isInFlip(float x, float y) {
        float rx = this.mPoints[2];
        float ry = this.mPoints[3];
        if (new RectF(rx - (this.mFlipWidth / 2.0f), ry - (this.mFlipHeight / 2.0f), (this.mFlipWidth / 2.0f) + rx, (this.mFlipHeight / 2.0f) + ry).contains(x, y)) {
            return true;
        }
        return false;
    }

    public boolean isInEdit(float x, float y) {
        if (isDrawedit()) {
            float rx = this.mPoints[6];
            float ry = this.mPoints[7];
            if (new RectF(rx - (this.mEditWidth / 2.0f), ry - (this.mEditHeight / 2.0f), (this.mEditWidth / 2.0f) + rx, (this.mEditHeight / 2.0f) + ry).contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {



        Log.e("ggggggg", "gggggggg");


        if (!isFocusable()) {
            return super.dispatchTouchEvent(event);
        }
        if (this.mViewRect == null) {
            this.mViewRect = new RectF(0.0f, 0.0f, (float) getMeasuredWidth(), (float) getMeasuredHeight());
        }
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                if (!isInController(x, y)) {
                    if (!isInDelete(x, y)) {
                        if (!isInFlip(x, y)) {
                            if (!isDrawedit() || !isInEdit(x, y)) {
                                if (this.mContentRect.contains(x, y)) {
                                    this.mLastPointY = y;
                                    this.mLastPointX = x;
                                    this.mInMove = true;
                                    Log.e("mInMove", "true");
                                    break;
                                }
                            }
                            this.mInEdit = true;
                            Log.e("mInEdit", "true");
                            break;
                        }
                        this.mInFlip = true;
                        Log.e("mInFlip", "true");
                        break;
                    }
                    this.mInDelete = true;
                    Log.e("mInDelete", "true");
                    break;
                }
                Log.e("mInController", "true");
                this.mInController = true;
                this.mLastPointY = y;
                this.mLastPointX = x;
                break;

            case MotionEvent.ACTION_UP :
                if (!isInDelete(x, y) || !this.mInDelete) {
                    if (isInFlip(x, y) && this.mInFlip) {
                        doFlipSticker();

                        Log.e("up","up");
                        this.mLastPointX = 0.0f;
                        this.mLastPointY = 0.0f;
                        this.mInController = false;
                        this.mInMove = false;
                        this.mInDelete = false;
                        this.mInFlip = false;
                        this.mInEdit = false;
                        break;
                    }

                    this.mLastPointX = 0.0f;
                    this.mLastPointY = 0.0f;
                    this.mInController = false;
                    this.mInMove = false;
                    this.mInDelete = false;
                    this.mInFlip = false;
                    this.mInEdit = false;

                    break;
                }
                doDeleteSticker();
                this.mLastPointX = 0.0f;
                this.mLastPointY = 0.0f;
                this.mInController = false;
                this.mInMove = false;
                this.mInDelete = false;
                this.mInFlip = false;
                this.mInEdit = false;
                break;

            case MotionEvent.ACTION_MOVE :

                Log.e("ggggggg", "gggggggg");
                if (this.mInController) {

                    Log.e("mInController", "move");
                    this.mMatrix.postRotate(rotation(event), this.mPoints[8], this.mPoints[9]);
                    float nowLenght = caculateLength(this.mPoints[0], this.mPoints[1]);
                    float touchLenght = caculateLength(event.getX(), event.getY());
                    if (((float) Math.sqrt((double) ((nowLenght - touchLenght) * (nowLenght - touchLenght)))) > 0.0f) {
                        float scale = touchLenght / nowLenght;
                        float nowsc = this.mStickerScaleSize * scale;
                        if (nowsc >= MIN_SCALE_SIZE && nowsc <= MAX_SCALE_SIZE) {
                            this.mMatrix.postScale(scale, scale, this.mPoints[8], this.mPoints[9]);
                            this.mStickerScaleSize = nowsc;
                        }
                    }
                    invalidate();
                    this.mLastPointX = x;
                    this.mLastPointY = y;
                    break;
                } else if (this.mInMove) {
                    Log.e("mInMove", "move");
                    float cX = x - this.mLastPointX;
                    float cY = y - this.mLastPointY;
                    this.mInController = false;
                    if (((float) Math.sqrt((double) ((cX * cX) + (cY * cY)))) > 2.0f && canStickerMove(cX, cY)) {
                        this.mMatrix.postTranslate(cX, cY);
                        postInvalidate();
                        this.mLastPointX = x;
                        this.mLastPointY = y;
                        break;
                    }
                } else {
                    return true;
                }
            case MotionEvent.ACTION_CANCEL :
                break;
        }


        return true;
    }

    private void doDeleteSticker() {
        setVisibility(GONE);
        if (this.mOnStickerDeleteListener != null) {
            this.mOnStickerDeleteListener.onDelete(this);
        }
        ((ViewGroup) getParent()).removeView(this);
    }

    private void doFlipSticker() {
        this.mBitmap = BitmapProcessing.flip(this.mBitmap, true, false);
        postInvalidate();
    }

    private void doEditSticker() {
    }

    public void changeColor(int color) {
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(color, Mode.SRC_IN));
        new Canvas(this.mBitmap).drawBitmap(this.mBitmap, 0.0f, 0.0f, paint);
        postInvalidate();
    }

    private boolean canStickerMove(float cx, float cy) {
        if (this.mViewRect.contains(cx + this.mPoints[8], cy + this.mPoints[9])) {
            return true;
        }
        return false;
    }

    private float caculateLength(float x, float y) {
        float ex = x - this.mPoints[8];
        float ey = y - this.mPoints[9];
        return (float) Math.sqrt((double) ((ex * ex) + (ey * ey)));
    }

    private float rotation(MotionEvent event) {
        return calculateDegree(event.getX(), event.getY()) - calculateDegree(this.mLastPointX, this.mLastPointY);
    }

    private float calculateDegree(float x, float y) {
        return (float) Math.toDegrees(Math.atan2((double) (y - this.mPoints[9]), (double) (x - this.mPoints[8])));
    }

    public RectF getContentRect() {
        return this.mContentRect;
    }

    public String getFont() {
        return this.font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isEdit() {
        return this.isEdit;
    }

    public void setEdit(boolean edit) {
        this.isEdit = edit;
    }

    public int getAlign() {
        return this.align;
    }

    public void setAlign(int align) {
        this.align = align;
    }

    public int getCircle() {
        return this.circle;
    }

    public void setCircle(int circle) {
        this.circle = circle;
    }

    public boolean isDrawedit() {
        return this.drawedit;
    }

    public void setDrawedit(boolean drawedit) {
        this.drawedit = drawedit;
    }

    public void setOnStickerDeleteListener(OnStickerDeleteListener listener) {
        this.mOnStickerDeleteListener = listener;
    }
}
