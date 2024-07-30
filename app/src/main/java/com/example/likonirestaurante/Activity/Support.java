package com.example.likonirestaurante.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.likonirestaurante.R;

public class Support extends AppCompatActivity {

    private static final int CALL_PHONE_REQUEST_CODE = 1;
    private String phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        getIntentData();
        setupBottomNavigationView();
        setupDialSupportButton();
        setupChatWhatsAppButton();
    }

    private void getIntentData() {
        phoneNo = getIntent().getStringExtra("PhoneNo");
    }

    private void setupDialSupportButton() {
        LinearLayout callButton = findViewById(R.id.ContactBtn);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCallPermissionAndDial();
            }
        });
    }

    private void checkCallPermissionAndDial() {
        if (ContextCompat.checkSelfPermission(Support.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Support.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_REQUEST_CODE);
        } else {
            dialPhoneNumber();
        }
    }

    private void dialPhoneNumber() {
        String phoneNumber = "tel:+254757410491"; // Replace with the desired phone number
        Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));
        startActivity(dialIntent);
    }

    private void setupChatWhatsAppButton() {
        LinearLayout chatButton = findViewById(R.id.ChatBtn);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWhatsAppChat();
            }
        });
    }

    private void openWhatsAppChat() {
        String phoneNumber = "+254757410491"; // Replace with the desired phone number
        String url = "https://api.whatsapp.com/send?phone=" + phoneNumber;
        Intent chatIntent = new Intent(Intent.ACTION_VIEW);
        chatIntent.setData(Uri.parse(url));
        startActivity(chatIntent);
    }

    private void setupBottomNavigationView() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profileBtn = findViewById(R.id.ProfileBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        LinearLayout trackOrderBtn = findViewById(R.id.TrackOrderBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToActivity(MainMenu.class);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToActivity(Account.class);
            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToActivity(Cart.class);
            }
        });

        trackOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToActivity(Purchases.class);
            }
        });
    }

    private void navigateToActivity(Class<?> targetActivity) {
        Intent intent = new Intent(Support.this, targetActivity);
        intent.putExtra("PhoneNo", phoneNo);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PHONE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dialPhoneNumber();
            }
        }
    }
}
