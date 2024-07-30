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
import com.example.likonirestaurante.Adapter.MenuAdapter;
import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.R;

import java.util.ArrayList;

public class UgaliSideDish extends AppCompatActivity {
    private static final String MESSAGE_ID = "phoneno_prefs";
    RecyclerView.Adapter adapter;
    RecyclerView recyclerViewUgaliMeals;
    private String Id;
    ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_ugali_side_dish);

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
        recyclerViewUgaliMeals = findViewById(R.id.ugali_side_view);
        recyclerViewUgaliMeals.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> UgaliMeals = new ArrayList<>();
        UgaliMeals.add(new FoodDomain("Ugali and Beef","ugali_and_beef","The famous Kenyan beef stew is a go-to when it comes to throwing down meals in the kitchen, ‘Ugali na Nyama’ being our absolute fave! It will be served with Kales as the greens",230.0,4.6,12,90.11));
        UgaliMeals.add(new FoodDomain("Ugali and Matumbo","ugali_matumbo","Matumbo an often neglected (and even loathed) meal can sometimes be the simplect and most full-filling food. order it accompanied by Ugali and Kales served as vegetables",230.0,4.6,12,90.11));
        UgaliMeals.add(new FoodDomain("Ugali and Mayai","ugali_mayai","Ugali Mayai is basically a spicy scrambled egg stew, that is served with Ugali, a common Kenyan maize flour starch. ",230.0,4.6,12,90.11));
        UgaliMeals.add(new FoodDomain("Ugali and Omena","ugali_omena","Omena is a Kenyan meal enjoyed by most people from the lakeside or coastal region. It is a go to meal to fill the feeling of eating a fish. Accompanied with Ugali and Kales served as vegetables  ",230.0,4.6,12,90.11));


        adapter=new MenuAdapter(this, UgaliMeals);
        recyclerViewUgaliMeals.setAdapter(adapter);
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