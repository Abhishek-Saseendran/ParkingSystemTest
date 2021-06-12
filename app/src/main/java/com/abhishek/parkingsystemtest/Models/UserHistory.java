package com.abhishek.parkingsystemtest.Models;

import com.google.firebase.Timestamp;

public class UserHistory {

    String transactionId;
    Timestamp bookingTime;
    Timestamp arrival;
    Timestamp exit;
    double amount;

    public UserHistory() {
    }

    public UserHistory(String transactionId, Timestamp bookingTime, Timestamp arrival, Timestamp exit, double amount) {
        this.transactionId = transactionId;
        this.bookingTime = bookingTime;
        this.arrival = arrival;
        this.exit = exit;
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Timestamp getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Timestamp bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Timestamp getArrival() {
        return arrival;
    }

    public void setArrival(Timestamp arrival) {
        this.arrival = arrival;
    }

    public Timestamp getExit() {
        return exit;
    }

    public void setExit(Timestamp exit) {
        this.exit = exit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
