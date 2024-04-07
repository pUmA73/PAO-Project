package models;

public class Car extends Vehicle{
    private String bodyType;
    private String gearboxType;
    private String driveType;

    public Car(Vehicle vehicle) {
        super(vehicle.getMake(), vehicle.getModel(), vehicle.getProductionYear(), vehicle.getEngineCapacity(),
                vehicle.getEngineConfiguration(), vehicle.getPower(), vehicle.getTorque(), vehicle.getColor(),
                vehicle.getAccidentFree());
    }

    // Setters
    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public void setGearboxType(String gearboxType) {
        this.gearboxType = gearboxType;
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    // Getters
    public String getBodyType() {
        return bodyType;
    }

    public String getGearboxType() {
        return gearboxType;
    }

    public String getDriveType() {
        return driveType;
    }

    @Override
    public String toString() {
        return "Car{" +
                "Make:" + getMake() + "\n" +
                "Model: " + getModel() + "\n" +
                "Production year: " + getProductionYear() + "\n" +
                "Engine capacity: " + getEngineCapacity() + "\n" +
                "Engine configuration: " + getEngineConfiguration() + "\n" +
                "Power: " + getPower() + "\n" +
                "Torque: " + getTorque() + "\n" +
                "Color: " + getColor() + "\n" +
                "Accident free: " + getAccidentFree() + "\n" +
                "Body type: " + getBodyType() + "\n" +
                "Gearbox type: " + getGearboxType() + "\n" +
                "Drive: " + getDriveType() + "\n" +
                "}";
    }
}
