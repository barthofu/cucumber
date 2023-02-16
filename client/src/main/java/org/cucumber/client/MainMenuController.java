package org.cucumber.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.cucumber.client.services.UserService;
import org.cucumber.client.utils.classes.Controller;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.client.utils.enums.Views;
import org.cucumber.common.dto.UserDTO;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;

import java.io.IOException;


public class MainMenuController extends Controller {

    public MainMenuController() {
        super("La vitesse rencontre l'amour!");
    }

    @FXML
    protected Label usernameLabel;
    @FXML
    protected Label connectedUsersLabel;

    @Override
    public void onInit() {

        updateTotalUsers();

        UserDTO currentUser = UserService.getInstance().getCurrentUser();
        if (currentUser != null) {
            setCurrentUserDisplayInfos(currentUser);
        }
    }

    @FXML
    protected void onQuitButton(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    protected void onFavButton(ActionEvent event) throws IOException {
        FXUtils.goTo(Views.FAV.getViewName(), this, event);
    }

    @FXML
    protected void onStartDating(ActionEvent event) throws IOException {

        FXUtils.goTo(Views.WAITING.getViewName(), this, event);
    }

    public void updateTotalUsers() {

        int totalUsers = UserService.getInstance().getTotalUsers();
        this.connectedUsersLabel.setText(String.format("%d personnes connectées", totalUsers));
    }

    protected void setCurrentUserDisplayInfos(UserDTO user) {
        usernameLabel.setText("Connecté en tant que " + user.getUsername());
    }

    // ================================
    // Utilities
    // ================================

    public MainMenuController getController() {
        return this;
    }
}
