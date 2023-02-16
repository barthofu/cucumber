package org.cucumber.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Routes used by the server and the client.
 * Thanks to that, all routes are type-safe and can be easily refactored without risks.
 */
@Getter
@AllArgsConstructor
public class Routes {

    @Getter
    @AllArgsConstructor
    public enum Server {
        FAV_ADD("fav/add"),
        FAV_GET("fav/get"),
        FAV_REMOVE("fav/remove"),

        CHAT_SEND("chat/send"),
        CHAT_JOIN("chat/join"),
        CHAT_CANCEL("chat/cancel"),
        CHAT_CLOSE("chat/close"),

        USER_ME("user/me"),
        USER_EDIT("user/edit"),

        AUTH_REGISTER("auth/register"),
        AUTH_LOGIN("auth/login"),
        AUTH_LOGOUT("auth/logout");

        private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum Client {
        USER_TOTAL("user/total"),
        SESSION_STOP("chat/stop"),
        MESSAGE_RECEIVE("chat/receive");

        private final String value;
    }
}
