import Payment.PaymentService;
import Payment.PaymentType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HotelManagementSystem {
    private Map<Integer, Room> roomMap;
    private Map<String, Order> orderMap;
    private Map<Integer, User> userMap;

    public static HotelManagementSystem Instance;

    public HotelManagementSystem() {
        roomMap = new HashMap<>();
        orderMap = new HashMap<>();
        userMap = new HashMap<>();
    }

    public static synchronized HotelManagementSystem getInstance() {
        if (Instance == null) {
            Instance = new HotelManagementSystem();
        }
        return Instance;
    }

    public void addRoom(Room room) {
        if (roomMap.containsKey(room.getId())) {
            throw new IllegalArgumentException("Room already exists.");
        }
        roomMap.put(room.getId(), room);
    }

    public void updateRoom(Room room) {
        Room existingRoom = roomMap.get(room.getId());
        if (existingRoom != null) {
            existingRoom.setRoomType(room.getRoomType());
            existingRoom.setRoomStatus(room.getRoomStatus());
        } else {
            throw new IllegalArgumentException("Room Id does not exist.");
        }
    }

    public void removeRoom(int roomId) {
        if (roomMap.containsKey(roomId)) {
            roomMap.remove(roomId);
        } else {
            throw new IllegalArgumentException("Room Id is not existed");
        }
    }

    public Room getRoom(int roomId) {
        if (roomMap.containsKey(roomId)) {
            return roomMap.get(roomId);
        } else {
            throw new IllegalArgumentException("Room Id is not existed");
        }
    }

    public void addUser(User user) {
        if (!userMap.containsKey(user.getUserId())) {
            userMap.put(user.getUserId(), user);
        } else {
            throw new IllegalArgumentException("User is already existed in the system");
        }
    }

    public User updateUser(User user) {
        if (userMap.containsKey(user.getUserId())) {
            return userMap.put(user.getUserId(), user);
        } else {
            throw new IllegalArgumentException("UserId not existed in the system");
        }
    }

    public synchronized Order bookRoom(int userId, int roomId, LocalDate checkIn, LocalDate checkOut) {
        // Validate the user
        if (!userMap.containsKey(userId)) {
            throw new IllegalArgumentException("User does not exist: " + userId);
        }

        // Validate the room
        Room room = roomMap.get(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room does not exist: " + roomId);
        }

        // Check room availability
        if (room.getRoomStatus() != RoomStatus.Available) {
            throw new IllegalArgumentException("Room is not available.");
        }

        // Book the room and create the order
        room.bookRoom();
        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, userId, roomId, checkIn, checkOut);
        order.orderRoom();

        // Store the order
        orderMap.put(orderId, order);

        return order;
    }

    public synchronized boolean checkIn(int roomId) {
        try {
            Room room = roomMap.get(roomId);
            // Booked room should be checked before allowing user to checkIn.
            if(room.getRoomStatus() == RoomStatus.Booked){
                room.checkIn();
                return true;
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception);
        }
        return false;
    }

    public synchronized boolean checkOut(String orderId, PaymentType paymentType, List<Double> addOns) {
        try {
            Order order = orderMap.get(orderId);
            Room room = roomMap.get(order.getRoomId());
            room.checkOut();

            boolean paymentSuccess = processPayment(order.getCheckInDate(),
                    order.getCheckOutDate(), room, order, paymentType, addOns);
            if (!paymentSuccess) {
                throw new IllegalArgumentException("Payment failed to process");
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error during Payment: " + e.getMessage());
            return false;
        }
    }

    private boolean processPayment(LocalDate checkInDate, LocalDate checkOutDate, Room room, Order order,
                                   PaymentType paymentType, List<Double> addOns) {
            double totalAmount = calculatePayment(checkInDate, checkOutDate, room, addOns);
            PaymentService paymentService = new PaymentService();
            boolean paymentSuccess = paymentService.pay(totalAmount, order.getOrderId(), paymentType);

            if (paymentSuccess) {
                order.setTotalAmount(totalAmount);
                order.setPaymentType(paymentType);
                order.setOrderStatus(OrderStatus.Completed);

                return true;
            } else {
                return false;
            }
    }

    private double calculatePayment(LocalDate checkInDate, LocalDate checkOutDate, Room room, List<Double> addOns) {
        int numberOfDays = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        double roomPrice = room.getRoomType().getPrice();

        double totalAddOns = 0;
        if(addOns == null){
            throw new IllegalArgumentException("Null Pointer exception");
        }
        if( addOns != null || !addOns.isEmpty()){
            for (double addon: addOns){
                totalAddOns += addon;
            }
        }
        return (numberOfDays * roomPrice) + totalAddOns;
    }

    // how to handle addons.
    // support multiple mode of payments.:strategy
    // what design patterns I should be handling. factory, strategy, singleton

    // learn what to return while designing, scopes.
    // how I should be handling categories, payment, addons, price for each room.
    // how many deign patterns.


    // who should be user, admin?
    // what features we should provide to admin so that other users cant access it.
}
