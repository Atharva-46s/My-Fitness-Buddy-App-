package com.example.trail4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login2_SignUpDone extends AppCompatActivity {

    EditText emailEditText,passwordEditText;
    Button login2Btn;
    ProgressBar progressBar;
    TextView createAccountBtnTextView,forgotPasswordBtnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login2_sign_up_done);

        emailEditText=findViewById(R.id.email_edit_text);
        passwordEditText=findViewById(R.id.password_edit_text);
        login2Btn=findViewById(R.id.login2_btn);
        progressBar=findViewById(R.id.progress_bar);
        createAccountBtnTextView=findViewById(R.id.create_account_text_view_btn);
        forgotPasswordBtnTextView=findViewById(R.id.forgot_password_text_view_btn);

        login2Btn.setOnClickListener((v)-> loginUser());
        createAccountBtnTextView.setOnClickListener((v)->startActivity(new Intent(login2_SignUpDone.this,login2_withAuth.class)));
        forgotPasswordBtnTextView.setOnClickListener((v)-> forgotPassword());

    }

    void loginUser(){
        String email= emailEditText.getText().toString();
        String password= passwordEditText.getText().toString();

        boolean isValidated=validateData(email,password);

        if (!isValidated){
            return;
        }

        loginAccountInFirebase(email,password);
    }

    void loginAccountInFirebase(String email,String password){
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);

                if (task.isSuccessful()){
                    if (firebaseAuth.getCurrentUser().isEmailVerified()){
                        startActivity(new Intent(login2_SignUpDone.this,dashboard.class));

                    }else{
                        Utility.showToast(login2_SignUpDone.this,"Email is not verified, please verify your email ");
                    }
                }else{
                    Utility.showToast(login2_SignUpDone.this,task.getException().getLocalizedMessage());
                }
            }
        });
    }

    void forgotPassword(){
        startActivity(new Intent(login2_SignUpDone.this,forgotPassword.class));
    }

    void changeInProgress(boolean inProgress) {
        if (inProgress){
            progressBar.setVisibility(View.VISIBLE);
            login2Btn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            login2Btn.setVisibility(View.VISIBLE);
        }
    }

    boolean validateData(String email,String password){
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Email is invalid");
            return false;
        }

        if (password.length()<6){
            passwordEditText.setError("Password length is invalid");
            return false;

        }

        return true;
    }

}