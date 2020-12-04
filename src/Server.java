import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import lombok.Cleanup;

public class Server {
    private static final int serverPort = 3;
    private static ServerDatagramSocket socket;
    private static String returnMessage, filename;
    private static File userDirectory, listOfFolders[];
    private static final File resourceDirectory = new File("C:\\Network\\Resources\\");

    public Server() {
    }

    public static void parse(String message) throws IOException {
        var code = message.substring(0, 3);
        message = message.substring(3).trim();
        switch (code) {
            case "000":
                Server.logIn(message);
                break;
            case "001":
                Server.logOut(message);
                break;
            case "200":
                Server.setFilename(message);
                break;
            case "201":
                Server.listFiles();
                break;
            case "202":
                Server.upload(message);
                break;
            case "203":
                Server.download(message);
                break;
        }
    }

    private static void logIn(String message) {
        Server.userDirectory = new File(Server.resourceDirectory, message);
        if (Server.userDirectory.mkdirs())
            Server.returnMessage = "100 Welcome, " + message + '!';
        else
            Server.returnMessage = "100 Welcome back, " + message + '!';
    }

    private static void logOut(String mesage) {
        Server.returnMessage = "101 Good bye, " + mesage + '!';
    }

    private static void setFilename(String message) {
        Server.filename = message;
    }

    private static void listFiles() throws IOException {
        var sb = new StringBuilder();
        Server.listOfFolders = Server.resourceDirectory.listFiles();
        for (var eachFolder : Server.listOfFolders) {
            File listOfFiles[] = eachFolder.listFiles();
            for (var i = 0; i < listOfFiles.length; ++i) {
                sb.append(listOfFiles[i].getName());
                if (i != listOfFiles.length - 1)
                    sb.append(";");
            }
        }
        var files = sb.toString();
        if (files.length() == 0)
            files = "No Files Found";
        Server.returnMessage = "300 " + files;
    }

    private static void upload(String message) throws IOException {
        byte[] fileInBytes = message.getBytes();
        var f = new File(Server.userDirectory, Server.filename);
        f.createNewFile();
        @Cleanup
        var fout = new FileOutputStream(f);
        fout.write(fileInBytes);
        Server.returnMessage = "301 Your upload is complete.";
    }

    private static byte[] fileToByteArray(String filePath, long fileSize) throws IOException {
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

    private static void download(String message) throws IOException {
        for (var eachFolder : Server.listOfFolders) {
            var f = new File(eachFolder, message);
            if (f.isFile()) {
                byte[] fileInBytes = fileToByteArray(f.getAbsolutePath(), f.length());
                Server.returnMessage = "302 " + new String(fileInBytes);
                return;
            }
        }
        Server.returnMessage = "303 File does not exist!";
    }

    public static void main(String args[]) throws SocketException, IOException {
        Server.socket = new ServerDatagramSocket(Server.serverPort);
        Server.socket.connect(new InetSocketAddress("www.google.com", 80));
        System.out.println("Server Address: " + Server.socket.getLocalAddress().getHostAddress());
        System.out.println("Server Port: " + Server.serverPort);
        Server.socket.disconnect();
        for (;;) {
            DatagramInfomation request = Server.socket.receiveDatagramInfomation();
            Server.parse(request.getMessage());
            Server.socket.sendMessage(request.getAddress(), request.getPort(),
                    Server.returnMessage);
        }
    }
}
