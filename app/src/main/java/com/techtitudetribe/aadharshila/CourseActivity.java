package com.techtitudetribe.aadharshila;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class CourseActivity extends AppCompatActivity {

    private RecyclerView allCoursesList;
    private DatabaseReference courseRef;
    private ProgressBar progressBar;
    private RelativeLayout mToolbar;
    private RelativeLayout noInternetLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        progressBar=  findViewById(R.id.course_progress_bar);
        noInternetLayout = findViewById(R.id.course_no_internet_layout);

        allCoursesList = findViewById(R.id.course_list_view);
        allCoursesList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        allCoursesList.setLayoutManager(linearLayoutManager);

        mToolbar =  findViewById(R.id.back_course);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        courseRef= FirebaseDatabase.getInstance().getReference().child("Courses");

        if (!isNetworkAvailable()) {
            progressBar.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);

        } else  {
            noInternetLayout.setVisibility(View.INVISIBLE);
            displayAllCourses();
        }

    }

    private void displayAllCourses() {
        Query sortCoursesInDecreasingOrder= courseRef.orderByChild("crsCount");
        FirebaseRecyclerAdapter<CourseAdapter,CourseActivity.CourseViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<CourseAdapter, CourseActivity.CourseViewHolder>(
                        CourseAdapter.class,
                        R.layout.courses_layout,
                        CourseViewHolder.class,
                        sortCoursesInDecreasingOrder
                ) {

                    protected void populateViewHolder(CourseActivity.CourseViewHolder courseViewHolder, final CourseAdapter courseAdapter, final int i) {

                        RelativeLayout background = courseViewHolder.mView.findViewById(R.id.course_layout_background);
                        final RelativeLayout expandableView = courseViewHolder.mView.findViewById(R.id.expandable_view);
                        final RelativeLayout mainContainer = courseViewHolder.mView.findViewById(R.id.course_main_container);
                        RelativeLayout viewLess = courseViewHolder.mView.findViewById(R.id.course_view_less);
                        ImageView viewLessBack = courseViewHolder.mView.findViewById(R.id.course_view_less_background);
                        final RelativeLayout viewMore = courseViewHolder.mView.findViewById(R.id.course_view_more);
                        ImageView viewMoreImage = courseViewHolder.mView.findViewById(R.id.course_layout_view_more_image);
                        TextView enter = courseViewHolder.mView.findViewById(R.id.course_layout_enroll_now);
                        TextView title = courseViewHolder.mView.findViewById(R.id.course_layout_topic);


                        int colortype = i%6;
                        switch (colortype)
                        {
                            case 0 : background.setBackgroundColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_red));
                                    enter.setBackgroundColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_red));
                                    viewMoreImage.setColorFilter(ContextCompat.getColor(CourseActivity.this,R.color.creative_red));
                                    title.setTextColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_red));
                                    viewLessBack.setColorFilter(ContextCompat.getColor(CourseActivity.this,R.color.creative_red));
                                    break;
                            case 1 : background.setBackgroundColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_green));
                                enter.setBackgroundColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_green));
                                viewMoreImage.setColorFilter(ContextCompat.getColor(CourseActivity.this,R.color.creative_green));
                                title.setTextColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_green));
                                viewLessBack.setColorFilter(ContextCompat.getColor(CourseActivity.this,R.color.creative_green));
                                break;
                            case 2 : background.setBackgroundColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_yellow_dark));
                                enter.setBackgroundColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_yellow_dark));
                                viewMoreImage.setColorFilter(ContextCompat.getColor(CourseActivity.this,R.color.creative_yellow_dark));
                                title.setTextColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_yellow_dark));
                                viewLessBack.setColorFilter(ContextCompat.getColor(CourseActivity.this,R.color.creative_yellow_dark));
                                break;
                            case 3 : background.setBackgroundColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_sky_blue));
                                enter.setBackgroundColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_sky_blue));
                                viewMoreImage.setColorFilter(ContextCompat.getColor(CourseActivity.this,R.color.creative_sky_blue));
                                title.setTextColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_sky_blue));
                                viewLessBack.setColorFilter(ContextCompat.getColor(CourseActivity.this,R.color.creative_sky_blue));
                                break;
                            case 4 : background.setBackgroundColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_orange));
                                enter.setBackgroundColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_orange));
                                viewMoreImage.setColorFilter(ContextCompat.getColor(CourseActivity.this,R.color.creative_orange));
                                title.setTextColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_orange));
                                viewLessBack.setColorFilter(ContextCompat.getColor(CourseActivity.this,R.color.creative_orange));
                                break;
                            case 5 : background.setBackgroundColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_violet));
                                enter.setBackgroundColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_violet));
                                viewMoreImage.setColorFilter(ContextCompat.getColor(CourseActivity.this,R.color.creative_violet));
                                title.setTextColor(ContextCompat.getColor(CourseActivity.this,R.color.creative_violet));
                                viewLessBack.setColorFilter(ContextCompat.getColor(CourseActivity.this,R.color.creative_violet));
                                break;
                        }

                        enter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(CourseActivity.this,CourseViewActivity.class);
                                intent.putExtra("title",courseAdapter.getCrsTitle());
                                intent.putExtra("lecNumber",String.valueOf(i+1));
                                startActivity(intent);
                            }
                        });

                        courseViewHolder.setCrsTitle(courseAdapter.getCrsTitle());
                        courseViewHolder.setCrsDescription(courseAdapter.getCrsDescription());
                        courseViewHolder.setCrsDuration(courseAdapter.getCrsDuration());
                        courseViewHolder.setCrsPrice(courseAdapter.getCrsPrice());
                        courseViewHolder.setCrsRequirement(courseAdapter.getCrsRequirement());
                        courseViewHolder.setCrsImageUrl(getApplicationContext(),courseAdapter.getCrsImageUrl());
                        progressBar.setVisibility(View.GONE);

                        viewMore.setVisibility(View.VISIBLE);
                        viewMore.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onClick(View v) {
                                expandableView.setVisibility(View.VISIBLE);
                                viewMore.setVisibility(View.GONE);
                                RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams)mainContainer.getLayoutParams();
                                relativeParams.setMargins(0, 0, 0, -60);  // left, top, right, bottom
                                mainContainer.setLayoutParams(relativeParams);
                            }
                        });

                        viewLess.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onClick(View v) {
                                expandableView.setVisibility(View.GONE);
                                viewMore.setVisibility(View.VISIBLE);
                                RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams)mainContainer.getLayoutParams();
                                relativeParams.setMargins(0, 0, 0, 60);  // left, top, right, bottom
                                mainContainer.setLayoutParams(relativeParams);
                            }
                        });
                    }
                };
        allCoursesList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setCrsTitle(String crsTitle)
        {
            TextView titleText = (TextView) mView.findViewById(R.id.course_layout_topic);
            titleText.setText(crsTitle);
        }
        public void setCrsDescription(String crsDescription)
        {
            TextView desText = (TextView) mView.findViewById(R.id.course_layout_description);
            desText.setText("Description : "+crsDescription);
        }
        public void setCrsDuration(String crsDuration)
        {
            TextView durationText = (TextView)mView.findViewById(R.id.course_layout_duration);
            durationText.setText("Duration : "+crsDuration);
        }
        public void setCrsPrice(String crsPrice)
        {
            TextView priceText = (TextView)mView.findViewById(R.id.course_layout_price);
            priceText.setText("Price : Rs."+crsPrice);
        }
        public void setCrsRequirement(String crsRequirement)
        {
            TextView reqText = (TextView) mView.findViewById(R.id.course_layout_requirement);
            reqText.setText(crsRequirement);
        }
        public void setCrsImageUrl(Context ctx,String crsImageUrl)
        {
            ImageView imageCover = (ImageView) mView.findViewById(R.id.course_layout_cover);
            Picasso.with(ctx).load(crsImageUrl).into(imageCover);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CourseActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}