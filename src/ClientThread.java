import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
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
    private void connectAndCommunicate() throws Exception {


        try {
            Socket socket = new Socket(host, PORT);
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            oos.writeObject(sendPayload);
            recvPayload = ois.readObject();
            if (recvPayload instanceof ServerResponse) {
                ResponseType respType = ((ServerResponse) recvPayload).getResponseType();
                switch (respType) {
                    case MENU:
                        Platform.runLater(() -> {
                            cont.updateStatus("Status: Connected to server");
                            List<MenuItem> menu = (List<MenuItem>) ((ServerResponse)recvPayload).getPayload();
                            cont.updateMenuDisplay(menu);


                        });
                        break;
                    case ORDER_CONFIRMATION:
                        Platform.runLater(() -> {
                        });
                        break;
                    case ERROR:
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
                if(sendPayload == null)
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

    public void run() {

        try {
            connectAndCommunicate();
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
