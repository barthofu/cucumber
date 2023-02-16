package org.cucumber.server.controllers;

import org.cucumber.common.dto.UserDTO;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.generics.Status;
import org.cucumber.common.dto.generics.UserTarget;
import org.cucumber.common.utils.Routes;
import org.cucumber.server.models.bo.User;
import org.cucumber.server.models.so.SocketClient;
import org.cucumber.server.repositories.Repositories;
import org.cucumber.server.repositories.impl.UserRepository;
import org.cucumber.server.utils.classes.Controller;
import org.cucumber.server.utils.mappers.UserMapper;
import org.mapstruct.factory.Mappers;

public class UserController {

    public class UserMe extends Controller {

        public static final String route = Routes.Server.USER_ME.getValue();
        public UserMe() {
            super(route);
        }

        @Override
        public void handle(SocketClient socketClient, String requestId, Object args) {

            User user = socketClient.getUser();

            if (user != null) {
                UserDTO userDTO = Mappers.getMapper(UserMapper.class).userToUserDTO(user);
                socketClient.sendToClient(new SocketMessage(requestId, userDTO));
            } else {
                socketClient.sendToClient(new SocketMessage(requestId, new Status(false, "You are not logged in!")));
            }
        }
    }

    public static class Edit extends Controller {

        public static final String route = Routes.Server.USER_EDIT.getValue();
        public Edit() {
            super(route);
        }

        @Override
        public void handle(SocketClient socketClient, String requestId, Object args) {
            UserTarget user = args instanceof UserTarget ? ((UserTarget) args) : null;

            if (user != null) {

                try {
                    User updatedUser = Repositories.get(UserRepository.class).updateWithDTO(user.getTarget());
                    socketClient.sendToClient(new SocketMessage(requestId, Mappers.getMapper(UserMapper.class).userToUserDTO(updatedUser)));
                } catch (Exception e) {
                    socketClient.sendToClient(new SocketMessage(requestId, new Status(false, "Une erreur est survenue")));
                }
            } else {
                socketClient.sendToClient(new SocketMessage(requestId, new Status(false, "Bad request")));
            }
        }
    }
}
