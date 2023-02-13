package org.cucumber.server.controllers;

import org.cucumber.common.dto.SocketMessage;
import org.cucumber.common.dto.contents.LoginMsg;
import org.cucumber.common.dto.contents.LoginResponse;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.server.models.so.SocketClient;
import org.cucumber.server.services.AuthService;
import org.cucumber.server.utils.classes.Controller;

public class LoginController extends Controller {
    public static final String route = "login";

    public LoginController() {
        super(route);
    }

    @Override
    public void handle(SocketClient socketClient, String requestId, Object args) {
        LoginMsg arguments = args instanceof LoginMsg ? ((LoginMsg) args) : null;
        if (arguments != null) {
            try {
                socketClient.sendToClient(new SocketMessage(requestId, route, new LoginResponse(
                        AuthService.getInstance().checkAuth(arguments.getUsername(), arguments.getPassword())
                )));
            } catch (Exception ignore) {
                socketClient.sendToClient(new SocketMessage(requestId, route, new LoginResponse(false)));
            }
        } else {
            Logger.log(LoggerStatus.SEVERE, String.format("%s : %s", LoginController.class.getName(), "args is null"));
        }
    }
}
