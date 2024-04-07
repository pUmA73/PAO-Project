package models;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ArrayList;

public class Auction {
    private String auctionId;
    private Vehicle auctionedVehicle;
    private String description;
    private double startingPrice;
    private double highestBid;
    private LocalDate startTime;
    private LocalDate finishTime;
    private List<BufferedImage> photos;

    // Setters
    public void setAuctionId(String auctionId) {this.auctionId = auctionId;}
    public void setAuctionedVehicle(Vehicle auctionedVehicle) {
        this.auctionedVehicle = auctionedVehicle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public void setHighestBid(double highestBid) {
        this.highestBid = highestBid;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(LocalDate finishTime) {
        this.finishTime = finishTime;
    }

    // Getters
    public String getAuctionId() {return auctionId;}
    public Vehicle getAuctionedVehicle() {
        return auctionedVehicle;
    }

    public String getDescription() {
        return description;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public LocalDate getFinishTime() {
        return finishTime;
    }
}
