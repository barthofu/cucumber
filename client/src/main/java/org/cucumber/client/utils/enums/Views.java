package org.cucumber.client.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Name of the files representing the JFX views.
 */
@Getter
@AllArgsConstructor
public enum Views {

    MAIN_MENU("main-menu-view"),
    FAV("fav-view"),
    LOGIN("login-view"),
    REGISTER("register-view"),
    CHAT("chat-view"),
    WAITING("waiting-view"),
    PROFILE("profile-view");

    private final String value;
}
