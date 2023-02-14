package org.cucumber.server.controllers;

import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.generics.Status;
import org.cucumber.common.dto.requests.RegisterRequest;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.common.utils.Routes;
import org.cucumber.server.models.so.SocketClient;
import org.cucumber.server.services.AuthService;
import org.cucumber.server.utils.classes.Controller;

public class RegisterController extends Controller {
    public static final String route = Routes.Server.REGISTER_REGISTER.getValue();

    public RegisterController() {
        super(route);
    }

    @Override
    public void handle(SocketClient socketClient, String requestId, Object args) {
        RegisterRequest arguments = args instanceof RegisterRequest ? ((RegisterRequest) args) : null;
        if (arguments != null) {
            try {
                AuthService.getInstance().register(arguments.getUsername(), arguments.getPassword());
                socketClient.sendToClient(new SocketMessage(requestId, route, new Status(true)));
            } catch (Exception ignore) {
                socketClient.sendToClient(new SocketMessage(requestId, route, new Status(false)));
            }
        } else {
            Logger.log(LoggerStatus.SEVERE, String.format("%s : %s", RegisterController.class.getName(), "args is null"));
        }
    }
}
