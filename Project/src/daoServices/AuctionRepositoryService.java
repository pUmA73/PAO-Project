package daoServices;

import dao.AuctionDao;
import models.Auction;

public class AuctionRepositoryService {

    private AuctionDao auctionDao;

    public AuctionRepositoryService() {
        this.auctionDao = new AuctionDao();
    }

    public Auction getAuctionById(String auctionId) {
        Auction auction = auctionDao.read(auctionId);
        if(auction != null) {
            System.out.println(auction);
        } else {
            System.out.println("No auction has been found!");
        }

        return auction;
    }

    public Auction getAuctionByVehicle(String make, String model) {
        Auction auction = auctionDao.read(make, model);
        if(auction != null) {
            System.out.println(auction);
        } else {
            System.out.println("No auction has been found!");
        }

        return auction;
    }

    public void removeAuction(String auctionId) {
        Auction auction = auctionDao.read(auctionId);
        if(auction == null) {
            System.out.println("No auction has been found!");
        } else {
            auctionDao.delete(auction);
            System.out.println("Removed " + auction);
        }
    }

    public void addAuction(Auction auction) {
        if(auction != null) {
            auctionDao.create(auction);
        }
    }

}
