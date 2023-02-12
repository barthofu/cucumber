package org.cucumber.server.repositories.impl;

import jakarta.persistence.EntityManager;
import org.cucumber.server.models.bo.User;
import org.cucumber.server.repositories.IUserRepository;

public class UserRepository extends BasicRepository<User> implements IUserRepository {

    public UserRepository(EntityManager em) {
        super(em, User.class);
    }
}
