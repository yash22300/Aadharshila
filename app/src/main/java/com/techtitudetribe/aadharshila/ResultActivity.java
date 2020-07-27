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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    private TextView subjectTitle;
    private String standard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        viewPager2 = findViewById(R.id.view_pager_result);
        subjectTitle = findViewById(R.id.result_subject_title);

        standard = getIntent().getExtras().getString("class");

        List<resultSliderItem> sliderItems = new ArrayList<>();
        switch (standard)
        {
            case "1"  : sliderItems.add(new resultSliderItem(R.drawable.hindi));
                sliderItems.add(new resultSliderItem(R.drawable.maths));
                sliderItems.add(new resultSliderItem(R.drawable.g_k));
                sliderItems.add(new resultSliderItem(R.drawable.moral));
                sliderItems.add(new resultSliderItem(R.drawable.english));
                break;
            case "2"  : sliderItems.add(new resultSliderItem(R.drawable.hindi));
                sliderItems.add(new resultSliderItem(R.drawable.maths));
                sliderItems.add(new resultSliderItem(R.drawable.g_k));
                sliderItems.add(new resultSliderItem(R.drawable.moral));
                sliderItems.add(new resultSliderItem(R.drawable.english));
                break;
            case "3"  : sliderItems.add(new resultSliderItem(R.drawable.hindi));
                sliderItems.add(new resultSliderItem(R.drawable.maths));
                sliderItems.add(new resultSliderItem(R.drawable.g_k));
                sliderItems.add(new resultSliderItem(R.drawable.moral));
                sliderItems.add(new resultSliderItem(R.drawable.english));
                break;
            case "4"  : sliderItems.add(new resultSliderItem(R.drawable.hindi));
                sliderItems.add(new resultSliderItem(R.drawable.maths));
                sliderItems.add(new resultSliderItem(R.drawable.science));
                sliderItems.add(new resultSliderItem(R.drawable.moral));
                sliderItems.add(new resultSliderItem(R.drawable.english));
                break;
            case "5"  : sliderItems.add(new resultSliderItem(R.drawable.hindi));
                sliderItems.add(new resultSliderItem(R.drawable.maths));
                sliderItems.add(new resultSliderItem(R.drawable.science));
                sliderItems.add(new resultSliderItem(R.drawable.moral));
                sliderItems.add(new resultSliderItem(R.drawable.english));
                break;
            case "6"  : sliderItems.add(new resultSliderItem(R.drawable.hindi));
                sliderItems.add(new resultSliderItem(R.drawable.maths));
                sliderItems.add(new resultSliderItem(R.drawable.science));
                sliderItems.add(new resultSliderItem(R.drawable.g_k));
                sliderItems.add(new resultSliderItem(R.drawable.english));
                break;
            case "7"  : sliderItems.add(new resultSliderItem(R.drawable.hindi));
                sliderItems.add(new resultSliderItem(R.drawable.maths));
                sliderItems.add(new resultSliderItem(R.drawable.science));
                sliderItems.add(new resultSliderItem(R.drawable.g_k));
                sliderItems.add(new resultSliderItem(R.drawable.english));
                break;
            case "8"  : sliderItems.add(new resultSliderItem(R.drawable.hindi));
                sliderItems.add(new resultSliderItem(R.drawable.maths));
                sliderItems.add(new resultSliderItem(R.drawable.science));
                sliderItems.add(new resultSliderItem(R.drawable.g_k));
                sliderItems.add(new resultSliderItem(R.drawable.english));
                break;
            case "9"  : sliderItems.add(new resultSliderItem(R.drawable.hindi));
                sliderItems.add(new resultSliderItem(R.drawable.maths));
                sliderItems.add(new resultSliderItem(R.drawable.science));
                sliderItems.add(new resultSliderItem(R.drawable.g_k));
                sliderItems.add(new resultSliderItem(R.drawable.english));
                break;
            case "10"  : sliderItems.add(new resultSliderItem(R.drawable.hindi));
                sliderItems.add(new resultSliderItem(R.drawable.maths));
                sliderItems.add(new resultSliderItem(R.drawable.science));
                sliderItems.add(new resultSliderItem(R.drawable.g_k));
                sliderItems.add(new resultSliderItem(R.drawable.english));
                break;
            case "11"  : sliderItems.add(new resultSliderItem(R.drawable.chemistry));
                sliderItems.add(new resultSliderItem(R.drawable.maths));
                sliderItems.add(new resultSliderItem(R.drawable.biology));
                sliderItems.add(new resultSliderItem(R.drawable.physics));
                sliderItems.add(new resultSliderItem(R.drawable.accounts));
                break;
            case "12"  : sliderItems.add(new resultSliderItem(R.drawable.chemistry));
                sliderItems.add(new resultSliderItem(R.drawable.maths));
                sliderItems.add(new resultSliderItem(R.drawable.biology));
                sliderItems.add(new resultSliderItem(R.drawable.physics));
                sliderItems.add(new resultSliderItem(R.drawable.accounts));
                break;
        }

        viewPager2.setAdapter(new ResultSliderAdapter(sliderItems,viewPager2,getApplicationContext(),standard));
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
                    case 0 : switch (standard)
                            {
                                case "1" :  subjectTitle.setText("Hindi");
                                            break;
                                case "2" :  subjectTitle.setText("Hindi");
                                            break;
                                case "3" :  subjectTitle.setText("Hindi");
                                            break;
                                case "4" :  subjectTitle.setText("Hindi");
                                            break;
                                case "5" :  subjectTitle.setText("Hindi");
                                            break;
                                case "6" :  subjectTitle.setText("Hindi");
                                            break;
                                case "7" :  subjectTitle.setText("Hindi");
                                            break;
                                case "8" :  subjectTitle.setText("Hindi");
                                            break;
                                case "9" :  subjectTitle.setText("Hindi");
                                            break;
                                case "10" :  subjectTitle.setText("Hindi");
                                            break;
                                case "11" :  subjectTitle.setText("Chemistry");
                                            break;
                                case "12" :  subjectTitle.setText("Chemistry");
                                            break;
                            }
                        subjectTitle.setTextColor(ContextCompat.getColor(ResultActivity.this,R.color.creative_red));
                        break;
                    case 1 : subjectTitle.setText("Mathematics");
                        subjectTitle.setTextColor(ContextCompat.getColor(ResultActivity.this,R.color.creative_green));
                        break;
                    case 2 : switch (standard)
                            {
                                case "1" :  subjectTitle.setText("General Knowledge");
                                    break;
                                case "2" :  subjectTitle.setText("General Knowledge");
                                    break;
                                case "3" :  subjectTitle.setText("General Knowledge");
                                    break;
                                case "4" :  subjectTitle.setText("Science");
                                    break;
                                case "5" :  subjectTitle.setText("Science");
                                    break;
                                case "6" :  subjectTitle.setText("Science");
                                    break;
                                case "7" :  subjectTitle.setText("Science");
                                    break;
                                case "8" :  subjectTitle.setText("Science");
                                    break;
                                case "9" :  subjectTitle.setText("Science");
                                    break;
                                case "10" :  subjectTitle.setText("Science");
                                    break;
                                case "11" :  subjectTitle.setText("Biology");
                                    break;
                                case "12" :  subjectTitle.setText("Biology");
                                    break;
                            }
                        subjectTitle.setTextColor(ContextCompat.getColor(ResultActivity.this,R.color.creative_yellow_dark));
                        break;
                    case 3 : switch (standard)
                            {
                                case "1" :  subjectTitle.setText("Moral Values");
                                    break;
                                case "2" :  subjectTitle.setText("Moral Values");
                                    break;
                                case "3" :  subjectTitle.setText("Moral Values");
                                    break;
                                case "4" :  subjectTitle.setText("Moral Values");
                                    break;
                                case "5" :  subjectTitle.setText("Moral Values");
                                    break;
                                case "6" :  subjectTitle.setText("General Knowledge");
                                    break;
                                case "7" :  subjectTitle.setText("General Knowledge");
                                    break;
                                case "8" :  subjectTitle.setText("General Knowledge");
                                    break;
                                case "9" :  subjectTitle.setText("General Knowledge");
                                    break;
                                case "10" :  subjectTitle.setText("General Knowledge");
                                    break;
                                case "11" :  subjectTitle.setText("Physics");
                                    break;
                                case "12" :  subjectTitle.setText("Physics");
                                    break;
                            }
                        subjectTitle.setTextColor(ContextCompat.getColor(ResultActivity.this,R.color.creative_sky_blue));
                        break;
                    case 4 : switch (standard)
                            {
                                case "1" :  subjectTitle.setText("English");
                                    break;
                                case "2" :  subjectTitle.setText("English");
                                    break;
                                case "3" :  subjectTitle.setText("English");
                                    break;
                                case "4" :  subjectTitle.setText("English");
                                    break;
                                case "5" :  subjectTitle.setText("English");
                                    break;
                                case "6" :  subjectTitle.setText("English");
                                    break;
                                case "7" :  subjectTitle.setText("English");
                                    break;
                                case "8" :  subjectTitle.setText("English");
                                    break;
                                case "9" :  subjectTitle.setText("English");
                                    break;
                                case "10" :  subjectTitle.setText("English");
                                    break;
                                case "11" :  subjectTitle.setText("Accounts");
                                    break;
                                case "12" :  subjectTitle.setText("Accounts");
                                    break;
                            }
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
