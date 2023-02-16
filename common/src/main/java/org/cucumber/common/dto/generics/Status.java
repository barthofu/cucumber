package org.cucumber.common.dto.generics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.cucumber.common.dto.base.SocketMessageContent;

@Getter
@Setter
@RequiredArgsConstructor
public class Status extends SocketMessageContent {

    private final boolean success;
    private String message;

    public Status(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }
}
