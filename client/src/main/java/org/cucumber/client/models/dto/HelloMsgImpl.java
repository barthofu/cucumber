package org.cucumber.client.models.dto;

import org.cucumber.common.dto.SocketMessage;
import org.cucumber.common.dto.SocketMessageContent;

import java.io.Serializable;

public class HelloMsgImpl extends SocketMessageContent implements Serializable {
    String text;

    public HelloMsgImpl(String text) {
        this.text = text;
    }
}
