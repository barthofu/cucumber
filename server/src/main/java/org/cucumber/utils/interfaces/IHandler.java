package org.cucumber.utils.interfaces;

import org.cucumber.models.so.SocketClient;

@FunctionalInterface
public interface IHandler {
    void apply(SocketClient socketClient, String requestId, Object args);
}
