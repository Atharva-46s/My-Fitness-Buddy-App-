package com.example.trail4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    TextInputLayout fullname_var,username_var,email_var,phonenumber_var;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.signup);

        fullname_var=findViewById(R.id.fullname_field);
        username_var=findViewById(R.id.username_field_);
        email_var=findViewById(R.id.email_field);
        phonenumber_var=findViewById(R.id.phone_number_field);

    }

    public void registerbuttonclick(View view) {
        String fullname=fullname_var.getEditText().getText().toString();
        String username=username_var.getEditText().getText().toString();
        String email=email_var.getEditText().getText().toString();
        String phonenumber=phonenumber_var.getEditText().getText().toString();

        if(!fullname.isEmpty()){
            fullname_var.setError(null);
            fullname_var.setErrorEnabled(false);
            if(!username.isEmpty()){
                username_var.setError(null);
                username_var.setErrorEnabled(false);
                if(!email.isEmpty()){
                    email_var.setError(null);
                    email_var.setErrorEnabled(false);
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        if(!phonenumber.isEmpty()){
                            phonenumber_var.setError(null);
                            phonenumber_var.setErrorEnabled(false);

                            phonenumber_var.setError("please enter the phone number");
                            firebaseDatabase=FirebaseDatabase.getInstance();
                            reference=firebaseDatabase.getReference("datauser");

                            String fullname_s=fullname_var.getEditText().getText().toString();
                            String username_s=username_var.getEditText().getText().toString();
                            String email_s=email_var.getEditText().getText().toString();
                            String phonenumber_s=phonenumber_var.getEditText().getText().toString();

                            storingdata storingdatass= new storingdata(fullname_s,username_s,email_s,phonenumber_s);

                            reference.child(username_s).setValue(storingdatass);

                            Toast.makeText(getApplicationContext(),"Profile Saved Successfully",Toast.LENGTH_SHORT).show();

                            Intent intent= new Intent(getApplicationContext(),account_changes.class);
                            startActivity(intent);
                            finish();

                        }else{
                            phonenumber_var.setError("please enter phone number");
                        }
                    }
                    else{
                        email_var.setError("please enter valid email id");
                    }
                }else{
                    email_var.setError("please enter the email id");
                }
            }else{
                username_var.setError("please enter the username");
            }
        }else{
            fullname_var.setError("Please enter the full name");
        }
    }
}