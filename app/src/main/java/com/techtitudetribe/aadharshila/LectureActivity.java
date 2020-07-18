package com.techtitudetribe.aadharshila;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class LectureActivity extends AppCompatActivity {

    private RecyclerView englishListView,mathsListView,scienceListView;
    private DatabaseReference engRef,stuRef;
    private String status,currentUser;
    private RelativeLayout mssgLayout;
    private FirebaseAuth mAuth;
    private RelativeLayout noInternetLayout;
    private RelativeLayout mToolbar;
    private SearchView bookSearch;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        mAuth=FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        noInternetLayout = findViewById(R.id.id_no_internet_vid_layout);
        scrollView = findViewById(R.id.video_container);

        mToolbar =  findViewById(R.id.back_lecture);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LectureActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        engRef = FirebaseDatabase.getInstance().getReference().child("Lectures");
        stuRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUser);
        stuRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    status = dataSnapshot.child("Subscription").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        englishListView = (RecyclerView) findViewById(R.id.english_list_view);
        englishListView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        englishListView.setLayoutManager(linearLayoutManager);

        mathsListView = (RecyclerView) findViewById(R.id.maths_list_view);
        mathsListView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setReverseLayout(true);
        linearLayoutManager1.setStackFromEnd(true);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mathsListView.setLayoutManager(linearLayoutManager1);

        scienceListView = (RecyclerView) findViewById(R.id.science_list_view);
        scienceListView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        scienceListView.setLayoutManager(linearLayoutManager2);

        mssgLayout=findViewById(R.id.subscription_mssg_layout);

        if (!isNetworkAvailable()) {
            scrollView.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);

        } else  {
            noInternetLayout.setVisibility(View.INVISIBLE);
            scrollView.setVisibility(View.VISIBLE);
            DisplayAllEnglishVideos();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void DisplayAllEnglishVideos() {
        Query sortPostInDecendingOrder = engRef.orderByChild("count");

        FirebaseRecyclerAdapter<VideoAdapter, LectureActivity.EnglishVidViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<VideoAdapter, LectureActivity.EnglishVidViewHolder>
                        (
                                VideoAdapter.class,
                                R.layout.all_video_layout,
                                LectureActivity.EnglishVidViewHolder.class,
                                sortPostInDecendingOrder
                        )
                {
                    @Override
                    protected void populateViewHolder(LectureActivity.EnglishVidViewHolder viewHolder, final VideoAdapter model, int position) {

                        View colorView = viewHolder.mView.findViewById(R.id.video_view);
                        TextView vidTitle = viewHolder.mView.findViewById(R.id.vid_title);
                        progressBar = viewHolder.mView.findViewById(R.id.vid_progress_bar);

                        int colorType = position%6;
                        switch (colorType)
                        {
                            case 0 : colorView.setBackgroundColor(ContextCompat.getColor(LectureActivity.this,R.color.creative_red));
                                vidTitle.setTextColor(ContextCompat.getColor(LectureActivity.this,R.color.creative_red));
                                break;
                            case 1 : colorView.setBackgroundColor(ContextCompat.getColor(LectureActivity.this,R.color.creative_green));
                                vidTitle.setTextColor(ContextCompat.getColor(LectureActivity.this,R.color.creative_green));
                                break;
                            case 2 : colorView.setBackgroundColor(ContextCompat.getColor(LectureActivity.this,R.color.creative_yellow));
                                vidTitle.setTextColor(ContextCompat.getColor(LectureActivity.this,R.color.creative_yellow));
                                break;
                            case 3 : colorView.setBackgroundColor(ContextCompat.getColor(LectureActivity.this,R.color.creative_sky_blue));
                                vidTitle.setTextColor(ContextCompat.getColor(LectureActivity.this,R.color.creative_sky_blue));
                                break;
                            case 4 : colorView.setBackgroundColor(ContextCompat.getColor(LectureActivity.this,R.color.creative_orange));
                                vidTitle.setTextColor(ContextCompat.getColor(LectureActivity.this,R.color.creative_orange));
                                break;
                            case 5 : colorView.setBackgroundColor(ContextCompat.getColor(LectureActivity.this,R.color.creative_violet));
                                vidTitle.setTextColor(ContextCompat.getColor(LectureActivity.this,R.color.creative_violet));
                                break;
                            //setBackgroundTint(ContextCompat.getColor(getApplicationContext(), R.color.creative_red)); break;
                        }

                        //final String PostKey = getRef(position).getKey();

                        viewHolder.setVidTitle(model.getVidTitle());
                        viewHolder.setVidDescription(model.getVidDescription());
                        viewHolder.setCoverUrl(getApplicationContext(),model.getCoverUrl());
                        progressBar.setVisibility(View.GONE);

                        if(status.equals("NO"))
                        {
                            viewHolder.mView.setClickable(false);
                            mssgLayout.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            mssgLayout.setVisibility(View.INVISIBLE);
                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(LectureActivity.this,LectureViewActivity.class);
                                    //intent.setAction(Intent.ACTION_VIEW);
                                    intent.putExtra("title",model.getVidTitle());
                                    intent.putExtra("url",model.getVidUrl());
                                    //intent.setData(Uri.parse(model.getUrl()));
                                    //intent.putExtra("Postkey",PostKey);
                                    //intent.setData(Uri.parse(UpdatePdf.getUrl()));
                                    startActivity(intent);
                                }
                            });
                        }

                    }
                };
        englishListView.setAdapter(firebaseRecyclerAdapter);
        mathsListView.setAdapter(firebaseRecyclerAdapter);
        scienceListView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class EnglishVidViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public EnglishVidViewHolder(@NonNull View itemView) {
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

        public void setCoverUrl(Context ctx,String coverUrl){
            ImageView coverImage = (ImageView) mView.findViewById(R.id.vid_cover_page);
            coverImage.setImageResource(R.drawable.vid_background);
            Picasso.with(ctx).load(coverUrl).into(coverImage);


        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LectureActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
