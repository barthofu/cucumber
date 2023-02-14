package org.cucumber.client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.cucumber.client.utils.classes.Controller;

import java.io.IOException;

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

    }

    @Override
    public void onView() {

    }
}
