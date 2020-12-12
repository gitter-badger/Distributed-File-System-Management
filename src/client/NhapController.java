package client;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NhapController implements Initializable {


    @FXML
    private Button myButton;

    @FXML
    private TextField myTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // When user click on myButton
    // this method will be called.
    // Khi người dùng nhấn vào Button myButton
    // phương thức này sẽ được gọi
    public void showDateTime(ActionEvent event) {
        System.out.println("Button Clicked!");

        Date now = new Date();

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

        // Dữ liệu Model
        String dateTimeString = df.format(now);

        // Hiển thị lên VIEW.
        myTextField.setText(dateTimeString);

    }

    public void goIntoScene2(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("nhap2.fxml"));
        Parent root = loader.load();
        // NhapController nhapcontrol = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Scene 2");
        stage.show();
    }
}
