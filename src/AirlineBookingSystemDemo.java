import Flight.FlightType;
import Flight.SeatType;
import Payment.PaymentType;
import Reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AirlineBookingSystemDemo {
    public static void main(String[] args) {
        AirlineBookingFacade airlineBookingFacade = new AirlineBookingFacade();

        User admin = new User("ADMIN", "admin", "adm", "1234567890");

        var flight1 = airlineBookingFacade.addFlight("Boeing123", FlightType.International, admin.getUserId());
        airlineBookingFacade.addFlight("Boeing234", FlightType.International, admin.getUserId());

        airlineBookingFacade.addSeats(flight1.getFlightId(), SeatType.Economy, 40, admin.getUserId());
        airlineBookingFacade.addSeats(flight1.getFlightId(), SeatType.BusinessClass, 20, admin.getUserId());
        airlineBookingFacade.addSeats(flight1.getFlightId(), SeatType.FirstClass, 10, admin.getUserId());

        airlineBookingFacade.addFlightSchedule(flight1.getFlightId(), "India", "USA",
                LocalDateTime.of(2024, 2, 2, 18, 0),
                LocalDateTime.of(2024, 2, 4, 10, 0), admin.getUserId());
        airlineBookingFacade.addFlightSchedule(flight1.getFlightId(), "USA", "India",
                LocalDateTime.of(2024, 2, 5, 18, 0),
                LocalDateTime.of(2024, 2, 7, 10, 0), admin.getUserId());
        airlineBookingFacade.addFlightSchedule(flight1.getFlightId(), "India", "USA",
                LocalDateTime.of(2024, 2, 9, 18, 0),
                LocalDateTime.of(2024, 2, 11, 10, 0), admin.getUserId());
        airlineBookingFacade.addFlightSchedule(flight1.getFlightId(), "USA", "India",
                LocalDateTime.of(2024, 2, 13, 18, 0),
                LocalDateTime.of(2024, 2, 15, 10, 0), admin.getUserId());

        User user1 = new User("B123", "Megha", "megha@gmail.com", "2345678910");
        User user2 = new User("B123", "Kriya", "kriya@gmail.com", "2945678910");

        var availableFlights = airlineBookingFacade.searchFlights("India", "USA", LocalDate.of(2024, 2, 2));
        for (var availableFlight : availableFlights) {
            System.out.println(availableFlight.toString());
        }
        System.out.println("------------------------------------------");
        Reservation reservation1 = airlineBookingFacade.bookFlight(PaymentType.Credit,
                1000, flight1.getFlightId(), user1.getUserId(), SeatType.FirstClass + "A2");
        System.out.println("ReservedNumber:" + reservation1.getReservationNumber());
        System.out.print("AvailableSeats:" + flight1.getFlightId() + "\t");
        System.out.println(airlineBookingFacade.getAvailableSeats(flight1.getFlightId()));
    }
}
