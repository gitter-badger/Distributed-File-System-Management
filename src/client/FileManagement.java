package client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Cleanup;

public class FileManagement {
    private Client client;
    private DefaultListModel<String> defaultList = new DefaultListModel<String>();
    private File fileToUpload;
    private JList fileList;
    private static String username = GraphicalUserInterface.username,
            serverPort = GraphicalUserInterface.serverPort,
            serverAddress = GraphicalUserInterface.serverAddress;
    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView closeButton;
    @FXML
    private ImageView logOutButton;
    @FXML
    private TextField directory;



    public static String getUsername() {
        return username;
    }

    public static String getServerPort() {
        return serverPort;
    }

    public static String getServerAddress() {
        return serverAddress;
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

    public void updateFileList() throws IOException {
        this.client.sendMessage("201 ");
        this.defaultList.clear();
        String[] files = this.client.getLastMessage().split(";");
        for (var i = 0; i < files.length; ++i)
            this.defaultList.addElement(files[i]);
    }

    @FXML
    private void selectFile(MouseEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileToUpload = fileChooser.showOpenDialog(stage);
        String link = fileToUpload.getAbsolutePath();
        if (fileToUpload != null) {
            directory.setText(link);
        }
    }


    @FXML
    private void upload(MouseEvent event) {
        System.out.println("pressed button");
        try {
            byte fileInBytes[];
            fileInBytes =
                    client.fileToByteArray(fileToUpload.getAbsolutePath(), fileToUpload.length());
            var fileContents = new String(fileInBytes);
            client.sendMessage("200 " + fileToUpload.getName());
            client.sendMessage("202 " + fileContents);
            updateFileList();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void download(MouseEvent event) {
        try {
            var selectedFile = fileList.getSelectedValue().toString();
            client.sendMessage("203 " + selectedFile);
            var message = client.getLastMessage();
            if (!message.equals("File does not exist!")) {
                byte[] fileInBytes = client.getLastMessage().getBytes();
                Files.createDirectories(Paths.get("C:\\Network\\Downloads\\"));
                var newFile = new File("C:\\Network\\Downloads\\" + selectedFile);
                newFile.createNewFile();
                @Cleanup
                var fout = new FileOutputStream("C:\\Network\\Downloads\\" + selectedFile);
                fout.write(fileInBytes);
            } else
                JOptionPane.showMessageDialog(null, message);
            updateFileList();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void delete(MouseEvent event) {
        System.out.println("Hello");
    }

    @FXML
    private void logOut(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("GraphicalUserInterface.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            Stage stageFM = (Stage) logOutButton.getScene().getWindow();
            stageFM.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
