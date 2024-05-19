import models.Auction;
import services.*;

import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        UserService userService = new UserService();
        VehicleService vehicleService = new VehicleService();
        AuctionService auctionService = new AuctionService();
        TransactionService transactionService = new TransactionService();

        // main loop
        while(true) {
            classesMenu();

            String command = scanner.nextLine().toLowerCase();
            System.out.println("Command received: " + command);

            switch(command) {
                case "user":
                    submenu(userService, scanner);
                    break;
                case "vehicle":
                    submenu(vehicleService, scanner);
                    break;
                case "auction":
                    submenu(auctionService, scanner);
                    break;
                case "transaction":
                    submenu(transactionService, scanner);
                    break;
                case "quit":
                    System.out.println("Exiting..");
                    return;
                default:
                    System.out.println("Wrong command");
            }
        }
    }

    private static void submenu(CrudService service, Scanner scanner) {
        while(true) {
            System.out.println("Available commands: ");
            System.out.println("Create");
            System.out.println("Read");
            System.out.println("Update");
            System.out.println("Delete");
            System.out.println("Back");
            System.out.println("Enter command: ");

            String choice = scanner.nextLine().toLowerCase();

            if(choice.equals("back")) {
                break;
            }

            switch (choice) {
                case "create":
                    service.create(scanner);
                    break;
                case "read":
                    service.read(scanner);
                    break;
                case "update":
                    service.update(scanner);
                    break;
                case "delete":
                    service.delete(scanner);
                    break;
                default:
                    System.out.println("Wrong command");
            }
        }
    }

    private static void classesMenu() {
        System.out.println("Choose a class: ");
        System.out.println("User");
        System.out.println("Vehicle");
        System.out.println("Auction");
        System.out.println("Transaction");
        System.out.println("Quit");
    }
}
