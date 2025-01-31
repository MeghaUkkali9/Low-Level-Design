import Payment.PaymentType;

import java.time.LocalDate;
import java.util.ArrayList;

public class HotelManagementSystemDemo {
    public static void main(String[] args) {
        HotelManagementSystem hotelManagementSystem = HotelManagementSystem.getInstance();

        //Adding Users
        User user1 = new User(123,"Megha", "U",
                "484-685-8171", "megha");
        User user2 = new User(124,"Saroja", "U",
                "484-685-8172", "saroja");
        hotelManagementSystem.addUser(user1);
        hotelManagementSystem.addUser(user2);

        //Adding Rooms
        RoomType roomType1 = new RoomType(RoomCategory.DoubleRoom, 1200);
        RoomType roomType2 = new RoomType(RoomCategory.SingleRoom, 800);
        RoomType roomType3 = new RoomType(RoomCategory.Deluxe, 2400);
        RoomType roomType4 = new RoomType(RoomCategory.Premium, 4800);

        Room doubleRoom1 = new Room(101, roomType1);
        Room doubleRoom2 = new Room(102, roomType1);
        Room doubleRoom3 = new Room(103, roomType1);

        Room singleRoom1 = new Room(104, roomType2);
        Room singleRoom2 = new Room(105, roomType2);

        hotelManagementSystem.addRoom(doubleRoom1);
        hotelManagementSystem.addRoom(doubleRoom2);
        hotelManagementSystem.addRoom(doubleRoom3);
        hotelManagementSystem.addRoom(singleRoom1);
        hotelManagementSystem.addRoom(singleRoom2);

        //Book room

        Order order = hotelManagementSystem.bookRoom(123, 101, LocalDate.of(2024,11,19)
                , LocalDate.of(2024,11,20));

        hotelManagementSystem.checkIn(101);
        hotelManagementSystem.checkOut(order.getOrderId(), PaymentType.Cash, new ArrayList<>());

    }
}
