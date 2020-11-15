import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerDatagramSocket extends DatagramSocket {
    public ServerDatagramSocket(int port) throws SocketException {
        super(port);
    }

    public void sendMessage(InetAddress receiverIP, int receiverPort, String message)
            throws IOException {
        byte buffer[] = message.getBytes();
        DatagramPacket datagram =
                new DatagramPacket(buffer, buffer.length, receiverIP, receiverPort);
        this.send(datagram);
    }

    public String receiveMessage() throws IOException {
        byte receiverBuffer[] = new byte[1024];
        DatagramPacket datagram = new DatagramPacket(receiverBuffer, 1024);
        this.receive(datagram);
        String message = new String(receiverBuffer);
        return message;
    }

    public DatagramInfomation receiveDatagramInfomation() throws IOException {
        byte receiverBuffer[] = new byte[1024];
        DatagramPacket datagram = new DatagramPacket(receiverBuffer, 1024);
        this.receive(datagram);
        DatagramInfomation infomation = new DatagramInfomation(new String(receiverBuffer),
                datagram.getAddress(), datagram.getPort());
        return infomation;
    }
}
