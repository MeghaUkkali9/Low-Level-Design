import java.util.concurrent.locks.ReentrantLock;

class Car {
    private final int carId;
    private final String carModel;
    private final String slateNo;
    private final CarType carType;
    private double rentalPrice;
    private boolean isAvailable;
    private final ReentrantLock lock = new ReentrantLock();

    public Car(int carid, String carModel, String slateNo, CarType carType, double rentalPrice) {
        this.carId = carid;
        this.carModel = carModel;
        this.slateNo = slateNo;
        this.carType = carType;
        this.rentalPrice = rentalPrice;
        this.isAvailable = true;
    }

    public int getCarId() {
        return carId;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getSlateNo() {
        return slateNo;
    }

    public CarType getCarType() {
        return carType;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        synchronized (lock) {
            this.rentalPrice = rentalPrice;
        }
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void makeAvailable() {
        lock.lock();
        try {
            if (this.isAvailable) {
                throw new IllegalArgumentException("Car is already available.");
            }
            this.isAvailable = true;
        } finally {
            lock.unlock();
        }
    }

    public boolean bookCar() {
        lock.lock();
        try {
            if (!this.isAvailable) {
                return false;
            }
            this.isAvailable = false;
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "car id: " + this.carId +
                "\tcar model: " + this.carModel +
                "\tSlate no: " + this.slateNo +
                "\tRentalPrice: " + this.rentalPrice;
    }
}
