package src.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClientDatagramSocket extends DatagramSocket {
    public ClientDatagramSocket() throws SocketException {
        super();
    }

    public void sendMessage(InetAddress receiverIP, int receiverPort, String message)
            throws IOException {
        super.send(new DatagramPacket(message.getBytes(), message.getBytes().length, receiverIP,
                receiverPort));
    }

    public String receiveMessage() throws IOException {
        byte receiverBuffer[] = new byte[8192];
        super.receive(new DatagramPacket(receiverBuffer, 8192));
        return new String(receiverBuffer);
    }
}
