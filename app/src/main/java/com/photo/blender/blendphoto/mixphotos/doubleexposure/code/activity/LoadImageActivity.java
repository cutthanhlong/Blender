package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SystemUtil;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.adapter.MyCreationAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class LoadImageActivity extends AppCompatActivity {

    List<Uri> imageList = new ArrayList<>();
    ArrayList<Uri> screenshotList = new ArrayList<>();
    List<Uri> listImage;
    ArrayList<Uri> listCamera = new ArrayList<>();

    List<Uri> listScreen;
    MyCreationAdapter myCreationAdapter;
    MyCreationAdapter myCreationAdapter1;
    RecyclerView rcvListImg,rcvListImg1;

    TextView tv_toolbar, tv1,tv2,tv3,tv4;
    ImageView back;
    TextView done;
    String[] extensions = new String[]{"jpg", "jpeg", "JPG", "JPEG"};
    private boolean isselected = false;
    private boolean isselected1 = false;
    private boolean isselected2 = false;
    private boolean isselected3 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SystemUtil.setLocale(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (ContextCompat.checkSelfPermission(LoadImageActivity.this.getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") != 0
                || ContextCompat.checkSelfPermission(LoadImageActivity.this.getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            if (Build.VERSION.SDK_INT >= 23) {
                LoadImageActivity.this.requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 922);
            }
        }

        rcvListImg = findViewById(R.id.rcvListImg);
        rcvListImg1 = findViewById(R.id.rcvListImg1);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        back = findViewById(R.id.back);
        done = findViewById(R.id.done_text);
        tv_toolbar = findViewById(R.id.tv_toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        done.setVisibility(View.GONE);
        tv_toolbar.setText(getText(R.string.select_ig));
        tv1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                refreshImage();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv1.setTextColor(getColor(R.color.white));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv2.setTextColor(getColor(R.color.color_text_image));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv3.setTextColor(getColor(R.color.color_text_image));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv4.setTextColor(getColor(R.color.color_text_image));
                }
                tv1.setBackgroundResource(R.drawable.bg_exit);
                tv2.setBackgroundResource(R.drawable.bg_folder);
                tv3.setBackgroundResource(R.drawable.bg_folder);
                tv4.setBackgroundResource(R.drawable.bg_folder);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                loadListDowload();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv1.setTextColor(getColor(R.color.color_text_image));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv2.setTextColor(getColor(R.color.white));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv3.setTextColor(getColor(R.color.color_text_image));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv4.setTextColor(getColor(R.color.color_text_image));
                }
                tv1.setBackgroundResource(R.drawable.bg_folder);
                tv2.setBackgroundResource(R.drawable.bg_exit);
                tv3.setBackgroundResource(R.drawable.bg_folder);
                tv4.setBackgroundResource(R.drawable.bg_folder);
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv1.setTextColor(getColor(R.color.color_text_image));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv2.setTextColor(getColor(R.color.color_text_image));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv3.setTextColor(getColor(R.color.white));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv4.setTextColor(getColor(R.color.color_text_image));
                }
                tv1.setBackgroundResource(R.drawable.bg_folder);
                tv2.setBackgroundResource(R.drawable.bg_folder);
                tv3.setBackgroundResource(R.drawable.bg_exit);
                tv4.setBackgroundResource(R.drawable.bg_folder);
                myCreationAdapter = new MyCreationAdapter(LoadImageActivity.this, getAllScreenshots1(), new MyCreationAdapter.OnClickImage() {
                    @Override
                    public void onClickImage(int pos) {
                        if (!isselected3){
                            Intent intent = new Intent(LoadImageActivity.this, CropActivity.class);
                            intent.putExtra("uri", screenshotList.get(pos).getPath());
                            startActivityForResult(intent, 5);
                            isselected3 = true;
                        }
                    }
                });
                rcvListImg.setAdapter(myCreationAdapter);
                if (screenshotList.size() == 0) {
                    findViewById(R.id.layoutNoPics).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.layoutNoPics).setVisibility(View.GONE);
                }
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv1.setTextColor(getColor(R.color.color_text_image));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv2.setTextColor(getColor(R.color.color_text_image));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv3.setTextColor(getColor(R.color.color_text_image));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tv4.setTextColor(getColor(R.color.white));
                }
                tv1.setBackgroundResource(R.drawable.bg_folder);
                tv2.setBackgroundResource(R.drawable.bg_folder);
                tv3.setBackgroundResource(R.drawable.bg_folder);
                tv4.setBackgroundResource(R.drawable.bg_exit);
                myCreationAdapter = new MyCreationAdapter(LoadImageActivity.this, getAllCamera(), new MyCreationAdapter.OnClickImage() {
                    @Override
                    public void onClickImage(int pos) {
                        if (!isselected2){
                            Intent intent = new Intent(LoadImageActivity.this, CropActivity.class);
                            intent.putExtra("uri", listCamera.get(pos).getPath());
                            startActivityForResult(intent, 5);
                            isselected2 = true;
                        }
                    }
                });
                rcvListImg.setAdapter(myCreationAdapter);
                if (listCamera.size() == 0) {
                    findViewById(R.id.layoutNoPics).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.layoutNoPics).setVisibility(View.GONE);
                }
            }
        });

    }



    private void getImage() {
        // Lấy danh sách ảnh từ thư viện
        String[] projection = { MediaStore.Images.Media.DATA };
        String sortOrder = MediaStore.Images.Media.DATE_TAKEN + " DESC";
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Thêm Uri của ảnh vào danh sách
                @SuppressLint("Range")
                String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                Uri imageUri = Uri.parse(imagePath);
                imageList.add(imageUri);
            }
            cursor.close();
        }

        // Hiển thị danh sách ảnh trên RecyclerView
        myCreationAdapter = new MyCreationAdapter(this, imageList, new MyCreationAdapter.OnClickImage() {
            @Override
            public void onClickImage(int pos) {
                if (!isselected1){
                    Intent intent = new Intent(LoadImageActivity.this, CropActivity.class);
                    intent.putExtra("uri", imageList.get(pos).getPath());
                    startActivityForResult(intent, 5);
                    isselected1 = true;
                }
            }
        });
        Log.d("All",imageList.size()+"");
        rcvListImg.setAdapter(myCreationAdapter);
        if (imageList.size() == 0) {
            findViewById(R.id.layoutNoPics).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.layoutNoPics).setVisibility(View.GONE);
        }
    }
    private void refreshImage() {
        // Xóa danh sách ảnh cũ
        imageList.clear();

        // Load lại danh sách ảnh mới
        getImage();

        // Cập nhật lại adapter trên RecyclerView
        myCreationAdapter.notifyDataSetChanged();
    }


    public void loadListDowload() {
        listImage = loadAllImages(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());
        myCreationAdapter = new MyCreationAdapter(this, listImage, new MyCreationAdapter.OnClickImage() {
            @Override
            public void onClickImage(int pos) {
                if (!isselected){
                    Intent intent = new Intent(LoadImageActivity.this, CropActivity.class);
                    intent.putExtra("uri", listImage.get(pos).getPath());
                    startActivityForResult(intent, 5);
                    isselected = true;
                }
            }
        });
        rcvListImg.setAdapter(myCreationAdapter);
        if (listImage.size() == 0) {
            findViewById(R.id.layoutNoPics).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.layoutNoPics).setVisibility(View.GONE);
        }
    }
    private List<Uri> loadAllImages(String str) {
        int size;
        HashMap hashMap = new HashMap();
        File file = new File(str, "/");
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file2 : listFiles) {
                    if (!file2.isDirectory()) {
                        for (String endsWith : this.extensions) {
                            if (file2.getAbsolutePath().endsWith(endsWith)) {
                                hashMap.put(Long.valueOf(file2.lastModified()), Uri.fromFile(file2));
                            }
                        }
                    }
                }
            }
        }
        if (hashMap.size() == 0) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(hashMap.keySet());
        Collections.sort(arrayList);
        size = arrayList.size();
        ArrayList arrayList2 = new ArrayList();
        for (size--; size >= 0; size--) {
            arrayList2.add(hashMap.get(arrayList.get(size)));
        }
        return arrayList2;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == 5) {
            setResult(5);
            finish();
        }
    }

    private ArrayList<Uri> getAllScreenshots1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            File screenshotsDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Screenshots/");
            if (screenshotsDirectory.exists()) {
                File[] files = screenshotsDirectory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        Uri uri = Uri.fromFile(file);
                        if (!screenshotList.contains(uri)) { // Kiểm tra trước khi thêm vào danh sách
                            screenshotList.add(uri);
                        }
                    }
                }
            }
        } else {
            File screenshotsDirectory = new File(Environment.getExternalStorageDirectory() + "/Pictures/Screenshots/");
            if (screenshotsDirectory.exists()) {
                File[] files = screenshotsDirectory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        Uri uri = Uri.fromFile(file);
                        if (!screenshotList.contains(uri)) { // Kiểm tra trước khi thêm vào danh sách
                            screenshotList.add(uri);
                        }
                    }
                }
            }
        }
        Log.e("Screen",""+screenshotList.size());

        return screenshotList;
    }
    private ArrayList<Uri> getAllCamera() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            File screenshotsDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Camera/");
            if (screenshotsDirectory.exists()) {
                File[] files = screenshotsDirectory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        Uri uri = Uri.fromFile(file);
                        if (!listCamera.contains(uri)){
                            listCamera.add(uri);
                        }
                    }
                }
            }
        } else {
            File screenshotsDirectory = new File(Environment.getExternalStorageDirectory() + "/Pictures/Camera");
            if (screenshotsDirectory.exists()) {
                File[] files = screenshotsDirectory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        Uri uri = Uri.fromFile(file);
                        if (!listCamera.contains(uri)) { // Kiểm tra trước khi thêm vào danh sách
                            listCamera.add(uri);
                        }
                    }
                }
            }
        }

        Log.e("Camera",""+listCamera.size());
        return listCamera;
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isselected = false;
        isselected1 = false;
        isselected2 = false;
        isselected3 = false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv1.setTextColor(getColor(R.color.white));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv2.setTextColor(getColor(R.color.color_text_image));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv3.setTextColor(getColor(R.color.color_text_image));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv4.setTextColor(getColor(R.color.color_text_image));
        }
        tv1.setBackgroundResource(R.drawable.bg_exit);
        tv2.setBackgroundResource(R.drawable.bg_folder);
        tv3.setBackgroundResource(R.drawable.bg_folder);
        tv4.setBackgroundResource(R.drawable.bg_folder);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 922){
            MainActivity.done.setEnabled(true);
            MainActivity.done.setBackgroundResource(R.drawable.bg_export);
        }
    }
}