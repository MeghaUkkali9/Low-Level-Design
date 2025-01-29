package Flight;

import java.util.Map;

public class FlightSearchResult {
    private String flightNumber;
    private String source;
    private String destination;
    private Map<SeatType, Integer> seats;

    public FlightSearchResult(String flightNumber, String source, String destination, Map<SeatType, Integer> seats) {

        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.seats = seats;
    }

    public Map<SeatType, Integer> getSeats() {
        return seats;
    }

    public String getDestination() {
        return destination;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getSource() {
        return source;
    }

    public String toString() {
        return "FlightNumber:" + this.flightNumber +
                "\tSource:" + this.source +
                "\tDestination:" + this.destination +
                "\tAvailableSeats:" + this.seats + "\n";
    }
}
