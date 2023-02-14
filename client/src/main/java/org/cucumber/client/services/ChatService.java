package org.cucumber.client.services;

import lombok.Getter;
import lombok.Setter;
import org.cucumber.common.dto.UserDTO;

@Getter
@Setter
public class ChatService {

    private boolean isChatting;

    private UserDTO otherUserDTO;

    public void startChat(UserDTO otherUserDTO) {
        isChatting = true;
        this.otherUserDTO = otherUserDTO;
    }

    public void reset() {
        isChatting = false;
        otherUserDTO = null;
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
