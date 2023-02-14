package org.cucumber.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.cucumber.common.dto.base.SocketMessageContent;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class MessageDTO extends SocketMessageContent {
    private String text;
}
