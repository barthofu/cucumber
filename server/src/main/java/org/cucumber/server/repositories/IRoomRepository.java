package org.cucumber.server.repositories;

import org.cucumber.server.models.bo.Room;

public interface IRoomRepository {

    Room createRoom(int userId1, int userId2);

    Room findByUserId(int userId);
}
