package org.cucumber.server;

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

