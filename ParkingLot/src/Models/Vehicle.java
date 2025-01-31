package Models;

public class Vehicle {
    protected String licensePlate;
    protected VehicleType size;

    public Vehicle(String licensePlate, VehicleType size) {
        this.licensePlate = licensePlate;
        this.size = size;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getSize() {
        return size;
    }
}
