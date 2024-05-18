package dao;

import models.Auction;
import models.Car;
import models.Motorcycle;
import models.User;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuctionDao implements DaoInterface<Auction> {

    private static AuctionDao auctionDao;

    private final Connection connection = DatabaseConnection.getConnection();

    public AuctionDao() throws SQLException {}

    public static AuctionDao getInstance() throws SQLException {
        if(auctionDao == null) {
            auctionDao = new AuctionDao();
        }
        return auctionDao;
    }

    @Override
    public void add(Auction auction) throws SQLException {
        String sql = "INSERT INTO auctionsapp_schema.auction (vehicleId, description, startingPrice, highestBid, startTime, finishTime, sellerId) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, auction.getAuctionedVehicle().getVehicleId());
            statement.setString(2, auction.getDescription());
            statement.setDouble(3, auction.getStartingPrice());
            statement.setDouble(4, auction.getHighestBid());
            statement.setDate(5, auction.getStartTime());
            statement.setDate(6, auction.getFinishTime());
            statement.setInt(7, auction.getSeller().getUserId());
            statement.executeUpdate();
        }
    }

    @Override
    public Auction read(int auctionId) throws SQLException {
        String sql = "SELECT * FROM auctionsapp_schema.auction WHERE auctionId = ?";
        ResultSet rs = null;
        Auction auction = null;

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, auctionId);
            rs = statement.executeQuery();

            while(rs.next()) {
                auction = new Auction();
                auction.setAuctionId(auctionId);
                auction.setDescription(rs.getString("description"));
                auction.setStartingPrice((rs.getDouble("startingPrice")));
                auction.setHighestBid(rs.getDouble("highestBid"));
                auction.setStartTime(rs.getDate("startTime"));
                auction.setFinishTime(rs.getDate("finishTime"));
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
        }

        if(auction != null) {
            // Setam vehiculul scos la licitatie. Acesta poate fi car sau motorcycle
            Car car = CarDao.getInstance().read(rs.getInt("vehicleId"));
            Motorcycle motorcycle = MotorcycleDao.getInstance().read(rs.getInt("vehicleId"));

            if(car != null) {
                auction.setAuctionedVehicle(car);
            } else {
                auction.setAuctionedVehicle(motorcycle);
            }

            User user = UserDao.getInstance().read(rs.getInt("userId"));

            if(user != null) {
                auction.setSeller(user);
            }
        }

        return auction;
    }

    @Override
    public void delete(Auction auction) throws SQLException {
        String sql = "DELETE FROM auctionsapp_schema.auction a WHERE a.auctionId = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, auction.getAuctionId());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Auction auction) throws SQLException {
        String sql = "UPDATE auctionsapp_schema.auction SET vehicleId = ?, " +
                "description = ?, startingPrice = ?, highestBid = ?, " +
                "startTime = ?, finishTime = ?, sellerId = ? " +
                "WHERE auctionId = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, auction.getAuctionedVehicle().getVehicleId());
            statement.setString(2, auction.getDescription());
            statement.setDouble(3, auction.getStartingPrice());
            statement.setDouble(4, auction.getHighestBid());
            statement.setDate(5, auction.getStartTime());
            statement.setDate(6, auction.getFinishTime());
            statement.setInt(7, auction.getSeller().getUserId());
            statement.setInt(8, auction.getAuctionId());
            statement.executeUpdate();
        }
    }
}
