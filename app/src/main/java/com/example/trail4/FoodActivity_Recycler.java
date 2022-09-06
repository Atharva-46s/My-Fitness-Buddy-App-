package com.example.trail4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodActivity_Recycler extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    FoodsAdapter foodsAdapter;
    ArrayList<Food> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_food_recycler);

        recyclerView=findViewById(R.id.recycler_view_food);
        database= FirebaseDatabase.getInstance().getReference("food_nutrition");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list= new ArrayList<>();
        foodsAdapter=new FoodsAdapter(this,list);
        recyclerView.setAdapter(foodsAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    Food food = dataSnapshot.getValue(Food.class);
                    list.add(food);
                }
                foodsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}