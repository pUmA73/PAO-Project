package dao;

import models.Car;

import java.util.List;
import java.util.ArrayList;

public class CarDao {

    private static List<Car> cars = new ArrayList<>();

    public Car read(String make, String model) {
        if(!cars.isEmpty()) {
            for(Car c : cars) {
                if(c.getMake().equals(make) && c.getModel().equals(model)) {
                    return c;
                }
            }
        }
        return null;
    }

    public void delete(Car car) {
        cars.remove(car);
    }

    public void create(Car car) {
        cars.add(car);
    }
}
