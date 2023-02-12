package org.cucumber.server.utils.interfaces;

import org.cucumber.server.models.so.SocketClient;

@FunctionalInterface
public interface IHandler {
    void apply(SocketClient socketClient, String requestId, Object args);
}
