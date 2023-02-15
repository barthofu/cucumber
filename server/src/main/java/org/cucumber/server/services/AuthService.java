package org.cucumber.server.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.server.models.bo.User;
import org.cucumber.server.repositories.Repositories;
import org.cucumber.server.repositories.impl.UserRepository;

import java.util.List;

public class AuthService {

    public User checkAuth(String username, String password) {
        try {
            List<User> users = Repositories.get(UserRepository.class).findAll();
            for (User user : users)
                if (username.equals(user.getUsername()) && checkHash(password, user.getPassword())) {
                    return user;
                }
        } catch (Exception e) {
            Logger.log(LoggerStatus.SEVERE, e.getMessage());
        }
        return null;
    }

    public void register(String username, String password) {

        User user = User.builder()
                .username(username)
                .password(hashPsswd(password))
                .build();

        Repositories
                .get(UserRepository.class)
                .create(user);

        Logger.log(LoggerStatus.INFO, "Added new user: " + username);
    }

    public String hashPsswd(String str) {
        return BCrypt
                .withDefaults()
                .hashToString(12, str.toCharArray());
    }

    public boolean checkHash(String psswd, String hash) {
        return BCrypt.verifyer().verify(psswd.toCharArray(), hash).verified;
    }

    // ================================
    // Singleton
    // ================================

    private static AuthService instance;

    private AuthService() {}

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }
}
