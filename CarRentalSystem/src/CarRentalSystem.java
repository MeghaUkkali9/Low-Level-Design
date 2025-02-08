import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CarRentalSystem {
    private static CarRentalSystem instance;
    private static final Object lock = new Object();

    private final Map<Integer, Car> carMap = new ConcurrentHashMap<>();
    private final Map<String, Reservation> reservationMap = new ConcurrentHashMap<>();

    private CarRentalSystem() {
    }

    public static CarRentalSystem getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new CarRentalSystem();
                }
            }
        }
        return instance;
    }

    public List<Car> getAvailableCars(CarType carType, LocalDate startDate, LocalDate endDate) {
        List<Car> availableCars = new ArrayList<>();

        for (Car car : carMap.values()) {
            if (car.getCarType().equals(carType) && car.isAvailable() && isCarAvailable(car.getCarId(), startDate, endDate)) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }

    public Reservation reserveCar(int customerId, int carId, LocalDate fromDate, LocalDate toDate) {
        Car car = carMap.get(carId);

        if (car == null) {
            throw new IllegalArgumentException("Car not found.");
        }

        synchronized (car) {
            if (!car.isAvailable() || !isCarAvailable(carId, fromDate, toDate)) {
                throw new IllegalArgumentException("Car is already reserved in this time range.");
            }

            if (!car.bookCar()) {
                throw new IllegalArgumentException("Car is already booked by another user.");
            }

            Reservation reservation = new Reservation(generateReservationId(), carId, customerId, fromDate, toDate);
            reservationMap.put(reservation.getReservationId(), reservation);

            return reservation;
        }
    }

    public Reservation updateReservation(Reservation updatedReservation) {
        Reservation existingReservation = reservationMap.get(updatedReservation.getReservationId());

        if (existingReservation == null) {
            throw new IllegalArgumentException("Reservation not found.");
        }

        if (updatedReservation.getCarId() != existingReservation.getCarId() &&
                !isCarAvailable(updatedReservation.getCarId(),
                        updatedReservation.getBookingDate(), updatedReservation.getReturnDate())) {
            throw new IllegalArgumentException("The new car is not available for the selected dates.");
        }

        reservationMap.put(existingReservation.getReservationId(), updatedReservation);
        return updatedReservation;
    }

    public void cancelReservation(String reservationId) {
        Reservation reservation = reservationMap.get(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation ID " + reservationId + " not found.");
        }

        Car car = carMap.get(reservation.getCarId());
        if (car == null) {
            throw new IllegalArgumentException("Car ID " + reservation.getCarId() + " not found.");
        }

        synchronized (car) {
            if (!car.isAvailable()) {
                car.makeAvailable();
            }
        }

        synchronized (reservation) {
            if (reservation.getStatus() == ReservationStatus.Cancelled) {
                throw new IllegalStateException("Reservation has already been cancelled.");
            }

            reservation.cancelReservation();
            reservationMap.remove(reservationId);
        }
    }

    public void addCar(Car car) {
        carMap.put(car.getCarId(), car);
    }

    public Car updateRentalPrice(int carId, double price) {
        Car car = carMap.get(carId);
        if (car == null) {
            throw new IllegalArgumentException("Car with ID " + carId + " not found.");
        }

        synchronized (car) {
            car.setRentalPrice(price);
        }
        return car;
    }

    private boolean isCarAvailable(int carId, LocalDate startDate, LocalDate endDate) {
        for (Reservation reservation : reservationMap.values()) {
            if (reservation.getCarId() == carId) {
                if (startDate.isBefore(reservation.getReturnDate()) && endDate.isAfter(reservation.getBookingDate())) {
                    return false;
                }
            }
        }
        return true;
    }

    private synchronized String generateReservationId() {
        return "RES" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
