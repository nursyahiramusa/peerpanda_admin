package com.example.peerpandaserver_app2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peerpandaserver_app2.Common.Common;
import com.example.peerpandaserver_app2.Interface.OnItemClickListener;
import com.example.peerpandaserver_app2.Model.Tutor;
import com.example.peerpandaserver_app2.ViewHolder.TutorViewHolder;
import com.example.peerpandaserver_app2.Model.Tutor;
import com.example.peerpandaserver_app2.ViewHolder.TutorViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseDatabase database;
    DatabaseReference tutor;

    TextView txtFullName, txtProgramme, txtStuid;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Tutor, TutorViewHolder> adapter;

    String TutorId="";

    Button btnhome, btnlist, btnrequest, btnlogout;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        tutor = database.getReference("Tutor");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set name for user
        View headerView = navigationView.getHeaderView(0);
        txtFullName = (TextView) headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getName());
        txtProgramme = (TextView) headerView.findViewById(R.id.txtProgramme);
        txtProgramme.setText(Common.currentUser.getIsStaff());
        txtStuid = (TextView) headerView.findViewById(R.id.txtStuid);
        txtStuid.setText(Common.currentUser.getAdminID());

        //load menu
        recycler_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        //Button home content
        btnhome = (Button)findViewById(R.id.btnhome);
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Home.this, Home.class);
                startActivity(home);

            }
        });

        btnlist = (Button)findViewById(R.id.btnlist);
        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent list = new Intent(Home.this, TutorList.class);
                startActivity(list);

            }
        });

        btnrequest = (Button)findViewById(R.id.btnreq);
        btnrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent req = new Intent(Home.this, RequestList.class);
                startActivity(req);

            }
        });

        btnlogout = (Button)findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(Home.this, MainActivity.class);
                startActivity(logout);

            }
        });

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

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
        //if (id == R.id.action_settings) {
            //return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Home) {
            Intent home = new Intent(Home.this, Home.class);
            startActivity(home);
        } else if (id == R.id.nav_TutorRequest) {
            Intent TutorRequest = new Intent(Home.this, RequestList.class);
            startActivity(TutorRequest);
        } else if (id == R.id.nav_TutorList) {
            Intent TutorList = new Intent(Home.this, TutorList.class);
            startActivity(TutorList);;
        } else if (id == R.id.nav_Exit) {
            Intent SignIn = new Intent(Home.this, MainActivity.class);
            SignIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(SignIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void loadMenu() {
        adapter = new FirebaseRecyclerAdapter<Tutor, TutorViewHolder>(Tutor.class,R.layout.tutor_item, TutorViewHolder.class, tutor) {
            @Override
            protected void populateViewHolder(TutorViewHolder viewHolder, Tutor model, int position) {
                viewHolder.tutor_name.setText(model.getName());
                viewHolder.courseTeach.setText(model.getCourseTeach());
                Picasso.with(getBaseContext()).load(model.getTutorImage())
                        .into(viewHolder.tutor_image);
                final Tutor clickItem = model;
                viewHolder.setItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(Home.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
                        Intent tutorDetail = new Intent(Home.this, TutorList.class);
                        tutorDetail.putExtra("TutorId",adapter.getRef(position).getKey());
                        startActivity(tutorDetail);

                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);

    }

}
