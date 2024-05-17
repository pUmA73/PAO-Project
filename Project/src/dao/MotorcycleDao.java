package dao;

import models.Car;
import models.Motorcycle;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MotorcycleDao implements DaoInterface<Motorcycle>{
    private static MotorcycleDao motorcycleDao;

    private Connection connection = DatabaseConnection.getConnection();

    public MotorcycleDao() throws SQLException {}

    public static MotorcycleDao getInstance() throws SQLException {
        if(motorcycleDao == null) {
            motorcycleDao = new MotorcycleDao();
        }
        return motorcycleDao;
    }

    @Override
    public void add(Motorcycle motorcycle) throws SQLException {
        String sql1 = "INSERT INTO auctionsapp_schema.motorcycle (vehicleId, category, hasQuickshifter, hasABS) VALUES (?, ?, ?, ?);";
        String sql2 = "INSERT INTO auctionsapp_schema.vehicle (make, model, productionYear, engineCapacity, engineConfiguration, " +
                "power, torque, color, accidentFree) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        String sqlVehicleId = "SELECT vehicleId FROM auctionsapp_schema.vehicle ORDER BY vehicleId DESC LIMIT 1";
        int vehicleId = 0;

        try(PreparedStatement statement = connection.prepareStatement(sql2);) {
            statement.setString(1, motorcycle.getMake());
            statement.setString(2, motorcycle.getModel());
            statement.setInt(3, motorcycle.getProductionYear());
            statement.setDouble(4, motorcycle.getEngineCapacity());
            statement.setString(5, motorcycle.getEngineConfiguration());
            statement.setInt(6, motorcycle.getPower());
            statement.setInt(7, motorcycle.getTorque());
            statement.setString(8, motorcycle.getColor());
            statement.setBoolean(9, motorcycle.getAccidentFree());
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
            statement.setString(2, motorcycle.getCategory());
            statement.setBoolean(3, motorcycle.getHasQuickshifter());
            statement.setBoolean(4, motorcycle.getHasABS());
            statement.executeUpdate();
        }
    }

    @Override
    public Motorcycle read(String make, String model) throws SQLException {
        String sql = "SELECT * FROM auctionsapp_schema.motorcycle m JOIN auctionsapp_schema.vehicle v " +
                "ON m.vehicleId = v.vehicleId WHERE UPPER(v.make) = ? AND UPPER(v.model) = ?";
        ResultSet rs = null;

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, make.toUpperCase());
            statement.setString(2, model.toUpperCase());

            rs = statement.executeQuery();

            while(rs.next()) {
                Motorcycle motorcycle = new Motorcycle();
                motorcycle.setMake(rs.getString("make"));
                motorcycle.setModel(rs.getString("model"));
                motorcycle.setProductionYear(rs.getInt("productionYear"));
                motorcycle.setEngineCapacity(rs.getDouble("engineCapacity"));
                motorcycle.setEngineConfiguration(rs.getString("engineConfiguration"));
                motorcycle.setPower(rs.getInt("power"));
                motorcycle.setTorque(rs.getInt("torque"));
                motorcycle.setColor(rs.getString("color"));
                motorcycle.setAccidentFree(rs.getBoolean("accidentFree"));
                motorcycle.setCategory(rs.getString("category"));
                motorcycle.setHasQuickshifter(rs.getBoolean("hasQuickshifter"));
                motorcycle.setHasABS(rs.getBoolean("hasABS"));
                return motorcycle;
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return null;
    }

    @Override
    public void delete(Motorcycle motorcycle) throws SQLException {
        String sql = "DELETE FROM auctionsapp_schema.vehicle v WHERE UPPER(v.make) = ? AND UPPER(v.model) = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, motorcycle.getMake().toUpperCase());
            statement.setString(2, motorcycle.getModel().toUpperCase());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Motorcycle motorcycle) throws SQLException {
        String sqlVehicle = "UPDATE auctionsapp_schema.vehicle SET productionYear = ?, engineCapacity = ?, " +
                "engineConfiguration = ?, power = ?, torque = ?, color = ?, accidentFree = ? " +
                "WHERE vehicleId = ?";

        String sqlMotorcycle = "UPDATE auctionsapp_schema.motorcycle SET category = ?, " +
                "hasQuickshifter = ?, hasABS = ? WHERE vehicleId = ?";

        String sqlVehicleId = "SELECT vehicleId FROM auctionsapp_schema.vehicle WHERE UPPER(make) = ? AND UPPER(model) = ?";

        int vehicleId = 0;

        try(PreparedStatement statement = connection.prepareStatement(sqlVehicleId);) {
            statement.setString(1, motorcycle.getMake().toUpperCase());
            statement.setString(2, motorcycle.getModel().toUpperCase());

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                vehicleId = resultSet.getInt("vehicleId");
            }
        }


        try(PreparedStatement statement = connection.prepareStatement(sqlVehicle);) {
            statement.setInt(1, motorcycle.getProductionYear());
            statement.setDouble(2, motorcycle.getEngineCapacity());
            statement.setString(3, motorcycle.getEngineConfiguration());
            statement.setInt(4, motorcycle.getPower());
            statement.setInt(5, motorcycle.getTorque());
            statement.setString(6, motorcycle.getColor());
            statement.setBoolean(7, motorcycle.getAccidentFree());
            statement.setInt(8, vehicleId);
            statement.executeUpdate();
        }

        try(PreparedStatement statement = connection.prepareStatement(sqlMotorcycle);) {
            statement.setString(1, motorcycle.getCategory());
            statement.setBoolean(2, motorcycle.getHasQuickshifter());
            statement.setBoolean(3, motorcycle.getHasABS());
            statement.setInt(4, vehicleId);
            statement.executeUpdate();
        }

    }
}
