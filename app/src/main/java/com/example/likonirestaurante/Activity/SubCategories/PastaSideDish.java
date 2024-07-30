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

public class PastaSideDish extends AppCompatActivity {
    private static final String MESSAGE_ID = "phoneno_prefs";
    RecyclerView.Adapter adapter;
    RecyclerView recyclerViewPastaMeals;
    private String Id;
    ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_pasta_side_dish);
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
        recyclerViewPastaMeals = findViewById(R.id.pasta_side_view);
        recyclerViewPastaMeals.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> PastaMeals = new ArrayList<>();
        PastaMeals.add(new FoodDomain("Pasta and Meat Balls", "pasta_meatball", "Indulge in the classic combination of pasta and meatballs. Our pasta is cooked al dente and tossed in a rich and flavorful sauce, paired with tender and juicy meatballs. The combo comes with a generous portion of pasta and 3 meatballs.", 400.0, 4.9, 25, 750.0));
        PastaMeals.add(new FoodDomain("Pasta and Beef","pasta_beef","Indulge in the classic combination of pasta and beef. Our pasta is cooked al dente and tossed in a rich and flavorful meat sauce, paired with tender and juicy beef. The combo comes with a generous portion of pasta and a substantial serving of beef.", 400.0, 4.9, 25, 800.0));


        adapter=new MenuAdapter(this,PastaMeals);
        recyclerViewPastaMeals.setAdapter(adapter);
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