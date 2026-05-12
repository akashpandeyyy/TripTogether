package com.example.tt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class RideListFragment extends Fragment {

    private static final String ARG_TYPE = "type";
    private String rideType;

    private RecyclerView recyclerView;
    private LinearLayout emptyLayout;
    private Button actionButton;
    private RideAdapter adapter;
    private List<RideItem> rideList;

    public static RideListFragment newInstance(String type) {
        RideListFragment fragment = new RideListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rideType = getArguments().getString(ARG_TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        if ("posted".equals(rideType)) {
            view = inflater.inflate(R.layout.fragment_my_posts, container, false);
            recyclerView = view.findViewById(R.id.postsRecyclerView);
            emptyLayout = view.findViewById(R.id.emptyPostsLayout);
            actionButton = view.findViewById(R.id.postRideButton);
        } else {
            view = inflater.inflate(R.layout.fragment_my_bookings, container, false);
            recyclerView = view.findViewById(R.id.bookingsRecyclerView);
            emptyLayout = view.findViewById(R.id.emptyBookingsLayout);
            actionButton = view.findViewById(R.id.findRideButton);
        }

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        loadData();
        setupActionButton();

        return view;
    }

    private void loadData() {
        rideList = new ArrayList<>();

        if ("posted".equals(rideType)) {
            // Posted rides data
            rideList.add(new RideItem(
                    "Rewa → Bhopal",
                    "Today, 08:30 AM",
                    "₹270",
                    "3 seats",
                    "2/3",
                    "Active",
                    "posted"
            ));

            rideList.add(new RideItem(
                    "Bhopal → Indore",
                    "Tomorrow, 09:00 AM",
                    "₹350",
                    "4 seats",
                    "1/4",
                    "Active",
                    "posted"
            ));

            rideList.add(new RideItem(
                    "Rewa → Satna",
                    "2024-12-25, 12:00 PM",
                    "₹150",
                    "4 seats",
                    "0/4",
                    "Completed",
                    "posted"
            ));

        } else {
            // Booked rides data with phone numbers for call
            rideList.add(new RideItem(
                    "Rewa → Bhopal",
                    "Today, 08:30 AM",
                    "₹540",
                    "2 seats",
                    "Confirmed",
                    "booked",
                    "Aman Tiwari",
                    "Toyota Innova · MP09 AB 1234",
                    "9876543210"
            ));

            rideList.add(new RideItem(
                    "Bhopal → Indore",
                    "Tomorrow, 09:00 AM",
                    "₹1050",
                    "3 seats",
                    "Confirmed",
                    "booked",
                    "Saurabh Patel",
                    "Honda City · MP01 GH 3456",
                    "9876543222"
            ));

            rideList.add(new RideItem(
                    "Rewa → Bhopal",
                    "2024-12-25, 10:00 AM",
                    "₹250",
                    "1 seat",
                    "Upcoming",
                    "booked",
                    "Rahul Mehta",
                    "Hyundai i20 · MP04 CD 5678",
                    "9876543333"
            ));

            rideList.add(new RideItem(
                    "Bhopal → Rewa",
                    "2024-12-26, 02:30 PM",
                    "₹1000",
                    "2 seats",
                    "Pending",
                    "booked",
                    "Priya Sharma",
                    "Maruti Suzuki · MP07 EF 9012",
                    "9876544444"
            ));
        }

        if (rideList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            adapter = new RideAdapter(rideList, rideType);
            recyclerView.setAdapter(adapter);
        }
    }

    private void setupActionButton() {
        if (actionButton != null) {
            if ("posted".equals(rideType)) {
                actionButton.setText("Post a Ride");
                actionButton.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), PostTripActivity.class);
                    startActivity(intent);
                });
            } else {
                actionButton.setText("Find a Ride");
                actionButton.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                });
            }
        }
    }

    // Ride Item Model Class
    public static class RideItem {
        String route;
        String dateTime;
        String price;
        String seats;
        String bookings;
        String status;
        String type;
        String driverName;
        String carInfo;
        String phoneNumber;

        // Constructor for posted rides
        public RideItem(String route, String dateTime, String price,
                        String seats, String bookings, String status,
                        String type, String driverName, String carInfo, String phoneNumber) {
            this.route = route;
            this.dateTime = dateTime;
            this.price = price;
            this.seats = seats;
            this.bookings = bookings;
            this.status = status;
            this.type = type;
            this.driverName = driverName;
            this.carInfo = carInfo;
            this.phoneNumber = phoneNumber;
        }

        // Constructor for posted rides (simplified)
        public RideItem(String route, String dateTime, String price,
                        String seats, String bookings, String status, String type) {
            this(route, dateTime, price, seats, bookings, status, type, null, null, null);
        }

        // Constructor for booked rides
        public RideItem(String route, String dateTime, String price,
                        String seats, String status, String type,
                        String driverName, String carInfo, String phoneNumber) {
            this(route, dateTime, price, seats, "", status, type, driverName, carInfo, phoneNumber);
        }
    }

    // Ride Adapter
    class RideAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<RideItem> rides;
        private String type;

        RideAdapter(List<RideItem> rides, String type) {
            this.rides = rides;
            this.type = type;
        }

        @Override
        public int getItemViewType(int position) {
            return "posted".equals(type) ? 0 : 1;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (viewType == 0) {
                View view = inflater.inflate(R.layout.item_posted_ride, parent, false);
                return new PostedRideViewHolder(view);
            } else {
                View view = inflater.inflate(R.layout.item_booked_ride, parent, false);
                return new BookedRideViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            RideItem ride = rides.get(position);

            if (holder instanceof PostedRideViewHolder) {
                PostedRideViewHolder vh = (PostedRideViewHolder) holder;
                vh.routeText.setText(ride.route);
                vh.dateTimeText.setText(ride.dateTime);
                vh.priceText.setText(ride.price);
                vh.seatsText.setText(ride.seats);
                setStatusBadge(vh.statusBadge, ride.status);
            } else if (holder instanceof BookedRideViewHolder) {
                BookedRideViewHolder vh = (BookedRideViewHolder) holder;
                vh.driverName.setText(ride.driverName);
                vh.carInfo.setText(ride.carInfo);
                vh.routeText.setText(ride.route);
                vh.dateTimeText.setText(ride.dateTime);
                vh.seatsBooked.setText(ride.seats);
                vh.totalPrice.setText(ride.price);
                setStatusBadge(vh.bookingStatus, ride.status);

                // Call button functionality
                vh.callButton.setOnClickListener(v -> {
                    makePhoneCall(ride.phoneNumber);
                });
            }
        }

        private void setStatusBadge(TextView badge, String status) {
            badge.setText(status);
            switch (status.toLowerCase()) {
                case "active":
                    badge.setBackgroundResource(R.drawable.status_badge_active);
                    break;
                case "confirmed":
                    badge.setBackgroundResource(R.drawable.status_badge_confirmed);
                    break;
                case "upcoming":
                    badge.setBackgroundResource(R.drawable.status_badge_upcoming);
                    break;
                case "pending":
                    badge.setBackgroundResource(R.drawable.status_badge_pending);
                    break;
                case "completed":
                    badge.setBackgroundResource(R.drawable.status_badge_completed);
                    break;
                default:
                    badge.setBackgroundResource(R.drawable.status_badge_confirmed);
            }
        }

        private void makePhoneCall(String phoneNumber) {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Phone number not available", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public int getItemCount() {
            return rides.size();
        }

        // ViewHolder for Posted Rides
        class PostedRideViewHolder extends RecyclerView.ViewHolder {
            TextView routeText, dateTimeText, priceText, seatsText, statusBadge;

            PostedRideViewHolder(@NonNull View itemView) {
                super(itemView);
                routeText = itemView.findViewById(R.id.routeText);
                dateTimeText = itemView.findViewById(R.id.dateTimeText);
                priceText = itemView.findViewById(R.id.priceText);
                seatsText = itemView.findViewById(R.id.seatsText);
                statusBadge = itemView.findViewById(R.id.statusBadge);
            }
        }

        // ViewHolder for Booked Rides
        class BookedRideViewHolder extends RecyclerView.ViewHolder {
            TextView driverName, carInfo, routeText, dateTimeText, seatsBooked, totalPrice, bookingStatus;
            MaterialButton callButton;

            BookedRideViewHolder(@NonNull View itemView) {
                super(itemView);
                driverName = itemView.findViewById(R.id.driverName);
                carInfo = itemView.findViewById(R.id.carInfo);
                routeText = itemView.findViewById(R.id.routeText);
                dateTimeText = itemView.findViewById(R.id.dateTimeText);
                seatsBooked = itemView.findViewById(R.id.seatsBooked);
                totalPrice = itemView.findViewById(R.id.totalPrice);
                bookingStatus = itemView.findViewById(R.id.bookingStatus);
                callButton = itemView.findViewById(R.id.callButton);
            }
        }
    }
}