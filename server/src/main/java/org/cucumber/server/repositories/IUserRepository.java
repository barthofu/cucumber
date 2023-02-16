package org.cucumber.server.repositories;

import org.cucumber.common.dto.UserDTO;
import org.cucumber.server.models.bo.User;

import java.util.Optional;
import java.util.Set;

public interface IUserRepository {

    Optional<User> getByUsername(String username);
    User updateWithDTO(UserDTO userDTO);

    Set<User> getUserFav(User user);
    void deleteFav(User user, Integer targetId);
    void addFav(User user, Integer targetId);

}
