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

import com.example.likonirestaurante.Activity.Cartegories.Heavy_Meals;

import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.R;
import com.example.likonirestaurante.Adapter.MenuAdapter;
import java.util.ArrayList;

public class RiceSideDish extends AppCompatActivity {
    private static final String MESSAGE_ID = "phoneno_prefs";
    RecyclerView.Adapter adapter;
    RecyclerView recyclerViewRiceMeals;
    private String Id;
    ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_rice_side_dish);
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
                Intent intent = new Intent(getApplicationContext(), Heavy_Meals.class);
                intent.putExtra("PhoneNo", Id);
                startActivity(intent);
            }
        });
    }


    private void recyclerViewRiceMeals() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewRiceMeals = findViewById(R.id.rice_side_view);
        recyclerViewRiceMeals.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> Ricemeals = new ArrayList<>();
        Ricemeals.add(new FoodDomain("Pilau","pilau","Indulge in the flavors of East Africa with our delicious Pilau. Our Pilau is a traditional Kenyan dish made with fragrant rice and a blend of spices, it's a perfect side dish to any meal.", 170.0, 4.6, 45, 65.2));
        Ricemeals.add(new FoodDomain("Rice and Minji", "rice_minji", "Try our delicious combination of Rice and Minji. Minji is a popular Kenyan dish made with ground meat and spices, it's a great source of protein and it will be served with rice.", 250.0, 4.8, 20, 600.0));
        Ricemeals.add(new FoodDomain("Rice and Beans", "rice_beans", "Indulge in the flavors of East Africa with our Rice and Beans. Our beans are cooked with a blend of spices and served with rice.", 250.0, 4.7, 25, 600.0));
        Ricemeals.add(new FoodDomain("Rice and Green grams", "rice_ndegu", "Experience the taste of rice and green grams, a traditional Kenyan dish made with green grams and served with rice.", 200.0, 4.6, 20, 500.0));
        Ricemeals.add(new FoodDomain("Rice and Beef", "rice_beef", "Try our delicious combination of Rice and Beef. Our beef is cooked with a blend of spices and served with rice.", 250.0, 4.8, 20, 600.0));
        Ricemeals.add(new FoodDomain("Rice and Matumbo", "rice_matumbo", "Try our unique and delicious combination of Rice and Matumbo. Matumbo is a traditional delicacy made from the intestines of cows, and it's a great source of protein, it will be served with rice.", 250.0, 4.7, 25, 600.0));
        Ricemeals.add(new FoodDomain("Rice and Chicken", "rice_chicken", "Enjoy a hearty meal with our rice and chicken combo. We serve fluffy and fragrant rice, paired with succulent chicken cooked to perfection. The combo comes with a generous portion of rice and 2 pieces of chicken.", 350.0, 4.5, 20, 600.0));



        adapter=new MenuAdapter(this,Ricemeals);
        recyclerViewRiceMeals.setAdapter(adapter);
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