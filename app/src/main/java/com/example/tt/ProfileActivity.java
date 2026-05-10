package com.example.tt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class ProfileActivity extends AppCompatActivity {

    private TextView proName;
    private TextView proMail;
    private TextView proMob;
    private TextView proDelete;

    private MaterialButton btnLogout;

    private BottomNavigationView bottomNavigation;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_profile);

        initViews();

        initSharedPref();

        loadUserData();

        setupLogout();

        setupDeleteAccount();

        setupBottomNavigation();
    }

    // Initialize Views

    private void initViews() {

        proName = findViewById(R.id.pro_name);

        proMail = findViewById(R.id.pro_mail);

        proMob = findViewById(R.id.pro_mob);

        proDelete = findViewById(R.id.pro_del);

        btnLogout = findViewById(R.id.pro_logout);

        bottomNavigation = findViewById(R.id.bottom_navigation);
    }

    // SharedPref

    private void initSharedPref() {

        sharedPreferences =
                getSharedPreferences(
                        "TT_USER_DATA",
                        MODE_PRIVATE
                );
    }

    // Load Dummy User Data

    private void loadUserData() {

        String name =
                sharedPreferences.getString(
                        "user_name",
                        "Akash Pandey"
                );

        String email =
                sharedPreferences.getString(
                        "user_email",
                        "akashpandey@gmail.com"
                );

        String mobile =
                sharedPreferences.getString(
                        "user_mobile",
                        "9876543210"
                );

        proName.setText(name);

        proMail.setText(email);

        proMob.setText("+91 " + mobile);
    }

    // Logout

    private void setupLogout() {

        btnLogout.setOnClickListener(v -> {

            new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton(
                            "Yes",
                            (dialog, which) -> {

                                SharedPreferences.Editor editor =
                                        sharedPreferences.edit();

                                editor.putBoolean(
                                        "is_logged_in",
                                        false
                                );

                                editor.apply();

                                Toast.makeText(
                                        ProfileActivity.this,
                                        "Logged Out Successfully",
                                        Toast.LENGTH_SHORT
                                ).show();

                                Intent intent =
                                        new Intent(
                                                ProfileActivity.this,
                                                LoginActivity.class
                                        );

                                intent.setFlags(
                                        Intent.FLAG_ACTIVITY_NEW_TASK
                                                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                );

                                startActivity(intent);

                                finish();
                            }
                    )
                    .setNegativeButton(
                            "Cancel",
                            null
                    )
                    .show();
        });
    }

    // Delete Account

    private void setupDeleteAccount() {

        proDelete.setOnClickListener(v -> {

            new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Delete Account")
                    .setMessage(
                            "This action will remove your dummy account data permanently."
                    )
                    .setPositiveButton(
                            "Delete",
                            (dialog, which) -> {

                                SharedPreferences.Editor editor =
                                        sharedPreferences.edit();

                                editor.clear();

                                editor.apply();

                                Toast.makeText(
                                        ProfileActivity.this,
                                        "Account Deleted",
                                        Toast.LENGTH_SHORT
                                ).show();

                                Intent intent =
                                        new Intent(
                                                ProfileActivity.this,
                                                RegisterActivity.class
                                        );

                                intent.setFlags(
                                        Intent.FLAG_ACTIVITY_NEW_TASK
                                                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                );

                                startActivity(intent);

                                finish();
                            }
                    )
                    .setNegativeButton(
                            "Cancel",
                            null
                    )
                    .show();
        });
    }

    // Bottom Navigation

    private void setupBottomNavigation() {

        bottomNavigation.setSelectedItemId(R.id.nav_profile);

        bottomNavigation.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {

                Intent intent =
                        new Intent(
                                ProfileActivity.this,
                                MainActivity.class
                        );

                startActivity(intent);

                finish();

                return true;
            }

            else if (itemId == R.id.nav_post) {

                Intent intent =
                        new Intent(
                                ProfileActivity.this,
                                PostTripActivity.class
                        );

                startActivity(intent);

                finish();

                return true;
            }

            else if (itemId == R.id.nav_search) {

                Intent intent =
                        new Intent(
                                ProfileActivity.this,
                                SearchActivity.class
                        );

                startActivity(intent);

                finish();

                return true;
            }

            return true;
        });
    }
}