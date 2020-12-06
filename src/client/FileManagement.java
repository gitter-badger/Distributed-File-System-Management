package client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Cleanup;

public class FileManagement extends Application {
    private Client client;
    private DefaultListModel<String> defaultList = new DefaultListModel<String>();
    private File fileToUpload;
    private JList fileList;
    private static String username = GraphicalUserInterface.username,
            serverPort = GraphicalUserInterface.serverPort,
            serverAddress = GraphicalUserInterface.serverAddress;
    @FXML
    private AnchorPane pane;

    @Override
    public void start(Stage stage) throws IOException {
        this.client = new Client(FileManagement.serverAddress, FileManagement.serverPort);
        this.client.sendMessage("000 " + FileManagement.username);
        this.updateFileList();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("FileManagement.fxml"))));
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

    private void updateFileList() throws IOException {
        this.client.sendMessage("201 ");
        this.defaultList.clear();
        String[] files = this.client.getLastMessage().split(";");
        for (var i = 0; i < files.length; ++i)
            this.defaultList.addElement(files[i]);
    }

    @FXML
    private void selectFile(MouseEvent event) {
        System.out.println("Hello");
    }


    @FXML
    private void upload(ActionEvent event) {
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
    private void logOut(MouseEvent event) {
        try {
            client.sendMessage("001 " + FileManagement.username);
            Platform.exit();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String... args) {
        Application.launch(FileManagement.class, args);
    }
}
