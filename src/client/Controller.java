package client;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.AnchorPane;

public class Controller {

    public AnchorPane pane;
    public TextField usernameTextField;
    public TextField serverAddressTextField;
    public TextField serverPortTextField;

    public void closeWindow(MouseEvent event) {
        System.exit(0);
    }

    public void minimizeWindow(MouseEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setIconified(true);
    }

    public void openFile(MouseEvent event) throws IOException {
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Open");
        Stage stage = (Stage) pane.getScene().getWindow();
        filechooser.showOpenDialog(stage);

        if (filechooser != null) {
            filechooser.setInitialFileName(filechooser.getInitialFileName());
            // filechooser.getInitialDirectory();
            filechooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
            // Adding action on the menu item
            File file = filechooser.showSaveDialog(stage);
            filechooser.setInitialDirectory(file.getParentFile());

        }
    }

    // public void logIn(ActionEvent event) {
    // FileManagement.main(usernameTextField.getText(), serverAddressTextField.getText(),
    // serverPortTextField.getText());
    // }
}
