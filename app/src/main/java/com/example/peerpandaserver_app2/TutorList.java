package com.example.peerpandaserver_app2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import info.hoang8f.widget.FButton;

public class TutorList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button btnView;

    FirebaseDatabase database;
    DatabaseReference table_tutor;
    FirebaseStorage storage;
    StorageReference storageReference;

    Tutor currentTutor;

    TextView tutor_name, tutor_image, stuid, courseTeach;
    RatingBar ratingBar;

    String TutorId="";

    FirebaseRecyclerAdapter<Tutor, TutorViewHolder> adapter;

    MaterialEditText name, studentid, programme, sem, courseTch, courseName, price ;
    Button btnAdd, btnCancel;

    Tutor newTutor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("All Tutor");
        setSupportActionBar(toolbar);

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        table_tutor = database.getReference("Tutor");;

        recyclerView = (RecyclerView)findViewById(R.id.recycler_tutor);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        loadListTutor(TutorId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //Intent csrtIntent = new Intent(TutorList.this, Cart.class);
                //startActivity(csrtIntent);
                showDialog();
            }
        });
    }
    private void showDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TutorList.this);
        alertDialog.setTitle("Add new Tutor");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_new_tutor_layout = inflater.inflate(R.layout.add_new_tutor_layout,null);

        name = add_new_tutor_layout.findViewById(R.id.name);
        courseTch = add_new_tutor_layout.findViewById(R.id.courseTeach);

        studentid = add_new_tutor_layout.findViewById(R.id.stuid);
        programme = add_new_tutor_layout.findViewById(R.id.programme);
        sem = add_new_tutor_layout.findViewById(R.id.sem);
        courseName = add_new_tutor_layout.findViewById(R.id.courseName);
        price = add_new_tutor_layout.findViewById(R.id.total_pay);

        btnAdd = add_new_tutor_layout.findViewById(R.id.btnAdd);
        btnCancel = add_new_tutor_layout.findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final ProgressDialog mDialog = new ProgressDialog(TutorList.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                table_tutor.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if tutor already exist
                        if(dataSnapshot.child(studentid.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(TutorList.this, "Student ID already registered",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mDialog.dismiss();

                            Tutor tutor = new Tutor(
                                    name.getText().toString(),
                                    courseTch.getText().toString(),
                                    programme.getText().toString(),
                                    sem.getText().toString(),
                                    studentid.getText().toString(),
                                    price.getText().toString(),
                                    courseName.getText().toString());

                            table_tutor.child(studentid.getText().toString()).setValue(tutor);
                            Toast.makeText(TutorList.this, "Added succesfully",Toast.LENGTH_SHORT).show();

                            //To Home page
                            Intent signIn = new Intent(TutorList.this, TutorList.class);
                            startActivity(signIn);
                            //Common.currentUser = user;

                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn = new Intent(TutorList.this, TutorList.class);
                startActivity(signIn);
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

    private void loadListTutor(String tutorId) {
        adapter = new FirebaseRecyclerAdapter<Tutor, TutorViewHolder>(Tutor.class,R.layout.tutor_item, TutorViewHolder.class, table_tutor) {
            @Override
            protected void populateViewHolder(TutorViewHolder viewHolder, Tutor model, int position) {
                viewHolder.tutor_name.setText(model.getName());
                viewHolder.stuid.setText(model.getStuID());
                viewHolder.courseTeach.setText(model.getCourseTeach());
                Picasso.with(getBaseContext()).load(model.getTutorImage())
                        .into(viewHolder.tutor_image);
                final Tutor clickItem = model;
                viewHolder.setItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(Home.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
                        Intent tutorDetail = new Intent(TutorList.this, TutorDetail.class);
                        tutorDetail.putExtra("TutorId",adapter.getRef(position).getKey());
                        startActivity(tutorDetail);
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
    /*
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_other) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    //update and delete

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals(Common.UPDATE))
        {
            showEditDialog(adapter.getRef(item.getOrder()).getKey(), adapter.getItem(item.getOrder()));

        }else if(item.getTitle().equals(Common.DELETE))
        {
            showDeleteDialog(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    private void showEditDialog(final String key, final Tutor item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TutorList.this);
        alertDialog.setTitle("Edit Tutor");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_new_tutor_layout = inflater.inflate(R.layout.edit_tutor_layout,null);

        name = add_new_tutor_layout.findViewById(R.id.name);
        studentid = add_new_tutor_layout.findViewById(R.id.stuid);
        programme = add_new_tutor_layout.findViewById(R.id.programme);
        sem = add_new_tutor_layout.findViewById(R.id.sem);
        courseTch = add_new_tutor_layout.findViewById(R.id.courseTeach);
        courseName = add_new_tutor_layout.findViewById(R.id.courseName);
        price = add_new_tutor_layout.findViewById(R.id.total_pay);

        //set default name
        name.setText(item.getName());
        studentid.setText(item.getStuID());
        programme.setText(item.getCourse());
        sem.setText(item.getSem());
        courseTch.setText(item.getCourseTeach());
        courseName.setText(item.getCoursename());
        price.setText(item.getPrice());

        btnAdd = add_new_tutor_layout.findViewById(R.id.btnAdd);
        btnCancel = add_new_tutor_layout.findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final ProgressDialog mDialog = new ProgressDialog(TutorList.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                table_tutor.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mDialog.dismiss();

                        Tutor tutor = new Tutor(
                                programme.getText().toString(),
                                courseTch.getText().toString(),
                                name.getText().toString(),
                                sem.getText().toString(),
                                studentid.getText().toString(),
                                price.getText().toString(),
                                courseName.getText().toString());

                        table_tutor.child(studentid.getText().toString()).setValue(tutor);
                        Toast.makeText(TutorList.this, "Save succesfully",Toast.LENGTH_SHORT).show();

                        //To Home page
                        Intent signIn = new Intent(TutorList.this, TutorList.class);
                        startActivity(signIn);
                        //Common.currentUser = user;

                        finish();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn = new Intent(TutorList.this, TutorList.class);
                startActivity(signIn);
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
        table_tutor.child(key).removeValue();
        Toast.makeText(this, "Tutor deleted!", Toast.LENGTH_SHORT).show();

    }
}
