package org.cucumber.utils.classes;

import lombok.Getter;
import org.cucumber.models.so.SocketClient;

@Getter
public abstract class Controller {

    private final org.cucumber.utils.interfaces.IHandler IHandler;

    private final String route;

    public Controller (String route) {
        this.route = route;
        IHandler = this::handle;
    };

    public abstract void handle(SocketClient socketClient, String requestId, Object args);

}
