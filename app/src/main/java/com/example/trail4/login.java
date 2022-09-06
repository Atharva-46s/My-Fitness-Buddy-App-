package com.example.trail4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    Button signupbutton,loginbtn;

    TextInputLayout username_var,password_var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.login);

        signupbutton=findViewById(R.id.signup);
        loginbtn=findViewById(R.id.login);

        username_var=findViewById(R.id.username_text_field_design);
        password_var=findViewById(R.id.password_input_field);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username=username_var.getEditText().getText().toString();
                String password=password_var.getEditText().getText().toString();

                if(!username.isEmpty()){
                    username_var.setError(null);
                    username_var.setErrorEnabled(false);
                    if(!password.isEmpty()){
                        password_var.setError(null);
                        password_var.setErrorEnabled(false);

                        final String username_data= username_var.getEditText().getText().toString();
                        final String password_data= password_var.getEditText().getText().toString();

                        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference= firebaseDatabase.getReference("datauser");

                        Query check_username= databaseReference.orderByChild("username").equalTo(username_data);

                        check_username.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists()){
                                    username_var.setError(null);
                                    username_var.setErrorEnabled(false);
                                    String passwordcheck= dataSnapshot.child(username_data).child("password").getValue(String.class);
                                    if (passwordcheck.equals(password_data)){
                                        password_var.setError(null);
                                        password_var.setErrorEnabled(false);
                                        Intent intent= new Intent(getApplicationContext(),dashboard.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(),"Login successfull",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else{
                                        password_var.setError("Wrong password");
                                    }
                                }else{

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                username_var.setError("User does not exists");

                            }
                        });


                    }else{
                        password_var.setError("Please enter the password");
                    }
                }else{
                    username_var.setError("Please enter the username");
                }

            }
        });


        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),signup.class);
                startActivity(intent);

            }
        });

    }
}