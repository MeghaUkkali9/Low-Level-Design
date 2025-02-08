package Payment;

public class CashPayment implements IPayment {
    @Override
    public boolean processPayment(double amount) {
        return true;
    }
}
