package client;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GraphicalUserInterface extends Application {
    public static String username, serverPort, serverAddress;
    @FXML
    private AnchorPane pane;
    @FXML
    private Button logInButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField serverPortTextField;
    @FXML
    private TextField serverAddressTextField;


    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("GraphicalUserInterface.fxml"))));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @FXML
    private void closeWindow(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void minimizeWindow(MouseEvent event) {
        ((Stage) pane.getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void logIn(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    GraphicalUserInterface.username = usernameTextField.getText();
                    GraphicalUserInterface.serverPort = serverPortTextField.getText();
                    GraphicalUserInterface.serverAddress = serverAddressTextField.getText();
                    new FileManagement().start(new Stage());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public static void main(String args[]) {
        Application.launch(GraphicalUserInterface.class, args);
    }
}
