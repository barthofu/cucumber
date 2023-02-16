package org.cucumber.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    private ActionEvent lastActionEvent;

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

        lastActionEvent = event;

        SocketMessageService.getInstance().send(
                new SocketMessage(
                        UUID.randomUUID().toString(),
                        Routes.Server.AUTH_LOGOUT.getValue(),
                        new Empty()
                ),
                MainMenuViewController::handleLogoutResponse,
                this
        );
    }

    private static <T extends Controller> void handleLogoutResponse(SocketMessageContent response, T context) {
        Status status = (Status) response;

        if (status.isSuccess()) {
            UserService.getInstance().reset();
            Platform.runLater(() -> {
                try {
                    FXUtils.goTo(Views.LOGIN.getViewName(), context, ((MainMenuViewController) context).lastActionEvent);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

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
        if (totalUsers == 1) this.connectedUsersLabel.setText("1 personne connectée");
        else this.connectedUsersLabel.setText(String.format("%d personnes connectées", totalUsers));
    }

    protected void setCurrentUserDisplayInfos(UserDTO user) {
        usernameLabel.setText("Connecté en tant que " + user.getUsername());
    }

    // ================================
    // Utilities
    // ================================

    public MainMenuViewController getController() {
        return this;
    }
}
