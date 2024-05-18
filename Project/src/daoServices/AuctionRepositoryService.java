package daoServices;

import dao.AuctionDao;
import models.Auction;

import java.sql.SQLException;

public class AuctionRepositoryService {
    private final AuctionDao auctionDao = AuctionDao.getInstance();

    public AuctionRepositoryService() throws SQLException {}

    public Auction getAuction(int auctionId) throws SQLException {
        Auction auction = auctionDao.read(auctionId);
        if(auction != null) {
            System.out.println(auction);
        } else {
            System.out.println("No auction found!");
        }

        return auction;
    }

    public void removeAuction(int auctionId) throws SQLException {
        Auction auction = getAuction(auctionId);
        if(auction == null) return;

        auctionDao.delete(auction);
        System.out.println("Removed " + auction);
    }

    public void addAuction(Auction auction) throws SQLException {
        if(auction != null) {
            auctionDao.add(auction);
        }
    }

    public void updateAuction(Auction auction) throws SQLException {
        if(auction != null) {
            auctionDao.update(auction);
        }
    }
}
