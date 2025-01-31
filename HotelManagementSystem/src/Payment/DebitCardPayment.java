package Payment;

public class DebitCardPayment implements PaymentStrategy {

    @Override
    public boolean processPayment(double amount, String orderId) {
        System.out.println("Payment is processed for orderId:"+orderId+" pricing at: "+amount +" through debit card.");
        return true;
    }
}
