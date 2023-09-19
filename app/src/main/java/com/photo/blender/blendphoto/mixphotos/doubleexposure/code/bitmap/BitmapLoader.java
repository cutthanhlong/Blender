package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.bitmap;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;

import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.other.MemoryManagement;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.other.StickerView;

import java.io.IOException;
import java.io.InputStream;

public class BitmapLoader {

    public static android.graphics.Bitmap load(android.content.Context r11, int[] r12, java.lang.String r13) {
        return null;
    }

    public static Bitmap loadFromAsset(Context context, int[] holderDimension, String image_url) {
        Bitmap bm = null;
        Options options = new Options();
        if (VERSION.SDK_INT < 11) {
            try {
                options.getClass().getField("inNativeAlloc").setBoolean(options, true);
            } catch (Exception e) {
            }
        }
        options.inJustDecodeBounds = true;
        options.inScaled = false;
        AssetManager assetManager = context.getAssets();
        try {
            InputStream istr = assetManager.open(image_url);
            BitmapFactory.decodeStream(istr, null, options);
            istr.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (OutOfMemoryError e3) {
        }
        int inSampleSize = 1;
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        int holderWidth = holderDimension[0];
        int holderHeight = holderDimension[1];
        if (outHeight > holderHeight || outWidth > holderWidth) {
            int halfWidth = outWidth / 2;
            int halfHeight = outHeight / 2;
            while (halfHeight / inSampleSize > holderHeight && halfWidth / inSampleSize > holderWidth) {
                inSampleSize *= 2;
            }
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        try {
            InputStream istr = assetManager.open(image_url);
            bm = BitmapFactory.decodeStream(istr, null, options);
            istr.close();
            return bm;
        } catch (IOException e4) {
            return bm;
        } catch (OutOfMemoryError e5) {
            return bm;
        }
    }

    public static Bitmap loadFromResource(Context context, int[] holderDimension, int drawable) {
        Bitmap bm = null;
        Options options = new Options();
        if (VERSION.SDK_INT < 11) {
            try {
                options.getClass().getField("inNativeAlloc").setBoolean(options, true);
            } catch (Exception e) {
            }
        }
        options.inJustDecodeBounds = true;
        options.inScaled = false;
        try {
            BitmapFactory.decodeResource(context.getResources(), drawable, options);
        } catch (OutOfMemoryError e2) {
        }
        int inSampleSize = 1;
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        int holderWidth = holderDimension[0];
        int holderHeight = holderDimension[1];
        if (outHeight > holderHeight || outWidth > holderWidth) {
            int halfWidth = outWidth / 2;
            int halfHeight = outHeight / 2;
            while (halfHeight / inSampleSize > holderHeight && halfWidth / inSampleSize > holderWidth) {
                inSampleSize *= 2;
            }
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        try {
            bm = BitmapFactory.decodeResource(context.getResources(), drawable, options);
        } catch (OutOfMemoryError e3) {
        }
        return bm;
    }

}
