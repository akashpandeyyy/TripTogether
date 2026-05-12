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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MyBookingsFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayout emptyLayout;
    private Button findRideButton;
    private BookingsAdapter adapter;
    private List<MyRidesActivity.BookingItem> bookingList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bookings, container, false);

        recyclerView = view.findViewById(R.id.bookingsRecyclerView);
        emptyLayout = view.findViewById(R.id.emptyBookingsLayout);
        findRideButton = view.findViewById(R.id.findRideButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadDummyBookings();

        findRideButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void loadDummyBookings() {
        bookingList = new ArrayList<>();

        bookingList.add(new MyRidesActivity.BookingItem(
                "Aman Tiwari",
                "Toyota Innova · MP09 AB 1234",
                "Rewa → Bhopal",
                "Today, 08:30 AM",
                "2 seats",
                "₹540",
                "Confirmed",
                "9876543210"
        ));

        bookingList.add(new MyRidesActivity.BookingItem(
                "Saurabh Patel",
                "Honda City · MP01 GH 3456",
                "Bhopal → Indore",
                "Tomorrow, 09:00 AM",
                "3 seats",
                "₹1050",
                "Confirmed",
                "9876543222"
        ));

        bookingList.add(new MyRidesActivity.BookingItem(
                "Rahul Mehta",
                "Hyundai i20 · MP04 CD 5678",
                "Rewa → Bhopal",
                "2024-12-25, 10:00 AM",
                "1 seat",
                "₹250",
                "Upcoming",
                "9876543333"
        ));

        if (bookingList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            adapter = new BookingsAdapter(bookingList);
            recyclerView.setAdapter(adapter);
        }
    }

    // Adapter for Bookings
    class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> {
        private List<MyRidesActivity.BookingItem> bookings;

        BookingsAdapter(List<MyRidesActivity.BookingItem> bookings) {
            this.bookings = bookings;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_booked_ride, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MyRidesActivity.BookingItem booking = bookings.get(position);

            holder.driverName.setText(booking.driverName);
            holder.carInfo.setText(booking.carInfo);
            holder.routeText.setText(booking.route);
            holder.dateTimeText.setText(booking.dateTime);
            holder.seatsBooked.setText(booking.seats);
            holder.totalPrice.setText(booking.price);
            setStatusBadge(holder.bookingStatus, booking.status);

            // Call button functionality
            holder.callButton.setOnClickListener(v -> {
                makePhoneCall(booking.phoneNumber);
            });
        }

        private void setStatusBadge(TextView badge, String status) {
            badge.setText(status);
            switch (status.toLowerCase()) {
                case "confirmed":
                    badge.setBackgroundResource(R.drawable.status_badge_confirmed);
                    break;
                case "upcoming":
                    badge.setBackgroundResource(R.drawable.status_badge_upcoming);
                    break;
                case "pending":
                    badge.setBackgroundResource(R.drawable.status_badge_pending);
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
            return bookings.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView driverName, carInfo, routeText, dateTimeText, seatsBooked, totalPrice, bookingStatus;
            MaterialButton callButton;

            ViewHolder(@NonNull View itemView) {
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