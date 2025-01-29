package Flight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Flight {
    private final String flightId;
    private FlightType flightType;
    private FlightStatus flightStatus;
    private List<FlightSchedule> schedules;
    private ConcurrentHashMap<String, Seat> seats;

    public Flight(String flightId,
                  FlightType flightType) {
        this.flightId = flightId;
        this.flightType = flightType;
        this.schedules = new ArrayList<>();
        this.seats = new ConcurrentHashMap<>();
        this.flightStatus = FlightStatus.Landed;
    }

    public Map<SeatType, Integer> getAvailableSeats() {
        Map<SeatType, Integer> seatMap = new HashMap<>();
        for (Seat seat : this.seats.values()) {
            if (seat.isAvailable()) {
                seatMap.put(seat.getSeatType(),
                        seatMap.getOrDefault(seat.getSeatType(), 0) + 1);
            }
        }
        return seatMap;
    }

    public List<Seat> addSeats(int seatCount, SeatType seatType) {
        for (int i = 0; i < seatCount; i++) {
            seats.put(seatType.toString() + "A" + i, new Seat(seatType + "A" + i, seatType));
        }
        return this.seats.values().stream().toList();
    }

    public List<Seat> getSeats() {
        return this.seats.values().stream().toList();
    }

    public FlightSchedule addSchedule(FlightSchedule flightSchedule) {
        schedules.add(flightSchedule);
        return flightSchedule;
    }

    public List<FlightSchedule> getFlightSchedules() {
        return schedules;
    }

    public synchronized void bookSeat(String seatNumber) {
        Seat seat = seats.get(seatNumber);
        if (seat == null) {
            throw new IllegalArgumentException("Seat " + seatNumber + " does not exist.");
        }
        if (!seat.isAvailable()) {
            throw new IllegalStateException("Seat " + seatNumber + " is already booked.");
        }
        seat.bookSeat();
    }

    public synchronized void cancelSeatBooking(String seatNumber) {
        Seat seat = seats.get(seatNumber);
        if (seat == null) {
            throw new IllegalArgumentException("Seat " + seatNumber + " does not exist.");
        }
        if (seat.isAvailable()) {
            throw new IllegalStateException("Seat " + seatNumber + " is not booked.");
        }
        seat.cancelSeat();
    }

    public String getFlightId() {
        return flightId;
    }

    public FlightType getFlightType() {
        return flightType;
    }

    public void setFlightType(FlightType flightType) {
        this.flightType = flightType;
    }

    public FlightStatus getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(FlightStatus flightStatus) {
        this.flightStatus = flightStatus;
    }
}
