package org.cucumber.client.utils.interfaces;

import org.cucumber.common.dto.SocketMessageContent;

import java.io.IOException;

@FunctionalInterface
public interface IMessageCallback {
    void apply(SocketMessageContent content, Object context);
}
