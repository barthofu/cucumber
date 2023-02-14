package org.cucumber.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.cucumber.client.utils.classes.Controller;
import org.cucumber.client.services.MessageManager;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.client.utils.enums.Views;
import org.cucumber.common.dto.SocketMessage;
import org.cucumber.common.dto.SocketMessageContent;
import org.cucumber.common.dto.contents.*;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;

import java.io.IOException;
import java.util.UUID;

public class LoginController extends Controller {

    public LoginController() {
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
        FXUtils.goTo(Views.REGISTER.getViewName(), this, event);
    }

    @FXML
    protected void onLoginButton(ActionEvent event) throws IOException {
            lastActionEvent = event;
            MessageManager.getInstance().send(
                    new SocketMessage(
                            UUID.randomUUID().toString(),
                            "login",
                            new LoginMsg(username.getText(), password.getText())
                    ),
                    LoginController::handleLoginResponse,
                    this
            );
    }

    public static <T extends Controller> void handleLoginResponse(SocketMessageContent message, T context){
        try {
            if (( (LoginResponse) message).isStatus()) {
                Platform.runLater(() -> {
                    try {
                        FXUtils.goTo(Views.MAIN_MENU.getViewName(), context, ((LoginController) context).lastActionEvent);
                    } catch (IOException e) {
                        ((LoginController) context).errorLabel.setText(e.getMessage());
                    }
                });
            }else{
                Platform.runLater(() -> {
                    ((LoginController) context).errorLabel.setText("Connexion impossible");
                });
            }
        }catch (Exception e){
            System.out.println(RegisterViewController.class.getName() + " : "+  e.getMessage());
        }
    }

    //run on hello response
    public static void handleHelloResponse(SocketMessageContent message, Object context){
        System.out.println("hello from the callback on server response : " + ((Message) message).getText());
    }


    @Override
    public void onView() {

    }
}
