package com.example.tt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText sourceET, destinationET;
    private MaterialButton searchBtn;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Views initialization
        sourceET = findViewById(R.id.m_source);
        destinationET = findViewById(R.id.m_destination);
        searchBtn = findViewById(R.id.m_search);
        bottomNav = findViewById(R.id.bottom_navigation);

        // Ride Search Logic
        searchBtn.setOnClickListener(v -> {
            String source = sourceET.getText().toString().trim();
            String dest = destinationET.getText().toString().trim();

            if (source.isEmpty() || dest.isEmpty()) {
                Toast.makeText(this, "Bhai, dono locations toh daalo!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Searching for rides from " + source, Toast.LENGTH_SHORT).show();
            }
        });

        // Bottom Navigation Logic (ClassCastException Fix)
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

             if (itemId == R.id.nav_post) {
                 Intent intent = new Intent(MainActivity.this, PostTripActivity.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                 startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_search) {
                 Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                 startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_profile) {
                 Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                 startActivity(intent);
                return true;
            }
            return false;
        });
    }
}