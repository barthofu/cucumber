package org.cucumber.client.services;

import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.cucumber.client.MainMenuController;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.common.dto.UserDTO;

import java.util.Optional;

@Getter
@Setter
public class UserService {

    private Stage currentStage;

    private UserDTO currentUser = null;

    private int totalUsers = 0;

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;

        Optional<MainMenuController> mainMenuController = FXUtils.getCurrentController(MainMenuController.class);
        Platform.runLater(() -> {
            mainMenuController.ifPresent(MainMenuController::updateTotalUsers);
        });
    }

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
