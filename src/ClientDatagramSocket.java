import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClientDatagramSocket extends DatagramSocket {
    public ClientDatagramSocket() throws SocketException {
    }

    public ClientDatagramSocket(int port) throws SocketException {
        super(port);
    }

    public void sendMessage(InetAddress receiverIP, int receiverPort, String message)
            throws IOException {
        this.send(new DatagramPacket(message.getBytes(), message.getBytes().length, receiverIP,
                receiverPort));
    }

    public String receiveMessage() throws IOException {
        byte receiverBuffer[] = new byte[1024];
        this.receive(new DatagramPacket(receiverBuffer, 1024));
        return new String(receiverBuffer);
    }
}
