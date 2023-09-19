package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Language;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Intro.IntroScreenActivity;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Language.Interface.IClickItemLanguage;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Language.Model.LanguageModel;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Language.adapter.LanguageStartAdapter;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SharePrefUtils;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SystemUtil;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LanguageStartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView btn_done;
    List<LanguageModel> listLanguage;
    String codeLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtil.setLocale(this);
        setContentView(R.layout.activity_language_start);

        recyclerView = findViewById(R.id.recyclerView);
        btn_done = findViewById(R.id.done);
        codeLang = Locale.getDefault().getLanguage();

        initData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LanguageStartAdapter languageAdapter = new LanguageStartAdapter(listLanguage, new IClickItemLanguage() {
            @Override
            public void onClickItemLanguage(String code) {
                codeLang = code;
            }
        }, this);


        // set checked default lang local
        String codeLangDefault = Locale.getDefault().getLanguage();
        String[] langDefault = {"hi", "zh", "es", "fr", "pt", "in", "de"};
        if (!Arrays.asList(langDefault).contains(codeLangDefault)) codeLang = "en";

        languageAdapter.setCheck(codeLang);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(languageAdapter);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePrefUtils.increaseCountFirstHelp(LanguageStartActivity.this);
                SystemUtil.saveLocale(getBaseContext(), codeLang);
                startActivity(new Intent(LanguageStartActivity.this, IntroScreenActivity.class).putExtra("INTRO_FROM_SPLASH", true));
                finish();
            }
        });
    }

    private void initData() {
        listLanguage = new ArrayList<>();
        String lang = Locale.getDefault().getLanguage();
        listLanguage.add(new LanguageModel("China", "zh"));
        listLanguage.add(new LanguageModel("English", "en"));
        listLanguage.add(new LanguageModel("French", "fr"));
        listLanguage.add(new LanguageModel("German", "de"));
        listLanguage.add(new LanguageModel("Hindi", "hi"));
        listLanguage.add(new LanguageModel("Indonesia", "in"));
        listLanguage.add(new LanguageModel("Portuguese", "pt"));
        listLanguage.add(new LanguageModel("Spanish", "es"));





        for (int i = 0; i < listLanguage.size(); i++) {
            if (listLanguage.get(i).getCode().equals(lang)) {
                listLanguage.add(0, listLanguage.get(i));
                listLanguage.remove(i + 1);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
