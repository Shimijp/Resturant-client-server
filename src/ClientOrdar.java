import java.io.Serializable;
import java.util.List;

public class ClientOrdar implements Serializable {
    private ClientDetails clientDetails;
    private List<MenuItem> orderedItems;
    public ClientOrdar(ClientDetails clientDetails, List<MenuItem> orderedItems) {
        this.clientDetails = clientDetails;
        this.orderedItems = orderedItems;
    }
    public ClientDetails getClientDetails() {
        return clientDetails;
    }
    public List<MenuItem> getOrderedItems() {
        return orderedItems;
    }
    public  void setOrderedItems(List<MenuItem> orderedItems) {
        this.orderedItems = orderedItems;
    }
}
