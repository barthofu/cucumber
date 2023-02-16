package org.cucumber.server.controllers;

import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.generics.Status;
import org.cucumber.common.dto.generics.UserTarget;
import org.cucumber.common.dto.responses.GetFavOfUserResponse;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.common.utils.Routes;
import org.cucumber.server.models.bo.User;
import org.cucumber.server.models.so.SocketClient;
import org.cucumber.server.repositories.Repositories;
import org.cucumber.server.repositories.impl.UserRepository;
import org.cucumber.server.utils.classes.Controller;
import org.cucumber.server.utils.mappers.UserMapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

public class FavControllers {

    public static class Add extends Controller {
        public static final String route = Routes.Server.FAV_ADD.getValue();

        public Add() {
            super(route);
        }

        @Override
        public void handle(SocketClient socketClient, String requestId, Object args) {
            UserTarget arguments = args instanceof UserTarget ? ((UserTarget) args) : null;
            UserRepository userRepository = Repositories.get(UserRepository.class);
            try {
                userRepository.addFav(socketClient.getUser(), arguments.getTarget().getId());

                socketClient.sendToClient(new SocketMessage(
                        requestId,
                        route,
                        new Status(
                                true
                        )));
            } catch (Exception e) {
                e.printStackTrace();
                Logger.log(LoggerStatus.SEVERE, "can't save fav");
            }
        }
    }

    public static class Remove extends Controller {
        public static final String route = Routes.Server.FAV_REMOVE.getValue();

        public Remove() {
            super(route);
        }

        @Override
        public void handle(SocketClient socketClient, String requestId, Object args) {
            UserTarget arguments = args instanceof UserTarget ? ((UserTarget) args) : null;
            UserRepository userRepository = Repositories.get(UserRepository.class);
            try {
                userRepository.deleteFav(socketClient.getUser(), arguments.getTarget().getId());

                socketClient.sendToClient(new SocketMessage(
                        requestId,
                        new Status(true)
                ));
            } catch (Exception e) {
                Logger.log(LoggerStatus.SEVERE, e.getMessage());
                e.printStackTrace();
                socketClient.sendToClient(new SocketMessage(
                        requestId,
                        new Status(false)
                ));
            }
        }
    }

    public static class Get extends Controller {
        public static final String route = Routes.Server.FAV_GET.getValue();

        public Get() {
            super(route);
        }

        @Override
        public void handle(SocketClient socketClient, String requestId, Object args) {
            Set<User> userSet = socketClient.getUser().getFavorites();

            socketClient.sendToClient(new SocketMessage(
                    requestId,
                    new GetFavOfUserResponse(
                            Mappers.getMapper(UserMapper.class).userSetToUserDTOSet(userSet)
                    )));
        }
    }
}
