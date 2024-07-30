package com.example.likonirestaurante.Activity.Profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.likonirestaurante.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class FullScreenImageActivity extends AppCompatActivity {

    private static final String TAG = "FullScreenImageActivity";
    private ImageView fullScreenImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        fullScreenImageView = findViewById(R.id.fullScreenImageView);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("imageUrl");
        Uri imageUri = intent.getParcelableExtra("imageUri");

        if (imageUrl != null) {
            loadImageFromUrl(imageUrl);
        } else if (imageUri != null) {
            fullScreenImageView.setImageURI(imageUri);
        } else {
            Log.e(TAG, "No image URI or URL provided");
        }
    }

    private void loadImageFromUrl(String imageUrl) {
        Picasso.get().load(imageUrl).into(fullScreenImageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Image loaded successfully from URL");
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Failed to load image from URL", e);
            }
        });
    }
}
