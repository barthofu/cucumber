package org.cucumber;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import org.cucumber.common.dto.SocketMessage;
import org.cucumber.models.so.SocketClient;
import org.cucumber.services.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Server {

    private static Server instance;

    public static Server getInstance() {
        if (instance == null) {
            try {
                instance = new Server();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }
        return instance;
    }

    private final List<SocketClient> socketClients = new ArrayList<>();
    private ServerSocket socket;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private Server() throws Exception {

        int port;
        try {
            port = Integer.parseInt(Dotenv.load().get("PORT"));
        } catch (Exception ignore) {
            port = 3000;
        }

        this.socket = new ServerSocket(port);
    }

    public void listen() {

        while (true) {
            try {
                Socket sockNewClient = socket.accept();
                // TODO : auth check
                SocketClient newSocketClient = new SocketClient(sockNewClient);
                System.out.println("client connected");
                this.addClient(newSocketClient);

                Thread threadNewClient = new Thread(new Connection(newSocketClient));
                threadNewClient.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // messages

    public void broadcastMessage(SocketMessage message, int id) {
        this.socketClients.forEach(connectedClient -> {
            if (connectedClient.getId() != id) connectedClient.send(message);
        });
    }

    // manage clients

    public void addClient(SocketClient socketClient) {
        //a new client has joined
        this.socketClients.add(socketClient);
    }

    public void removeClient(SocketClient socketClient) {
        this.socketClients.remove(socketClient);
    }
}
