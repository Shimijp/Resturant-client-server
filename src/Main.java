import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("ClientApp.fxml"));
        javafx.scene.Parent root = loader.load();
        primaryStage.setTitle("Client Application");
        primaryStage.setScene(new javafx.scene.Scene(root, 800, 800));
        primaryStage.show();
    }
}