package org.cucumber.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.controlsfx.control.action.Action;
import org.cucumber.client.services.ChatService;
import org.cucumber.client.services.MessageManager;
import org.cucumber.client.utils.classes.Controller;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.client.utils.enums.Views;
import org.cucumber.common.dto.SocketMessage;
import org.cucumber.common.dto.SocketMessageContent;
import org.cucumber.common.dto.contents.*;

import java.io.IOException;
import java.util.UUID;

public class WaitingController extends Controller implements Initializable {

    public WaitingController() {
        super("En attente d'un interlocuteur");
    }

    @FXML
    protected Button button_cancel;

    @FXML
    protected void onCancel(ActionEvent event) throws IOException {
        MessageManager.getInstance().send(
                new SocketMessage(UUID.randomUUID().toString(), "chat/cancel", new EmptyContent()),
                WaitingController::handleCancelResponse,
                this
        );
        FXUtils.goTo(Views.MAIN_MENU.getViewName(), this, event);
    }

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        try {
            MessageManager.getInstance().send(
                    new SocketMessage(
                            UUID.randomUUID().toString(),
                            "chat/join",
                            new EmptyContent()
                    ),
                    WaitingController::handleJoinChatRoomResponse,
                    this
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T extends Controller> void handleCancelResponse(SocketMessageContent socketMessageContent, T context) {
        GenericSuccess response = (GenericSuccess) socketMessageContent;
        if (response.isSuccess()) {
            Platform.runLater(() -> {
                try {
                    FXUtils.goTo(Views.MAIN_MENU.getViewName(), context, ((WaitingController) context).button_cancel.getScene());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private static <T extends Controller> void handleJoinChatRoomResponse(SocketMessageContent socketMessageContent, T context) {
        try {
            UserInfo userInfo = (UserInfo) socketMessageContent;

            ChatService.getInstance().startChat(userInfo);

            Platform.runLater(() -> {
                try {
                    FXUtils.goTo(Views.CHAT.getViewName(), context, ((WaitingController) context).button_cancel.getScene());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e){
            System.out.println(MainMenuController.class.getName() + " : "+  e.getMessage());
        }
    }
}
