package org.cucumber.server.controllers;

import org.cucumber.common.dto.SocketMessage;
import org.cucumber.common.dto.socketmsg_impl.HelloMsg;
import org.cucumber.common.dto.socketmsg_impl.RegisterMsg;
import org.cucumber.common.dto.socketmsg_impl.RegisterResponse;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.db.AuthService;
import org.cucumber.db.DAO;
import org.cucumber.server.models.so.SocketClient;
import org.cucumber.server.utils.classes.Controller;

public class RegisterController extends Controller {
    public static final String route = "register";

    public RegisterController() {
        super(route);
    }

    @Override
    public void handle(SocketClient socketClient, String requestId, Object args) {
        RegisterMsg arguments = args instanceof RegisterMsg ? ((RegisterMsg) args) : null;
        if (arguments != null) {
            try {
                AuthService.register(arguments.getUsername(), arguments.getPassword());
                socketClient.sendToClient(new SocketMessage(requestId, route, new RegisterResponse(true)));
            } catch (Exception ignore) {
                System.out.println(ignore.getMessage());
                socketClient.sendToClient(new SocketMessage(requestId, route, new RegisterResponse(false)));
            }
        } else {
            Logger.log(LoggerStatus.SEVERE, String.format("%s : %s", RegisterController.class.getName(), "args is null"));
        }
    }
}