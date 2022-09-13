package com.example.trail4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class dashboard extends AppCompatActivity {

    ImageView menuBtn2;
    TextView foodNutritionFunctionalityTextView;
//    MenuItem account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.dashboard);

//        account=findViewById(R.id.menuAccount);



        menuBtn2=findViewById(R.id.imageMenu);
        menuBtn2.setOnClickListener((v)->showMenu());

        foodNutritionFunctionalityTextView=findViewById(R.id.food_nutrition_functionality_text_view);
        foodNutritionFunctionalityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashboard.this,FoodActivity_Recycler.class));
            }
        });


    }

    void showMenu(){
        PopupMenu popupMenu=new PopupMenu(dashboard.this,menuBtn2);
        popupMenu.getMenu().add("Account");
        popupMenu.getMenu().add("Home");
        popupMenu.getMenu().add("About");
        popupMenu.getMenu().add("Privacy Policy");
        popupMenu.getMenu().add("Help and Feedback");
        popupMenu.getMenu().add("Settings");
        popupMenu.getMenu().add("Logout");
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(dashboard.this,login2_SignUpDone.class));
                    finish();
                    return true;

                }else if (menuItem.getTitle()=="Account"){
                    startActivity(new Intent(dashboard.this,account_changes.class));
                }
                return false;
            }

        });



    }

    public void bmifuntionality(View view) {
        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    public void notesfunctionality(View view){
        Intent intent= new Intent(getApplicationContext(),Notes.class);
        startActivity(intent);
    }
}