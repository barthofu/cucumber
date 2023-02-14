package org.cucumber.server.controllers;

import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.generics.Status;
import org.cucumber.common.dto.requests.LoginRequest;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.common.utils.Routes;
import org.cucumber.server.core.SocketManager;
import org.cucumber.server.models.bo.User;
import org.cucumber.server.models.so.SocketClient;
import org.cucumber.server.services.AuthService;
import org.cucumber.server.utils.classes.Controller;
import org.cucumber.server.utils.classes.Timeout;
import org.cucumber.server.utils.mappers.UserMapper;
import org.mapstruct.factory.Mappers;

public class LoginController extends Controller {
    public static final String route = Routes.Server.LOGIN_LOGIN.getValue();

    public LoginController() {
        super(route);
    }

    @Override
    public void handle(SocketClient socketClient, String requestId, Object args) {
        LoginRequest arguments = args instanceof LoginRequest ? ((LoginRequest) args) : null;
        if (arguments != null) {
            try {

                User user = AuthService.getInstance().checkAuth(arguments.getUsername(), arguments.getPassword());

                socketClient.setUser(user);

                socketClient.sendToClient(new SocketMessage(
                        requestId,
                        route,
                        Mappers.getMapper(UserMapper.class).userToUserDTO(user)
                ));

                Timeout.setTimeout(() -> {
                    SocketManager.getInstance().broadcastCountLoggedIn();
                }, 1000);

            } catch (Exception ignore) {
                socketClient.sendToClient(new SocketMessage(requestId, route, new Status(false)));
            }
        } else {
            Logger.log(LoggerStatus.SEVERE, String.format("%s : %s", LoginController.class.getName(), "args is null"));
        }
    }
}
