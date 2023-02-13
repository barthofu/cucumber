package org.cucumber.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.cucumber.client.services.MessageManager;
import org.cucumber.client.utils.classes.Controller;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.client.utils.enums.Views;
import org.cucumber.common.dto.SocketMessage;
import org.cucumber.common.dto.SocketMessageContent;
import org.cucumber.common.dto.contents.JoinRoomResponse;
import org.cucumber.common.dto.contents.RegisterRequest;
import org.cucumber.common.dto.contents.RegisterResponse;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;

import java.io.IOException;
import java.util.UUID;


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

    public static void onShowCallback(MainMenuController context){
        // TODO: 14/02/2023 ask how many client to serv
        int nbCLient = 4;
        context.connectedUsersLabel.setText(String.format("%d personnes connectées", nbCLient));
    }
    @FXML
    protected void onStartDating(ActionEvent event) throws IOException {

        FXUtils.goTo(Views.WAITING.getViewName(), this, event);
    }




}
