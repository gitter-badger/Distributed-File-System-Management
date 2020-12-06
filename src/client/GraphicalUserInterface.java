package client;

import java.io.IOException;
import javafx.application.Application;
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
    @FXML
    private AnchorPane pane;
    @FXML
    private Button logInButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField serverAddressTextField;
    @FXML
    private TextField serverPortTextField;

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
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void logIn(ActionEvent event) {
        FileManagement.main(usernameTextField.getText(), serverAddressTextField.getText(),
                serverPortTextField.getText());
    }

    public static void main(String args[]) {
        Application.launch(args);
    }
}
