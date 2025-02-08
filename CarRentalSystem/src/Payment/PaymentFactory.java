package Payment;

public class PaymentFactory {
    public static IPayment getPayment(PaymentMethod paymentType) {
        switch (paymentType) {
            case Cash -> {
                return new CashPayment();
            }
            case Debit -> {
                return new DebitPayment();
            }
            case Credit -> {
                return new CreditPayment();
            }
            default -> throw new IllegalArgumentException(paymentType + " is not supported");
        }
    }
}
