package com.techtitudetribe.aadharshila;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    private TextView subjectTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        viewPager2 = findViewById(R.id.view_pager_result);
        subjectTitle = findViewById(R.id.result_subject_title);

        List<resultSliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new resultSliderItem(R.drawable.chemistry));
        sliderItems.add(new resultSliderItem(R.drawable.maths));
        sliderItems.add(new resultSliderItem(R.drawable.biology));
        sliderItems.add(new resultSliderItem(R.drawable.physics));
        sliderItems.add(new resultSliderItem(R.drawable.english));

        viewPager2.setAdapter(new ResultSliderAdapter(sliderItems,viewPager2,getApplicationContext()));


        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
                page.setScaleX(0.75f + r * 0.50f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0 : subjectTitle.setText("Chemistry");
                        subjectTitle.setTextColor(ContextCompat.getColor(ResultActivity.this,R.color.creative_red));
                        break;
                    case 1 : subjectTitle.setText("Maths");
                        subjectTitle.setTextColor(ContextCompat.getColor(ResultActivity.this,R.color.creative_green));
                        break;
                    case 2 : subjectTitle.setText("Biology");
                        subjectTitle.setTextColor(ContextCompat.getColor(ResultActivity.this,R.color.creative_yellow_dark));
                        break;
                    case 3 : subjectTitle.setText("Physics");
                        subjectTitle.setTextColor(ContextCompat.getColor(ResultActivity.this,R.color.creative_sky_blue));
                        break;
                    case 4 : subjectTitle.setText("English");
                        subjectTitle.setTextColor(ContextCompat.getColor(ResultActivity.this,R.color.creative_orange));
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ResultActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
