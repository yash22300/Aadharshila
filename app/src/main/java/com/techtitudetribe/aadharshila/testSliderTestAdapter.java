package com.techtitudetribe.aadharshila;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class testSliderTestAdapter extends RecyclerView.Adapter<testSliderTestAdapter.SliderViewHolder> {

    private List<testSliderItem> sliderItems;
    private ViewPager2 viewPager2;
    private Context mContext;
    private FirebaseAuth mAuth;
    private DatabaseReference stuRef;
    private String currentUserId,currentClass;
    private String standard;

    testSliderTestAdapter(List<testSliderItem> sliderItems, ViewPager2 viewPager2, Context context, String standard) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
        this.mContext=context;
        this.standard=standard;
    }


    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.test_viewpager_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder,int position) {

        holder.setImage(sliderItems.get(position));
        //holder.setSubject(model.getSubject());
        //holder.setSubject(sliderItems.get(position));
        //holder.setDescription(sliderItems.get(position));

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        View topView = holder.mView.findViewById(R.id.test_view_top);
        RelativeLayout bottomView = holder.mView.findViewById(R.id.test_view_bottom);
        RelativeLayout backgroundView = holder.mView.findViewById(R.id.test_view_background);
        RelativeLayout imageBackgroundView = holder.mView.findViewById(R.id.test_view_image_background);
        TextView subject = holder.mView.findViewById(R.id.test_view_subject);
        TextView description = holder.mView.findViewById(R.id.test_view_description);
        final ProgressBar progress = holder.mView.findViewById(R.id.start_test_progress_bar);
        final TextView startTest = holder.mView.findViewById(R.id.start_test);

        subject.setTextColor(ContextCompat.getColor(mContext,R.color.white));
        description.setTextColor(ContextCompat.getColor(mContext,R.color.black));

        int colortype=position%5;
        switch (colortype)
        {
            case 0 : topView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_red));
                bottomView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_red));
                backgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_red_light));
                switch (standard)
                {
                    case "1" : subject.setText("Hindi");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "2" : subject.setText("Hindi");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "3" : subject.setText("Hindi");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "4" : subject.setText("Hindi");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "5" : subject.setText("Hindi");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "6" : subject.setText("Hindi");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "7" : subject.setText("Hindi");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "8" : subject.setText("Hindi");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "9" : subject.setText("Hindi");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "10" : subject.setText("Hindi");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "11" : subject.setText("Chemistry");
                        description.setText("Question based on Numericals");
                        break;
                    case "12" : subject.setText("Chemistry");
                        description.setText("Question based on Numericals");
                        break;
                }
                /*bottomView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            startTest.setVisibility(View.GONE);
                            progress.setVisibility(View.VISIBLE);
                            stuRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserId);
                            stuRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists())
                                    {
                                        currentClass = dataSnapshot.child("Class").getValue().toString();
                                        Intent intent = new Intent(mContext,QuestionsActivity.class);
                                        intent.putExtra("type","a");
                                        intent.putExtra("subject","Chemistry");
                                        intent.putExtra("class",currentClass);
                                        v.getContext().startActivity(intent);
                                        progress.setVisibility(View.GONE);
                                        startTest.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    });*/
                imageBackgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_red));
                break;
            case 1 : topView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_green));
                bottomView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_green));
                backgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_green_light));
                subject.setText("Mathematics");
                description.setText("Numerical questions");
                /*bottomView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        startTest.setVisibility(View.GONE);
                        progress.setVisibility(View.VISIBLE);
                        stuRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserId);
                        stuRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists())
                                {
                                    currentClass = dataSnapshot.child("Class").getValue().toString();
                                    Intent intent = new Intent(mContext,QuestionsActivity.class);
                                    intent.putExtra("type","b");
                                    intent.putExtra("subject","Mathematics");
                                    intent.putExtra("class",currentClass);
                                    v.getContext().startActivity(intent);
                                    progress.setVisibility(View.GONE);
                                    startTest.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });*/
                imageBackgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_green));
                break;
            case 2 : topView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_yellow_dark));
                bottomView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_yellow_dark));
                backgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_yellow_light));
                switch (standard)
                {
                    case "1" : subject.setText("GK");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "2" : subject.setText("GK");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "3" : subject.setText("GK");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "4" : subject.setText("Science");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "5" : subject.setText("Science");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "6" : subject.setText("Science");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "7" : subject.setText("Science");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "8" : subject.setText("Science");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "9" : subject.setText("Science");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "10" : subject.setText("Science");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "11" : subject.setText("Biology");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "12" : subject.setText("Biology");
                        description.setText("Question based on Theoretical Concept");
                        break;
                }
                /*bottomView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        startTest.setVisibility(View.GONE);
                        progress.setVisibility(View.VISIBLE);
                        stuRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserId);
                        stuRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists())
                                {
                                    currentClass = dataSnapshot.child("Class").getValue().toString();
                                    Intent intent = new Intent(mContext,QuestionsActivity.class);
                                    intent.putExtra("type","c");
                                    intent.putExtra("subject","Biology");
                                    intent.putExtra("class",currentClass);
                                    v.getContext().startActivity(intent);
                                    progress.setVisibility(View.GONE);
                                    startTest.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });*/
                imageBackgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_yellow_dark));
                break;
            case 3 : topView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_sky_blue));
                bottomView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_sky_blue));
                backgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_sky_blue_light));
                switch (standard)
                {
                    case "1" : subject.setText("Moral Values");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "2" : subject.setText("Moral Values");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "3" : subject.setText("Moral Values");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "4" : subject.setText("Moral Values");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "5" : subject.setText("Moral Values");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "6" : subject.setText("GK");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "7" : subject.setText("GK");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "8" : subject.setText("GK");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "9" : subject.setText("GK");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "10" : subject.setText("GK");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "11" : subject.setText("Physics");
                        description.setText("Question based on Numerical Concept");
                        break;
                    case "12" : subject.setText("Physics");
                        description.setText("Question based on Numerical Concept");
                        break;
                }
                /*bottomView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        startTest.setVisibility(View.GONE);
                        progress.setVisibility(View.VISIBLE);
                        stuRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserId);
                        stuRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists())
                                {
                                    currentClass = dataSnapshot.child("Class").getValue().toString();
                                    Intent intent = new Intent(mContext,QuestionsActivity.class);
                                    intent.putExtra("type","d");
                                    intent.putExtra("subject","Physics");
                                    intent.putExtra("class",currentClass);
                                    v.getContext().startActivity(intent);
                                    progress.setVisibility(View.GONE);
                                    startTest.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });*/
                imageBackgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_sky_blue));
                break;
            case 4 : topView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_orange));
                bottomView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_orange));
                backgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_orange_light));
                switch (standard)
                {
                    case "1" : subject.setText("English");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "2" : subject.setText("English");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "3" : subject.setText("English");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "4" : subject.setText("English");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "5" : subject.setText("English");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "6" : subject.setText("English");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "7" : subject.setText("English");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "8" : subject.setText("English");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "9" : subject.setText("English");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "10" : subject.setText("English");
                        description.setText("Question based on Theoretical Concept");
                        break;
                    case "11" : subject.setText("Accounts");
                        description.setText("Question based on Numerical Concept");
                        break;
                    case "12" : subject.setText("Accounts");
                        description.setText("Question based on Numerical Concept");
                        break;
                }
                /*bottomView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        startTest.setVisibility(View.GONE);
                        progress.setVisibility(View.VISIBLE);
                        stuRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserId);
                        stuRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists())
                                {
                                    currentClass = dataSnapshot.child("Class").getValue().toString();
                                    Intent intent = new Intent(mContext,QuestionsActivity.class);
                                    intent.putExtra("type","e");
                                    intent.putExtra("subject","English");
                                    intent.putExtra("class",currentClass);
                                    v.getContext().startActivity(intent);
                                    progress.setVisibility(View.GONE);
                                    startTest.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });*/
                imageBackgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_orange));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {

        View mView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }
        void setImage(testSliderItem testSliderItem)
        {
            ImageView imageView = mView.findViewById(R.id.image_slider_test);
            imageView.setImageResource(testSliderItem.getImage());
        }
        /*public void setSubject(testSliderItem subject)
        {
            TextView sub = mView.findViewById(R.id.test_view_subject);
            sub.setText( subject.toString());
        }
        public void setDescription(testSliderItem description)
        {
            TextView des = mView.findViewById(R.id.test_view_description);
            des.setText(description.toString());
        }*/
    }



}
