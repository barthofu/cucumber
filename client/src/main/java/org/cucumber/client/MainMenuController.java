package org.cucumber.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.cucumber.client.utils.classes.FXUtils;
import org.cucumber.common.so.LoggerStatus;
import org.cucumber.common.utils.Logger;

import java.io.IOException;

public class MainMenuController {

    @FXML
    protected void onQuitButton(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    protected void onFavButton(ActionEvent event) throws IOException {
        Logger.log(LoggerStatus.INFO, "fav");
        FXUtils.goToFav(this, event);
    }


}
