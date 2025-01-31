import Models.*;

import java.util.ArrayList;
import java.util.List;

class Driver {
    public static void main(String[] args) {
        // Create a parking lot with a specific number of spots
        ParkingLot parkingLot = new ParkingLot(10); // Assume we have 10 parking spots

        // Create some vehicles
        Vehicle car1 = new Car("ABC123");
        Vehicle car2 = new Car("XYZ789");
        Vehicle bike1 = new MotorCycle("BIKE1");
        Vehicle bus1 = new Truck("BUS1");

        // Park the vehicles
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(parkingLot.parkVehicle(car1));
        tickets.add(parkingLot.parkVehicle(car2));
        tickets.add(parkingLot.parkVehicle(bike1));
        tickets.add(parkingLot.parkVehicle(bus1));

        // Display parked vehicle tickets
        for (Ticket ticket : tickets) {
            if (ticket != null) {
                System.out.println("Vehicle " + ticket.getVehicle().getLicensePlate() +
                        " parked at spot " + ticket.getSpot().spotNumber);
            } else {
                System.out.println("No available spot for vehicle.");
            }
        }

        // Simulate leaving the parking lot
        for (Vehicle vehicle : new Vehicle[]{car1, bike1, bus1}) {
            Vehicle leavingVehicle = parkingLot.leaveParking(vehicle.getLicensePlate());
            if (leavingVehicle != null) {
                System.out.println("Vehicle " + leavingVehicle.getLicensePlate() + " has left the parking lot.");
            } else {
                System.out.println("Vehicle not found in the parking lot.");
            }
        }

        // Attempt to park again after some have left
        Vehicle car3 = new Car("NEWCAR");
        Ticket newCarTicket = parkingLot.parkVehicle(car3);
        if (newCarTicket != null) {
            System.out.println("Vehicle " + newCarTicket.getVehicle().getLicensePlate() +
                    " parked at spot " + newCarTicket.getSpot().spotNumber);
        }
    }
}
