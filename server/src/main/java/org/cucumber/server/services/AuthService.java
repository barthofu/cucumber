package org.cucumber.server.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.cucumber.common.dto.UserDTO;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.server.core.SocketManager;
import org.cucumber.server.models.bo.User;
import org.cucumber.server.repositories.Repositories;
import org.cucumber.server.repositories.impl.UserRepository;
import org.cucumber.server.utils.errors.UserAlreadyLoggedInException;
import org.cucumber.server.utils.errors.UsernameAlreadyTakenException;

import java.util.List;

/**
 * Service for authentication
 */
public class AuthService {

    public User login(String username, String password) throws UserAlreadyLoggedInException {

        List<User> users = Repositories.get(UserRepository.class).findAll();
        for (User user : users) {
            if (username.equals(user.getUsername()) && checkHash(password, user.getPassword())) {
                // user found with correct username and password

                // check user is already logged in
                if (SocketManager.getInstance().getByUserId(user.getId()) != null) throw new UserAlreadyLoggedInException();

                return user;
            }
        }

        return null;
    }

    public void register(UserDTO userDTO, String password) throws UsernameAlreadyTakenException {

        User userWithSameUsername = Repositories
                .get(UserRepository.class)
                .getByUsername(userDTO.getUsername());
        if (userWithSameUsername != null)
            throw new UsernameAlreadyTakenException();

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(hashPsswd(password))
                .age(userDTO.getAge())
                .description(userDTO.getDescription())
                .build();

        Repositories
                .get(UserRepository.class)
                .create(user);

        Logger.log(LoggerStatus.INFO, "Added new user: " + userDTO.getUsername());
    }

    public void logout(int userId) {
        SocketManager.getInstance().removeClient(SocketManager.getInstance().getByUserId(userId));
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
