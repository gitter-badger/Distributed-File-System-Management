package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Nhap extends Application {


    @Override
    public void start(Stage primaryStage) {
        try {
            // Đọc file fxml và vẽ giao diện.
            Parent root = FXMLLoader.load(getClass().getResource("nhap.fxml"));

            primaryStage.setTitle("My Application");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
