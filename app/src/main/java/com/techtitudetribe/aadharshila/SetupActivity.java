package com.techtitudetribe.aadharshila;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class SetupActivity extends AppCompatActivity {

    private EditText setupStudentName,setupContactNo,setupDob;
    private Button createAccount;
    private ImageView studentProfile;
    private static int GALLERY_PICK=1;
    private Spinner setupGenderSpinner,setupClassSpinner;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    private StorageReference UserProfileImageRef;
    String currentUserId,image,download,standard;
    private ProgressDialog LoadingBar;
    private TextView setupGender,setupStandard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        setupGender = (TextView) findViewById(R.id.setupGender);
        setupStudentName = (EditText) findViewById(R.id.student_Name);
        setupStandard = (TextView) findViewById(R.id.setUpClass);
        setupContactNo = (EditText) findViewById(R.id.setupContact);
        setupGenderSpinner = (Spinner) findViewById(R.id.setupGenderSpinner);
        setupClassSpinner = (Spinner) findViewById(R.id.setupClassSpinner);
        setupDob = (EditText) findViewById(R.id.setup_dob);
        createAccount = (Button) findViewById(R.id.create_new_account);
        studentProfile = (ImageView) findViewById(R.id.student_profile);
        LoadingBar = new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        UserRef= FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserId);
        UserProfileImageRef= FirebaseStorage.getInstance().getReference().child("Profile Image");

        Calendar calendar  = Calendar.getInstance();
        final int year  = calendar.get(Calendar.YEAR);
        final int month  = calendar.get(Calendar.MONTH);
        final int day  = calendar.get(Calendar.DAY_OF_MONTH);

        setupDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SetupActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = day+"-"+month+"-"+year;
                        setupDob.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        List<String> Gender = new ArrayList<>();
        Gender.add(0,"Choose Gender");
        Gender.add("Male");
        Gender.add("Female");
        Gender.add("Other");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this,R.layout.spinner_item,Gender);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        setupGenderSpinner.setAdapter(dataAdapter);
        setupGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if ((item.toString()).equals("Choose Gender"))
                {
                    setupGender.setText(null);
                }
                else
                {
                    setupGender.setText(item.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> Class = new ArrayList<>();
        Class.add(0,"Choose Class");
        Class.add("1");
        Class.add("2");
        Class.add("3");
        Class.add("4");
        Class.add("5");
        Class.add("6");
        Class.add("7");
        Class.add("8");
        Class.add("9");
        Class.add("10");
        Class.add("11");
        Class.add("12 or above");

        ArrayAdapter<String> dataAdapterClass;
        dataAdapterClass = new ArrayAdapter<>(this,R.layout.spinner_item,Class);
        dataAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        setupClassSpinner.setAdapter(dataAdapterClass);
        setupClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if ((item.toString()).equals("Choose Class"))
                {
                    setupStandard.setText(null);
                }
                else if((item.toString()).equals("12 or above"))
                {
                    setupStandard.setText(item.toString());
                    standard = "12";
                }
                else
                {
                    setupStandard.setText(item.toString());
                    standard = item.toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
            userMap.put("Test First","NO");
            userMap.put("Test Second","NO");
            userMap.put("Test Third","NO");
            userMap.put("Test Four","NO");
            userMap.put("Test Five","NO");

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