package com.techtitudetribe.aadharshila;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

public class WalkthroughAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public WalkthroughAdapter(Context context) {
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.walkthrough1,
            R.drawable.walkthrough2,
            R.drawable.walkthrough3,
            R.drawable.walkthrough4,
            R.drawable.walkthrough5
    };

    public String[] slide_headings = {
            "Exams on Weekend",
            "Free e-Books",
            "Kid's Special Classes",
            "12+ Extra Courses",
            "Live Doubt Classes"
    };

    public String[] slide_descriptions = {
            "Exams will be conducted on every weekend according to the past 7 days' syllabus.",
            "You will find most of the books here whether these are NCERT or of Private publications.",
            "Some classes which give moral values to kid's should be given. So that, they respect elders, have some kindness.",
            "Some extra curriculum activity courses will be provided i.e. Web Development, Hacking, Dance, Self-Defense and many more.",
            "On every weekend, there will be a live session for students' doubt."
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.walkthrough_layout,container,false);

        ImageView sliderImageView = (ImageView) view.findViewById(R.id.image_walkthrough);
        TextView sliderHeading = (TextView) view.findViewById(R.id.title_walkthrough);
        TextView sliderDescription = (TextView) view.findViewById(R.id.description_walkthrough);

        LinearLayout textBackground = (LinearLayout) view.findViewById(R.id.text_walkthrough_back);
        ImageView imageBackground = (ImageView) view.findViewById(R.id.image_walkthrough_background);

        switch (position)
        {
            case 0 : textBackground.setBackgroundColor(ContextCompat.getColor(context,R.color.creative_violet_light));
                    imageBackground.setColorFilter(ContextCompat.getColor(context,R.color.creative_violet_light));
                    break;
            case 1 : textBackground.setBackgroundColor(ContextCompat.getColor(context,R.color.creative_red_light));
                imageBackground.setColorFilter(ContextCompat.getColor(context,R.color.creative_red_light));
                break;
            case 2 : textBackground.setBackgroundColor(ContextCompat.getColor(context,R.color.creative_orange_light));
                imageBackground.setColorFilter(ContextCompat.getColor(context,R.color.creative_orange_light));
                break;
            case 3 : textBackground.setBackgroundColor(ContextCompat.getColor(context,R.color.creative_sky_blue_light));
                imageBackground.setColorFilter(ContextCompat.getColor(context,R.color.creative_sky_blue_light));
                break;
            case 4 : textBackground.setBackgroundColor(ContextCompat.getColor(context,R.color.creative_green_light));
                imageBackground.setColorFilter(ContextCompat.getColor(context,R.color.creative_green_light));
                break;
        }

        sliderImageView.setImageResource(slide_images[position]);
        sliderHeading.setText(slide_headings[position]);
        sliderDescription.setText(slide_descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
