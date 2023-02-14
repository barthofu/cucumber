package org.cucumber.server.controllers;

import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.MessageDTO;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.server.utils.classes.Controller;
import org.cucumber.server.models.so.SocketClient;

public class HelloWorld extends Controller {
    public static final String route = "helloWorld";

    public HelloWorld() {
        super(route);
    }

    @Override
    public void handle(SocketClient socketClient, String requestId, Object args) {
        MessageDTO arguments = args instanceof MessageDTO ? ((MessageDTO) args) : null;
        Logger.log(LoggerStatus.INFO, arguments == null ? "null" : arguments.toString());
        if (arguments != null) {
            socketClient.sendToClient(new SocketMessage(requestId, route, new MessageDTO("Hello " + arguments.getText())));
        } else {
            socketClient.sendToClient(new SocketMessage(requestId, route, new MessageDTO("Hello world")));
        }
    }
}
