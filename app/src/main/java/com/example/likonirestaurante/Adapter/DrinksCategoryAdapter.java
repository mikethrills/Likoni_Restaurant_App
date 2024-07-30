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
import com.example.likonirestaurante.Activity.SubCategories.HotDrinks;
import com.example.likonirestaurante.Activity.SubCategories.NonAlcoholicDrinks;
import com.example.likonirestaurante.Activity.SubCategories.SoftDrinks;
import com.example.likonirestaurante.Domain.CategoryDomain;
import com.example.likonirestaurante.R;

import java.util.ArrayList;

public class DrinksCategoryAdapter extends RecyclerView.Adapter<DrinksCategoryAdapter.ViewHolder> {
    ArrayList<CategoryDomain> DrinksCategory;


    public DrinksCategoryAdapter(ArrayList<CategoryDomain> DrinksCategory) {
        this.DrinksCategory = DrinksCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_drinks, parent,false );

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.CategoryName.setText(DrinksCategory.get(position).getTitle());

        String picUrl="";
        switch (position){
            case 0: {
                picUrl="coffee";
                break;
            }

            case 1: {
                picUrl="soda";
                break;
            }

            case 2: {
                picUrl="orange_juice";
                break;
            }


        }
        int drawableResourceId=holder.itemView.getContext().getResources()
                .getIdentifier(picUrl,"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId).into(holder.CategoryImage);

        holder.CategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DrinksCategory.get(position).getTitle().equalsIgnoreCase("Hot Drinks")){
                    Intent intent = new Intent(holder.itemView.getContext(), HotDrinks.class);
                    intent.putExtra("title", DrinksCategory.get(position).getTitle());
                    holder.itemView.getContext().startActivity(intent);

                }
                else if(DrinksCategory.get(position).getTitle().equalsIgnoreCase("Soft Drinks")){
                    Intent intent= new Intent(holder.itemView.getContext(), SoftDrinks.class);
                    intent.putExtra("title", DrinksCategory.get(position).getTitle());
                    holder.itemView.getContext().startActivity(intent);
                }
                else if(DrinksCategory.get(position).getTitle().equalsIgnoreCase("Non-Alcoholic")){
                    Intent intent= new Intent(holder.itemView.getContext(), NonAlcoholicDrinks.class);
                    intent.putExtra("title", DrinksCategory.get(position).getTitle());
                    holder.itemView.getContext().startActivity(intent);
                }

            }

        });

    }


    @Override
    public int getItemCount() {return DrinksCategory.size();}

    //Creating the variables in ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView CategoryName;
        ImageView CategoryImage;
        ConstraintLayout CategoryButton;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Declaring the variables in ViewHolder
            CategoryName=itemView.findViewById(R.id.CartName);
            CategoryImage=itemView.findViewById(R.id.CategoryImage);
            CategoryButton=itemView.findViewById(R.id.main_layout);



        }
    }
}