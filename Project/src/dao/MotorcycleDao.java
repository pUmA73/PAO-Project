package dao;

import models.Motorcycle;

import java.util.List;
import java.util.ArrayList;

public class MotorcycleDao {

    private static List<Motorcycle> motorcycles= new ArrayList<>();

    public Motorcycle read(String make, String model) {
        if(!motorcycles.isEmpty()) {
            for(Motorcycle m : motorcycles) {
                if(m.getMake().equals(make) && m.getModel().equals(model)) {
                    return m;
                }
            }
        }
        return null;
    }

    public void delete(Motorcycle motorcycle) {
        motorcycles.remove(motorcycle);
    }

    public void create(Motorcycle motorcycle) {
        motorcycles.add(motorcycle);
    }

}
