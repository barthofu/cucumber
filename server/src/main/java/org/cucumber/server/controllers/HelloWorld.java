package org.cucumber.server.controllers;

import lombok.AllArgsConstructor;
import org.cucumber.common.dto.SocketMessage;
import org.cucumber.common.dto.SocketMessageContent;
import org.cucumber.server.utils.classes.Controller;
import org.cucumber.server.models.so.SocketClient;

public class HelloWorld extends Controller {

    public static final String route = "helloWorld";

    @AllArgsConstructor
    static class Args extends SocketMessageContent {
        private String text;
    }

    public HelloWorld() {
        super(route);
    }

    @Override
    public void handle(SocketClient socketClient, String requestId, Object args) {
        Args arguments = args instanceof Args ? ((Args) args) : null;

        if (arguments != null) {
            socketClient.sendToClient(new SocketMessage(requestId, new Args("Hello " + arguments.text)));
        } else {
            socketClient.sendToClient(new SocketMessage(requestId, new Args("Hello world")));
        }
    }
}