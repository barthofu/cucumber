package org.cucumber.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;

import java.io.IOException;

public class MainMenuController {

    @FXML
    protected void onQuitButton(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    protected void onFavButton(ActionEvent event) {
        Logger.log(LoggerStatus.INFO, "fav");
    }


}
