package com.techtitudetribe.aadharshila;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rd.PageIndicatorView;

public class WalkthroughActivity extends AppCompatActivity {

    private ViewPager walkthroughPager;
    private PageIndicatorView pageIndicatorView;

    private WalkthroughAdapter sliderAdapter;
    private Button getStarted;
    private RelativeLayout leftButtoon,rightButton;
    private ImageView back,forward;

    private int mCurrentPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);

        walkthroughPager = (ViewPager) findViewById(R.id.walkthrough_viwepager);
        leftButtoon = (RelativeLayout) findViewById(R.id.walkthrough_left_button);
        rightButton = (RelativeLayout) findViewById(R.id.walkthrough_right_button);
        getStarted = (Button) findViewById(R.id.get_started_button);

        back = (ImageView) findViewById(R.id.back_arrow_background);
        forward = (ImageView) findViewById(R.id.forward_arrow_background);

        pageIndicatorView = findViewById(R.id.pager_indicator_walkthrough);
        pageIndicatorView.setCount(5);

        sliderAdapter = new WalkthroughAdapter(this);
        walkthroughPager.setAdapter(sliderAdapter);


        walkthroughPager.addOnPageChangeListener(viewListener);

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walkthroughPager.setCurrentItem(mCurrentPager + 1);
            }
        });

        leftButtoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walkthroughPager.setCurrentItem(mCurrentPager - 1);
            }
        });

        if (restorePrefData()) {
            Intent splashActivity = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(splashActivity);
            finish();
        }
    }
    private boolean restorePrefData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = sharedPreferences.getBoolean("isIntroOpened",false);
        return isIntroActivityOpenedBefore;
    }



    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPager = position;

            if (position ==0)
            {
                rightButton.setEnabled(true);
                leftButtoon.setEnabled(false);
                leftButtoon.setVisibility(View.INVISIBLE);
                getStarted.setVisibility(View.INVISIBLE);
                pageIndicatorView.setVisibility(View.VISIBLE);
            } else if (position == 4) {
                rightButton.setEnabled(true);
                leftButtoon.setEnabled(true);
                leftButtoon.setVisibility(View.INVISIBLE);
                rightButton.setVisibility(View.INVISIBLE);
                pageIndicatorView.setVisibility(View.INVISIBLE);
                getStarted.setVisibility(View.VISIBLE);
                getStarted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent splashIntent = new Intent(WalkthroughActivity.this,LoginActivity.class);
                        startActivity(splashIntent);
                        savePrefsData();
                        finish();
                    }
                });

            } else {
                rightButton.setEnabled(true);
                leftButtoon.setEnabled(true);
                leftButtoon.setVisibility(View.VISIBLE);
                getStarted.setVisibility(View.INVISIBLE);
                rightButton.setVisibility(View.VISIBLE);
                pageIndicatorView.setVisibility(View.VISIBLE);
            }

            pageIndicatorView.setSelection(position);
            switch (position)
            {
                case 0 : pageIndicatorView.setSelectedColor(ContextCompat.getColor(WalkthroughActivity.this,R.color.creative_violet));
                        back.setColorFilter(ContextCompat.getColor(WalkthroughActivity.this,R.color.creative_violet));
                        forward.setColorFilter(ContextCompat.getColor(WalkthroughActivity.this,R.color.creative_violet));
                    break;
                case 1 : pageIndicatorView.setSelectedColor(ContextCompat.getColor(WalkthroughActivity.this,R.color.creative_red));
                    back.setColorFilter(ContextCompat.getColor(WalkthroughActivity.this,R.color.creative_red));
                    forward.setColorFilter(ContextCompat.getColor(WalkthroughActivity.this,R.color.creative_red));
                    break;
                case 2 : pageIndicatorView.setSelectedColor(ContextCompat.getColor(WalkthroughActivity.this,R.color.creative_orange));
                    back.setColorFilter(ContextCompat.getColor(WalkthroughActivity.this,R.color.creative_orange));
                    forward.setColorFilter(ContextCompat.getColor(WalkthroughActivity.this,R.color.creative_orange));
                    break;
                case 3 : pageIndicatorView.setSelectedColor(ContextCompat.getColor(WalkthroughActivity.this,R.color.creative_sky_blue));
                    back.setColorFilter(ContextCompat.getColor(WalkthroughActivity.this,R.color.creative_sky_blue));
                    forward.setColorFilter(ContextCompat.getColor(WalkthroughActivity.this,R.color.creative_sky_blue));
                    break;
                case 4 : pageIndicatorView.setSelectedColor(ContextCompat.getColor(WalkthroughActivity.this,R.color.creative_green));
                    back.setColorFilter(ContextCompat.getColor(WalkthroughActivity.this,R.color.creative_green));
                    forward.setColorFilter(ContextCompat.getColor(WalkthroughActivity.this,R.color.creative_green));
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };



    private void savePrefsData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();
    }
}