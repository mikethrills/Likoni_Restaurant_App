package com.example.likonirestaurante.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.likonirestaurante.Activity.Receipt;
import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.R;

import java.util.ArrayList;

public class BoughtgoodsAdapter extends RecyclerView.Adapter<BoughtgoodsAdapter.ViewHolder> {
    private ArrayList<FoodDomain> mReceiptItems;

    public BoughtgoodsAdapter(Receipt receipt, ArrayList<FoodDomain> receiptItems) {
        mReceiptItems = receiptItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_boughtgoods, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        FoodDomain food = mReceiptItems.get(position);
        holder.mItemName.setText(food.getTitle());
        holder.mItemQuantity.setText(String.valueOf(food.getNumberInCart()) + " X");
        holder.mItemPrice.setText("KES "+ String.valueOf(food.getFee()));

    }

    @Override
    public int getItemCount() {
        return mReceiptItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mItemName, mItemQuantity, mItemPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemName = itemView.findViewById(R.id.fooditem);
            mItemQuantity = itemView.findViewById(R.id.itemcount);
            mItemPrice = itemView.findViewById(R.id.foodprice);
        }
    }
}
