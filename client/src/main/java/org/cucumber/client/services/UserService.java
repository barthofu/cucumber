package org.cucumber.client.services;

import lombok.Getter;
import lombok.Setter;
import org.cucumber.common.dto.UserDTO;

@Getter
@Setter
public class UserService {

    private UserDTO currentUser = null;

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    // ================================
    // Singleton
    // ================================

    private static UserService instance;

    private UserService() {}

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
}
