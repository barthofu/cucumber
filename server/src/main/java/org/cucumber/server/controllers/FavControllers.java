package org.cucumber.server.controllers;

import org.cucumber.common.utils.Routes;
import org.cucumber.server.models.so.SocketClient;
import org.cucumber.server.utils.classes.Controller;

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

        }
    }
}