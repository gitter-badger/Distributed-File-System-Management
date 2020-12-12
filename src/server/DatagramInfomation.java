package server;

import java.net.InetAddress;
import lombok.Getter;

@Getter
public class DatagramInfomation {
    private String message;
    private InetAddress address;
    private int port;

    public DatagramInfomation(String message, InetAddress address, int port) {
        this.message = message;
        this.address = address;
        this.port = port;
    }
}
