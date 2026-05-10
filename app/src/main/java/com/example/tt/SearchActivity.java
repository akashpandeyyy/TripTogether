package com.example.tt;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tt.adapter.RideAdapter;
import com.example.tt.RideModel;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView emptyView;

    private ArrayList<RideModel> rideList;
    private RideAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.sr_result);
        emptyView = findViewById(R.id.empty_view);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rideList = new ArrayList<>();

        loadDummyRides();

        adapter = new RideAdapter(this, rideList);

        recyclerView.setAdapter(adapter);

        checkEmptyState();
    }

    private void loadDummyRides() {

        rideList.add(new RideModel(
                "Rahul Sharma",
                "Rewa",
                "Bhopal",
                "650",
                3
        ));

        rideList.add(new RideModel(
                "Aman Verma",
                "Indore",
                "Ujjain",
                "300",
                2
        ));

        rideList.add(new RideModel(
                "Shubham Tiwari",
                "Satna",
                "Jabalpur",
                "450",
                4
        ));

        rideList.add(new RideModel(
                "Aditya Mishra",
                "Bhopal",
                "Indore",
                "350",
                1
        ));

        rideList.add(new RideModel(
                "Vikas Singh",
                "Rewa",
                "Satna",
                "150",
                2
        ));
    }

    private void checkEmptyState() {

        if (rideList.isEmpty()) {

            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        } else {

            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}