package com.example.tt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PostTripActivity extends AppCompatActivity {

    private Button btnPost, btnHome, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_trip);

        btnPost = findViewById(R.id.pv_post);
        btnHome = findViewById(R.id.navigation_home);
        btnProfile = findViewById(R.id.navigation_profile);

        btnPost.setOnClickListener(v -> {
            // TODO: Save trip data
            // After posting, go back to MainActivity or show success
            Intent intent = new Intent(PostTripActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(PostTripActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(PostTripActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }
}