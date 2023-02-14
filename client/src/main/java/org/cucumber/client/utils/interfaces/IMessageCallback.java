package org.cucumber.client.utils.interfaces;

import org.cucumber.client.utils.classes.Controller;
import org.cucumber.common.dto.base.SocketMessageContent;

@FunctionalInterface
public interface IMessageCallback {
    <T extends Controller> void apply(SocketMessageContent content, T context);
}
