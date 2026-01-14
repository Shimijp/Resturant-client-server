import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.Optional;

public class ClientController {

    @FXML private Label statusLabel;

    @FXML private VBox menuDisplay;

    @FXML private VBox orderDetails;

    @FXML private Label totalLabel;


    double currentTotal = 0.0;

    @FXML void onOrderPressed(ActionEvent event) {

    }

    @FXML
    void initialize() {
        String host = getHost();
        if (host != null && !host.isEmpty()) {
            updateStatus("Status: Connecting to " + host + "...");
            ClientThread clientThread = new ClientThread(this, host);
            Thread t = new Thread(clientThread);
            t.start();
        } else {
            updateStatus("Status: No host provided.");
        }
    }

    public void updateStatus(String status) {
        statusLabel.setText(status);

    }
    public void updateMenuDisplay(List<MenuItem> menuItems) {
        menuDisplay.getChildren().clear(); // מנקה את התפריט הישן

        for (MenuItem item : menuItems) {

            //for the display of order details summary
            String ordarDetails = "";
            double total = 0.0;
            total += item.getPrice();
            ordarDetails += item.getItemDesc() + " - " + item.getPrice() + " ₪\n";

            HBox row = new HBox();
            row.setAlignment(Pos.CENTER_LEFT);
            row.setSpacing(10);

            //ill be honest, I copied this style from gemini because i am not a front-end guy
            row.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0; -fx-padding: 10; -fx-background-color: white;");



            VBox detailsBox = new VBox(5);

            //name label
            Label nameLabel = new Label(item.getItemDesc());
            nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            //type label
            Label typeLabel = new Label(item.getType());
            typeLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 11px;");

            //add to details box
            detailsBox.getChildren().addAll(nameLabel, typeLabel);


            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);


            VBox priceActionBox = new VBox(5);
            priceActionBox.setAlignment(Pos.CENTER_RIGHT);

            Label priceLabel = new Label(item.getPrice() + " ₪");
            priceLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

            //add button for the ordar
            Button addButton = new Button("add +");
            addButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand;");


            addButton.setOnAction(e -> addToCart(item));

            priceActionBox.getChildren().addAll(priceLabel, addButton);


            row.getChildren().addAll(detailsBox, spacer, priceActionBox);

            updateOrderDetails(ordarDetails);
            updateTotal(total);
            menuDisplay.getChildren().add(row);
        }
    }
    public String getHost()
    {
        TextInputDialog dialog = new TextInputDialog("localhost");
        dialog.setTitle("Server IP/Name");
        dialog.setHeaderText("Enter Server IP or Hostname");
        dialog.setContentText("IP/Hostname:");
        Optional<String> result = dialog.showAndWait();


        return result.orElse(null);

    }
    public void updateOrderDetails(String details) {
        orderDetails.getChildren().clear();
        Label detailsLabel = new Label(details);
        detailsLabel.setStyle("-fx-font-size: 13px;");
        orderDetails.getChildren().add(detailsLabel);
    }
    private void addToCart(MenuItem item) {



    }
    public void updateTotal(double total) {
        totalLabel.setText("Total: " + total + " ₪");
    }


}
