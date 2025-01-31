package Payment;

public class PaymentStrategyFactory {

    public static PaymentStrategy getPaymentProcessor(PaymentType paymentType) {
            switch (paymentType) {
                case Cash:
                    return new CashPayment();
                case CreditCard:
                    return new CreditCardPayment();
                case DebitCard:
                    return new DebitCardPayment();
                default:
                    throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
            }
    }
}
