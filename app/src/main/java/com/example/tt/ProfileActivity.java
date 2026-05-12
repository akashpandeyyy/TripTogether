package com.example.tt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class ProfileActivity extends AppCompatActivity {

    private TextView proName, proMail, proMob;
    private TextView proDelete;
    private MaterialButton btnLogout;
    private BottomNavigationView bottomNavigation;
    private MaterialCardView editProfileCard;

    // Menu options
    private LinearLayout myTripsOption, savedRidesOption, paymentOption, helpOption, aboutOption;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        setupWindowInsets();
        initViews();
        initSharedPref();
        loadUserData();
        setupClickListeners();
        setupLogout();
        setupDeleteAccount();
        setupBottomNavigation();
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Apply top padding for status bar
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);

            // Apply bottom padding to navigation view to account for system navigation bar
            if (bottomNavigation != null) {
                bottomNavigation.setPadding(0, 0, 0, systemBars.bottom);
            }

            return insets;
        });
    }

    private void initViews() {
        proName = findViewById(R.id.pro_name);
        proMail = findViewById(R.id.pro_mail);
        proMob = findViewById(R.id.pro_mob);
        proDelete = findViewById(R.id.pro_del);
        btnLogout = findViewById(R.id.pro_logout);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        editProfileCard = findViewById(R.id.editProfileCard);

        // Menu options
        myTripsOption = findViewById(R.id.myTripsOption);
        savedRidesOption = findViewById(R.id.savedRidesOption);
        paymentOption = findViewById(R.id.paymentOption);
        helpOption = findViewById(R.id.helpOption);
        aboutOption = findViewById(R.id.aboutOption);
    }

    private void initSharedPref() {
        sharedPreferences = getSharedPreferences("TT_USER_DATA", MODE_PRIVATE);
    }

    private void loadUserData() {
        String name = sharedPreferences.getString("user_name", "Som Pandey");
        String email = sharedPreferences.getString("user_email", "sompandey@gmail.com");
        String mobile = sharedPreferences.getString("user_mobile", "9876543210");

        proName.setText(name);
        proMail.setText(email);
        proMob.setText("+91 " + mobile);
    }

    private void setupClickListeners() {
        // Edit Profile
        if (editProfileCard != null) {
            editProfileCard.setOnClickListener(v -> showEditProfileDialog());
        }

        // My Trips - Navigate to My Rides
        if (myTripsOption != null) {
            myTripsOption.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, MyRidesActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            });
        }

        // Saved Rides
        if (savedRidesOption != null) {
            savedRidesOption.setOnClickListener(v -> {
                Toast.makeText(this, "Saved Rides feature coming soon", Toast.LENGTH_SHORT).show();
            });
        }

        // Payment Methods
        if (paymentOption != null) {
            paymentOption.setOnClickListener(v -> showPaymentDialog());
        }

        // Help & Support
        if (helpOption != null) {
            helpOption.setOnClickListener(v -> showHelpDialog());
        }

        // About
        if (aboutOption != null) {
            aboutOption.setOnClickListener(v -> showAboutDialog());
        }
    }

    private void showEditProfileDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_edit_profile, null);

        com.google.android.material.textfield.TextInputEditText etName = view.findViewById(R.id.edit_name);
        com.google.android.material.textfield.TextInputEditText etEmail = view.findViewById(R.id.edit_email);
        com.google.android.material.textfield.TextInputEditText etMobile = view.findViewById(R.id.edit_mobile);

        // Load current data
        etName.setText(proName.getText().toString());
        etEmail.setText(proMail.getText().toString());
        String currentMobile = proMob.getText().toString().replace("+91 ", "");
        etMobile.setText(currentMobile);

        builder.setTitle("Edit Profile")
                .setView(view)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newName = etName.getText().toString().trim();
                    String newEmail = etEmail.getText().toString().trim();
                    String newMobile = etMobile.getText().toString().trim();

                    if (!newName.isEmpty() && !newEmail.isEmpty() && !newMobile.isEmpty()) {
                        if (newMobile.length() == 10) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user_name", newName);
                            editor.putString("user_email", newEmail);
                            editor.putString("user_mobile", newMobile);
                            editor.apply();

                            loadUserData();
                            Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Enter valid 10-digit mobile number", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showPaymentDialog() {
        String[] paymentMethods = {"Credit/Debit Card", "UPI", "Net Banking", "Wallet"};

        new AlertDialog.Builder(this)
                .setTitle("Payment Methods")
                .setItems(paymentMethods, (dialog, which) -> {
                    Toast.makeText(this, paymentMethods[which] + " selected", Toast.LENGTH_SHORT).show();
                })
                .setPositiveButton("Close", null)
                .show();
    }

    private void showHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Help & Support")
                .setMessage("For any assistance, please contact:\n\n📧 Email: support@triptogether.com\n📞 Phone: +91 9876543210\n\nWe're here to help 24/7!")
                .setPositiveButton("Call Support", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(android.net.Uri.parse("tel:9876543210"));
                    startActivity(intent);
                })
                .setNeutralButton("Email", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(android.net.Uri.parse("mailto:support@triptogether.com"));
                    startActivity(intent);
                })
                .setNegativeButton("Close", null)
                .show();
    }

    private void showAboutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("About TripTogether")
                .setMessage("Version: 1.0.0\n\nTripTogether is a ride-sharing platform connecting travelers for safe and affordable journeys across cities.\n\nDeveloped by Shelu Pandey")
                .setPositiveButton("OK", null)
                .show();
    }

    private void setupLogout() {
        btnLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("is_logged_in", false);
                        editor.apply();

                        Toast.makeText(ProfileActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void setupDeleteAccount() {
        proDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Delete Account")
                    .setMessage("This action will permanently delete all your data. This cannot be undone.")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        Toast.makeText(ProfileActivity.this, "Account Deleted Permanently", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(ProfileActivity.this, RegisterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void setupBottomNavigation() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        if (bottomNavigation != null) {
            bottomNavigation.setSelectedItemId(R.id.navigation_profile);

            bottomNavigation.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                } else if (itemId == R.id.navigation_post_ride) {
                    Intent intent = new Intent(ProfileActivity.this, PostTripActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                } else if (itemId == R.id.navigation_my_ride) {
                    Intent intent = new Intent(ProfileActivity.this, MyRidesActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    return true;
                }

                return false;
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserData();
        if (bottomNavigation != null) {
            bottomNavigation.setSelectedItemId(R.id.navigation_profile);
        }
    }
}