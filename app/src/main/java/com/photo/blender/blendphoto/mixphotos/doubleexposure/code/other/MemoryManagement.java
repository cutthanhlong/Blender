package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.other;

import android.app.ActivityManager;
import android.content.Context;

public class MemoryManagement {
    public static float free(Context context) {
        int memoryClass = ((ActivityManager) context.getSystemService("activity")).getMemoryClass() - 24;
        if (memoryClass < 1) {
            memoryClass = 1;
        }
        return (float) memoryClass;
    }
}
