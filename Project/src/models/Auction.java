package models;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.Period;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

public class Auction {
    private int auctionId;
    private Vehicle auctionedVehicle;
    private User seller;
    private String description;
    private double startingPrice;
    private double highestBid;
    private Date startTime;
    private Date finishTime;

    public Auction() {}

    public Auction(Vehicle auctionedVehicle, User seller, String description,
                   double startingPrice, double highestBid, Date startTime, Date finishTime) {
        this.auctionedVehicle = auctionedVehicle;
        this.seller = seller;
        this.description = description;
        this.startingPrice = startingPrice;
        this.highestBid = highestBid;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    // Setters
    public void setAuctionId(int auctionId) {this.auctionId = auctionId;}
    public void setAuctionedVehicle(Vehicle auctionedVehicle) {
        this.auctionedVehicle = auctionedVehicle;
    }

    public void setSeller(User seller) {this.seller = seller;}

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public void setHighestBid(double highestBid) {
        this.highestBid = highestBid;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    // Getters
    public int getAuctionId() {return auctionId;}
    public Vehicle getAuctionedVehicle() {
        return auctionedVehicle;
    }

    public User getSeller() {return seller;}

    public String getDescription() {
        return description;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }
}
