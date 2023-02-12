package org.cucumber.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.cucumber.client.utils.classes.FXUtils;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button loginBtn;

    @FXML
    protected void onLoginButton(ActionEvent event) throws IOException {
        System.out.println("login");

        Parent parent = FXMLLoader.load(LoginController.class.getResource(FXUtils.mainMenuViewName));
        Scene scene = new Scene(parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setTitle("Cucumber - La vitesse rencontre l'amour!");
        appStage.setResizable(false);
        appStage.setScene(scene);
        appStage.show();
    }
}