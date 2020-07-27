package com.techtitudetribe.aadharshila;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.techtitudetribe.aadharshila.R;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    //for G-sign in
    private static final int RC_SIGN_IN =1;
    private GoogleApiClient mGoogleSignInClient;

    private Button loginButton;
    private EditText userEmail, userPassword;
    private TextView needNewAccountLink,textForgetPassword;
    private ProgressDialog LoadingBar;
    private FirebaseAuth mAuth;
    private RelativeLayout googleSignInButton;

    private CallbackManager mCallbackManager;

    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());

        needNewAccountLink = (TextView) findViewById(R.id.register_account_link);
        userEmail = (EditText) findViewById(R.id.loginEmail);
        userPassword = (EditText) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        LoadingBar = new ProgressDialog(this);

        googleSignInButton = findViewById(R.id.google_login_layout);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        needNewAccountLink.setPaintFlags(needNewAccountLink.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        textForgetPassword=(TextView) findViewById(R.id.text_forget_password);
        textForgetPassword.setPaintFlags(textForgetPassword.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allowUserToLogin();
            }
        });

        needNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignup = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intentSignup);
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this,"Connection Failed...",Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }


    private void updateUi(FirebaseUser user) {
        if(user!=null)
        {

        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            LoadingBar.show();
            LoadingBar.setContentView(R.layout.progress_bar);
            LoadingBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LoadingBar.setCanceledOnTouchOutside(true);

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess())
            {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                Toast.makeText(this,"Plz wait",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Can't connect with Google", Toast.LENGTH_SHORT).show();
                LoadingBar.dismiss();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            sendUserToMainActivity();
                            LoadingBar.dismiss();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            String message = task.getException().toString();
                            sendUserToLoginActivity();
                            Toast.makeText(LoginActivity.this, "Not authenticated : " + message, Toast.LENGTH_SHORT).show();
                            LoadingBar.dismiss();
                        }
                    }
                });
    }

    private void sendUserToLoginActivity() {
        Intent mainIntent = new Intent(LoginActivity.this,LoginActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void allowUserToLogin() {
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(LoginActivity.this,"Plz write your email...",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Plz write your password...", Toast.LENGTH_SHORT).show();
        }else
            {
                LoadingBar.show();
                LoadingBar.setContentView(R.layout.progress_bar);
                LoadingBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                LoadingBar.setCanceledOnTouchOutside(true);

                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    sendUserToMainActivity();
                                    Toast.makeText(LoginActivity.this,"Login successfully...",Toast.LENGTH_SHORT).show();
                                    LoadingBar.dismiss();
                                }
                                else
                                {
                                    String message = task.getException().getMessage();
                                    Toast.makeText(LoginActivity.this,"Error Occurred..."+ message,Toast.LENGTH_SHORT).show();
                                    LoadingBar.dismiss();
                                }
                            }
                        });
            }
    }

    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null)
        {
            sendUserToMainActivity();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
