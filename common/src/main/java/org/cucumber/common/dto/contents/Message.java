package org.cucumber.common.dto.contents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.cucumber.common.dto.SocketMessageContent;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message extends SocketMessageContent {
    private String text;
}
