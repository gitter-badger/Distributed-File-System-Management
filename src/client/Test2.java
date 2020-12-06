package client;

import javafx.fxml.FXMLLoader;
import java.io.File;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Test2 extends Application {
    // ImageView imageView;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("nhap.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        File file = new File("img/Power.png");
        Image image = new Image(file.toURI().toString());
        ImageView vuong = new ImageView(image);
        // imageView.setFitWidth(50);
        // imageView.setPreserveRatio(true);
        // imageView.setX(100);
        // imageView.setY(100);

        Button button = new Button("add");
        ((Pane) root).getChildren().addAll(button);
        button.setOnAction(event -> {
            System.exit(0);
        });


        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
