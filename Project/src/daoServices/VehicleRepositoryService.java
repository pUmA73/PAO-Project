package daoServices;

import dao.CarDao;
import dao.MotorcycleDao;
import models.Car;
import models.Motorcycle;
import models.Vehicle;

import java.sql.SQLException;

import static utils.Constants.CAR;

public class VehicleRepositoryService {
    private final CarDao carDao = CarDao.getInstance();
    private final MotorcycleDao motorcycleDao = MotorcycleDao.getInstance();

    public VehicleRepositoryService() throws SQLException {}

    public Vehicle getVehicle(String vehicleType, String make, String model) {
        Vehicle vehicle = null;
        try {
            if(vehicleType.equals(CAR)) {
                vehicle = getCarByMakeModel(make, model);
            } else {
                vehicle = getMotorcycleByMakeModel(make, model);
            }
            if(vehicle == null) {
                return null;
            }
        } catch(SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return vehicle;
    }

    public Car getCarByMakeModel(String make, String model) throws SQLException {
        Car car = carDao.read(make, model);
        if(car != null) {
            System.out.println(car);
        } else {
            System.out.println("No car of the make and model found!");
        }

        return car;
    }

    public Motorcycle getMotorcycleByMakeModel(String make, String model) throws SQLException {
        Motorcycle motorcycle = motorcycleDao.read(make, model);
        if(motorcycle != null) {
            System.out.println(motorcycle);
        } else {
            System.out.println("No motorcycle of the make and model found!");
        }

        return motorcycle;
    }

    public void removeVehicle(String vehicleType, String make, String model) throws SQLException {
        Vehicle vehicle = getVehicle(vehicleType, make, model);
        if(vehicle == null) return;

        switch (vehicle) {
            case Car car -> carDao.delete(car);
            case Motorcycle motorcycle -> motorcycleDao.delete(motorcycle);
            default -> throw new IllegalStateException("Unexpected value: " + vehicle);
        }
        System.out.println("Removed " + vehicle);
    }

    public void addVehicle(Vehicle vehicle) throws SQLException {
        if(vehicle != null) {
            switch (vehicle) {
                case Car car -> carDao.add(car);
                case Motorcycle motorcycle -> motorcycleDao.add(motorcycle);
                default -> throw new IllegalStateException("Unexpected value: " + vehicle);
            }
        }
    }

    public void updateVehicle(Vehicle vehicle) throws SQLException {
        if(vehicle != null) {
            switch (vehicle) {
                case Car car -> carDao.update(car);
                case Motorcycle motorcycle -> motorcycleDao.update(motorcycle);
                default -> throw new IllegalStateException("Unexpected value: " + vehicle);
            }
        }
    }
}
