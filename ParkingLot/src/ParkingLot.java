import Models.ParkingSpot;
import Models.Ticket;
import Models.Vehicle;
import Models.VehicleType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ParkingLot {
    private final List<ParkingSpot> spots;
    private final HashMap<String, Ticket> tickets;

    public ParkingLot(int numSpots) {
        spots = new ArrayList<>(numSpots);
        tickets = new HashMap<>();
        for (int i = 0; i < numSpots; i++) {
            spots.add(new ParkingSpot(VehicleType.Car, i));
        }
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        for (ParkingSpot spot : spots) {
            if (spot.park(vehicle)) {
                Ticket ticket = new Ticket(vehicle, spot);
                tickets.put(vehicle.getLicensePlate(), ticket);
                return ticket;
            }
        }
        return null; // No available spot
    }

    public Vehicle leaveParking(String licensePlate) {
        Ticket ticket = tickets.remove(licensePlate);
        if (ticket != null) {
            ParkingSpot spot = ticket.getSpot();
            return spot.leave();
        }
        return null; // No such vehicle
    }
}

