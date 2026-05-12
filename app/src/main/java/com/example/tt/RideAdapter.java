package com.example.tt;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class RideAdapter extends RecyclerView.Adapter<RideAdapter.RideViewHolder> {

    private List<RideModel> rideList;
    private OnRideClickListener listener;
    private android.content.Context context;

    public interface OnRideClickListener {
        void onRideClick(RideModel ride);
    }

    public RideAdapter(android.content.Context context, List<RideModel> rideList, OnRideClickListener listener) {
        this.context = context;
        this.rideList = rideList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ride_result, parent, false);
        return new RideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RideViewHolder holder, int position) {
        RideModel ride = rideList.get(position);

        holder.driverName.setText(ride.getUserName());
        holder.carInfo.setText(ride.getCarModel());

        // Handle rating - check if exists in layout
        if (holder.rating != null) {
            holder.rating.setText(ride.getRating());
        }

        holder.timeText.setText(ride.getTime());
        holder.fromLocation.setText(ride.getSource());
        holder.toLocation.setText(ride.getDestination());
        holder.priceText.setText("₹" + ride.getAmount());
        holder.seatsText.setText(ride.getSeats() + " seats");

        // Calculate total price (price per seat * number of seats)
        int pricePerSeat = Integer.parseInt(ride.getAmount());
        int seatCount = Integer.parseInt(ride.getSeats());
        int totalPrice = pricePerSeat * seatCount;
        holder.totalPrice.setText("₹" + totalPrice);

        holder.bookButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRideClick(ride);
            }
        });

        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRideClick(ride);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }

    public static class RideViewHolder extends RecyclerView.ViewHolder {
        public TextView driverName;
        public TextView carInfo;
        public TextView rating;
        public TextView timeText;
        public TextView fromLocation;
        public TextView toLocation;
        public TextView priceText;
        public TextView seatsText;
        public TextView totalPrice;
        public MaterialButton bookButton;
        public CardView cardView;
        ImageView carIcon;

        public RideViewHolder(@NonNull View itemView) {
            super(itemView);
            driverName = itemView.findViewById(R.id.driverName);
            carInfo = itemView.findViewById(R.id.carInfo);

            // Try to find rating, if not present, it will be null
            rating = itemView.findViewById(R.id.rating);

            timeText = itemView.findViewById(R.id.timeText);
            fromLocation = itemView.findViewById(R.id.fromLocation);
            toLocation = itemView.findViewById(R.id.toLocation);
            priceText = itemView.findViewById(R.id.priceText);
            seatsText = itemView.findViewById(R.id.seatsText);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            bookButton = itemView.findViewById(R.id.bookButton);
            cardView = itemView.findViewById(R.id.rideCard);
            carIcon = itemView.findViewById(R.id.carIcon);
        }
    }
}