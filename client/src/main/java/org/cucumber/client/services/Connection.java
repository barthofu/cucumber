package org.cucumber.client.services;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.UUID;

import org.cucumber.common.dto.SocketMessage;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;

public class Connection implements Runnable {

    private final Socket socket;
    private final String id;

    private final ObjectInputStream in;

    public Connection (Socket socket) {

        this.socket = socket;
        this.id = UUID.randomUUID().toString();

        try {
            this.in = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        // receive

        while (true) {

            // wait for a message from the server
            SocketMessage message = waitForMessage();
            if (message == null) continue;

            // handle message
            MessageManager.getInstance().receive(message.getId(), message.getContent());

            Logger.log(LoggerStatus.INFO, "\tincoming message: " + message.getRoute());
        }

        // client.disconnectedServer();

    }

    private SocketMessage waitForMessage() {

        SocketMessage message = null;

        try {
            message = (SocketMessage) in.readObject();
            System.out.println("Reading incoming message (" + this.id + ")");
        } catch (IOException e) {
            System.out.println("Can't receive incoming message (" + this.id + ")");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't parse incoming message (" + this.id + ")");
        } finally {

            if (message == null) {
                // TODO: server disconnected
            }

            return message;
        }
    }
}
