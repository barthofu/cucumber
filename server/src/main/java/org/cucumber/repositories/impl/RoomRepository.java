package org.cucumber.repositories.impl;

import jakarta.persistence.EntityManager;
import org.cucumber.models.bo.Room;
import org.cucumber.repositories.IRoomRepository;

public class RoomRepository extends BasicRepository<Room> implements IRoomRepository {

    public RoomRepository(EntityManager em) {
        super(em, Room.class);
    }
}
