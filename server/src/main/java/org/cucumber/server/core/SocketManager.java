package org.cucumber.server.core;

import org.cucumber.common.dto.UsersDTO;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.common.utils.Routes;
import org.cucumber.server.models.so.SocketClient;
import org.cucumber.server.services.ChatRoomService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The SocketManager service is responsible for managing the socket connections of the clients to the server.
 */
public class SocketManager implements Runnable {

    private final List<SocketClient> socketClients = new ArrayList<>();

    private ServerSocket serverSocket;

    public void init(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /**
     * Listen for new clients
     */
    @Override
    public void run() {

        while (true) {
            try {

                Socket sockNewClient = serverSocket.accept();
                SocketClient newSocketClient = new SocketClient(sockNewClient);
                addClient(newSocketClient);

                Thread threadNewClient = new Thread(newSocketClient);
                threadNewClient.start();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Broadcast a message to all clients
     * @param message the message to broadcast
     */
    public void broadcastMessage(SocketMessage message) {
        this.socketClients.forEach(connectedClient -> {
            connectedClient.sendToClient(message);
        });
    }

    /**
     * Broadcast a message to all clients except the one who sent it
     * @param message the message to broadcast
     * @param id the id of the client who sent the message
     */
    public void broadcastMessage(SocketMessage message, int id) {
        this.socketClients.forEach(connectedClient -> {
            if (connectedClient.getSocketId() != id) connectedClient.sendToClient(message);
        });
    }

    public SocketClient getById(int id) {
        return this.socketClients
                .stream()
                .filter(socketClient -> socketClient.getSocketId() == id)
                .findFirst()
                .orElse(null);
    }

    public SocketClient getByUserId(int id) {
        return this.socketClients
                .stream()
                .filter(socketClient -> socketClient.isLoggedIn() && socketClient.getUser().getId() == id)
                .findFirst()
                .orElse(null);
    }

    public int countLoggedIn() {
        return (int) this.socketClients.stream().filter(socketClient -> socketClient.getUser() != null).count();
    }

    /**
     * Add a client to the list of connected clients
     * @param socketClient the client to add
     */
    public void addClient(SocketClient socketClient) {

        Logger.log(LoggerStatus.INFO, String.format("New client connected (%d)", socketClient.getSocketId()));

        this.socketClients.add(socketClient);

        broadcastCountLoggedIn();
    }

    /**
     * Remove a client from the list of connected clients
     * @param socketClient the client to remove
     */
    public void removeClient(SocketClient socketClient) {

        Logger.log(LoggerStatus.INFO, String.format("Client disconnected (%d)", socketClient.getSocketId()));

        if (socketClient.isLoggedIn()) {
            ChatRoomService.getInstance().removeWaitingUser(socketClient.getUser().getId());
            ChatRoomService.getInstance().closeRoom(socketClient.getUser().getId());
        }

        this.socketClients.remove(socketClient);

        broadcastCountLoggedIn();
    }

    public void broadcastCountLoggedIn() {
        this.broadcastMessage(new SocketMessage(
                UUID.randomUUID().toString(),
                Routes.Client.USER_TOTAL.getValue(),
                new UsersDTO(this.countLoggedIn())
        ));
    }

    // ================================
    // Singleton
    // ================================

    private static SocketManager instance;

    private SocketManager() {}

    public static SocketManager getInstance() {
        if (instance == null) {
            instance = new SocketManager();
        }
        return instance;
    }

}
