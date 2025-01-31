public class RoomType {
    private RoomCategory roomCategory;
    private double price;

    public RoomType(RoomCategory roomCategory, double price){
        this.roomCategory = roomCategory;
        this.price = price;
    }

    public RoomCategory getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(RoomCategory roomCategory) {
        this.roomCategory = roomCategory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
