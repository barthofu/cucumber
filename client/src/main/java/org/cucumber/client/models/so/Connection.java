package org.cucumber.client.models.so;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import org.cucumber.client.services.MessageManager;
import org.cucumber.common.dto.SocketMessage;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;

public class Connection implements Runnable {

    private final Socket socket;
    private final String id;
    private boolean isActive = true;

    private ObjectInputStream in;
    private final ObjectOutputStream out;

    public Connection (Socket socket, ObjectOutputStream out) {
        this.socket = socket;
        this.out = out;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Listen for incoming messages from the server
     */
    @Override
    public void run() {

        try {
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // receive
        while (isActive) {

            // wait for a message from the server
            SocketMessage message = waitForMessage();

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
        }

        if (message == null) {
            // TODO: server disconnected
            isActive = false;
        }

        return message;

    }

    public void send(SocketMessage socketMessage){
        try {
            out.writeObject(socketMessage);
            out.flush();
        }catch (Exception ignore){
            Logger.log(LoggerStatus.SEVERE, "couldn't send msg to server");
        }
    }
}
