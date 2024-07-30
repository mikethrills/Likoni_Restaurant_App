package com.example.likonirestaurante.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.likonirestaurante.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    // ViewBinding instance
    private ActivityLoginBinding binding;

    // Country codes for East African countries
    private final String[] countryCodes = {"+254", "+255", "+256", "+257", "+250", "+253"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewBinding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up country code spinner
        Spinner countryCodeSpinner = binding.countryCodeSpinner;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countryCodes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryCodeSpinner.setAdapter(adapter);

        // Set OnClickListener for Continue Button
        binding.ContinueLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String mobileNo = binding.EnterMobileNo.getText().toString().trim();
                String countryCode = countryCodeSpinner.getSelectedItem().toString().trim();

                // Validate mobile number
                if (mobileNo.isEmpty() || mobileNo.length() < 9) {
                    Toast.makeText(LoginActivity.this, "Enter a valid Mobile Number", Toast.LENGTH_SHORT).show();
                } else {
                    // Construct the full number
                    String number = countryCode + mobileNo;

                    // Intent to start OTP_Verification activity
                    Intent intent = new Intent(LoginActivity.this, OTP_Verification.class);
                    intent.putExtra("Mobile", number);
                    startActivity(intent);
                }
            }
        });
    }
}
