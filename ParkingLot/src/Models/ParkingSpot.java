package Models;

public class ParkingSpot {
    private Vehicle vehicle;
    private final VehicleType size;
    public final int spotNumber;

    public ParkingSpot(VehicleType size, int spotNumber) {
        this.size = size;
        this.spotNumber = spotNumber;
    }

    public boolean park(Vehicle vehicle) {
        if (isAvailable() && this.size == vehicle.getSize()) {
            this.vehicle = vehicle;
            return true;
        }
        return false;
    }

    public Vehicle leave() {
        Vehicle temp = vehicle;
        vehicle = null;
        return temp;
    }

    public boolean isAvailable() {
        return vehicle == null;
    }
}
