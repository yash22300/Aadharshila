package com.techtitudetribe.aadharshila;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class CourseViewActivity extends AppCompatActivity {

    private RecyclerView lectureList,pdfList;
    private String course,lecture;
    private TextView titleText;
    private DatabaseReference pdfRef,lectureRef;
    private ProgressBar progressBar1,progressBar2;
    private RelativeLayout noInternetLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);

        lectureList = (RecyclerView) findViewById(R.id.course_lecture_list_view);
        pdfList = (RecyclerView) findViewById(R.id.pdf_course_list_view);
        progressBar1= (ProgressBar) findViewById(R.id.lecture_course_progress_bar);
        progressBar2= (ProgressBar) findViewById(R.id.pdf_course_progress_bar);
        noInternetLayout = (RelativeLayout) findViewById(R.id.course_view_no_internet_layout);
        titleText = (TextView) findViewById(R.id.course_view_title_name);

        course = getIntent().getExtras().getString("title");
        titleText.setText(course);
        lecture = getIntent().getExtras().getString("lecNumber");

        pdfRef = FirebaseDatabase.getInstance().getReference().child("Courses").child("Course"+lecture).child("Books");
        lectureRef = FirebaseDatabase.getInstance().getReference().child("Courses").child("Course"+lecture).child("Lectures");

        lectureList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        lectureList.setLayoutManager(linearLayoutManager);

        pdfList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setReverseLayout(true);
        linearLayoutManager1.setStackFromEnd(true);
        pdfList.setLayoutManager(linearLayoutManager1);

        if (!isNetworkAvailable()) {
            progressBar1.setVisibility(View.GONE);
            progressBar2.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);

        } else  {
            noInternetLayout.setVisibility(View.INVISIBLE);
            displayAllLectures();
            displayAllPdfs();
        }

    }


    private void displayAllLectures() {
        Query sortPostInDecendingOrder = lectureRef.orderByChild("count");

        FirebaseRecyclerAdapter<VideoAdapter, VidViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<VideoAdapter, VidViewHolder>
                        (
                                VideoAdapter.class,
                                R.layout.all_video_layout,
                                VidViewHolder.class,
                                sortPostInDecendingOrder
                        )
                {
                    @Override
                    protected void populateViewHolder(VidViewHolder viewHolder, final VideoAdapter model, final int position) {

                        View colorView = viewHolder.mView.findViewById(R.id.video_view);
                        TextView vidTitle = viewHolder.mView.findViewById(R.id.vid_title);
                        ProgressBar progressBar = viewHolder.mView.findViewById(R.id.vid_progress_bar);

                        int colorType = position%6;
                        switch (colorType)
                        {
                            case 0 : colorView.setBackgroundColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_red));
                                vidTitle.setTextColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_red));
                                break;
                            case 1 : colorView.setBackgroundColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_green));
                                vidTitle.setTextColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_green));
                                break;
                            case 2 : colorView.setBackgroundColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_yellow));
                                vidTitle.setTextColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_yellow));
                                break;
                            case 3 : colorView.setBackgroundColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_sky_blue));
                                vidTitle.setTextColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_sky_blue));
                                break;
                            case 4 : colorView.setBackgroundColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_orange));
                                vidTitle.setTextColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_orange));
                                break;
                            case 5 : colorView.setBackgroundColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_violet));
                                vidTitle.setTextColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_violet));
                                break;
                        }

                        //final String PostKey = getRef(position).getKey();

                        viewHolder.setVidTitle(model.getVidTitle());
                        viewHolder.setVidDescription(model.getVidDescription());
                        viewHolder.setCoverUrl(getApplicationContext(),model.getCoverUrl());
                        progressBar1.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(CourseViewActivity.this,LectureViewActivity.class);
                                intent.putExtra("title",model.getVidTitle());
                                intent.putExtra("url",model.getVidUrl());
                                intent.putExtra("type","Course"+lecture);
                                intent.putExtra("lecNumber",String.valueOf(position));
                                startActivity(intent);
                            }
                        });
                    }
                };
        lectureList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class VidViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public VidViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setVidTitle(String vidTitle) {
            TextView title = (TextView) mView.findViewById(R.id.vid_title);
            title.setText(vidTitle);
        }


        public void setVidDescription(String vidDescription) {
            TextView description = (TextView) mView.findViewById(R.id.vid_description);
            description.setText(vidDescription);
        }

        public void setCoverUrl(Context ctx,String coverUrl) {
            ImageView coverImage = (ImageView) mView.findViewById(R.id.vid_cover_page);
            coverImage.setImageResource(R.drawable.vid_background);
            Picasso.with(ctx).load(coverUrl).into(coverImage);


        }
    }
    private void displayAllPdfs() {

        Query sortPostInDecendingOrder = pdfRef.orderByChild("count");
        FirebaseRecyclerAdapter<UpdatePdf, PdfViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<UpdatePdf, PdfViewHolder>
                        (
                                UpdatePdf.class,
                                R.layout.all_pdf_layout,
                                PdfViewHolder.class,
                                sortPostInDecendingOrder
                        )
                {
                    @Override
                    protected void populateViewHolder(PdfViewHolder viewHolder, final UpdatePdf model, int position) {

                        View colorView = viewHolder.mView.findViewById(R.id.pdf_view);
                        TextView pdfTitle = viewHolder.mView.findViewById(R.id.pdf_title);

                        int colorType = position%6;
                        switch (colorType)
                        {
                            case 0 : colorView.setBackgroundColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_red));
                                pdfTitle.setTextColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_red));
                                break;
                            case 1 : colorView.setBackgroundColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_green));
                                pdfTitle.setTextColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_green));
                                break;
                            case 2 : colorView.setBackgroundColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_yellow));
                                pdfTitle.setTextColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_yellow));
                                break;
                            case 3 : colorView.setBackgroundColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_sky_blue));
                                pdfTitle.setTextColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_sky_blue));
                                break;
                            case 4 : colorView.setBackgroundColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_orange));
                                pdfTitle.setTextColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_orange));
                                break;
                            case 5 : colorView.setBackgroundColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_violet));
                                pdfTitle.setTextColor(ContextCompat.getColor(CourseViewActivity.this,R.color.creative_violet));
                                break;
                            //setBackgroundTint(ContextCompat.getColor(getApplicationContext(), R.color.creative_red)); break;
                        }

                        //final String PostKey = getRef(position).getKey();

                        viewHolder.setTitle(model.getTitle());
                        viewHolder.setDescription(model.getDescription());
                        progressBar2.setVisibility(View.GONE);
                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(CourseViewActivity.this, PdfViewActivity.class);
                                //intent.setAction(Intent.ACTION_VIEW);
                                intent.putExtra("title", model.getTitle());
                                intent.putExtra("url", model.getUrl());
                                //intent.setData(Uri.parse(model.getUrl()));
                                //intent.putExtra("Postkey",PostKey);
                                //intent.setData(Uri.parse(UpdatePdf.getUrl()));
                                startActivity(intent);
                            }
                        });

                    }
                };
        pdfList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class PdfViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public PdfViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setTitle(String title) {
            TextView pdfTitle = (TextView) mView.findViewById(R.id.pdf_title);
            pdfTitle.setText(title);
        }

        public void setDescription(String description) {
            TextView pdfDescription = (TextView) mView.findViewById(R.id.pdf_description);
            pdfDescription.setText(description);
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CourseViewActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}