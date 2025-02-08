import java.time.LocalDate;
import java.util.List;

public class CarRentalDemo {


    public static void main(String[] args) {
        CarRentalSystem rentalSystem = CarRentalSystem.getInstance();

        Car car1 = new Car(1,"sel", "KA28990",  CarType.SUV, 50.0);
        Car car2 = new Car(2, "x3","KE34569",CarType.Sedan, 30.0);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);

        LocalDate startDate = LocalDate.of(2025, 2, 10);
        LocalDate endDate = LocalDate.of(2025, 2, 15);

        List<Car> availableCars = rentalSystem.getAvailableCars(CarType.SUV, startDate, endDate);

        System.out.println("Available Cars:");
        for (Car car : availableCars) {
            System.out.println("Car ID: " + car.getCarId() + ", Type: " + car.getCarType() + ", Price: " + car.getRentalPrice());
        }

        Reservation reservation = rentalSystem.reserveCar(1001, 1, startDate, endDate);
        System.out.println("\nReservation made: " + reservation.getReservationId() + " for Car ID: " + reservation.getCarId());

        Reservation updatedReservation = new Reservation(reservation.getReservationId(), 1, 1001, startDate.plusDays(1), endDate.plusDays(1));
        rentalSystem.updateReservation(updatedReservation);
        System.out.println("\nUpdated Reservation: " + updatedReservation.getReservationId());

        rentalSystem.cancelReservation(reservation.getReservationId());
        System.out.println("\nReservation " + reservation.getReservationId() + " has been canceled.");
    }

}
