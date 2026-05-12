package com.example.tt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class RideDetailActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TextView driverName, carInfo, rating, timeText, fromLocation, toLocation;
    private TextView pricePerSeat, seatsAvailable, totalPrice, dateText;
    private MaterialButton bookNowButton;
    private MaterialCardView contactCard;

    private String userName, source, destination, amount, seats, time, carModel, userRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ride_detail);

        // Window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            androidx.core.graphics.Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        getIntentData();
        setupToolbar();
        displayRideDetails();
        setupBookButton();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        driverName = findViewById(R.id.driverName);
        carInfo = findViewById(R.id.carInfo);
        rating = findViewById(R.id.rating);
        timeText = findViewById(R.id.timeText);
        fromLocation = findViewById(R.id.fromLocation);
        toLocation = findViewById(R.id.toLocation);
        pricePerSeat = findViewById(R.id.pricePerSeat);
        seatsAvailable = findViewById(R.id.seatsAvailable);
        totalPrice = findViewById(R.id.totalPrice);
        dateText = findViewById(R.id.dateText);
        bookNowButton = findViewById(R.id.bookNowButton);
        contactCard = findViewById(R.id.contactCard);
    }

    private void getIntentData() {
        userName = getIntent().getStringExtra("userName");
        source = getIntent().getStringExtra("source");
        destination = getIntent().getStringExtra("destination");
        amount = getIntent().getStringExtra("amount");
        seats = getIntent().getStringExtra("seats");
        time = getIntent().getStringExtra("time");
        carModel = getIntent().getStringExtra("carModel");
        userRating = getIntent().getStringExtra("rating");
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Ride Details");
        }

        toolbar.setNavigationOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
    }

    private void displayRideDetails() {
        if (driverName != null) driverName.setText(userName);
        if (carInfo != null) carInfo.setText(carModel);
        if (rating != null) rating.setText(userRating);
        if (timeText != null) timeText.setText(time);
        if (fromLocation != null) fromLocation.setText(source);
        if (toLocation != null) toLocation.setText(destination);

        int price = Integer.parseInt(amount);
        int seatCount = Integer.parseInt(seats);
        int total = price * seatCount;

        if (pricePerSeat != null) pricePerSeat.setText("₹" + amount + "/seat");
        if (seatsAvailable != null) seatsAvailable.setText(seats + " seats available");
        if (totalPrice != null) totalPrice.setText("₹" + total);
        if (dateText != null) dateText.setText("Today, " + time);

        // Setup contact card click
        if (contactCard != null) {
            contactCard.setOnClickListener(v -> {
                Toast.makeText(this, "Contacting " + userName + "...", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void setupBookButton() {
        bookNowButton.setOnClickListener(v -> {
            // Show confirmation dialog
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Confirm Booking")
                    .setMessage("Do you want to book this ride?\n\n" +
                            "From: " + source + "\n" +
                            "To: " + destination + "\n" +
                            "Price: ₹" + amount + "/seat\n" +
                            "Total: " + totalPrice.getText())
                    .setPositiveButton("Confirm", (dialog, which) -> {
                        Toast.makeText(this, "Ride booked successfully!", Toast.LENGTH_LONG).show();

                        // Navigate back to My Rides
                        Intent intent = new Intent(RideDetailActivity.this, MyRidesActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}