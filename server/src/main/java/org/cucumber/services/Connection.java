package org.cucumber.services;

import org.cucumber.Server;
import org.cucumber.common.dto.SocketMessage;
import org.cucumber.models.so.SocketClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Runnable;

public class Connection implements Runnable {

    private SocketClient socketClient;
    private boolean isActive;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Connection(SocketClient socketClient) {

        this.socketClient = socketClient;
        this.isActive = true;

        try {
            out = new ObjectOutputStream(socketClient.getSocket().getOutputStream());
            in = new ObjectInputStream(socketClient.getSocket().getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

        while (isActive) {

            // wait for a message from the client
            SocketMessage message = waitForMessage();
            if (message == null) continue;

            // handle message
            Router.getInstance().handle(this.socketClient, message);

            System.out.println("\tincoming message: " + message.getRoute());
        }
    }

    private SocketMessage waitForMessage() {

        SocketMessage message = null;

        try {
            message = (SocketMessage) in.readObject();
            System.out.println("Reading incoming message (" + this.socketClient.getId() + ")");
        } catch (IOException e) {
            System.out.println("Can't receive incoming message (" + this.socketClient.getId() + ")");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't parse incoming message (" + this.socketClient.getId() + ")");
        } finally {

            if (message == null) {
                System.out.println("\tincoming message is null");
                Server.getInstance().removeClient(socketClient);
                isActive = false;
            }

            return message;
        }
    }
}
