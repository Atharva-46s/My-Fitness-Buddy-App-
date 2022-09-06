package com.example.trail4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class account_changes extends AppCompatActivity {

    TextView updateAccountTextView,deleteAccountTextView,updateProfileTextView;
    Button confirmDeleteAccountBtn;
    Dialog dialog;
    ImageView imageViewClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_account_changes);

        updateAccountTextView=findViewById(R.id.update_account_text_view);
        deleteAccountTextView=findViewById(R.id.delete_account_text_view);
        updateProfileTextView=findViewById(R.id.update_profile_info_text_view);
        imageViewClose=findViewById(R.id.image_view_close);
        confirmDeleteAccountBtn=findViewById(R.id.confirm_delete_account_btn);

        dialog=new Dialog(this);

        deleteAccountTextView.setOnClickListener((v)-> confirmDeleteDialog());
        updateAccountTextView.setOnClickListener((v)->updateEmail());
        updateProfileTextView.setOnClickListener((v)-> updateProfile());

    }

    void updateProfile(){
        startActivity(new Intent(account_changes.this,signup.class));
    }

    void updateEmail(){
        startActivity(new Intent(account_changes.this,update_email.class));
    }

    void confirmDeleteDialog(){
        dialog.setContentView(R.layout.delete_confirmation_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose=dialog.findViewById(R.id.image_view_close);
        Button btnConfirmDeleteAccount=dialog.findViewById(R.id.confirm_delete_account_btn);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Utility.showToast(account_changes.this,"Action Terminated");
            }
        });


        btnConfirmDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Utility.showToast(account_changes.this,"Account Deleted Successfully");
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getApplicationContext(),login2_SignUpDone.class));
                            finish();

                        }else{
                            Utility.showToast(account_changes.this,task.getException().getLocalizedMessage());
                        }

                    }
                });

            }
        });

        dialog.show();


    }
}