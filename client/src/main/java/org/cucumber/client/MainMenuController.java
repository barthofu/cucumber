package org.cucumber.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.cucumber.client.utils.classes.Controller;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.client.utils.enums.Views;
import org.cucumber.common.dto.UserDTO;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;

import java.io.IOException;


public class MainMenuController extends Controller {

    @FXML
    protected Label usernameLabel;
    @FXML
    protected Label connectedUsersLabel;

    public MainMenuController() {
        super("La vitesse rencontre l'amour!");
    }

    @FXML
    protected void onQuitButton(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    protected void onFavButton(ActionEvent event) throws IOException {
        Logger.log(LoggerStatus.INFO, "fav");
        FXUtils.goTo(Views.FAV.getViewName(), this, event);
    }

    @FXML
    protected void onStartDating(ActionEvent event) throws IOException {

        FXUtils.goTo(Views.WAITING.getViewName(), this, event);
    }

    protected void getHowManyClient(){
        // TODO: 14/02/2023 ask how many client to serv
        int nbCLient = 4;
        this.connectedUsersLabel.setText(String.format("%d personnes connectées", nbCLient));
    }

    protected void getCurrentUser(UserDTO user){
        usernameLabel.setText("Connecté en tant que " + user);
    }

    @Override
    public void onView() {
        getHowManyClient();
        getCurrentUser(null);
    }
}
