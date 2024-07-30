package com.example.likonirestaurante.Adapter;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.likonirestaurante.Activity.Cartegories.Breakfast;
import com.example.likonirestaurante.Activity.Cartegories.Drinks;
import com.example.likonirestaurante.Activity.Cartegories.FastFood;
import com.example.likonirestaurante.Activity.Cartegories.Heavy_Meals;
import com.example.likonirestaurante.Activity.Cartegories.Snacks;
import com.example.likonirestaurante.Domain.CategoryDomain;
import com.example.likonirestaurante.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<CategoryDomain> categoryDomains;

    public CategoryAdapter(ArrayList<CategoryDomain> categoryDomains) {
        this.categoryDomains = categoryDomains;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.CategoryName.setText(categoryDomains.get(position).getTitle());

        String picUrl = "";
        switch (position) {
            case 0: {
                picUrl = "heavy_meals";
                break;
            }

            case 1: {
                picUrl = "breakfast";
                break;
            }

            case 2: {
                picUrl = "fastfood";
                break;
            }

            case 3: {
                picUrl = "snack";
                break;
            }

            case 4: {
                picUrl = "drinks";
                break;
            }

        }
        int drawableResourceId = holder.itemView.getContext().getResources()
                .getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId).into(holder.CategoryImage);



        holder.CategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(categoryDomains.get(position).getTitle().equalsIgnoreCase("Heavy Meals")){
                Intent intent = new Intent(holder.itemView.getContext(), Heavy_Meals.class);
                intent.putExtra("title", categoryDomains.get(position).getTitle());
                holder.itemView.getContext().startActivity(intent);

            }
                else if(categoryDomains.get(position).getTitle().equalsIgnoreCase("Fast Food")){
                    Intent intent= new Intent(holder.itemView.getContext(), FastFood.class);
                    intent.putExtra("title", categoryDomains.get(position).getTitle());
                    holder.itemView.getContext().startActivity(intent);
                }
                else if(categoryDomains.get(position).getTitle().equalsIgnoreCase("breakfast")){
                    Intent intent= new Intent(holder.itemView.getContext(), Breakfast.class);
                    intent.putExtra("title", categoryDomains.get(position).getTitle());
                    holder.itemView.getContext().startActivity(intent);
                }
                else if(categoryDomains.get(position).getTitle().equalsIgnoreCase("Snacks")){
                    Intent intent= new Intent(holder.itemView.getContext(), Snacks.class);
                    intent.putExtra("title", categoryDomains.get(position).getTitle());
                    holder.itemView.getContext().startActivity(intent);
                }
                else if(categoryDomains.get(position).getTitle().equalsIgnoreCase("Drinks")){
                    Intent intent= new Intent(holder.itemView.getContext(), Drinks.class);
                    intent.putExtra("title", categoryDomains.get(position).getTitle());
                    holder.itemView.getContext().startActivity(intent);
                }
            }

        });


    }



    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }

    //Creating the variables in ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView CategoryName;
        ImageView CategoryImage;
        ConstraintLayout CategoryButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            //Declaring the variables in ViewHolder
            CategoryName = itemView.findViewById(R.id.CartName);
            CategoryImage = itemView.findViewById(R.id.CategoryImage);
            CategoryButton = itemView.findViewById(R.id.main_layout);


        }

    }
}