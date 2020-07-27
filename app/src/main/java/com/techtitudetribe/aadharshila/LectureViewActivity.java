package com.techtitudetribe.aadharshila;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class LectureViewActivity extends AppCompatActivity {

    private TextView lectureTitle;
    private PlayerView playerView;
    private ImageView btFullScreen;
    private ProgressBar spinKitView;
    private DatabaseReference cmtRef,stuRef;
    private SimpleExoPlayer simpleExoPlayer;
    Boolean flag = false;
    private RelativeLayout cardView;
    private FirebaseAuth mAuth;
    private String standard,subject,lecture,currentUser;
    private EditText doubt;
    private ImageView postDoubt;
    private RecyclerView doubtList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_view);

        lectureTitle = (TextView) findViewById(R.id.lecture_title_name);
        playerView = (PlayerView) findViewById(R.id.lecture_video_view);
        spinKitView = (ProgressBar) findViewById(R.id.progress_spinkit_lecture);
        btFullScreen = playerView.findViewById(R.id.bt_fullscreen);
        cardView =  findViewById(R.id.lecture_view_card);
        doubt = (EditText) findViewById(R.id.inputDoubt);
        postDoubt = (ImageView) findViewById(R.id.post_doubt);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        stuRef = FirebaseDatabase.getInstance().getReference().child("Students");

        String title = getIntent().getExtras().getString("title");
        lectureTitle.setText(title);

        standard = getIntent().getExtras().getString("type");
        lecture = getIntent().getExtras().getString("lecNumber");

        final String url = getIntent().getExtras().getString("url");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Uri videoUrl = Uri.parse(url);

        LoadControl loadControl = new DefaultLoadControl();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(
                new AdaptiveTrackSelection.Factory(bandwidthMeter)
        );
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                LectureViewActivity.this,trackSelector,loadControl
        );
        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory(
                "exoplayer_video"
        );
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(videoUrl,
                factory,extractorsFactory,null,null);
        playerView.setPlayer(simpleExoPlayer);
        playerView.setKeepScreenOn(true);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if(playbackState == Player.STATE_BUFFERING){
                    spinKitView.setVisibility(View.VISIBLE);
                }else if(playbackState == Player.STATE_READY) {
                    spinKitView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

        doubtList = (RecyclerView) findViewById(R.id.doubt_lecture_view);
        doubtList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(this);
        linearLayoutManager4.setReverseLayout(true);
        linearLayoutManager4.setStackFromEnd(true);
        doubtList.setLayoutManager(linearLayoutManager4);

        btFullScreen.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onClick(View v) {
                if(flag){
                    final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                    int pixels = (int) (280 * scale + 0.5f);
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    FrameLayout.LayoutParams rel_btn = new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, pixels);
                    cardView.setLayoutParams(rel_btn);
                    flag = false;
                }else{
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT);
                    cardView.setLayoutParams(layoutParams);
                    flag= true;
                }
            }
        });

        switch (standard)
        {
            case "1" :  subject = getIntent().getExtras().getString("sub");
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Class 1").child(subject).child("Lec"+lecture);
                break;
            case "2" :  subject = getIntent().getExtras().getString("sub");
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Class 2").child(subject).child("Lec"+lecture);
                break;
            case "3" :  subject = getIntent().getExtras().getString("sub");
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Class 3").child(subject).child("Lec"+lecture);
                break;
            case "4" :  subject = getIntent().getExtras().getString("sub");
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Class 4").child(subject).child("Lec"+lecture);
                break;
            case "5" :  subject = getIntent().getExtras().getString("sub");
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Class 5").child(subject).child("Lec"+lecture);
                break;
            case "6" :  subject = getIntent().getExtras().getString("sub");
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Class 6").child(subject).child("Lec"+lecture);
                break;
            case "7" :  subject = getIntent().getExtras().getString("sub");
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Class 7").child(subject).child("Lec"+lecture);
                break;
            case "8" :  subject = getIntent().getExtras().getString("sub");
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Class 8").child(subject).child("Lec"+lecture);
                break;
            case "9" :  subject = getIntent().getExtras().getString("sub");
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Class 9").child(subject).child("Lec"+lecture);
                break;
            case "10" :  subject = getIntent().getExtras().getString("sub");
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Class 10").child(subject).child("Lec"+lecture);
                break;
            case "11" :  subject = getIntent().getExtras().getString("sub");
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Class 11").child(subject).child("Lec"+lecture);
                break;
            case "12" :  subject = getIntent().getExtras().getString("sub");
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Class 12").child(subject).child("Lec"+lecture);
                break;
            case "Course1" :
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Course1").child("Lec"+lecture);
                break;
            case "Course2" :
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Course2").child("Lec"+lecture);
                break;
            case "Course3" :
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Course3").child("Lec"+lecture);
                break;
            case "Course4" :
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Course4").child("Lec"+lecture);
                break;
            case "Course5" :
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Course5").child("Lec"+lecture);
                break;
            case "Course6" :
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Course6").child("Lec"+lecture);
                break;
            case "Course7" :
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Course7").child("Lec"+lecture);
                break;
            case "Course8" :
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Course8").child("Lec"+lecture);
                break;
            case "Course9" :
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Course9").child("Lec"+lecture);
                break;
            case "Course10" :
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Course10").child("Lec"+lecture);
                break;
            case "Course11" :
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Course11").child("Lec"+lecture);
                break;
            case "Course12" :
                cmtRef = FirebaseDatabase.getInstance().getReference().child("Doubts").child("Course12").child("Lec"+lecture);
                break;
        }

        postDoubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stuRef.child(currentUser).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            String stuName=dataSnapshot.child("Student Name").getValue().toString();

                            validateDoubt(stuName);

                            doubt.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        displayDoubts();
    }

    private void displayDoubts() {
        //Query sortDoubt = cmtRef.orderByChild("count");

        FirebaseRecyclerAdapter<DoubtAdapter,DoubtViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<DoubtAdapter, DoubtViewHolder>(
                        DoubtAdapter.class,
                        R.layout.doubt_layout,
                        DoubtViewHolder.class,
                        cmtRef
                ) {
                    @Override
                    protected void populateViewHolder(DoubtViewHolder doubtViewHolder, DoubtAdapter doubtAdapter, int i) {
                        ImageView doubtImage = doubtViewHolder.mView.findViewById(R.id.doubt_image);
                        TextView name = doubtViewHolder.mView.findViewById(R.id.doubt_username);

                        int colorType = i%6;
                        switch (colorType)
                        {
                            case 0 : name.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_red));
                                doubtImage.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.creative_red));
                                break;
                            case 1 : name.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_green));
                                doubtImage.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.creative_green));
                                break;
                            case 2 : name.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_yellow_dark));
                                doubtImage.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.creative_yellow_dark));
                                break;
                            case 3 : name.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_sky_blue));
                                doubtImage.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.creative_sky_blue));
                                break;
                            case 4 : name.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_orange));
                                doubtImage.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.creative_orange));
                                break;
                            case 5 : name.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_violet));
                                doubtImage.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.creative_violet));
                                break;
                        }

                        doubtViewHolder.setStuDate(doubtAdapter.getStuDate());
                        doubtViewHolder.setStuDoubt(doubtAdapter.getStuDoubt());
                        doubtViewHolder.setStuName(doubtAdapter.getStuName());
                    }
                };
        doubtList.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        simpleExoPlayer.setPlayWhenReady(false);
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.getPlaybackState();
    }

    private void validateDoubt(String stuName)
    {
        String doubtText = doubt.getText().toString();
        if(TextUtils.isEmpty(doubtText))
        {
            Toast.makeText(LectureViewActivity.this,"Plz enter your doubt...",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Calendar calFordDate = Calendar.getInstance();
            final String saveCurrentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calFordDate.getTime());

            Calendar calFordTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
            final String saveCurrentTime=currentTime.format(calFordTime.getTime());

            final String doubtRandomName = currentUser + saveCurrentDate + saveCurrentTime;

            HashMap doubtMap = new HashMap();
            doubtMap.put("stuName",stuName);
            doubtMap.put("stuDoubt",doubtText);
            doubtMap.put("stuDate",saveCurrentDate);

            cmtRef.child(doubtRandomName).updateChildren(doubtMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(LectureViewActivity.this,"Your doubt has been successfully sent...",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(LectureViewActivity.this,"Error Occured",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public static class DoubtViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public DoubtViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setStuDate(String stuDate) {
            TextView dateText = (TextView) mView.findViewById(R.id.doubt_date);
            dateText.setText(stuDate);
        }

        public void setStuDoubt(String stuDoubt) {
            TextView doubtText = (TextView) mView.findViewById(R.id.doubt_output);
            doubtText.setText(stuDoubt);
        }

        public void setStuName(String stuName) {
            TextView stuText = (TextView) mView.findViewById(R.id.doubt_username);
            stuText.setText(stuName);
        }
    }
}
