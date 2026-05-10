package com.example.tt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText sourceET;
    private TextInputEditText destinationET;

    private MaterialButton searchBtn;

    private BottomNavigationView bottomNav;

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

        initSharedPref();

        loadDummyRides();

        setupSearchLogic();

        setupBottomNavigation();

        showUserWelcome();
    }

    private void initViews() {

        sourceET = findViewById(R.id.m_source);

        destinationET = findViewById(R.id.m_destination);

        searchBtn = findViewById(R.id.m_search);

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

    // Dummy API Data

    private void loadDummyRides() {

        dummyRides.add(
                new RideModel(
                        "Rewa",
                        "Bhopal",
                        "Aman Tiwari",
                        "₹650",
                        "3 Seats",
                        "08:30 AM"
                )
        );

        dummyRides.add(
                new RideModel(
                        "Indore",
                        "Ujjain",
                        "Rahul Mishra",
                        "₹220",
                        "2 Seats",
                        "10:00 AM"
                )
        );

        dummyRides.add(
                new RideModel(
                        "Bhopal",
                        "Indore",
                        "Saurabh Patel",
                        "₹350",
                        "4 Seats",
                        "02:15 PM"
                )
        );

        dummyRides.add(
                new RideModel(
                        "Ujjain",
                        "Rewa",
                        "Aditya Singh",
                        "₹780",
                        "1 Seat",
                        "06:00 PM"
                )
        );

        dummyRides.add(
                new RideModel(
                        "Rewa",
                        "Indore",
                        "Vikas Sharma",
                        "₹900",
                        "2 Seats",
                        "09:45 PM"
                )
        );
    }

    // Search Logic

    private void setupSearchLogic() {

        searchBtn.setOnClickListener(v -> {

            String source =
                    sourceET.getText().toString().trim();

            String destination =
                    destinationET.getText().toString().trim();

            if (TextUtils.isEmpty(source)) {

                sourceET.setError("Enter source location");

                return;
            }

            if (TextUtils.isEmpty(destination)) {

                destinationET.setError("Enter destination");

                return;
            }

            // Save Recent Search

            saveRecentSearch(source, destination);

            // Fake API Loading

            searchBtn.setEnabled(false);

            searchBtn.setText("Searching Rides...");

            new Handler(Looper.getMainLooper())
                    .postDelayed(() -> {

                        RideModel ride =
                                searchRide(source, destination);

                        if (ride != null) {

                            String result =
                                    "🚖 Ride Found\n\n" +
                                            "Driver: " + ride.driverName +
                                            "\nFrom: " + ride.source +
                                            "\nTo: " + ride.destination +
                                            "\nFare: " + ride.price +
                                            "\nSeats: " + ride.seats +
                                            "\nTime: " + ride.time;

                            Toast.makeText(
                                    MainActivity.this,
                                    result,
                                    Toast.LENGTH_LONG
                            ).show();

                        } else {

                            Toast.makeText(
                                    MainActivity.this,
                                    "No rides found 😔",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                        searchBtn.setEnabled(true);

                        searchBtn.setText("Find a Ride");

                    }, 2000);
        });
    }

    // Search Dummy Ride

    private RideModel searchRide(
            String source,
            String destination
    ) {

        for (RideModel ride : dummyRides) {

            if (ride.source.equalsIgnoreCase(source)
                    && ride.destination.equalsIgnoreCase(destination)) {

                return ride;
            }
        }

        return null;
    }

    // Save Search History

    private void saveRecentSearch(
            String source,
            String destination
    ) {

        SharedPreferences.Editor editor =
                sharedPreferences.edit();

        editor.putString("last_source", source);

        editor.putString("last_destination", destination);

        editor.apply();
    }

    // Welcome Message

    private void showUserWelcome() {

        String mobile =
                sharedPreferences.getString(
                        "user_mobile",
                        "Guest"
                );

        String[] greetings = {

                "Welcome Back 👋",

                "Ready for your next trip?",

                "Explore rides across MP 🚖",

                "Travel smart with TT ✨"
        };

        int random =
                new Random().nextInt(greetings.length);

        Toast.makeText(
                this,
                greetings[random] + "\nUser: " + mobile,
                Toast.LENGTH_SHORT
        ).show();
    }

    // Bottom Nav

    private void setupBottomNavigation() {

        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.nav_post) {

                startActivity(
                        new Intent(
                                MainActivity.this,
                                PostTripActivity.class
                        )
                );

                return true;

            } else if (itemId == R.id.nav_search) {

                startActivity(
                        new Intent(
                                MainActivity.this,
                                SearchActivity.class
                        )
                );

                return true;

            } else if (itemId == R.id.nav_profile) {

                startActivity(
                        new Intent(
                                MainActivity.this,
                                ProfileActivity.class
                        )
                );

                return true;
            }

            return true;
        });
    }

    // Dummy Model

    public static class RideModel {

        String source;

        String destination;

        String driverName;

        String price;

        String seats;

        String time;

        public RideModel(
                String source,
                String destination,
                String driverName,
                String price,
                String seats,
                String time
        ) {

            this.source = source;

            this.destination = destination;

            this.driverName = driverName;

            this.price = price;

            this.seats = seats;

            this.time = time;
        }
    }
}