package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;

import androidx.cardview.widget.CardView;

import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Dialog.callback.IBaseListener;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SystemUtil;


public class DialogExitApp extends Dialog {
    public Activity activity;
    CardView btnStay, btnQuit;
    IBaseListener iBaseListener;

    public DialogExitApp(Activity activity, IBaseListener iBaseListener) {
        super(activity);
        this.activity = activity;
        this.iBaseListener = iBaseListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SystemUtil.setLocale(getContext());
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_exit_app);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(true);

        btnStay = findViewById(R.id.btnStay);
        btnQuit = findViewById(R.id.btnQuit);

        btnStay.setOnClickListener(v -> {
            iBaseListener.onCancel();
        });

        btnQuit.setOnClickListener(v -> {
            iBaseListener.onExit();
        });
    }
}
