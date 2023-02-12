package org.cucumber.server;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.server.core.SocketManager;

import java.net.ServerSocket;

@Getter
public class Server {

    private int port;

    public Server() {

        try {
            port = Integer.parseInt(Dotenv.load().get("PORT"));
        } catch (Exception ignore) {
            Logger.log(LoggerStatus.WARNING, "Can't access .env value \"PORT\" : setting port to default value 3000");
            port = 3000;
        }

    }

    public void start() throws Exception {

        Logger.log(LoggerStatus.INFO, "Server started");

        // instantiate the server socket
        ServerSocket serverSocket = new ServerSocket(port);

        // instantiate the socket manager
        SocketManager socketManager = SocketManager.getInstance();
        socketManager.init(serverSocket);

        // start the socket manager in a new thread
        Thread threadConnectionManager = new Thread(socketManager);
        threadConnectionManager.start();
    }


}
