package org.cucumber.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.cucumber.client.services.ChatService;
import org.cucumber.client.services.SocketMessageService;
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

public class WaitingViewController extends Controller {

    public WaitingViewController() {
        super("En attente d'un interlocuteur");
    }

    @FXML
    protected Button button_cancel;

    @FXML
    protected void onCancel(ActionEvent event) throws IOException {
        SocketMessageService.getInstance().send(
                new SocketMessage(UUID.randomUUID().toString(), Routes.Server.CHAT_CANCEL.getValue(), new Empty()),
                WaitingViewController::handleCancelResponse,
                this
        );
        FXUtils.goTo(Views.MAIN_MENU.getValue(), this, event);
    }

    @Override
    public void onInit() {
        try {
            SocketMessageService.getInstance().send(
                    new SocketMessage(
                            UUID.randomUUID().toString(),
                            Routes.Server.CHAT_JOIN.getValue(),
                            new Empty()
                    ),
                    WaitingViewController::handleJoinChatRoomResponse,
                    this
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T extends Controller> void handleCancelResponse(SocketMessageContent response, T context) {

        Status status = (Status) response;

        if (status.isSuccess()) {
            Platform.runLater(() -> {
                try {
                    FXUtils.goTo(Views.MAIN_MENU.getValue(), context, ((WaitingViewController) context).button_cancel.getScene());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private static <T extends Controller> void handleJoinChatRoomResponse(SocketMessageContent response, T context) {
        try {
            UserDTO userDTO = (UserDTO) response;
            ChatService.getInstance().startChat(userDTO);
            Platform.runLater(() -> {
                try {
                    ChatViewController.chatter = userDTO;
                    FXUtils.goTo(Views.CHAT.getValue(), context, ((WaitingViewController) context).button_cancel.getScene());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e){
            System.out.println(MainMenuViewController.class.getName() + " : "+  e.getMessage());
        }
    }

}
