package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Language;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity.HomeActivity;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Language.Model.LanguageModel;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Language.adapter.LanguageAdapter;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SystemUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView btn_back,done;
    List<LanguageModel> listLanguage;
    String codeLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtil.setLocale(this);
        setContentView(R.layout.activity_language);

        recyclerView = findViewById(R.id.recyclerView);
        btn_back = findViewById(R.id.back);
        done = findViewById(R.id.done);
        codeLang = Locale.getDefault().getLanguage();
        initData();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LanguageAdapter languageAdapter = new LanguageAdapter(listLanguage, code -> codeLang = code, this);


        findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SystemUtil.saveLocale(getBaseContext(), codeLang);
                back();
            }
        });

        languageAdapter.setCheck(SystemUtil.getPreLanguage(getBaseContext()));


        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(languageAdapter);
        //   recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initData() {
        listLanguage = new ArrayList<>();
        listLanguage.add(new LanguageModel("English", "en"));
        listLanguage.add(new LanguageModel("Portuguese", "pt"));
        listLanguage.add(new LanguageModel("Spanish", "es"));
        listLanguage.add(new LanguageModel("German", "de"));
        listLanguage.add(new LanguageModel("French", "fr"));
        listLanguage.add(new LanguageModel("China", "zh"));
        listLanguage.add(new LanguageModel("Hindi", "hi"));
        listLanguage.add(new LanguageModel("Indonesia", "in"));

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void back() {
        startActivity(new Intent(LanguageActivity.this, HomeActivity.class));
        finishAffinity();
    }

}
