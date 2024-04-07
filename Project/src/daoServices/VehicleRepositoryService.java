package daoServices;


import dao.CarDao;
import dao.MotorcycleDao;
import models.Motorcycle;
import models.Car;
import models.Vehicle;

public class VehicleRepositoryService {

    private CarDao carDao;
    private MotorcycleDao motorcycleDao;

    public VehicleRepositoryService() {
        this.carDao = new CarDao();
        this.motorcycleDao = new MotorcycleDao();
    }

    public Car getCarByMakeModel(String make, String model) {
        Car car = carDao.read(make, model);
        if(car != null) {
            System.out.println(car);
        } else {
            System.out.println("No car of the make and model has been found!");
        }

        return car;
    }

    public Motorcycle getMotorcycleByMakeModel(String make, String model) {
        Motorcycle moto = motorcycleDao.read(make, model);
        if(moto != null) {
            System.out.println(moto);
        } else {
            System.out.println("No motorcycle of the make and model has been found!");
        }

        return moto;
    }

    public Vehicle getVehicle(String typeOfVehicle, String make, String model) {
        Vehicle vehicle;
        if(typeOfVehicle.equals("car")) {
            vehicle = getCarByMakeModel(make, model);
        } else {
            vehicle = getMotorcycleByMakeModel(make, model);
        }

        if(vehicle == null) {
            System.out.println("No vehicle of the make and model has been found!");
            return null;
        }

        return vehicle;
    }

    public void removeVehicle(String typeOfVehicle, String make, String model) {
        Vehicle vehicle = getVehicle(typeOfVehicle, make, model);
        if(vehicle == null) return;

        switch(vehicle) {
            case Car car -> carDao.delete(car);
            case Motorcycle motorcycle -> motorcycleDao.delete(motorcycle);
            default -> throw new IllegalStateException("Unexpected value: " + vehicle);
        }

        System.out.println("Removed " + vehicle);
    }

    public void addVehicle(Vehicle vehicle) {
        if(vehicle != null) {
            switch(vehicle) {
                case Car car -> carDao.create(car);
                case Motorcycle motorcycle -> motorcycleDao.create(motorcycle);
                default -> throw new IllegalStateException("Unexpected value " + vehicle);
            }
        }
    }
}
