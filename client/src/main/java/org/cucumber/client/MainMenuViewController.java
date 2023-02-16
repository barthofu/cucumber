package org.cucumber.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.cucumber.client.services.SocketMessageService;
import org.cucumber.client.services.UserService;
import org.cucumber.client.utils.classes.Controller;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.client.utils.enums.Views;
import org.cucumber.common.dto.UserDTO;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.base.SocketMessageContent;
import org.cucumber.common.dto.generics.Empty;
import org.cucumber.common.dto.generics.Status;
import org.cucumber.common.utils.Routes;

import java.io.IOException;
import java.util.UUID;


public class MainMenuViewController extends Controller {

    public MainMenuViewController() {
        super("La vitesse rencontre l'amour!");
    }

    @FXML
    protected Label usernameLabel;
    @FXML
    protected Label connectedUsersLabel;
    @FXML
    protected ImageView avatar;

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
    protected void onLogoutButton(ActionEvent event) throws IOException {

        SocketMessageService.getInstance().send(
                new SocketMessage(
                        UUID.randomUUID().toString(),
                        Routes.Server.AUTH_LOGOUT.getValue(),
                        new Empty()
                ),
                MainMenuViewController::handleLogoutResponse,
                this
        );

        UserService.getInstance().reset();
        FXUtils.goTo(Views.LOGIN.getValue(), this, event);
    }

    private static <T extends Controller> void handleLogoutResponse(SocketMessageContent response, T context) {
    }

    @FXML
    protected void onFavButton(ActionEvent event) throws IOException {
        FXUtils.goTo(Views.FAV.getValue(), this, event);
    }

    @FXML
    protected void onStartDating(ActionEvent event) throws IOException {
        FXUtils.goTo(Views.WAITING.getValue(), this, event);
    }

    @FXML
    protected void onProfileButton(ActionEvent event) throws IOException {
        FXUtils.goTo(Views.PROFILE.getValue(), this, event);
    }

    public void updateTotalUsers() {

        int totalUsers = UserService.getInstance().getTotalUsers();
        if (totalUsers == 1) this.connectedUsersLabel.setText("1 personne connectée");
        else this.connectedUsersLabel.setText(String.format("%d personnes connectées", totalUsers));
    }

    protected void setCurrentUserDisplayInfos(UserDTO user) {
        usernameLabel.setText(user.getUsername());
        if (user.getAvatar() != null) avatar.setImage(new Image(user.getAvatar()));
    }

    // ================================
    // Utilities
    // ================================

    public MainMenuViewController getController() {
        return this;
    }
}
