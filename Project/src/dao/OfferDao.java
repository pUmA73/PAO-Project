package dao;

import models.Auction;
import models.Offer;
import models.User;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OfferDao implements DaoInterface<Offer>{

    private static OfferDao offerDao;

    private Connection connection = DatabaseConnection.getConnection();

    public OfferDao() throws SQLException {}

    public static OfferDao getInstance() throws SQLException {
        if(offerDao == null) {
            offerDao = new OfferDao();
        }
        return offerDao;
    }

    @Override
    public void add(Offer offer) throws SQLException {
        String sql = "INSERT INTO auctionsapp_schema.offer (buyerId, auctionId, bid, offerTime) " +
                "VALUES (?, ?, ?, ?);";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, offer.getBuyer().getUserId());
            statement.setInt(2, offer.getListing().getAuctionId());
            statement.setDouble(3, offer.getBid());
            statement.setDate(4, offer.getOfferTime());
            statement.executeUpdate();
        }
    }

    @Override
    public Offer read(int id) throws SQLException {
        String sql = "SELECT * FROM auctionsapp_schema.offer WHERE offerId = ?";
        ResultSet rs = null;
        Offer offer = null;

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            rs = statement.executeQuery();

            while(rs.next()) {
                offer = new Offer();
                offer.setOfferId(id);
                offer.setBid(rs.getDouble("bid"));
                offer.setOfferTime(rs.getDate("offerTime"));
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
        }

        if(offer != null) {
            Auction auction = AuctionDao.getInstance().read(rs.getInt("auctionId"));
            User buyer = UserDao.getInstance().read(rs.getInt("buyerId"));

            if(auction != null) {
                offer.setListing(auction);
            }
            if(buyer != null) {
                offer.setBuyer(buyer);
            }
        }

        return offer;
    }

    @Override
    public void delete(Offer offer) throws SQLException {
        String sql = "DELETE FROM auctionsapp_schema.offer o WHERE o.offerId = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, offer.getOfferId());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Offer offer) throws SQLException {
        String sql = "UPDATE auctionsapp_schema.offer SET buyerId = ?, " +
                "auctionId = ?, bid = ?, offerTime = ? " +
                "WHERE offerId = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, offer.getBuyer().getUserId());
            statement.setInt(2, offer.getListing().getAuctionId());
            statement.setDouble(3, offer.getBid());
            statement.setDate(4, offer.getOfferTime());
            statement.setInt(5, offer.getOfferId());
            statement.executeUpdate();
        }
    }
}
