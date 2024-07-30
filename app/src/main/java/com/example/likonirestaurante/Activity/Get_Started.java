package com.example.likonirestaurante.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.likonirestaurante.databinding.ActivityGetStartedBinding;

public class Get_Started extends AppCompatActivity {

    // The shared preferences file name
    private static final String PREFERENCES_FILE = "phoneno_prefs";

    // ViewBinding instance
    private ActivityGetStartedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewBinding
        binding = ActivityGetStartedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get Phone Number from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("PhoneNumber", null);

        // If a PhoneNumber is already registered, go straight to Main Menu Activity
        if (phoneNumber != null) {
            startMainActivity();
            return; // Exit the onCreate method to avoid further processing
        }

        // Set up the button click listener
        binding.Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent to start LoginActivity
                Intent intent = new Intent(Get_Started.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to start Main Menu Activity
    private void startMainActivity() {
        // Intent to start MainMenu
        Intent intent = new Intent(Get_Started.this, MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
