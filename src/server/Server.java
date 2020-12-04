package server;

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
    private static String returnMessage, username, filename;

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
        var userDir = new File("C:\\Network\\" + message);
        if (userDir.mkdirs())
            Server.returnMessage = "100 Welcome, " + message + '!';
        else
            Server.returnMessage = "100 Welcome back, " + message + '!';
        Server.username = message;
    }

    private static void logOut(String mesage) {
        Server.returnMessage = "101 Good bye, " + mesage + '!';
        Server.username = null;
    }

    private static void setFilename(String message) {
        Server.filename = message;
    }

    private static void listFiles() throws IOException {
        var userDirectory = new File("C:\\Network\\" + Server.username);
        File[] listOfFiles = userDirectory.listFiles();
        var sb = new StringBuilder();
        for (var i = 0; i < listOfFiles.length; ++i) {
            sb.append(listOfFiles[i].getName());
            if (i != listOfFiles.length - 1)
                sb.append(";");
        }
        var files = sb.toString();
        if (files.length() == 0)
            files = "No Files Found";
        Server.returnMessage = "300 " + files;
    }

    private static void upload(String message) throws IOException {
        byte[] fileInBytes = message.getBytes();
        var f = new File("C:\\Network\\" + Server.username + "\\" + Server.filename);
        f.createNewFile();
        @Cleanup
        var fout = new FileOutputStream("C:\\Network\\" + Server.username + "\\" + Server.filename);
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
        var f = new File("C:\\Network\\" + Server.username + "\\" + message);
        if (f.isFile()) {
            byte[] fileInBytes = fileToByteArray(f.getAbsolutePath(), f.length());
            Server.returnMessage = "302 " + new String(fileInBytes);
        } else
            Server.returnMessage = "303 File does not exist!";
    }

    public static void main(String args[]) throws SocketException, IOException {
        Server.socket = new ServerDatagramSocket(Server.serverPort);
        Server.socket.connect(new InetSocketAddress("www.google.com", 80));
        System.out.println("Server Address: " + Server.socket.getLocalAddress().getHostAddress());
        System.out.println("Server Port: " + Server.serverPort);
        Server.socket.close();
        Server.socket = new ServerDatagramSocket(Server.serverPort);
        for (;;) {
            DatagramInfomation request = Server.socket.receiveDatagramInfomation();
            Server.parse(request.getMessage());
            Server.socket.sendMessage(request.getAddress(), request.getPort(),
                    Server.returnMessage);
        }
    }
}
