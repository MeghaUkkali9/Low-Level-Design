package Flight;

import java.time.LocalDateTime;

public class FlightSchedule {
    private final String scheduleId;
    private final String flightNumber;
    private String source;
    private String destination;
    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;

    public FlightSchedule(String scheduleId,
                          String flightNumber,
                          String source,
                          String destination,
                          LocalDateTime arrivalDate,
                          LocalDateTime departureDate) {
        this.scheduleId = scheduleId;
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}
