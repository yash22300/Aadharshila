package Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

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
import com.techtitudetribe.aadharshila.LoginActivity;
import com.techtitudetribe.aadharshila.R;
import com.techtitudetribe.aadharshila.SetupActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class SettingFragment extends Fragment {

    private FirebaseAuth mAuth;
    private RelativeLayout logOut,basicInfoContainer,updateClassLayout1;
    private TextView settingStudentName,userClass;
    private ImageView settingProfile;
    private static int GALLERY_PICK=1;
    private DatabaseReference userRef;
    private String currentUserId,standard,date,download,image;
    private ProgressDialog LoadingBar;
    private StorageReference UserProfileImageRef;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.setting_fragment,container,false);

        mAuth= FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserId);
        UserProfileImageRef= FirebaseStorage.getInstance().getReference().child("Profile Image");

        settingProfile = v.findViewById(R.id.setting_profile);
        settingStudentName = v.findViewById(R.id.setting_student_name);
        basicInfoContainer = v.findViewById(R.id.basic_info_container);
        updateClassLayout1 = v.findViewById(R.id.update_class_layout1);
        LoadingBar = new ProgressDialog(getActivity());

        logOut = v.findViewById(R.id.log_out);

        /*settingProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.startPickImageActivity(getActivity());
            }
        });*/

        basicInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AlertDialogTheme);
                View view = LayoutInflater.from(getActivity()).inflate(
                        R.layout.basic_info_update,
                        (RelativeLayout)v.findViewById(R.id.dialog_profile_container)
                );
                builder.setView(view);
                TextView cancel = (TextView) view.findViewById(R.id.cancel_alert_profile);
                final TextView userGender = (TextView) view.findViewById(R.id.setting_user_gender);
                Spinner spinner= view.findViewById(R.id.settingGenderSpinner);
                TextView confirm = (TextView) view.findViewById(R.id.update_profile);
                final EditText userName = view.findViewById(R.id.setting_user_name);
                final EditText userMob = view.findViewById(R.id.setting_user_mobile);
                final TextView userBday = view.findViewById(R.id.setting_user_bday);
                final Context context = getActivity();

                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            String name = dataSnapshot.child("Student Name").getValue().toString();
                            String gender = dataSnapshot.child("Gender").getValue().toString();
                            String contact = dataSnapshot.child("Contact Number").getValue().toString();
                            String dob = dataSnapshot.child("DOB").getValue().toString();

                            userName.setText(name);
                            //userClass.setText("Class : "+standard);
                            userGender.setText(gender);
                            userMob.setText(contact);
                            userBday.setText(dob);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




                userBday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar  = Calendar.getInstance();
                        final int year  = calendar.get(Calendar.YEAR);
                        final int month  = calendar.get(Calendar.MONTH);
                        final int day  = calendar.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                month = month+1;
                                date = day+"-"+month+"-"+year;
                                userBday.setText(date);
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
                dataAdapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_item,Gender);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(dataAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object item = parent.getItemAtPosition(position);
                        if ((item.toString()).equals("Choose Gender"))
                        {
                            userGender.setText(null);
                        }
                        else
                        {
                            userGender.setText(item.toString());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                final AlertDialog alertDialog = builder.create();
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String username = userName.getText().toString();
                        String gender = userGender.getText().toString();
                        String mob = userMob.getText().toString();
                        String date = userBday.getText().toString();

                        if (TextUtils.isEmpty(username))
                        {
                            Toast.makeText(getActivity(), "Plz write your name...", Toast.LENGTH_SHORT).show();
                        }else if (TextUtils.isEmpty(gender))
                        {
                            Toast.makeText(getActivity(), "Plz select your gender...", Toast.LENGTH_SHORT).show();
                        }else if (TextUtils.isEmpty(mob))
                        {
                            Toast.makeText(getActivity(), "Plz write your contact...", Toast.LENGTH_SHORT).show();
                        }else if (TextUtils.isEmpty(username))
                        {
                            Toast.makeText(getActivity(), "Plz select your DOB...", Toast.LENGTH_SHORT).show();
                        }else {
                            HashMap classMap = new HashMap();
                            classMap.put("Gender",gender);
                            classMap.put("Student Name",username);
                            classMap.put("Contact Number",mob);
                            classMap.put("DOB",date);

                            userRef.updateChildren(classMap).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful())
                                    {
                                        alertDialog.dismiss();
                                        Toast.makeText(getActivity(), "Profile updated Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                alertDialog.show();
            }
        });

        updateClassLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AlertDialogTheme);
                View view = LayoutInflater.from(getActivity()).inflate(
                        R.layout.update_class_layout,
                        (RelativeLayout)v.findViewById(R.id.dialog_class_container)
                );
                builder.setView(view);
                TextView cancel = (TextView) view.findViewById(R.id.cancel_alert_class);
                userClass = view.findViewById(R.id.setting_user_class);
                Spinner spinner= view.findViewById(R.id.settingClassSpinner);
                TextView confirm = (TextView) view.findViewById(R.id.update_class);

                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            String Class = dataSnapshot.child("Class").getValue().toString();
                            userClass.setText("Class : "+Class);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

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
                dataAdapterClass = new ArrayAdapter<>(getActivity(),R.layout.spinner_item,Class);
                dataAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(dataAdapterClass);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object item = parent.getItemAtPosition(position);
                        if ((item.toString()).equals("Choose Class"))
                        {
                            userClass.setText(null);
                        }
                        else if((item.toString()).equals("12 or above"))
                        {
                            userClass.setText(item.toString());
                            standard = "12";
                        }
                        else
                        {
                            userClass.setText(item.toString());
                            standard = item.toString();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                final AlertDialog alertDialog = builder.create();
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(TextUtils.isEmpty(standard))
                        {
                            Toast.makeText(getActivity(),"Plz select your class...",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            HashMap classMap = new HashMap();
                            classMap.put("Class",standard);

                            userRef.updateChildren(classMap).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful())
                                    {
                                        alertDialog.dismiss();
                                        Intent i = getActivity().getBaseContext().getPackageManager().
                                                getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        getActivity().finish();
                                    }
                                }
                            });
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                alertDialog.show();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AlertDialogTheme);
                View view = LayoutInflater.from(getActivity()).inflate(
                        R.layout.alert_box_layout,
                        (RelativeLayout)v.findViewById(R.id.dialog_alert_container)
                );
                builder.setView(view);
                TextView cancel = (TextView) view.findViewById(R.id.cancel_alert);
                TextView confirm = (TextView) view.findViewById(R.id.confirm_alert);

                final AlertDialog alertDialog = builder.create();
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        mAuth.signOut();
                        sendUserToLoginActivity();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                alertDialog.show();
            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.hasChild("Student Profile"))
                    {
                        image = dataSnapshot.child("Student Profile").getValue().toString();
                        Picasso.with(getActivity()).load(image).placeholder(R.drawable.ic_user_default).into(settingProfile);
                    }
                    else
                    {
                        //Toast.makeText(this,"Plz upload your image first...",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "Plz upload your image first...", Toast.LENGTH_SHORT).show();
                    }
                    String name = dataSnapshot.child("Student Name").getValue().toString();

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data!=null) {
            Uri ImageUri = CropImage.getPickImageResultUri(getActivity(), data);
            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(getContext(),SettingFragment.this);
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
                                    userRef.child("Student Profile").setValue(download);
                                    Picasso.with(getActivity()).load(downloadUri).placeholder(R.drawable.ic_user_default).into(settingProfile);
                                    LoadingBar.dismiss();

                                }
                            }
                        });
            }
            else
            {

                Toast.makeText(getActivity(),"Error Occurred...",Toast.LENGTH_SHORT).show();
                LoadingBar.dismiss();
            }
        }
    }
}
