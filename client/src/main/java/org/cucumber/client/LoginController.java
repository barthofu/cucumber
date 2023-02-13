package org.cucumber.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.cucumber.client.utils.classes.Controller;
import org.cucumber.client.services.MessageManager;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.client.utils.enums.Views;
import org.cucumber.common.dto.SocketMessage;
import org.cucumber.common.dto.SocketMessageContent;
import org.cucumber.common.dto.socketmsg_impl.HelloMsg;
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
    protected void onSignUp(ActionEvent event) throws IOException {
        FXUtils.goTo(Views.REGISTER.getViewName(), this, event);
    }

    @FXML
    protected void onLoginButton(ActionEvent event) throws IOException {

        System.out.println("login");
        FXUtils.goTo(Views.LOGIN.getViewName(), this, event);

        try {
            Logger.log(LoggerStatus.INFO, "trying sending hello");

            MessageManager.getInstance().send(
                    new SocketMessage(
                            UUID.randomUUID().toString(),
                            "helloWorld",
                            new HelloMsg("ClientApp")
                    ),
                    LoginController::handleHelloResponse,
                    this
            );
        } catch (IOException e) {
            Logger.log(LoggerStatus.SEVERE, e.getMessage());
        }
    }

    //run on hello response
    public static void handleHelloResponse(SocketMessageContent message, Object context){
        System.out.println("hello from the callback on server response : " + ((HelloMsg) message).getText());
    }
}
