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

    private RecyclerView sub1ListView,sub2ListView,sub3ListView,sub4ListView,sub5ListView;
    private DatabaseReference subRef1,subRef2,subRef3,subRef4,subRef5,stuRef;
    private String status,currentUser;
    private RelativeLayout mssgLayout;
    private FirebaseAuth mAuth;
    private RelativeLayout noInternetLayout;
    private RelativeLayout mToolbar;
    private String standard;
    private TextView sub1,sub2,sub3,sub4,sub5;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar1,progressBar2,progressBar3,progressBar4,progressBar5;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        mAuth=FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        noInternetLayout = findViewById(R.id.id_no_internet_vid_layout);
        scrollView = findViewById(R.id.video_container);

        sub1 = (TextView) findViewById(R.id.sub1_title_name);
        sub2 = (TextView) findViewById(R.id.sub2_title_name);
        sub3 = (TextView) findViewById(R.id.sub3_title_name);
        sub4 = (TextView) findViewById(R.id.sub4_title_name);
        sub5 = (TextView) findViewById(R.id.sub5_title_name);

        progressBar1 = (ProgressBar) findViewById(R.id.sub1_progress_bar);
        progressBar2 = (ProgressBar) findViewById(R.id.sub2_progress_bar);
        progressBar3 = (ProgressBar) findViewById(R.id.sub3_progress_bar);
        progressBar4 = (ProgressBar) findViewById(R.id.sub4_progress_bar);
        progressBar5 = (ProgressBar) findViewById(R.id.sub5_progress_bar);

        standard = getIntent().getExtras().getString("class");
        switch (standard)
        {
            case "1" :   subRef1 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 1").child("Moral Values");
                subRef2 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 1").child("Mathematics");
                subRef3 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 1").child("General Knowledge");
                subRef4 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 1").child("Hindi");
                subRef5 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 1").child("English");
                sub1.setText("Moral Values");
                sub2.setText("Mathematics");
                sub3.setText("General Knowledge");
                sub4.setText("Hindi");
                sub5.setText("English");
                break;
            case "2" :   subRef1 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 2").child("Moral Values");
                subRef2 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 2").child("Mathematics");
                subRef3 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 2").child("General Knowledge");
                subRef4 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 2").child("Hindi");
                subRef5 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 2").child("English");
                sub1.setText("Moral Values");
                sub2.setText("Mathematics");
                sub3.setText("General Knowledge");
                sub4.setText("Hindi");
                sub5.setText("English");
                break;
            case "3" :   subRef1 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 3").child("Moral Values");
                subRef2 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 3").child("Mathematics");
                subRef3 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 3").child("General Knowledge");
                subRef4 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 3").child("Hindi");
                subRef5 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 3").child("English");
                sub1.setText("Moral Values");
                sub2.setText("Mathematics");
                sub3.setText("General Knowledge");
                sub4.setText("Hindi");
                sub5.setText("English");
                break;
            case "4" :   subRef1 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 4").child("English");
                subRef2 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 4").child("Mathematics");
                subRef3 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 4").child("Science");
                subRef4 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 4").child("Hindi");
                subRef5 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 4").child("Moral Values");
                sub1.setText("Moral Values");
                sub2.setText("Mathematics");
                sub3.setText("Science");
                sub4.setText("Hindi");
                sub5.setText("English");
                break;
            case "5" :   subRef1 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 5").child("English");
                subRef2 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 5").child("Mathematics");
                subRef3 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 5").child("Science");
                subRef4 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 5").child("Hindi");
                subRef5 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 5").child("Moral Values");
                sub1.setText("Moral Values");
                sub2.setText("Mathematics");
                sub3.setText("Science");
                sub4.setText("Hindi");
                sub5.setText("English");
                break;
            case "6" :   subRef1 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 6").child("English");
                subRef2 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 6").child("Mathematics");
                subRef3 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 6").child("General Knowledge");
                subRef4 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 6").child("Hindi");
                subRef5 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 6").child("Science");
                sub1.setText("English");
                sub2.setText("Mathematics");
                sub3.setText("General Knowledge");
                sub4.setText("Hindi");
                sub5.setText("Science");
                break;
            case "7" :   subRef1 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 7").child("English");
                subRef2 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 7").child("Mathematics");
                subRef3 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 7").child("General Knowledge");
                subRef4 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 7").child("Hindi");
                subRef5 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 7").child("Science");
                sub1.setText("English");
                sub2.setText("Mathematics");
                sub3.setText("General Knowledge");
                sub4.setText("Hindi");
                sub5.setText("Science");
                break;
            case "8" :   subRef1 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 8").child("English");
                subRef2 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 8").child("Mathematics");
                subRef3 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 8").child("General Knowledge");
                subRef4 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 8").child("Hindi");
                subRef5 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 8").child("Science");
                sub1.setText("English");
                sub2.setText("Mathematics");
                sub3.setText("General Knowledge");
                sub4.setText("Hindi");
                sub5.setText("Science");
                break;
            case "9" :   subRef1 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 9").child("English");
                subRef2 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 9").child("Mathematics");
                subRef3 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 9").child("General Knowledge");
                subRef4 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 9").child("Hindi");
                subRef5 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 9").child("Science");
                sub1.setText("English");
                sub2.setText("Mathematics");
                sub3.setText("General Knowledge");
                sub4.setText("Hindi");
                sub5.setText("Science");
                break;
            case "10" :   subRef1 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 10").child("English");
                subRef2 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 10").child("Mathematics");
                subRef3 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 10").child("General Knowledge");
                subRef4 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 10").child("Hindi");
                subRef5 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 10").child("Science");
                sub1.setText("English");
                sub2.setText("Mathematics");
                sub3.setText("General Knowledge");
                sub4.setText("Hindi");
                sub5.setText("Science");
                break;
            case "11" :   subRef1 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 11").child("Chemistry");
                subRef2 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 11").child("Mathematics");
                subRef3 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 11").child("Biology");
                subRef4 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 11").child("Accountancy");
                subRef5 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 11").child("Physics");
                sub1.setText("Chemistry");
                sub2.setText("Mathematics");
                sub3.setText("Biology");
                sub4.setText("Accountancy");
                sub5.setText("Physics");
                break;
            case "12" :   subRef1 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 12").child("Chemistry");
                subRef2 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 12").child("Mathematics");
                subRef3 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 12").child("Biology");
                subRef4 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 12").child("Accountancy");
                subRef5 = FirebaseDatabase.getInstance().getReference().child("Lectures").child("Class 12").child("Physics");
                sub1.setText("Chemistry");
                sub2.setText("Mathematics");
                sub3.setText("Biology");
                sub4.setText("Accountancy");
                sub5.setText("Physics");
                break;
        }

        mToolbar =  findViewById(R.id.back_lecture);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LectureActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

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

        sub1ListView = (RecyclerView) findViewById(R.id.sub1_list_view);
        sub1ListView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        sub1ListView.setLayoutManager(linearLayoutManager);

        sub2ListView = (RecyclerView) findViewById(R.id.sub2_list_view);
        sub2ListView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setReverseLayout(true);
        linearLayoutManager1.setStackFromEnd(true);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        sub2ListView.setLayoutManager(linearLayoutManager1);

        sub3ListView = (RecyclerView) findViewById(R.id.sub3_list_view);
        sub3ListView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        sub3ListView.setLayoutManager(linearLayoutManager2);

        sub4ListView = (RecyclerView) findViewById(R.id.sub4_list_view);
        sub4ListView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setReverseLayout(true);
        linearLayoutManager3.setStackFromEnd(true);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        sub4ListView.setLayoutManager(linearLayoutManager3);

        sub5ListView = (RecyclerView) findViewById(R.id.sub5_list_view);
        sub5ListView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(this);
        linearLayoutManager4.setReverseLayout(true);
        linearLayoutManager4.setStackFromEnd(true);
        linearLayoutManager4.setOrientation(LinearLayoutManager.HORIZONTAL);
        sub5ListView.setLayoutManager(linearLayoutManager4);

        mssgLayout=findViewById(R.id.subscription_mssg_layout);

        if (!isNetworkAvailable()) {
            scrollView.setVisibility(View.GONE);
            progressBar1.setVisibility(View.GONE);
            progressBar2.setVisibility(View.GONE);
            progressBar3.setVisibility(View.GONE);
            progressBar4.setVisibility(View.GONE);
            progressBar5.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);

        } else  {
            noInternetLayout.setVisibility(View.INVISIBLE);
            scrollView.setVisibility(View.VISIBLE);
            DisplayAllSub1Videos();
            DisplayAllSub2Videos();
            DisplayAllSub3Videos();
            DisplayAllSub4Videos();
            DisplayAllSub5Videos();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void DisplayAllSub1Videos() {
        Query sortPostInDecendingOrder = subRef1.orderByChild("count");

        FirebaseRecyclerAdapter<VideoAdapter, LectureActivity.Sub1VidViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<VideoAdapter, LectureActivity.Sub1VidViewHolder>
                        (
                                VideoAdapter.class,
                                R.layout.all_video_layout,
                                LectureActivity.Sub1VidViewHolder.class,
                                sortPostInDecendingOrder
                        )
                {
                    @Override
                    protected void populateViewHolder(LectureActivity.Sub1VidViewHolder viewHolder, final VideoAdapter model, final int position) {

                        View colorView = viewHolder.mView.findViewById(R.id.video_view);
                        TextView vidTitle = viewHolder.mView.findViewById(R.id.vid_title);

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
                        progressBar1.setVisibility(View.GONE);

                        if(status.equals("NO"))
                        {
                            viewHolder.mView.setClickable(true);
                            mssgLayout.setVisibility(View.INVISIBLE);
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
                                    intent.putExtra("type",standard);
                                    intent.putExtra("sub",sub1.getText().toString());
                                    intent.putExtra("lecNumber",String.valueOf(position));
                                    //intent.setData(Uri.parse(model.getUrl()));
                                    //intent.putExtra("Postkey",PostKey);
                                    //intent.setData(Uri.parse(UpdatePdf.getUrl()));
                                    startActivity(intent);
                                }
                            });
                        }

                    }
                };
        sub1ListView.setAdapter(firebaseRecyclerAdapter);
    }
    private void DisplayAllSub2Videos() {
        Query sortPostInDecendingOrder = subRef2.orderByChild("count");

        FirebaseRecyclerAdapter<VideoAdapter, LectureActivity.Sub2VidViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<VideoAdapter, LectureActivity.Sub2VidViewHolder>
                        (
                                VideoAdapter.class,
                                R.layout.all_video_layout,
                                LectureActivity.Sub2VidViewHolder.class,
                                sortPostInDecendingOrder
                        )
                {
                    @Override
                    protected void populateViewHolder(LectureActivity.Sub2VidViewHolder viewHolder, final VideoAdapter model, final int position) {

                        View colorView = viewHolder.mView.findViewById(R.id.video_view);
                        TextView vidTitle = viewHolder.mView.findViewById(R.id.vid_title);

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
                        progressBar2.setVisibility(View.GONE);

                        if(status.equals("NO"))
                        {
                            viewHolder.mView.setClickable(true);
                        }
                        else
                        {
                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(LectureActivity.this,LectureViewActivity.class);
                                    //intent.setAction(Intent.ACTION_VIEW);
                                    intent.putExtra("title",model.getVidTitle());
                                    intent.putExtra("url",model.getVidUrl());
                                    intent.putExtra("type",standard);
                                    intent.putExtra("sub",sub2.getText().toString());
                                    intent.putExtra("lecNumber",String.valueOf(position));
                                    //intent.setData(Uri.parse(model.getUrl()));
                                    //intent.putExtra("Postkey",PostKey);
                                    //intent.setData(Uri.parse(UpdatePdf.getUrl()));
                                    startActivity(intent);
                                }
                            });
                        }

                    }
                };
        sub2ListView.setAdapter(firebaseRecyclerAdapter);
    }
    private void DisplayAllSub3Videos() {
        Query sortPostInDecendingOrder = subRef3.orderByChild("count");

        FirebaseRecyclerAdapter<VideoAdapter, LectureActivity.Sub3VidViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<VideoAdapter, LectureActivity.Sub3VidViewHolder>
                        (
                                VideoAdapter.class,
                                R.layout.all_video_layout,
                                LectureActivity.Sub3VidViewHolder.class,
                                sortPostInDecendingOrder
                        )
                {
                    @Override
                    protected void populateViewHolder(LectureActivity.Sub3VidViewHolder viewHolder, final VideoAdapter model, final int position) {

                        View colorView = viewHolder.mView.findViewById(R.id.video_view);
                        TextView vidTitle = viewHolder.mView.findViewById(R.id.vid_title);

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
                        progressBar3.setVisibility(View.GONE);

                        if(status.equals("NO"))
                        {
                            viewHolder.mView.setClickable(true);
                        }
                        else
                        {
                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(LectureActivity.this,LectureViewActivity.class);
                                    //intent.setAction(Intent.ACTION_VIEW);
                                    intent.putExtra("title",model.getVidTitle());
                                    intent.putExtra("url",model.getVidUrl());
                                    intent.putExtra("type",standard);
                                    intent.putExtra("sub",sub3.getText().toString());
                                    intent.putExtra("lecNumber",String.valueOf(position));
                                    //intent.setData(Uri.parse(model.getUrl()));
                                    //intent.putExtra("Postkey",PostKey);
                                    //intent.setData(Uri.parse(UpdatePdf.getUrl()));
                                    startActivity(intent);
                                }
                            });
                        }

                    }
                };
        sub3ListView.setAdapter(firebaseRecyclerAdapter);
    }
    private void DisplayAllSub4Videos() {
        Query sortPostInDecendingOrder = subRef4.orderByChild("count");

        FirebaseRecyclerAdapter<VideoAdapter, LectureActivity.Sub4VidViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<VideoAdapter, LectureActivity.Sub4VidViewHolder>
                        (
                                VideoAdapter.class,
                                R.layout.all_video_layout,
                                LectureActivity.Sub4VidViewHolder.class,
                                sortPostInDecendingOrder
                        )
                {
                    @Override
                    protected void populateViewHolder(LectureActivity.Sub4VidViewHolder viewHolder, final VideoAdapter model, final int position) {

                        View colorView = viewHolder.mView.findViewById(R.id.video_view);
                        TextView vidTitle = viewHolder.mView.findViewById(R.id.vid_title);

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
                        progressBar4.setVisibility(View.GONE);

                        if(status.equals("NO"))
                        {
                            viewHolder.mView.setClickable(true);
                        }
                        else
                        {
                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(LectureActivity.this,LectureViewActivity.class);
                                    //intent.setAction(Intent.ACTION_VIEW);
                                    intent.putExtra("title",model.getVidTitle());
                                    intent.putExtra("url",model.getVidUrl());
                                    intent.putExtra("type",standard);
                                    intent.putExtra("sub",sub4.getText().toString());
                                    intent.putExtra("lecNumber",String.valueOf(position));
                                    //intent.setData(Uri.parse(model.getUrl()));
                                    //intent.putExtra("Postkey",PostKey);
                                    //intent.setData(Uri.parse(UpdatePdf.getUrl()));
                                    startActivity(intent);
                                }
                            });
                        }

                    }
                };
        sub4ListView.setAdapter(firebaseRecyclerAdapter);
    }
    private void DisplayAllSub5Videos() {
        Query sortPostInDecendingOrder = subRef5.orderByChild("count");

        FirebaseRecyclerAdapter<VideoAdapter, LectureActivity.Sub5VidViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<VideoAdapter, LectureActivity.Sub5VidViewHolder>
                        (
                                VideoAdapter.class,
                                R.layout.all_video_layout,
                                LectureActivity.Sub5VidViewHolder.class,
                                sortPostInDecendingOrder
                        )
                {
                    @Override
                    protected void populateViewHolder(LectureActivity.Sub5VidViewHolder viewHolder, final VideoAdapter model, final int position) {

                        View colorView = viewHolder.mView.findViewById(R.id.video_view);
                        TextView vidTitle = viewHolder.mView.findViewById(R.id.vid_title);

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
                        progressBar5.setVisibility(View.GONE);

                        if(status.equals("NO"))
                        {
                            viewHolder.mView.setClickable(true);
                        }
                        else
                        {
                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(LectureActivity.this,LectureViewActivity.class);
                                    //intent.setAction(Intent.ACTION_VIEW);
                                    intent.putExtra("title",model.getVidTitle());
                                    intent.putExtra("url",model.getVidUrl());
                                    intent.putExtra("type",standard);
                                    intent.putExtra("sub",sub5.getText().toString());
                                    intent.putExtra("lecNumber",String.valueOf(position));
                                    //intent.setData(Uri.parse(model.getUrl()));
                                    //intent.putExtra("Postkey",PostKey);
                                    //intent.setData(Uri.parse(UpdatePdf.getUrl()));
                                    startActivity(intent);
                                }
                            });
                        }

                    }
                };
        sub5ListView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class Sub1VidViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public Sub1VidViewHolder(@NonNull View itemView) {
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
    public static class Sub2VidViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public Sub2VidViewHolder(@NonNull View itemView) {
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
    public static class Sub3VidViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public Sub3VidViewHolder(@NonNull View itemView) {
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
    public static class Sub4VidViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public Sub4VidViewHolder(@NonNull View itemView) {
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
    public static class Sub5VidViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public Sub5VidViewHolder(@NonNull View itemView) {
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
