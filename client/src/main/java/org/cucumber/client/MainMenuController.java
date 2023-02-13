package org.cucumber.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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




}
