package org.cucumber.server.controllers;

import org.cucumber.common.dto.MessageDTO;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.generics.Status;
import org.cucumber.common.utils.Routes;
import org.cucumber.server.models.so.SocketClient;
import org.cucumber.server.services.ChatRoomService;
import org.cucumber.server.utils.classes.Controller;

public class ChatController {


    public static class SendMessage extends Controller {
        public static final String route = Routes.Server.CHAT_SEND.getValue();
        public SendMessage() { super(route); }

        @Override
        public void handle(SocketClient socketClient, String requestId, Object args) {
            MessageDTO arguments = args instanceof MessageDTO ? ((MessageDTO) args) : null;
            if (arguments != null) {
                //TODO: xxx
                // 1. get the id of the sender
                // 2. get the id of the receiver
                // 3. save in database
                // 4. send to the receiver
            }
        }
    }

    public static class JoinRoom extends Controller {
        public static final String route = Routes.Server.CHAT_JOIN.getValue();
        public JoinRoom() { super(route); }

        @Override
        public void handle(SocketClient socketClient, String requestId, Object args) {

            ChatRoomService.getInstance().addWaitingUser(
                    socketClient.getUser().getId(),
                    requestId,
                    socketClient.getSocketId()
            );
        }
    }

    public static class CancelJoin extends Controller {
        public static final String route = Routes.Server.CHAT_CANCEL.getValue();
        public CancelJoin() { super(route); }

        @Override
        public void handle(SocketClient socketClient, String requestId, Object args) {

            ChatRoomService.getInstance().removeWaitingUser(socketClient.getUser().getId());
            socketClient.sendToClient(new SocketMessage(
                    requestId,
                    new Status(true)
            ));
        }
    }

}
