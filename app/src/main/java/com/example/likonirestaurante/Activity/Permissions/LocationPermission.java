package com.example.likonirestaurante.Activity.Permissions;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.likonirestaurante.Activity.TrackOrder;
import com.example.likonirestaurante.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class LocationPermission extends AppCompatActivity {

    // Declaring Variable
    Button Allow;
    private static final String PREFS_NAME = "phoneno_prefs"; // Changed to a more descriptive name

    private String Id;
    double total;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_permission);

        // Instantiating the variable
        Allow = findViewById(R.id.Allow);

        getIncomingIntent();
        checkPermissionAndStartActivity();
        setupGrantButton();
    }

    private void getIncomingIntent() {
        // Get Phone Number from Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Id = sharedPreferences.getString("PhoneNumber", "Login With Phone Number");

        // Get intent from Cart to pass amount to Track Order
        total = getIntent().getDoubleExtra("total_amount", 0);
        time = getIntent().getIntExtra("total_time", 0);
    }

    private void checkPermissionAndStartActivity() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startTrackOrderActivity();
        }
    }

    private void setupGrantButton() {
        Allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(LocationPermission.this)
                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                startTrackOrderActivity();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                if (permissionDeniedResponse.isPermanentlyDenied()) {
                                    showPermissionDeniedDialog();
                                } else {
                                    Toast.makeText(LocationPermission.this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                showPermissionRationale(permissionToken);
                            }
                        })
                        .check();
            }
        });
    }

    private void startTrackOrderActivity() {
        Intent intent = new Intent(LocationPermission.this, TrackOrder.class);
        intent.putExtra("total_amount", total);
        intent.putExtra("total_time", time);
        intent.putExtra("PhoneNo", Id);
        startActivity(intent);
        finish();
    }

    private void showPermissionDeniedDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permission_denied_title)
                .setMessage(R.string.permission_denied_message)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivity(intent);
                })
                .show();
    }

    private void showPermissionRationale(PermissionToken token) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permission_required_title)
                .setMessage(R.string.location_permission_rationale)
                .setNegativeButton(R.string.cancel, (dialog, which) -> token.cancelPermissionRequest())
                .setPositiveButton(R.string.ok, (dialog, which) -> token.continuePermissionRequest())
                .show();
    }
}
