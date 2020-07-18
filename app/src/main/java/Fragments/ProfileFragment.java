package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private ImageView studentProfilePic;
    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;
    String currentUserId;

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
                    profileStudentClass.setText(standard);
                    profileStudentGender.setText(gender);
                    profileStudentContact.setText(contact);
                    profileStudentDob.setText(dob);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }

}
