package Fragments;

import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.techtitudetribe.aadharshila.R;

public class ProfileFragment extends Fragment {

    private TextView profileStudentName,profileStudentClass,profileStudentGender,profileStudentContact,profileStudentDob;
    private ImageView studentProfilePic,basicInfoImage,classImage,profileContactImage;
    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;
    String currentUserId;
    private RelativeLayout classSetting,basicInfoContainer,expandViewClass,expandViewContact,profileContactSetting;
    private LinearLayout basicExpand;
    private Button subscribe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=  inflater.inflate(R.layout.profile_fragment,container,false);

        profileStudentName = v.findViewById(R.id.profile_student_name);
        profileStudentClass = v.findViewById(R.id.profile_student_class);
        profileStudentGender = v.findViewById(R.id.profile_student_gender);
        profileStudentContact = v.findViewById(R.id.profile_student_contact);
        profileStudentDob = v.findViewById(R.id.profile_student_dob);
        studentProfilePic = v.findViewById(R.id.profile_user_pic);

        subscribe = v.findViewById(R.id.subscribe_now_profile);
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Is is Demo app, no need to subscribe...\nYou can view all content...", Toast.LENGTH_SHORT).show();
            }
        });

        basicExpand = v.findViewById(R.id.profile_expandable_view_basic);
        basicInfoImage = v.findViewById(R.id.profile_basic_info_image);
        expandViewClass = v.findViewById(R.id.profile_expandable_view_class);
        classImage = v.findViewById(R.id.profile_class_image);
        classSetting = v.findViewById(R.id.profile_class_setting);
        basicInfoContainer = v.findViewById(R.id.profile_basic_info_container);
        profileContactSetting = v.findViewById(R.id.profile_contact_setting);
        expandViewContact = v.findViewById(R.id.profile_expandable_view_contact);
        profileContactImage = v.findViewById(R.id.profile_contact_image);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserId);

        profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    String name = dataSnapshot.child("Student Name").getValue().toString();
                    String standard = dataSnapshot.child("Class").getValue().toString();
                    String gender = dataSnapshot.child("Gender").getValue().toString();
                    String contact = dataSnapshot.child("Contact Number").getValue().toString();
                    String dob = dataSnapshot.child("DOB").getValue().toString();
                    String profile = dataSnapshot.child("Student Profile").getValue().toString();

                    Picasso.with(getActivity()).load(profile).placeholder(R.drawable.ic_user_default).into(studentProfilePic);

                    profileStudentName.setText(name);
                    profileStudentClass.setText("Class : "+standard);
                    profileStudentGender.setText("Gender : "+gender);
                    profileStudentContact.setText("+91 "+contact);
                    profileStudentDob.setText("DOB : "+dob);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        classSetting.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (expandViewClass.getVisibility()==View.GONE)
                {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90,24,24);
                    classImage.setScaleType(ImageView.ScaleType.MATRIX);
                    TransitionManager.beginDelayedTransition(classSetting, new AutoTransition());
                    expandViewClass.setVisibility(View.VISIBLE);
                    classImage.setImageMatrix(matrix);
                }
                else
                {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(360,24,24);
                    classImage.setScaleType(ImageView.ScaleType.MATRIX);
                    TransitionManager.beginDelayedTransition(classSetting, new AutoTransition());
                    expandViewClass.setVisibility(View.GONE);
                    classImage.setImageMatrix(matrix);
                }
            }
        });

        profileContactSetting.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (expandViewContact.getVisibility()==View.GONE)
                {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90,24,24);
                    profileContactImage.setScaleType(ImageView.ScaleType.MATRIX);
                    TransitionManager.beginDelayedTransition(profileContactSetting, new AutoTransition());
                    expandViewContact.setVisibility(View.VISIBLE);
                    profileContactImage.setImageMatrix(matrix);
                }
                else
                {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(360,24,24);
                    profileContactImage.setScaleType(ImageView.ScaleType.MATRIX);
                    TransitionManager.beginDelayedTransition(profileContactSetting, new AutoTransition());
                    expandViewContact.setVisibility(View.GONE);
                    profileContactImage.setImageMatrix(matrix);
                }
            }
        });

        basicInfoContainer.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (basicExpand.getVisibility()==View.GONE)
                {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90,24,24);
                    basicInfoImage.setScaleType(ImageView.ScaleType.MATRIX);
                    TransitionManager.beginDelayedTransition(basicInfoContainer, new AutoTransition());
                    basicExpand.setVisibility(View.VISIBLE);
                    basicInfoImage.setImageMatrix(matrix);
                }
                else
                {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(360,24,24);
                    basicInfoImage.setScaleType(ImageView.ScaleType.MATRIX);
                    TransitionManager.beginDelayedTransition(basicInfoContainer, new AutoTransition());
                    basicExpand.setVisibility(View.GONE);
                    basicInfoImage.setImageMatrix(matrix);
                }
            }
        });
        return v;
    }

}
