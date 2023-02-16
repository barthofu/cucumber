package org.cucumber.server.repositories;

import org.cucumber.server.models.bo.User;

import java.util.Set;

public interface IUserRepository {

    User getByUsername(String username);

    Set<User> getUserFav(User user);

    void deleteFav(User user, Integer targetId);
    void addFav(User user, Integer targetId);

}
