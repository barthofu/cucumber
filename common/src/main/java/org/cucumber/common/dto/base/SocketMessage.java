package org.cucumber.common.dto.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class SocketMessage implements Serializable {
    private final String id;
    private String route;
    private final SocketMessageContent content;
}
