package com.techtitudetribe.aadharshila;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.w3c.dom.Text;

import java.util.HashMap;

public class SetupActivity extends AppCompatActivity {

    private EditText setupStudentName,setupStandard,setupContactNo,setupDob,setupGender;
    private Button createAccount;
    private ImageView studentProfile;
    private static int GALLERY_PICK=1;

    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    private StorageReference UserProfileImageRef;
    String currentUserId,image,download;
    private ProgressDialog LoadingBar;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        setupStudentName = (EditText) findViewById(R.id.student_Name);
        setupStandard = (EditText) findViewById(R.id.setUpClass);
        setupContactNo = (EditText) findViewById(R.id.setupContact);
        setupGender = (EditText) findViewById(R.id.setupGender);
        setupDob = (EditText) findViewById(R.id.setup_dob);
        createAccount = (Button) findViewById(R.id.create_new_account);
        studentProfile = (ImageView) findViewById(R.id.student_profile);
        LoadingBar = new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        UserRef= FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserId);
        UserProfileImageRef= FirebaseStorage.getInstance().getReference().child("Profile Image");

        studentProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.startPickImageActivity(SetupActivity.this);
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidations();
            }
        });

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    if (dataSnapshot.hasChild("Student Profile"))
                    {
                       // image = dataSnapshot.child("Student Profile").getValue().toString();
                        Picasso.with(SetupActivity.this).load(image).placeholder(R.drawable.ic_user_default).into(studentProfile);
                    }
                    else
                    {
                        //Toast.makeText(this,"Plz upload your image first...",Toast.LENGTH_SHORT).show();
                        Toast.makeText(SetupActivity.this, "Plz upload your image first...", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkValidations() {
        String stu_name = setupStudentName.getText().toString();
        String standard = setupStandard.getText().toString();
        String contactNo = setupContactNo.getText().toString();
        String dob = setupDob.getText().toString();
        String gender = setupGender.getText().toString();

        if(TextUtils.isEmpty(download))
        {
            Toast.makeText(this, "Upload image first...", Toast.LENGTH_SHORT).show();
        }else
        if(TextUtils.isEmpty(stu_name))
        {
            Toast.makeText(this, "Write your name first...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(standard))
        {
            Toast.makeText(this, "enter your class...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(contactNo))
        {
            Toast.makeText(this, "Write your phone number...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(dob))
        {
            Toast.makeText(this, "write your date of birth...", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(gender))
        {
            Toast.makeText(this, "select your gender...", Toast.LENGTH_SHORT).show();
        }else
        {
            LoadingBar.show();
            LoadingBar.setContentView(R.layout.progress_bar);
            LoadingBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LoadingBar.setCanceledOnTouchOutside(true);

            HashMap userMap = new HashMap();
            userMap.put("Student Name",stu_name);
            userMap.put("Class",standard);
            userMap.put("Contact Number",contactNo);
            userMap.put("DOB",dob);
            userMap.put("Gender",gender);
            userMap.put("Subscription","NO");
            userMap.put("Test First","YES");
            userMap.put("Test Second","YES");
            userMap.put("Test Third","YES");
            userMap.put("Test Four","YES");
            userMap.put("Test Five","YES");

            UserRef.child("Student Profile").setValue(download);
                   /* .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(SetupActivity.this,"Image uploaded successfully...",Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();
                            }
                            else
                            {   String message = task.getException().getMessage();
                                Toast.makeText(SetupActivity.this,"Error Occurred 1...",Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();
                            }
                        }
                    });*/

            UserRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful())
                    {
                        sendUserToMainActivity();
                        Toast.makeText(SetupActivity.this, "Your account has been created successfully...", Toast.LENGTH_SHORT).show();
                        LoadingBar.dismiss();
                    }
                    else
                    {
                        Toast.makeText(SetupActivity.this, "Error Occurred : Try Again", Toast.LENGTH_SHORT).show();
                        LoadingBar.dismiss();
                    }
                }
            });
        }

    }

    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(SetupActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK && data!=null)
        {
            Uri ImageUri = CropImage.getPickImageResultUri(this,data);
            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
            /*if(CropImage.isReadExternalStoragePermissionsRequired(this,ImageUri))
            {
                Uri uri = ImageUri;
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }
            else
            {
                startCrop(ImageUri);
            }*/
        }

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK)
            {
                LoadingBar.show();
                LoadingBar.setContentView(R.layout.progress_bar);
                LoadingBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                LoadingBar.setCanceledOnTouchOutside(true);

                final Uri resultUri = result.getUri();
                final StorageReference filePath = UserProfileImageRef.child(currentUserId +"image/jpg");

                Task uploadTask = filePath.putFile(resultUri);
                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        return filePath.getDownloadUrl();
                    }
                })
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task)
                            {
                                if(task.isSuccessful())
                                {
                                    Uri downloadUri = (Uri) task.getResult();
                                    download=downloadUri.toString();
                                    Picasso.with(SetupActivity.this).load(downloadUri).placeholder(R.drawable.ic_user_default).into(studentProfile);
                                    LoadingBar.dismiss();

                                }
                            }
                        });
            }
            else
            {

                Toast.makeText(SetupActivity.this,"Error Occurred...",Toast.LENGTH_SHORT).show();
                LoadingBar.dismiss();
            }
        }


    }
}