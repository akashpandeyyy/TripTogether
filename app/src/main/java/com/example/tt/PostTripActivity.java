package com.example.tt;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PostTripActivity extends AppCompatActivity {

    private TextInputEditText sourceET, destET, amountET, seatsET, dateET;
    private TextInputLayout sourceLayout, destLayout, amountLayout, seatsLayout, dateLayout;
    private MaterialButton postBtn;
    private BottomNavigationView bottomNav;
    private MaterialCardView tipsCard;
    private TextView titleText;

    private SharedPreferences sharedPreferences;
    private Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_trip);

        setupWindowInsets();
        initViews();
        initSharedPref();
        setupDatePicker();
        setupPostRide();
        setupBottomNavigation();
        setupTipsCard();
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            androidx.core.graphics.Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Apply top padding for status bar
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);

            // Apply bottom padding to navigation view to account for system navigation bar
            if (bottomNav != null) {
                bottomNav.setPadding(0, 0, 0, systemBars.bottom);
            }

            return insets;
        });
    }

    private void initViews() {
        sourceET = findViewById(R.id.p_source);
        destET = findViewById(R.id.p_destination);
        amountET = findViewById(R.id.p_ammount);
        seatsET = findViewById(R.id.p_seats);
        dateET = findViewById(R.id.p_date);

        sourceLayout = findViewById(R.id.post_source_card);
        destLayout = findViewById(R.id.post_destination_card);
        amountLayout = findViewById(R.id.post_amount_card);
        seatsLayout = findViewById(R.id.post_seats_card);
        dateLayout = findViewById(R.id.post_date_card);

        postBtn = findViewById(R.id.pv_post);
        bottomNav = findViewById(R.id.bottom_navigation);
        
        titleText = findViewById(R.id.post_title);
    }

    private void initSharedPref() {
        sharedPreferences = getSharedPreferences("TT_USER_DATA", MODE_PRIVATE);

        // Load user name for personalization
        String userName = sharedPreferences.getString("user_name", "");
        if (!TextUtils.isEmpty(userName) && titleText != null) {
            titleText.setText("Post a Ride, " + userName.split(" ")[0]);
        }
    }

    private void setupDatePicker() {
        selectedDate = Calendar.getInstance();

        dateET.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog dialog = new DatePickerDialog(
                    PostTripActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        selectedDate.set(year, month, dayOfMonth);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        dateET.setText(sdf.format(selectedDate.getTime()));

                        // Clear error if any
                        dateLayout.setErrorEnabled(false);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            // Set minimum date to today
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dialog.show();
        });
    }

    private void setupPostRide() {
        postBtn.setOnClickListener(v -> {
            String source = sourceET.getText().toString().trim();
            String destination = destET.getText().toString().trim();
            String amount = amountET.getText().toString().trim();
            String seats = seatsET.getText().toString().trim();
            String date = dateET.getText().toString().trim();

            boolean isValid = true;

            // Validation
            if (TextUtils.isEmpty(source)) {
                sourceLayout.setError("Enter pickup location");
                sourceLayout.setErrorEnabled(true);
                sourceET.requestFocus();
                isValid = false;
            } else {
                sourceLayout.setErrorEnabled(false);
            }

            if (TextUtils.isEmpty(destination)) {
                destLayout.setError("Enter destination");
                destLayout.setErrorEnabled(true);
                destET.requestFocus();
                isValid = false;
            } else {
                destLayout.setErrorEnabled(false);
            }

            if (source.equals(destination) && !TextUtils.isEmpty(source)) {
                destLayout.setError("Destination cannot be same as pickup");
                destLayout.setErrorEnabled(true);
                isValid = false;
            }

            if (TextUtils.isEmpty(amount)) {
                amountLayout.setError("Enter price per seat");
                amountLayout.setErrorEnabled(true);
                amountET.requestFocus();
                isValid = false;
            } else {
                int price = Integer.parseInt(amount);
                if (price < 50) {
                    amountLayout.setError("Price cannot be less than ₹50");
                    amountLayout.setErrorEnabled(true);
                    isValid = false;
                } else {
                    amountLayout.setErrorEnabled(false);
                }
            }

            if (TextUtils.isEmpty(seats)) {
                seatsLayout.setError("Enter available seats");
                seatsLayout.setErrorEnabled(true);
                seatsET.requestFocus();
                isValid = false;
            } else {
                int seatCount = Integer.parseInt(seats);
                if (seatCount < 1) {
                    seatsLayout.setError("At least 1 seat required");
                    seatsLayout.setErrorEnabled(true);
                    isValid = false;
                } else if (seatCount > 6) {
                    seatsLayout.setError("Maximum 6 seats allowed");
                    seatsLayout.setErrorEnabled(true);
                    isValid = false;
                } else {
                    seatsLayout.setErrorEnabled(false);
                }
            }

            if (TextUtils.isEmpty(date)) {
                dateLayout.setError("Select departure date");
                dateLayout.setErrorEnabled(true);
                isValid = false;
            } else {
                dateLayout.setErrorEnabled(false);
            }

            if (isValid) {
                saveAndPostRide(source, destination, amount, seats, date);
            } else {
                // Shake animation for error
                findViewById(R.id.mainCard).animate()
                        .translationX(10f)
                        .setDuration(50)
                        .withEndAction(() ->
                                findViewById(R.id.mainCard).animate()
                                        .translationX(0f)
                                        .setDuration(50)
                                        .start())
                        .start();
            }
        });
    }

    private void saveAndPostRide(String source, String destination, String amount, String seats, String date) {
        // Show loading state
        postBtn.setEnabled(false);
        postBtn.setText("Publishing Ride...");

        // Get driver name from SharedPreferences
        String driverName = sharedPreferences.getString("user_name", "User");
        String userMobile = sharedPreferences.getString("user_mobile", "9876543210");

        // Save to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save as a new ride post
        String rideId = String.valueOf(System.currentTimeMillis());
        editor.putString("ride_" + rideId + "_source", source);
        editor.putString("ride_" + rideId + "_destination", destination);
        editor.putString("ride_" + rideId + "_amount", amount);
        editor.putString("ride_" + rideId + "_seats", seats);
        editor.putString("ride_" + rideId + "_date", date);
        editor.putString("ride_" + rideId + "_driver", driverName);
        editor.putString("ride_" + rideId + "_phone", userMobile);
        editor.putInt("ride_count", sharedPreferences.getInt("ride_count", 0) + 1);
        editor.apply();

        // Simulate network delay
        postBtn.postDelayed(() -> {
            postBtn.setEnabled(true);
            postBtn.setText("Publish Ride");

            Toast.makeText(PostTripActivity.this,
                    "✅ Ride Posted Successfully!\n" +
                            source + " → " + destination + "\n" +
                            "₹" + amount + "/seat · " + seats + " seats",
                    Toast.LENGTH_LONG).show();

            clearFields();

            // Optional: Navigate to My Rides
             Intent intent = new Intent(PostTripActivity.this, MyRidesActivity.class);
             startActivity(intent);

        }, 1500);
    }

    private void clearFields() {
        sourceET.setText("");
        destET.setText("");
        amountET.setText("");
        seatsET.setText("");
        dateET.setText("");

        // Reset errors
        sourceLayout.setErrorEnabled(false);
        destLayout.setErrorEnabled(false);
        amountLayout.setErrorEnabled(false);
        seatsLayout.setErrorEnabled(false);
        dateLayout.setErrorEnabled(false);

        // Reset date
        selectedDate = Calendar.getInstance();
    }

    private void setupTipsCard() {
        if (tipsCard != null) {
            tipsCard.setOnClickListener(v -> {
                Toast.makeText(this,
                        "💡 Tips for better rides:\n" +
                                "• Set reasonable prices\n" +
                                "• Share accurate pickup points\n" +
                                "• Be responsive to passengers",
                        Toast.LENGTH_LONG).show();
            });
        }
    }

    private void setupBottomNavigation() {
        bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.navigation_post_ride);

            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();

                if (id == R.id.navigation_home) {
                    Intent intent = new Intent(PostTripActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                } else if (id == R.id.navigation_my_ride) {
                    Intent intent = new Intent(PostTripActivity.this, MyRidesActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                } else if (id == R.id.navigation_profile) {
                    Intent intent = new Intent(PostTripActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                } else if (id == R.id.navigation_post_ride) {
                    return true;
                }

                return false;
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}