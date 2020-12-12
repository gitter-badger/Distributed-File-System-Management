package client;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GraphicalUserInterface extends Application {

    protected Client client;
    private FileManagement controller;

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
    @FXML
    private ImageView closeButton;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("GraphicalUserInterface.fxml"))));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @FXML
    private void closeWindow(MouseEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeWindow(MouseEvent event) {
        ((Stage) pane.getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void logIn(ActionEvent event) throws IOException {
        try {
            this.client =
                    new Client(serverAddressTextField.getText(), serverPortTextField.getText());
            this.client.sendMessage("000 " + usernameTextField.getText());
            // controller = new FileManagement(client);
            // controller.updateFileList();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FileManagement.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


    public Client getClient() {
        return client;
    }

    public static void main(String args[]) {
        Application.launch(GraphicalUserInterface.class, args);
    }
}
