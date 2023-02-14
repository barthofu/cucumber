package org.cucumber.server.controllers;

import org.cucumber.common.dto.UserDTO;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.generics.Status;
import org.cucumber.common.dto.responses.GetFavOfUserResponse;
import org.cucumber.common.utils.Routes;
import org.cucumber.server.models.bo.User;
import org.cucumber.server.models.so.SocketClient;
import org.cucumber.server.services.FavService;
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

        }
    }

    public static class Remove extends Controller {
        public static final String route = Routes.Server.FAV_REMOVE.getValue();

        public Remove() {
            super(route);
        }

        @Override
        public void handle(SocketClient socketClient, String requestId, Object args) {

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
                    ));
        }
    }
}