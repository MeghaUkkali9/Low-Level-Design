package Payment;

interface PaymentStrategy {
    boolean processPayment(double amount, String orderId);
}
