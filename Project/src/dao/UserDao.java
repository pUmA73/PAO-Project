package dao;

import models.User;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao implements DaoInterface<User>{
    private static UserDao userDao;

    private Connection connection = DatabaseConnection.getConnection();

    public UserDao() throws SQLException {}

    public static UserDao getInstance() throws SQLException {
        if(userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }

    @Override
    public void add(User user) throws SQLException {
        String sql = "INSERT INTO auctionsapp_schema.user (firstName, lastName, email, password, rating) VALUES (?, ?, ?, ?, ?);";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setInt(5, user.getRating());
            statement.executeUpdate();
        }
    }

    @Override
    public User read(String firstName, String lastName) throws SQLException {
        String sql = "SELECT * FROM auctionsapp_schema.user u  WHERE UPPER(u.firstName) = ? AND UPPER(u.lastName) = ?";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, firstName.toUpperCase());
            statement.setString(2, lastName.toUpperCase());
            rs = statement.executeQuery();

            while(rs.next()) {
                User user = new User();
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRating(rs.getInt("rating"));
                return user;
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return null;
    }

    @Override
    public void delete(User user) throws SQLException {
        String sql = "DELETE FROM auctionsapp_schema.user s WHERE s.firstName = ? AND s.lastName = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String sql = "UPDATE auctionsapp_schema.user SET email = ?, " +
                "password = ?, rating = ? WHERE UPPER(firstName) = ? AND " +
                "UPPER(lastName) = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRating());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.executeUpdate();
        }
    }
}
