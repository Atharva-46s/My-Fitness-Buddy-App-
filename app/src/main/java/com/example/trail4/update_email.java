package com.example.trail4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class update_email extends AppCompatActivity {

    Button updateEmailBtn;
    EditText newEmailEditText;

    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_update_email);

        updateEmailBtn=findViewById(R.id.update_email_btn);
        newEmailEditText=findViewById(R.id.new_email_edit_text);

        updateEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseUser.updateEmail(newEmailEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Utility.showToast(update_email.this,"Email Updated Successfully");
                            firebaseAuth.signOut();
                            startActivity(new Intent(update_email.this,login2_SignUpDone.class));
                            finish();
                        }else{
                            Utility.showToast(update_email.this,task.getException().getLocalizedMessage());
                        }
                    }
                });

            }
        });
    }
}