package models;

public class Transaction {
    private int transactionId;
    private User seller;
    private User buyer;
    private Vehicle soldVehicle;
    private double finalPrice;

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
}
