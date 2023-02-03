package org.cucumber.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.cucumber.controllers.HelloWorld;
import org.cucumber.common.dto.SocketMessage;
import org.cucumber.models.so.SocketClient;
import org.cucumber.utils.classes.Controller;

import java.util.Set;

@Getter
@Setter
public class Router {

    static Router instance;

    public static Router getInstance() {
        if (instance == null) {
            instance = new Router();
        }
        return instance;
    }

    private final Set<Controller> routes;

    private Router() {
        this.routes = Set.of(
                new HelloWorld()
        );
    }

    public void handle(SocketClient socketClient, SocketMessage socketMessage) throws EntityNotFoundException {

        findFromRoute(socketMessage.getRoute())
                .getIHandler()
                .apply(
                        socketClient,
                        socketMessage.getId(),
                        socketMessage.getContent()
                );
    }

    public Controller findFromRoute(String route) throws EntityNotFoundException {
        return this.routes.stream().filter(controller -> controller.getRoute().equals(route)).findFirst().orElseThrow(EntityNotFoundException::new);
    }
}
