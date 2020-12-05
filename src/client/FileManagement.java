package client;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import lombok.Cleanup;

@SuppressWarnings("rawtypes")
public class FileManagement {
    private static final long serialVersionUID = 1L;
    private File fileToUpload;
    private DefaultListModel<String> defaultList = new DefaultListModel<String>();
    private TextField directory;
    private ListView fileList;
    private Client client;
    public AnchorPane pane;

    @SuppressWarnings("unchecked")
    public FileManagement(String username, String serverHost, String serverPort)
            throws NumberFormatException, IOException {
        this.client = new Client(serverHost, serverPort);
        this.client.sendMessage("000 " + username);
        this.updateFileList();

        ImageView selectFileButton = new ImageView();
        selectFileButton.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open");
            Stage stage = (Stage) pane.getScene().getWindow();
            File fileToUpload = fileChooser.showOpenDialog(stage);
            if (fileToUpload != null) {
                directory.setText(fileToUpload.getAbsolutePath());
            } else {
                directory.setText("Choose File");
            }

        });

        ImageView uploadButton = new ImageView();
        uploadButton.setOnMouseClicked(event -> {
            try {
                byte fileInBytes[];
                fileInBytes = client.fileToByteArray(fileToUpload.getAbsolutePath(),
                        fileToUpload.length());
                var fileContents = new String(fileInBytes);
                client.sendMessage("200 " + fileToUpload.getName());
                client.sendMessage("202 " + fileContents);
                updateFileList();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        Alert alert = new Alert(AlertType.INFORMATION);
        ImageView downloadButton = new ImageView();
        downloadButton.setOnMouseClicked(event -> {
            try {
                var selectedFile = fileList.getSelectionModel().getSelectedItem().toString();
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
                    alert.setContentText(message);
                alert.showAndWait();
                updateFileList();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        ImageView logOutButton = new ImageView();
        logOutButton.setOnMouseClicked(event -> {
            try {
                client.sendMessage("001 " + username);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

    }

    private void updateFileList() throws IOException {
        this.client.sendMessage("201 ");
        this.defaultList.clear();
        String[] files = this.client.getLastMessage().split(";");
        for (var i = 0; i < files.length; ++i)
            this.defaultList.addElement(files[i]);
    }

    public static void main(String... args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FileManagement(args[0], args[1], args[2]);
                } catch (NumberFormatException | IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}
