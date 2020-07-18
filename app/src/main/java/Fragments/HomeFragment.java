package Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.techtitudetribe.aadharshila.BookActivity;
import com.techtitudetribe.aadharshila.LectureActivity;
import com.techtitudetribe.aadharshila.R;
import com.techtitudetribe.aadharshila.ResultActivity;
import com.techtitudetribe.aadharshila.TestActivity;

public class HomeFragment extends Fragment {

    private FloatingActionButton floatingActionButton;
    private SliderLayout sliderLayout;
    private FirebaseAuth mAuth;
    private DatabaseReference pdfRef,stuRef;
    private RelativeLayout eBookLayout,eLectureLayout,eTestLayout,eResultLayout;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private String currentClass,currentUser;
    private ProgressBar bookProgress,lectureProgress;
    //private String imageUrl1,imageUrl2,imageUrl3,imageUrl4,imageUrl5;
    //private URL url1,url2,url3,url4,url5;
    //private URI uri1,uri2,uri3,uri4,uri5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.home_fragment,container,false);




        floatingActionButton=v.findViewById(R.id.fab_customer_care);

        sliderLayout = v.findViewById(R.id.imageSlider);
        bookProgress = v.findViewById(R.id.book_progress_bar);
        lectureProgress = v.findViewById(R.id.lecture_progress_bar);

        mAuth= FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();


        eBookLayout = v.findViewById(R.id.e_book_layout);

        eBookLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookProgress.setVisibility(View.VISIBLE);
                stuRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUser);
                stuRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        currentClass = dataSnapshot.child("Class").getValue().toString();
                        Intent i = new Intent(getActivity(), BookActivity.class);
                        i.putExtra("class",currentClass);
                        startActivity(i);
                        getActivity().finish();
                        bookProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        eLectureLayout=v.findViewById(R.id.lecture_layout);
        eLectureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lectureProgress.setVisibility(View.VISIBLE);
                stuRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUser);
                stuRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        currentClass = dataSnapshot.child("Class").getValue().toString();
                        Intent i = new Intent(getActivity(), LectureActivity.class);
                        i.putExtra("class",currentClass);
                        startActivity(i);
                        getActivity().finish();
                        lectureProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });;
            }
        });

        eTestLayout = v.findViewById(R.id.test_layout);
        eTestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TestActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        eResultLayout = v.findViewById(R.id.result_layout);
        eResultLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ResultActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        /*DocumentReference user = FirebaseFirestore.getInstance().collection("MainScreen").document("SliderImages");
        user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists())
                {
                    imageUrl1 = documentSnapshot.getString("image1");
                    imageUrl2 = documentSnapshot.getString("image2");
                    imageUrl3 = documentSnapshot.getString("image3");
                    imageUrl4 = documentSnapshot.getString("image4");
                    imageUrl5 = documentSnapshot.getString("image5");
                }
            }
        });*/

        sliderLayout.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(2); //set scroll delay in seconds :

        setSliderViews();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = openGmail(getActivity());
                startActivity(emailIntent);
            }
        });

        return v;
    }

    private void setSliderViews() {
        for (int i = 1; i <= 5; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(getActivity());

            switch (i) {
                case 1:
                    sliderView.setImageDrawable(R.drawable.is1);
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.is2);
                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.is3);
                    break;
                case 4:
                    sliderView.setImageDrawable(R.drawable.is4);
                    break;
                case 5:
                    sliderView.setImageDrawable(R.drawable.is5);
                    break;

            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderLayout.addSliderView(sliderView);

        }
    }


        public static Intent openGmail (Context context){
            try {
                context.getPackageManager()
                        .getPackageInfo("com.google.android.gm", 0);
                return new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:e.Aadharshila.techtitudetribe@gmail.com"));
            } catch (Exception ex) {
                return new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com/mail/u/2/#inbox?compose=new"));
            }
        }

    }