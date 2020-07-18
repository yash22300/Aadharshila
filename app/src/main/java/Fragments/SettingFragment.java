package Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.techtitudetribe.aadharshila.LoginActivity;
import com.techtitudetribe.aadharshila.R;

public class SettingFragment extends Fragment {

    private FirebaseAuth mAuth;
    private RelativeLayout logOut;
    private TextView settingStudentName;
    private ImageView settingProfile;
    private DatabaseReference userRef;
    String currentUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.setting_fragment,container,false);

        mAuth= FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserId);

        settingProfile = v.findViewById(R.id.setting_profile);
        settingStudentName = v.findViewById(R.id.setting_student_name);

        logOut = v.findViewById(R.id.log_out);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                sendUserToLoginActivity();
            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String name = dataSnapshot.child("Student Name").getValue().toString();
                    String profile = dataSnapshot.child("Student Profile").getValue().toString();

                    Picasso.with(getActivity()).load(profile).placeholder(R.drawable.ic_user_default).into(settingProfile);
                    settingStudentName.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }

    private void sendUserToLoginActivity() {
        Intent intent=new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
