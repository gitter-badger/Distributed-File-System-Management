package client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Cleanup;

public class Helper extends GUI implements Initializable {
    private ObservableList<String> observableList = FXCollections.observableArrayList();
    private File fileToUpload;

    @FXML
    private TextField directory;

    @FXML
    private ListView<String> listView;

    public void updateFileList() throws IOException {
        GUI.client.sendMessage("201 ");
        this.observableList.clear();
        String[] files = GUI.client.getLastMessage().split(";");
        for (var i = 0; i < files.length; ++i)
            this.observableList.add(files[i]);
    }

    @FXML
    private void selectFile(MouseEvent event) {
        fileToUpload = new FileChooser().showOpenDialog(closeButton.getScene().getWindow());
        if (fileToUpload != null)
            directory.setText(fileToUpload.getAbsolutePath());
    }

    @FXML
    private void upload(MouseEvent event) {
        try {
            byte fileInBytes[];
            fileInBytes =
                    client.fileToByteArray(fileToUpload.getAbsolutePath(), fileToUpload.length());
            var fileContents = new String(fileInBytes);
            client.sendMessage("200 " + fileToUpload.getName());
            client.sendMessage("202 " + fileContents);
            updateFileList();
            directory.setText("");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void download(MouseEvent event) {
        try {
            var selectedFile = (String) listView.getSelectionModel().getSelectedItem();
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
        try {
            var selectedFile = (String) listView.getSelectionModel().getSelectedItem();
            client.sendMessage("204 " + selectedFile);
            var message = client.getLastMessage();
            if (message.equals("File does not exist!"))
                JOptionPane.showMessageDialog(null, message);
            updateFileList();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void logOut(MouseEvent event) throws IOException {
        try {
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene(new FXMLLoader(getClass().getResource("GUI.fxml")).load()));
            stage.show();
            ((Stage) closeButton.getScene().getWindow()).close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            updateFileList();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setItems(observableList);
    }
}
