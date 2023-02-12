package org.cucumber.client;

import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;

import java.io.IOException;

public class Main {

    public static int port = 3000;
    public static String address = "localhost";

    public static void main(String[] args) {

        try {
            Client client = Client.getInstance();
            client.openConnection(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
