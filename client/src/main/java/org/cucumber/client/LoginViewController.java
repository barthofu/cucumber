package org.cucumber.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.cucumber.client.services.SocketMessageService;
import org.cucumber.client.services.UserService;
import org.cucumber.client.utils.classes.Controller;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.client.utils.enums.Views;
import org.cucumber.common.dto.UserDTO;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.base.SocketMessageContent;
import org.cucumber.common.dto.generics.Status;
import org.cucumber.common.dto.requests.LoginRequest;
import org.cucumber.common.utils.Routes;

import java.io.IOException;
import java.util.UUID;

public class LoginViewController extends Controller {

    public LoginViewController() {
        super("Se connecter");
    }

    @FXML
    private Button loginBtn;

    @FXML
    protected TextField username;
    @FXML
    protected TextField password;
    @FXML
    protected Label errorLabel;

    private ActionEvent lastActionEvent;

    @FXML
    protected void onSignUp(ActionEvent event) throws IOException {
        FXUtils.goTo(Views.REGISTER.getValue(), this, event);
    }

    @FXML
    protected void onLoginButton(ActionEvent event) throws IOException {

        setErrorMessage("");
        lastActionEvent = event;

        SocketMessageService.getInstance().send(
                new SocketMessage(
                        UUID.randomUUID().toString(),
                        Routes.Server.AUTH_LOGIN.getValue(),
                        new LoginRequest(username.getText(), password.getText())
                ),
                LoginViewController::handleLoginResponse,
                this
        );
    }

    public void setErrorMessage(String message) {
        errorLabel.setText(message);
    }

    public static <T extends Controller> void handleLoginResponse(SocketMessageContent response, T context) {
        try {
            if (response instanceof UserDTO) {

                UserService.getInstance().setCurrentUser((UserDTO) response);

                Platform.runLater(() -> {
                    try {
                        FXUtils.goTo(Views.MAIN_MENU.getValue(), context, ((LoginViewController) context).lastActionEvent);
                    } catch (IOException e) {
                        ((LoginViewController) context).errorLabel.setText(e.getMessage());
                    }
                });
            } else {
                String message = ((Status) response).getMessage();
                Platform.runLater(() -> {
                    ((LoginViewController) context).setErrorMessage(message != null ? message : "Une erreur est survenue");
                });
            }
        } catch (Exception e){
            System.out.println(RegisterViewController.class.getName() + " : "+  e.getMessage());
        }
    }

}
