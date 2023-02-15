package org.cucumber.server.services;

import org.cucumber.server.models.bo.Message;
import org.cucumber.server.models.bo.Room;
import org.cucumber.server.models.bo.User;
import org.cucumber.server.models.so.SocketClient;
import org.cucumber.server.repositories.Repositories;
import org.cucumber.server.repositories.impl.MessageRepository;
import org.cucumber.server.repositories.impl.RoomRepository;

import java.util.Objects;
import java.util.Optional;

public class MessageService {

    public Message saveMessage(User sender, String content) {

        // get the active room of the sender
        Room room = Repositories
                .get(RoomRepository.class)
                .findByUserId(sender.getId());

        if (room != null) {

            // get the receiver
            Optional<User> receiver = room
                    .getUsers()
                    .stream()
                    .filter(user ->
                            !Objects.equals(user.getId(), sender.getId())
                    )
                    .findFirst();

            if (receiver.isPresent()) {

                // save in database
                Message message = Message.builder()
                        .content(content)
                        .room(room)
                        .from(sender)
                        .to(receiver.get())
                        .build();

                return Repositories.get(MessageRepository.class).create(message);
            }
        } return null;
    }

    // ====================
    // Singleton
    // ====================

    private static MessageService instance;

    private MessageService() {}

    public static MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService();
        }
        return instance;
    }
}
