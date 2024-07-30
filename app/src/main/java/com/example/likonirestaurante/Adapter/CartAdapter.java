package com.example.likonirestaurante.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.Helper.ManagementCart;
import com.example.likonirestaurante.Interface.ChangeNumberItemListener;
import com.example.likonirestaurante.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ManagementCart managementCart;
    ChangeNumberItemListener changeNumberItemListener;
    ArrayList<FoodDomain> listSelectedFood;

    public CartAdapter(ArrayList<FoodDomain> listSelectedFood, Context context, ChangeNumberItemListener changeNumberItemListener) {
        this.listSelectedFood = listSelectedFood;
        managementCart = new ManagementCart(context);
        this.changeNumberItemListener = changeNumberItemListener;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent,false );

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

// Adding Kenya to the local inbuilt class to get it's position, currency and time
        Locale locale=new Locale("en", "KE");
        NumberFormat numberFormat=NumberFormat.getCurrencyInstance(locale);

        holder.title.setText(listSelectedFood.get(position).getTitle());
        holder.eachItemTotal.setText(numberFormat.format(Math.round(listSelectedFood.get(position).getNumberInCart() * listSelectedFood.get(position).getFee())));
        holder.numberItem.setText(String.valueOf(listSelectedFood.get(position).getNumberInCart()) + " X");


    int drawableResourceId=holder.itemView.getContext().getResources()
            .getIdentifier(listSelectedFood.get(position).getPicture(),"drawable",
            holder.itemView.getContext().getPackageName());

    Glide.with(holder.itemView.getContext())
                .load(drawableResourceId).into(holder.picture);


// The "DeleteItems" method of the "managementCart" object is called, passing in the following parameters:
        //listSelectedFood - an ArrayList of selected FoodDomain objects
        //position - the position of the FoodDomain object in the ArrayList
        //ChangeNumberItemListener - an interface that has a single method, "changed".
        holder.Delete.setOnClickListener(view -> {
            managementCart.DeleteItems(listSelectedFood, position, new ChangeNumberItemListener() {
                @Override
                public void changed() {
    //After the item is removed, the "notifyDataSetChanged" method of the adapter is called to refresh the view,
        // and the "changed" method of the "changeNumberItemListener" interface is called
                    // to notify any listeners that the number of items has changed.
                    CartAdapter.this.notifyDataSetChanged();
                    changeNumberItemListener.changed();

                }
            });
        });

    holder.AddBtn.setOnClickListener(view -> managementCart.plusNumberFood(listSelectedFood, position, new ChangeNumberItemListener() {
        @Override
        public void changed() {
            CartAdapter.this.notifyDataSetChanged();
            changeNumberItemListener.changed();

        }
    }));

    holder.MinusBtn.setOnClickListener(view -> {
        managementCart.minusNumberFood(listSelectedFood, position, new ChangeNumberItemListener() {
            @Override
            public void changed() {
                CartAdapter.this.notifyDataSetChanged();
                changeNumberItemListener.changed();

            }
        });
    });



    }


    @Override
     public int getItemCount() {
        return listSelectedFood.size();
    }

    //Creating the variables in ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView picture;
        TextView numberItem;
        TextView eachItemTotal;
        ImageView AddBtn, MinusBtn, Delete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

    //Declaring the variables in ViewHolder
        title=itemView.findViewById(R.id.CartName);
        picture=itemView.findViewById(R.id.CategoryImage);
        numberItem=itemView.findViewById(R.id.numberItemTxt);
        eachItemTotal=itemView.findViewById(R.id.EachItemTotal);
        AddBtn=itemView.findViewById(R.id.plusCartBtn);
        MinusBtn=itemView.findViewById(R.id.minusCartBtn);
        Delete = itemView.findViewById(R.id.delete);

        }
    }
}