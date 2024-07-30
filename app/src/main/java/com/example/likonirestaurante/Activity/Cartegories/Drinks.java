package com.example.likonirestaurante.Activity.Cartegories;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.likonirestaurante.Activity.Account;
import com.example.likonirestaurante.Activity.Cart;
import com.example.likonirestaurante.Activity.MainMenu;
import com.example.likonirestaurante.Activity.Purchases;
import com.example.likonirestaurante.Activity.Support;
import com.example.likonirestaurante.Adapter.DrinksCategoryAdapter;
import com.example.likonirestaurante.Adapter.MenuAdapter;
import com.example.likonirestaurante.Domain.CategoryDomain;
import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Drinks extends AppCompatActivity {
    private static final String MESSAGE_ID = "phoneno_prefs";

    RecyclerView.Adapter adapter, adapter2;
    RecyclerView recyclerViewDrinks, DrinksCartegory;
    private String Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_drinks);


        bottomNavigationView();
        recyclerViewDrinks();
        DrinksCartegory();
        getIncomingIntent();
        getIntentBundle();
        bottomNavigationView();
    }

    private void getIntentBundle() {
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
                Intent intent = new Intent(Drinks.this, MainMenu.class);
                intent.putExtra("Phone",Id);
                startActivity(intent);
            }
        });

//Button Response for Profile button
        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Drinks.this, Account.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Cart Button
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Drinks.this, Cart.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Support Button
        SupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Drinks.this, Support.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for TrackOrder Button
        TrackOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Drinks.this, Purchases.class);
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

    private void recyclerViewDrinks() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewDrinks = findViewById(R.id.DrinksView);
        recyclerViewDrinks.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> Drinks= new ArrayList<>();
        Drinks.add(new FoodDomain("Tea", "tea", "Experience the traditional Kenyan taste of tea. Our tea is brewed with the best leaves and served with milk and sugar.", 70.0, 4.5, 5, 150.0));
        Drinks.add(new FoodDomain("Coffee", "coffee", "Wake up to the aroma of freshly brewed coffee. Our coffee is made from the best Kenyan coffee beans, served with milk and sugar.", 80.0, 4.8, 5, 200.0));
        Drinks.add(new FoodDomain("Milk", "milk", "Enjoy a glass of fresh milk, perfect for any time of the day.", 50.0, 4.5, 2, 150.0));Drinks.add(new FoodDomain("Soda 300ml Bottle", "soda", "This is a combo of Fries along with chicken", 250.0, 4.7, 12, 75.15));
        Drinks.add(new FoodDomain("Maziwa Mala", "maziwa_mala", "Refresh yourself with a glass of Maziwa Mala, a traditional Kenyan beverage made from sour milk. It's a perfect side drink to any meal.", 50.0, 4.5, 2, 150.0));
        Drinks.add(new FoodDomain("Mango Juice", "mango_juice", "Treat yourself to a glass of our delicious Mango Juice, made from fresh and sweet mangoes.", 70.0, 4.8, 5, 200.0));
        Drinks.add(new FoodDomain("Orange Juice", "orange_juice", "Enjoy a glass of fresh Orange Juice, perfect for any time of the day.", 60.0, 4.6, 2, 150.0));
        Drinks.add(new FoodDomain("Soda 300ml Bottle", "soda", "Refresh yourself with a bottle of soda. Choose from a variety of flavors such as cola, lemon, and orange.", 40.0, 4.5, 2, 150.0));
        Drinks.add(new FoodDomain("Soda 500ml Bottle", "soda", "Refresh yourself with a bigger bottle of soda. Choose from a variety of flavors such as cola, lemon, and orange.", 60.0, 4.5, 2, 150.0));

        Random r = new Random();
        Collections.shuffle(Drinks, r);

        for(FoodDomain meal : Drinks) {
            Log.d("Shuffled Drinks", "Name: " + meal.getTitle() + " Description: " + meal.getDescription());
        }

        adapter=new MenuAdapter(this, Drinks);
        recyclerViewDrinks.setAdapter(adapter);

    }

    private void DrinksCartegory() {
        DrinksCartegory=findViewById(R.id.DrinksCartegory);


//Adding items from the Category Domain by ArrayListing
        ArrayList <CategoryDomain>  CategoryDrink= new ArrayList<>();
        CategoryDrink.add(new CategoryDomain("Hot Drinks",""));
        CategoryDrink.add(new CategoryDomain("Soft Drinks",""));
        CategoryDrink.add(new CategoryDomain("Non-Alcoholic",""));

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL);
        DrinksCartegory.setLayoutManager(staggeredGridLayoutManager);

//Connecting the CategoryAdapter to the activity
        adapter2=new DrinksCategoryAdapter(CategoryDrink);
        DrinksCartegory.setAdapter(adapter2);


    }
}