package com.example.likonirestaurante.Activity.Cartegories;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.likonirestaurante.Activity.Account;
import com.example.likonirestaurante.Activity.Cart;
import com.example.likonirestaurante.Activity.MainMenu;
import com.example.likonirestaurante.Activity.Purchases;
import com.example.likonirestaurante.Activity.Support;
import com.example.likonirestaurante.Adapter.MenuAdapter;
import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.R;

import java.util.ArrayList;

public class Snacks extends AppCompatActivity {
    private static final String MESSAGE_ID = "phoneno_prefs";
    RecyclerView.Adapter adapter;
    RecyclerView recyclerViewSnacks;
    private String Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_snacks);

        recyclerViewSnacks();
        getIncomingIntent();
        getIntentBundle();
        bottomNavigationView();
    }

    private void getIntentBundle(){
//Get Phone Number from Shared Preferences
        SharedPreferences getSharedPrefs = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        Id = getSharedPrefs.getString("PhoneNumber", "Login With Phone Number");
    }

    private void bottomNavigationView() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout ProfileBtn = findViewById(R.id.ProfileBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        LinearLayout SupportBtn = findViewById(R.id.SupportBtn);
        LinearLayout TrackOrderBtn = findViewById(R.id.TrackOrderBtn);




//Button Response for MainMenu button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Snacks.this, MainMenu.class);
                intent.putExtra("Phone",Id);
                startActivity(intent);
            }
        });

//Button Response for Profile button
        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Snacks.this, Account.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Cart Button
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Snacks.this, Cart.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Support Button
        SupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Snacks.this, Support.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for TrackOrder Button
        TrackOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Snacks.this, Purchases.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });



    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("title")) {
            String CategoryName = getIntent().getStringExtra("title");


            setImage(CategoryName);
        }

    }

    private void setImage(String categoryName) {

        TextView CategoryName = findViewById(R.id.CartName);
        CategoryName.setText(categoryName);

    }

    private void recyclerViewSnacks() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewSnacks = findViewById(R.id.SnacksView);
        recyclerViewSnacks.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> Snack = new ArrayList<>();
        Snack.add(new FoodDomain("Homemade Crisps", "crips", "Treat yourself to a bag of our delicious homemade crisps. Made with fresh potatoes and cooked to perfection. Choose from a variety of flavors such as salt and vinegar, barbecue, and sour cream and onion.", 70.0, 4.8, 15, 400.0));

        adapter=new MenuAdapter(this, Snack);
        recyclerViewSnacks.setAdapter(adapter);

    }
}