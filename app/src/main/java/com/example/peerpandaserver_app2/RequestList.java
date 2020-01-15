package com.example.peerpandaserver_app2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peerpandaserver_app2.Common.Common;
import com.example.peerpandaserver_app2.Interface.OnItemClickListener;
import com.example.peerpandaserver_app2.Model.Tutor;
import com.example.peerpandaserver_app2.ViewHolder.RequestViewHolder;
import com.example.peerpandaserver_app2.ViewHolder.TutorViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

public class RequestList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button btnAccept, btnReject;

    FirebaseDatabase database;
    DatabaseReference table_tutorReq, table_tutor;
    FirebaseStorage storage;
    StorageReference storageReference;

    Tutor currentTutor;

    TextView name, sem, studentid, tutoid, courseTeach, courseName, courseGrade, programme, campus, gender, phoneno, price;
    RatingBar ratingBar;

    String TutorId="";

    FirebaseRecyclerAdapter<Tutor, RequestViewHolder> adapter;

    //MaterialEditText name, studentid, programme, sem, courseTch, courseName, price ;
    //Button btnAdd, btnCancel;

    Tutor newTutor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Requested Tutor");
        setSupportActionBar(toolbar);

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        table_tutorReq = database.getReference("TutorRequest");
        table_tutor = database.getReference("Tutor");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_tutor);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        loadListTutor(TutorId);
    }

    private void loadListTutor(String tutorId) {
        adapter = new FirebaseRecyclerAdapter<Tutor, RequestViewHolder>(Tutor.class,R.layout.request_item, RequestViewHolder.class, table_tutorReq) {
            @Override
            protected void populateViewHolder(RequestViewHolder viewHolder, Tutor model, int position) {
                viewHolder.name.setText(model.getName());
                viewHolder.studentid.setText(model.getStuID());

                final Tutor clickItem = model;
                viewHolder.setItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(RequestList.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
                        Intent requestDetail = new Intent(RequestList.this, RequestDetail.class);
                        requestDetail.putExtra("TutorId",adapter.getRef(position).getKey());
                        startActivity(requestDetail);
                    }
                });
            }
        };
        //LayoutInflater.from(parent.getContext())
        //        .inflate(R.layout.tutor_item, parent, false);
        //set adapter
        //Log.d("TAG", "" + adapter.getItemCount());
        recyclerView.setAdapter(adapter);

    }

    //update and delete
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals(Common.UPDATE)) {
            showEditDialog(adapter.getRef(item.getOrder()).getKey(), adapter.getItem(item.getOrder()));
        }
        return super.onContextItemSelected(item);
    }

    private void showEditDialog(final String key, final Tutor item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RequestList.this);
        alertDialog.setTitle("Manage Tutor");
        alertDialog.setMessage("Do you want to accept this tutor request?");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_new_tutor_layout = inflater.inflate(R.layout.accept_reject_layout,null);

        name = add_new_tutor_layout.findViewById(R.id.name);
        studentid = add_new_tutor_layout.findViewById(R.id.stuid);
        courseTeach = add_new_tutor_layout.findViewById(R.id.courseTeach);
        programme = add_new_tutor_layout.findViewById(R.id.programme);
        sem = add_new_tutor_layout.findViewById(R.id.sem);
        courseName = add_new_tutor_layout.findViewById(R.id.courseName);
        price = add_new_tutor_layout.findViewById(R.id.total_pay);
        campus = add_new_tutor_layout.findViewById(R.id.campus);
        gender = add_new_tutor_layout.findViewById(R.id.gender);
        phoneno = add_new_tutor_layout.findViewById(R.id.phoneno);
        courseGrade = add_new_tutor_layout.findViewById(R.id.courseGrade);
        tutoid = add_new_tutor_layout.findViewById(R.id.stuid);

        //set default name
        name.setText(item.getName());
        studentid.setText(item.getStuID());
        courseTeach.setText(item.getCourseTeach());
        programme.setText(item.getCourse());
        sem.setText(item.getSem());
        courseName.setText(item.getCoursename());
        price.setText(item.getPrice());
        campus.setText(item.getCampus());
        gender.setText(item.getGender());
        phoneno.setText(item.getPhoneno());
        courseGrade.setText(item.getCourseGrade());
        tutoid.setText(item.getStuID());


        btnAccept = add_new_tutor_layout.findViewById(R.id.btnAccept);
        btnReject = add_new_tutor_layout.findViewById(R.id.btnReject);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final ProgressDialog mDialog = new ProgressDialog(RequestList.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                table_tutor.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            mDialog.dismiss();

                            Tutor tutor = new Tutor(
                                    name.getText().toString(),
                                    studentid.getText().toString(),
                                    courseTeach.getText().toString(),
                                    sem.getText().toString(),
                                    programme.getText().toString(),
                                    tutoid.getText().toString(),
                                    gender.getText().toString(),
                                    price.getText().toString(),
                                    campus.getText().toString(),
                                    phoneno.getText().toString(),
                                    courseName.getText().toString(),
                                    courseGrade.getText().toString()
                            );

                        table_tutor.child(studentid.getText().toString()).setValue(tutor);
                            Toast.makeText(RequestList.this, "User now has become Tutor",Toast.LENGTH_SHORT).show();

                            //Common.currentUser = user;
                            table_tutorReq.child(studentid.getText().toString()).removeValue();
                            table_tutorReq.child(studentid.getText().toString()).removeValue();
                            Intent signIn = new Intent(RequestList.this, Home.class);
                            startActivity(signIn);
                            finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent(RequestList.this, RequestList.class);
                startActivity(v);
                table_tutorReq.child(studentid.getText().toString()).removeValue();
                finish();
            }
        });

        alertDialog.setView(add_new_tutor_layout);
        alertDialog.setIcon(R.drawable.add);

        //set button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                //update info
                item.setName(name.getText().toString());
                item.setStuID(studentid.getText().toString());
                table_tutor.child(key).setValue(item);
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void showDeleteDialog(String key) {
        table_tutorReq.child(key).removeValue();
        //Toast.makeText(this, "Tutor deleted!", Toast.LENGTH_SHORT).show();

    }


}
