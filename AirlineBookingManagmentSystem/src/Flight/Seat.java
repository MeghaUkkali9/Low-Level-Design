package Flight;

public class Seat {
    private String seatNumber;
    private boolean isAvailable;
    private SeatType seatType;

    public Seat(String seatNumber, SeatType seatType) {
        this.seatNumber = seatNumber;
        this.isAvailable = true;
        this.seatType = seatType;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public boolean bookSeat() {
        if(!this.isAvailable){
            throw new IllegalArgumentException("Seat is not available.");
        }
        this.isAvailable = false;
        return isAvailable;
    }

    public boolean cancelSeat() {
        this.isAvailable = true;
        return isAvailable;
    }

    public boolean isAvailable(){
        return this.isAvailable;
    }
}
