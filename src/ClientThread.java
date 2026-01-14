import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class ClientThread implements Runnable {
    private ClientController cont;
    private String host;
    private Object recvPayload;
    private Object sendPayload;
    private final int PORT = 3333;


    public ClientThread(ClientController cont, String host) {
        this.cont = cont;
        this.host = host;
        sendPayload = new ClientRequest(RequestType.GET_MENU, null);

    }

    /**
     * Initial connection to server and communication
     * should start communication with server, and get menu from it
     * @throws Exception
     */
    private void initialConnectAndCommunicate() throws Exception {


        try {
            Socket socket = new Socket(host, PORT);
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            oos.writeObject(sendPayload);
            recvPayload = ois.readObject();// read server response
            if (recvPayload instanceof ServerResponse) {
                ResponseType respType = ((ServerResponse) recvPayload).getResponseType();
                switch (respType) {
                    case MENU:// got menu from server and display it
                        Platform.runLater(() -> {
                            cont.updateStatus("Status: Connected to server");
                            List<MenuItem> menu = (List<MenuItem>) ((ServerResponse)recvPayload).getPayload();
                            cont.updateMenuDisplay(menu);
                            hasRecvMenu = true;
                        });
                        break;
                    case ERROR: // got error from server
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Server Error");
                            alert.setHeaderText("An error occurred on the server");
                            alert.setContentText((String) ((ServerResponse) recvPayload).getPayload());
                            alert.showAndWait();
                        });
                        break;
                    default:
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Response Error");
                            alert.setHeaderText("Unknown response type from server");
                            alert.setContentText("The server sent an unrecognized response type.");
                            alert.showAndWait();
                        });
                }
                if(sendPayload != null)
                {
                    oos.writeObject(sendPayload);
                }
                ois.close();
                is.close();
                oos.close();
                os.close();
                socket.close();

            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    //this will be called from the controller when user wants to place an order
    public void sendOderDetails() throws Exception
    {
        try {
            Socket socket = new Socket(host, PORT);
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            ClientDetails details = cont.getClientDetails();
            List<MenuItem> orderedItems = cont.getCartItems();
            sendPayload = new ClientOrdar(details, orderedItems);
            sendPayload = new ClientRequest(RequestType.PLACE_ORDER, sendPayload);
            oos.writeObject(sendPayload);
            recvPayload = ois.readObject();// read server response
            if (recvPayload instanceof ServerResponse) {
                ResponseType respType = ((ServerResponse) recvPayload).getResponseType();
                switch (respType) {
                    case ORDER_CONFIRMATION:// got order confirmation from server
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Order Confirmation");
                            alert.setHeaderText("Order Placed Successfully");
                            alert.setContentText((String) ((ServerResponse) recvPayload).getPayload());
                            alert.showAndWait();
                            cont.resetOrder(); // clear the order after successful placement
                        });
                        break;
                    case ERROR: // got error from server
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Server Error");
                            alert.setHeaderText("An error occurred on the server");
                            alert.setContentText((String) ((ServerResponse) recvPayload).getPayload());
                            alert.showAndWait();
                        });
                        break;
                    default: //unknown response type
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Response Error");
                            alert.setHeaderText("Unknown response type from server");
                            alert.setContentText("The server sent an unrecognized response type.");
                            alert.showAndWait();
                        });
                }
            }
            ois.close();
            is.close();
            oos.close();
            os.close();
            socket.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public void run() {

        try {
            initialConnectAndCommunicate();

        } catch (Exception e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection Error");
                alert.setHeaderText("Could not connect to server");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            });

        }
    }

}
