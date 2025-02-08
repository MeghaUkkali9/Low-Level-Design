package Payment;

public class DebitPayment implements IPayment {
    @Override
    public boolean processPayment(double amount) {
        return true;
    }
}
