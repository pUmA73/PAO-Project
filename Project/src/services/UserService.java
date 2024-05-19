package services;

import daoServices.UserRepositoryService;
import models.User;
import utils.AuditManager;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;

import static utils.Constants.AUDIT_FILE;

public class UserService implements CrudService {
    private final UserRepositoryService databaseService;

    public UserService() throws SQLException {
        this.databaseService = new UserRepositoryService();
    }

    private User setUserInfo(String firstName, String lastName, Scanner scanner) {
        System.out.println("Email: ");
        String email = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        System.out.println("Rating: ");
        int rating = scanner.nextInt();
        scanner.nextLine();

        return new User(firstName, lastName, email, password, rating);
    }

    private void userInit(Scanner scanner) throws SQLException {
        System.out.println("First Name: ");
        String firstName = scanner.nextLine();
        System.out.println("Last Name: ");
        String lastName = scanner.nextLine();

        if(databaseService.getUser(firstName, lastName) != null) {return;}

        User user = setUserInfo(firstName, lastName, scanner);
        try {
            databaseService.addUser(user);
            System.out.println("Created " + user);
            AuditManager.writeToFile(AUDIT_FILE, "add user " + user.getFirstName() + " " + user.getLastName());
        } catch(SQLException e) {
            System.out.println("Cannot create " + user + " exception " + e.getSQLState() + " " + e.getMessage());
        }
    }

    @Override
    public void create(Scanner scanner) {
        try {
            userInit(scanner);
        } catch (SQLException e) {
            System.out.println("The user cannot be created " + e.getSQLState() + " " + e.getMessage());
        }
    }

    @Override
    public void read(Scanner scanner) {
        System.out.println("First Name: ");
        String firstName = scanner.nextLine();
        System.out.println("Last Name: ");
        String lastName = scanner.nextLine();

        try{
            databaseService.getUserByName(firstName, lastName);
            AuditManager.writeToFile(AUDIT_FILE, "user read " + firstName + " " + lastName);
        } catch (SQLException e) {
            System.out.println("User cannot be found + " + e.getSQLState() + " " + e.getMessage());
        }
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.println("First Name: ");
        String firstName = scanner.nextLine();
        System.out.println("Last Name: ");
        String lastName = scanner.nextLine();

        try{
            databaseService.removeUser(firstName, lastName);
            AuditManager.writeToFile(AUDIT_FILE, "user delete " + firstName + " " + lastName);
        } catch (SQLException e) {
            System.out.println("User cannot be deleted + " + e.getSQLState() + " " + e.getMessage());
        }
    }

    @Override
    public void update(Scanner scanner) {
        System.out.println("First Name: ");
        String firstName = scanner.nextLine();
        System.out.println("Last Name: ");
        String lastName = scanner.nextLine();

        User user = databaseService.getUser(firstName, lastName);
        if(user == null) {return;}

        User userUpdate = setUserInfo(firstName, lastName, scanner);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(userUpdate.getEmail());
        user.setPassword(userUpdate.getPassword());
        user.setRating((userUpdate.getRating()));

        try{
            databaseService.updateUser(user);
        } catch(SQLException e) {
            System.out.println("User cannot be updated " + e.getSQLState() + " " + e.getMessage());
        }
    }
}
