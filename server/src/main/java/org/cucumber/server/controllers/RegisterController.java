package org.cucumber.server.controllers;

import org.cucumber.common.dto.UserDTO;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.generics.Status;
import org.cucumber.common.dto.requests.RegisterRequest;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.common.utils.Routes;
import org.cucumber.server.models.so.SocketClient;
import org.cucumber.server.services.AuthService;
import org.cucumber.server.utils.classes.Controller;
import org.cucumber.server.utils.errors.UsernameAlreadyTakenException;
import org.cucumber.server.utils.mappers.UserMapper;
import org.mapstruct.factory.Mappers;

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

                UserDTO userDTO = Mappers.getMapper(UserMapper.class).registerRequestToUserDTO(arguments);

                AuthService.getInstance().register(userDTO, arguments.getPassword());
                socketClient.sendToClient(new SocketMessage(requestId, new Status(true)));

            } catch (UsernameAlreadyTakenException e) {
                socketClient.sendToClient(new SocketMessage(requestId, new Status(false, e.getMessage())));
            } catch (Exception ignore) {
                socketClient.sendToClient(new SocketMessage(requestId, new Status(false)));
            }
        } else {
            Logger.log(LoggerStatus.SEVERE, String.format("%s : %s", RegisterController.class.getName(), "args is null"));
        }
    }
}
