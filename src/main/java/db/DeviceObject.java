package db;

public class DeviceObject {
    public int id;
    public String name;
    public String type;
    public String ip_address;
    public String location;
    public DeviceObject(int id, String name, String type, String ip_address, String location) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.ip_address = ip_address;
        this.location = location;
    }
}
