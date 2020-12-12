package client;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import lombok.Cleanup;

public class Client {
    private InetAddress serverAddress;
    private int serverPort;
    private ClientDatagramSocket socket;
    private String lastMessage = "";

    @FXML
    private Button logInButton;

    public Client(String serverAddress, String serverPort)
            throws NumberFormatException, SocketException, UnknownHostException {
        this.socket = new ClientDatagramSocket();
        this.serverAddress = InetAddress.getByName(serverAddress);
        this.serverPort = Integer.parseInt(serverPort);
    }

    public String getLastMessage() {
        return this.lastMessage;
    }

    private void closeSocket() {
        this.socket.close();
    }

    private String getMessage(String message) throws IOException {
        this.socket.sendMessage(this.serverAddress, this.serverPort, message);
        return this.socket.receiveMessage();
    }


    Alert a = new Alert(AlertType.NONE);
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            a.setAlertType(AlertType.INFORMATION);
            a.setContentText("Hello");
            a.show();
        }
    };

    private void parse(String message) {
        var code = message.substring(0, 3);
        message = message.substring(3).trim();
        switch (code) {
            case "100":
                // a.setContentText("Hello");
                // logInButton.setOnAction(event);
                JOptionPane.showMessageDialog(null, message);
                break;
            case "101":
                JOptionPane.showMessageDialog(null, message);
                this.closeSocket();
                break;
            case "300":
                this.lastMessage = message;
                break;
            case "301":
                JOptionPane.showMessageDialog(null, message);
                break;
            case "302":
                JOptionPane.showMessageDialog(null, "Your download is complete.");
                this.lastMessage = message;
                break;
            case "303":
                this.lastMessage = message;
                break;
        }
    }

    public void sendMessage(String message) throws IOException {
        this.parse(getMessage(message));
    }

    public byte[] fileToByteArray(String filePath, long fileSize) throws IOException {
        byte byteArray[] = new byte[(int) fileSize];
        @Cleanup
        var fin = new FileInputStream(filePath);
        var i = 0;
        while (fin.available() != 0) {
            byteArray[i] = (byte) fin.read();
            ++i;
        }
        return byteArray;
    }
}
