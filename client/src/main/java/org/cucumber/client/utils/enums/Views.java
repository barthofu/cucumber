package org.cucumber.client.utils.enums;

/**
 * name of the files representing the JFX views
 * */
public enum Views {
    MAIN_MENU("main-menu-view"),
    FAV("fav-view"),
    LOGIN("login-view"),
    REGISTER("register-view"),
    CHAT("chat-view"),
    WAITING("waiting-view");

    private final String viewName;

    Views(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}
