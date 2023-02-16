package org.cucumber.common.dto;

import lombok.*;
import org.cucumber.common.dto.base.SocketMessageContent;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDTO extends SocketMessageContent {
    private Integer id;
    private final String username;
    private String description;
    private String avatar;
    private int age;
}
