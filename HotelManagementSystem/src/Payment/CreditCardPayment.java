package Payment;

public class CreditCardPayment implements PaymentStrategy {

    @Override
    public boolean processPayment(double amount, String orderId) {
        System.out.println("Payment is processed for orderId:"+orderId+" pricing at: "+amount +" through credit card.");
        return true;
    }
}
