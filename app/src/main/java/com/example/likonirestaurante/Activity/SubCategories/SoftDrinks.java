package com.example.likonirestaurante.Activity.SubCategories;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.likonirestaurante.Activity.Cartegories.Drinks;
import com.example.likonirestaurante.Adapter.MenuAdapter;
import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.R;

import java.util.ArrayList;

public class SoftDrinks extends AppCompatActivity {
    private static final String MESSAGE_ID = "phoneno_prefs";
    RecyclerView.Adapter adapter;
    RecyclerView recyclerViewSoftDrinks;
    private String Id;
    ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_soft_drinks);
        
        getIncomingIntent();
        recyclerViewRiceMeals();
        getIntentBundle();
        bottomNavigationView();
    }

    private void getIntentBundle(){
//Get Phone Number from Shared Preferences
        SharedPreferences getSharedPrefs = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        Id = getSharedPrefs.getString("PhoneNumber", "Login With Phone Number");

    }

    private void bottomNavigationView() {
        Back = findViewById(R.id.backbtn);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Drinks.class);
                intent.putExtra("PhoneNo", Id);
                startActivity(intent);
            }
        });

    }


    private void recyclerViewRiceMeals() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewSoftDrinks = findViewById(R.id.softdrinks_side_view);
        recyclerViewSoftDrinks.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> SoftDrink = new ArrayList<>();
        SoftDrink.add(new FoodDomain("Soda 300ml Bottle", "soda", "Refresh yourself with a bottle of soda. Choose from a variety of flavors such as cola, lemon, and orange.", 40.0, 4.5, 2, 150.0));
        SoftDrink.add(new FoodDomain("Soda 500ml Bottle", "soda", "Refresh yourself with a bigger bottle of soda. Choose from a variety of flavors such as cola, lemon, and orange.", 60.0, 4.5, 2, 150.0));

        adapter=new MenuAdapter(this,SoftDrink);
        recyclerViewSoftDrinks.setAdapter(adapter);
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

}