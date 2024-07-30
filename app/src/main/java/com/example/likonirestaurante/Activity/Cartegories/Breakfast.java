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
import com.example.likonirestaurante.Domain.CategoryDomain;
import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.R;

import java.util.ArrayList;

public class Breakfast extends AppCompatActivity {
    private static final String MESSAGE_ID = "phoneno_prefs";
    RecyclerView.Adapter adapter;
    RecyclerView recyclerViewBreakfast;
    private CategoryDomain object;
    private String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_breakfast);


        recyclerViewBreakfast();
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
                Intent intent = new Intent(Breakfast.this, MainMenu.class);
                intent.putExtra("Phone",Id);
                startActivity(intent);
            }
        });

//Button Response for Profile button
        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Breakfast.this, Account.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Cart Button
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Breakfast.this, Cart.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Support Button
        SupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Breakfast.this, Support.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for TrackOrder Button
        TrackOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Breakfast.this, Purchases.class);
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


    private void recyclerViewBreakfast() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewBreakfast = findViewById(R.id.BreakfastView);
        recyclerViewBreakfast.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> Breakfasts = new ArrayList<>();
        Breakfasts.add(new FoodDomain("Samosa", "samosa", "Try our delicious samosas, a classic Indian snack made with a savory filling of vegetables or meat wrapped in a flaky pastry. Served with a side of chutney.", 120.0, 4.6, 15, 450.0));
        Breakfasts.add(new FoodDomain("Boiled Eggs", "mayai_boiled", "Boiled eggs are a popular breakfast dish in Kenya, often served with a side of chapati or ugali.", 150.0, 4.5, 15, 200.0));
        Breakfasts.add(new FoodDomain("Mahamri", "mahamri", "Mahamri is a traditional Swahili breakfast pastry made from dough and coconut milk, often served with a side of tea or coffee.", 100.0, 4.0, 10, 150.0));Breakfasts.add(new FoodDomain("Sausage", "sausages", "Enjoy a classic and delicious sausage cooked to perfection. Served with a side of chips or on a bun, it's the perfect meal for any time of the day.", 200.0, 4.4, 10, 600.0));
        Breakfasts.add(new FoodDomain("Smokie", "smokie", "Try our delicious smokie, a sausage wrapped in dough and deep-fried to a golden brown. It's a perfect snack or meal on the go.", 150.0, 4.8, 10, 500.0));
        Breakfasts.add(new FoodDomain("Chapati", "chapati", "Chapati is a popular flatbread made from wheat flour and water, often served with a variety of savory toppings or curries.", 50.0, 4.5, 5, 100.0));

        adapter=new MenuAdapter(this, Breakfasts);
        recyclerViewBreakfast.setAdapter(adapter);

    }
}