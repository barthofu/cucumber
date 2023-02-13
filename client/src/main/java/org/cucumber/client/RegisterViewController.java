package org.cucumber.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.cucumber.client.utils.classes.Controller;
import org.cucumber.client.services.MessageManager;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.client.utils.enums.Views;
import org.cucumber.common.dto.SocketMessage;
import org.cucumber.common.dto.SocketMessageContent;
import org.cucumber.common.dto.socketmsg_impl.RegisterMsg;
import org.cucumber.common.dto.socketmsg_impl.RegisterResponse;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;

import java.io.IOException;
import java.util.UUID;

public class RegisterViewController extends Controller {

    public RegisterViewController() {
        super("S'inscrire");
    }

    @FXML
    protected Label errorLabel;
    @FXML
    protected TextField username;
    @FXML
    protected TextField password;
    @FXML
    protected TextField passwordVerif;

    private ActionEvent lastActionEvent;

    @FXML
    protected void onRegisterButton(ActionEvent event) throws IOException {
        Logger.log(LoggerStatus.INFO, "register");

        if (password.getText().equals(passwordVerif.getText())){
            lastActionEvent = event;
            MessageManager.getInstance().send(
                    new SocketMessage(
                            UUID.randomUUID().toString(),
                            "register",
                            new RegisterMsg(username.getText(), password.getText())
                    ),
                    RegisterViewController::handleRegisterResponse,
                    this
            );
        }else{
            errorLabel.setText("Les mots de passes ne correpondent pas");
        }
    }

    private static <T extends Controller> void handleRegisterResponse(SocketMessageContent socketMessageContent, T context) {
        try {
            if (( (RegisterResponse) socketMessageContent).isStatus()) {
                Platform.runLater(() -> {
                    try {
                        FXUtils.goTo(Views.LOGIN.getViewName(), context, ((RegisterViewController) context).lastActionEvent);
                    } catch (IOException e) {
                        ((RegisterViewController) context).errorLabel.setText(e.getMessage());
                    }
                });
            }else{
                Platform.runLater(() -> {
                    ((RegisterViewController) context).errorLabel.setText("Erreur lors de la création du compte");
                });
            }
        }catch (Exception e){
            System.out.println(RegisterViewController.class.getName() + " : "+  e.getMessage());
        }
    }
}
