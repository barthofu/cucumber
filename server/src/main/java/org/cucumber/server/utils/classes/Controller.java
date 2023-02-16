package org.cucumber.server.utils.classes;

import lombok.Getter;
import org.cucumber.server.models.so.SocketClient;

/**
 * The Controller class is the base class for all controllers.
 * It provides a default implementation of the IHandler interface.
 */
@Getter
public abstract class Controller {

    private final org.cucumber.server.utils.interfaces.IHandler IHandler;

    private final String route;

    public Controller (String route) {
        this.route = route;
        IHandler = this::handle;
    };

    public abstract void handle(SocketClient socketClient, String requestId, Object args);

}
