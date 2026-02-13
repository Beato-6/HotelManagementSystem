public class Guest {

    private int id;
    private String name;
    private int roomNumber;
    private String phone;

    public Guest(int id, String name, int roomNumber, String phone) {
        this.id = id;
        this.name = name;
        this.roomNumber = roomNumber;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getPhone() {
        return phone;
    }
}
