package com.example.likonirestaurante.Activity.Profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.likonirestaurante.R;

public class Wallet extends AppCompatActivity {

    ImageView Back;
    private String Id;

    private static final String PREFS_NAME = "MyPrefsFile"; // Define a more descriptive name for SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_wallet);

        Back = findViewById(R.id.backbtn);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getIntentBundle();
    }

    private void getIntentBundle() {
        Id = getIntent().getStringExtra("Phone");

        // Get Phone Number from Shared Preferences
        SharedPreferences getSharedPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Id = getSharedPrefs.getString("PhoneNumber", "Login With Phone Number");

        // Optional: Use the retrieved phone number in your UI or logic
        // For example:
        // TextView phoneNumberTextView = findViewById(R.id.phone_number_text_view);
        // phoneNumberTextView.setText(Id);
    }
}
