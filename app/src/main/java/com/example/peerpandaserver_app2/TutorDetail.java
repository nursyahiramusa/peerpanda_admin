package com.example.peerpandaserver_app2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.peerpandaserver_app2.Common.Common;
import com.example.peerpandaserver_app2.Database.Database;
import com.example.peerpandaserver_app2.Model.Booking;
import com.example.peerpandaserver_app2.Model.Rating;
import com.example.peerpandaserver_app2.Model.Tutor;
import com.example.peerpandaserver_app2.Model.User;
import com.example.peerpandaserver_app2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.Arrays;
import java.util.Calendar;


public class TutorDetail extends AppCompatActivity {

    TextView name, stuid, campus, programme, sem, gender, phoneno, courseTeach, courseName, total_pay, courseGrade;
    TextView bookID, datetime, location , coursecode, stuID, tutorID;
    ImageView tutor_image;
    FloatingActionButton btnRating;
    Button btnBookNow;
    RatingBar ratingBar;
    String tutorId="";
    String status = "0";

    FirebaseDatabase database;
    DatabaseReference tutors, tutorRequest;

    Tutor currentTutor;
    User currentUser;



    public static EditText DateEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_detail);

        //Firebase
        database = FirebaseDatabase.getInstance();
        tutors = database.getReference("Tutor");
        tutorRequest = database.getReference("TutorRequest");

        //FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //String uid = currentUser.getUid();
        //tutor_image = (ImageView)findViewById(R.id.tutor_image);

        name = (TextView)findViewById(R.id.name);
        stuid = (TextView)findViewById(R.id.stuid);
        campus = (TextView)findViewById(R.id.campus);
        sem = (TextView)findViewById(R.id.sem);
        programme = (TextView)findViewById(R.id.programme);
        gender = (TextView)findViewById(R.id.gender);
        phoneno = (TextView)findViewById(R.id.phoneno);
        courseTeach = (TextView)findViewById(R.id.courseTeach);
        courseName = (TextView) findViewById(R.id.courseName);
        total_pay = (TextView)findViewById(R.id.total_pay);
        //courseGrade = (TextView)findViewById(R.id.courseGrade);



        //Get tutor id from Intent
        if(getIntent() !=null)
            tutorId = getIntent().getStringExtra("TutorId");
        if(tutorId != null){
            getDetailsTutor(tutorId);
        }

    }

    private void getDetailsTutor(final String tutorId) {
        tutors.child(tutorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentTutor = dataSnapshot.getValue(Tutor.class);

                //set image
                //Picasso.with(getBaseContext()).load(currentTutor.getTutorImage()).into(tutor_image);

                campus.setText(currentTutor.getCampus());
                sem.setText(currentTutor.getSem());
                courseTeach.setText(currentTutor.getCourseTeach());
                name.setText(currentTutor.getName());
                programme.setText(currentTutor.getCourse());
                total_pay.setText(currentTutor.getPrice());
                gender.setText(currentTutor.getGender());
                stuid.setText(currentTutor.getStuID());
                courseName.setText(currentTutor.getCoursename());
                //courseGrade.setText(currentTutor.getCourseGrade());
                phoneno.setText(currentTutor.getPhoneno());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}