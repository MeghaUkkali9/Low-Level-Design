package Payment;

public class PaymentProcessor {
    private static PaymentProcessor Instance;
    private static Object lock = new Object();

    private PaymentProcessor(){}

    public static PaymentProcessor getInstance(){
        if (Instance == null){
            synchronized (lock){
                if (Instance == null){
                    Instance = new PaymentProcessor();
                }
            }
        }
        return Instance;
    }

    public boolean processPayment(PaymentType payment, double amount){
        IPayment paymentType = PaymentFactory.getPaymentType(payment);
        return paymentType.processPayment(amount);
    }
}
