package org.cucumber.client.utils.enums;

public enum Views {
    MAIN_MENU("main-menu-view"),
    FAV("fav-view"),
    LOGIN("login-view"),
    REGISTER("register-view");

    private final String viewName;

    Views(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}
