package org.cucumber.common.dto.requests;

import lombok.*;
import org.cucumber.common.dto.base.SocketMessageContent;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class RegisterRequest extends SocketMessageContent {
    private final String username;
    private final String password;
    private int age;
    private String description;
    private String avatar;
}
