package org.cucumber.common.dto.contents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.cucumber.common.dto.SocketMessageContent;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class GenericSuccess extends SocketMessageContent {

    private boolean success = true;
}
