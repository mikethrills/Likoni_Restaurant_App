package com.example.likonirestaurante.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.likonirestaurante.Adapter.BoughtgoodsAdapter;
import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.Domain.OrderDatabaseDomain;
import com.example.likonirestaurante.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Receipt extends AppCompatActivity {

    private static final String MESSAGE_ID = "phoneno_prefs";
    private String phoneNumber;
    private TextView OrderId, OrderDate, Mpesa, OrderStatus, amount;
    private TextView Subtotal, DeliveryFee, taxFee, Total, Message;
    private RecyclerView CartItemsView;

    private OrderDatabaseDomain object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        object = (OrderDatabaseDomain) getIntent().getSerializableExtra("object");

        // Get Phone Number from Shared Preferences
        SharedPreferences getSharedPrefs = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        phoneNumber = getSharedPrefs.getString("PhoneNumber", "Login With Phone Number");

        inviews();
        getOrderHistory(object);

        Button btnButton = findViewById(R.id.backbutton);
        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void inviews() {
        OrderId = findViewById(R.id.text_order_id);
        OrderDate = findViewById(R.id.text_order_date);
        OrderStatus = findViewById(R.id.text_order_status);
        Mpesa = findViewById(R.id.mpesaid);
        amount = findViewById(R.id.GrandTotal);
        CartItemsView = findViewById(R.id.cart_items);

        Subtotal = findViewById(R.id.TotalFeeText);
        DeliveryFee = findViewById(R.id.DeliveryFeeText);
        taxFee = findViewById(R.id.taxFeeText);
        Total = findViewById(R.id.text_total_fee);
        Message = findViewById(R.id.message);
    }

    private void getOrderHistory(OrderDatabaseDomain order) {
        OrderId.setText("Order ID: " + order.getOrderId());
        OrderDate.setText(order.getDate());
        OrderStatus.setText(order.getStatus());
        Mpesa.setText("Transaction Code: " + order.getMpesaId());

        // Reference the "Orders" node in the Firebase Realtime Database
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");

        // Query the database to get all orders with the specified orderId
        Query orderIDQuery = ordersRef.orderByChild("orderId").equalTo(order.getOrderId());
        orderIDQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<FoodDomain> cartItems = new ArrayList<>();
                    double total = 0;
                    double deliveryFee = 100.0;
                    double serviceFee = 0;

                    for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot foodSnapshot : orderSnapshot.child("cartItems").getChildren()) {
                            FoodDomain food = foodSnapshot.getValue(FoodDomain.class);
                            if (food != null) {
                                cartItems.add(food);
                                double amount = food.getFee() * food.getNumberInCart();
                                total += amount;
                            }
                        }

                        serviceFee = 0.10 * total;
                        double fee = total + deliveryFee + serviceFee;

                        Subtotal.setText("KES " + total);
                        DeliveryFee.setText("KES " + deliveryFee);
                        taxFee.setText("KES " + serviceFee);
                        Total.setText("KES " + fee);
                        amount.setText("Paid: KES " + fee);
                        Message.setText("Thanks for ordering, " + order.getName());

                        displayOrderHistory(cartItems);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseError", databaseError.getMessage());
            }
        });
    }

    private void displayOrderHistory(ArrayList<FoodDomain> receiptOrder) {
        // Create a new adapter for the RecyclerView
        BoughtgoodsAdapter adapter = new BoughtgoodsAdapter(this, receiptOrder);

        // Set the adapter for the RecyclerView
        CartItemsView.setAdapter(adapter);

        // Set the layout manager for the RecyclerView
        CartItemsView.setLayoutManager(new LinearLayoutManager(this));
    }
}