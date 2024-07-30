package com.example.likonirestaurante.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.likonirestaurante.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // Duration of the splash screen in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.SplashTheme); // Set the splash screen theme if applicable
        setContentView(R.layout.activity_splash);

        // Use Handler to delay the transition to the next activity
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start Get_Started activity
                Intent intent = new Intent(SplashActivity.this, Get_Started.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // Finish SplashActivity so it's removed from the back stack
            }
        }, SPLASH_DURATION); // Delay in milliseconds (3 seconds)
    }
}
