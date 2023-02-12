package org.cucumber.server.repositories.impl;

import jakarta.persistence.EntityManager;
import org.cucumber.server.models.bo.Room;
import org.cucumber.server.repositories.IRoomRepository;

public class RoomRepository extends BasicRepository<Room> implements IRoomRepository {

    public RoomRepository(EntityManager em) {
        super(em, Room.class);
    }
}
