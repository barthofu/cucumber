package org.cucumber.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.cucumber.client.services.MessageManager;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.common.dto.SocketMessage;
import org.cucumber.common.dto.SocketMessageContent;
import org.cucumber.common.dto.socketmsg_impl.HelloMsg;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;

import java.io.IOException;
import java.util.UUID;

public class LoginController {
    @FXML
    private Button loginBtn;

    @FXML
    protected void onLoginButton(ActionEvent event) throws IOException {
        System.out.println("login");
        FXUtils.goToMainMenu(this, event);

        try {
            Logger.log(LoggerStatus.INFO, "trying sending hello");
//            Client.getInstance().send(
            MessageManager.getInstance().send(
                    new SocketMessage(
                            UUID.randomUUID().toString(),
                            "helloWorld",
                            new HelloMsg("ClientApp")
                    ),
                    LoginController::handleHelloResponse
            );
        } catch (IOException e) {
            Logger.log(LoggerStatus.SEVERE, e.getMessage());
        }
    }

    //run on hello response
    public static void handleHelloResponse(SocketMessageContent message){
        System.out.println("hello from the callback on server response : " + ((HelloMsg) message).getText());
    }
}