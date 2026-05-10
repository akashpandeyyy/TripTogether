package com.example.tt;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PostTripActivity extends AppCompatActivity {

    private TextInputEditText sourceET;
    private TextInputEditText destET;
    private TextInputEditText amountET;
    private TextInputEditText seatsET;
    private TextInputEditText dateET;

    private MaterialButton postBtn;

    private BottomNavigationView bottomNav;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_post_trip);

        initViews();

        initSharedPref();

        setupDatePicker();

        setupPostRide();

        setupBottomNavigation();
    }

    // Initialize Views

    private void initViews() {

        sourceET = findViewById(R.id.p_source);

        destET = findViewById(R.id.p_destination);

        amountET = findViewById(R.id.p_ammount);

        seatsET = findViewById(R.id.p_seats);

        dateET = findViewById(R.id.p_date);

        postBtn = findViewById(R.id.pv_post);

        bottomNav = findViewById(R.id.bottom_navigation);
    }

    // SharedPref

    private void initSharedPref() {

        sharedPreferences =
                getSharedPreferences(
                        "TT_USER_DATA",
                        MODE_PRIVATE
                );
    }

    // Date Picker

    private void setupDatePicker() {

        dateET.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            DatePickerDialog dialog =
                    new DatePickerDialog(
                            PostTripActivity.this,
                            (view, year, month, dayOfMonth) -> {

                                Calendar selected =
                                        Calendar.getInstance();

                                selected.set(
                                        year,
                                        month,
                                        dayOfMonth
                                );

                                SimpleDateFormat sdf =
                                        new SimpleDateFormat(
                                                "dd MMM yyyy",
                                                Locale.getDefault()
                                        );

                                dateET.setText(
                                        sdf.format(selected.getTime())
                                );

                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );

            dialog.show();
        });
    }

    // Post Ride

    private void setupPostRide() {

        postBtn.setOnClickListener(v -> {

            String source =
                    sourceET.getText().toString().trim();

            String destination =
                    destET.getText().toString().trim();

            String amount =
                    amountET.getText().toString().trim();

            String seats =
                    seatsET.getText().toString().trim();

            String date =
                    dateET.getText().toString().trim();

            // Validation

            if (TextUtils.isEmpty(source)) {

                sourceET.setError("Enter pickup location");

                return;
            }

            if (TextUtils.isEmpty(destination)) {

                destET.setError("Enter destination");

                return;
            }

            if (TextUtils.isEmpty(amount)) {

                amountET.setError("Enter amount");

                return;
            }

            if (TextUtils.isEmpty(seats)) {

                seatsET.setError("Enter seats");

                return;
            }

            if (TextUtils.isEmpty(date)) {

                dateET.setError("Select date");

                return;
            }

            // Save Dummy Data

            SharedPreferences.Editor editor =
                    sharedPreferences.edit();

            editor.putString("trip_source", source);

            editor.putString("trip_destination", destination);

            editor.putString("trip_amount", amount);

            editor.putString("trip_seats", seats);

            editor.putString("trip_date", date);

            editor.apply();

            // Loading UI

            postBtn.setEnabled(false);

            postBtn.setText("Publishing Ride...");

            // Fake API Delay

            postBtn.postDelayed(() -> {

                postBtn.setEnabled(true);

                postBtn.setText("Publish Ride");

                Toast.makeText(
                        PostTripActivity.this,
                        "🚖 Ride Posted Successfully",
                        Toast.LENGTH_LONG
                ).show();

                // Clear Fields

                clearFields();

            }, 1800);
        });
    }

    // Clear Inputs

    private void clearFields() {

        sourceET.setText("");

        destET.setText("");

        amountET.setText("");

        seatsET.setText("");

        dateET.setText("");
    }

    // Bottom Navigation

    private void setupBottomNavigation() {

        bottomNav.setSelectedItemId(R.id.nav_post);

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {

                Intent intent =
                        new Intent(
                                PostTripActivity.this,
                                MainActivity.class
                        );

                startActivity(intent);

                finish();

                return true;
            }

            else if (id == R.id.nav_search) {

                Intent intent =
                        new Intent(
                                PostTripActivity.this,
                                SearchActivity.class
                        );

                startActivity(intent);

                finish();

                return true;
            }

            else if (id == R.id.nav_profile) {

                Intent intent =
                        new Intent(
                                PostTripActivity.this,
                                ProfileActivity.class
                        );

                startActivity(intent);

                finish();

                return true;
            }

            return true;
        });
    }
}