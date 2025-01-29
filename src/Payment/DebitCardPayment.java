package Payment;

public class DebitCardPayment implements IPayment{
    public boolean processPayment(double amount) {
        System.out.println("Processing payment with credit card of total amount" + amount);
        return true;
    }
}
