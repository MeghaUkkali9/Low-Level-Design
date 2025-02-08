package Payment;

public class PaymentFactory {
    public static IPayment getPaymentType(PaymentType payment) {
        if (payment.equals(PaymentType.UPI)) {
            return new UPIPayment();
        } else if (payment.equals(PaymentType.Credit)) {
            return new CreditCardPayment();
        } else if (payment.equals(PaymentType.Debit)) {
            return new DebitCardPayment();
        } else {
            throw new IllegalArgumentException(payment + " is not supported.");
        }
    }
}
