package com.example.peerpandaserver_app2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.peerpandaserver_app2.Model.Tutor;
import com.example.peerpandaserver_app2.Model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class RequestDetail extends AppCompatActivity {

    TextView name, stuid, campus, programme, sem, gender, phoneno, courseTeach, courseName, total_pay, coursegrade;
    TextView bookID, datetime, location , coursecode, stuID, tutorID;
    ImageView tutor_image;
    FloatingActionButton btnRating;
    Button btnBookNow;
    RatingBar ratingBar;
    String tutorId="";
    String status = "0";

    FirebaseDatabase database;
    DatabaseReference table_tutorReq;
    DatabaseReference ratingTbl;
    DatabaseReference bookingTbl, userTbl;

    Tutor currentTutor;
    User currentUser;

    public static EditText DateEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        //Firebase
        database = FirebaseDatabase.getInstance();
        table_tutorReq = database.getReference("TutorRequest");

        //FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //String uid = currentUser.getUid();
        name = (TextView)findViewById(R.id.name);
        courseTeach = (TextView)findViewById(R.id.courseTeach);
        tutor_image = (ImageView)findViewById(R.id.tutor_image);

        stuid = (TextView)findViewById(R.id.stuid);
        campus = (TextView)findViewById(R.id.campus);
        sem = (TextView)findViewById(R.id.sem);
        programme = (TextView)findViewById(R.id.programme);
        gender = (TextView)findViewById(R.id.gender);
        phoneno = (TextView)findViewById(R.id.phoneno);

        coursegrade = (TextView)findViewById(R.id.courseGrade);
        courseName = (TextView) findViewById(R.id.courseName);
        total_pay = (TextView)findViewById(R.id.total_pay);


        //Get tutor id from Intent
        if(getIntent() !=null)
            tutorId = getIntent().getStringExtra("TutorId");
        if(tutorId != null){
            getDetailsTutor(tutorId);
        }

    }

    private void getDetailsTutor(final String tutorId) {
        table_tutorReq.child(tutorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentTutor = dataSnapshot.getValue(Tutor.class);

                name.setText(currentTutor.getName());
                courseTeach.setText(currentTutor.getCourseTeach());
                Picasso.with(getBaseContext()).load(R.drawable.noprofile).into(tutor_image);

                stuid.setText(currentTutor.getTutorID());
                campus.setText(currentTutor.getCampus());
                programme.setText(currentTutor.getCourse());
                sem.setText(currentTutor.getSem());
                gender.setText(currentTutor.getGender());
                phoneno.setText(currentTutor.getPhoneno());

                coursegrade.setText(currentTutor.getCourseGrade());
                courseName.setText(currentTutor.getCoursename());
                total_pay.setText(currentTutor.getPrice());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}