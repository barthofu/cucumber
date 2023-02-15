package org.cucumber.client.services;

import javafx.application.Platform;
import lombok.Getter;
import lombok.Setter;
import org.cucumber.client.ChatController;
import org.cucumber.client.MainMenuController;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.common.dto.MessageDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service to store incoming messages and manage them.
 */
@Getter
@Setter
public class MessageService {

    List<MessageDTO> messages = new ArrayList<>();

    public void addMessage(MessageDTO message) {

        // append message to list
        messages.add(message);

        // check if message is from current user
        boolean isOwnMessage = message.getFrom() == UserService.getInstance().getCurrentUser().getId();

        Optional<ChatController> chatController = FXUtils.getCurrentController(ChatController.class);
        Platform.runLater(() -> {
            chatController.ifPresent(controller -> controller.appendMessage(message, isOwnMessage));
        });
    }

    // ================================
    // Singleton
    // ================================

    private static MessageService instance;

    private MessageService() {}

    public static MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService();
        }
        return instance;
    }
}
