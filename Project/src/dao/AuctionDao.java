package dao;

import models.Auction;

import java.util.List;
import java.util.ArrayList;

public class AuctionDao {
    private static List<Auction> auctions = new ArrayList<>();

    // Find the auction based on the auctioned vehicle
    public Auction read(String make, String model) {
        if(!auctions.isEmpty()) {
            for(Auction a : auctions) {
                if(a.getAuctionedVehicle().getMake().equals(make) &&
                   a.getAuctionedVehicle().getModel().equals(model)) {
                    return a;
                }
            }
        }
        return null;
    }

    // Find the auction based on the auction ID
    public Auction read(String auctionId) {
        if(!auctions.isEmpty()) {
            for(Auction a : auctions) {
                if(a.getAuctionId().equals(auctionId)) {
                    return a;
                }
            }
        }
        return null;
    }

    public void delete(Auction auction) {
        auctions.remove(auction);
    }

    public void create(Auction auction) {
        auctions.add(auction);
    }

}
