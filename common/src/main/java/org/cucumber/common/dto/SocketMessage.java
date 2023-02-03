package org.cucumber.common.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocketMessage {

    private final String id;
    private final String route;
    private final SocketMessageContent content;

    public SocketMessage(SocketMessageContent content) {

        this.id = UUID.randomUUID().toString();
        this.content = content;
        this.route = null;
    }

    public SocketMessage(String id, SocketMessageContent content) {

        this.id = id;
        this.content = content;
        this.route = null;
    }

    public SocketMessage(String id, String route, SocketMessageContent content) {

        this.id = id;
        this.content = content;
        this.route = route;
    }

}
