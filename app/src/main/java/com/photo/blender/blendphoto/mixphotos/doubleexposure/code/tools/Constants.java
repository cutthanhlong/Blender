package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.ParcelFileDescriptor;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

public class Constants {
    public static Bitmap createSplashEffect(int[] pixels, Bitmap bitmap, int progress, boolean isReversed, int color) {
        return createBlackAndWhite(pixels, bitmap, progress, isReversed, color);
    }

    public static Bitmap createBlackAndWhite(int[] pixels, Bitmap src, int threshold, boolean isReversed, int color) {
        int[] newPixels = new int[pixels.length];
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        int width = src.getWidth();
        Bitmap bmOut = Bitmap.createBitmap(width, src.getHeight(), src.getConfig());
        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            int gray = (int) (((0.2989d * ((double) Color.red(pixel))) + (0.587d * ((double) Color.green(pixel)))) + (0.114d * ((double) Color.blue(pixel))));
            if (isReversed) {
                if (gray > threshold) {
                    newPixels[i] = Color.argb(0, 0, 0, 0);
                } else {
                    newPixels[i] = Color.argb(255, r, g, b);
                }
            } else if (gray > threshold) {
                newPixels[i] = Color.argb(255, r, g, b);
            } else {
                newPixels[i] = Color.argb(0, 0, 0, 0);
            }
        }
        bmOut.setPixels(newPixels, 0, bmOut.getWidth(), 0, 0, bmOut.getWidth(), bmOut.getHeight());
        return bmOut;
    }

    public static Bitmap getEffectAppliedBitmap(ArrayList<Integer> grays, int width, int height, Config bitmapconfig, int threshold, boolean isReversed, int color) {
        int[] pixels = new int[grays.size()];
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        Bitmap bmOut = Bitmap.createBitmap(width, height, bitmapconfig);
        for (int i = 0; i < pixels.length; i++) {
            int gray = ((Integer) grays.get(i)).intValue();
            if (isReversed) {
                if (gray > threshold) {
                    pixels[i] = Color.argb(0, 0, 0, 0);
                } else {
                    pixels[i] = Color.argb(255, r, g, b);
                }
            } else if (gray > threshold) {
                pixels[i] = Color.argb(255, r, g, b);
            } else {
                pixels[i] = Color.argb(0, 0, 0, 0);
            }
        }
        bmOut.setPixels(pixels, 0, bmOut.getWidth(), 0, 0, bmOut.getWidth(), bmOut.getHeight());
        return bmOut;
    }

    public static Bitmap resizeBitmap(Bitmap bit, int width, int height) {
        float wr = (float) width;
        float hr = (float) height;
        float wd = (float) bit.getWidth();
        float he = (float) bit.getHeight();
        Log.i("testings", wr + "  " + hr + "  and  " + wd + "  " + he);
        float rat1 = wd / he;
        float rat2 = he / wd;
        if (wd > wr) {
            wd = wr;
            he = wd * rat2;
            Log.i("testings", "if (wd > wr) " + wd + "  " + he);
            if (he > hr) {
                he = hr;
                wd = he * rat1;
                Log.i("testings", "  if (he > hr) " + wd + "  " + he);
            } else {
                wd = wr;
                he = wd * rat2;
                Log.i("testings", " in else " + wd + "  " + he);
            }
        } else if (he > hr) {
            he = hr;
            wd = he * rat1;
            Log.i("testings", "  if (he > hr) " + wd + "  " + he);
            if (wd > wr) {
                wd = wr;
                he = wd * rat2;
            } else {
                Log.i("testings", " in else " + wd + "  " + he);
            }
        } else if (rat1 > 0.75f) {
            wd = wr;
            he = wd * rat2;
            Log.i("testings", " if (rat1 > .75f) ");
        } else if (rat2 > 1.5f) {
            he = hr;
            wd = he * rat1;
            Log.i("testings", " if (rat2 > 1.5f) ");
        } else {
            he = wr * rat2;
            Log.i("testings", " in else ");
            if (he > hr) {
                he = hr;
                wd = he * rat1;
                Log.i("testings", "  if (he > hr) " + wd + "  " + he);
            } else {
                wd = wr;
                he = wd * rat2;
                Log.i("testings", " in else " + wd + "  " + he);
            }
        }
        return Bitmap.createScaledBitmap(bit, (int) wd, (int) he, false);
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri, float screenWidth, float screenHeight) throws IOException {
        try {
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Options bfo = new Options();
            bfo.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fileDescriptor, null, bfo);
            Options optsDownSample = new Options();
            if (screenWidth <= screenHeight) {
                screenWidth = screenHeight;
            }
            int maxDim = (int) screenWidth;
            optsDownSample.inSampleSize = ImageUtils.getClosestResampleSize(bfo.outWidth, bfo.outHeight, maxDim);
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, optsDownSample);
            Matrix m = new Matrix();
            if (image.getWidth() > maxDim || image.getHeight() > maxDim) {
                Options optsScale = ImageUtils.getResampling(image.getWidth(), image.getHeight(), maxDim);
                m.postScale(((float) optsScale.outWidth) / ((float) image.getWidth()), ((float) optsScale.outHeight) / ((float) image.getHeight()));
            }
            String pathInput = ImageUtils.getRealPathFromURI(uri, context);
            if (new Integer(VERSION.SDK).intValue() > 4) {
                int rotation = ExifUtils.getExifRotation(pathInput);
                if (rotation != 0) {
                    m.postRotate((float) rotation);
                }
            }
            image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), m, true);
            parcelFileDescriptor.close();
            return image;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CharSequence getSpannableString(Context ctx, Typeface ttf, int stringId) {
        SpannableStringBuilder builder = new SpannableStringBuilder().append(new SpannableString(ctx.getResources().getString(stringId)));
        return builder.subSequence(0, builder.length());
    }

    public static Typeface getHeaderTypeface(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "OPENSANS-SEMIBOLD.ttf");
    }

    public static Typeface getTextTypeface(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "OPENSANS-SEMIBOLD.ttf");
    }

    public static Typeface getTextTypefaceFont(Activity activity, String fonts) {
        return Typeface.createFromAsset(activity.getAssets(), fonts);
    }
}
