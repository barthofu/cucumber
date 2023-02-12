package org.cucumber;

import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;

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

