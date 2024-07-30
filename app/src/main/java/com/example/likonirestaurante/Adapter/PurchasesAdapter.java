package com.example.likonirestaurante.Adapter;


import android.content.Context;
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
import com.example.likonirestaurante.Activity.TrackOrder;
import com.example.likonirestaurante.Domain.OrderDatabaseDomain;
import com.example.likonirestaurante.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PurchasesAdapter extends RecyclerView.Adapter<PurchasesAdapter.OrderViewHolder> {

    private ArrayList<OrderDatabaseDomain> orderHistory;
    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference orderRef;


    public PurchasesAdapter(Context context, ArrayList<OrderDatabaseDomain> orderHistory) {
        this.context = context;
        this.orderHistory = orderHistory;
        database = FirebaseDatabase.getInstance();
        orderRef = database.getReference("Orders");

    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_purchases, parent, false);

        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        int drawableResourceId = holder.itemView.getContext().getResources()
                .getIdentifier("cart_banner", "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId).into(holder.Image);


        OrderDatabaseDomain order = orderHistory.get(position);
        int numberOfItems = order.getCartItems().size();

        holder.MpesaID.setText("M-pesa ID: " + order.getMpesaId());
        holder.OrderDate.setText("Order Date: " + order.getDate());
        holder.TotalAmount.setText(". " + order.getTotalAmount());
        holder.ItemList.setText("Item List: " + numberOfItems);
        holder.Status.setText(order.getStatus());
        holder.OrderID.setText("Order ID: " + order.getOrderId());


        holder.OrderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), TrackOrder.class);
                intent.putExtra("object",order);
                holder.itemView.getContext().startActivity(intent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return orderHistory.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView MpesaID, OrderDate, TotalAmount, ItemList, Status, OrderID;
        public ImageView Image;
        ConstraintLayout OrderLayout;



        public OrderViewHolder(View view) {
            super(view);

            OrderID = view.findViewById(R.id.orderid);
            MpesaID = view.findViewById(R.id.transaction_code);
            OrderDate = view.findViewById(R.id.order_date);
            TotalAmount = view.findViewById(R.id.order_total);
            ItemList = view.findViewById(R.id.no_incart);
            Status = view.findViewById(R.id.order_status);
            Image = view.findViewById(R.id.puchasesImage);
            OrderLayout = view.findViewById(R.id.Order);



        }
    }
}
