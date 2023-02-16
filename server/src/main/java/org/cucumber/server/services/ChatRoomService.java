package org.cucumber.server.services;

import lombok.AllArgsConstructor;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.generics.Empty;
import org.cucumber.common.utils.Routes;
import org.cucumber.server.core.SocketManager;
import org.cucumber.server.models.bo.Room;
import org.cucumber.server.models.bo.User;
import org.cucumber.server.repositories.Repositories;
import org.cucumber.server.repositories.impl.RoomRepository;
import org.cucumber.server.repositories.impl.UserRepository;
import org.cucumber.server.utils.mappers.UserMapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing chat rooms. It is responsible for creating new rooms, assign users to them and match them.
 */
public class ChatRoomService {

    @AllArgsConstructor
    public static class WaitingUser {
        public int userId;
        public String requestId;
        public int socketId;
    }

    List<WaitingUser> waitingUsers = new ArrayList<>();

    /**
     * Add a user to the waiting list. If there are two users in the list, they are matched and a new room is created.
     */
    public void addWaitingUser(int userId, String requestId, int socketId) {

        waitingUsers.add(new WaitingUser(userId, requestId, socketId));

        if (waitingUsers.size() == 2) {
            processWaitingUsers();
        }
    }

    public User getChatter(int userId) {
        Room room = Repositories
                .get(RoomRepository.class)
                .findByUserId(userId);

        if (room != null) {
            return getChatter(userId, room);
        } else {
            return null;
        }
    }

    public User getChatter(int userId, Room room) {

        return room.getUsers().stream()
                .filter(user -> user.getId() != userId)
                .findFirst()
                .orElse(null);
    }

    public void processWaitingUsers() {

        // 1. Get the two first users
        WaitingUser user1 = waitingUsers.get(0);
        WaitingUser user2 = waitingUsers.get(1);

        // 2. Create a new room
        Room newRoom = Repositories.get(RoomRepository.class).createRoom(user1.userId, user2.userId);

        // 3. Remove the two users from the waiting list
        removeWaitingUser(user1.userId);
        removeWaitingUser(user2.userId);

        // 4. Send the room info to the two users
        SocketManager.getInstance()
                .getById(user1.socketId)
                .sendToClient(new SocketMessage(
                        user1.requestId,
                        Mappers.getMapper(UserMapper.class).userToUserDTO(
                                Repositories.get(UserRepository.class).findById(user2.userId)
                        )
                ));
        SocketManager.getInstance()
                .getById(user2.socketId)
                .sendToClient(new SocketMessage(
                        user2.requestId,
                        Mappers.getMapper(UserMapper.class).userToUserDTO(
                                Repositories.get(UserRepository.class).findById(user1.userId)
                        )
                ));
    }

    public void removeWaitingUser(int userId) {
        waitingUsers.removeIf(w -> w.userId == userId);
    }

    public void closeRoom(int userId) {

        RoomRepository repository = Repositories.get(RoomRepository.class);
        Room room = repository.findByUserId(userId);

        if (room != null && room.getActive()) {

            room.setActive(false);
            repository.update(room);

            // send to both users that the chat has been closed
            SocketManager socketManager = SocketManager.getInstance();

            socketManager.getByUserId(getChatter(userId, room).getId()).sendToClient(
                    new SocketMessage(
                            null,
                            Routes.Client.SESSION_STOP.getValue(),
                            new Empty()
                    )
            );

            socketManager.getByUserId(userId).sendToClient(
                    new SocketMessage(
                            null,
                            Routes.Client.SESSION_STOP.getValue(),
                            new Empty()
                    )
            );
        }

    }

    // ================================
    // Singleton
    // ================================

    private static ChatRoomService instance;

    private ChatRoomService() {
    }

    public static ChatRoomService getInstance() {
        if (instance == null) {
            instance = new ChatRoomService();
        }
        return instance;
    }
}
