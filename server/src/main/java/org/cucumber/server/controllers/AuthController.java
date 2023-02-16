package org.cucumber.server.controllers;

import org.cucumber.common.dto.UserDTO;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.generics.Status;
import org.cucumber.common.dto.requests.LoginRequest;
import org.cucumber.common.dto.requests.RegisterRequest;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.common.utils.Routes;
import org.cucumber.common.utils.Timeout;
import org.cucumber.server.core.SocketManager;
import org.cucumber.server.models.bo.User;
import org.cucumber.server.models.so.SocketClient;
import org.cucumber.server.services.AuthService;
import org.cucumber.server.utils.classes.Controller;
import org.cucumber.server.utils.errors.InvalidCredentialsException;
import org.cucumber.server.utils.errors.UserAlreadyLoggedInException;
import org.cucumber.server.utils.errors.UsernameAlreadyTakenException;
import org.cucumber.server.utils.mappers.UserMapper;
import org.mapstruct.factory.Mappers;

public class AuthController {

    public static class Login extends Controller {

        public static final String route = Routes.Server.AUTH_LOGIN.getValue();
        public Login() {
            super(route);
        }

        @Override
        public void handle(SocketClient socketClient, String requestId, Object args) {
            LoginRequest arguments = args instanceof LoginRequest ? ((LoginRequest) args) : null;

            if (arguments != null) {

                try {

                    User user = AuthService.getInstance().login(arguments.getUsername(), arguments.getPassword());

                    if (user != null) {

                        socketClient.setUser(user);
                        socketClient.sendToClient(new SocketMessage(
                                requestId,
                                Mappers.getMapper(UserMapper.class).userToUserDTO(user)
                        ));

                        Timeout.setTimeout(() -> {
                            SocketManager.getInstance().broadcastCountLoggedIn();
                        }, 500);

                    } else {
                        throw new InvalidCredentialsException();
                    }

                } catch (UserAlreadyLoggedInException | InvalidCredentialsException e) {
                    socketClient.sendToClient(new SocketMessage(requestId, new Status(false, e.getMessage())));
                }

            } else {
                Logger.log(LoggerStatus.SEVERE, String.format("%s : %s", Login.class.getName(), "args is null"));
            }
        }
    }


    public static class Register extends Controller {

        public static final String route = Routes.Server.AUTH_REGISTER.getValue();
        public Register() {
            super(route);
        }

        @Override
        public void handle(SocketClient socketClient, String requestId, Object args) {
            RegisterRequest arguments = args instanceof RegisterRequest ? ((RegisterRequest) args) : null;

            if (arguments != null) {
                try {

                    UserDTO userDTO = Mappers.getMapper(UserMapper.class).registerRequestToUserDTO(arguments);

                    AuthService.getInstance().register(userDTO, arguments.getPassword());
                    socketClient.sendToClient(new SocketMessage(requestId, new Status(true)));

                } catch (UsernameAlreadyTakenException e) {
                    socketClient.sendToClient(new SocketMessage(requestId, new Status(false, e.getMessage())));
                } catch (Exception ignore) {
                    socketClient.sendToClient(new SocketMessage(requestId, new Status(false)));
                }
            } else {
                Logger.log(LoggerStatus.SEVERE, String.format("%s : %s", Register.class.getName(), "args is null"));
            }
        }
    }


    public static class Logout extends Controller {

        public static final String route = Routes.Server.AUTH_LOGOUT.getValue();
        public Logout() {
            super(route);
        }

        @Override
        public void handle(SocketClient socketClient, String requestId, Object args) {

            try {

                AuthService.getInstance().logout(socketClient.getUser().getId());

                socketClient.sendToClient(new SocketMessage(requestId, new Status(true)));

            } catch (Exception e) {
                Logger.log(LoggerStatus.SEVERE, String.format("%s : %s", Logout.class.getName(), e.getMessage()));
                socketClient.sendToClient(new SocketMessage(requestId, new Status(false)));
            }
        }
    }


}
