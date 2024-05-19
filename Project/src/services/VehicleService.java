package services;

import daoServices.VehicleRepositoryService;
import models.vehicle.Car;
import models.vehicle.Motorcycle;
import models.vehicle.Vehicle;
import utils.AuditManager;

import java.sql.SQLException;
import java.util.Scanner;

import static utils.Constants.*;

public class VehicleService implements CrudService {

    private final VehicleRepositoryService databaseService;

    public VehicleService() throws SQLException {
        this.databaseService = new VehicleRepositoryService();
    }

    public boolean typeOfVehicleValidation(String typeOfVehicle) {
        if(!typeOfVehicle.equals(CAR) && !typeOfVehicle.equals(MOTORCYCLE)) {
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

    private void vehicleInit(Scanner scanner, String typeOfVehicle) throws SQLException {
        System.out.println("Make: ");
        String make = scanner.nextLine();
        System.out.println("Model: ");
        String model = scanner.nextLine();

        if(typeOfVehicle.equals(CAR) && databaseService.getCarByMakeModel(make, model) != null) { return; }
        if(typeOfVehicle.equals(MOTORCYCLE) && databaseService.getMotorcycleByMakeModel(make, model) != null) { return; }

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

        try{
            databaseService.addVehicle(vehicle);
            System.out.println("Created " + vehicle);
            AuditManager.writeToFile(AUDIT_FILE, "add vehicle " + vehicle.getMake() + " " + vehicle.getModel());
        } catch(SQLException e) {
            System.out.println("Cannot create " + vehicle + " exception " + e.getSQLState() + " " + e.getMessage());
        }

    }

    @Override
    public void create(Scanner scanner) {
        System.out.println("Enter the type of vehicle [car/motorcycle]:");
        String typeOfVehicle = scanner.nextLine().toLowerCase();
        if(!typeOfVehicleValidation(typeOfVehicle)) { return; }
        try{
            vehicleInit(scanner, typeOfVehicle);
        } catch(SQLException e) {
            System.out.println("Vehicle cannot be created " + e.getSQLState() + " " + e.getMessage());
        }
    }

//    public void readOrderedCars() {
//        List<Car> orderedCars = databaseService.sortCarsByHp();
//        for(Car car : orderedCars) {
//            System.out.println(car);
//        }
//    }

    @Override
    public void read(Scanner scanner) {
        System.out.println("Make:");
        String make = scanner.nextLine();

        System.out.println("Model:");
        String model = scanner.nextLine();

        try{
            databaseService.getCarByMakeModel(make, model);
            AuditManager.writeToFile(AUDIT_FILE, "car read " + make + " " + model);
        } catch(SQLException e) {
            System.out.println("Car cannot be found " + e.getSQLState() + " " + e.getMessage());
        }

        try{
            databaseService.getMotorcycleByMakeModel(make, model);
            AuditManager.writeToFile(AUDIT_FILE, "motorcycle read " + make + " " + model);
        } catch(SQLException e) {
            System.out.println("Motorcycle cannot be found " + e.getSQLState() + " " + e.getMessage());
        }
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.println("Make:");
        String make = scanner.nextLine();

        System.out.println("Model:");
        String model = scanner.nextLine();

        System.out.println("Vehicle type[car/motorcycle]:");
        String typeOfVehicle = scanner.nextLine();

        if(!typeOfVehicleValidation(typeOfVehicle)) { return; }

        try{
            databaseService.removeVehicle(typeOfVehicle, make, model);
            AuditManager.writeToFile(AUDIT_FILE, "vehicle deletion " + make + " " + model);
        } catch (SQLException e) {
            System.out.println("Vehicle cannot be deleted " + e.getSQLState() + " " + e.getMessage());
        }
    }

    @Override
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

        try{
            databaseService.updateVehicle(vehicle);
        } catch (SQLException e) {
            System.out.println("Vehicle cannot be updated " + e.getSQLState() + " " + e.getMessage());
        }
    }
}
