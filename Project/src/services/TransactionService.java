package services;

import daoServices.TransactionRepositoryService;
import daoServices.UserRepositoryService;
import daoServices.VehicleRepositoryService;
import models.Transaction;
import models.User;
import models.vehicle.Vehicle;
import utils.AuditManager;

import java.sql.SQLException;
import java.util.Scanner;

import static utils.Constants.AUDIT_FILE;

public class TransactionService implements CrudService {
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

        Transaction transaction = setTransactionInfo(scanner);
        try{
            databseService.addTransaction(transaction);
            System.out.println("Created " + transaction);
            int lastTransactionId = databseService.getLastTransaction().getTransactionId();
            AuditManager.writeToFile(AUDIT_FILE, "add transaction " + lastTransactionId);
        } catch (SQLException e) {
            System.out.println("Cannot create " + transaction + " exception " + e.getSQLState() + " " + e.getMessage());
        }
    }

    @Override
    public void create(Scanner scanner) {
        try{
            transcationInit(scanner);
        } catch (SQLException e) {
            System.out.println("Transaction cannot be created " + e.getSQLState() + " " + e.getMessage());
        }
    }

    @Override
    public void read(Scanner scanner) {
        System.out.println("Transaction ID: ");
        int transactionId = scanner.nextInt();
        scanner.nextLine();

        try{
            databseService.getTransactionById(transactionId);
            AuditManager.writeToFile(AUDIT_FILE, "transaction read " + transactionId);
        } catch (SQLException e) {
            System.out.println("Transaction cannot be found " + e.getSQLState() + " " + e.getMessage());
        }
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.println("Transaction ID: ");
        int transactionId = scanner.nextInt();
        scanner.nextLine();

        try{
            databseService.removeTransaction(transactionId);
            AuditManager.writeToFile(AUDIT_FILE, "transaction delete " + transactionId);
        } catch (SQLException e) {
            System.out.println("Transaction cannot be deleted " + e.getSQLState() + " " + e.getMessage());
        }
    }

    @Override
    public void update(Scanner scanner) {
        System.out.println("Transaction ID: ");
        int transactionId = scanner.nextInt();
        scanner.nextLine();

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
