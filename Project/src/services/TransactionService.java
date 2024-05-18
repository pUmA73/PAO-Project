package services;

import daoServices.TransactionRepositoryService;
import daoServices.UserRepositoryService;
import daoServices.VehicleRepositoryService;
import models.Transaction;
import models.User;
import models.Vehicle;
import utils.AuditManager;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;

import static utils.Constants.AUDIT_FILE;

public class TransactionService {
    private final TransactionRepositoryService databseService;

    private final VehicleRepositoryService vehicleDb;

    private final UserRepositoryService userDb;

    public TransactionService() throws SQLException {
        this.databseService = new TransactionRepositoryService();
        this.vehicleDb = new VehicleRepositoryService();
        this.userDb = new UserRepositoryService();
    }

    private Transaction setTransactionInfo(Scanner scanner) {
        System.out.println("Seller First Name: ");
        String sellerFirstName = scanner.nextLine();
        System.out.println("Seller Last Name: ");
        String sellerLastName = scanner.nextLine();
        User seller = userDb.getUser(sellerFirstName, sellerLastName);

        System.out.println("Buyer First Name: ");
        String buyerFirstName = scanner.nextLine();
        System.out.println("Buyer Last Name: ");
        String buyerLastName = scanner.nextLine();
        User buyer = userDb.getUser(buyerFirstName, buyerLastName);

        System.out.println("Vehicle type[car/motorcycle]:");
        String typeOfVehicle = scanner.nextLine();

        System.out.println("Vehicle Make: ");
        String make = scanner.nextLine();
        System.out.println("Vehicle Model: ");
        String model = scanner.nextLine();
        Vehicle vehicle = vehicleDb.getVehicle(typeOfVehicle, make, model);

        System.out.println("Final Price: ");
        double finalPrice = scanner.nextDouble();
        scanner.nextLine();

        return new Transaction(seller, buyer, vehicle, finalPrice);
    }

    private void transcationInit(Scanner scanner) throws SQLException {
        int lastTransactionId = databseService.getLastTransaction().getTransactionId();

        Transaction transaction = setTransactionInfo(scanner);
        try{
            databseService.addTransaction(transaction);
            System.out.println("Created " + transaction);
            AuditManager.writeToFile(AUDIT_FILE, "add transaction " + lastTransactionId + 1);
        } catch (SQLException e) {
            System.out.println("Cannot create " + transaction + " exception " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void create(Scanner scanner) {
        try{
            transcationInit(scanner);
        } catch (SQLException e) {
            System.out.println("Transaction cannot be created " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void read(Scanner scanner) {
        System.out.println("Transaction ID: ");
        int transactionId = scanner.nextInt();

        try{
            databseService.getTransactionById(transactionId);
            AuditManager.writeToFile(AUDIT_FILE, "transaction read " + transactionId);
        } catch (SQLException e) {
            System.out.println("Transaction cannot be found " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void delete(Scanner scanner) {
        System.out.println("Transaction ID: ");
        int transactionId = scanner.nextInt();

        try{
            databseService.removeTransaction(transactionId);
            AuditManager.writeToFile(AUDIT_FILE, "transaction delete " + transactionId);
        } catch (SQLException e) {
            System.out.println("Transaction cannot be deleted " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void update(Scanner scanner) {
        System.out.println("Transaction ID: ");
        int transactionId = scanner.nextInt();

        Transaction transaction = databseService.getTransaction(transactionId);
        if(transaction == null) {return;}

        Transaction transactionUpdate = setTransactionInfo(scanner);
        transaction.setSeller(transactionUpdate.getSeller());
        transaction.setBuyer(transactionUpdate.getBuyer());
        transaction.setSoldVehicle(transactionUpdate.getSoldVehicle());
        transaction.setFinalPrice(transactionUpdate.getFinalPrice());

        try {
            databseService.updateTransaction(transaction);
        } catch(SQLException e) {
                System.out.println("Transaction cannot be updated " + e.getSQLState() + " " + e.getMessage());
            }
    }

}
