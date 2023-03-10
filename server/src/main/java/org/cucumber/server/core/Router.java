package org.cucumber.server.core;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.cucumber.server.controllers.*;
import org.cucumber.server.utils.classes.Controller;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.server.models.so.SocketClient;

import java.util.Set;

/**
 * The Router service is used to handle incoming messages from clients and dispatch them to the corresponding controller instance.
 */
@Getter
@Setter
public class Router {

    private final Set<Controller> routes;

    private Router() {
        this.routes = Set.of(
                new AuthController.Login(),
                new AuthController.Register(),
                new AuthController.Logout(),
                new ChatController.SendMessage(),
                new ChatController.Join(),
                new ChatController.Cancel(),
                new ChatController.Close(),
                new FavController.Add(),
                new FavController.Get(),
                new FavController.Remove(),
                new UserController.Edit()
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

    /**
     * Retrieve a controller class from its route.
     * @param route The route of the controller.
     */
    public Controller findFromRoute(String route) throws EntityNotFoundException {
        return this.routes.stream().filter(controller -> controller.getRoute().equals(route)).findFirst().orElseThrow(EntityNotFoundException::new);
    }

    // ================================
    // Singleton
    // ================================

    private static Router instance;

    public static Router getInstance() {
        if (instance == null) {
            instance = new Router();
        }
        return instance;
    }
}
