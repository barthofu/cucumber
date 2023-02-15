package org.cucumber.common.dto;

import lombok.*;
import org.cucumber.common.dto.base.SocketMessageContent;

import java.time.Instant;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class MessageDTO extends SocketMessageContent {
    private final String content;
    private int from;
    private int to;
    private Instant date;
}
