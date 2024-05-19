package models.vehicle;

import models.vehicle.Vehicle;

public class Motorcycle extends Vehicle {
    private String category;
    private boolean hasQuickshifter;
    private boolean hasABS;

    public Motorcycle() {}

    public Motorcycle(Vehicle vehicle) {
        super(vehicle.getMake(), vehicle.getModel(), vehicle.getProductionYear(), vehicle.getEngineCapacity(),
                vehicle.getEngineConfiguration(), vehicle.getPower(), vehicle.getTorque(), vehicle.getColor(),
                vehicle.getAccidentFree());
    }

    // Setters
    public void setCategory(String category) {
        this.category = category;
    }

    public void setHasQuickshifter(boolean hasQuickshifter) {
        this.hasQuickshifter = hasQuickshifter;
    }

    public void setHasABS(boolean hasABS) {
        this.hasABS = hasABS;
    }

    // Getters
    public String getCategory() {
        return category;
    }

    public boolean getHasQuickshifter() {
        return hasQuickshifter;
    }

    public boolean getHasABS() {
        return hasABS;
    }

    @Override
    public String toString() {
        return "Motorcycle{" +
                "Make:" + getMake() + "\n" +
                "Model: " + getModel() + "\n" +
                "Production year: " + getProductionYear() + "\n" +
                "Engine capacity: " + getEngineCapacity() + "\n" +
                "Engine configuration: " + getEngineConfiguration() + "\n" +
                "Power: " + getPower() + "\n" +
                "Torque: " + getTorque() + "\n" +
                "Color: " + getColor() + "\n" +
                "Accident free: " + getAccidentFree() + "\n" +
                "Category: " + getCategory() + "\n" +
                "Quickshifter: " + getHasQuickshifter() + "\n" +
                "ABS: " + getHasABS() + "\n" +
                "}";
    }
}
