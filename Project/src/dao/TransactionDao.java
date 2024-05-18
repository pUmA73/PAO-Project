package dao;

import models.*;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDao implements DaoInterface<Transaction> {

    private static TransactionDao transactionDao;

    private final Connection connection = DatabaseConnection.getConnection();

    public TransactionDao() throws SQLException {}

    public static TransactionDao getInstance() throws SQLException {
        if(transactionDao == null) {
            transactionDao = new TransactionDao();
        }
        return transactionDao;
    }

    @Override
    public void add(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO auctionsapp_schema.transaction (buyerId, sellerId, vehicleId, finalPrice) " +
                "VALUES (?, ?, ?, ?);";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, transaction.getBuyer().getUserId());
            statement.setInt(2, transaction.getSeller().getUserId());
            statement.setInt(3, transaction.getSoldVehicle().getVehicleId());
            statement.setDouble(4, transaction.getFinalPrice());
            statement.executeUpdate();
        }
    }

    @Override
    public Transaction read(int id) throws SQLException {
        String sql = "SELECT * FROM auctionsapp_schema.transaction WHERE transactionId = ?";
        ResultSet rs = null;
        Transaction transaction = null;

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            rs = statement.executeQuery();

            while(rs.next()) {
                transaction = new Transaction();
                transaction.setTransactionId(id);
                transaction.setFinalPrice(rs.getDouble("finalPrice"));
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
        }

        if(transaction != null) {
            User buyer = UserDao.getInstance().read(rs.getInt("buyerId"));
            User seller = UserDao.getInstance().read(rs.getInt("sellerId"));
            Car car = CarDao.getInstance().read(rs.getInt("vehicleId"));
            Motorcycle motorcycle = MotorcycleDao.getInstance().read(rs.getInt("vehicleId"));

            if(seller != null) {
                transaction.setSeller(seller);
            }
            if(buyer != null) {
                transaction.setBuyer(buyer);
            }
            if(car != null) {
                transaction.setSoldVehicle(car);
            } else {
                transaction.setSoldVehicle(motorcycle);
            }
        }
        return transaction;
    }

    public Transaction readLastTransaction() throws SQLException {
        String sql = "SELECT transactionId FROM auctionsapp_schema.transaction ORDER BY transactionId DESC LIMIT 1";
        ResultSet rs = null;
        Transaction transaction = null;

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            rs = statement.executeQuery();

            while(rs.next()) {
                transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("transactionId"));
                transaction.setFinalPrice(rs.getDouble("finalPrice"));
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
        }

        if(transaction != null) {
            User buyer = UserDao.getInstance().read(rs.getInt("buyerId"));
            User seller = UserDao.getInstance().read(rs.getInt("sellerId"));
            Car car = CarDao.getInstance().read(rs.getInt("vehicleId"));
            Motorcycle motorcycle = MotorcycleDao.getInstance().read(rs.getInt("vehicleId"));

            if(seller != null) {
                transaction.setSeller(seller);
            }
            if(buyer != null) {
                transaction.setBuyer(buyer);
            }
            if(car != null) {
                transaction.setSoldVehicle(car);
            } else {
                transaction.setSoldVehicle(motorcycle);
            }
        }
        return transaction;
    }

    @Override
    public void delete(Transaction transaction) throws SQLException {
        String sql = "DELETE FROM auctionsapp_schema.transaction t WHERE t.transactionId = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, transaction.getTransactionId());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Transaction transaction) throws SQLException {
        String sql = "UPDATE auctionsapp_schema.transaction SET sellerId = ?, " +
                "buyerId = ?, vehicleId = ?, finalPrice = ? " +
                "WHERE transactionId = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, transaction.getSeller().getUserId());
            statement.setInt(2, transaction.getBuyer().getUserId());
            statement.setInt(3, transaction.getSoldVehicle().getVehicleId());
            statement.setDouble(4, transaction.getFinalPrice());
            statement.setInt(5, transaction.getTransactionId());
            statement.executeUpdate();
        }
    }
}
