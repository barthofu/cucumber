package org.cucumber.client.utils.interfaces;

import org.cucumber.common.dto.SocketMessageContent;

@FunctionalInterface
public interface IMessageCallback {
    void apply(SocketMessageContent content);
}
