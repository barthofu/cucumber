package org.cucumber.client;

import java.io.IOException;
import java.net.Socket;

import org.cucumber.client.services.Connection;

public class Client {

    public static int port = 3000;
    public static String address = "localhost";

    public static void main(String[] args) {

        try {

            openConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void openConnection() throws IOException {

        Socket socket = new Socket(address, port);

        Connection connection = new Connection(socket);

        Thread clientReceiveThread = new Thread(connection);
        clientReceiveThread.start();
    }

    public static void disconnectedServer() {
        // TODO: implement
    }

}
