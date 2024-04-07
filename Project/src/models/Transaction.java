package models;

public class Transaction {
    private User seller;
    private User buyer;
    private Vehicle soldVehicle;
    private double finalPrice;

    // Setters
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
