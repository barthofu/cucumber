package org.cucumber.client.services;

import lombok.Getter;
import lombok.Setter;
import org.cucumber.common.dto.contents.UserInfo;

@Getter
@Setter
public class ChatService {

    private boolean isChatting;

    private UserInfo otherUserInfo;

    public void startChat(UserInfo otherUserInfo) {
        isChatting = true;
        this.otherUserInfo = otherUserInfo;
    }

    public void reset() {
        isChatting = false;
        otherUserInfo = null;
    }

    // ================================
    // Singleton
    // ================================

    private static ChatService instance;

    private ChatService() {}

    public static ChatService getInstance() {
        if (instance == null) {
            instance = new ChatService();
        }
        return instance;
    }
}
