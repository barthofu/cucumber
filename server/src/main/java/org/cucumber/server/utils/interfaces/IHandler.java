package org.cucumber.server.utils.interfaces;

import org.cucumber.server.models.so.SocketClient;

/**
 * The IHandler interface is the base interface for all handlers.
 */
@FunctionalInterface
public interface IHandler {
    void apply(SocketClient socketClient, String requestId, Object args);
}
