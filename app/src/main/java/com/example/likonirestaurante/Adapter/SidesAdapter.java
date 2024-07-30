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
import com.example.likonirestaurante.Activity.SubCategories.ChapatiSideDish;
import com.example.likonirestaurante.Activity.SubCategories.PastaSideDish;
import com.example.likonirestaurante.Activity.SubCategories.RiceSideDish;
import com.example.likonirestaurante.Activity.SubCategories.UgaliSideDish;
import com.example.likonirestaurante.Domain.CategoryDomain;
import com.example.likonirestaurante.R;

import java.util.ArrayList;

public class SidesAdapter extends RecyclerView.Adapter<SidesAdapter.ViewHolder> {
    ArrayList<CategoryDomain> SideDish;


    public SidesAdapter(ArrayList<CategoryDomain> SideDish) {
        this.SideDish = SideDish;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_sides, parent,false );

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.CategoryName.setText(SideDish.get(position).getTitle());

        String picUrl="";
        switch (position){
            case 0: {
                picUrl="rice";
                break;
            }

            case 1: {
                picUrl="ugali";
                break;
            }

            case 2: {
                picUrl="chapati";
                break;
            }

            case 3: {
                picUrl="pasta";
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
                if(SideDish.get(position).getTitle().equalsIgnoreCase("Rice")){
                    Intent intent = new Intent(holder.itemView.getContext(), RiceSideDish.class);
                    intent.putExtra("title", SideDish.get(position).getTitle());
                    holder.itemView.getContext().startActivity(intent);

                }
                else if(SideDish.get(position).getTitle().equalsIgnoreCase("Ugali")){
                    Intent intent= new Intent(holder.itemView.getContext(), UgaliSideDish.class);
                    intent.putExtra("title", SideDish.get(position).getTitle());
                    holder.itemView.getContext().startActivity(intent);
                }
                else if(SideDish.get(position).getTitle().equalsIgnoreCase("Chapati")){
                    Intent intent= new Intent(holder.itemView.getContext(), ChapatiSideDish.class);
                    intent.putExtra("title", SideDish.get(position).getTitle());
                    holder.itemView.getContext().startActivity(intent);
                }
                else if(SideDish.get(position).getTitle().equalsIgnoreCase("Pasta")){
                    Intent intent= new Intent(holder.itemView.getContext(), PastaSideDish.class);
                    intent.putExtra("title", SideDish.get(position).getTitle());
                    holder.itemView.getContext().startActivity(intent);
                }

            }

        });

    }


    @Override
    public int getItemCount() {return SideDish.size();}

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