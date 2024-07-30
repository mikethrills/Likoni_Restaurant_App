package com.example.likonirestaurante.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.likonirestaurante.Activity.Profile.AccountDetails;
import com.example.likonirestaurante.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Account extends AppCompatActivity {

    private static final String PHONE_ID = "phoneno_prefs";
    private static final String PHONE_NUMBER_KEY = "PhoneNumber";
    private static final String PROFILE_PROMPT_SHOWN = "profile_prompt_shown";

    private CardView profileCardView, walletCardView, ordersCardView, logoutCardView;
    private TextView profileNameTextView, profileName2TextView;
    private ImageView profilePictureImageView;
    private ProgressBar progressBar;

    private SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        initializeUIComponents();
        retrieveUserIdFromIntent();
        setupBottomNavigationView();
        fetchAccountDetails();
        setupCardViewListeners();
    }

    private void initializeUIComponents() {
        sharedPreferences = getSharedPreferences(PHONE_ID, MODE_PRIVATE);

        progressBar = findViewById(R.id.progressBar2);
        profileCardView = findViewById(R.id.ProfilePage);
        walletCardView = findViewById(R.id.Wallet);
        ordersCardView = findViewById(R.id.OrderPage);
        logoutCardView = findViewById(R.id.logout);
        profileNameTextView = findViewById(R.id.ProfileName);
        profileName2TextView = findViewById(R.id.profileName2);
        profilePictureImageView = findViewById(R.id.ProfilePicture);
    }

    private void retrieveUserIdFromIntent() {
        userId = getIntent().getStringExtra("PhoneNo");
        if (userId == null) {
            userId = sharedPreferences.getString(PHONE_NUMBER_KEY, "Login With Phone Number");
        }
    }

    private void setupBottomNavigationView() {
        findViewById(R.id.homeBtn).setOnClickListener(view -> navigateToActivity(MainMenu.class));
        findViewById(R.id.cartBtn).setOnClickListener(view -> navigateToActivity(Cart.class));
        findViewById(R.id.ProfileBtn).setOnClickListener(view -> navigateToActivity(AccountDetails.class));
        findViewById(R.id.SupportBtn).setOnClickListener(view -> navigateToActivity(Support.class));
        findViewById(R.id.TrackOrderBtn).setOnClickListener(view -> navigateToActivity(Purchases.class));
    }

    private void fetchAccountDetails() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String phoneNumber = userSnapshot.child("phoneNumber").getValue(String.class);
                    if (phoneNumber != null && phoneNumber.equals(userId)) {
                        updateProfileDetails(userSnapshot);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }

    private void updateProfileDetails(DataSnapshot userSnapshot) {
        String firstName = userSnapshot.child("firstName").getValue(String.class);
        String lastName = userSnapshot.child("lastName").getValue(String.class);
        String imageUrl = userSnapshot.child("imageUrl").getValue(String.class);

        profileNameTextView.setText(firstName);
        profileName2TextView.setText(lastName);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(profilePictureImageView);
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            if (!sharedPreferences.getBoolean(PROFILE_PROMPT_SHOWN, false)) {
                showProfilePicPrompt();
            } else {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    private void showProfilePicPrompt() {
        new AlertDialog.Builder(this)
                .setTitle("Set Profile")
                .setMessage("Go to Account Details to Set your Profile")
                .setPositiveButton("Set Profile", (dialog, which) -> {
                    navigateToActivity(AccountDetails.class);
                    sharedPreferences.edit().putBoolean(PROFILE_PROMPT_SHOWN, true).apply();
                })
                .setNegativeButton("Later", (dialog, which) -> {
                    dialog.dismiss();
                    sharedPreferences.edit().putBoolean(PROFILE_PROMPT_SHOWN, true).apply();
                    progressBar.setVisibility(View.GONE);
                })
                .show();
    }

    private void setupCardViewListeners() {
        profileCardView.setOnClickListener(view -> navigateToActivity(AccountDetails.class));
        walletCardView.setOnClickListener(view -> navigateToActivity(com.example.likonirestaurante.Activity.Profile.Wallet.class));
        ordersCardView.setOnClickListener(view -> navigateToActivity(Purchases.class));
        logoutCardView.setOnClickListener(view -> showLogoutPrompt());
    }

    private void showLogoutPrompt() {
        new AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setMessage("You are going to be logged out")
                .setPositiveButton("Yes", (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();
                    sharedPreferences.edit().clear().apply();
                    startActivity(new Intent(Account.this, Get_Started.class));
                    finish();
                })
                .setNegativeButton("No, Stay Logged In", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(Account.this, activityClass);
        intent.putExtra("PhoneNo", userId);
        startActivity(intent);
    }
}
