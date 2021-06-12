package com.abhishek.parkingsystemtest.Models;

public class ParkingSlot {

    boolean booked;
    String userId;
    String slotId;
    String transactionId;
    boolean parked;
    boolean elapsed_timer;
    String licensePlate;

    public ParkingSlot() {
    }

    public ParkingSlot(boolean booked, String userId, String slotId, String transactionId,
                       boolean parked, boolean elapsed_timer, String licensePlate) {
        this.booked = booked;
        this.userId = userId;
        this.slotId = slotId;
        this.transactionId = transactionId;
        this.parked = parked;
        this.elapsed_timer = elapsed_timer;
        this.licensePlate = licensePlate;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public boolean isParked() {
        return parked;
    }

    public void setParked(boolean parked) {
        this.parked = parked;
    }

    public boolean isElapsed_timer() {
        return elapsed_timer;
    }

    public void setElapsed_timer(boolean elapsed_timer) {
        this.elapsed_timer = elapsed_timer;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
