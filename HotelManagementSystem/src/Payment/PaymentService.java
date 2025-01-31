package Payment;

public class PaymentService{

    public boolean pay(double amount, String orderId, PaymentType paymentType){
        PaymentStrategy paymentProcessor = PaymentStrategyFactory.getPaymentProcessor(paymentType);
        return paymentProcessor.processPayment(amount, orderId);
    }
}
