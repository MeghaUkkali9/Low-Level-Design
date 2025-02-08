package Reservation;

import ExceptionHandling.ReservationNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ReservationManager {
    private static ReservationManager Instance;
    private static Object lock = new Object();
    private Map<String, Reservation> map = new ConcurrentHashMap<String, Reservation>();
    private final AtomicInteger bookingCounter = new AtomicInteger(0);

    private ReservationManager() {
    }

    public static ReservationManager getInstance() {
        if (Instance == null) {
            synchronized (lock) {
                if (Instance == null) {
                    Instance = new ReservationManager();
                }
            }
        }
        return Instance;
    }

    public synchronized Reservation reserve(String flightNumber, String userId, String seatNumber) {
        String bookingNumber = generateBookingNumber();
        Reservation reservation = new Reservation(userId, bookingNumber, flightNumber, seatNumber);

        reservation.reserve();
        map.put(bookingNumber, reservation);
        return reservation;
    }

    public synchronized Reservation cancelReservation(String reservationNumber) {
        if (map.containsKey(reservationNumber)) {
            Reservation reservation = map.get(reservationNumber);

            reservation.cancelReservation();
            map.put(reservation.getReservationNumber(), reservation);
            return reservation;
        } else {
            throw new ReservationNotFoundException(reservationNumber + " is not existed.");
        }
    }

    private String generateBookingNumber() {
        int bookingId = bookingCounter.incrementAndGet();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "BKG" + timestamp + String.format("%06d", bookingId);
    }
}
