import ExceptionHandling.PaymentProcessingException;
import Flight.Flight;
import Payment.PaymentProcessor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import Flight.*;
import Payment.PaymentType;
import Reservation.Reservation;
import Reservation.ReservationManager;

public class AirlineBookingFacade {
    // use concurrent hashmaps for better usage of for concurrent users
    FlightManager flightManager;
    ReservationManager reservationManager;
    PaymentProcessor paymentProcessor;

    public AirlineBookingFacade() {
        flightManager = FlightManager.getInstance();
        reservationManager = ReservationManager.getInstance();
        paymentProcessor = PaymentProcessor.getInstance();
    }

    public List<FlightSearchResult> searchFlights(String source, String destination, LocalDate localDate) {
        //Flight manager is responsible for creating flight and keeping flight data consistent.
        // keeping seat allocation consistent as we don't want to create new object everytime multiple users
        // asks for flight data. so we need singleton.
        //Multiple users searching for flights should get results from the same repository of flight data managed by the singleton.
        return flightManager.searchFlights(source, destination, localDate);
    }

    public Map<SeatType, Integer> getAvailableSeats(String flightNumber) {
        return flightManager.getAvailableSeats(flightNumber);
    }

    public synchronized Reservation bookFlight(PaymentType payment, double amount, String flightNumber,
                                               String userId, String seatNumber) {
        if (!flightManager.isSeatAvailable(seatNumber, flightNumber)) {
            throw new IllegalArgumentException("The seat is already booked by another passenger.");
        }
        flightManager.bookFlightSeat(seatNumber, flightNumber);
        if (processPayment(payment, amount)) {
            return reservationManager.reserve(flightNumber, userId, seatNumber);
        } else {
            flightManager.cancelFlightSeat(seatNumber, flightNumber);
            throw new PaymentProcessingException("Payment failed for booking.");
        }
    }

    public synchronized Reservation cancelFlight(String reservationNumber) {
        var reservation = reservationManager.cancelReservation(reservationNumber);
        //process refund.
        flightManager.cancelFlightSeat(reservation.getSeatNumber(), reservation.getFlightId());
        return reservation;
    }

    private boolean processPayment(PaymentType payment, double amount) {
        return paymentProcessor.processPayment(payment, amount);
    }

    public Flight addFlight(String flightNumber, FlightType flightType, String userId) {
        if (!userId.equals("ADMIN")) {
            throw new IllegalArgumentException("No access to add Flight for this user:" + userId);
        }
        return flightManager.addFlight(flightNumber, flightType);
    }

    public FlightSchedule addFlightSchedule(String flightNumber, String source, String destination,
                                            LocalDateTime arrivalDate, LocalDateTime depatureTime, String userId) {
        if (!userId.equals("ADMIN")) {
            throw new IllegalArgumentException("No access to add Flight for this user:" + userId);
        }
        return flightManager.addFlightSchedule(flightNumber, source, destination, arrivalDate, depatureTime);
    }

    public Flight addSeats(String flightNumber, SeatType seatType, int seatCount, String userId) {
        if (!userId.equals("ADMIN")) {
            throw new IllegalArgumentException("No access to add Flight for this user:" + userId);
        }
        return flightManager.addSeats(flightNumber, seatType, seatCount);
    }
}

//TODO:
/*
 * process refund when we cancel booking
 * calculate price: simple, dynamic, addons
 * payment: it just gets total payment, this is fine.
 * concurrency check where it is required.
 * */
