package org.cucumber.server.repositories.impl;

import jakarta.persistence.EntityManager;
import org.cucumber.server.models.bo.User;
import org.cucumber.server.repositories.IUserRepository;

import java.util.Set;

public class UserRepository extends BasicRepository<User> implements IUserRepository {

    public UserRepository(EntityManager em) {
        super(em, User.class);
    }

    @Override
    public Set<User> getUserFav(User user) {
        return user.getFavorites();
    }
}
