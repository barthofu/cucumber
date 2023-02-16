package org.cucumber.server.controllers;

import org.cucumber.common.dto.MessageDTO;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.generics.Empty;
import org.cucumber.common.dto.generics.Status;
import org.cucumber.common.dto.generics.UserTarget;
import org.cucumber.common.utils.Routes;
import org.cucumber.server.core.SocketManager;
import org.cucumber.server.models.bo.Message;
import org.cucumber.server.models.so.SocketClient;
import org.cucumber.server.services.ChatRoomService;
import org.cucumber.server.services.MessageService;
import org.cucumber.server.utils.classes.Controller;
import org.cucumber.server.utils.mappers.MessageMapper;
import org.mapstruct.factory.Mappers;

public class ChatController {


    public static class ChatSend extends Controller {
        public static final String route = Routes.Server.CHAT_SEND.getValue();

        public ChatSend() {
            super(route);
        }

        @Override
        public void handle(SocketClient socketClient, String requestId, Object args) {
            MessageDTO arguments = args instanceof MessageDTO ? ((MessageDTO) args) : null;

            if (arguments != null) {

                Message message = MessageService.getInstance().saveMessage(socketClient.getUser(), arguments.getContent());

                if (message != null) {
                    // send back the message to both users

                    SocketMessage socketMessage = new SocketMessage(
                            requestId,
                            Routes.Client.MESSAGE_RECEIVE.getValue(),
                            Mappers.getMapper(MessageMapper.class).messageToMessageDTO(message)
                    );

                    SocketClient socketReceiver = SocketManager.getInstance().getByUserId(message.getTo().getId());
                    socketReceiver.sendToClient(socketMessage);
                    socketClient.sendToClient(socketMessage);
                }
            }
        }
    }

    public static class ChatJoin extends Controller {
        public static final String route = Routes.Server.CHAT_JOIN.getValue();

        public ChatJoin() {
            super(route);
        }

        @Override
        public void handle(SocketClient socketClient, String requestId, Object args) {
            ChatRoomService.getInstance().addWaitingUser(
                    socketClient.getUser().getId(),
                    requestId,
                    socketClient.getSocketId()
            );
        }
    }

    public static class ChatCancel extends Controller {
        public static final String route = Routes.Server.CHAT_CANCEL.getValue();

        public ChatCancel() {
            super(route);
        }

        @Override
        public void handle(SocketClient socketClient, String requestId, Object args) {

            ChatRoomService.getInstance().removeWaitingUser(socketClient.getUser().getId());
            socketClient.sendToClient(new SocketMessage(
                    requestId,
                    new Status(true)
            ));
        }
    }

    public static class ChatClose extends Controller {
        public static final String route = Routes.Server.CHAT_CLOSE.getValue();

        public ChatClose() {
            super(route);
        }

        @Override
        public void handle(SocketClient socketClient, String requestId, Object args) {

            ChatRoomService.getInstance().closeRoom(socketClient.getUser().getId());
        }
    }
}
