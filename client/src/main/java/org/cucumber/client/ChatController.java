package org.cucumber.client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import org.cucumber.client.services.SocketMessageService;
import org.cucumber.client.utils.classes.Controller;
import org.cucumber.common.dto.MessageDTO;
import org.cucumber.common.dto.UserDTO;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.base.SocketMessageContent;
import org.cucumber.common.dto.generics.UserTarget;
import org.cucumber.common.utils.Routes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    //TOP BAR
    @FXML
    protected Label name;
    @FXML
    protected Label chrono;
    @FXML
    protected Button fav;
    @FXML
    protected Button stop;

    public static UserDTO chatter;

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

        this.name.setText(chatter.getUsername());
    }

    public void appendMessage(MessageDTO message, boolean isOwnMessage) {

        final int paddingSize = 10;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String formattedDate = simpleDateFormat.format(Date.from(message.getDate()));

        VBox vBox = new VBox();
        vBox.setSpacing(-1);
        vBox.setAlignment(isOwnMessage ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        vBox.setPadding(new Insets(15, 0, 0, 0));

        // text
        HBox hBox = new HBox();
        hBox.setAlignment(isOwnMessage ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(0, isOwnMessage ? 0 : paddingSize, 0, isOwnMessage ? paddingSize : 0));

        Text messageText = new Text(message.getContent());
        messageText.setWrappingWidth(300);
        messageText.setStyle("-fx-font-size: 15px;");

        TextFlow textFlow = new TextFlow(messageText);
        textFlow.setPadding(new Insets(0, paddingSize, 0, paddingSize));
        textFlow.setStyle(
                (isOwnMessage ? "-fx-background-color: #00bfff;" : "-fx-background-color: #f0f0f0;") +
                        " -fx-background-radius: 10px;");

        hBox.getChildren().add(textFlow);

        // info
        Text infoText = new Text("\n" + formattedDate);
        infoText.setStyle("-fx-font-size: 10px;");

        TextFlow infoTextFlow = new TextFlow(infoText);
        infoTextFlow.setPadding(new Insets(0, isOwnMessage ? paddingSize : 0, 0, isOwnMessage ? 0 : paddingSize));
        infoTextFlow.setTextAlignment(isOwnMessage ? TextAlignment.RIGHT : TextAlignment.LEFT);

        vBox.getChildren().add(hBox);
        vBox.getChildren().add(infoTextFlow);

        vbox_messages.getChildren().add(vBox);
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
        tf_message.setText("");
    }

    private static <T extends Controller> void handleMessageSendResponse(SocketMessageContent response, T context) {

    }
    private static <T extends Controller> void handleAddFavResponse(SocketMessageContent response, T context) {

    }

    @FXML
    protected void onFav(ActionEvent event) throws IOException {
        // send message to server
        SocketMessageService.getInstance().send(
                new SocketMessage(
                        UUID.randomUUID().toString(),
                        Routes.Server.FAV_ADD.getValue(),
                        new UserTarget(chatter)
                ),
                ChatController::handleAddFavResponse,
                this
        );
        this.fav.setDisable(true);
    }

    @Override
    public void onView() {

    }
}
