package org.cucumber.server.controllers;

import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.generics.Status;
import org.cucumber.common.dto.generics.ClientTarget;
import org.cucumber.common.dto.responses.GetFavOfUserResponse;
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
            ClientTarget arguments = args instanceof ClientTarget ? ((ClientTarget) args) : null;
            Set<User> userSet = socketClient.getUser().getFavorites();
            try {
                UserRepository repository = Repositories.get(UserRepository.class);
                userSet.add(repository.findById(arguments.getTarget().getId()));
                socketClient.getUser().setFavorites(userSet);

                socketClient.sendToClient(new SocketMessage(
                        requestId,
                        route,
                        new Status(
                                true
                        )));
            }catch (Exception e){
                socketClient.sendToClient(new SocketMessage(
                        requestId,
                        route,
                        new Status(
                                false,
                                e.getMessage()
                        )));
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
            ClientTarget arguments = args instanceof ClientTarget ? ((ClientTarget) args) : null;
            Set<User> userSet = socketClient.getUser().getFavorites();
            try {
                userSet.remove(arguments.getTarget());
                socketClient.getUser().setFavorites(userSet);
                Repositories.get(UserRepository.class).update(socketClient.getUser());
                socketClient.sendToClient(new SocketMessage(
                        requestId,
                        route,
                        new Status(
                                true
                        )));
            }catch (Exception e){
                socketClient.sendToClient(new SocketMessage(
                        requestId,
                        route,
                        new Status(
                                false
                        )));
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
                    route,
                    new GetFavOfUserResponse(
                            Mappers.getMapper(UserMapper.class).userSetToUserDTOSet(userSet)
                    )));
        }
    }
}