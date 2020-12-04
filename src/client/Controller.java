package client;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

public class Controller {

    public AnchorPane pane;

    public void closeWindow(MouseEvent event) {
        System.exit(0);
    }

    public void minimizeWindow(MouseEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setIconified(true);
    }
}
