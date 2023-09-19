package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Dialog.DialogExitApp;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Dialog.DialogRating;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Dialog.callback.IBaseListener;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity.SettingActivity;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;


public class SomeThingApp {
    public static ReviewManager manager;
    public static ReviewInfo reviewInfo;

    public static void rateApp(Activity activity, int type) {
        DialogRating ratingDialog = new DialogRating(activity);
        ratingDialog.init(activity, new DialogRating.OnPress() {
            @Override
            public void send() {

                ratingDialog.dismiss();
                SettingActivity.ll_2.setVisibility(View.GONE);
                SettingActivity.v2.setVisibility(View.GONE);
                String uriText = "mailto:" + SharePrefUtils.email +
                        "?subject=" + "Review for " + SharePrefUtils.subject +
                        "&body=" + SharePrefUtils.subject +
                        "\nRate : " + ratingDialog.getRating() + "\nContent: ";
                Uri uri = Uri.parse(uriText);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(uri);
                try {
                    if (type == 1) {
                        activity.finishAffinity();
                    }
                    activity.startActivity(Intent.createChooser(sendIntent, activity.getString(R.string.Send_Email)));
                    SharePrefUtils.forceRated(activity);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(activity, activity.getString(R.string.There_is_no), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void rating() {
                manager = ReviewManagerFactory.create(activity);
                Task<ReviewInfo> request = manager.requestReviewFlow();
                request.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
                    @Override
                    public void onComplete(@NonNull Task<ReviewInfo> task) {
                        SettingActivity.ll_2.setVisibility(View.GONE);
                        SettingActivity.v2.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            reviewInfo = task.getResult();
                            Log.e("ReviewInfo", "" + reviewInfo);
                            Task<Void> flow = manager.launchReviewFlow(activity, reviewInfo);
                            flow.addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    SharePrefUtils.forceRated(activity);
                                    ratingDialog.dismiss();
                                    if (type == 1) {
                                        activity.finishAffinity();
                                    }
                                }
                            });
                        } else {
                            ratingDialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void later() {
                ratingDialog.dismiss();
                if (type == 1) {
                    activity.finishAffinity();
                }
            }

        });
        try {
            ratingDialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }

    public static void shareApp(Activity activity) {
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name));
        intentShare.putExtra(Intent.EXTRA_TEXT, "Download application :"
                + "https://play.google.com/store/apps/details?id=" + activity.getPackageName());
        activity.startActivity(Intent.createChooser(intentShare, "Share with"));
    }

    public static void moreApp(Activity activity) {
//        activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("link")));
    }

    public static DialogExitApp dialogExitApp;

    public static void exitApp(Activity activity) {
        dialogExitApp = new DialogExitApp(activity, new IBaseListener() {
            @Override
            public void onExit() {
                if (dialogExitApp.isShowing()) {
                    dialogExitApp.dismiss();
                    activity.finishAffinity();
                }
            }

            @Override
            public void onCancel() {
                if (dialogExitApp.isShowing()) {
                    dialogExitApp.dismiss();
                }
            }
        });
        int w = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.9);
        int h = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialogExitApp.getWindow().setLayout(w, h);
        dialogExitApp.show();
    }
}
