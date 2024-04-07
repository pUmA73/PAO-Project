package models;

import java.time.LocalDate;

public class Offer {
    protected String offerId;
    protected User buyer;
    protected Auction listing;
    protected double bid;
    protected LocalDate offerTime;

    // Setters
    public void setOfferId(String offerId) {this.offerId = offerId;}

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setListing(Auction listing) {
        this.listing = listing;
    }

    public void setBid(double bid) {this.bid = bid;}

    public void setOfferTime(LocalDate offerTime) {
        this.offerTime = offerTime;
    }

    // Getters
    public String getOfferId() {return offerId;}

    public User getBuyer() {
        return buyer;
    }

    public Auction getListing() {
        return listing;
    }

    public double getBid() {return bid;}

    public LocalDate getOfferTime() {
        return offerTime;
    }
}
