package services;

import daoServices.AuctionRepositoryService;
import daoServices.UserRepositoryService;
import daoServices.VehicleRepositoryService;
import models.Auction;
import models.Transaction;
import models.User;
import models.Vehicle;
import utils.AuditManager;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

import static utils.Constants.AUDIT_FILE;

public class AuctionService {
    private final AuctionRepositoryService databaseService;

    private final VehicleRepositoryService vehicleDb;

    private final UserRepositoryService userDb;

    public AuctionService() throws SQLException {
        this.databaseService = new AuctionRepositoryService();
        this.userDb = new UserRepositoryService();
        this.vehicleDb = new VehicleRepositoryService();
    }

    private Auction setAuctionInfo(Scanner scanner) {
        System.out.println("Vehicle type[car/motorcycle]:");
        String typeOfVehicle = scanner.nextLine();

        System.out.println("Vehicle Make: ");
        String make = scanner.nextLine();
        System.out.println("Vehicle Model: ");
        String model = scanner.nextLine();
        Vehicle vehicle = vehicleDb.getVehicle(typeOfVehicle, make, model);


        System.out.println("Seller First Name: ");
        String sellerFirstName = scanner.nextLine();
        System.out.println("Seller Last Name: ");
        String sellerLastName = scanner.nextLine();
        User seller = userDb.getUser(sellerFirstName, sellerLastName);

        System.out.println("Description: ");
        String description = scanner.nextLine();

        System.out.println("Starting Price: ");
        double startingPrice = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Highest Bid: ");
        double highestBid = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Start Time: ");
        String startTimeString = scanner.nextLine();
        Date startTime = null;
        try {
            startTime = Date.valueOf(startTimeString);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: The date string is not in the correct format.");
        }

        System.out.println("Finish Time: ");
        String finishTimeString = scanner.nextLine();
        Date finishTime = null;
        try {
            finishTime = Date.valueOf(finishTimeString);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: The date string is not in the correct format.");
        }

        return new Auction(vehicle, seller, description, startingPrice, highestBid, startTime, finishTime);
    }

    private void auctionInit(Scanner scanner) throws SQLException {
        Auction auction = setAuctionInfo(scanner);
        try {
            databaseService.addAuction(auction);
            System.out.println("Created " + auction);
            int lastAuctionId = databaseService.getLastauction().getAuctionId();
            AuditManager.writeToFile(AUDIT_FILE, "add auction " + lastAuctionId);
        } catch (SQLException e) {
            System.out.println("Cannot create auction " + auction + " exception " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void create(Scanner scanner) {
        try{
            auctionInit(scanner);
        } catch (SQLException e) {
            System.out.println("Auction cannot be created " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void read(Scanner scanner) {
        System.out.println("Auction ID: ");
        int auctionId = scanner.nextInt();

        try{
            databaseService.getAuctionById(auctionId);
            AuditManager.writeToFile(AUDIT_FILE, "auction read " + auctionId);
        } catch (SQLException e) {
            System.out.println("Auction cannot be found " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void delete(Scanner scanner) {
        System.out.println("Auction ID: ");
        int auctionId = scanner.nextInt();

        try{
            databaseService.removeAuction(auctionId);
            AuditManager.writeToFile(AUDIT_FILE, "auction deleted " + auctionId);
        } catch (SQLException e) {
            System.out.println("Auction cannot be deleted " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void update(Scanner scanner) {
        System.out.println("Auction ID: ");
        int auctionId = scanner.nextInt();

        Auction auction = databaseService.getAuction(auctionId);
        if(auction == null) {return;}

        Auction auctionUpdate = setAuctionInfo(scanner);
        auction.setAuctionedVehicle(auctionUpdate.getAuctionedVehicle());
        auction.setSeller(auctionUpdate.getSeller());
        auction.setDescription(auctionUpdate.getDescription());
        auction.setStartingPrice(auctionUpdate.getStartingPrice());
        auction.setHighestBid(auctionUpdate.getHighestBid());
        auction.setStartTime(auctionUpdate.getStartTime());
        auction.setFinishTime(auctionUpdate.getFinishTime());


        try {
            databaseService.updateAuction(auction);
        } catch(SQLException e) {
            System.out.println("Transaction cannot be updated " + e.getSQLState() + " " + e.getMessage());
        }
    }
}
