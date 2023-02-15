package org.cucumber.client.models.so;

import org.cucumber.client.services.MessageService;
import org.cucumber.client.services.SocketMessageService;
import org.cucumber.client.services.UserService;
import org.cucumber.common.dto.MessageDTO;
import org.cucumber.common.dto.UsersDTO;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.common.utils.Routes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * The Connection class is responsible for managing the connection to the server.
 */
public class Connection implements Runnable {

    private final Socket socket;
    private boolean isActive = true;

    private ObjectInputStream in;
    private final ObjectOutputStream out;

    public Connection (Socket socket, ObjectOutputStream out) {
        this.socket = socket;
        this.out = out;
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
            Logger.log(LoggerStatus.INFO, "incoming message: " + message.getRoute());

            if (message.getRoute() != null) {
                // handle the message
                handleRoute(message);
            } else {
                // send the message to the message service
                SocketMessageService.getInstance().receive(message.getId(), message.getContent());
            }
        }

        // TODO: handle connection lost
        // client.disconnectedServer();
    }

    private void handleRoute(SocketMessage message) {

        if (matchRoute(message, Routes.Client.USER_TOTAL)) {
            UserService.getInstance().setTotalUsers(((UsersDTO) message.getContent()).getTotalUsers());

        } else if (matchRoute(message, Routes.Client.MESSAGE_RECEIVE)) {
            MessageService.getInstance().addMessage((MessageDTO) message.getContent());
        }
    }

    private boolean matchRoute(SocketMessage message, Routes.Client route) {
        return message.getRoute().equals(route.getValue());
    }

    private SocketMessage waitForMessage() {

        SocketMessage message = null;

        try {
            message = (SocketMessage) in.readObject();
            Logger.log(LoggerStatus.INFO, String.format("[%s] reading : %s", message.getId(), message.getContent().getClass().getName()));
        } catch (IOException e) {
            Logger.log(LoggerStatus.SEVERE, String.format("couldn't be received > %s : %s", e.getClass().getName(), e.getMessage()));
        } catch (ClassNotFoundException e) {
            Logger.log(LoggerStatus.SEVERE, String.format("couldn't be parsed > %s : %s", e.getClass().getName(), e.getMessage()));
        }

        if (message == null) {
            // TODO: server disconnected
            isActive = false;
        }

        return message;

    }

    public void sendToServer(SocketMessage socketMessage){
        try {
            out.writeObject(socketMessage);
            out.flush();
        }catch (Exception ignore){
            Logger.log(LoggerStatus.SEVERE, "couldn't send msg to server");
        }
    }
}
