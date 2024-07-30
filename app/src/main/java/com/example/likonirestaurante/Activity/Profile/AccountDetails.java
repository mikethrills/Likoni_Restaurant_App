package com.example.likonirestaurante.Activity.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.likonirestaurante.Activity.Account;
import com.example.likonirestaurante.Domain.UserDomain;
import com.example.likonirestaurante.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class AccountDetails extends AppCompatActivity {

    private static final int GALLERY_CODE = 1;
    private static final String TAG = "AccountDetails";

    private ImageView ProfilePic, Back, EditProfilePic;
    private TextView firstName, lastName, mail, PhoneNumber;
    private Button update;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    private Uri imageUri;
    private String PhoneNo;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_account_details);

        initializeUIComponents();
        retrievePhoneNumberFromIntent();
        setButtonListeners();

        sharedPreferences = getSharedPreferences("LoginCredentials", MODE_PRIVATE);
        getAccountDetails();
    }

    private void initializeUIComponents() {
        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        mail = findViewById(R.id.mail);
        Back = findViewById(R.id.backbtn);
        update = findViewById(R.id.Update);
        PhoneNumber = findViewById(R.id.PhoneNo);
        ProfilePic = findViewById(R.id.profilepic);
        EditProfilePic = findViewById(R.id.edit_profilepic); // Add this ImageView in your XML
        progressBar = findViewById(R.id.progressBar);
    }

    private void retrievePhoneNumberFromIntent() {
        PhoneNo = getIntent().getStringExtra("PhoneNo");
        if (PhoneNo != null) {
            PhoneNumber.setText(PhoneNo);
        }
    }

    private void getAccountDetails() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users");

        databaseReference.child(PhoneNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    updateUIWithRetrievedData(dataSnapshot);
                } else {
                    // Handle case where user data doesn't exist
                    clearUI();
                    showToast("User data not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showToast("Failed to retrieve account details");
                Log.e(TAG, "DatabaseError: " + error.getMessage());
            }
        });
    }

    private void updateUIWithRetrievedData(DataSnapshot dataSnapshot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("initialFirstName", dataSnapshot.child("firstName").getValue(String.class));
        editor.putString("initialLastName", dataSnapshot.child("lastName").getValue(String.class));
        editor.putString("initialEmail", dataSnapshot.child("email").getValue(String.class));
        editor.apply();

        firstName.setText(dataSnapshot.child("firstName").getValue(String.class));
        lastName.setText(dataSnapshot.child("lastName").getValue(String.class));
        mail.setText(dataSnapshot.child("email").getValue(String.class));
        PhoneNumber.setText(PhoneNo); // Update phone number (if changed)

        String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(ProfilePic, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "Profile picture loaded successfully");
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {
                    Log.e(TAG, "Failed to load profile picture", e);
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            // Clear image if no imageUrl is available
            ProfilePic.setImageResource(R.drawable.add_image); // Placeholder image
            progressBar.setVisibility(View.GONE);
        }

        // Store imageUrl in tag for ProfilePic
        ProfilePic.setTag(imageUrl);
    }

    private void clearUI() {
        firstName.setText("");
        lastName.setText("");
        mail.setText("");
        PhoneNumber.setText("");
        ProfilePic.setImageResource(R.drawable.add_image); // Placeholder image
        progressBar.setVisibility(View.GONE);
    }

    private void setButtonListeners() {
        ProfilePic.setOnClickListener(view -> {
            String imageUrl = (String) ProfilePic.getTag(); // Retrieve imageUrl from tag
            if (imageUrl != null) {
                Intent intent = new Intent(AccountDetails.this, FullScreenImageActivity.class);
                intent.putExtra("imageUrl", imageUrl);
                startActivity(intent);
            }
        });

        EditProfilePic.setOnClickListener(view -> pickImage());

        update.setOnClickListener(view -> {
            if (validateInputs()) {
                updateProfilePicture();
            }
        });

        Back.setOnClickListener(view -> {
            Intent intent = new Intent(AccountDetails.this, Account.class);
            intent.putExtra("PhoneNo", PhoneNo);
            startActivity(intent);
        });
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            ProfilePic.setImageURI(imageUri);
            ProfilePic.setTag(null); // Clear imageUrl tag when imageUri is set
            showToast("Profile picture selected");
        }
    }

    private boolean validateInputs() {
        String FirstName = firstName.getText().toString().trim();
        String LastName = lastName.getText().toString().trim();
        String Email = mail.getText().toString().trim();
        String Number = PhoneNumber.getText().toString().trim();

        if (TextUtils.isEmpty(FirstName) || TextUtils.isEmpty(LastName) || TextUtils.isEmpty(Email) || TextUtils.isEmpty(Number)) {
            showToast("All fields are required");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            showToast("Invalid email address");
            return false;
        }

        if (!Patterns.PHONE.matcher(Number).matches()) {
            showToast("Invalid phone number");
            return false;
        }

        return true;
    }

    private void updateProfilePicture() {
        if (imageUri != null) {
            uploadImageToFirebase();
        } else {
            updateProfileInDatabase(null);
        }
    }

    private void uploadImageToFirebase() {
        String FirstName = firstName.getText().toString().trim();
        String LastName = lastName.getText().toString().trim();
        String Name = FirstName + " " + LastName;
        StorageReference filepath = FirebaseStorage.getInstance().getReference("Profiles").child(Name + "_" + System.currentTimeMillis());

        progressBar.setVisibility(View.VISIBLE);

        filepath.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> filepath.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    Log.d(TAG, "Uploaded image URL: " + imageUrl);
                    updateProfileInDatabase(imageUrl);
                }))
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    showToast("Failed to upload profile picture");
                    Log.e(TAG, "Upload failed: " + e.getMessage());
                });
    }

    private void updateProfileInDatabase(String imageUrl) {
        String FirstName = firstName.getText().toString().trim();
        String LastName = lastName.getText().toString().trim();
        String Email = mail.getText().toString().trim();
        String Number = PhoneNumber.getText().toString().trim();

        // Check if any of the credentials have changed
        boolean credentialsChanged = !FirstName.equals(sharedPreferences.getString("initialFirstName", ""))
                || !LastName.equals(sharedPreferences.getString("initialLastName", ""))
                || !Email.equals(sharedPreferences.getString("initialEmail", ""))
                || !Number.equals(sharedPreferences.getString("initialPhoneNumber", ""));

        if (credentialsChanged) {
            UserDomain userDomain = new UserDomain(FirstName, LastName, Email, Number, imageUrl);

            databaseReference.child(Number)
                    .setValue(userDomain)
                    .addOnSuccessListener(aVoid -> {
                        progressBar.setVisibility(View.GONE);
                        showToast("Profile updated successfully");
                        // Save updated credentials
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("initialFirstName", FirstName);
                        editor.putString("initialLastName", LastName);
                        editor.putString("initialEmail", Email);
                        editor.putString("initialPhoneNumber", Number);
                        editor.apply();
                    })
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.GONE);
                        showToast("Failed to update profile");
                        Log.e(TAG, "Profile update failed: " + e.getMessage());
                    });
        } else {
            progressBar.setVisibility(View.GONE);
            showToast("No changes detected in profile");
        }
    }

    private void showToast(String message) {
        Toast.makeText(AccountDetails.this, message, Toast.LENGTH_SHORT).show();
    }
}
