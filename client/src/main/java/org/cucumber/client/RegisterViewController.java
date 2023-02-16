package org.cucumber.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.cucumber.client.services.SocketMessageService;
import org.cucumber.client.utils.classes.Controller;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.client.utils.enums.Views;
import org.cucumber.common.dto.base.SocketMessage;
import org.cucumber.common.dto.base.SocketMessageContent;
import org.cucumber.common.dto.generics.Status;
import org.cucumber.common.dto.requests.RegisterRequest;
import org.cucumber.common.utils.Routes;

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
    @FXML
    public TextField age;
    @FXML
    public TextArea description;
    @FXML
    public TextField avatar;

    private ActionEvent lastActionEvent;

    @Override
    public void onInit() {

        // force the field to be numeric only
        age.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                age.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    protected void onRegisterButton(ActionEvent event) throws IOException {

        // checks
        if (username.getText().isEmpty()) errorLabel.setText("Le nom d'utilisateur est vide");
        else if (password.getText().isEmpty()) errorLabel.setText("Le mot de passe est vide");
        else if (passwordVerif.getText().isEmpty()) errorLabel.setText("La vérification du mot de passe est vide");
        else if (!password.getText().equals(passwordVerif.getText())) errorLabel.setText("Les mots de passes ne correpondent pas");

        lastActionEvent = event;

        RegisterRequest registerRequest = RegisterRequest.builder()
                .username(username.getText())
                .password(password.getText())
                .build();

        if (!age.getText().isEmpty()) registerRequest.setAge(Integer.parseInt(age.getText()));
        if (!description.getText().isEmpty()) registerRequest.setDescription(description.getText());
        if (!avatar.getText().isEmpty()) registerRequest.setAvatar(avatar.getText());

        SocketMessageService.getInstance().send(
                new SocketMessage(
                        UUID.randomUUID().toString(),
                        Routes.Server.AUTH_REGISTER.getValue(),
                        registerRequest
                ),
                RegisterViewController::handleRegisterResponse,
                this
        );
    }

    private static <T extends Controller> void handleRegisterResponse(SocketMessageContent response, T context) {
        Status status = (Status) response;

        try {
            if (status.isSuccess()) {
                Platform.runLater(() -> {
                    try {
                        FXUtils.goTo(Views.LOGIN.getValue(), context, ((RegisterViewController) context).lastActionEvent);
                    } catch (IOException e) {
                        ((RegisterViewController) context).errorLabel.setText(e.getMessage());
                    }
                });
            } else {
                Platform.runLater(() -> {
                    ((RegisterViewController) context).errorLabel.setText(status.getMessage() != null ? status.getMessage() : "Une erreur est survenue");
                });
            }
        } catch (Exception e) {
            System.out.println(RegisterViewController.class.getName() + " : "+  e.getMessage());
        }
    }

}
