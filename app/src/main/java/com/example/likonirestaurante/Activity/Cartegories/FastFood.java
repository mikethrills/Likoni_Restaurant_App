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
import com.example.likonirestaurante.Adapter.PopularAdapter;
import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.R;

import java.util.ArrayList;

public class FastFood extends AppCompatActivity {
    private static final String MESSAGE_ID = "phoneno_prefs";
    RecyclerView.Adapter adapter, adapter2;
    RecyclerView recyclerViewFastFood, recyclerViewFastFoodCombo;
    private String Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_fast_food);

        recyclerViewFastFood();
        recyclerViewFastFoodCombo();
        getIncomingIntent();
        getIntentBundle();
        bottomNavigationView();
    }

    private void recyclerViewFastFoodCombo() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewFastFoodCombo = findViewById(R.id.fastfood_combo_side_view);
        recyclerViewFastFoodCombo.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain>Combo = new ArrayList<>();
        Combo.add(new FoodDomain("Fries and Chicken", "fries_and_chicken", "Our fries are crispy and golden brown, paired with tender and juicy fried chicken, a perfect combination to satisfy your cravings. Our combo comes with a generous portion of fries and 3 pieces of chicken.", 300.0, 4.8, 15, 650.0));
        Combo.add(new FoodDomain("Burger and Fries", "burger_fries", "Get a classic burger with a side of crispy fries.", 600.0, 4.9, 30, 1200.0));        Combo.add(new FoodDomain("Fries and Chicken","fries_and_chicken","This is a combo of Fries along with chicken",250.0,4.7,12,75.15));
        Combo.add(new FoodDomain("Fish and Chips", "fish_chips", "Get a crispy and golden fried fish fillet with a side of hot and fluffy chips.", 600.0, 4.8, 25, 1000.0));        Combo.add(new FoodDomain("Fries and Chicken","fries_and_chicken","This is a combo of Fries along with chicken",250.0,4.7,12,75.15));

        adapter2 = new PopularAdapter(Combo);
        recyclerViewFastFoodCombo.setAdapter(adapter2);
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
                Intent intent = new Intent(FastFood.this, MainMenu.class);
                intent.putExtra("Phone",Id);
                startActivity(intent);
            }
        });

//Button Response for Profile button
        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FastFood.this, Account.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Cart Button
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FastFood.this, Cart.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Support Button
        SupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FastFood.this, Support.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for TrackOrder Button
        TrackOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FastFood.this, Purchases.class);
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

    private void recyclerViewFastFood() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewFastFood = findViewById(R.id.fastfood_side_view);
        recyclerViewFastFood.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> FastFoods = new ArrayList<>();
        FastFoods.add(new FoodDomain("Chips Large", "chips_l", "Treat yourself to a large portion of our crispy and tasty chips. Perfect for sharing or for satisfying a big appetite.", 150.0, 4.5, 20, 600.0));
        FastFoods.add(new FoodDomain("Chips XL", "chips_xl", "Get even more of our delicious chips with our XL portion. Perfect for satisfying a big appetite or for sharing with friends.", 200.0, 4.5, 20, 800.0));
        FastFoods.add(new FoodDomain("Chips XXL", "chips_xxl", "Get the ultimate chip experience with our XXL portion. Perfect for a big group of friends or for satisfying a big appetite.", 250.0, 4.5, 20, 1000.0));
        FastFoods.add(new FoodDomain("Chips Masala", "chips_masala", "Try our delicious chips with a twist of masala. Perfectly seasoned and crispy, you won't be able to stop munching on these.", 200.0, 4.5, 20, 800.0));
        FastFoods.add(new FoodDomain("Bhajia XL", "bhajia", "Try our Bhajia XL, A crispy snack made from flour and mixed with vegetables such as onions and tomatoes.", 150.0, 4.5, 20, 600.0));
        FastFoods.add(new FoodDomain("Bhajia Extra Large", "bhajia", "Get even more of our delicious Bhajia with our Extra Large portion. Perfect for satisfying a big appetite or for sharing with friends.", 200.0, 4.5, 20, 800.0));
        FastFoods.add(new FoodDomain("Chicken Tikka Masala", "chicken_tikka_masala", "Try our delicious and creamy chicken tikka masala. Perfectly spiced and cooked to perfection, served with rice or naan.", 400.0, 4.8, 45, 900.0));

        adapter=new MenuAdapter(this, FastFoods);
        recyclerViewFastFood.setAdapter(adapter);

    }
}