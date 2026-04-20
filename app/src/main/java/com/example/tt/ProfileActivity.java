package com.example.tt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private Button btnLogout, btnDelete, btnHome, btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        btnLogout = findViewById(R.id.pro_logout);
        btnDelete = findViewById(R.id.pro_del);
        btnHome = findViewById(R.id.navigation_home);
        btnPost = findViewById(R.id.navigation_post);

        btnLogout.setOnClickListener(v -> {
            // Clear session if any
            Intent intent = new Intent(ProfileActivity.this, LoginRegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        btnDelete.setOnClickListener(v -> {
            // TODO: Delete account from backend
            // Then logout
            Intent intent = new Intent(ProfileActivity.this, LoginRegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnPost.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, PostTripActivity.class);
            startActivity(intent);
        });
    }
}