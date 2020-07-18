package com.techtitudetribe.aadharshila;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class QuestionsActivity extends AppCompatActivity {

    private TextView option1,option2,option3,option4,question,answer,nextText,previousText,submitText,time,quesNumber,subjectName;
    private CardView nextLayout,previousLayout,submitLayout;
    private RelativeLayout quesBackground;
    private String type,standard;
    private int total=1;
    private int correct=0,wrong=0;
    private DatabaseReference quesRef,studentRef,resultRef;
    private FirebaseAuth mAuth;
    private String currentUser,saveCurrentDate,saveCurrentTime,resultRandomName;
    private String testFirst,testSecond,testThird,testFourth,testFifth;
    private ImageView questionSpeaker;
    private TextToSpeech mTTS;
    private long count1 = 0,count2=0,count3=0,count4=0,count5=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        option1 = findViewById(R.id.question_option1);
        option2 = findViewById(R.id.question_option2);
        option3 = findViewById(R.id.question_option3);
        option4 = findViewById(R.id.question_option4);
        question = findViewById(R.id.subject_question);
        nextText = findViewById(R.id.next_text_question);
        previousText = findViewById(R.id.previous_text_question);
        submitText = findViewById(R.id.submit_text_question);
        time = findViewById(R.id.subject_test_time);
        nextLayout = findViewById(R.id.next_layout);
        previousLayout = findViewById(R.id.previous_layout);
        submitLayout = findViewById(R.id.submit_layout);
        quesBackground = findViewById(R.id.ques_subject_background);
        quesNumber = findViewById(R.id.question_number_text);
        subjectName = findViewById(R.id.ques_subject_title);
        questionSpeaker = findViewById(R.id.question_speaker);

        mAuth=FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        studentRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUser);
        resultRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUser).child("Results");
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                testFirst = dataSnapshot.child("Test First").getValue().toString();
                testSecond = dataSnapshot.child("Test Second").getValue().toString();
                testThird = dataSnapshot.child("Test Third").getValue().toString();
                testFourth = dataSnapshot.child("Test Fourth").getValue().toString();
                testFifth = dataSnapshot.child("Test Five").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        type = getIntent().getExtras().getString("type");
        standard = getIntent().getExtras().getString("class");
        switch (type)
        {
            case "a" :  nextText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_red));
                        quesNumber.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_red));
                        previousText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_red));
                        submitText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_red));
                        quesBackground.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_red));
                        subjectName.setText(getIntent().getExtras().getString("subject"));
                        updateQuestions1();
                        reverseTimer(600,time);
                        break;
            case "b" :  nextText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_green));
                        quesNumber.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_green));
                        previousText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_green));
                        submitText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_green));
                        quesBackground.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_green));
                        subjectName.setText(getIntent().getExtras().getString("subject"));
                        updateQuestions2();
                        reverseTimer(600,time);
                        break;
            case "c" :  nextText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_yellow));
                        quesNumber.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_yellow));
                        previousText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_yellow));
                        submitText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_yellow));
                        quesBackground.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_yellow));
                        subjectName.setText(getIntent().getExtras().getString("subject"));
                        updateQuestions3();
                        reverseTimer(600,time);
                        break;
            case "d" :  nextText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_sky_blue));
                        quesNumber.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_sky_blue));
                        previousText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_sky_blue));
                        submitText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_sky_blue));
                        quesBackground.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_sky_blue));
                        subjectName.setText(getIntent().getExtras().getString("subject"));
                        updateQuestions4();
                        reverseTimer(600,time);
                        break;
            case "e" :  nextText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_orange));
                        quesNumber.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_orange));
                        previousText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_orange));
                        submitText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_orange));
                        quesBackground.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_orange));
                        subjectName.setText(getIntent().getExtras().getString("subject"));
                        updateQuestions5();
                        reverseTimer(600,time);
                        break;
        }

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS)
                {
                    int result = mTTS.setLanguage(Locale.ENGLISH);
                    if(result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                        Log.e("TTS","Language not supported");
                    }
                    else
                    {
                        questionSpeaker.setEnabled(true);
                    }
                }
                else
                {
                    Log.e("TTS","Initialization failed");
                }
            }
        });

        questionSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTTS.isSpeaking())
                {
                    mTTS.stop();
                }
                else
                {
                    speak();
                }
            }
        });

    }

    private void speak() {
        String text = question.getText().toString();
        mTTS.setSpeechRate(1.0f);
        mTTS.setPitch(0.8f);
        mTTS.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }

    private void updateQuestions1() {
        if(total>5)
        {
            //open result
        }
        else
        {
            quesRef = FirebaseDatabase.getInstance().getReference().child("Quiz").child(standard).child("Chemistry").child(String.valueOf(total));
            quesRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final QuestionsAdapter questionsAdapter = dataSnapshot.getValue(QuestionsAdapter.class);
                            question.setText(questionsAdapter.getQuestion());
                            option1.setText(questionsAdapter.getOption1());
                            option2.setText(questionsAdapter.getOption2());
                            option3.setText(questionsAdapter.getOption3());
                            option4.setText(questionsAdapter.getOption4());

                            option1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    colorChecker1(type);

                                    if(option1.getText().toString().equals(questionsAdapter.getAnswer()))
                                    {
                                        correct++;
                                    }
                                }
                            });
                            option2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    colorChecker2(type);

                                    if(option2.getText().toString().equals(questionsAdapter.getAnswer()))
                                    {
                                        correct++;
                                    }
                                }
                            });
                            option3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    colorChecker3(type);

                                    if(option3.getText().toString().equals(questionsAdapter.getAnswer()))
                                    {
                                        correct++;
                                    }
                                }
                            });
                            option4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    colorChecker4(type);

                                    if(option4.getText().toString().equals(questionsAdapter.getAnswer()))
                                    {
                                        correct++;
                                    }
                                }
                            });

                            nextLayout.setVisibility(View.VISIBLE);

                            if(testFirst.equals("YES"))
                            {
                                nextLayout.setClickable(true);
                                nextLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        total++;
                                        updateQuestions1();
                                        option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                        option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                        option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                        option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                        option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                        option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                        option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                        option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    }
                                });

                                if(total==1)
                                {
                                    previousLayout.setVisibility(View.GONE);
                                    nextLayout.setVisibility(View.VISIBLE);
                                    nextLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            total++;
                                            updateQuestions1();
                                            option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                            option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                            option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                            option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                            option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                            option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                            option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                            option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                        }
                                    });
                                    submitLayout.setVisibility(View.GONE);
                                }
                                else if(total>1 && total<5)
                                {
                                    previousLayout.setVisibility(View.VISIBLE);
                                    previousLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            total--;
                                            updateQuestions1();
                                            option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                            option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                            option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                            option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                            option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                            option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                            option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                            option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                        }
                                    });
                                    nextLayout.setVisibility(View.VISIBLE);
                                    nextLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            total++;
                                            updateQuestions1();
                                            option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                            option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                            option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                            option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                            option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                            option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                            option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                            option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                        }
                                    });
                                    submitLayout.setVisibility(View.GONE);
                                }
                                else if (total==5)
                                {
                                    previousLayout.setVisibility(View.VISIBLE);
                                    previousLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            total--;
                                            updateQuestions1();
                                            option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                            option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                            option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                            option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                            option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                            option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                            option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                            option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                        }
                                    });
                                    nextLayout.setVisibility(View.GONE);
                                    submitLayout.setVisibility(View.VISIBLE);
                                    submitLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            HashMap userMap = new HashMap();
                                            userMap.put("Test First","NO");


                                            studentRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                                                @Override
                                                public void onComplete(@NonNull Task task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        savingResultInformation();
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(QuestionsActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                            else
                            {
                                nextLayout.setClickable(false);
                            }
                        }

                private void savingResultInformation() {
                    Calendar calFordDate = Calendar.getInstance();
                    saveCurrentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calFordDate.getTime());

                    Calendar calFordTime = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                    saveCurrentTime=currentTime.format(calFordTime.getTime());

                    resultRandomName = saveCurrentDate + saveCurrentTime;


                    resultRef.child("Chemistry").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                count1 = dataSnapshot.getChildrenCount();
                            }
                            else
                            {
                                count1 = 0;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    HashMap resultMap = new HashMap();
                    resultMap.put("total",total);
                    resultMap.put("date",saveCurrentDate);
                    resultMap.put("correct",correct);
                    resultMap.put("wrong",wrong);
                    resultMap.put("count", count1);

                    resultRef.child("Chemistry").child("Result"+resultRandomName).updateChildren(resultMap).addOnCompleteListener(
                            new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {

                                    if (task.isSuccessful())
                                    {
                                        Intent newIntent  = new Intent(QuestionsActivity.this,MainActivity.class);
                                        startActivity(newIntent);
                                    }
                                    else
                                    {
                                        Toast.makeText(QuestionsActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                    );
                }

                @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
            quesNumber.setText(String.valueOf(total));
        }
    }
    private void updateQuestions2() {
        if(total>5)
        {
            //open result
        }
        else
        {
            quesRef = FirebaseDatabase.getInstance().getReference().child("Quiz").child(standard).child("Maths").child(String.valueOf(total));
            quesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final QuestionsAdapter questionsAdapter = dataSnapshot.getValue(QuestionsAdapter.class);
                    question.setText(questionsAdapter.getQuestion());
                    option1.setText(questionsAdapter.getOption1());
                    option2.setText(questionsAdapter.getOption2());
                    option3.setText(questionsAdapter.getOption3());
                    option4.setText(questionsAdapter.getOption4());

                    option1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            colorChecker1(type);

                            if(option1.getText().toString().equals(questionsAdapter.getAnswer()))
                            {
                                correct++;
                            }
                        }
                    });
                    option2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            colorChecker2(type);

                            if(option2.getText().toString().equals(questionsAdapter.getAnswer()))
                            {
                                correct++;
                            }
                        }
                    });
                    option3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            colorChecker3(type);

                            if(option3.getText().toString().equals(questionsAdapter.getAnswer()))
                            {
                                correct++;
                            }
                        }
                    });
                    option4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            colorChecker4(type);

                            if(option4.getText().toString().equals(questionsAdapter.getAnswer()))
                            {
                                correct++;
                            }
                        }
                    });

                    nextLayout.setVisibility(View.VISIBLE);

                    if(testSecond.equals("YES"))
                    {
                        nextLayout.setClickable(true);
                        nextLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                total++;
                                updateQuestions2();
                                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                            }
                        });

                        if(total==1)
                        {
                            previousLayout.setVisibility(View.GONE);
                            nextLayout.setVisibility(View.VISIBLE);
                            nextLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    total++;
                                    updateQuestions2();
                                    option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                }
                            });
                            submitLayout.setVisibility(View.GONE);
                        }
                        else if(total>1 && total<5)
                        {
                            previousLayout.setVisibility(View.VISIBLE);
                            previousLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    total--;
                                    updateQuestions2();
                                    option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                }
                            });
                            nextLayout.setVisibility(View.VISIBLE);
                            nextLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    total++;
                                    updateQuestions2();
                                    option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                }
                            });
                            submitLayout.setVisibility(View.GONE);
                        }
                        else if (total==5)
                        {
                            previousLayout.setVisibility(View.VISIBLE);
                            previousLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    total--;
                                    updateQuestions2();
                                    option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                }
                            });
                            nextLayout.setVisibility(View.GONE);
                            submitLayout.setVisibility(View.VISIBLE);
                            submitLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    HashMap userMap = new HashMap();
                                    userMap.put("Test Second","NO");


                                    studentRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            if(task.isSuccessful())
                                            {
                                                savingResultInformation();

                                            }
                                            else
                                            {
                                                Toast.makeText(QuestionsActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });

                        }
                    }
                    else
                    {
                        nextLayout.setClickable(false);
                    }
                }

                private void savingResultInformation() {
                    Calendar calFordDate = Calendar.getInstance();
                    saveCurrentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calFordDate.getTime());

                    Calendar calFordTime = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                    saveCurrentTime=currentTime.format(calFordTime.getTime());

                    resultRandomName = saveCurrentDate + saveCurrentTime;

                    resultRef.child("Mathematics").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                count2 = dataSnapshot.getChildrenCount();
                            }
                            else
                            {
                                count2 = 0;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    HashMap resultMap = new HashMap();
                    resultMap.put("total",total);
                    resultMap.put("date",saveCurrentDate);
                    resultMap.put("correct",correct);
                    resultMap.put("wrong",wrong);
                    resultMap.put("count",count2);

                    resultRef.child("Mathematics").child("Result"+resultRandomName).updateChildren(resultMap).addOnCompleteListener(
                            new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {

                                    if (task.isSuccessful())
                                    {
                                        Intent newIntent  = new Intent(QuestionsActivity.this,MainActivity.class);
                                        startActivity(newIntent);
                                    }
                                    else
                                    {
                                        Toast.makeText(QuestionsActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                    );
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            quesNumber.setText(String.valueOf(total));
        }
    }
    private void updateQuestions3() {
        if(total>5)
        {
            //open result
        }
        else
        {
            quesRef = FirebaseDatabase.getInstance().getReference().child("Quiz").child(standard).child("Biology").child(String.valueOf(total));
            quesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final QuestionsAdapter questionsAdapter = dataSnapshot.getValue(QuestionsAdapter.class);
                    question.setText(questionsAdapter.getQuestion());
                    option1.setText(questionsAdapter.getOption1());
                    option2.setText(questionsAdapter.getOption2());
                    option3.setText(questionsAdapter.getOption3());
                    option4.setText(questionsAdapter.getOption4());

                    option1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            colorChecker1(type);

                            if(option1.getText().toString().equals(questionsAdapter.getAnswer()))
                            {
                                correct++;
                            }
                        }
                    });
                    option2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            colorChecker2(type);

                            if(option2.getText().toString().equals(questionsAdapter.getAnswer()))
                            {
                                correct++;
                            }
                        }
                    });
                    option3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            colorChecker3(type);

                            if(option3.getText().toString().equals(questionsAdapter.getAnswer()))
                            {
                                correct++;
                            }
                        }
                    });
                    option4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            colorChecker4(type);

                            if(option4.getText().toString().equals(questionsAdapter.getAnswer()))
                            {
                                correct++;
                            }
                        }
                    });

                    nextLayout.setVisibility(View.VISIBLE);

                    if(testThird.equals("YES"))
                    {
                        nextLayout.setClickable(true);
                        nextLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                total++;
                                updateQuestions3();
                                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                            }
                        });

                        if(total==1)
                        {
                            previousLayout.setVisibility(View.GONE);
                            nextLayout.setVisibility(View.VISIBLE);
                            nextLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    total++;
                                    updateQuestions3();
                                    option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                }
                            });
                            submitLayout.setVisibility(View.GONE);
                        }
                        else if(total>1 && total<5)
                        {
                            previousLayout.setVisibility(View.VISIBLE);
                            previousLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    total--;
                                    updateQuestions3();
                                    option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                }
                            });
                            nextLayout.setVisibility(View.VISIBLE);
                            nextLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    total++;
                                    updateQuestions3();
                                    option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                }
                            });
                            submitLayout.setVisibility(View.GONE);
                        }
                        else if (total==5)
                        {
                            previousLayout.setVisibility(View.VISIBLE);
                            previousLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    total--;
                                    updateQuestions3();
                                    option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                }
                            });
                            nextLayout.setVisibility(View.GONE);
                            submitLayout.setVisibility(View.VISIBLE);
                            submitLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    HashMap userMap = new HashMap();
                                    userMap.put("Test Third","NO");


                                    studentRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            if(task.isSuccessful())
                                            {
                                                savingResultInformation();

                                            }
                                            else
                                            {
                                                Toast.makeText(QuestionsActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }
                    else
                    {
                        nextLayout.setClickable(false);
                    }
                }

                private void savingResultInformation() {
                    Calendar calFordDate = Calendar.getInstance();
                    saveCurrentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calFordDate.getTime());

                    Calendar calFordTime = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                    saveCurrentTime=currentTime.format(calFordTime.getTime());

                    resultRandomName = saveCurrentDate + saveCurrentTime;

                    resultRef.child("Biology").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                count3 = dataSnapshot.getChildrenCount();
                            }
                            else
                            {
                                count3 = 0;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    HashMap resultMap = new HashMap();
                    resultMap.put("total",total);
                    resultMap.put("date",saveCurrentDate);
                    resultMap.put("correct",correct);
                    resultMap.put("wrong",wrong);
                    resultMap.put("count",count3);

                    resultRef.child("Biology").child("Result"+resultRandomName).updateChildren(resultMap).addOnCompleteListener(
                            new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {

                                    if (task.isSuccessful())
                                    {
                                        Intent newIntent  = new Intent(QuestionsActivity.this,MainActivity.class);
                                        startActivity(newIntent);
                                    }
                                    else
                                    {
                                        Toast.makeText(QuestionsActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                    );
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            quesNumber.setText(String.valueOf(total));
        }
    }
    private void updateQuestions4() {
        if(total>5)
        {
            //open result
        }
        else
        {
            quesRef = FirebaseDatabase.getInstance().getReference().child("Quiz").child(standard).child("Physics").child(String.valueOf(total));
            quesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final QuestionsAdapter questionsAdapter = dataSnapshot.getValue(QuestionsAdapter.class);
                    question.setText(questionsAdapter.getQuestion());
                    option1.setText(questionsAdapter.getOption1());
                    option2.setText(questionsAdapter.getOption2());
                    option3.setText(questionsAdapter.getOption3());
                    option4.setText(questionsAdapter.getOption4());

                    option1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            colorChecker1(type);

                            if(option1.getText().toString().equals(questionsAdapter.getAnswer()))
                            {
                                correct++;
                            }
                        }
                    });
                    option2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            colorChecker2(type);

                            if(option2.getText().toString().equals(questionsAdapter.getAnswer()))
                            {
                                correct++;
                            }
                        }
                    });
                    option3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            colorChecker3(type);

                            if(option3.getText().toString().equals(questionsAdapter.getAnswer()))
                            {
                                correct++;
                            }
                        }
                    });
                    option4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            colorChecker4(type);

                            if(option4.getText().toString().equals(questionsAdapter.getAnswer()))
                            {
                                correct++;
                            }
                        }
                    });

                    nextLayout.setVisibility(View.VISIBLE);

                    if(testFourth.equals("YES"))
                    {
                        nextLayout.setClickable(true);
                        nextLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                total++;
                                updateQuestions4();
                                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                            }
                        });

                        if(total==1)
                        {
                            previousLayout.setVisibility(View.GONE);
                            nextLayout.setVisibility(View.VISIBLE);
                            nextLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    total++;
                                    updateQuestions4();
                                    option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                }
                            });
                            submitLayout.setVisibility(View.GONE);
                        }
                        else if(total>1 && total<5)
                        {
                            previousLayout.setVisibility(View.VISIBLE);
                            previousLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    total--;
                                    updateQuestions4();
                                    option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                }
                            });
                            nextLayout.setVisibility(View.VISIBLE);
                            nextLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    total++;
                                    updateQuestions4();
                                    option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                }
                            });
                            submitLayout.setVisibility(View.GONE);
                        }
                        else if (total==5)
                        {
                            previousLayout.setVisibility(View.VISIBLE);
                            previousLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    total--;
                                    updateQuestions4();
                                    option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                }
                            });
                            nextLayout.setVisibility(View.GONE);
                            submitLayout.setVisibility(View.VISIBLE);
                            submitLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    HashMap userMap = new HashMap();
                                    userMap.put("Test Fourth","NO");


                                    studentRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            if(task.isSuccessful())
                                            {
                                                savingResultInformation();
                                            }
                                            else
                                            {
                                                Toast.makeText(QuestionsActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }
                    else
                    {
                        nextLayout.setClickable(false);
                    }
                }

                private void savingResultInformation() {
                    Calendar calFordDate = Calendar.getInstance();
                    saveCurrentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calFordDate.getTime());

                    Calendar calFordTime = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                    saveCurrentTime=currentTime.format(calFordTime.getTime());

                    resultRandomName = saveCurrentDate + saveCurrentTime;

                    /*resultRef.child("Physics").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                count4 = dataSnapshot.getChildrenCount();
                            }
                            else
                            {
                                count4 = 0;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });*/

                    HashMap resultMap = new HashMap();
                    resultMap.put("total",total);
                    resultMap.put("date",saveCurrentDate);
                    resultMap.put("correct",correct);
                    resultMap.put("wrong",wrong);
                    resultMap.put("count",count4);

                    resultRef.child("Physics").child("Result"+resultRandomName).updateChildren(resultMap);
                                        Intent newIntent  = new Intent(QuestionsActivity.this,MainActivity.class);
                                        startActivity(newIntent);




                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            quesNumber.setText(String.valueOf(total));
        }
    }
    private void updateQuestions5() {
        if(total>5)
        {
            //open result
        }
        else
        {
            quesRef = FirebaseDatabase.getInstance().getReference().child("Quiz").child(standard).child("English").child(String.valueOf(total));
            quesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final QuestionsAdapter questionsAdapter = dataSnapshot.getValue(QuestionsAdapter.class);
                    question.setText(questionsAdapter.getQuestion());
                    option1.setText(questionsAdapter.getOption1());
                    option2.setText(questionsAdapter.getOption2());
                    option3.setText(questionsAdapter.getOption3());
                    option4.setText(questionsAdapter.getOption4());

                    option1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            colorChecker1(type);

                            if(option1.getText().toString().equals(questionsAdapter.getAnswer()))
                            {
                                correct++;
                            }
                        }
                    });
                    option2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            colorChecker2(type);

                            if(option2.getText().toString().equals(questionsAdapter.getAnswer()))
                            {
                                correct++;
                            }
                        }
                    });
                    option3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            colorChecker3(type);

                            if(option3.getText().toString().equals(questionsAdapter.getAnswer()))
                            {
                                correct++;
                            }
                        }
                    });
                    option4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            colorChecker4(type);

                            if(option4.getText().toString().equals(questionsAdapter.getAnswer()))
                            {
                                correct++;
                            }
                        }
                    });

                    nextLayout.setVisibility(View.VISIBLE);

                    if(testFifth.equals("YES"))
                    {
                        nextLayout.setClickable(true);
                        nextLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                total++;
                                updateQuestions5();
                                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                            }
                        });

                        if(total==1)
                        {
                            previousLayout.setVisibility(View.GONE);
                            nextLayout.setVisibility(View.VISIBLE);
                            nextLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    total++;
                                    updateQuestions5();
                                    option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                }
                            });
                            submitLayout.setVisibility(View.GONE);
                        }
                        else if(total>1 && total<5)
                        {
                            previousLayout.setVisibility(View.VISIBLE);
                            previousLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    total--;
                                    updateQuestions5();
                                    option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                }
                            });
                            nextLayout.setVisibility(View.VISIBLE);
                            nextLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    total++;
                                    updateQuestions5();
                                    option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                }
                            });
                            submitLayout.setVisibility(View.GONE);
                        }
                        else if (total==5)
                        {
                            previousLayout.setVisibility(View.VISIBLE);
                            previousLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    total--;
                                    updateQuestions5();
                                    option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                                    option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                    option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                                }
                            });
                            nextLayout.setVisibility(View.GONE);
                            submitLayout.setVisibility(View.VISIBLE);
                            submitLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    HashMap userMap = new HashMap();
                                    userMap.put("Test Five","NO");

                                    studentRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            if(task.isSuccessful())
                                            {
                                                updateResultStatistics();
                                                Intent newIntent = new Intent(QuestionsActivity.this,MainActivity.class);
                                                startActivity(newIntent);
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(QuestionsActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }
                            });
                        }
                    }
                    else
                    {
                        nextLayout.setClickable(false);
                    }
                }

                private void updateResultStatistics() {

                    Calendar calFordDate = Calendar.getInstance();
                    saveCurrentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calFordDate.getTime());

                    Calendar calFordTime = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                    saveCurrentTime=currentTime.format(calFordTime.getTime());

                    resultRandomName = saveCurrentDate + saveCurrentTime;

                    resultRef.child("English").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                count5 = dataSnapshot.getChildrenCount();
                            }
                            else
                            {
                                count5 = 0;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    HashMap resultMap = new HashMap();
                    resultMap.put("total",total);
                    resultMap.put("date",saveCurrentDate);
                    resultMap.put("correct",correct);
                    resultMap.put("wrong",wrong);
                    resultMap.put("count",count5);

                    resultRef.child("English").child("Result"+resultRandomName).updateChildren(resultMap).addOnCompleteListener(
                            new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {

                                    if (task.isSuccessful())
                                    {
                                        Intent newIntent = new Intent(QuestionsActivity.this,MainActivity.class);
                                        startActivity(newIntent);
                                    }
                                    else
                                    {
                                        Toast.makeText(QuestionsActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                    );
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            quesNumber.setText(String.valueOf(total));
        }
    }

    private void colorChecker1(String type) {
        switch (type)
        {
            case "a" : option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_red));
                        option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                        option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                        option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                        option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                        option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                        option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                        option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                        break;
            case "b" : option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_green));
                        option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                        option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                        option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                        option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                        option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                        option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                        option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                        break;
            case "c" : option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_yellow));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
            case "d" : option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_sky_blue));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
            case "e" : option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_orange));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
        }
    }
    private void colorChecker2(String type) {
        switch (type)
        {
            case "a" : option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_red));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
            case "b" : option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_green));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
            case "c" : option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_yellow));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
            case "d" : option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_sky_blue));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
            case "e" : option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_orange));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
        }
    }
    private void colorChecker3(String type) {
        switch (type)
        {
            case "a" : option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_red));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
            case "b" : option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_green));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
            case "c" : option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_yellow));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
            case "d" : option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_sky_blue));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
            case "e" : option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_orange));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
        }
    }
    private void colorChecker4(String type) {
        switch (type)
        {
            case "a" : option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_red));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
            case "b" : option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_green));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
            case "c" : option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_yellow));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
            case "d" : option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_sky_blue));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
            case "e" : option4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.creative_orange));
                option4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                option2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                option1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
        }
    }

    private void reverseTimer(int seconds, final TextView tv)
    {
        new CountDownTimer(seconds*1000 + 1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished/1000);
                int minutes = seconds / 60;
                int hours = minutes / 60;
                seconds = seconds % 60;
                tv.setText(String.format("%02d",hours)+":"+String.format("%02d",minutes)+":"+String.format("%02d",seconds));
            }

            @Override
            public void onFinish() {
                tv.setText("00:00:00");
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        if(mTTS!=null)
        {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(mTTS!=null)
        {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onBackPressed();
    }
}
