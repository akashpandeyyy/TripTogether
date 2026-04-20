package com.example.tt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class PostTripActivity extends AppCompatActivity {

    private TextInputEditText sourceET, destET, amountET, seatsET, dateET;
    private MaterialButton postBtn;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_trip); // Make sure layout name is correct

        // 1. Initialize all Input Fields
        sourceET = findViewById(R.id.p_source);
        destET = findViewById(R.id.p_destination);
        amountET = findViewById(R.id.p_ammount); // XML mein spelling 'ammount' hai toh wahi use ki hai
        seatsET = findViewById(R.id.p_seats);
        dateET = findViewById(R.id.p_date);

        // 2. Initialize Button (Fixes ClassCastException)
        // Make sure you are using R.id.pv_post here and NOT a menu ID
        postBtn = findViewById(R.id.pv_post);

        // 3. Initialize Bottom Navigation
        bottomNav = findViewById(R.id.bottom_navigation);

        // Post Button Click Logic
        postBtn.setOnClickListener(v -> {
            String source = sourceET.getText().toString().trim();
            if (source.isEmpty()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ride Posted Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        // Bottom Navigation Logic
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Intent intent = new Intent(PostTripActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else if (id == R.id.nav_profile) {
                Intent intent = new Intent(PostTripActivity.this, PostTripActivity.class);
                startActivity(intent);
                return true;
            }
            // Add other navigation logic here
            return false;
        });
    }
}