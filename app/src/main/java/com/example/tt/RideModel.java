package com.example.tt;

public class RideModel {

    private final String userName;
    private final String source;
    private final String destination;
    private final String amount;
    private final String seats;
    private final String time;
    private final String carModel;
    private final String rating;

    public RideModel(String userName, String source, String destination,
                     String amount, int seats, String time,
                     String carModel, String rating) {
        this.userName = userName;
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.seats = String.valueOf(seats);
        this.time = time;
        this.carModel = carModel;
        this.rating = rating;
    }

    public String getUserName() { return userName; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public String getAmount() { return amount; }
    public String getSeats() { return seats; }
    public String getTime() { return time; }
    public String getCarModel() { return carModel; }
    public String getRating() { return rating; }
}