package services;

import daoServices.VehicleRepositoryService;
import models.Car;
import models.Motorcycle;
import models.Vehicle;

import java.util.Scanner;

public class VehicleService {

    private VehicleRepositoryService databaseService;

    public VehicleService() {
        this.databaseService = new VehicleRepositoryService();
    }

    public boolean typeOfVehicleValidation(String typeOfVehicle) {
        if(!typeOfVehicle.equals("car") && !typeOfVehicle.equals("motorcycle")) {
            System.out.println("Wrong vehicle type!");
            return false;
        }
        return true;
    }

    private Vehicle setGeneralInfo(String make, String model, Scanner scanner) {
        System.out.println("Year of production:");
        int productionYear = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Engine capacity:");
        double engineCapacity = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Engine configuration:");
        String engineConfiguration = scanner.nextLine();

        System.out.println("Power:");
        int power = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Torque:");
        int torque = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Color:");
        String color = scanner.nextLine();

        System.out.println("Is the vehicle accident free:");
        boolean accidentFree = Boolean.parseBoolean(scanner.nextLine());

        return new Vehicle(make, model, productionYear, engineCapacity, engineConfiguration, power, torque, color, accidentFree);
    }

    private void carInit(Scanner scanner, Car car) {
        System.out.println("Body type:");
        String bodyType = scanner.nextLine();

        System.out.println("Gearbox type:");
        String gearboxType = scanner.nextLine();

        System.out.println("Drive type:");
        String driveType = scanner.nextLine();

        car.setBodyType(bodyType);
        car.setGearboxType(gearboxType);
        car.setDriveType(driveType);
    }

    private void motorcycleInit(Scanner scanner, Motorcycle motorcycle) {
        System.out.println("Category:");
        String category = scanner.nextLine();

        System.out.println("Does the bike have a quick shifter:");
        boolean hasQuickshifter = scanner.nextBoolean();
        scanner.nextLine();

        System.out.println("Does the bike have ABS:");
        boolean hasABS = scanner.nextBoolean();
        scanner.nextLine();

        motorcycle.setCategory(category);
        motorcycle.setHasQuickshifter(hasQuickshifter);
        motorcycle.setHasABS(hasABS);
    }

    private void vehicleInit(Scanner scanner, String typeOfVehicle) {
        System.out.println("Make: ");
        String make = scanner.nextLine();
        System.out.println("Model: ");
        String model = scanner.nextLine();

        if(typeOfVehicle.equals("car") && databaseService.getCarByMakeModel(make, model) != null) { return; }
        if(typeOfVehicle.equals("motorcycle") && databaseService.getMotorcycleByMakeModel(make, model) != null) { return; }

        Vehicle vehicle = setGeneralInfo(make, model, scanner);
        if(typeOfVehicle.equals("car")) {
            Car car = new Car(vehicle);
            carInit(scanner, car);
            vehicle = car;
        } else {
            Motorcycle motorcycle = new Motorcycle(vehicle);
            motorcycleInit(scanner, motorcycle);
            vehicle = motorcycle;
        }

        databaseService.addVehicle(vehicle);
        System.out.println("Created " + vehicle  );
    }

    public void create(Scanner scanner) {
        System.out.println("Enter the type of vehicle [car/motorcycle]:");
        String typeOfVehicle = scanner.nextLine().toLowerCase();
        if(!typeOfVehicleValidation(typeOfVehicle)) { return; }
        vehicleInit(scanner, typeOfVehicle);
    }


    public void read(Scanner scanner) {
        System.out.println("Make:");
        String make = scanner.nextLine();

        System.out.println("Model:");
        String model = scanner.nextLine();

        Car car = databaseService.getCarByMakeModel(make, model);
        Motorcycle motorcycle = databaseService.getMotorcycleByMakeModel(make, model);

        if(car != null) {
            System.out.println(car);
        }
        if(motorcycle != null) {
            System.out.println(motorcycle);
        }
    }

    public void delete(Scanner scanner) {
        System.out.println("Make:");
        String make = scanner.nextLine();

        System.out.println("Model:");
        String model = scanner.nextLine();

        System.out.println("Vehicle type[car/motorcycle]:");
        String typeOfVehicle = scanner.nextLine();

        if(!typeOfVehicleValidation(typeOfVehicle)) { return; }
        databaseService.removeVehicle(typeOfVehicle, make, model);
    }

    public void update(Scanner scanner) {
        System.out.println("Vehicle type[car/motorcycle]:");
        String typeOfVehicle = scanner.nextLine();
        if(!typeOfVehicleValidation(typeOfVehicle)) { return; }

        System.out.println("Make:");
        String make = scanner.nextLine();

        System.out.println("Model:");
        String model = scanner.nextLine();

        Vehicle vehicle = databaseService.getVehicle(typeOfVehicle, make, model);
        if(vehicle == null) { return; }

        Vehicle vehicleGeneralInfo = setGeneralInfo(make, model, scanner);
        //vehicle.setMake(make);
        //vehicle.setModel(model);
        vehicle.setProductionYear(vehicleGeneralInfo.getProductionYear());
        vehicle.setEngineCapacity(vehicleGeneralInfo.getEngineCapacity());
        vehicle.setEngineConfiguration(vehicleGeneralInfo.getEngineConfiguration());
        vehicle.setPower(vehicleGeneralInfo.getPower());
        vehicle.setTorque(vehicleGeneralInfo.getTorque());
        vehicle.setColor(vehicleGeneralInfo.getColor());
        vehicle.setAccidentFree(vehicleGeneralInfo.getAccidentFree());

        if(typeOfVehicle.equals("car")) {
            carInit(scanner, (Car) vehicle);
        } else {
            motorcycleInit(scanner, (Motorcycle) vehicle);
        }
    }
}
