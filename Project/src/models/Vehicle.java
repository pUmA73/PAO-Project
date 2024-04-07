package models;

public class Vehicle {
    protected String make;
    protected String model;
    protected int productionYear;
    protected double engineCapacity;
    protected String engineConfiguration;
    protected int power;
    protected int torque;
    protected String color;
    protected boolean accidentFree;

    // Constructors
    public Vehicle() {}

    public Vehicle(String make, String model, int productionYear, double engineCapacity, String engineConfiguration, int power,
                   int torque, String color, boolean accidentFree) {
        this.make = make;
        this.model = model;
        this.productionYear = productionYear;
        this.engineCapacity = engineCapacity;
        this.engineConfiguration = engineConfiguration;
        this.power = power;
        this.torque = torque;
        this.color = color;
        this.accidentFree = accidentFree;
    }

    // Setters
    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public void setEngineCapacity(double engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public void setEngineConfiguration(String engineConfiguration) {
        this.engineConfiguration = engineConfiguration;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setTorque(int torque) {
        this.torque = torque;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setAccidentFree(boolean accidentFree) {
        this.accidentFree = accidentFree;
    }

    // Getters
    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public double getEngineCapacity() {
        return engineCapacity;
    }

    public String getEngineConfiguration() {
        return engineConfiguration;
    }

    public int getPower() {
        return power;
    }

    public int getTorque() {
        return torque;
    }

    public String getColor() {
        return color;
    }

    public boolean getAccidentFree() {
        return accidentFree;
    }


}
