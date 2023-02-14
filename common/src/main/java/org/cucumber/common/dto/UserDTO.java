package org.cucumber.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.cucumber.common.dto.base.SocketMessageContent;

@AllArgsConstructor
@Getter
@Setter
public class UserDTO extends SocketMessageContent {

    private Integer id;
    private String username;
    private String description;
    private String avatar;
    private String age;

}
