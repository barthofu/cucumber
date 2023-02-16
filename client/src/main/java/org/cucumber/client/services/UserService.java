package org.cucumber.client.services;

import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.cucumber.client.MainMenuViewController;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.common.dto.UserDTO;

import java.util.Optional;

/**
 * Service to store the current user after login and manage it.
 */
@Getter
@Setter
public class UserService {

    private UserDTO currentUser = null;

    /**
     * the total number of connected users, updated reactively by the server.
     */
    private int totalUsers = 0;

    /**
     * Update the main menu label "user number".
     * @param totalUsers actual number of users
     */
    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;

        Optional<MainMenuViewController> mainMenuController = FXUtils.getCurrentController(MainMenuViewController.class);
        Platform.runLater(() -> {
            mainMenuController.ifPresent(MainMenuViewController::updateTotalUsers);
        });
    }

    /**
     * is the current user logged in ?
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Logout the current user.
     */
    public void reset() {
        currentUser = null;
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
