package Flight;

import ExceptionHandling.DuplicateFlightException;
import ExceptionHandling.FlightNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FlightManager {
    private static FlightManager Instance;
    private static Object lock = new Object();
    private static Map<String, Flight> map = new ConcurrentHashMap<String, Flight>();

    private FlightManager() {
    }

    public static FlightManager getInstance() {
        if (Instance == null) {
            synchronized (lock) {
                if (Instance == null) {
                    Instance = new FlightManager();
                }
            }
        }
        return Instance;
    }

    //Its good idea : while searching for flight, we can display available seats for users.
    public List<FlightSearchResult> searchFlights(String source, String destination, LocalDate localDate) {
        var flightSearchResults = new ArrayList<FlightSearchResult>();

        for (Map.Entry<String, Flight> entry : map.entrySet()) {
            var flight = entry.getValue();
            var flightSchedules = flight.getFlightSchedules();
            for (var flightSchedule : flightSchedules) {
                if (flightSchedule.getSource().equals(source) &&
                        flightSchedule.getDestination().equals(destination) &&
                        flightSchedule.getArrivalDate().toLocalDate().equals(localDate)) {
                    FlightSearchResult flightSearchResult = new FlightSearchResult(
                            flight.getFlightId(),
                            flightSchedule.getSource(),
                            flightSchedule.getDestination(),
                            flight.getAvailableSeats());
                    flightSearchResults.add(flightSearchResult);
                }
            }

        }
        return flightSearchResults;
    }

    public Map<SeatType, Integer> getAvailableSeats(String flightNumber) {
        Flight flight = map.get(flightNumber);
        if (flight == null) {
            throw new FlightNotFoundException("Flight not found: " + flightNumber);
        }
        return flight.getAvailableSeats();
    }

    public synchronized Flight addFlight(String flightNumber, FlightType flightType) {
        if (map.containsKey(flightNumber)) {
            throw new DuplicateFlightException(flightNumber + " is already existed in the system");
        }

        Flight flight = new Flight(flightNumber, flightType);
        map.put(flightNumber, flight);
        return flight;
    }

    public synchronized FlightSchedule addFlightSchedule(String flightNumber, String source, String destination,
                                                         LocalDateTime arrivalDate, LocalDateTime depatureTime) {
        var flight = map.get(flightNumber);
        if (flight == null) {
            throw new FlightNotFoundException(flightNumber + " is not existed in the system");
        }
        int scheduleId = flight.getFlightSchedules().isEmpty()
                ? 1
                : Integer.valueOf(flight.getFlightSchedules()
                .get(flight.getFlightSchedules().size() - 1)
                .getScheduleId()) + 1;
        var flightSchedule = new FlightSchedule(
                String.valueOf(scheduleId),
                flightNumber, source, destination, arrivalDate, depatureTime
        );
        flight.addSchedule(flightSchedule);
        return flightSchedule;
    }

    public Flight addSeats(String flightNumber, SeatType seatType, int seatCount) {
        Flight flight = map.get(flightNumber);
        if (flight == null) {
            throw new FlightNotFoundException(flightNumber + " is not existed in the system");
        }
        flight.addSeats(seatCount, seatType);
        return flight;
    }

    public Flight updateFlightSchedule(String flightNumber, String scheduleId, String source, String destination,
                                       LocalDateTime arrivalDate, LocalDateTime depatureTime) {
        if (map.containsKey(flightNumber)) {
            Flight flight = map.get(flightNumber);
            var flightSchedules = flight.getFlightSchedules();
            for (var schedule : flightSchedules) {
                if (scheduleId.equals(schedule.getScheduleId())) {
                    synchronized (flight) {
                        schedule.setSource(source);
                        schedule.setDestination(destination);
                        schedule.setArrivalDate(arrivalDate);
                        schedule.setDepartureDate(depatureTime);
                    }
                    return flight;
                }
            }
        }
        throw new IllegalArgumentException(flightNumber + "or" + scheduleId + " is not existed in the system");
    }

    public void removeFlight(String flightNumber) {
        if (map.containsKey(flightNumber)) {
            map.remove(flightNumber);
        } else {
            throw new IllegalArgumentException(flightNumber + " is not existed in the system");
        }
    }

    public synchronized boolean bookFlightSeat(String seatNumber, String flightNumber) {
        Flight flight = map.get(flightNumber);
        if (flight == null) {
            throw new FlightNotFoundException("Flight not found: " + flightNumber);
        }
        flight.bookSeat(seatNumber);
        return true;
    }

    public synchronized boolean cancelFlightSeat(String seatNumber, String flightNumber) {
        Flight flight = map.get(flightNumber);
        if (flight == null) {
            throw new FlightNotFoundException("Flight not found: " + flightNumber);
        }
        flight.cancelSeatBooking(seatNumber);
        return true;
    }

    public synchronized boolean isSeatAvailable(String seatNumber, String flightNumber) {
        Flight flight = map.get(flightNumber);
        Seat seat = flight.getSeats().stream()
                .filter(x -> x.getSeatNumber().equals(seatNumber))
                .findFirst()
                .orElse(null);
        return seat != null && seat.isAvailable();
    }
}
