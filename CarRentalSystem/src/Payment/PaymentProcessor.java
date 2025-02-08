package Payment;

public class PaymentProcessor {
    private static PaymentProcessor PaymentProcessorInstance;
    private static Object lock = new Object();

    private PaymentProcessor() {
    }

    public static PaymentProcessor getInstance() {
        if (PaymentProcessorInstance == null) {
            synchronized (lock) {
                if (PaymentProcessorInstance == null) {
                    PaymentProcessorInstance = new PaymentProcessor();
                }
            }
        }
        return PaymentProcessorInstance;
    }

    public boolean processPayment(double totalAmount, PaymentMethod paymentType) {
        var paymentFactory = PaymentFactory.getPayment(paymentType);
        return paymentFactory.processPayment(totalAmount);
    }
}

