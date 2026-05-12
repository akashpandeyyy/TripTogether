package com.example.tt;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import android.content.Intent;

public class MyRidesActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    MaterialToolbar toolbar;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rides);

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        viewPager.setAdapter(new RidePagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("My Posts");
                    } else {
                        tab.setText("My Bookings");
                    }
                }).attach();

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.navigation_my_ride);

            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    Intent intent = new Intent(MyRidesActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                } else if (itemId == R.id.navigation_my_ride) {
                    return true;
                } else if (itemId == R.id.navigation_post_ride) {
                    Intent intent = new Intent(MyRidesActivity.this, PostTripActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    Intent intent = new Intent(MyRidesActivity.this, ProfileActivity.class);
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
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.navigation_my_ride);
        }
    }

    // Model class for Posted Rides
    public static class RideItem {
        public String route, dateTime, price, seats, bookings, status;

        public RideItem(String route, String dateTime, String price,
                        String seats, String bookings, String status) {
            this.route = route;
            this.dateTime = dateTime;
            this.price = price;
            this.seats = seats;
            this.bookings = bookings;
            this.status = status;
        }
    }

    // Model class for Booked Rides
    public static class BookingItem {
        public String driverName, carInfo, route, dateTime, seats, price, status, phoneNumber;

        public BookingItem(String driverName, String carInfo, String route,
                           String dateTime, String seats, String price,
                           String status, String phoneNumber) {
            this.driverName = driverName;
            this.carInfo = carInfo;
            this.route = route;
            this.dateTime = dateTime;
            this.seats = seats;
            this.price = price;
            this.status = status;
            this.phoneNumber = phoneNumber;
        }
    }
}