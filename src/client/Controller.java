package client;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

public class Controller {

    public AnchorPane pane;
    public TextField usernameTextField;
    public TextField serverAddressTextField;
    public TextField serverPortTextField;
    public Button logInButton;

    public void closeWindow(MouseEvent event) {
        System.exit(0);
    }

    public void minimizeWindow(MouseEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setIconified(true);
    }

    public void logIn(ActionEvent event) {
        FileManagement.main(usernameTextField.getText(), serverAddressTextField.getText(),
                serverPortTextField.getText());
    }
}
