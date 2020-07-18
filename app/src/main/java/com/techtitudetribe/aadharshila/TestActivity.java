package com.techtitudetribe.aadharshila;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;


import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private PageIndicatorView pageIndicatorView;
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    private RelativeLayout mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        viewPager2 = findViewById(R.id.view_pager_test);
        pageIndicatorView = findViewById(R.id.pager_indicator_test_view);
        pageIndicatorView.setCount(5);
        pageIndicatorView.setAnimationType(AnimationType.NONE);

        List<testSliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new testSliderItem(R.drawable.chemistry));
        sliderItems.add(new testSliderItem(R.drawable.maths));
        sliderItems.add(new testSliderItem(R.drawable.biology));
        sliderItems.add(new testSliderItem(R.drawable.physics));
        sliderItems.add(new testSliderItem(R.drawable.english));



        /*List<testSliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new testSliderItem(R.drawable.chemistry,Integer.parseInt("Chemistry"),Integer.parseInt("Question based on Numericals")));
        sliderItems.add(new testSliderItem(R.drawable.maths,Integer.parseInt("Mathematics"),Integer.parseInt("Numerical questions")));
        sliderItems.add(new testSliderItem(R.drawable.biology,Integer.parseInt("Biology"),Integer.parseInt("Theortical Questions")));
        sliderItems.add(new testSliderItem(R.drawable.physics,Integer.parseInt("Physics"),Integer.parseInt("Numerical Questions")));
        sliderItems.add(new testSliderItem(R.drawable.english,Integer.parseInt("English"),Integer.parseInt("Grammar based questions")));*/

        mToolbar =  findViewById(R.id.back_test);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viewPager2.setAdapter(new testSliderTestAdapter(sliderItems,viewPager2,getApplicationContext()));


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
                page.setScaleX(0.80f + r * 0.40f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                pageIndicatorView.setSelection(position);
                switch (position)
                {
                    case 0 : pageIndicatorView.setSelectedColor(ContextCompat.getColor(TestActivity.this,R.color.creative_red));
                        break;
                    case 1 : pageIndicatorView.setSelectedColor(ContextCompat.getColor(TestActivity.this,R.color.creative_green));
                        break;
                    case 2 : pageIndicatorView.setSelectedColor(ContextCompat.getColor(TestActivity.this,R.color.creative_yellow_dark));
                        break;
                    case 3 : pageIndicatorView.setSelectedColor(ContextCompat.getColor(TestActivity.this,R.color.creative_sky_blue));
                        break;
                    case 4 : pageIndicatorView.setSelectedColor(ContextCompat.getColor(TestActivity.this,R.color.creative_orange));
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
        Intent intent = new Intent(TestActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }


}
