import java.time.LocalDate;

public class Reservation {
    private String reservationId;
    private int carId;
    private int customerId;
    private ReservationStatus status;
    private LocalDate bookingDate;
    private LocalDate returnDate;

    public Reservation(String reservationId, int carId, int customerId, LocalDate bookingDate, LocalDate returningDate) {
        this.reservationId = reservationId;
        this.carId = carId;
        this.customerId = customerId;
        this.status = ReservationStatus.Booked;
        this.bookingDate = bookingDate;
        this.returnDate = returningDate;
    }

    public boolean cancelReservation(){
        if(this.status == ReservationStatus.Booked){
            this.status = ReservationStatus.Cancelled;
            return true;
        }
        throw new IllegalArgumentException(this.carId + " is not booked");
    }

    public String getReservationId() {
        return reservationId;
    }

    public int getCarId() {
        return carId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getReturnDate() {
        return this.returnDate;
    }

    public void setReturningDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
