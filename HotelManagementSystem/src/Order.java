import Payment.PaymentType;

import java.time.LocalDate;

public class Order {
    private String orderId;
    private int userId;
    private int roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private OrderStatus  orderStatus;
    private double TotalAmount;
    private PaymentType paymentType;

    public Order(String orderId,
                 int userId,
                 int roomId,
                 LocalDate checkInDate,
                 LocalDate checkOutDate){
        this.orderId = orderId;
        this.userId = userId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.orderStatus = OrderStatus.Pending;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void orderRoom(){
        if(this.orderStatus == OrderStatus.Booked){
            throw new IllegalArgumentException("Room is already booked.");
        }
        this.orderStatus = OrderStatus.Booked;
    }

    public void cancelRoom(){
        if(this.orderStatus == OrderStatus.Cancelled){
            throw new IllegalArgumentException("Order is already cancelled.");
        }
        this.orderStatus = OrderStatus.Cancelled;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        TotalAmount = totalAmount;
    }
}
