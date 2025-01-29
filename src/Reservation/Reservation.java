package Reservation;

public class Reservation {
    private String userId;
    private String reservationNumber;
    private String flightId;
    private String seatNumber;
    private ReservationStatus status;

    public Reservation(String userId,
                       String reservationNumber,
                       String flightId,
                       String seatNumber) {
        this.userId = userId;
        this.reservationNumber = reservationNumber;
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.status = ReservationStatus.Booked;
    }

    public String getUserId() {
        return userId;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public boolean reserve(){
        this.status = ReservationStatus.Booked;
        return true;
    }

    public boolean cancelReservation(){
        if(this.status == ReservationStatus.Booked) {
            this.status = ReservationStatus.Cancelled;
            return true;
        }else {
            throw new IllegalArgumentException("Not Reserved");
        }
    }
}
