package org.cucumber.server.models.so;

import lombok.Getter;
import org.cucumber.common.dto.SocketMessage;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.server.core.Router;
import org.cucumber.server.core.SocketManager;
import org.cucumber.server.models.bo.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Getter
public class SocketClient implements Runnable {

    private static int idCounter = 0;

    private final int socketId;
    private final Socket socket;
    private boolean isActive;

    private User user = null;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public SocketClient(Socket socket) {

        this.socketId = ++idCounter;
        this.socket = socket;

        try {
            out = new ObjectOutputStream(this.socket.getOutputStream());
            in = new ObjectInputStream(this.socket.getInputStream());
            this.isActive = true;
        } catch (IOException e) {
            Logger.log(LoggerStatus.SEVERE, String.format("Can't reach client N°%d : %s", this.socketId, e.getMessage()));
//            Server.getInstance().removeClient(socketClient);
        }
    }

    @Override
    public void run() {

        while (isActive) {

            // wait for a message from the client
            SocketMessage message = waitForMessage();
            if (message == null) continue;

            // handle message
            Logger.log(LoggerStatus.INFO, "incoming message: " + message.getRoute());
            Router.getInstance().handle(this, message);
        }
    }

    private SocketMessage waitForMessage() {

        SocketMessage message = null;

        try {
            message = (SocketMessage) in.readObject();
            Logger.log(LoggerStatus.INFO, String.format("[%s:%s] reading : %s", this.socketId, message.getId(), message.getContent().getClass().getName()));
        } catch (IOException e) {
            Logger.log(LoggerStatus.SEVERE, String.format("[%s] couldn't be received : %s", this.socketId, e.getMessage()));
        } catch (ClassNotFoundException e) {
            Logger.log(LoggerStatus.SEVERE, String.format("[%s] couldn't be parsed : %s", this.socketId, e.getMessage()));
        }

        if (message == null) {
            SocketManager.getInstance().removeClient(this);
            isActive = false;
        }

        return message;
    }

    public void closeClient() {
        try {
            this.in.close();
            this.out.close();
            this.socket.close();
        } catch (IOException ioException) {
            Logger.log(LoggerStatus.SEVERE, "Can't close client (" + socketId + ")");
        }
    }

    public void sendToClient(SocketMessage message) {
        try {
            this.out.writeObject(message);
            this.out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
