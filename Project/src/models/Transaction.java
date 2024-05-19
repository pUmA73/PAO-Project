package models;

import models.vehicle.Vehicle;

public class Transaction {
    private int transactionId;
    private User seller;
    private User buyer;
    private Vehicle soldVehicle;
    private double finalPrice;

    public Transaction() {}

    public Transaction(User seller, User buyer, Vehicle soldVehicle, double finalPrice) {
        this.seller = seller;
        this.buyer = buyer;
        this.soldVehicle = soldVehicle;
        this.finalPrice = finalPrice;
    }

    // Setters
    public void setTransactionId(int transactionId) {this.transactionId = transactionId;}

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void setSoldVehicle(Vehicle soldVehicle) {
        this.soldVehicle = soldVehicle;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    // Getters
    public int getTransactionId() {return transactionId;}

    public User getBuyer() {
        return buyer;
    }

    public User getSeller() {
        return seller;
    }

    public Vehicle getSoldVehicle() {
        return soldVehicle;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    @Override
    public String toString() {
        return "Transaction {" +
                "Seller: " + getSeller().getFirstName() + " " + getSeller().getLastName() + "\n" +
                "Buyer: " + getBuyer().getFirstName() + " " + getBuyer().getLastName() + "\n" +
                "Sold Vehicle: " + getSoldVehicle().getMake() + " " + getSoldVehicle().getModel() + "\n" +
                "Final Price: " + getFinalPrice() + "\n" +
                "}";
    }
}
