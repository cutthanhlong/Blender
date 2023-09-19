package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Intro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity.HomeActivity;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.Local.SystemUtil;


public class IntroScreenActivity extends AppCompatActivity {
    ImageView[] dots = null;
    int positionPage = 0;
    ViewPager viewPager;
    TextView tvTitle, tvContent;
    CardView btnStart, btnNext;
    String[] title;
    String[] content;
    TextView skip;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtil.setLocale(this);
        setContentView(R.layout.activity_intro);


        viewPager = findViewById(R.id.view_pager2);
        btnNext = findViewById(R.id.btnNext);
        btnStart = findViewById(R.id.btnStart);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        skip = findViewById(R.id.skip);

        dots = new ImageView[]{findViewById(R.id.cricle_1), findViewById(R.id.cricle_2), findViewById(R.id.cricle_3)};
        title = new String[]{getResources().getString(R.string.title1), getResources().getString(R.string.title2), getResources().getString(R.string.title3)};
        content = new String[]{getResources().getString(R.string.content1), getResources().getString(R.string.content2), getResources().getString(R.string.content3)};

        SlideAdapter adapter = new SlideAdapter(this);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                positionPage = position;
            }

            @Override
            public void onPageSelected(int position) {
                changeContentInit(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome();
            }
        });
    }

    private void changeContentInit(int position) {
        tvTitle.setText(title[position]);
        tvContent.setText(content[position]);
        for (int i = 0; i < 3; i++) {
            if (i == position)
                dots[i].setImageResource(R.drawable.ic_dot_selected);
            else
                dots[i].setImageResource(R.drawable.ic_dot_not_select);
        }

        switch (position) {
            case 0:
            case 1:
                skip.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
                btnStart.setVisibility(View.GONE);
                break;
            case 2:
                skip.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
                btnStart.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void goToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        changeContentInit(viewPager.getCurrentItem());
    }
}