public class Room {
    private int id;
    private RoomType roomType;
    private RoomStatus roomStatus;

    public Room(int id, RoomType roomType){
        this.id = id;
        this.roomType = roomType;
        this.roomStatus = RoomStatus.Available;
    }

    public int getId(){
        return this.id;
    }

    public RoomStatus getRoomStatus(){
        return this.roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus){
        this.roomStatus = roomStatus;
    }

    public RoomType getRoomType(){
        return this.roomType;
    }
    public void setRoomType(RoomType roomType){
        this.roomType = roomType;
    }

    public boolean isAvailable(){
        return this.roomStatus == RoomStatus.Available;
    }

    public void bookRoom(){
        if(this.roomStatus == RoomStatus.Booked){
            throw new IllegalArgumentException("Room is already booked");
        }
        this.roomStatus = RoomStatus.Booked;
    }

    public void cancelRoom(){
        this.roomStatus = RoomStatus.Available;
    }

    public void checkIn(){
        if(this.roomStatus == RoomStatus.Booked){
            this.roomStatus = RoomStatus.Occupied;
        }else {
            throw new IllegalArgumentException("Room is not Booked!");
        }
    }

    public void checkOut(){
        if(this.roomStatus == RoomStatus.Occupied){
            this.roomStatus = RoomStatus.Available;
        }else {
            throw new IllegalArgumentException("Room is not occupied!");
        }
    }
}
