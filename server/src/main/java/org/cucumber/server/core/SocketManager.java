package org.cucumber.server.core;

import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.server.models.so.SocketClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
                // TODO : auth check
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
        return this.socketClients.stream().filter(socketClient -> socketClient.getSocketId() == id).findFirst().orElse(null);
    }

    public SocketClient getByUserId(int id) {
        return this.socketClients.stream().filter(socketClient -> socketClient.getUser().getId() == id).findFirst().orElse(null);
    }

    /**
     * Add a client to the list of connected clients
     * @param socketClient the client to add
     */
    public void addClient(SocketClient socketClient) {

        Logger.log(LoggerStatus.INFO, String.format("New client connected (%d)", socketClient.getSocketId()));
        this.socketClients.add(socketClient);
    }

    /**
     * Remove a client from the list of connected clients
     * @param socketClient the client to remove
     */
    public void removeClient(SocketClient socketClient) {

        Logger.log(LoggerStatus.INFO, String.format("Client disconnected (%d)", socketClient.getSocketId()));
        this.socketClients.remove(socketClient);
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
