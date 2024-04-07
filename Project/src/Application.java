import services.VehicleService;

import javax.crypto.spec.PSource;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        VehicleService vehicleService = new VehicleService();

        // main loop
        while(true) {
            menu();

            String command = scanner.nextLine();
            System.out.println("Command received: " + command);

            switch(command) {
                case "Create":
                    vehicleService.create(scanner);
                    break;
                case "Read":
                    vehicleService.read(scanner);
                    break;
                case "Update":
                    vehicleService.update(scanner);
                    break;
                case "Delete":
                    vehicleService.delete(scanner);
                    break;
                case "Quit":
                    System.out.println("Exiting..");
                    return;
                default:
                    System.out.println("Wrong command");
            }
        }
    }

    private static void menu() {
        System.out.println("Available commands: ");
        System.out.println("Create");
        System.out.println("Read");
        System.out.println("Update");
        System.out.println("Delete");
        System.out.println("Quit");
        System.out.println("Enter command: ");
    }
}
