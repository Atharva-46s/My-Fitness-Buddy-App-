package com.example.trail4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    EditText forgotPasswordEmailEditText;
    Button forgotPasswordSendMailBtn;

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forgot_password);

        forgotPasswordEmailEditText=findViewById(R.id.forgot_password_email_edit_text);
        forgotPasswordSendMailBtn=findViewById(R.id.forgot_password_send_mail_btn);

        forgotPasswordSendMailBtn.setOnClickListener((v)-> validateEmail());
    }

    void validateEmail(){
        email=forgotPasswordEmailEditText.getText().toString();
        if (email.isEmpty()){
            forgotPasswordEmailEditText.setError("Email Required...");
        }else{
            forgotPasswordSendMail();
        }
    }

    void forgotPasswordSendMail(){
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Utility.showToast(forgotPassword.this,"Check your mail");
                    startActivity(new Intent(forgotPassword.this,login2_SignUpDone.class));
                    finish();
                }else{
                    Utility.showToast(forgotPassword.this,task.getException().getLocalizedMessage());
                }
            }
        });
    }


}