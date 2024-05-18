package daoServices;

import dao.UserDao;
import models.User;

import java.sql.SQLException;

public class UserRepositoryService {
    private final UserDao userDao = UserDao.getInstance();

    public UserRepositoryService() throws SQLException {}

    public User getUserByName(String firstName, String lastName) throws SQLException {
        User user = userDao.read(firstName, lastName);
        if(user != null) {
            System.out.println(user);
        } else {
            System.out.println("No user found!");
        }

        return user;
    }

    public User getUser(String firstName, String lastName) {
        User user = null;
        try {
            user = getUserByName(firstName, lastName);
            if(user == null) {
                return null;
            }
        } catch(SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return user;
    }

    public void removeUser(String firstName, String lastName) throws SQLException {
        User user = getUser(firstName, lastName);
        if(user == null) return;

        userDao.delete(user);
        System.out.println("Removed " + user);
    }

    public void addUser(User user) throws SQLException {
        if(user != null) {
            userDao.add(user);
        }
    }

    public void updateUser(User user) throws SQLException {
        if(user != null) {
            userDao.update(user);
        }
    }
}
