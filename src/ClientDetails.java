import java.io.Serializable;

public class ClientDetails implements Serializable {
    private String name;
    private String phoneNumber;
    private String address;
    public ClientDetails(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    public String getName() {
        return name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public String toString() {
        return "Name: " + name + "\nPhone Number: " + phoneNumber + "\nAddress: " + address;
    }


}
