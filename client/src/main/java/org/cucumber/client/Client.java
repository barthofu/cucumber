package org.cucumber.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.cucumber.client.models.so.Connection;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;

public class Client {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;

    public void openConnection(String address, int port) throws IOException {

        socket = new Socket(address, port);
        out = new ObjectOutputStream(socket.getOutputStream());

        Thread clientReceiveThread = new Thread(new Connection(socket, out));

        clientReceiveThread.start();

        Logger.log(LoggerStatus.INFO, "Client connected to server");
    }

    public void closeConnection() throws IOException {
        if (this.in != null) {
            this.in.close();
        }
        out.close();
        socket.close();
    }

    public void sendToServer(SocketMessage socketMessage) throws IOException {
        Logger.log(LoggerStatus.INFO, String.format("sending [%s]SocketMessage(%s) to `%s`", socketMessage.getId(), socketMessage.getContent().getClass().getName(), socketMessage.getRoute()));
        out.writeObject(socketMessage);
        out.flush();
    }

    // ================================
    // Singleton
    // ================================

    private static Client instance;

    private Client() {
    }

    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

}
