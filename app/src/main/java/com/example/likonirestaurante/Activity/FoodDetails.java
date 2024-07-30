package com.example.likonirestaurante.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.Helper.ManagementCart;
import com.example.likonirestaurante.R;

import java.text.NumberFormat;
import java.util.Locale;

public class FoodDetails extends AppCompatActivity {

    private TextView title, description, detailPrice,totalPriceFee, numberItemTxt, totalPriceTxt, ratingTxt, prepMinutesTxt, caloriesCountTxt;
    private ImageView minusCartBtn, plusCartBtn, detailPic;
    private Button AddToCartBar;
    private int numberItem=1;
    ManagementCart managementCart;
    private FoodDomain object;
    private String Id;

    private static final String MESSAGE_ID = "phoneno_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        managementCart = new ManagementCart(this);


        getIntentBundle();
        iniView();
        getBundle();
        bottomNavigationView();

    }


    private void getIntentBundle() {
//Get Phone Number from Shared Preferences
        SharedPreferences getSharedPrefs = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        Id = getSharedPrefs.getString("PhoneNumber", "Login With Phone Number");
    }


    private void getBundle() {
        object = (FoodDomain) getIntent(). getSerializableExtra("object");

        int drawableResourceId = this.getResources().getIdentifier(object.getPicture(), "drawable", this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(detailPic);

    Locale locale=new Locale("en", "KE");
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        title.setText(object.getTitle());
        detailPrice.setText("KES  " + object.getFee());
        description.setText(object.getDescription());
        numberItemTxt.setText(String.valueOf(numberItem));
     //   caloriesCountTxt.setText(object.getCalories() + " Calories");
        ratingTxt.setText(object.getRatings() + " ");
        prepMinutesTxt.setText(object.getTime() + " Min");
       // totalPriceTxt.setText("KES  "+object.getFee());
        totalPriceFee.setText("KES  "+Math.round(numberItem * object.getFee()));



        plusCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberItem = numberItem + 1;
                numberItemTxt.setText(String.valueOf(numberItem));
                totalPriceFee.setText("KES "+Math.round(numberItem * object.getFee()));
            }
        });

        minusCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberItem > 1) {
                    numberItem = numberItem - 1;
                }
                numberItemTxt.setText(String.valueOf(numberItem));
                totalPriceFee.setText("KES "+Math.round(numberItem * object.getFee()));
            }
        });

        AddToCartBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumberInCart(numberItem);
                managementCart.insertFood(object);

            }
        });

        ImageView imageView = findViewById(R.id.imageView4);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodDetails.this, Cart.class);
                intent.putExtra("Phone",Id);
                startActivity(intent);
            }
        });

    }


    private void iniView() {
        totalPriceFee=findViewById(R.id.TotalPriceFee);
        title=findViewById(R.id.Detail_title);
        description=findViewById(R.id.Description);
        detailPrice=findViewById(R.id.Detail_price);
        numberItemTxt=findViewById(R.id.numberItemTxt);
        minusCartBtn=findViewById(R.id.minusCartBtn);
        plusCartBtn=findViewById(R.id.plusCartBtn);
        detailPic=findViewById(R.id.detail_pic);
        AddToCartBar=findViewById(R.id.AddToCartBar);
        ratingTxt=findViewById(R.id.ratingsTxt);
        prepMinutesTxt=findViewById(R.id.prep_minutesTxt);

    //    caloriesCountTxt=findViewById(R.id.calories_countTxt);
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
                Intent intent = new Intent(FoodDetails.this, MainMenu.class);
                intent.putExtra("Phone",Id);
                startActivity(intent);
            }
        });

//Button Response for Profile button
        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodDetails.this, Account.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Cart Button
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodDetails.this, Cart.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Support Button
        SupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodDetails.this, Support.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for TrackOrder Button
        TrackOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodDetails.this, Purchases.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });



    }


}

