package Payment;

public class CashPayment implements PaymentStrategy {

    @Override
    public boolean processPayment(double amount, String orderId) {
        System.out.println("Payment is processed for orderId:"+orderId+" pricing at: "+amount +" through cash.");
        return true;
    }
}
