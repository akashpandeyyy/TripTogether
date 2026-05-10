package com.example.tt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tt.R;
import com.example.tt.RideModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class RideAdapter extends RecyclerView.Adapter<RideAdapter.RideViewHolder> {

    Context context;
    ArrayList<RideModel> list;

    public RideAdapter(Context context, ArrayList<RideModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_ride_result, parent, false);

        return new RideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RideViewHolder holder, int position) {

        RideModel model = list.get(position);

        holder.userName.setText(model.getUserName());
        holder.source.setText(model.getSource());
        holder.destination.setText(model.getDestination());
        holder.amount.setText("₹" + model.getAmount());
        holder.seats.setText(model.getSeats() + " Seats");

        holder.bookBtn.setOnClickListener(v -> {

            Toast.makeText(context,
                    "Ride Booked Successfully 🚖",
                    Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RideViewHolder extends RecyclerView.ViewHolder {

        TextView userName, source, destination, amount, seats;
        MaterialButton bookBtn;

        public RideViewHolder(@NonNull View itemView) {
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