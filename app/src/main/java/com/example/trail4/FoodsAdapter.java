package com.example.trail4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Food> list;

    public FoodsAdapter(Context context, ArrayList<Food> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_food_item,parent,false);
        return new FoodsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Food food = list.get(position);
        holder.foodTitleTextView.setText(food.getFood());
        holder.caloriesTextView.setText("CALORIES: "+food.getCalories());
        holder.carbohydratesTextView.setText("CARBS: "+food.getCarbohydrates());
        holder.proteinTextView.setText("PROTEIN: "+food.getProtein());
        holder.fatTextView.setText("FAT: "+food.getFat());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView foodTitleTextView,carbohydratesTextView,proteinTextView,fatTextView,caloriesTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            foodTitleTextView=itemView.findViewById(R.id.food_title_text_view);
            carbohydratesTextView=itemView.findViewById(R.id.carbohydrates_text_view);
            proteinTextView=itemView.findViewById(R.id.protein_text_view);
            fatTextView=itemView.findViewById(R.id.fat_text_view);
            caloriesTextView=itemView.findViewById(R.id.calories_text_view);
        }
    }

}
