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
import android.net.Uri;
import android.os.Bundle;
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

public class NewsFeedActivity extends AppCompatActivity {

    private RecyclerView newsListView;
    private DatabaseReference newsRef;
    private ProgressBar progressBar;
    private RelativeLayout mToolbar;
    private RelativeLayout noInternetLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        newsListView = (RecyclerView)findViewById(R.id.news_list_view);
        newsRef = FirebaseDatabase.getInstance().getReference().child("News");

        progressBar=  findViewById(R.id.news_progress_bar);
        noInternetLayout = findViewById(R.id.news_no_internet_layout);

        mToolbar =  findViewById(R.id.back_news);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsFeedActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        newsListView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        newsListView.setLayoutManager(linearLayoutManager);


        if (!isNetworkAvailable()) {
            progressBar.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);

        } else  {
            noInternetLayout.setVisibility(View.INVISIBLE);
            displayAllNewsFeed();
        }

    }

    private void displayAllNewsFeed() {
        Query sortNews = newsRef.orderByChild("newsCount");

        FirebaseRecyclerAdapter<NewsAdapter,NewsViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<NewsAdapter, NewsViewHolder>(
                        NewsAdapter.class,
                        R.layout.news_layout,
                        NewsViewHolder.class,
                        sortNews
                ) {
                    @Override
                    protected void populateViewHolder(NewsViewHolder newsViewHolder, final NewsAdapter newsAdapter, int i) {

                        View colorView = newsViewHolder.mView.findViewById(R.id.news_layout_view);
                        TextView pdfTitle = newsViewHolder.mView.findViewById(R.id.news_layout_title);

                        int colorType = i%6;
                        switch (colorType)
                        {
                            case 0 : colorView.setBackgroundColor(ContextCompat.getColor(NewsFeedActivity.this,R.color.creative_red));
                                pdfTitle.setTextColor(ContextCompat.getColor(NewsFeedActivity.this,R.color.creative_red));
                                break;
                            case 1 : colorView.setBackgroundColor(ContextCompat.getColor(NewsFeedActivity.this,R.color.creative_green));
                                pdfTitle.setTextColor(ContextCompat.getColor(NewsFeedActivity.this,R.color.creative_green));
                                break;
                            case 2 : colorView.setBackgroundColor(ContextCompat.getColor(NewsFeedActivity.this,R.color.creative_yellow));
                                pdfTitle.setTextColor(ContextCompat.getColor(NewsFeedActivity.this,R.color.creative_yellow));
                                break;
                            case 3 : colorView.setBackgroundColor(ContextCompat.getColor(NewsFeedActivity.this,R.color.creative_sky_blue));
                                pdfTitle.setTextColor(ContextCompat.getColor(NewsFeedActivity.this,R.color.creative_sky_blue));
                                break;
                            case 4 : colorView.setBackgroundColor(ContextCompat.getColor(NewsFeedActivity.this,R.color.creative_orange));
                                pdfTitle.setTextColor(ContextCompat.getColor(NewsFeedActivity.this,R.color.creative_orange));
                                break;
                            case 5 : colorView.setBackgroundColor(ContextCompat.getColor(NewsFeedActivity.this,R.color.creative_violet));
                                pdfTitle.setTextColor(ContextCompat.getColor(NewsFeedActivity.this,R.color.creative_violet));
                                break;
                            //setBackgroundTint(ContextCompat.getColor(getApplicationContext(), R.color.creative_red)); break;
                        }
                        newsViewHolder.setNewsTitle(newsAdapter.getNewsTitle());
                        newsViewHolder.setNewsDescription(newsAdapter.getNewsDescription());
                        newsViewHolder.setNewsImageUrl(getApplicationContext(),newsAdapter.getNewsImageUrl());
                        progressBar.setVisibility(View.GONE);

                        newsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(newsAdapter.getNewsUrl()));
                                startActivity(intent);
                            }
                        });
                    }
                };
        newsListView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setNewsTitle(String newsTitle)
        {
            TextView textTitle = (TextView) mView.findViewById(R.id.news_layout_title);
            textTitle.setText(newsTitle);
        }

        public void setNewsImageUrl(Context ctx,String newsImageUrl)
        {
            ImageView imageNews = (ImageView) mView.findViewById(R.id.news_layout_image);
            Picasso.with(ctx).load(newsImageUrl).into(imageNews);
        }

        public void setNewsDescription(String newsDescription)
        {
            TextView textDes = (TextView) mView.findViewById(R.id.news_layout_description);
            textDes.setText(newsDescription);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NewsFeedActivity.this,MainActivity.class);
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