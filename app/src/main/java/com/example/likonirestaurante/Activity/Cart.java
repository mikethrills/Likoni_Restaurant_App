package com.example.likonirestaurante.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.likonirestaurante.Activity.Permissions.LocationPermission;

import com.example.likonirestaurante.Adapter.CartAdapter;
import com.example.likonirestaurante.Helper.ManagementCart;
import com.example.likonirestaurante.Interface.ChangeNumberItemListener;
import com.example.likonirestaurante.R;

public class Cart extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView cart_view;

    private ManagementCart managementCart;

    private TextView subTotalFeeTxt;
    private TextView taxFeeTxt;
    private TextView DeliveryFeeTxt;
    private TextView TotalFeeTxt;
    private TextView emptyTxt;
    private TextView ETA, NumberInCart;
    private double tax;
    private ScrollView scrollView;
    private ConstraintLayout constraintLayout;
    private LinearLayout Bill;

    private String Id;
    private int Time;
    private double total;

    private String Amount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        managementCart = new ManagementCart(this);

        initViews();
        initList();
        calculateCard();
        getIntentBundle();
        bottomNavigationView();


    }

    private void initViews() {
        subTotalFeeTxt = findViewById(R.id.TotalFeeText);
        taxFeeTxt = findViewById(R.id.taxFeeText);
        DeliveryFeeTxt = findViewById(R.id.DeliveryFeeText);
        TotalFeeTxt = findViewById(R.id.text_total_fee);
        cart_view = findViewById(R.id.cart_view);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView);
        constraintLayout=findViewById(R.id.place_orderBtn);
        ETA = findViewById(R.id.eta2);
        Bill = findViewById(R.id.checkOUT);
        NumberInCart = findViewById(R.id.cartBtntxt);

    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cart_view.setLayoutManager(linearLayoutManager);

        adapter = new CartAdapter(managementCart.getListCart(), this, new ChangeNumberItemListener() {
            @Override
            public void changed() {
                calculateCard();
            }
        });
        cart_view.setAdapter(adapter);

        if (managementCart.getListCart().isEmpty()){

            NoitemPrompt();
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            constraintLayout.setVisibility(View.GONE);
            Bill.setVisibility(View.GONE);
        }
        else{

            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            constraintLayout.setVisibility(View.VISIBLE);
            Bill.setVisibility(View.VISIBLE);

        }
    }

    private void calculateCard() {
        double percentageTax = 0.10;
        double delivery = 100.0;

        tax = Math.round((managementCart.getTotalTax()*percentageTax)*100.0)/100.0;
        Time = (managementCart.getTotalETA());

        total = (Math.round((managementCart.getTotalFee()+ tax + delivery)*100.0)/100.0);
        double itemTotal=Math.round(managementCart.getTotalFee()*100.0)/100.0;

        Amount = String.valueOf(total);
        NumberInCart.setText("Cart: " + managementCart.getListCart().size());

        subTotalFeeTxt.setText("KES "+ itemTotal);
        taxFeeTxt.setText("KES " + tax);
        DeliveryFeeTxt.setText("KES " + delivery);
        TotalFeeTxt.setText("KES " + Amount);

        ETA.setText(Time + " Mins" );

//Button Response for TrackOrder Page Open
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Cart.this, LocationPermission.class);
                intent.putExtra("total_amount", total);
                intent.putExtra("total_time", Time);
                startActivity(intent);
            }
        });


    }


    private void getIntentBundle(){
        Id = getIntent().getStringExtra("PhoneNo");


    }

    private void NoitemPrompt(){
        new AlertDialog.Builder(this)
                .setTitle("No Item has been added to Cart")
                .setMessage("Shop with Likoni Restaurant. Continue Shopping.")
                .setPositiveButton("Main Menu",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which){
                     Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                     startActivity(intent);
                    }
                })
                .setNegativeButton("Later",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                })
                .show();
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
                Intent intent = new Intent(Cart.this, MainMenu.class);
                intent.putExtra("Phone",Id);
                startActivity(intent);
            }
        });

//Button Response for Support Button
        SupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart.this, Support.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Profile Button
        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart.this, Account.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for TrackOrder Button
        TrackOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart.this, Purchases.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });


    }
}
