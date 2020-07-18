package com.techtitudetribe.aadharshila;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private EditText userEmail,userPassword,userConfirmPassword;
    //private RelativeLayout googleSignupLayout, facebookSignupLayout;
    private Button signUpButton;
    private ProgressDialog LoadingBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userEmail = (EditText) findViewById(R.id.signupEmail);
        userPassword = (EditText) findViewById(R.id.signupPassword);
        userConfirmPassword = (EditText) findViewById(R.id.signupConfirmPassword);
        //googleSignupLayout = (RelativeLayout) findViewById(R.id.google_signup_layout);
        //facebookSignupLayout = (RelativeLayout) findViewById(R.id.facebook_signup_layout);
        signUpButton = (Button) findViewById(R.id.signupButton);
        LoadingBar = new ProgressDialog(this);


        mAuth=FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            sendUsertoMainActivity();
        }
    }

    private void sendUsertoMainActivity() {
        Intent intent = new Intent(SignupActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void createNewAccount() {
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        String confirmPassword = userConfirmPassword.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please write your email...",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password))
            {
                Toast.makeText(this,"Please write your password...",Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(confirmPassword))
                {
                    Toast.makeText(this,"Please cofirm your password...",Toast.LENGTH_SHORT).show();
                }else if(!password.equals(confirmPassword))
                    {
                        Toast.makeText(this,"Password do not match...",Toast.LENGTH_SHORT).show();
                    } else
                        {
                            //LayoutInflater inflater = getLayoutInflater();
                            //View layout=inflater.inflate(R.layout.progress_bar, (ViewGroup) findViewById(R.id.loading_bar_root));
                            //TextView textView = layout.findViewById(R.id.loading_bar_text);

                            LoadingBar.show();
                            LoadingBar.setContentView(R.layout.progress_bar);
                            //textView.setText("Authenticating...");
                            LoadingBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            LoadingBar.setCanceledOnTouchOutside(true);




                            mAuth.createUserWithEmailAndPassword(email,password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful())
                                            {
                                                sendUserToSetupActivity();
                                                Toast.makeText(SignupActivity.this, "You are authenticated successfully...", Toast.LENGTH_SHORT).show();
                                                LoadingBar.dismiss();
                                            }
                                            else
                                            {
                                                String message = task.getException().getMessage();
                                                Toast.makeText(SignupActivity.this, "Error Occured :" + message, Toast.LENGTH_SHORT).show();
                                                LoadingBar.dismiss();
                                            }

                                        }
                                    });
                        }
    }

    private void sendUserToSetupActivity() {
        Intent setupIntent = new Intent(SignupActivity.this,SetupActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }
}
