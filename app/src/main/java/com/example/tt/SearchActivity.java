package com.example.tt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout emptyLayout;
    private TextView emptyTextView;
    private CircularProgressIndicator progressBar;
    private MaterialToolbar toolbar;
    private MaterialCardView searchInfoCard;
    private TextView sourceText, destinationText;

    private ArrayList<RideModel> rideList;
    private RideAdapter adapter;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        // Window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            androidx.core.graphics.Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupToolbar();
        getIntentData();
        setupRecyclerView();
        loadRidesWithDelay();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.sr_result);
        emptyLayout = findViewById(R.id.empty_layout);
        emptyTextView = findViewById(R.id.empty_view);
        progressBar = findViewById(R.id.progress_bar);
        toolbar = findViewById(R.id.toolbar);
        searchInfoCard = findViewById(R.id.searchInfoCard);

        handler = new Handler(Looper.getMainLooper());
        rideList = new ArrayList<>();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
    }

    private void getIntentData() {
        String source = getIntent().getStringExtra("source");
        String destination = getIntent().getStringExtra("destination");

        // Update search info card with route
        TextView routeText = findViewById(R.id.routeText);
        if (routeText != null && source != null && destination != null) {
            routeText.setText(source + " → " + destination);
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void loadRidesWithDelay() {
        // Show loading
        showLoading(true);

        // Simulate network delay
        handler.postDelayed(() -> {
            loadDummyRides();
            setupAdapter();
            showLoading(false);
            checkEmptyState();
        }, 1500);
    }

    private void loadDummyRides() {
        rideList.clear();

        // Rewa to Bhopal rides
        rideList.add(new RideModel("Rahul Sharma", "Rewa", "Bhopal", "270", 3, "08:30 AM", "Toyota Innova", "⭐ 4.8"));
        rideList.add(new RideModel("Aman Verma", "Rewa", "Bhopal", "250", 2, "10:00 AM", "Hyundai i20", "⭐ 4.9"));
        rideList.add(new RideModel("Priya Singh", "Rewa", "Bhopal", "280", 4, "02:30 PM", "Maruti Suzuki", "⭐ 4.7"));

        // Bhopal to Indore rides
        rideList.add(new RideModel("Saurabh Patel", "Bhopal", "Indore", "350", 3, "09:00 AM", "Honda City", "⭐ 4.8"));
        rideList.add(new RideModel("Neha Gupta", "Bhopal", "Indore", "330", 2, "11:30 AM", "Volkswagen", "⭐ 4.9"));
        rideList.add(new RideModel("Vikram Singh", "Bhopal", "Indore", "370", 4, "04:00 PM", "Mahindra XUV", "⭐ 4.6"));

        // Indore to Bhopal rides
        rideList.add(new RideModel("Rajesh K", "Indore", "Bhopal", "340", 4, "08:00 AM", "Swift Dezire", "⭐ 4.7"));
        rideList.add(new RideModel("Meera S", "Indore", "Bhopal", "360", 2, "03:00 PM", "Honda Amaze", "⭐ 4.8"));

        // Get source and destination from intent for specific filtering
        String source = getIntent().getStringExtra("source");
        String destination = getIntent().getStringExtra("destination");

        if (source != null && destination != null && !source.isEmpty() && !destination.isEmpty()) {
            filterRidesByRoute(source, destination);
        }
    }

    private void filterRidesByRoute(String source, String destination) {
        ArrayList<RideModel> filteredList = new ArrayList<>();
        for (RideModel ride : rideList) {
            if (ride.getSource().equalsIgnoreCase(source) &&
                    ride.getDestination().equalsIgnoreCase(destination)) {
                filteredList.add(ride);
            }
        }
        rideList.clear();
        rideList.addAll(filteredList);
    }

    private void setupAdapter() {
        adapter = new RideAdapter(this, rideList, new RideAdapter.OnRideClickListener() {
            @Override
            public void onRideClick(RideModel ride) {
                Intent intent = new Intent(SearchActivity.this, RideDetailActivity.class);
                intent.putExtra("userName", ride.getUserName());
                intent.putExtra("source", ride.getSource());
                intent.putExtra("destination", ride.getDestination());
                intent.putExtra("amount", ride.getAmount());
                intent.putExtra("seats", ride.getSeats());
                intent.putExtra("time", ride.getTime());
                intent.putExtra("carModel", ride.getCarModel());
                intent.putExtra("rating", ride.getRating());
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void checkEmptyState() {
        if (rideList.isEmpty()) {
            emptyLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}