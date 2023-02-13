package org.cucumber.server.services;

import lombok.AllArgsConstructor;
import org.cucumber.common.dto.SocketMessage;
import org.cucumber.common.dto.contents.JoinRoomResponse;
import org.cucumber.server.core.SocketManager;
import org.cucumber.server.models.bo.Room;
import org.cucumber.server.repositories.Repositories;
import org.cucumber.server.repositories.impl.RoomRepository;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomService {

    @AllArgsConstructor
    public static class WaitingUser {
        public int userId;
        public String requestId;
        public int socketId;
    }

    List<WaitingUser> waitingUsers = new ArrayList<>();

    public void addWaitingUser(int userId, String requestId, int socketId) {

        waitingUsers.add(new WaitingUser(userId, requestId, socketId));

        if (waitingUsers.size() == 2) {
            processWaitingUsers();
        }
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
                        new JoinRoomResponse(
                                newRoom.getId(),
                                user2.userId
                        )
                ));
        SocketManager.getInstance()
                .getById(user2.socketId)
                .sendToClient(new SocketMessage(
                        user2.requestId,
                        new JoinRoomResponse(
                                newRoom.getId(),
                                user1.userId
                        )
                ));
    }

    public void removeWaitingUser(int userId) {
        waitingUsers.removeIf(w -> w.userId == userId);
    }

    // ================================
    // Singleton
    // ================================

    private static ChatRoomService instance;

    private ChatRoomService() {}

    public static ChatRoomService getInstance() {
        if (instance == null) {
            instance = new ChatRoomService();
        }
        return instance;
    }
}
