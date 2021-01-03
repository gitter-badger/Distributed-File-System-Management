package server;

import java.io.IOException;

public class Main {
    public static void main(String... args) {
        (new Thread() {

            @Override
            public void run() {
                try {
                    Server.getInstance();
                } catch (IOException exception) {
                }
            }
        }).start();
    }
}
