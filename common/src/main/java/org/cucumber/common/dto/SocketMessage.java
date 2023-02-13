package org.cucumber.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class SocketMessage implements Serializable {
    private final String id;
    private final String route;
    private final SocketMessageContent content;
}
