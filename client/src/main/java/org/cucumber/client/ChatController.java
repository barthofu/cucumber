package org.cucumber.client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.cucumber.client.services.SocketMessageService;
import org.cucumber.client.utils.classes.Controller;
import org.cucumber.common.dto.MessageDTO;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.base.SocketMessageContent;
import org.cucumber.common.utils.Routes;

import java.io.IOException;
import java.util.UUID;

public class ChatController extends Controller implements Initializable {

    @FXML
    private Button button_send;
    @FXML
    private TextField tf_message;
    @FXML
    private VBox vbox_messages;
    @FXML
    private ScrollPane sp_main;

    public ChatController() {
        super("Chat");
    }

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        // scroll to bottom when new message is added
        vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                sp_main.setVvalue((Double) newValue);
            }
        });

    }

    @FXML
    protected void onSendMessage(ActionEvent event) throws IOException {

        MessageDTO messageDTO = new MessageDTO(tf_message.getText());
        if (!messageDTO.getContent().isEmpty()) {
            // send message to server
            SocketMessageService.getInstance().send(
                    new SocketMessage(UUID.randomUUID().toString(), Routes.Server.CHAT_SEND.getValue(), messageDTO),
                    ChatController::handleMessageSendResponse,
                    this
            );
        }
    }

    private static <T extends Controller> void handleMessageSendResponse(SocketMessageContent response, T context) {

    }

    @Override
    public void onView() {

    }
}
