package Payment;

public class CreditPayment implements IPayment {
    @Override
    public boolean processPayment(double amount) {
        return true;
    }
}
