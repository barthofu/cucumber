package org.cucumber.common.dto.contents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.cucumber.common.dto.SocketMessageContent;

@AllArgsConstructor
@Getter
@Setter
public class UserInfo extends SocketMessageContent {

    private Integer id;
    private String username;
    private String description;
    private String avatar;
    private String age;

}
