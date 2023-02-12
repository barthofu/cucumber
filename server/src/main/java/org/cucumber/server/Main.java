package org.cucumber.server;

import org.cucumber.db.AuthService;

public class Main {

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

