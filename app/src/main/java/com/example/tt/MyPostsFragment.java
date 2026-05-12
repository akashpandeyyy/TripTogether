package com.example.tt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyPostsFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayout emptyLayout;
    private Button postRideButton;
    private PostsAdapter adapter;
    private List<MyRidesActivity.RideItem> rideList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_posts, container, false);

        recyclerView = view.findViewById(R.id.postsRecyclerView);
        emptyLayout = view.findViewById(R.id.emptyPostsLayout);
        postRideButton = view.findViewById(R.id.postRideButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadDummyPosts();

        postRideButton.setOnClickListener(v -> {
            // Navigate to Post Ride Activity
            android.content.Intent intent = new android.content.Intent(getActivity(), PostTripActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void loadDummyPosts() {
        rideList = new ArrayList<>();

        rideList.add(new MyRidesActivity.RideItem(
                "Rewa → Bhopal",
                "Today, 08:30 AM",
                "₹270",
                "3 seats",
                "2/3",
                "Active"
        ));

        rideList.add(new MyRidesActivity.RideItem(
                "Bhopal → Indore",
                "Tomorrow, 09:00 AM",
                "₹350",
                "4 seats",
                "1/4",
                "Active"
        ));

        rideList.add(new MyRidesActivity.RideItem(
                "Rewa → Satna",
                "2024-12-25, 12:00 PM",
                "₹150",
                "4 seats",
                "0/4",
                "Completed"
        ));

        if (rideList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            adapter = new PostsAdapter(rideList);
            recyclerView.setAdapter(adapter);
        }
    }

    // Adapter for Posts
    class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
        private List<MyRidesActivity.RideItem> rides;

        PostsAdapter(List<MyRidesActivity.RideItem> rides) {
            this.rides = rides;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_posted_ride, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MyRidesActivity.RideItem ride = rides.get(position);
            holder.routeText.setText(ride.route);
            holder.dateTimeText.setText(ride.dateTime);
            holder.priceText.setText(ride.price);
            holder.seatsText.setText(ride.seats);
            holder.bookingsText.setText(ride.bookings);
            setStatusBadge(holder.statusBadge, ride.status);
        }

        private void setStatusBadge(TextView badge, String status) {
            badge.setText(status);
            if (status.equals("Active")) {
                badge.setBackgroundResource(R.drawable.status_badge_active);
            } else {
                badge.setBackgroundResource(R.drawable.status_badge_completed);
            }
        }

        @Override
        public int getItemCount() {
            return rides.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView routeText, dateTimeText, priceText, seatsText, bookingsText, statusBadge;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                routeText = itemView.findViewById(R.id.routeText);
                dateTimeText = itemView.findViewById(R.id.dateTimeText);
                priceText = itemView.findViewById(R.id.priceText);
                seatsText = itemView.findViewById(R.id.seatsText);
                bookingsText = itemView.findViewById(R.id.bookingsText);
                statusBadge = itemView.findViewById(R.id.statusBadge);
            }
        }
    }
}