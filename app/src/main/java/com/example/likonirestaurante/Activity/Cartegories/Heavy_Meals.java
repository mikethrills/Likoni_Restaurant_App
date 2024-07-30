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
import com.example.likonirestaurante.Adapter.MenuAdapter;
import com.example.likonirestaurante.Adapter.SidesAdapter;
import com.example.likonirestaurante.Domain.CategoryDomain;
import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Heavy_Meals extends AppCompatActivity {
    private static final String MESSAGE_ID = "phoneno_prefs";
    RecyclerView.Adapter adapter, adapter2;
    RecyclerView recyclerViewHeavyMeals, recyclerViewSides;
    private CategoryDomain object;
    private String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_heavy_meals);
        
        getIntentBundle();
        recyclerViewHeavyMeals();
        recyclerViewSides();
        getIncomingIntent();
        bottomNavigationView();
    }

    private void getIntentBundle() {
//Get Phone Number from Shared Preferences
        SharedPreferences getSharedPrefs = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        Id = getSharedPrefs.getString("PhoneNumber", "Login With Phone Number");    }

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
                Intent intent = new Intent(Heavy_Meals.this, MainMenu.class);
                intent.putExtra("Phone",Id);
                startActivity(intent);
            }
        });

//Button Response for Profile button
        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Heavy_Meals.this, Account.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Cart Button
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Heavy_Meals.this, Cart.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Support Button
        SupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Heavy_Meals.this, Support.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for TrackOrder Button
        TrackOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Heavy_Meals.this, Purchases.class);
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


    private void recyclerViewSides() {

        recyclerViewSides=findViewById(R.id.DrinksCartegory);


//Adding items from the Category Domain by ArrayListing
        ArrayList <CategoryDomain> SideDish = new ArrayList<>();
        SideDish.add(new CategoryDomain("Rice",""));
        SideDish.add(new CategoryDomain("Ugali",""));
        SideDish.add(new CategoryDomain("Chapati",""));
        SideDish.add(new CategoryDomain("Pasta",""));

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL);
        recyclerViewSides.setLayoutManager(staggeredGridLayoutManager);

//Connecting the CategoryAdapter to the activity
        adapter2=new SidesAdapter(SideDish);
        recyclerViewSides.setAdapter(adapter2);


    }


    private void recyclerViewHeavyMeals() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewHeavyMeals = findViewById(R.id.HeavyMealsView);
        recyclerViewHeavyMeals.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> HeavyMeals = new ArrayList<>();

        HeavyMeals.add(new FoodDomain("Pilau","pilau","Indulge in the flavors of East Africa with our delicious Pilau. Our Pilau is a traditional Kenyan dish made with fragrant rice and a blend of spices, it's a perfect side dish to any meal.", 170.0, 4.6, 45, 65.2));
        HeavyMeals.add(new FoodDomain("Rice and Minji", "rice_minji", "Try our delicious combination of Rice and Minji. Minji is a popular Kenyan dish made with ground meat and spices, it's a great source of protein and it will be served with rice.", 250.0, 4.8, 20, 600.0));
        HeavyMeals.add(new FoodDomain("Rice and Beans", "rice_beans", "Indulge in the flavors of East Africa with our Rice and Beans. Our beans are cooked with a blend of spices and served with rice.", 250.0, 4.7, 25, 600.0));
        HeavyMeals.add(new FoodDomain("Rice and Green grams", "rice_ndegu", "Experience the taste of rice and green grams, a traditional Kenyan dish made with green grams and served with rice.", 200.0, 4.6, 20, 500.0));
        HeavyMeals.add(new FoodDomain("Rice and Beef", "rice_beef", "Try our delicious combination of Rice and Beef. Our beef is cooked with a blend of spices and served with rice.", 250.0, 4.8, 20, 600.0));
        HeavyMeals.add(new FoodDomain("Rice and Matumbo", "rice_matumbo", "Try our unique and delicious combination of Rice and Matumbo. Matumbo is a traditional delicacy made from the intestines of cows, and it's a great source of protein, it will be served with rice.", 250.0, 4.7, 25, 600.0));
        HeavyMeals.add(new FoodDomain("Rice and Chicken", "rice_chicken", "Enjoy a hearty meal with our rice and chicken combo. We serve fluffy and fragrant rice, paired with succulent chicken cooked to perfection. The combo comes with a generous portion of rice and 2 pieces of chicken.", 350.0, 4.5, 20, 600.0));
        HeavyMeals.add(new FoodDomain("Chapati and Minji", "chapati_minji", "Try our delicious combination of Chapati and Minji. Minji is a popular Kenyan dish made with ground meat and spices, it's a great source of protein and it will be served with Chapati.", 250.0, 4.8, 20, 600.0));
        HeavyMeals.add(new FoodDomain("Chapati and Beans", "chapati_beans", "Indulge in the flavors of East Africa with our Chapati and Beans. Our beans are cooked with a blend of spices and served with Chapati, a traditional Kenyan bread.", 250.0, 4.7, 25, 600.0));
        HeavyMeals.add(new FoodDomain("Chapati and Green grams", "chapati_ndengu", "Experience the taste of chapati and green grams, a traditional Kenyan dish made with green grams and served with chapati, a traditional Kenyan bread.", 200.0, 4.6, 20, 500.0));
        HeavyMeals.add(new FoodDomain("Chapati and Beef", "chapati_beef", "Try our delicious combination of Chapati and Beef. Our beef is cooked with a blend of spices and served with chapati, a traditional Kenyan bread.", 250.0, 4.8, 20, 600.0));
        HeavyMeals.add(new FoodDomain("Ugali and Beef","ugali_and_beef","The famous Kenyan beef stew is a go-to when it comes to throwing down meals in the kitchen, ‘Ugali na Nyama’ being our absolute fave! It will be served with Kales as the greens",230.0,4.6,12,90.11));
        HeavyMeals.add(new FoodDomain("Ugali and Matumbo","ugali_matumbo","Matumbo an often neglected (and even loathed) meal can sometimes be the simplect and most full-filling food. order it accompanied by Ugali and Kales served as vegetables",230.0,4.6,12,90.11));
        HeavyMeals.add(new FoodDomain("Ugali and Mayai","ugali_mayai","Ugali Mayai is basically a spicy scrambled egg stew, that is served with Ugali, a common Kenyan maize flour starch. ",230.0,4.6,12,90.11));
        HeavyMeals.add(new FoodDomain("Ugali and Omena","ugali_omena","Omena is a Kenyan meal enjoyed by most people from the lakeside or coastal region. It is a go to meal to fill the feeling of eating a fish. Accompanied with Ugali and Kales served as vegetables  ",230.0,4.6,12,90.11));
        HeavyMeals.add(new FoodDomain("Pasta and Meat Balls", "pasta_meatball", "Indulge in the classic combination of pasta and meatballs. Our pasta is cooked al dente and tossed in a rich and flavorful sauce, paired with tender and juicy meatballs. The combo comes with a generous portion of pasta and 3 meatballs.", 400.0, 4.9, 25, 750.0));
        HeavyMeals.add(new FoodDomain("Pasta and Beef","pasta_beef","Indulge in the classic combination of pasta and beef. Our pasta is cooked al dente and tossed in a rich and flavorful meat sauce, paired with tender and juicy beef. The combo comes with a generous portion of pasta and a substantial serving of beef.", 400.0, 4.9, 25, 800.0));

        Random r = new Random();
        Collections.shuffle(HeavyMeals, r);

        for(FoodDomain meal : HeavyMeals) {
            Log.d("Shuffled Heavy Meals", "Name: " + meal.getTitle() + " Description: " + meal.getDescription());
        }

        adapter=new MenuAdapter(this, HeavyMeals);
    recyclerViewHeavyMeals.setAdapter(adapter);

    }

}