package org.cucumber.server.repositories.impl;

import jakarta.persistence.EntityManager;
import org.cucumber.common.dto.UserDTO;
import org.cucumber.server.models.bo.User;
import org.cucumber.server.repositories.IUserRepository;

import java.util.Set;

public class UserRepository extends BasicRepository<User> implements IUserRepository {

    public UserRepository(EntityManager em) {
        super(em, User.class);
    }

    @Override
    public User getByUsername(String username) {
        return em
                .createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public User updateWithDTO(UserDTO userDTO) {

        // update all fields except password
        User user = em.find(User.class, userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setAge(userDTO.getAge());
        user.setDescription(userDTO.getDescription());
        user.setAvatar(userDTO.getAvatar());

        return update(user);
    }

    @Override
    public Set<User> getUserFav(User user) {
        return user.getFavorites();
    }

    @Override
    public void deleteFav(User user, Integer targetId) {
        em.getTransaction().begin();

        em
                .createNativeQuery(String.format("DELETE FROM user_favorite uf WHERE uf.to = %d AND uf.from = %d", targetId, user.getId()))
                .executeUpdate();

        em.getTransaction().commit();
        em.refresh(user);
    }

    @Override
    public void addFav(User user, Integer targetId) {
        em.getTransaction().begin();

        em
                .createNativeQuery(String.format("INSERT INTO user_favorite (\"to\", \"from\") VALUES(%d, %d)", targetId, user.getId()))
                .executeUpdate();

        em.getTransaction().commit();
        em.refresh(user);
    }
}
