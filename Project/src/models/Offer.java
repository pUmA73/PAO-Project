package models;

import java.sql.Date;
import java.time.LocalDate;

public class Offer {
    private int offerId;
    protected User buyer;
    protected Auction listing;
    protected double bid;
    protected Date offerTime;

    // Setters
    public void setOfferId(int offerId) {this.offerId = offerId;}

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setListing(Auction listing) {
        this.listing = listing;
    }

    public void setBid(double bid) {this.bid = bid;}

    public void setOfferTime(Date offerTime) {
        this.offerTime = offerTime;
    }

    // Getters
    public int getOfferId() {return offerId;}

    public User getBuyer() {
        return buyer;
    }

    public Auction getListing() {
        return listing;
    }

    public double getBid() {return bid;}

    public Date getOfferTime() {
        return offerTime;
    }
}
