package com.techtitudetribe.aadharshila;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.techtitudetribe.aadharshila.R;

import Fragments.ContactFragment;
import Fragments.HomeFragment;
import Fragments.NotifyFragment;
import Fragments.ProfileFragment;
import Fragments.SettingFragment;


public class MainActivity extends AppCompatActivity {

    private MeowBottomNavigation meo;
    private final static int ID_PROFILE=1;
    private final static int ID_NOTIFY=2;
    private final static int ID_HOME=3;
    private final static int ID_CONTACT=4;
    private final static int ID_SETTING=5;

    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    String currentUserId;
    
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meo=(MeowBottomNavigation) findViewById(R.id.bottom_nav);
        meo.add(new MeowBottomNavigation.Model(ID_PROFILE, R.drawable.icon_profile));
        meo.add(new MeowBottomNavigation.Model(ID_NOTIFY, R.drawable.icon_notify));
        meo.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.home_icon));
        meo.add(new MeowBottomNavigation.Model(ID_CONTACT, R.drawable.icon_contact));
        meo.add(new MeowBottomNavigation.Model(ID_SETTING, R.drawable.icon_setting));

        mAuth = FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        UserRef= FirebaseDatabase.getInstance().getReference().child("Students");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

        meo.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });
        meo.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment select_fragment = null;
                switch (item.getId())
                {
                    case ID_PROFILE : select_fragment = new ProfileFragment();
                                        break;
                    case ID_NOTIFY : select_fragment = new NotifyFragment();
                                        break;
                    case ID_HOME : select_fragment = new HomeFragment();
                                        break;
                    case ID_CONTACT : select_fragment = new ContactFragment();
                                        break;
                    case ID_SETTING : select_fragment = new SettingFragment();
                                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,select_fragment).commit();
            }
        });

        meo.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Fragment select_fragment = null;
                switch (item.getId())
                {
                    case ID_PROFILE : select_fragment = new ProfileFragment();
                        break;
                    case ID_NOTIFY : select_fragment = new NotifyFragment();
                        break;
                    case ID_HOME : select_fragment = new HomeFragment();
                        break;
                    case ID_CONTACT : select_fragment = new ContactFragment();
                        break;
                    case ID_SETTING : select_fragment = new SettingFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,select_fragment).commit();
            }
        });
        meo.show(ID_HOME,true);

        FirebaseMessaging.getInstance().subscribeToTopic("Students");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null)
        {
            sendUserToLoginActivity();
        }
        else
        {
            CheckUserExistence();
        }
    }

    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    private void CheckUserExistence()
    {
        final String current_user_id = mAuth.getCurrentUser().getUid();
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(!dataSnapshot.hasChild(current_user_id))
                {
                    SendUserToSetupActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SendUserToSetupActivity() {
        Intent setupIntent = new Intent(MainActivity.this,SetupActivity.class);
        setupIntent .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent );
        finish();
    }

    @Override
    public void onBackPressed() {
        meo.show(ID_HOME,true);
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Press back again to exit...", Toast.LENGTH_SHORT).show();
        }
        backPressedTime=System.currentTimeMillis();
    }
}
