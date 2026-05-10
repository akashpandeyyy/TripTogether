// RideModel.java
package com.example.tt;

public class RideModel {

    private final String userName;
    private final String source;
    private final String destination;
    private final String amount;
    private final String seats;


    public RideModel(String userName,
                     String source,
                     String destination,
                     String amount,
                     String seats) {

        this.userName = userName;
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.seats = seats;
    }

    public String getUserName() {
        return userName;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getAmount() {
        return amount;
    }

    public String getSeats() {
        return seats;
    }


}