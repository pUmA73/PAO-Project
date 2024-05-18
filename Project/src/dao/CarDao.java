package dao;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import models.Car;
import models.User;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CarDao implements DaoInterface<Car>{
    private static CarDao carDao;

    private Connection connection = DatabaseConnection.getConnection();

    public CarDao() throws SQLException {}

    public static CarDao getInstance() throws SQLException {
        if(carDao == null) {
            carDao = new CarDao();
        }
        return carDao;
    }

    @Override
    public void add(Car car) throws SQLException {
        String sql1 = "INSERT INTO auctionsapp_schema.car (vehicleId, bodyType, gearboxType, driveType) VALUES (?, ?, ?, ?);";
        String sql2 = "INSERT INTO auctionsapp_schema.vehicle (make, model, productionYear, engineCapacity, engineConfiguration, " +
                "power, torque, color, accidentFree) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        String sqlVehicleId = "SELECT vehicleId FROM auctionsapp_schema.vehicle ORDER BY vehicleId DESC LIMIT 1";
        int vehicleId = 0;

        try(PreparedStatement statement = connection.prepareStatement(sql2);) {
            statement.setString(1, car.getMake());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getProductionYear());
            statement.setDouble(4, car.getEngineCapacity());
            statement.setString(5, car.getEngineConfiguration());
            statement.setInt(6, car.getPower());
            statement.setInt(7, car.getTorque());
            statement.setString(8, car.getColor());
            statement.setBoolean(9, car.getAccidentFree());
            statement.executeUpdate();
        }

        // incercam sa obtinem id-ul ultimului vehicle introdus in baza de date
        // id-ul vehicle-ului pe care vrem sa-l introducem si in car
        try(PreparedStatement statement = connection.prepareStatement(sqlVehicleId);) {
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                vehicleId = resultSet.getInt("vehicleId");
            }
        }

        try(PreparedStatement statement = connection.prepareStatement(sql1);) {
            statement.setInt(1, vehicleId);
            statement.setString(2, car.getBodyType());
            statement.setString(3, car.getGearboxType());
            statement.setString(4, car.getDriveType());
            statement.executeUpdate();
        }
    }

    @Override
    public Car read(int id) throws SQLException {
        String sql = "SELECT * FROM auctionsapp_schema.car c JOIN auctionsapp_schema.vehicle " +
                "v on c.vehicleId = v.vehicleId WHERE v.vehicleId = ?";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            rs = statement.executeQuery();

            while(rs.next()) {
                Car car = new Car();
                car.setVehicleId(id);
                car.setMake(rs.getString("make"));
                car.setModel(rs.getString("model"));
                car.setProductionYear(rs.getInt("productionYear"));
                car.setEngineCapacity(rs.getDouble("engineCapacity"));
                car.setEngineConfiguration(rs.getString("engineConfiguration"));
                car.setPower(rs.getInt("power"));
                car.setTorque(rs.getInt("torque"));
                car.setColor(rs.getString("color"));
                car.setAccidentFree(rs.getBoolean("accidentFree"));
                car.setBodyType(rs.getString("bodyType"));
                car.setGearboxType(rs.getString("gearboxType"));
                car.setDriveType(rs.getString("driveType"));
                return car;
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return null;
    }

    public Car read(String make, String model) throws SQLException {
        String sql = "SELECT * FROM auctionsapp_schema.car c JOIN auctionsapp_schema.vehicle v " +
                "ON c.vehicleId = v.vehicleId WHERE UPPER(v.make) = ? AND UPPER(v.model) = ?";
        ResultSet rs = null;

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, make.toUpperCase());
            statement.setString(2, model.toUpperCase());

            rs = statement.executeQuery();

            while(rs.next()) {
                Car car = new Car();
                car.setMake(rs.getString("make"));
                car.setModel(rs.getString("model"));
                car.setProductionYear(rs.getInt("productionYear"));
                car.setEngineCapacity(rs.getDouble("engineCapacity"));
                car.setEngineConfiguration(rs.getString("engineConfiguration"));
                car.setPower(rs.getInt("power"));
                car.setTorque(rs.getInt("torque"));
                car.setColor(rs.getString("color"));
                car.setAccidentFree(rs.getBoolean("accidentFree"));
                car.setBodyType(rs.getString("bodyType"));
                car.setGearboxType(rs.getString("gearboxType"));
                car.setDriveType(rs.getString("driveType"));
                return car;
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return null;
    }

    @Override
    public void delete(Car car) throws SQLException {
        String sql = "DELETE FROM auctionsapp_schema.vehicle v WHERE UPPER(v.make) = ? AND UPPER(v.model) = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, car.getMake().toUpperCase());
            statement.setString(2, car.getModel().toUpperCase());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Car car) throws SQLException {
        String sqlVehicle = "UPDATE auctionsapp_schema.vehicle SET productionYear = ?, engineCapacity = ?, " +
                "engineConfiguration = ?, power = ?, torque = ?, color = ?, accidentFree = ? " +
                "WHERE vehicleId = ?";

        String sqlCar = "UPDATE auctionsapp_schema.car SET bodyType = ?, " +
                "gearboxType = ?, driveType = ? WHERE vehicleId = ?";

        String sqlVehicleId = "SELECT vehicleId FROM auctionsapp_schema.vehicle WHERE UPPER(make) = ? AND UPPER(model) = ?";

        int vehicleId = 0;

        try(PreparedStatement statement = connection.prepareStatement(sqlVehicleId);) {
            statement.setString(1, car.getMake().toUpperCase());
            statement.setString(2, car.getModel().toUpperCase());

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                vehicleId = resultSet.getInt("vehicleId");
            }
        }


        try(PreparedStatement statement = connection.prepareStatement(sqlVehicle);) {
            statement.setInt(1, car.getProductionYear());
            statement.setDouble(2, car.getEngineCapacity());
            statement.setString(3, car.getEngineConfiguration());
            statement.setInt(4, car.getPower());
            statement.setInt(5, car.getTorque());
            statement.setString(6, car.getColor());
            statement.setBoolean(7, car.getAccidentFree());
            statement.setInt(8, vehicleId);
            statement.executeUpdate();
        }

        try(PreparedStatement statement = connection.prepareStatement(sqlCar);) {
            statement.setString(1, car.getBodyType());
            statement.setString(2, car.getGearboxType());
            statement.setString(3, car.getDriveType());
            statement.setInt(4, vehicleId);
            statement.executeUpdate();
        }

    }
}
