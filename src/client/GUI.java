package client;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUI extends Application {
    protected static Client client;
    protected static String username, serverPort, serverAddress;

    @FXML
    protected AnchorPane pane;

    @FXML
    protected ImageView closeButton;

    @FXML
    protected Button logInButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField serverPortTextField;

    @FXML
    private TextField serverAddressTextField;

    public GUI() {
        super();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("GUI.fxml"))));
        stage.show();
    }

    @FXML
    protected void closeWindow(MouseEvent event) {
        ((Stage) closeButton.getScene().getWindow()).close();
    }

    @FXML
    protected void minimizeWindow(MouseEvent event) {
        ((Stage) pane.getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void logIn(ActionEvent event) throws IOException {
        try {
            GUI.username = usernameTextField.getText();
            GUI.serverPort = serverPortTextField.getText();
            GUI.serverAddress = serverAddressTextField.getText();
            GUI.client = new Client(GUI.serverAddress, GUI.serverPort);
            GUI.client.sendMessage("000 " + GUI.username);
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene(new FXMLLoader(getClass().getResource("Helper.fxml")).load()));
            stage.show();
            ((Stage) closeButton.getScene().getWindow()).close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Application.launch(GUI.class, args);
    }
}
