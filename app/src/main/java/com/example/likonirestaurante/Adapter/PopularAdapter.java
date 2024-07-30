package com.example.likonirestaurante.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.likonirestaurante.Activity.FoodDetails;
import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.Helper.ManagementCart;
import com.example.likonirestaurante.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    private final ArrayList<FoodDomain> popularDomains;


    public PopularAdapter(ArrayList<FoodDomain> popularDomains) {
        this.popularDomains = popularDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Setting up locale for Kenya currency
        Locale locale = new Locale("en", "KE");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        // Extracting data and binding to views
        FoodDomain foodItem = popularDomains.get(position);
        holder.title.setText(foodItem.getTitle());
        holder.fee.setText(numberFormat.format(foodItem.getFee()));

        // Loading image using Glide
        int drawableResourceId = holder.itemView.getContext().getResources()
                .getIdentifier(foodItem.getPicture(), "drawable",
                        holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId).into(holder.picture);

        // Setting up click listeners
        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), FoodDetails.class);
            intent.putExtra("object", foodItem);
            holder.itemView.getContext().startActivity(intent);
        });

        holder.AddBtn.setOnClickListener(view -> {
            ManagementCart managementCart = new ManagementCart(holder.itemView.getContext());
            int numberItem = 1;
            foodItem.setNumberInCart(numberItem);
            managementCart.insertFood(foodItem);

            Toast.makeText(holder.itemView.getContext(),"item_added_to_cart", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return popularDomains.size();
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView picture;
        TextView fee;
        ImageView AddBtn;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initializing views
            title = itemView.findViewById(R.id.PopularName);
            picture = itemView.findViewById(R.id.PopularImage);
            fee = itemView.findViewById(R.id.PriceTag);
            AddBtn = itemView.findViewById(R.id.AddBtn);
            mainLayout = itemView.findViewById(R.id.mainlayout2);
        }
    }
}
