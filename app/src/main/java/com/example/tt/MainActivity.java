package com.example.tt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText sourceET, destinationET;
    private TextInputLayout sourceLayout, destinationLayout;
    private MaterialButton searchBtn;
    private BottomNavigationView bottomNav;
    private MaterialCardView notificationCard;
    private View mainLayout;

    // SharedPref
    private SharedPreferences sharedPreferences;

    // Dummy Ride List
    private final List<RideModel> dummyRides = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initViews();
        setupWindowInsets();
        initSharedPref();
        loadDummyRides();
        setupSearchLogic();
        setupBottomNavigation();
        setupGreeting();
        setupNotificationClick();
    }

    private void initViews() {
        mainLayout = findViewById(R.id.main);
        sourceET = findViewById(R.id.m_source);
        destinationET = findViewById(R.id.m_destination);
        sourceLayout = findViewById(R.id.source_card);
        destinationLayout = findViewById(R.id.destination_card);
        searchBtn = findViewById(R.id.m_search);
        bottomNav = findViewById(R.id.bottom_navigation);
        notificationCard = findViewById(R.id.notificationCard);

        // Ensure bottom navigation is visible
        if (bottomNav != null) {
            bottomNav.setVisibility(View.VISIBLE);
        }
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            
            // Apply top padding for status bar
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);

            // Apply bottom padding to navigation view to account for system navigation bar
            if (bottomNav != null) {
                bottomNav.setPadding(0, 0, 0, systemBars.bottom);
            }

            return insets;
        });
    }

    private void initSharedPref() {
        sharedPreferences = getSharedPreferences("TT_USER_DATA", MODE_PRIVATE);
    }

    private void setupGreeting() {
        TextView greetingText = findViewById(R.id.greetingText);
        TextView welcomeText = findViewById(R.id.welcome_text);

        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        String greeting;
        if (hourOfDay >= 5 && hourOfDay < 12) {
            greeting = "Good Morning 🌅";
        } else if (hourOfDay >= 12 && hourOfDay < 17) {
            greeting = "Good Afternoon ☀️";
        } else if (hourOfDay >= 17 && hourOfDay < 21) {
            greeting = "Good Evening 🌆";
        } else {
            greeting = "Good Night 🌙";
        }

        greetingText.setText(greeting);

        String userName = sharedPreferences.getString("user_name", "");
        if (!TextUtils.isEmpty(userName)) {
            welcomeText.setText("Where to, " + userName.split(" ")[0] + "?");
        }
    }

    private void setupNotificationClick() {
        if (notificationCard != null) {
            notificationCard.setOnClickListener(v -> {
                Toast.makeText(MainActivity.this, "No new notifications", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void loadDummyRides() {
        // Rewa to Bhopal routes
        dummyRides.add(new RideModel("Rewa", "Bhopal", "Aman Tiwari", "270", "2 Seats", "08:30 AM", "Toyota Innova", "MP09 AB 1234"));
        dummyRides.add(new RideModel("Rewa", "Bhopal", "Rahul Mehta", "250", "3 Seats", "10:00 AM", "Hyundai i20", "MP04 CD 5678"));
        dummyRides.add(new RideModel("Rewa", "Bhopal", "Priya Sharma", "280", "2 Seats", "02:30 PM", "Maruti Suzuki", "MP07 EF 9012"));

        // Bhopal to Indore routes
        dummyRides.add(new RideModel("Bhopal", "Indore", "Saurabh Patel", "350", "3 Seats", "09:00 AM", "Honda City", "MP01 GH 3456"));
        dummyRides.add(new RideModel("Bhopal", "Indore", "Neha Gupta", "330", "2 Seats", "11:30 AM", "Volkswagen", "MP03 IJ 7890"));
        dummyRides.add(new RideModel("Bhopal", "Indore", "Vikram Singh", "370", "4 Seats", "04:00 PM", "Mahindra XUV", "MP05 KL 1234"));

        // Bhopal to Rewa routes
        dummyRides.add(new RideModel("Bhopal", "Rewa", "Aditya Singh", "500", "2 Seats", "07:00 AM", "Toyota Fortuner", "MP06 MN 5678"));
        dummyRides.add(new RideModel("Bhopal", "Rewa", "Kavita Joshi", "480", "3 Seats", "01:00 PM", "Hyundai Verna", "MP08 OP 9012"));

        // Indore to Bhopal routes
        dummyRides.add(new RideModel("Indore", "Bhopal", "Rajesh K", "340", "4 Seats", "08:00 AM", "Swift Dezire", "MP02 QR 3456"));
        dummyRides.add(new RideModel("Indore", "Bhopal", "Meera S", "360", "2 Seats", "03:00 PM", "Honda Amaze", "MP10 ST 7890"));

        // Additional routes
        dummyRides.add(new RideModel("Ujjain", "Indore", "Divya M", "220", "3 Seats", "09:30 AM", "Maruti Baleno", "MP15 UV 1234"));
        dummyRides.add(new RideModel("Jabalpur", "Bhopal", "Ankit T", "400", "2 Seats", "11:00 AM", "Ford EcoSport", "MP20 WX 5678"));
        dummyRides.add(new RideModel("Gwalior", "Agra", "Sunil K", "550", "4 Seats", "06:00 AM", "Tata Nexon", "MP30 YZ 9012"));
        dummyRides.add(new RideModel("Bhopal", "Delhi", "Rohit M", "1200", "3 Seats", "07:00 PM", "Toyota Camry", "DL01 AB 1234"));
        dummyRides.add(new RideModel("Rewa", "Satna", "Monika P", "150", "4 Seats", "12:00 PM", "Wagon R", "MP40 CD 5678"));
    }

    private void setupSearchLogic() {
        searchBtn.setOnClickListener(v -> {
            String source = sourceET.getText().toString().trim();
            String destination = destinationET.getText().toString().trim();

            boolean isValid = true;

            if (TextUtils.isEmpty(source)) {
                sourceLayout.setError("Enter pickup location");
                sourceLayout.setErrorEnabled(true);
                sourceET.requestFocus();
                isValid = false;
            } else {
                sourceLayout.setErrorEnabled(false);
            }

            if (TextUtils.isEmpty(destination)) {
                destinationLayout.setError("Enter destination");
                destinationLayout.setErrorEnabled(true);
                destinationET.requestFocus();
                isValid = false;
            } else {
                destinationLayout.setErrorEnabled(false);
            }

            if (source.equals(destination) && !TextUtils.isEmpty(source)) {
                destinationLayout.setError("Destination cannot be same as pickup");
                destinationLayout.setErrorEnabled(true);
                isValid = false;
            }

            if (isValid) {
                saveRecentSearch(source, destination);

                // Show loading state
                searchBtn.setEnabled(false);
                searchBtn.setText("Searching Rides...");

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    List<RideModel> foundRides = searchRides(source, destination);

                    // Navigate to SearchActivity with results
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra("source", source);
                    intent.putExtra("destination", destination);
                    intent.putExtra("ride_count", foundRides.size());

                    // Pass ride data as serializable
                    ArrayList<RideModel> rideList = new ArrayList<>(foundRides);
                    intent.putExtra("ride_list", rideList);

                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                    searchBtn.setEnabled(true);
                    searchBtn.setText("Find a Ride");
                }, 1500);
            }
        });
    }

    private List<RideModel> searchRides(String source, String destination) {
        List<RideModel> results = new ArrayList<>();

        for (RideModel ride : dummyRides) {
            if (ride.source.equalsIgnoreCase(source) && ride.destination.equalsIgnoreCase(destination)) {
                results.add(ride);
            }
        }

        return results;
    }

    private void saveRecentSearch(String source, String destination) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("last_source", source);
        editor.putString("last_destination", destination);
        editor.apply();
    }

    private void setupBottomNavigation() {
        bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            // Set home as selected
            bottomNav.setSelectedItemId(R.id.navigation_home);

            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    // Already on home
                    return true;
                } else if (itemId == R.id.navigation_my_ride) {
                    Intent intent = new Intent(MainActivity.this, MyRidesActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                } else if (itemId == R.id.navigation_post_ride) {
                    Intent intent = new Intent(MainActivity.this, PostTripActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                }

                return false;
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ensure bottom navigation is still visible when returning to activity
        if (bottomNav != null) {
            bottomNav.setVisibility(View.VISIBLE);
            bottomNav.setSelectedItemId(R.id.navigation_home);
        }
    }

    // Ride Model Class
    public static class RideModel implements java.io.Serializable {
        public String source;
        public String destination;
        public String driverName;
        public String pricePerSeat;
        public String seats;
        public String time;
        public String carModel;
        public String carNumber;

        public RideModel(String source, String destination, String driverName,
                         String pricePerSeat, String seats, String time,
                         String carModel, String carNumber) {
            this.source = source;
            this.destination = destination;
            this.driverName = driverName;
            this.pricePerSeat = pricePerSeat;
            this.seats = seats;
            this.time = time;
            this.carModel = carModel;
            this.carNumber = carNumber;
        }

        public String getTotalPrice() {
            int price = Integer.parseInt(pricePerSeat);
            int seatCount = Integer.parseInt(seats.split(" ")[0]);
            return "₹" + (price * seatCount);
        }
    }
}