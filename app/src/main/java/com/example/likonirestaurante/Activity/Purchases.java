package com.example.likonirestaurante.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.likonirestaurante.Adapter.PurchasesAdapter;
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

public class Purchases extends AppCompatActivity {
    private static final String MESSAGE_ID = "phoneno_prefs";
    private String phoneNumber;
    private RecyclerView orderHistoryRecyclerView;
    private RecyclerView.Adapter adapter;
    private ImageView back;
    private TextView emptyText;
    private Button clearOrderHistoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_purchases);

        // Initialize views
        SharedPreferences getSharedPrefs = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        phoneNumber = getSharedPrefs.getString("PhoneNumber", "Login With Phone Number");

        orderHistoryRecyclerView = findViewById(R.id.purchases_view);
        back = findViewById(R.id.backbtn);
        emptyText = findViewById(R.id.emptyTxt2);
        clearOrderHistoryButton = findViewById(R.id.clear_order_history_button);

        // Fetch order history
        getOrderHistory(phoneNumber);

        // Set up back button click listener
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Set up clear order history button click listener
        clearOrderHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearOrderHistory(phoneNumber);
            }
        });
    }

    private void getOrderHistory(String phoneNumber) {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");
        Query phoneNumberQuery = ordersRef.orderByChild("phoneNumber").equalTo(phoneNumber);
        phoneNumberQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    emptyText.setVisibility(View.GONE);
                    ArrayList<OrderDatabaseDomain> orderHistory = new ArrayList<>();

                    for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                        Log.d("OrderSnapshot", "Order data: " + orderSnapshot.getValue());
                        try {
                            String totalAmount = orderSnapshot.child("totalAmount").getValue(String.class);
                            String orderId = orderSnapshot.child("orderId").getValue(String.class);

                            if (orderId != null) {
                                String name = orderSnapshot.child("name").getValue(String.class);
                                String phoneNumber = orderSnapshot.child("phoneNumber").getValue(String.class);
                                String mpesaId = orderSnapshot.child("mpesaID").getValue(String.class);
                                String date = orderSnapshot.child("date").getValue(String.class);
                                String timeOfOrder = orderSnapshot.child("TimeofOrder").getValue(String.class);
                                String paymentTime = orderSnapshot.child("PaymentTime").getValue(String.class);
                                String cookingTime = orderSnapshot.child("cookingTime").getValue(String.class);
                                String processingTime = orderSnapshot.child("ProcessingTime").getValue(String.class);
                                String pickUpTime = orderSnapshot.child("PickUptime").getValue(String.class);
                                String status = orderSnapshot.child("status").getValue(String.class);

                                ArrayList<FoodDomain> cartItems = new ArrayList<>();
                                for (DataSnapshot cartItemSnapshot : orderSnapshot.child("cartItems").getChildren()) {
                                    FoodDomain food = cartItemSnapshot.getValue(FoodDomain.class);
                                    Log.d("CartItemSnapshot", "Cart item data: " + food);
                                    cartItems.add(food);
                                }

                                OrderDatabaseDomain order = new OrderDatabaseDomain(name, phoneNumber, orderId, mpesaId, date, totalAmount, timeOfOrder, paymentTime, processingTime, pickUpTime, cookingTime, status, cartItems);
                                order.setStatus(status);
                                orderHistory.add(order);
                            } else {
                                Log.w("DataWarning", "Order ID is null for snapshot: " + orderSnapshot.toString());
                                Toast.makeText(Purchases.this, "Order ID is null", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("DataError", "Error processing order: " + e.getMessage(), e);
                        }
                    }
                    displayOrderHistory(orderHistory);
                } else {
                    Toast.makeText(Purchases.this, "You have no previous orders", Toast.LENGTH_SHORT).show();
                    emptyText.setVisibility(View.VISIBLE);
                    noItemPrompt();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseError", "Database error: " + databaseError.getMessage(), databaseError.toException());
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void displayOrderHistory(ArrayList<OrderDatabaseDomain> orderHistory) {
        if (adapter == null) {
            adapter = new PurchasesAdapter(this, orderHistory);
            orderHistoryRecyclerView.setAdapter(adapter);
            orderHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            adapter.notifyDataSetChanged(); // Notify adapter of data changes
        }
    }

    private void clearOrderHistory(String phoneNumber) {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");
        Query phoneNumberQuery = ordersRef.orderByChild("phoneNumber").equalTo(phoneNumber);
        phoneNumberQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                        orderSnapshot.getRef().removeValue().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(Purchases.this, "Order history cleared", Toast.LENGTH_SHORT).show();
                                emptyText.setVisibility(View.VISIBLE);
                                displayOrderHistory(new ArrayList<>()); // Clear RecyclerView
                            } else {
                                Toast.makeText(Purchases.this, "Failed to clear order history", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(Purchases.this, "No orders to clear", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseError", "Database error: " + databaseError.getMessage(), databaseError.toException());
            }
        });
    }

    private void noItemPrompt() {
        new android.app.AlertDialog.Builder(this)
                .setTitle("No Item has been Purchased")
                .setMessage("Shop with Likoni Restaurant. Continue Shopping.")
                .setPositiveButton("Main Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
