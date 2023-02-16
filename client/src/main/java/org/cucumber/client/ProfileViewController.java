package org.cucumber.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;
import org.cucumber.client.services.SocketMessageService;
import org.cucumber.client.services.UserService;
import org.cucumber.client.utils.classes.Controller;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.client.utils.enums.Views;
import org.cucumber.common.dto.UserDTO;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.base.SocketMessageContent;
import org.cucumber.common.dto.generics.UserTarget;
import org.cucumber.common.utils.Routes;

import java.io.IOException;
import java.util.UUID;

@Getter
@Setter
public class ProfileViewController extends Controller {

    public ProfileViewController() {
        super("En attente d'un interlocuteur");
    }

    @FXML
    protected Label username;
    @FXML
    protected TextField age;
    @FXML
    protected TextArea description;
    @FXML
    protected ImageView avatar;

    @FXML
    protected Button editButton;

    private boolean isEditting = false;

    @Override
    public void onInit() {

        UserDTO userDTO = UserService.getInstance().getCurrentUser();

        username.setText(userDTO.getUsername());
        age.setText(Integer.toString(userDTO.getAge()));
        description.setText(userDTO.getDescription());

        if (userDTO.getAvatar() != null) {
            new Thread(() -> {
                Image image = new Image(userDTO.getAvatar());
                Platform.runLater(() -> avatar.setImage(image));
            }).start();
        }
    }

    @FXML
    protected void onReturnButton(ActionEvent event) throws IOException {
        FXUtils.goTo(Views.MAIN_MENU.getValue(), this, event);
    }

    @FXML
    protected void onEditButton(ActionEvent event) throws IOException {

        if (isEditting) {
            // save

            UserDTO userDTO = UserService.getInstance().getCurrentUser();
            userDTO.setAge(Integer.parseInt(age.getText()));
            userDTO.setDescription(description.getText());

            SocketMessage message = new SocketMessage(
                    UUID.randomUUID().toString(),
                    Routes.Server.USER_EDIT.getValue(),
                    new UserTarget(userDTO)
            );

            SocketMessageService.getInstance().send(message, ProfileViewController::handleSaveResponse, this);

        } else {
            // edit

            isEditting = true;
            editButton.setText("Sauvegarder");

            age.setEditable(true);
            description.setEditable(true);
        }
    }

    private static <T extends Controller> void handleSaveResponse(SocketMessageContent response, T context) {

        if (response instanceof UserDTO) {
            Platform.runLater(() -> {
                ProfileViewController controller = (ProfileViewController) context;

                controller.isEditting = false;
                controller.editButton.setText("Modifier");

                controller.age.setEditable(false);
                controller.description.setEditable(false);

                UserService.getInstance().setCurrentUser(((UserDTO) response));
            });
        }
    }

}
