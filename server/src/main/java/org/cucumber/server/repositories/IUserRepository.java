package org.cucumber.server.repositories;

import org.cucumber.server.models.bo.User;

import java.util.List;
import java.util.Set;

public interface IUserRepository {
    
    Set<User> getUserFav(User user);

    void deleteFav(User source, Integer targetId);
}
