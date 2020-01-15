package com.example.peerpandaserver_app2;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.peerpandaserver_app2.Common.Common;
import com.example.peerpandaserver_app2.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    EditText pass, adminID;
    Button btnSignIn;
    FirebaseDatabase database;
    DatabaseReference table_user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //link to HomeNavActivity by signIn button
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        pass = (EditText)findViewById(R.id.pass);
        adminID = (EditText)findViewById(R.id.adminID);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        //Init firebase
        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                signInUser(adminID.getText().toString(), pass.getText().toString());

                final SharedPreferences sharedPreferences = getSharedPreferences("user_data",0);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
            }
        });
    }
    private void signInUser(String adminID, String pass) {
        final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
        mDialog.setMessage("Please wait...");
        mDialog.show();

        final String localadminID = adminID;
        final String localPass = pass;
        table_user.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //check if user not exist in database
                if(dataSnapshot.child(localadminID).exists()){
                    //get user info
                    mDialog.dismiss();
                    User user = dataSnapshot.child(localadminID).getValue(User.class);
                    user.setAdminID(localadminID); //set stuid
                    if(Boolean.parseBoolean(user.getIsStaff())){
                        if (user.getPass().equals(localPass)) {
                            //editor.putString("admin_id",localadminID);
                            //editor.commit();
                            //Toast.makeText(SignIn.this, "Sign in successfully ! \n"+sharedPreferences.getString("stud_id",""), Toast.LENGTH_SHORT).show();
                            Toast.makeText(SignIn.this, "Sign in successfully !", Toast.LENGTH_SHORT).show();
                            //To Home page
                            Intent Home = new Intent(SignIn.this, Home.class);
                            startActivity(Home);
                            Common.currentUser = user;
                            finish();

                        } else {
                            Toast.makeText(SignIn.this, "Sign In failed !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(SignIn.this, "Please Login with Staff ID", Toast.LENGTH_SHORT).show();

                }
                else{
                    mDialog.dismiss();
                    Toast.makeText(SignIn.this, "User not exist in Database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
