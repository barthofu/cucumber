package org.cucumber.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import lombok.Getter;
import org.cucumber.client.services.SocketMessageService;
import org.cucumber.client.utils.classes.Controller;
import org.cucumber.common.dto.MessageDTO;
import org.cucumber.common.dto.UserDTO;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.base.SocketMessageContent;
import org.cucumber.common.dto.generics.Empty;
import org.cucumber.common.dto.generics.UserTarget;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;
import org.cucumber.common.utils.Routes;
import org.cucumber.common.utils.Timeout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Getter
public class ChatViewController extends Controller {

    public ChatViewController() {
        super("Chat");
    }

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

    private Long timerStart = System.currentTimeMillis();

    @Override
    public void onInit() {

        // send message on enter key pressed
        this.button_send.getScene().setOnKeyPressed(ke -> {
            if (ke.getCode().toString().equals("ENTER")) {
                try {
                    onSendMessage(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // scroll to bottom when new message is added
        vbox_messages.heightProperty().addListener((observableValue, oldValue, newValue) -> sp_main.setVvalue((Double) newValue));

        this.name.setText(chatter.getUsername());
        this.timerStart = System.currentTimeMillis();
        updateChrono();
    }

    /**
     * Update the label **chrono**
     * Recursive function which manage the time elapsed since the beginning of the date
     * -> run onStop() if the timer as reach 5000 min
     */
    private void updateChrono() {

        long current = System.currentTimeMillis();
        int min5 = 300000;

        if (current <= (timerStart + min5)) { //+ 5min
            Timeout.setTimeout(() -> {
                long minutes = (int) (((min5 - (current - timerStart)) / 1000) / 60);
                int seconds = (int) (((min5 - (current - timerStart)) / 1000) % 60);
                Platform.runLater(() -> chrono.setText(String.format("%d:%d", minutes, seconds)));
                updateChrono();
            }, 1000);
        } else {
            try {
                onStop(null);
                Logger.log(LoggerStatus.WARNING, "No more time !");
            } catch (Exception e) {
                Logger.log(LoggerStatus.SEVERE, "error on stop");
            }
        }
    }

    /**
     * display a new message
     * @see MessageDTO
     * @param isOwnMessage : is currentUser owning this message
     * @param message : the message
     * */
    public void appendMessage(MessageDTO message, boolean isOwnMessage) {
        final int paddingSize = 10;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String formattedDate = simpleDateFormat.format(Date.from(message.getDate()));

        VBox vBox = new VBox();
        vBox.setSpacing(-1);
        vBox.setAlignment(isOwnMessage ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        vBox.setPadding(new Insets(15, 0, 0, 0));

        // message content text
        HBox hBox = new HBox();
        hBox.setAlignment(isOwnMessage ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(0, isOwnMessage ? 0 : paddingSize, 0, isOwnMessage ? paddingSize : 0));

        Text messageText = new Text(message.getContent());
        messageText.setWrappingWidth(300);
        messageText.setStyle("-fx-font-size: 14px;");

        TextFlow textFlow = new TextFlow(messageText);
        textFlow.setPadding(new Insets(0, paddingSize, 0, paddingSize));
        textFlow.setStyle(
                (isOwnMessage ? "-fx-background-color: #00bfff;" : "-fx-background-color: #d9d9d9;") +
                        " -fx-background-radius: 10px;");

        hBox.getChildren().add(textFlow);

        // additional info text
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

        String messageContent = tf_message.getText();

        if (messageContent.isEmpty()) {
            return;
        }

        MessageDTO messageDTO = new MessageDTO(messageContent);

        // send message to server
        SocketMessageService.getInstance().send(
                new SocketMessage(UUID.randomUUID().toString(), Routes.Server.CHAT_SEND.getValue(), messageDTO),
                ChatViewController::handleMessageSendResponse,
                this
        );

        // clear message field
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
                ChatViewController::handleAddFavResponse,
                this
        );

        this.fav.setDisable(true);
    }

    @FXML
    protected void onStop(ActionEvent event) throws IOException {
        // send message to server
        SocketMessageService.getInstance().send(
                new SocketMessage(
                        UUID.randomUUID().toString(),
                        Routes.Server.CHAT_CLOSE.getValue(),
                        new Empty()
                ),
                ChatViewController::handleStopResponse,
                this
        );

        this.fav.setDisable(true);
    }

    private static <T extends Controller> void handleStopResponse(SocketMessageContent response, T context) {
    }
}
