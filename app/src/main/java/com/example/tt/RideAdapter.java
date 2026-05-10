package com.example.tt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class RideAdapter extends RecyclerView.Adapter<RideAdapter.ViewHolder> {

    Context context;
    ArrayList<RideModel> list;
    OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(RideModel ride);
    }

    public RideAdapter(Context context,
                       ArrayList<RideModel> list,
                       OnBookClickListener listener) {

        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_ride_result, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RideModel ride = list.get(position);

        holder.userName.setText(ride.getUserName());
        holder.source.setText(ride.getSource());
        holder.destination.setText(ride.getDestination());
        holder.amount.setText("₹" + ride.getAmount());
        holder.seats.setText(ride.getSeats() + " Seats");

        holder.bookBtn.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBookClick(ride);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView userName, source, destination, amount, seats;
        MaterialButton bookBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name);
            source = itemView.findViewById(R.id.trip_source);
            destination = itemView.findViewById(R.id.trip_destination);
            amount = itemView.findViewById(R.id.trip_amount);
            seats = itemView.findViewById(R.id.trip_seats);

            bookBtn = itemView.findViewById(R.id.btn_book);
        }
    }
}
