import java.io.Serializable;
public class MenuItem implements Serializable {
    int id;
    String type;
    String itemDesc;
    double price;

    public MenuItem(int id, String type, String itemDesc, double price) {
        this.id = id;
        this.type = type;
        this.itemDesc = itemDesc;
        this.price = price;
    }
    public int getId() {
        return id;
    }
    public String getType() {
        return type;

    }
    public String getItemDesc() {
        return itemDesc;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Type: " + type + "\nDescription: " + itemDesc + "\nPrice: $" + price;
    }


}
