package com.example.likonirestaurante.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.likonirestaurante.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.HashMap;
import java.util.Map;

public class Complete_SigningUp extends AppCompatActivity {

    // Declaring variables
    EditText First_Name, Last_Name, Email, Contact;
    DatabaseReference userDatabase;
    FirebaseDatabase myDatabase;
    String PhoneNo;

    // The shared preferences file name
    private static final String PHONE_ID = "phoneno_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_signing_up);

        // Instantiate variables
        First_Name = findViewById(R.id.first_name);
        Last_Name = findViewById(R.id.last_name);
        Email = findViewById(R.id.Text_EmailAddress);
        Contact = findViewById(R.id.PhoneNo);
        Button Done = findViewById(R.id.Done);

        // Initialize Firebase Database
        myDatabase = FirebaseDatabase.getInstance();
        userDatabase = myDatabase.getReference("Users");

        // Calling getIntentBundle Method and setting data stored in PhoneNo variable as Contact which is the Phonenumber
        getIntentBundle();
        Contact.setText(PhoneNo);

        // Set OnClickListener for the Done Button
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if user already exists
                checkIfUserExists();
            }
        });
    }

    // Method for getting IntentPut Extra and Storing it on phone Memory as a SharedPreference
    private void getIntentBundle() {
        PhoneNo = getIntent().getStringExtra("PhoneNo");

        // Shared preference helps the data to be stored and can be used sometime else on the app device
        // Saving PhoneNumber in phone memory as a shared preference only our application will have access to this shared preferences
        // Accessing the SharedPreference class and connecting it to an object and call getSharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences(PHONE_ID, MODE_PRIVATE);

        // Accessing the SharedPreference editor so as to store data in it
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PhoneNumber", PhoneNo);
        editor.apply();
    }

    // Check if the user already exists
    private void checkIfUserExists() {
        userDatabase.orderByChild("phoneNumber").equalTo(PhoneNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User exists, log them in
                    logInUser();
                } else {
                    // User does not exist, create a new user
                    addUser();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                showErrorDialog("Database error: " + databaseError.getMessage());
            }
        });
    }

    // Method to log in the user
    private void logInUser() {
        Toast.makeText(this, "User already exists. Logging in.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Complete_SigningUp.this, MainMenu.class); // Redirect to the main menu or home screen
        intent.putExtra("Phone", PhoneNo);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void addUser() {
        // Variables will store the input on the form fields
        String firstName = First_Name.getText().toString().trim();
        String lastName = Last_Name.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String phoneNumber = Contact.getText().toString().trim();
        String imageUrl = ""; // Placeholder for image URL if needed

        // Check if fields are not empty
        if (!(email.isEmpty() || lastName.isEmpty() || firstName.isEmpty())) {
            // Create a map to store the user details
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("firstName", firstName);
            userMap.put("lastName", lastName);
            userMap.put("email", email);
            userMap.put("phoneNumber", phoneNumber);
            userMap.put("imageUrl", imageUrl);

            // Generate a unique key for the new user entry
            String userKey = userDatabase.push().getKey();

            // Add the user to the "Users" node in the Firebase Realtime Database
            if (userKey != null) {
                userDatabase.child(userKey).setValue(userMap)
                        .addOnSuccessListener(aVoid -> {
                            // Successfully added user
                            Toast.makeText(Complete_SigningUp.this, "Welcome " + firstName, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Complete_SigningUp.this, MainMenu.class);
                            intent.putExtra("Phone", PhoneNo);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        })
                        .addOnFailureListener(e -> {
                            // Failed to add user
                            Toast.makeText(Complete_SigningUp.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });
            } else {
                showErrorDialog("Failed to generate user key.");
            }
        } else {
            showErrorDialog("Please enter all the required fields.");
        }
    }

    // Setting an Alert Dialog For login Failure
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Sign Up Failed")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
