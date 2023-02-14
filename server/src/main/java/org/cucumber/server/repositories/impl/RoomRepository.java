package org.cucumber.server.repositories.impl;

import jakarta.persistence.EntityManager;
import org.cucumber.server.models.bo.Room;
import org.cucumber.server.models.bo.User;
import org.cucumber.server.repositories.IRoomRepository;
import org.cucumber.server.repositories.Repositories;

import java.util.Set;

public class RoomRepository extends BasicRepository<Room> implements IRoomRepository {

    public RoomRepository(EntityManager em) {
        super(em, Room.class);
    }

    @Override
    public Room createRoom(int userId1, int userId2) {

        User user1 = Repositories.get(UserRepository.class).findById(userId1);
        User user2 = Repositories.get(UserRepository.class).findById(userId2);

        Room room = Room
                .builder()
                .users(Set.of(user1, user2))
                .build();

        return create(room);
    }

    @Override
    public Room findByUserId(int userId) {
        return em.createQuery(
                    "SELECT r.* " +
                    "FROM Room r " +
                    "JOIN room_user ru ON ru.room_id = r.id " +
                    "WHERE ru.user_id = :userId " +
                    "  AND r.active = true ",
                    Room.class
                )
                .setParameter("userId", userId)
                .getSingleResult();
    }
}
