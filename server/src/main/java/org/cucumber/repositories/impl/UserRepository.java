package org.cucumber.repositories.impl;

import jakarta.persistence.EntityManager;
import org.cucumber.models.bo.User;
import org.cucumber.repositories.IUserRepository;

public class UserRepository extends BasicRepository<User> implements IUserRepository {

    public UserRepository(EntityManager em) {
        super(em, User.class);
    }
}
