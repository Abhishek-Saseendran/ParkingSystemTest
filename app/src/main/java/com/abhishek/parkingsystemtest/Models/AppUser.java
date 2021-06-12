package com.abhishek.parkingsystemtest.Models;

public class AppUser {

    String name;
    String license;
    String dlNumber;
    String phone;
    String email;
    double wallet;
    String slotNumber;
    String transactionId;
    boolean parked;
    boolean isReady;

    public AppUser() {
    }

    public AppUser(String name, String license, String phone, String email, double wallet, String dlNumber, String slotNumber,
                   String transactionId, boolean parked, boolean isReady) {
        this.name = name;
        this.license = license;
        this.phone = phone;
        this.email = email;
        this.wallet = wallet;
        this.dlNumber = dlNumber;
        this.slotNumber = slotNumber;
        this.transactionId = transactionId;
        this.parked = parked;
        this.isReady = isReady;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public String getDlNumber() {
        return dlNumber;
    }

    public void setDlNumber(String dlNumber) {
        this.dlNumber = dlNumber;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isParked() {
        return parked;
    }

    public void setParked(boolean parked) {
        this.parked = parked;
    }

    public boolean isIsReady() {
        return isReady;
    }

    public void setIsReady(boolean ready) {
        isReady = ready;
    }
}
