package org.cucumber.common.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocketMessage implements Serializable {

    private final String id;
    private final String route;
    private final SocketMessageContent content;

    public SocketMessage(String id, String route, SocketMessageContent content) {
        this.id = id;
        this.content = content;
        this.route = route;
    }

}
